package com.atguigu.ticket;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * (1)将服务的提供者注册到注册中心，首先要整合dubbo
 *		1、引入dubbo和zkclient相关依赖
 *		2、配置dubbo的扫描包和注册中心
 *		3、使用@Service发布服务
 */

@SpringBootApplication
@EnableDubbo     //如果发生空指针错误，原因是没有开启对应的Dubbo  所以需要在启动类上加上@EnableDubbo
public class ProviderTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProviderTicketApplication.class, args);
	}

}
