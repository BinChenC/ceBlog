package com.cb.myblog.service.impl;

import com.cb.myblog.domain.User;
import com.cb.myblog.repository.IUserDao;
import com.cb.myblog.service.IUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author cb
 * @version 1.0
 * @date 2020/3/16 9:22
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private IUserDao userDao;

    private String salt = "qwerty234567-=+hjgiorewj";

    @Override
    public User checkUser(String username,String password) {
        String saltPassword = salt + "/" + password;
        User user = userDao.findByUsernameAndPassword(username, DigestUtils.md5DigestAsHex(saltPassword.getBytes()));
        if (user != null) {
            user.setPassword(null);
            user.setBlogs(null);
            redisTemplate.opsForValue().set("user",user,30, TimeUnit.MINUTES);
        }
        return user;
    }

    @Override
    public Map<String, String> userToken(String userId) {
        Map<String, String> map = new HashMap<>();
        String randomStr = RandomStringUtils.randomAlphanumeric(5);
        String token = BCrypt.hashpw(randomStr, BCrypt.gensalt());
        map.put("msg", "success");
        map.put("token", token);
        map.put("id", userId);
        redisTemplate.opsForValue().set("token:"+userId,token,30, TimeUnit.MINUTES);
        return map;
    }

    @Override
    public Boolean checkToken(String id, String token) {
        Object user = redisTemplate.opsForValue().get("token:"+id);
        if (user == null) {
            return false;
        }
        return token.equals(user.toString());
    }

    @Override
    public User getCurrentUser(){
        User user = (User) redisTemplate.opsForValue().get("user");
        if (user == null) {
            return null;
        }
        return user;
    }

    @Override
    public Boolean rmCurrentUser(String id){
        Boolean user = redisTemplate.delete("user");
        Boolean token = redisTemplate.delete("token:" + id);
        return user && token;
    }

}
