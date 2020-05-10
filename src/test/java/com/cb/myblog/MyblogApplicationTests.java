package com.cb.myblog;

import com.cb.myblog.repository.ITagDao;
import com.cb.myblog.service.IBlogService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;


@SpringBootTest
class MyblogApplicationTests {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    ITagDao tag;

    @Autowired
    IBlogService blogService;

    @Test
    void contextLoads() {
        String psw = "qwerty234567-=+hjgiorewj/123";
        System.out.println(DigestUtils.md5DigestAsHex(psw.getBytes()));
    }


    @Test
    void test1(){
//        String randomStr = RandomStringUtils.randomAlphanumeric(5);
//        String token = BCrypt.hashpw(randomStr, BCrypt.gensalt());
//        System.out.println(randomStr);
//        System.out.println(token);
        redisTemplate.opsForHash().put("token","1","asdasfdasfafas");
        System.out.println(redisTemplate.opsForHash().get("token", "1"));
        Long token = redisTemplate.opsForHash().delete("token", "1");
        System.out.println(token);
        System.out.println(redisTemplate.opsForHash().get("token", "1"));

    }
}