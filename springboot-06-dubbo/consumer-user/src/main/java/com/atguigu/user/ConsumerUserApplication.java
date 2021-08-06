package com.atguigu.user;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 消费者要使用对应的注册中心的服务
 * （1）首先引入对应的依赖dubbo   zkclient
 * (2)配置对应的zookeeper的位置 ，
 * （3）引用服务使用  要在消费者端创建一样的接口，所在的包名也要一样
 * （4）通过调用（3）对应的接口进行远程调用
 */

@SpringBootApplication
@EnableDubbo      //如果发生空指针错误，原因是没有开启对应的Dubbo  所以需要在启动类上加上@EnableDubbo
public class ConsumerUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerUserApplication.class, args);
	}

}
