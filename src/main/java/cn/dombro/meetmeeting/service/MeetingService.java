package cn.dombro.meetmeeting.service;

import cn.dombro.meetmeeting.model.Meeting;
import cn.dombro.meetmeeting.model.Request;
import cn.dombro.meetmeeting.util.UpdateMeetingTask;
import cn.dombro.meetmeeting.util.UpdateSchedulerListener;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import it.sauronsoftware.cron4j.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeetingService.class);

    private MeetingService(){}

    public static MeetingService getInstance(){
        return new MeetingService();
    }

    //插入一条会议记录
    public int createMeeting(int uid,String title, String content,LocalDateTime date, String site, String[] labelArray, int restrict,int isPublic){
        String label = "";
        for(int i = 0;i < labelArray.length;i++){
            if ( i == labelArray.length-1){
                label += labelArray[i];
            }else {
                label += labelArray[i] + ",";
            }
        }
        Meeting meeting = new Meeting().set("title",title).set("content",content).set("date",date).
                            set("site",site).set("u_id",uid).set("label",label).set("restrict",restrict).set("status",0).set("isPublic",isPublic);
        meeting.save();
        int mid = meeting.getInt("m_id");
        LOGGER.info("向 meeting 表中新增 title: "+title+" date: "+date+" site: "+site+" 记录");
        //在向meeting表中插入记录后通过调度器在会议开始时修改该记录举办状态
        int startMinute = date.getMinute();
        int startHour = date.getHour();
        int startDay = date.getDayOfMonth();
        int startMonth = date.getMonthValue();
        Scheduler scheduler = new Scheduler();
        UpdateMeetingTask task = new UpdateMeetingTask(mid);
        UpdateSchedulerListener listener = new UpdateSchedulerListener();
        scheduler.schedule(startMinute+" "+startHour+" "+startDay+" "+startMonth+" *",task);
        scheduler.addSchedulerListener(listener);
        scheduler.start();
        return mid;
    }

    //根据会议id获取会议信息
    public Meeting getMeetingById(int mid){
        Kv cond = Kv.by("m_id = ",mid);
        SqlPara sqlPara = Db.getSqlPara("meeting.find",Kv.by("cond",cond));
        return Meeting.dao.findFirst(sqlPara);
    }

    //根据举办方id查询未举办的会议列表
    public List<Meeting> getNotHeldMeetingList(int uid){
        Kv cond = Kv.by("u_id = ",uid).set("status = ",0);
        SqlPara sqlPara = Db.getSqlPara("meeting.findList",Kv.by("cond",cond));
        List<Meeting> meetingList = Meeting.dao.find(sqlPara);
        return meetingList;
    }

    //根据举办方全部会议列表
    public List<Meeting> getMeetingListByUid(int uid){
        Kv cond = Kv.by("u_id = ",uid);
        SqlPara sqlPara = Db.getSqlPara("meeting.findList",Kv.by("cond",cond));
        return Meeting.dao.find(sqlPara);
    }

    //向request表中插入记录
    public int createRequest(int uid,int mid,String name,String tel,String remark){
        Request request = new Request().set("u_id",uid).set("m_id",mid).set("name",name).
                                        set("tel",tel).set("remark",remark).set("status",0);
        request.save();
        int rid = request.getInt("r_id");
        LOGGER.info("向 request 表中插入m_id: "+mid+" u_id: "+mid+"的一条记录");
        return rid;
    }

    //根据r_id查找request记录
    public Request getRequestByRid(int rid){
        Kv cond = Kv.by("r_id = ",rid);
        SqlPara sqlPara = Db.getSqlPara("request.find",Kv.by("cond",cond));
        return Request.dao.findFirst(sqlPara);
    }


    //根据举办方id得到List<Request>,只显示未审核且会议未召开的申请;只查询 rid 和 name 字段
    public List<Request> getRequestByUid(int uid){
        List<Meeting> meetingList = getNotHeldMeetingList(uid);
        List<Request> requestList = new ArrayList<>();
        for (Meeting meeting:meetingList){
            List<Request> requests =  meeting.getListRequest();
            requestList.addAll(requests);
        }
        return requestList;
    }

    //修改审核状态
    public void reviewRequest(int rid,int isPass){
        getRequestByRid(rid).set("status",isPass).update();
        LOGGER.info("更新 request 表中r_id: "+rid+" 记录的 status: "+isPass);
    }

    //根据审核状态查看个人申请列表 (查询字段 r_id,m_id,name)
    public List<Request> getMyRequest(int uid,int status){
        Kv cond = Kv.by("u_id = ",uid).set("status = ",status);
        SqlPara sqlPara = Db.getSqlPara("request.find",Kv.by("cond",cond));
        List<Request> requestList = Request.dao.find(sqlPara);
        return requestList;
    }

    //根据标签获取会议列表
    public List<Meeting> getMeetingByLabel(String label){
        Kv cond = Kv.by("label Like ","%"+label+"%").set("status = ",0).set("isPublic = ",1);
        SqlPara sqlPara = Db.getSqlPara("meeting.findList",Kv.by("cond",cond));
        return Meeting.dao.find(sqlPara);
    }

    //更新会议召开状态
    public boolean updateMeetingStatus(int mid){
        boolean isUpdate = getMeetingById(mid).set("status",1).update();
        if (isUpdate){
            LOGGER.info("更新 meeting 表中m_id: "+mid+" 记录的 status: 1 成功");
        }else {
            LOGGER.info("更新 meeting 表中m_id: "+mid+" 记录的 status: 1 失败");
        }
        return isUpdate;
    }

    //根据会议是否开始查询会议列表
    public List<Meeting> getMeetingByStatus(){
        Kv cond = Kv.by("status = ",0);
        SqlPara sqlPara = Db.getSqlPara("meeting.findList",Kv.by("cond",cond));
        return Meeting.dao.find(sqlPara);
    }

    //根据会议标题查找未召开且公开的的会议
    public List<Meeting> getMeetingByTitle(String title){
        Kv cond = Kv.by("title Like ","%"+title+"%").set("status = ",0).set("isPublic = ",1);
        SqlPara sqlPara = Db.getSqlPara("meeting.findList",Kv.by("cond",cond));
        return Meeting.dao.find(sqlPara);
    }

    //删除会议(只能会议发布者删除)
    public boolean deleteMeeting(int mid){
        boolean isDelete =  getMeetingById(mid).delete();
        if (isDelete){
            LOGGER.info("删除meeting表中 mid: "+mid+"的记录 成功");
        }else {
            LOGGER.info("删除meeting表中 mid: "+mid+"的记录 失败");
        }
        return isDelete;
    }

}
