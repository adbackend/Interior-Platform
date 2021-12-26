package kr.spring.adminMember.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.adminMember.dao.AdminMemberMapper;
import kr.spring.adminMember.vo.AdminMemberVO;

@Service
@Transactional
public class AdminMemberServiceImpl implements AdminMemberService{
	
	@Autowired
	private AdminMemberMapper adminMemberMapper;
	
	//============== 회원관리 ==============
	@Override
	public int getMemberCount(Map<String,Object> map) {
		return adminMemberMapper.getMemberCount(map);
	}
	
	@Override
	public List<AdminMemberVO> getMemberList(Map<String, Object> map){	
		return adminMemberMapper.getMemberList(map);
	}
	
	@Override
	public AdminMemberVO selectCheckMember(String mem_id) {
		return adminMemberMapper.selectCheckMember(mem_id);
	}
	
	@Override
	public AdminMemberVO selectMember(int mem_num) {
		return adminMemberMapper.selectMember(mem_num);
	}
	
	@Override
	public void updateMember(AdminMemberVO adminMemberVO) {
		adminMemberMapper.updateMember(adminMemberVO);
		adminMemberMapper.updateMemberAuth(adminMemberVO);
	}
	
	@Override
	public void deleteMember(int mem_num) {
		adminMemberMapper.deleteMember(mem_num);
		adminMemberMapper.deleteMemberAuth(mem_num);
	}

	@Override
	public int getOrderCount(int mem_num) {
		return adminMemberMapper.getOrderCount(mem_num);
	}

	@Override
	public int getCancelCount(int mem_num) {
		return adminMemberMapper.getCancelCount(mem_num);
	}

	/*
	 * @Override public int getReviewCount(int mem_num) { return
	 * adminMemberMapper.getReviewCount(mem_num); }
	 */

	@Override
	public int getQnaCount(int mem_num) {
		return adminMemberMapper.getQnaCount(mem_num);
	}
	
	//============== 관리자관리 ==============
	@Override
	public AdminMemberVO selectAdmin(int mem_num) {
		return adminMemberMapper.selectAdmin(mem_num);
	}
	
	@Override
	public int getAdminCount() {
		return adminMemberMapper.getAdminCount();
	}

	@Override
	public List<AdminMemberVO> getAdminList(Map<String, Object> map) {
		return adminMemberMapper.getAdminList(map);
	}
	
	@Override
	public void insertAdmin(AdminMemberVO adminMemberVO) {
		adminMemberVO.setMem_num(adminMemberMapper.selectMem_num());
		adminMemberMapper.insertAdmin(adminMemberVO);
		adminMemberMapper.insertAdminDetail(adminMemberVO);
	}

}
