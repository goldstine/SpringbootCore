package com.atguigu.elastic;

import com.atguigu.elastic.bean.Article;
import com.atguigu.elastic.bean.Book;
import com.atguigu.elastic.repository.BookRepository;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class Springboot03ElasticApplicationTests {

	//注入BookRepository
	@Autowired
	private BookRepository bookRepository;


//	@Autowired        springboot2.x不能自动注入对应的bean
//	private JestClient jestClient;
//通过JestClientFactory手动注入的方式
	public JestClient getJestCline(){
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig
				.Builder("http://192.168.13.135:9200")
				.multiThreaded(true)
				.build());
		return  factory.getObject();
	}

	JestClient jestClient=getJestCline();

	@Test
	public void testjest() throws IOException {
		//1、给ES中索引（保存）一个文档

		Article article = new Article();
		article.setId(1);
		article.setTitle("好消息");
		article.setAuthor("zhangsan");
		article.setContent("Hello World");

		//构建一个索引功能
		Index index = new Index.Builder(article).index("atguigu").type("news").build();

		jestClient.execute(index);
	}

	//测试搜索
	@Test
	public void search(){
		String json = "{\n" + "    \"query\" : {\n" + "        \"match\" : {\n"
				+ "            \"content\" : \"测试内容\"\n" + "        }\n"
				+ "    }\n" + "}";

		//构建搜索
		Search search = new Search.Builder(json).addIndex("jiatp").addType("news").build();
		try {
			SearchResult result = jestClient.execute(search);
			System.out.println(result.getJsonString());
		} catch(IOException e) {
			e.printStackTrace();
		}

	}

	//通过继承elasticsearchRepository接口的方法操作ES
	@Test
	public void testrepo(){
		Book book = new Book();
		bookRepository.save(book);
//		bookRepository.index(book);
	}


	@Test
	void contextLoads() {
	}

}
