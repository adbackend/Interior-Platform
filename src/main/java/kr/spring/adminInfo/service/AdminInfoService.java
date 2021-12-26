package kr.spring.adminInfo.service;

import kr.spring.login.vo.LoginVO;

/**
  * @FileName : AdminInfoService.java
  * @Date : 2021. 10. 11. 
  * @Author : 최유정
  * @Description : 관리자 마이페이지 Service단
  */
public interface AdminInfoService {
	
	public LoginVO adminInfo(String mem_id); //내 정보
	
	public void updateInfoAction(LoginVO loginVO); //내 수정
	
	public void updateInfoPasswdAction(LoginVO loginVO); //내정보 비밀번호 변경


}
