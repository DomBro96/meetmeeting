package cn.dombro.meetmeeting.service;

import cn.dombro.meetmeeting.model.User;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


//用户业务类
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserService(){}

    //只能通过该静态方法获取实例
    public static UserService getInstance(){
        return new UserService();
    }

    //登录业务
    public User getByAccAndPwd(String account,String password){
        Kv cond = Kv.by("account = ",account).set("password = ",password);
        SqlPara sqlPara = Db.getSqlPara("user.find",Kv.by("cond",cond));
        User user = User.dao.findFirst(sqlPara);
        LOGGER.info("从 user 表中查找 account: "+account+" password: "+password+"记录");
        return user;
}

    //添加用户
    public void createUser(String account,String username,String password,String iconUrl){
        new User().set("account",account).set("username",username).set("password",password).set("icon",iconUrl).save();
        LOGGER.info("向 user 表中插入 account: "+account+" username: "+username+"记录");
    }

    //判断账号是否存在
    public boolean isAccExist(String account){
        Kv cond = Kv.by("account = ",account);
        SqlPara sqlPara = Db.getSqlPara("user.find",Kv.by("cond",cond));
        if (User.dao.findFirst(sqlPara) == null){
            return true;
        }else {
            return false;
        }
    }

    //根据id得到信息
    public User getByUid(int id){
        Kv cond = Kv.by("u_id = ",id);
        SqlPara sqlPara = Db.getSqlPara("user.find",Kv.by("cond",cond));
        User user = User.dao.findFirst(sqlPara);
        return  user;
    }

    //修改信息
    public boolean editUser(int id,String username,String iconUrl){
        boolean isUpdate = getByUid(id).set("username",username).set("icon",iconUrl).update();
        if (isUpdate){
            LOGGER.info("修改 user 表中 u_id 为"+id+"的信息成功");
        }else {
            LOGGER.info("修改 user 表中 u_id 为"+id+"的信息失败");
        }
        return isUpdate;
    }

    //判断输入的密码与原密码是否一致
    public boolean isPwdRight(int id,String password){
        String pwd = User.dao.findByIdLoadColumns(id,"password").get("password");
        if (password.equals(pwd)){
             return true;
        }else {
             return false;
        }
    }
    //修改密码
    public boolean updatePwd(int id,String password){
        boolean isUpdate =  getByUid(id).set("password",password).update();
        if (isUpdate){
            LOGGER.info("用户u_id为"+id+"修改密码为"+password +"成功");
        }else {
            LOGGER.info("用户u_id为"+id+"修改密码为"+password +"失败");
        }
        return isUpdate;
    }

    //通过用户名或账号模糊查询
    public List<User> getByNameOrAcc(String input){
        Kv cond = Kv.by("term","%"+input+"%");
        SqlPara sqlPara = Db.getSqlPara("user.findByLike",cond);
        List<User> userList = User.dao.find(sqlPara);
        return userList;
    }



}
