package cn.dombro.meetmeeting.util;


import cn.dombro.meetmeeting.service.MeetingService;
import it.sauronsoftware.cron4j.TaskExecutionContext;

public class UpdateMeetingTask extends it.sauronsoftware.cron4j.Task {

    private MeetingService meetingService = null;

    private int mid;

    public UpdateMeetingTask(int mid) {
        this.meetingService = MeetingService.getInstance();
        this.mid = mid;
    }
    //要做的任务
    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        meetingService.updateMeetingStatus(mid);
        taskExecutionContext.setStatusMessage("update status");
    }

    //可以获取任务完成度
    @Override
    public boolean supportsCompletenessTracking() {
        return true;
    }

    //使任务可以停止
    @Override
    public boolean canBeStopped() {
        return true;
    }

    @Override
    public boolean supportsStatusTracking() {
        return  true;
    }
}
