package kr.spring.adminMember.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.adminMember.vo.AdminMemberVO;

public interface AdminMemberMapper {
	
	//============== 회원관리 ==============
	public int getMemberCount(Map<String,Object> map);
	
	public List<AdminMemberVO> getMemberList(Map<String,Object> map);
	
	@Select("SELECT m.*, d.mem_passwd FROM member m LEFT OUTER JOIN member_detail d ON m.mem_num=d.mem_num WHERE mem_id=#{mem_id}")
	public AdminMemberVO selectCheckMember(String mem_id);
	
	@Select("SELECT * FROM member m LEFT OUTER JOIN member_detail d ON m.mem_num=d.mem_num WHERE m.mem_num=#{mem_num} AND mem_auth IN(0,1,2)")
	public AdminMemberVO selectMember(int mem_num);
	
	@Update("UPDATE member_detail SET mem_name=#{mem_name},mem_phone=#{mem_phone},mem_email=#{mem_email},mem_zipcode=#{mem_zipcode},mem_address1=#{mem_address1},mem_address2=#{mem_address2} WHERE mem_num=#{mem_num}")
	public void updateMember(AdminMemberVO adminMemberVO);
	@Update("UPDATE member SET mem_auth=#{mem_auth} WHERE mem_num=#{mem_num}")
	public void updateMemberAuth(AdminMemberVO adminMemberVO);
	
	@Delete("DELETE FROM member_detail WHERE mem_num=#{mem_num}")
	public void deleteMember(int mem_num);
	@Update("UPDATE member SET mem_auth=0 WHERE mem_num=#{mem_num}")
	public void deleteMemberAuth(int mem_num);
	
	@Select("SELECT COUNT(*) FROM sorder WHERE mem_num=#{mem_num}")
	public int getOrderCount(int mem_num);
	@Select("SELECT COUNT(*) FROM sorder o JOIN delivery d ON o.order_no=d.order_no WHERE d_status_num=7 AND mem_num=#{mem_num}")
	public int getCancelCount(int mem_num);

	/*
	 * @Select("SELECT COUNT(*) FROM review WHERE mem_num=#{mem_num}") public int
	 * getReviewCount(int mem_num);
	 */
	@Select("SELECT COUNT(*) FROM qna WHERE mem_num=#{mem_num}")
	public int getQnaCount(int mem_num);
	
	
	//============== 관리자관리 ==============
	@Select("SELECT * FROM member m LEFT OUTER JOIN member_detail d ON m.mem_num=d.mem_num WHERE m.mem_num=#{mem_num} AND mem_auth IN (3,4)")
	public AdminMemberVO selectAdmin(int mem_num);
	
	@Select("SELECT COUNT(*) FROM member m, member_detail d WHERE m.mem_auth IN (3,4) and m.mem_num = d.mem_num")
	public int getAdminCount();
	
	@Select("SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM member m JOIN member_detail d ON m.mem_num=d.mem_num WHERE mem_auth IN (3,4) ORDER BY m.mem_auth DESC, m.mem_num)a) WHERE rnum >= #{start} AND rnum <= #{end}")
	public List<AdminMemberVO> getAdminList(Map<String,Object> map);
	
	@Select("SELECT member_seq.nextval FROM dual")
	public int selectMem_num();
	@Insert("INSERT INTO member VALUES (#{mem_num},#{mem_id},#{mem_auth})")
	public void insertAdmin(AdminMemberVO adminMemberVO);
	@Insert("INSERT INTO member_detail VALUES (#{mem_passwd},#{mem_name},#{mem_phone},#{mem_email},#{mem_zipcode},#{mem_address1},#{mem_address2},SYSDATE,#{mem_num})")
	public void insertAdminDetail(AdminMemberVO adminMemberVO);
	
}
