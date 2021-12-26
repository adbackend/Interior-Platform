package kr.spring.order.vo;

import java.sql.Date;

public class OrderAllVO {
	private String order_no;
	private int mem_num;
	private String order_name;
	private String order_phone;
	private String order_zipcode;
	private String order_address1;
	private String order_address2;
	private int order_method;
	private Date order_date;
	private int order_pay;
	private int delivery_pay;
	private int order_d_no;
	private int p_no;
	private int order_d_amount;
	private int delivery_no;
	private String d_status_name;
	private Date delivery_date;
	private String dcompany_name;
	private int d_status_num;
	private int buycount;
	private String p_name;
	
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public int getBuycount() {
		return buycount;
	}
	public void setBuycount(int buycount) {
		this.buycount = buycount;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getOrder_name() {
		return order_name;
	}
	public void setOrder_name(String order_name) {
		this.order_name = order_name;
	}
	public String getOrder_phone() {
		return order_phone;
	}
	public void setOrder_phone(String order_phone) {
		this.order_phone = order_phone;
	}
	public String getOrder_zipcode() {
		return order_zipcode;
	}
	public void setOrder_zipcode(String order_zipcode) {
		this.order_zipcode = order_zipcode;
	}
	public String getOrder_address1() {
		return order_address1;
	}
	public void setOrder_address1(String order_address1) {
		this.order_address1 = order_address1;
	}
	public String getOrder_address2() {
		return order_address2;
	}
	public void setOrder_address2(String order_address2) {
		this.order_address2 = order_address2;
	}
	public int getOrder_method() {
		return order_method;
	}
	public void setOrder_method(int order_method) {
		this.order_method = order_method;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public int getOrder_pay() {
		return order_pay;
	}
	public void setOrder_pay(int order_pay) {
		this.order_pay = order_pay;
	}
	public int getDelivery_pay() {
		return delivery_pay;
	}
	public void setDelivery_pay(int delivery_pay) {
		this.delivery_pay = delivery_pay;
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
	public int getDelivery_no() {
		return delivery_no;
	}
	public void setDelivery_no(int delivery_no) {
		this.delivery_no = delivery_no;
	}
	public String getD_status_name() {
		return d_status_name;
	}
	public void setD_status_name(String d_status_name) {
		this.d_status_name = d_status_name;
	}
	public Date getDelivery_date() {
		return delivery_date;
	}
	public void setDelivery_date(Date delivery_date) {
		this.delivery_date = delivery_date;
	}
	public String getDcompany_name() {
		return dcompany_name;
	}
	public void setDcompany_name(String dcompany_name) {
		this.dcompany_name = dcompany_name;
	}
	public int getD_status_num() {
		return d_status_num;
	}
	public void setD_status_num(int d_status_num) {
		this.d_status_num = d_status_num;
	}
	@Override
	public String toString() {
		return "OrderAllVO [order_no=" + order_no + ", mem_num=" + mem_num + ", order_name=" + order_name
				+ ", order_phone=" + order_phone + ", order_zipcode=" + order_zipcode + ", order_address1="
				+ order_address1 + ", order_address2=" + order_address2 + ", order_method=" + order_method
				+ ", order_date=" + order_date + ", order_pay=" + order_pay + ", delivery_pay=" + delivery_pay
				+ ", order_d_no=" + order_d_no + ", p_no=" + p_no + ", order_d_amount=" + order_d_amount
				+ ", delivery_no=" + delivery_no + ", d_status_name=" + d_status_name + ", delivery_date="
				+ delivery_date + ", dcompany_name=" + dcompany_name + ", d_status_num=" + d_status_num + "]";
	}
	
}
