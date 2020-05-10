package com.cb.myblog.controller.admin;

import com.cb.myblog.domain.User;
import com.cb.myblog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private IUserService userService;

    /**
     * 登录
     */
    @CrossOrigin
    @PostMapping("/login")
    public Map<String,String> login(@RequestBody User user){
        System.out.println("Login...");
        User rtUser = userService.checkUser(user.getUsername(), user.getPassword());

        Map<String, String> map;
        if (rtUser != null) {
            map=userService.userToken(""+rtUser.getId());
            return map;
        }
        map = new HashMap<>();
        map.put("msg", "error");
        return map;
    }

    /**
     * 注销
     */
    @CrossOrigin
    @GetMapping("/logout")
    public String logout(@RequestParam String userId){

        System.out.println("Logout...");
        if (userService.rmCurrentUser(userId)) {
            return "success";
        }

        return "error";
    }

    /**
     * 返回当前登录用户的信息
     */
    @CrossOrigin
    @GetMapping("/currentUser")
    public User checkMode(@RequestParam String id,@RequestParam String token){
        if ("".equals(id) || id == null) {
            return null;
        }

        if ("".equals(token) || token == null) {
            User user = userService.getCurrentUser();
            if (user == null) {
                return null;
            }
            user.setPassword("");
            user.setUsername("");
            return user;
        } else {
            if (userService.checkToken(id, token)){
                return userService.getCurrentUser();
            }
        }
        return null;
    }
}
