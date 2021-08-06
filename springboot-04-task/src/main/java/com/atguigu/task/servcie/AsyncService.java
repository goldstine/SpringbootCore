package com.atguigu.task.servcie;

import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    //创建一个异步任务
    public void hello(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("处理数据中。。。。");
    }

}
