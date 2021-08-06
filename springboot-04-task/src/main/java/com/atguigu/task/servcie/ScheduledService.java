package com.atguigu.task.servcie;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {
    //通过注解@Scheduled表示这是一个定时执行方法，该注解内部提供cron表达式
//    * on the second, minute, hour, day of month, month, and day of week.
//            * <p>For example, {@code "0 * * * * MON-FRI"} means once per minute on weekdays

    //星期可以写英文，也可以写数字0-7   0和7都表示周日   1-6分别表示周一到周六
    // ？是用来进行冲突匹配的 比如MON  在周几指定为周一MON   就不能在日期指定为每一天，需要写上？进行冲突匹配

//    @Scheduled(cron = "0 * * * * MON-FRI")//*表示任意时间，表示周一-周五 每月每日每时每分整秒启动
//    @Scheduled(cron = "0,1,2,3,4,5 * * * * MON-FRI")//表示0秒，1秒，2秒，3秒，4秒，5秒都启动，表示列举
//    @Scheduled(cron = "1-5 * * * * MON-FRI")   //表示区间启动 1-5每一秒启动一次
    @Scheduled(cron = "0/4 * * * * MON-FRI")    //表示步长 ，从0秒启动，每4秒执行一次
    public void hello(){
        System.out.println("hello执行定时任务。。。。");
    }
}
