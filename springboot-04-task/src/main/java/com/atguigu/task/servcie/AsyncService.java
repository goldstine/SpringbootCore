package com.atguigu.task.servcie;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    //创建一个异步任务
    //加上注解@Async告诉springboot这是一个异步的方法，同时要在主启动类上加上@EnableAsync才会起作用
    @Async
    public void hello(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("处理数据中。。。。");
    }

}
