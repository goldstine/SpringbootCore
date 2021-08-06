package com.atguigu.amqp.service;

import com.atguigu.amqp.bean.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    /**
     * 通过监听消息队列进行消息接收
     * 监听队列，只要被监听的队列中有消息，这会执行下面的方法
     */
    @RabbitListener(queues = "atguigu.news")
    public void receive(Book book){    //这里监听接收的是对象
        System.out.println("收到消息："+book);
    }

    //还可以监听接受Message消息结构体，包括消息的消息头，消息体等等
    @RabbitListener(queues = "atguigu")
    public void receive02(Message message){
        System.out.println(message.getBody());
        System.out.println(message);
    }

}
