package cn.dombro.meetmeeting.test;


import cn.dombro.meetmeeting.model.*;
import cn.dombro.meetmeeting.service.*;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;

import java.time.LocalDateTime;

public class ServiceTest {

    public static void main(String[] args) {
        PropKit.use("config.properties");
        //druid 数据源插件
        DruidPlugin dp = new DruidPlugin(PropKit.get("jdbcUrl"),PropKit.get("user"),PropKit.get("password"));
        dp.setDriverClass(PropKit.get("driverClass"));
        dp.set(PropKit.getInt("initialSize"),PropKit.getInt("minIdle"),PropKit.getInt("maxActive"));
        dp.setMaxWait(PropKit.getInt("maxWait"));
        //arp 插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        arp.setBaseSqlTemplatePath("E:\\IDEA\\meetmeeting\\src\\main\\webapp\\WEB-INF");
        arp.addSqlTemplate("sql/all.sql");
        arp.addMapping("user","u_id", User.class);
        arp.addMapping("contact", Contact.class);
        arp.addMapping("meeting","m_id" ,Meeting.class);
        arp.addMapping("request","r_id", Request.class);
        arp.addMapping("message","msg_id", Message.class);
        arp.addMapping("group","g_id",Group.class);
        arp.addMapping("vote","v_id",Vote.class);
        arp.addMapping("voting_result",VotingResult.class);
        arp.addMapping("signin","g_id",Signin.class);
        dp.start();
        arp.start();
        // System.out.println( MeetingService.getInstance().getNotHeldMeetingList(2));
        //UserService.getInstance().createUser("1300000000","dombro","111","dasdsadsad");
        //int id = UserService.getInstance().getByAccAndPwd("1300000000","111").getInt("u_id");
        //System.out.println(UserService.getInstance().isAccExist("1300000001"));
//        if(UserService.getInstance().isPwdRight(1,"111")){
//            UserService.getInstance().updatePwd(1,"22222");
//        }
       // UserService.getInstance().editUser(1,"dongbo","dasdsadas");

        //System.out.println(UserService.getInstance().getByNameOrAcc("o"));
//        String[] labels = {"面基","同学会","技术会"};
//        int mid =  MeetingService.getInstance().createMeeting(3,"dadas","dafafafa", LocalDateTime.now(),"dafafas",labels,1,1);
//        System.out.println(mid);
//        System.out.println(MeetingService.getInstance().getMeetingById(10));
//        MeetingService.getInstance().updateMeetingStatus(9);
        //System.out.println(MeetingService.getInstance().getMeetingById(5));
        //System.out.println(MeetingService.getInstance().getMeetingList(2));
        //MeetingService.getInstance().createRequest(1,2,"黄猪","130000001","我希望参加您的这次会议");
//        System.out.println(MeetingService.getInstance().getRequestByUid(2));
        //System.out.println(MeetingService.getInstance().getMeetingList(2));
//        MeetingService.getInstance().reviewRequest(3,1);
//        System.out.println(MeetingService.getInstance().getRequestByRid(3));
        //System.out.println(MeetingService.getInstance().getMyRequest(1,1));
       // System.out.println(MeetingService.getInstance().getMeetingByLabel("茶话会"));
        //System.out.println(MeetingService.getInstance().getMeetingByTitle("da"));
        //ContactService.getInstance().createContact(1,2,0);
        // ContactService.getInstance().deleteContact(1,2);
        //System.out.println(ContactService.getInstance().isFriend(1,2));
        //MessageService.getInstance().createMessage(3,1,1,"哈哈啊哈哈哈",LocalDateTime.now());
        //System.out.println(MessageService.getInstance().getListByUid(2));
       // System.out.println(MessageService.getInstance().getByMsgId(2));
       // System.out.println(MessageService.getInstance().updateMessage(2));
        //System.out.println(MessageService.getInstance().deleteMessage(3));
        //GroupService.getInstance().createGroup(5,1);
        //System.out.println(GroupService.getInstance().dropGroup(2,11));
//        String[] item = {"是","不是"};
//        VoteService.getInstance().createVote(2,"董博是好人吗",2,item);
//        MeetingService.getInstance().createRequest(1,2,"ahahahaah","12312312312","xasff");
//        System.out.println(VoteService.getInstance().getByVid(1));
//        System.out.println(VoteService.getInstance().getItems(1)[0]+VoteService.getInstance().getItems(1)[1]);
//         VoteService.getInstance().createVotingReslt(1,"是",2);
//         VoteService.getInstance().createVotingReslt(1,"不是",3);
//         VoteService.getInstance().createVotingReslt(1,"是",4);
//         VoteService.getInstance().createVotingReslt(1,"不是",5);
//         VoteService.getInstance().createVotingReslt(1,"是",6);
         //System.out.println(VoteService.getInstance().createVotingReslt(1,"是",2));
        //System.out.println(VoteService.getInstance().getVoteResult(1));
//          System.out.println(SigninService.getInstance().createSignin(1));
//          System.out.println(SigninService.getInstance().createSignin(1));
//           SigninService.getInstance().sign(1,5);
//        System.out.println(SigninService.getInstance().getAbsence(1));
//        System.out.println(SigninService.getInstance().getRandomUser(1,2));
        //MeetingService.getInstance().createMeeting()


    }
}
