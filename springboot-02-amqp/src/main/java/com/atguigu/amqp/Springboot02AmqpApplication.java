package com.atguigu.amqp;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 自动装配：
 * （1）rabbitAutoConfiguration
 * (2)有自动配置类连接工厂ConnectionFactory
 * (3)RabbitProperties封装了RabbitMq的配置
 * (4)RabbitTemplate:给RabbitMq发送和接收消息
 *（5）自动装配类中还有AmqpAdmin：RabbitMq的系统管理组件
 */

@EnableRabbit //前面都是通过配置类的bean   RabbitTemplate进行操作的，这里要使用@RabbitListener就需要开启基于注解的RabbitMq,@EnableRabbit
@SpringBootApplication
public class Springboot02AmqpApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot02AmqpApplication.class, args);
	}

}
