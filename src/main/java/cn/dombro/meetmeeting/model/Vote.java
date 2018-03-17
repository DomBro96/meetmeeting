package cn.dombro.meetmeeting.model;


import com.jfinal.plugin.activerecord.Model;

public class Vote  extends Model<Vote>{

    public static final Vote dao = new Vote().dao();
}
