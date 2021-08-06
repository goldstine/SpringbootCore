package com.atguigu.elastic.bean;

import io.searchbox.annotations.JestId;
import lombok.Data;

@Data
public class Article {

    @JestId        //表示JestId注解，表示这是一个主键
    private Integer id;
    private String author;
    private String title;
    private String content;
}
