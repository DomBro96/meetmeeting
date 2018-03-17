package cn.dombro.meetmeeting.model;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.SqlPara;

import java.util.List;

public class Meeting extends Model<Meeting> {

    public static final Meeting dao = new Meeting().dao();

    //查看每个会议未审核的申请列表
    public List<Request> getListRequest(){
        Kv cond = Kv.by("m_id = ",get("m_id")).set("status = ",0);
        SqlPara sqlPara = Db.getSqlPara("request.findList",Kv.by("cond",cond));
        return Request.dao.find(sqlPara);
    }
}
