package kr.spring.orderDetail.vo;

public class OrderDetailVO {
	private String order_no;
	private int order_d_no;
	private int p_no;
	private int order_d_amount;
	private int mem_num;
	
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public int getOrder_d_no() {
		return order_d_no;
	}
	public void setOrder_d_no(int order_d_no) {
		this.order_d_no = order_d_no;
	}
	public int getP_no() {
		return p_no;
	}
	public void setP_no(int p_no) {
		this.p_no = p_no;
	}
	public int getOrder_d_amount() {
		return order_d_amount;
	}
	public void setOrder_d_amount(int order_d_amount) {
		this.order_d_amount = order_d_amount;
	}
	@Override
	public String toString() {
		return "OrderDetailVO [order_no=" + order_no + ", order_d_no=" + order_d_no + ", p_no=" + p_no
				+ ", order_d_amount=" + order_d_amount + ", mem_num=" + mem_num + "]";
	}
}
