package com.cb.myblog.repository;

import com.cb.myblog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends JpaRepository<User,Integer> {

    User findByUsernameAndPassword(String username, String password);
}
