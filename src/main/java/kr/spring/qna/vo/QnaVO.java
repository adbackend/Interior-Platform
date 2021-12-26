package kr.spring.qna.vo;

import java.sql.Date;

public class QnaVO {
	private int level;
	private int board_no;
	private int grpno;
	private String board_title;
	private String board_content;
	private Date board_date;
	private int mem_num;
	private int board_kind;
	private int p_no;
	private String p_name;
	private String mem_id;	//join
	private String mem_name; //join
	private int board_parent;
	private int p_price;
	
	
	
	
	public int getP_price() {
		return p_price;
	}
	public void setP_price(int p_price) {
		this.p_price = p_price;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public int getBoard_parent() {
		return board_parent;
	}
	public void setBoard_parent(int board_parent) {
		this.board_parent = board_parent;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getBoard_no() {
		return board_no;
	}
	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}
	public int getGrpno() {
		return grpno;
	}
	public void setGrpno(int grpno) {
		this.grpno = grpno;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public Date getBoard_date() {
		return board_date;
	}
	public void setBoard_date(Date board_date) {
		this.board_date = board_date;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public int getBoard_kind() {
		return board_kind;
	}
	public void setBoard_kind(int board_kind) {
		this.board_kind = board_kind;
	}
	public int getP_no() {
		return p_no;
	}
	public void setP_no(int p_no) {
		this.p_no = p_no;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	@Override
	public String toString() {
		return "QnaVO [level=" + level + ", board_no=" + board_no + ", grpno=" + grpno + ", board_title=" + board_title
				+ ", board_content=" + board_content + ", board_date=" + board_date + ", mem_num=" + mem_num
				+ ", board_kind=" + board_kind + ", p_no=" + p_no + ", mem_id=" + mem_id + ", mem_name=" + mem_name
				+ ", board_parent=" + board_parent + "]";
	}
	
	
}
