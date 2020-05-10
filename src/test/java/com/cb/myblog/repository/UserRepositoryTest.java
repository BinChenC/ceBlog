package com.cb.myblog.repository;

import com.cb.myblog.domain.Tag;
import com.cb.myblog.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;

//@SpringBootTest
class UserRepositoryTest {

    RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<Object,Object>();

    @Test
    void findAll(){
        String salt = "qwerty234567-=+hjgiorewj";
        String saltPassword = salt + "/" + "123";
        String s = DigestUtils.md5DigestAsHex(saltPassword.getBytes());
        System.out.println(s);

    }

    @Test
    void testJson(){
        User user =(User) redisTemplate.opsForValue().get("user");
        System.out.println(user);
        System.out.println("-----------------------------------");
    }

}