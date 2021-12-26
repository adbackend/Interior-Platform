package kr.spring.category_top.vo;

public class Category_topVO {
	private int c_top_no;
	private String c_top_name;
	
	public int getC_top_no() {
		return c_top_no;
	}
	public void setC_top_no(int c_top_no) {
		this.c_top_no = c_top_no;
	}
	public String getC_top_name() {
		return c_top_name;
	}
	public void setC_top_name(String c_top_name) {
		this.c_top_name = c_top_name;
	}
	@Override
	public String toString() {
		return "Category_topVO [c_top_no=" + c_top_no + ", c_top_name=" + c_top_name + "]";
	}
	
	
}
