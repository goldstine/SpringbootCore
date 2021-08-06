package com.atguigu.user.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.ticket.service.TicketService;
import org.springframework.stereotype.Service;

@Service  //这里标记的注解时spring的组件
public class UserService {
    //这里如果服务的消费者需要进行消费，远程调用provider-ticket的getTicket()
    //首先需要将服务提供者注册到注册中心   provider-ticket

    @Reference
    TicketService ticketService;

    public void hello(){
        String ticket = ticketService.getTicket();
        System.out.println("买到票了:"+ticket);
    }

}
