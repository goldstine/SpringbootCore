package com.atguigu.cache;

import com.atguigu.cache.bean.Employee;
import com.atguigu.cache.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class Springboot01CacheApplicationTests {

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate<Object,Employee> empredisTemplate;

	@Test
	public void testEmp(){
		Employee emp1 = employeeMapper.getEmpById(1);
		System.out.println(emp1);
	}

	//测试redis的使用

	/**
	 * 通过redis操作一些基本的数据类型  list  set  zset(有序集合) hash  String
	 * stringRedisTemplate.opsForValue();操作字符串
	 * stringRedisTemplate.opsForHash();操作hash
	 * stringRedisTemplate.opsForList();  操作列表
	 * 		stringRedisTemplate.opsForSet();操作集合
	 * 		stringRedisTemplate.opsForZSet(); 操作有序集合
	 */
	@Test
	public void testRedis(){
		//往redis添加一条数据
//		stringRedisTemplate.opsForValue().append("msg","goldstine");
		//从redis中读出一条数据
//		String msg = stringRedisTemplate.opsForValue().get("msg");
//		System.out.println(msg);
		stringRedisTemplate.opsForList().leftPush("mylist","1");
		stringRedisTemplate.opsForList().leftPush("mylist","2");
	}

	//测试redisTemplate保存对象
	@Test
	public void testobjRedis(){
		Employee empById = employeeMapper.getEmpById(1);
//		redisTemplate.opsForValue().set("emp-01",empById);
		//set给某一个key设置一个值，append给某一个key追加一个值

		empredisTemplate.opsForValue().set("emp-01",empById);

	}


	@Test
	void contextLoads() {
	}

}
