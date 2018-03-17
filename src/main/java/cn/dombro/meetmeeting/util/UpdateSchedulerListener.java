package cn.dombro.meetmeeting.util;

import it.sauronsoftware.cron4j.SchedulerListener;
import it.sauronsoftware.cron4j.TaskExecutor;

public class UpdateSchedulerListener implements SchedulerListener {

    @Override
    public void taskLaunching(TaskExecutor taskExecutor) {

    }

    //在任务执行成功时被调用
    @Override
    public void taskSucceeded(TaskExecutor taskExecutor) {
       if (taskExecutor.getStatusMessage().equals("update status") && taskExecutor.getScheduler().isStarted()){
           //关闭调度器
          taskExecutor.getScheduler().stop();
          System.out.println(taskExecutor.getScheduler().isStarted());
       }
    }

    @Override
    public void taskFailed(TaskExecutor taskExecutor, Throwable throwable) {
        System.out.println("任务执行失败");
    }
}
