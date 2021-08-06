package com.atguigu.amqp;

import com.atguigu.amqp.bean.Book;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class Springboot02AmqpApplicationTests {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private AmqpAdmin amqpAdmin;

	/**
	 * 发送单播模式下的（点对点）
	 */
	@Test
	public void testRabbit(){
		//Message需要自己构造一个；定义消息体内容
//		rabbitTemplate.send(exchange,routeKey,message);
//		rabbitTemplate.convertAndSend(exchange,routeKey,object);只需要传入要发送的对象，自动序列化
		Map<String,Object> map=new HashMap();
		map.put("msg","这是第一个消息");
		map.put("data", Arrays.asList("helloworld",123,true));
//		rabbitTemplate.convertAndSend("exchange.direct","atguigu.news",map);
		//对象map被默认序列化之后发送到queue

		rabbitTemplate.convertAndSend("exchange.direct","atguigu.news",new Book("红楼梦","曹雪芹"));
	}

	/**
	 * 接收消息
	 */
	@Test
	public void receive(){
		Object msg = rabbitTemplate.receiveAndConvert("atguigu.news");
		System.out.println(msg.getClass());
		System.out.println(msg);
	}

	//广播方式fanout
	@Test
	public void testrabbit(){
		rabbitTemplate.convertAndSend("exchange.fanout","",new Book("三国演义","罗贯中"));

	}

	/**
	 * 通过AmqpAdmin创建交换机和队列以及绑定规则
	 *
	 */
	@Test
	public void testAmqp(){
		//接口Exchange有很多实现类

//		amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));//需要传入一个Exchange对象，所以需要先创建一个Exchange对象
//		System.out.println("创建交换机完成");

		//同样可以创建队列   queue是一个类，所以直接new
//		amqpAdmin.declareQueue(new Queue("amqpadmin.queue",true));

		//创建binding
		amqpAdmin.declareBinding(new Binding("amqpadmin.queue",Binding.DestinationType.QUEUE,"amqpadmin.exchange","amqp.haha",null));


	}


	@Test
	void contextLoads() {
	}

}
