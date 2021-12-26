package kr.spring.adminInfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.adminInfo.dao.AdminInfoMapper;
import kr.spring.login.vo.LoginVO;

@Service
public class AdminInfoServiceImpl implements AdminInfoService{

	@Autowired
	private AdminInfoMapper adminInfoMapper;
	
	@Override
	public LoginVO adminInfo(String mem_id) {
		return adminInfoMapper.adminInfo(mem_id);
	}

	@Override
	public void updateInfoAction(LoginVO loginVO) {
		adminInfoMapper.updateInfoAction(loginVO);
	}

	@Override
	public void updateInfoPasswdAction(LoginVO loginVO) {
		adminInfoMapper.updateInfoPasswdAction(loginVO);
	}
	
	

}
