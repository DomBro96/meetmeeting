package cn.dombro.meetmeeting.service;

import cn.dombro.meetmeeting.model.Group;
import cn.dombro.meetmeeting.model.Signin;
import cn.dombro.meetmeeting.model.User;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SigninService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SigninService.class);

    private SigninService(){

    }

    public static SigninService getInstance(){
        return new SigninService();
    }

    //新建签到表
    public int createSignin(int gid){
        String conferee = Group.dao.findById(gid).getStr("conferee");
        Signin signin = new Signin().set("g_id",gid).set("absence",conferee);
        signin.save();
        LOGGER.info("向 signin 插入 g_id : "+gid+"的记录");
        return signin.getInt("g_id");
    }

    //根据uid和gid从签到表中删除
    public boolean sign(int gid,int uid){
        //1.获取签到表中的absence
        //2.用""替换 uid
        //获取所有参会人员
        Signin signin = Signin.dao.findById(gid);
        String conferee = signin.getStr("absence");
        String confereeAll = Group.dao.findById(gid).getStr("conferee");
        Pattern pattern = Pattern.compile("\\b"+uid+"\\b\\s");
        Matcher matcher = pattern.matcher(conferee);
        Matcher matcher1 = pattern.matcher(confereeAll);
        //如果该用户不在群组中返回 false
        if (! matcher1.find()){
            return false;
        }
        //如果该用户在群组中但不在签到表中说明该用户已经签到 返回 false
        if (matcher1.find() && !matcher.find()){
            return false;
        }else {
         //否则更新改签到表
            String absence = matcher.replaceAll("");
            signin.set("absence",absence).update();
            LOGGER.info("signin表中 g_id : "+gid+" 将 u_id :"+uid);
            return true;
        }
    }

    //获取未签到名单
    public List<User> getAbsence(int gid){
        //1.得到桥到表未到名单
        Signin signin = Signin.dao.findById(gid);
        String conferee = signin.getStr("absence");
        String[] absenceAll = conferee.split("\\s");
        List<User> userList = new ArrayList();
        for (String absence:absenceAll){
            int uid = Integer.parseInt(absence);
            Kv cond = Kv.by("u_id = ",uid);
            SqlPara sqlPara = Db.getSqlPara("user.find",Kv.by("cond",cond));
            User user = User.dao.findFirst(sqlPara);
            userList.add(user);
        }
        return userList;
    }

    //抽奖业务
    public List<User> getRandomUser(int gid,int count){
        String conferee = Group.dao.findById(gid).getStr("conferee");
        String[] absenceArray = conferee.split("\\s");
        //随机数最大值
        int n = absenceArray.length - 1;
        Random random = new Random();
        List<User> userList = new ArrayList<>();
        //寻找count个用户
        for (int i = 0; i < count; i++){
            int lucky = Integer.parseInt(absenceArray[random.nextInt(n)]);
            Kv cond = Kv.by("u_id = ",lucky);
            SqlPara sqlPara = Db.getSqlPara("user.find",Kv.by("cond",cond));
            User luckyUser = User.dao.findFirst(sqlPara);
            userList.add(luckyUser);
        }
        return userList;
    }

}
