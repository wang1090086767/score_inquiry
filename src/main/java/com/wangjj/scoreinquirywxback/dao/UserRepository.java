package com.wangjj.scoreinquirywxback.dao;

import com.wangjj.scoreinquirywxback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName : UserRepository
 * @Author : wangJJ
 * @Date : 2020/1/2 16:56
 * @Description : TODO
 */
public interface UserRepository extends JpaRepository<User,Long> {

	/**
	 * 通过账户名和密码查询用户
	 * @param account 账户
	 * @param password 密码
	 * @return 用户
	 */
	User findByAccountAndPassword(String account, String password);
}
