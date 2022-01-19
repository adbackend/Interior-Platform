package kr.spring.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.login.dao.LoginMapper;
import kr.spring.login.vo.LoginVO;


@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private LoginMapper loginMapper;
	
	@Override
	public LoginVO loginAction(String id) {
		
		return loginMapper.loginAction(id);
	}

	@Override
	public void sendMail(LoginVO loginVO) {
		// TODO Auto-generated method stub
		
	}

}
