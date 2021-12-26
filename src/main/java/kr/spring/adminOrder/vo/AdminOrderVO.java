package kr.spring.adminOrder.vo;

import java.sql.Date;

public class AdminOrderVO {
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
	private String mem_id; //JOIN - member
	private String d_status_name; //JOIN - delivery_status
	
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
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getD_status_name() {
		return d_status_name;
	}
	public void setD_status_name(String d_status_name) {
		this.d_status_name = d_status_name;
	}
	
	@Override
	public String toString() {
		return "AdminOrderVO [order_no=" + order_no + ", mem_num=" + mem_num + ", order_name=" + order_name
				+ ", order_phone=" + order_phone + ", order_zipcode=" + order_zipcode + ", order_address1="
				+ order_address1 + ", order_address2=" + order_address2 + ", order_method=" + order_method
				+ ", order_date=" + order_date + ", order_pay=" + order_pay + ", delivery_pay=" + delivery_pay
				+ ", mem_id=" + mem_id + ", d_status_name=" + d_status_name + "]";
	}
	
}