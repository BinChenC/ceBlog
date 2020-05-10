//package com.cb.myblog.repository;
//
//import com.cb.myblog.domain.Article;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class IArticleDaoTest {
//
//    @Autowired
//    private IArticleDao dao;
//
//    @Test
//    void save(){
//        Article article = new Article();
//        article.setTitle("Spring Boot");
//        article.setAuthor("cebb");
//        article.setSort("科学");
//        Article save = dao.save(article);
//        System.out.println(save);
//    }
//
//    @Test
//    void findById(){
//        Article article = dao.findById(3).get();
//        System.out.println(article);
//    }
//
//}