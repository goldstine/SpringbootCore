package com.atguigu.elastic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot默认支持两种技术来和ES交互
 * 1、Jest（默认是不生效的）
 * 需要导入jest的工具包（io.searchbox.client.JestClient）
 * 2、Springboot  ElasticSearch
 *
 * 可能会出现版本匹配问题。
 * 这里出现了版本匹配问题，报错。。。。需要查对应的文档来接进行版本匹配
 *
 */
@SpringBootApplication
public class Springboot03ElasticApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot03ElasticApplication.class, args);
	}

}
