package cn.dombro.meetmeeting.service;

import cn.dombro.meetmeeting.model.Group;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupService {

    private final static Logger LOGGER = LoggerFactory.getLogger(GroupService.class);

    private GroupService(){}

    public static GroupService getInstance(){
        return new GroupService();
    }

    //新建群组
    public void createGroup(int mid,int admin){
        new Group().set("m_id",mid).set("admin",admin).save();
    }

    //根据用户id显示群组列表，获取到Group对象后可根据 group.getTitle(无参)方法后得到群组名称
    public List<Group> getListByConferee(int conferee){
        SqlPara sqlPara = Db.getSqlPara("group.find");
        List<Group> groupList = new ArrayList<>();
        List<Group> allGroup = Group.dao.find(sqlPara);
        //遍历全部群组列表
        for (Group group:allGroup){
            String confereeStr = group.get("conferee");
            //使用正则表达式
            Pattern pattern = Pattern.compile("\\b"+conferee+"\\b");
            Matcher matcher = pattern.matcher(confereeStr);
            if (matcher.find()){
                groupList.add(group);
            }
        }
        return groupList;
    }

    //根据管理员id显示群组列表
    public List<Group> getListByAdmin(int admin){
        Kv cond = Kv.by("admin = ",admin);
        SqlPara sqlPara = Db.getSqlPara("group.find",cond);
        return Group.dao.find(sqlPara);
    }

    //添加参会人员
    public boolean updateGroup(int gid,int conferee){
        Group group = getGroupById(gid);
        String confereeStr = getConferee(gid);
        confereeStr += conferee;
        boolean isUpdate = group.set("conferee",confereeStr+" ").update();
        if (isUpdate){
            LOGGER.info("更新 group 表中 gid："+gid+" 添加 conferee:"+conferee+"成功");
        }else {
            LOGGER.info("更新 group 表中 gid："+gid+" 添加 conferee:"+conferee+"失败");
        }
        return isUpdate;
    }

    //获取参会人员id
    public String getConferee(int gid){
        String conferee =  getGroupById(gid).get("conferee");
        return conferee;
    }

    //获取群组
    public Group getGroupById(int gid){
        return Group.dao.findById(gid);
    }

    //获取一个群组中的所有成员id
    public int[] getConfereeUid(int gid){
         String[] confereeArray = getConferee(gid).split(" ");
         int[] conferee = new int[confereeArray.length];
         for (int i = 0; i < confereeArray.length;i++){
             conferee[i] = Integer.parseInt(confereeArray[i]);
         }
         return conferee;
    }


    //退出群组
    public boolean dropGroup(int gid,int uid){
        Group group = getGroupById(gid);
        String confereeStr = group.get("conferee");
        String regex = "\\b"+uid+"\\b\\s";
        confereeStr = confereeStr.replaceAll(regex,"");
        System.out.println(confereeStr);
        boolean isUpdate = group.set("conferee",confereeStr).update();

        if (isUpdate){
            LOGGER.info("删除group表中 gid: "+gid+"中 uid:"+uid+"联系人成功");
        }else {
            LOGGER.info("删除group表中 gid: "+gid+"中 uid:"+uid+"联系人失败");
        }
        return isUpdate;
    }

 }
