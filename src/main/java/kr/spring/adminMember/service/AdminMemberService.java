package kr.spring.adminMember.service;

import java.util.List;
import java.util.Map;

import kr.spring.adminMember.vo.AdminMemberVO;

public interface AdminMemberService {
	
	//============== 회원관리 ==============
	public int getMemberCount(Map<String,Object> map);
	public List<AdminMemberVO> getMemberList(Map<String,Object> map);
	public AdminMemberVO selectCheckMember(String mem_id);
	public AdminMemberVO selectMember(int mem_num);
	public void updateMember(AdminMemberVO adminMemberVO);
	public void deleteMember(int mem_num);
	public int getOrderCount(int mem_num);
	public int getCancelCount(int mem_num);

	/* public int getReviewCount(int mem_num); */
	public int getQnaCount(int mem_num);
	
	//============== 관리자관리 ==============
	public AdminMemberVO selectAdmin(int mem_num);
	public int getAdminCount();
	public List<AdminMemberVO> getAdminList(Map<String,Object> map);
	public void insertAdmin(AdminMemberVO adminMemberVO);
}
