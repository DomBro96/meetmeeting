package cn.dombro.meetmeeting.service;

import cn.dombro.meetmeeting.model.Contact;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ContactService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);

    private ContactService(){}

    public static ContactService getInstance(){
        return new ContactService();
    }

    //添加记录
    public void createContact(int uid,int cid,int status){
        new Contact().set("u_id",uid).set("c_id",cid).set("status",status).save();
        LOGGER.info("向 contact 表中新增 u_id: "+uid+"c_id: "+cid+" status: "+0+"记录 ");
    }

    //获取一条好友表记录
    public Contact getContact(int uid,int cid){
        Kv cond = Kv.by("u_id = ",uid).set("c_id = ",cid);
        SqlPara sqlPara = Db.getSqlPara("contact.find",Kv.by("cond",cond));
        return Contact.dao.findFirst(sqlPara);
    }

    //更新操作
    public void updateContact(int uid,int cid){
        SqlPara sqlPara = Db.getSqlPara("contact.update",uid,cid);
        Db.update(sqlPara);
        LOGGER.info("向 contact 表中更新 u_id: "+uid+"c_id: "+cid+" status: "+1+"记录 ");
    }

    //获取好友列表(即 status = 1 记录)
    public List<Contact> getContactList(int uid){
        Kv cond = Kv.by("u_id = ",uid).set("status = ",1);
        SqlPara sqlPara = Db.getSqlPara("contact.find",Kv.by("cond",cond));
        return Contact.dao.find(sqlPara);
    }

    //删除好友记录
    public void deleteContact(int uid,int cid){
        SqlPara sqlPara = Db.getSqlPara("contact.delete",uid,cid,1);
        SqlPara sqlPara1 = Db.getSqlPara("contact.delete",cid,uid,1);
        Db.update(sqlPara);
        LOGGER.info("删除 contact 表中 u_id: "+uid+"c_id: "+cid+" status: "+1+"记录 ");
        Db.update(sqlPara1);
        LOGGER.info("删除 contact 表中 u_id: "+cid+"c_id: "+uid+" status: "+1+"记录 ");
    }

    //判断两人是否是好友
    public boolean isFriend(int uid,int cid){
        if(getContact(uid,cid).getInt("status") == 1 && getContact(cid,uid).getInt("status") == 1){
            return true;
        }
        return false;
    }




}
