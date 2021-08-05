package com.atguigu.cache.service;

import com.atguigu.cache.bean.Department;
import com.atguigu.cache.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@Service
public class DeptService {

    @Autowired
    private DepartmentMapper departmentMapper;

//    @Qualifier("deptCacheManager")
    @Autowired
    private RedisCacheManager redisCacheManager;

//    @Cacheable(value = "dept")
//    public Department getDeptById(Integer id){
//        System.out.println("查询部门"+id);
//        Department department=departmentMapper.getDeptById(id);
//        return department;
//    }

    //也可以通过直接缓存的方式，直接缓存进RedisCacheManager
    public Department getDeptById(Integer id){
        System.out.println("查询部门"+id);
        Department department=departmentMapper.getDeptById(id);

        //直接获取缓存对象进行操作CRUD        通过编码的方式主动往redis缓存数据
        Cache dept = redisCacheManager.getCache("dept");
        dept.put("dept:1",department);

        return department;
    }
}
