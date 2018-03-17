package cn.dombro.meetmeeting.service;

import cn.dombro.meetmeeting.model.Vote;
import cn.dombro.meetmeeting.model.VotingResult;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoteService.class);

    private VoteService(){

    }

    public static VoteService getInstance(){
        return new VoteService();
    }

    //创建投票，返回该投票vid
    public int createVote(int gid,String vName,int minute,String[] item){
        //1.首先获取当前时间
        //2.将分钟+minute得到截止时间
        //3.遍历 item 使其变为 item[i]+" " 格式 String
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadLine = now.plusMinutes(minute);
        String items = "";
        for (String it:item){
            items += it +" ";
        }
        Vote vote = new Vote().set("g_id",gid).set("v_name",vName).set("start_time",now).
                                     set("deadline",deadLine).set("item",items);
        System.out.println(vote == null);
        vote.save();
        LOGGER.info("向 vote 表中插入一条 g_id : "+gid+" v_name :"+vName+" 的记录");
        int vid = vote.getInt("v_id");
        return vid;
    }


    /**
     *  1.根据vid获取该投票对象
     *  2.可通过该方法返回值的属性 getter 方法获取各属性值
     */
    public Vote getByVid(int vid){
        Vote vote =  Vote.dao.findById(vid);
        return vote;
    }

    //根据vid获取投票选项数组
    public String[] getItems(int vid){
        String item = Vote.dao.findByIdLoadColumns(vid,"item").getStr("item");
        //1.将该字符串以" "分割
        String[] itemArray = item.split("\\s");
        return itemArray;
    }

    //群成员发布投票
    public boolean createVotingReslt(int vid,String item,int uid){
           List<VotingResult> resultList =  getUserVoteCount(uid,vid);
           //判断该用户是否投过票
           if (resultList.size() > 0){
               return false;
           }else {
               new VotingResult().set("v_id",vid).set("item",item).set("u_id",uid).save();
               LOGGER.info("向 voting_result 表中插入一条 v_id : "+vid+" item : "+item+" 的记录");
               return true;
           }
    }

    //查找Voting_result表中指定 u_id 和 v_id记录
    public List<VotingResult> getUserVoteCount(int uid,int vid){
        Kv cond = Kv.by("v_id = ",vid).set("u_id = ",uid);
        SqlPara sqlPara = Db.getSqlPara("vote.findResult",Kv.by("cond",cond));
        return VotingResult.dao.find(sqlPara);
    }

    //获取投票结果
    public Map<String,Integer> getVoteResult(int vid){
        //1.根据 vid 找到投票选项，得到一个String[]数据
        //2.利用选项数组中的每个选项查询result表中的对应List个数就是票数
        //3.再将选项和对应票数放入Map
        String[] items = getItems(vid);
        Map<String,Integer> resultMap = new HashMap<>();
        for (String item:items){
            Kv cond  = Kv.by("v_id = ",vid).set("item = ",item);
            SqlPara sqlPara = Db.getSqlPara("vote.findResult",Kv.by("cond",cond));
            List<VotingResult> resultList = VotingResult.dao.find(sqlPara);
            int count = resultList.size();
            resultMap.put(item,count);
        }
        return resultMap;
    }
}
