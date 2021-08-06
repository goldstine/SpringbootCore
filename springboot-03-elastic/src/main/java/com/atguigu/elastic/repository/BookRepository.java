package com.atguigu.elastic.repository;

import com.atguigu.elastic.bean.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookRepository extends ElasticsearchRepository<Book,Integer> {
        //泛型表示Book对象，以及Book的主键的类型
    //通过继承ElasticsearchRepository就可以有所有的方法，类似于BaseMapper<Book>

    //这里还可以自定义方法。。。。。
}
