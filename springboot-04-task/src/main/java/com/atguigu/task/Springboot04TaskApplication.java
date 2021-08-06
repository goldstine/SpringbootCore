package com.atguigu.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 异步任务：只需要进行异步执行的方法上加上@Async，然后在主启动类上加上@EnableAsync
 * 然后会自己开一个线程池进行异步执行
 * 定时任务：比如分析一次前一天的日志信息，Spring为我们提供了异步执行任务调度的方式，提供TaskExecutor  TaskScheduler
 * 两个注解@EnableScheduler    @Scheduled
 *
 * 邮件任务：
 */

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class Springboot04TaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot04TaskApplication.class, args);
	}

}
