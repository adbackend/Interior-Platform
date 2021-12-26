package kr.spring.category_sub.vo;

public class Category_subVO {
	private int c_sub_no;
	private String c_sub_name;
	private int c_top_no;
	
	public int getC_sub_no() {
		return c_sub_no;
	}
	public void setC_sub_no(int c_sub_no) {
		this.c_sub_no = c_sub_no;
	}
	public String getC_sub_name() {
		return c_sub_name;
	}
	public void setC_sub_name(String c_sub_name) {
		this.c_sub_name = c_sub_name;
	}
	public int getC_top_no() {
		return c_top_no;
	}
	public void setC_top_no(int c_top_no) {
		this.c_top_no = c_top_no;
	}
	@Override
	public String toString() {
		return "Category_subVO [c_sub_no=" + c_sub_no + ", c_sub_name=" + c_sub_name + ", c_top_no=" + c_top_no + "]";
	}
	
	
}
