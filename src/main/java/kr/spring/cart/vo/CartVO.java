package kr.spring.cart.vo;

public class CartVO {
	private int cart_no;
	private int mem_num;
	private int p_no;
	private int cart_amount;
	private int p_amount;
	
	public int getP_amount() {
		return p_amount;
	}
	public void setP_amount(int p_amount) {
		this.p_amount = p_amount;
	}
	public int getCart_no() {
		return cart_no;
	}
	public void setCart_no(int cart_no) {
		this.cart_no = cart_no;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public int getP_no() {
		return p_no;
	}
	public void setP_no(int p_no) {
		this.p_no = p_no;
	}
	public int getCart_amount() {
		return cart_amount;
	}
	public void setCart_amount(int cart_amount) {
		this.cart_amount = cart_amount;
	}
	@Override
	public String toString() {
		return "CartVO [cart_no=" + cart_no + ", mem_num=" + mem_num + ", p_no=" + p_no + ", cart_amount=" + cart_amount
				+ ", p_amount=" + p_amount + "]";
	}
}
