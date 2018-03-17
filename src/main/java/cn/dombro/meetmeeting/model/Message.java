package cn.dombro.meetmeeting.model;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.SqlPara;

public class Message extends Model<Message> {

  public static final Message dao = new Message().dao();

  //获取发件人姓名
  public String getUserName(){
    Kv cond = Kv.by("u_id = ",getInt("sender_id"));
    SqlPara sqlPara = Db.getSqlPara("user.find",Kv.by("cond",cond));
    String username =  User.dao.findFirst(sqlPara).get("username");
    return username;
  }

}
