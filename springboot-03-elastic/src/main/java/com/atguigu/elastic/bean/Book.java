package com.atguigu.elastic.bean;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName ="atguigu",indexStoreType = "book")
@Data
public class Book {
    private Integer id;
    private String bookName;
    private String author;
}
