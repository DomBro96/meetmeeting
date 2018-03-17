package cn.dombro.meetmeeting.model;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.SqlPara;

public class Group extends Model<Group> {

   public static final Group dao = new Group().dao();

   public String getTitle(){
      Kv cond = Kv.by("g_id = ",getInt("m_id"));
      SqlPara sqlPara = Db.getSqlPara("meeting.find",Kv.by("cond",cond));
      String title =  Meeting.dao.findFirst(sqlPara).get("title");
      return title;
   }
}
