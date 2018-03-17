package cn.dombro.meetmeeting.util;

import cn.dombro.meetmeeting.service.MeetingService;
import cn.dombro.meetmeeting.service.UserService;

/**
 * 用于建立通知消息的工具类
 */
public final class MsgUtil {

    /**
     * @param content 更新说明
     */
    public static String upgradMsg(String content){
        String msg = "您的系统已经可以更新 : ";
        msg +=  content;
        return msg;
    }

    /** 好友申请通知
     * @param uid 申请方id
     */
    public static String contactRequestMsg(int uid){
        String contactName = UserService.getInstance().getByUid(uid).getStr("username");
        String msg = "用户 "+contactName+" 申请成为您的好友";
        return msg;
    }

    /**
     * 好友申请结果通知
     * @param uid 申请好友的id
     * @param isSuccess 对方是否同意 即 contact表中的 status 字段
     */
    public static String contactResult(int uid,int isSuccess){
        String msg = null;
        String contactName = UserService.getInstance().getByUid(uid).getStr("username");
        if (isSuccess == 0){
            msg = contactName+" 拒绝了您的好友申请";
        }else if (isSuccess == 1){
            msg = contactName+" 接受了您的好友申请";
        }
        return msg;
    }

    /**
     * 参会申请通知无通知内容
     */

    /**
     * @param mid 申请的参会 id
     * @param isSuccess 是否通过申请 即 request 表中的 status 字段
     * @return
     */
    public static String meetingResultMsg(int mid,int isSuccess){
        String title = MeetingService.getInstance().getMeetingById(mid).getStr("title");
        String msg = null;
        if (isSuccess == 0){
            msg = "您申请参加的会议 "+title+" 被会议管理员拒绝了，去大厅看看其他会议吧";
        }else if (isSuccess == 1){
            msg = "您申请参加的会议 "+title+" ，会议管理员已同意您的参会";
        }
        return msg;
    }

}
