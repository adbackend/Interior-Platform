package kr.spring.login.vo;

import java.sql.Date;

public class LoginVO {
	
	private int mem_num; //시퀀스
	private String mem_id; //아이디
	private int mem_auth; // 0:탈퇴회원 1:정지회원 2:일반회원 3:일반관리자 4:최고관리자
	
	private String mem_passwd; //비밀번호
	private String mem_name; //이름
	private String mem_phone; //휴대폰
	private String mem_email; //이메일
	private String mem_zipcode; //우편번호
	private String mem_address1; //주소
	private String mem_address2; //상세주소
	private Date mem_date; //가입일
	
	
	//로그인시 비밀번호 일치여부 체크
	public boolean isCheckPassword(String userPasswd) {
		
		if(mem_auth>1 && mem_passwd.equals(userPasswd)) {
			return true;
		}
		
		return false;
	}
	
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public int getMem_auth() {
		return mem_auth;
	}
	public void setMem_auth(int mem_auth) {
		this.mem_auth = mem_auth;
	}
	public String getMem_passwd() {
		return mem_passwd;
	}
	public void setMem_passwd(String mem_passwd) {
		this.mem_passwd = mem_passwd;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMem_phone() {
		return mem_phone;
	}
	public void setMem_phone(String mem_phone) {
		this.mem_phone = mem_phone;
	}
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public String getMem_zipcode() {
		return mem_zipcode;
	}
	public void setMem_zipcode(String mem_zipcode) {
		this.mem_zipcode = mem_zipcode;
	}
	public String getMem_address1() {
		return mem_address1;
	}
	public void setMem_address1(String mem_address1) {
		this.mem_address1 = mem_address1;
	}
	public String getMem_address2() {
		return mem_address2;
	}
	public void setMem_address2(String mem_address2) {
		this.mem_address2 = mem_address2;
	}
	public Date getMem_date() {
		return mem_date;
	}
	public void setMem_date(Date mem_date) {
		this.mem_date = mem_date;
	}

	@Override
	public String toString() {
		return "LoginVO [mem_num=" + mem_num + ", mem_id=" + mem_id + ", mem_auth=" + mem_auth + ", mem_passwd="
				+ mem_passwd + ", mem_name=" + mem_name + ", mem_phone=" + mem_phone + ", mem_email=" + mem_email
				+ ", mem_zipcode=" + mem_zipcode + ", mem_address1=" + mem_address1 + ", mem_address2=" + mem_address2
				+ ", mem_date=" + mem_date + "]";
	}
	
	
	
	

}
