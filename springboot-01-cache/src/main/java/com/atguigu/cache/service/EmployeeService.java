package com.atguigu.cache.service;

import com.atguigu.cache.bean.Employee;
import com.atguigu.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

//@CacheConfig(cacheNames = "emp")//抽取缓存的公共配置
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 将方法的运行结果进行缓存：以后再要相同的数据，直接从缓存中获取，不用调用方法，访问数据库
     * cacheManager管理多个cache组件的，对缓存的真正CRUD操作在cache组件中，每一个缓存组件都有一个自己的唯一的名字
     * 几个属性：
     *  cacheNames/values:指定缓存组件的名字;将方法的返回结果放在那个缓存中，是数组的方式，可以指定多个缓存
     *  key:缓存数据使用的key,可以用它来指定。默认是使用方法参数的值   1-方法的返回值
     *      编写SpEL :  #id:参数id的值  等同于 #root.args[0]   #a0   #p0
     *  keyGenerator:key的生成器，可以自己指定key的生成器的组件id
     *           key/keyGenerator二选一，只需要写一个就可以了
     *  cacheManager: 缓存管理器（concurrentHashMap/redis）和cacheResolver缓存解析器二选一，都是指定缓存管理的
     *  condition:指定符合条件的情况下才缓存 condition="#id>0"  表示参数id>0的情况下才进行缓存
     *  unless:否定缓存，当unless指定的条件为true，方法的返回值就不会被缓存
     *      unless="result==null" 表示如果方法返回的结果为null，则不缓存
     *  sync:是否使用异步模式
     *默认是以同步的方式将查询结果缓存，可以设置为异步缓存。
     * 但是异步模式下unless属性不支持
     *
     *  原理：
     *  1、自动配置类 cacheAutoConfiguration.java
     *      org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
     *    2、判断哪一个配置类生效了 SimpleCacheConfiguration
     *      3、给容器中注册了一个cacheManager,concurrentMapCacheManager
     *      5、可以获取和创建ConcurrentMapCache类型的缓存组件：它的作用将数据保存在ConcurrentMap中；
     *      运行流程：
     *       @Cacheable：
     *      *   1、方法运行之前，先去查询Cache（缓存组件），按照cacheNames指定的名字获取；
     *      *      （CacheManager先获取相应的缓存），第一次获取缓存如果没有Cache组件会自动创建。
     *      *   2、去Cache中查找缓存的内容，使用一个key，默认就是方法的参数；
     *      *      key是按照某种策略生成的；默认是使用keyGenerator生成的，默认使用SimpleKeyGenerator生成key；
     *      *          SimpleKeyGenerator生成key的默认策略；
     *      *                  如果没有参数；key=new SimpleKey()；
     *      *                  如果有一个参数：key=参数的值
     *      *                  如果有多个参数：key=new SimpleKey(params)；
     *      *   3、没有查到缓存就调用目标方法；
     *      *   4、将目标方法返回的结果，放进缓存中
     *
     *       @Cacheable标注的方法执行之前先来检查缓存中有没有这个数据，默认按照参数的值作为key去查询缓存，
     *      *   如果没有就运行方法并将结果放入缓存；以后再来调用就可以直接使用缓存中的数据；
     *核心：
     *      *      1）、使用CacheManager【ConcurrentMapCacheManager】按照名字得到Cache【ConcurrentMapCache】组件
     *      *      2）、key使用keyGenerator生成的，默认是SimpleKeyGenerator
     *
     *
     */
    @Cacheable(cacheNames = "emp"/*,keyGenerator="myKeyGenerator",condition = "#a0>1",unless="#a0==2"*/)
    public Employee getEmp(Integer id){
        System.out.println("查询"+id+"号员工");
        Employee empById = employeeMapper.getEmpById(id);
        return empById;
    }

    /**
     * @CachePut 既调用方法，又更新缓存数据
     * 修改了数据库的某个数据，同时更更新缓存
     * 运行时机：
     * （1）先调用目标方法
     * （2）将目标方法的结果缓存起来,由于需要将方法返回值进行缓存，所以更新方法的返回值为更新对象，所以以后写update方法，需要写返回值
     *
     * 测试更新1号员工信息：lastName:zhangsan;gender:0
     *
     * 注意：这里存在一个问题，因为之前getEmp方法缓存的key为id=1
     * 更新的缓存的key为Employee对象，所以不能将原来的缓存更新
     * 这个时候最好是统一指定key，由于传参的时候Employee对象会带id,所以这里直接指定key="#employee.id"
     * updateEmp=>由于返回值和参数是同一个Employee对象，所以也可以写成key="#result.id"
     */
    @CachePut(cacheNames = "emp",key="#employee.id")
    public Employee updateEmp(Employee employee){
        System.out.println("updateEmp:"+employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }

    /**
     * @CacheEvict：缓存清除
     * 删除数据库中的某一个对象之后，将对应的缓存数据也删除
     * key指定要清除的数据
     * allEntries=true 设置删除emp缓存中的所有数据,此时不需要指定key
     * beforeInvocation=false  是否在方法之前执行，默认是false,默认是在方法执行之后执行的
     * 原因是如果方法发生错误10/0，如果之前执行，都会清空缓存，如果之后，则不会清空缓存
     *
     */

    @CacheEvict(value = "emp",key="#id",allEntries = true)
    public void deleteEmp(Integer id){
        System.out.println("deleteEmp:"+id);
        //int i=10/0;最好是在方法执行之后进行CacheEvict清空缓存
//        employeeMapper.deleteEmpById(id);
    }

    /**
     * 通过@Caching指定多个缓存规则
     */
    @Caching(
            cacheable ={
                    @Cacheable(value = "emp",key="#lastName")
            } ,
            put = {
                    @CachePut(value="emp",key="#result.id"),
                    @CachePut(value="emp",key="#result.email")
            }
    )
    public Employee getEmpByLastName(String lastName){
        System.out.println("还是执行了数据库查找");
        Employee emp = employeeMapper.getEmpByLastName(lastName);
        return emp;
    }
}
