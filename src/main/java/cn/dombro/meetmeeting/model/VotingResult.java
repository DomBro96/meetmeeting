package cn.dombro.meetmeeting.model;

import com.jfinal.plugin.activerecord.Model;

public class VotingResult extends Model<VotingResult> {

    public static final VotingResult dao = new VotingResult().dao();
}
