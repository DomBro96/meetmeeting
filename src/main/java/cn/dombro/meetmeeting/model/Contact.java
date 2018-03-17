package cn.dombro.meetmeeting.model;


import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.SqlPara;



public class Contact extends Model<Contact> {

    public static final Contact dao = new Contact().dao();

    public User getUsers(){
        Kv cond = Kv.by("u_id",getInt("c_id"));
        SqlPara sqlPara = Db.getSqlPara("user.find",Kv.by("cond",cond));
        return User.dao.findFirst(sqlPara);
    }

}
