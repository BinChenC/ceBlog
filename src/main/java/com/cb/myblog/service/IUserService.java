package com.cb.myblog.service;

import com.cb.myblog.domain.User;

import java.util.Map;

/**
 * @author cb
 * @version 1.0
 * @date 2020/3/16 9:19
 */
public interface IUserService {

    User checkUser(String username,String password);

    User getCurrentUser();

    Boolean rmCurrentUser(String id);

    Map<String, String> userToken(String userId);

    Boolean checkToken(String id, String token);
}
