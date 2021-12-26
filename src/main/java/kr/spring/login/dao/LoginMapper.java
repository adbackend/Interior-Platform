package kr.spring.login.dao;

import kr.spring.login.vo.LoginVO;

/**
  * @FileName : LoginMapper.java
  * @Date : 2021. 10. 9. 
  * @Author : 최유정
  * @Description : LoginMapper
  */
public interface LoginMapper {
	
	public LoginVO loginAction(String id);

}
