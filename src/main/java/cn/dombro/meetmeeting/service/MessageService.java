package cn.dombro.meetmeeting.service;

import cn.dombro.meetmeeting.model.Message;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public class MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    private MessageService(){}

    public static MessageService getInstance(){
        return new MessageService();
    }

    //增加记录
    public void createMessage(int receiverId, int senderId, int type, String message, LocalDateTime sendDate){
        new Message().set("receiver_id",receiverId).set("sender_id",senderId).set("type",type).
                      set("message",message).set("send_date_time",sendDate).set("read",0).save();
    }

    //删除
    public boolean deleteMessage(int msgId){
        boolean isDelete = getByMsgId(msgId).delete();
        if (isDelete){
            LOGGER.info("从message表中删除 msgId:"+msgId+"的一条记录成功");
        }else {
            LOGGER.info("从message表中删除 msgId:"+msgId+"的一条记录失败");
        }
        return isDelete;
    }

    //根据用户id查找该用户消息列表
    public List<Message> getListByUid(int receiverId){
        Kv cond = Kv.by("receiver_id = ",receiverId);
        SqlPara sqlPara = Db.getSqlPara("message.findList", Kv.by("cond",cond));
        return Message.dao.find(sqlPara);
    }

    //获取具体消息内容
    public Message getByMsgId(int msgId){
        Kv cond = Kv.by("msg_id = ",msgId);
        SqlPara sqlPara = Db.getSqlPara("message.find", Kv.by("cond",cond));
        return Message.dao.findById(msgId);
    }

    //更新是否已读
    public boolean updateMessage(int msgId){
        boolean isUpdate = getByMsgId(msgId).set("read",1).update();
        if (isUpdate){
            LOGGER.info("从message表中修改 msgId:"+msgId+"的一条记录 read: 1 成功");
        }else {
            LOGGER.info("从message表中修改 msgId:"+msgId+"的一条记录 read: 1 失败");
        }
        return isUpdate;
    }

}
