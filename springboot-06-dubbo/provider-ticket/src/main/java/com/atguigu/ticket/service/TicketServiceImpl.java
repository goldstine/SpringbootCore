package com.atguigu.ticket.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service  //加上注解@Service将服务发布出去，这里是dubbo的service发布出去
public class TicketServiceImpl implements TicketService{
    @Override
    public String getTicket() {
        return "《西游记》";
    }
}
