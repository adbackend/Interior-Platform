package kr.spring.adminRevenue.vo;

import java.sql.Date;

/**
  * @FileName : RevenueVO.java
  * @Date : 2021. 10. 15. 
  * @Author : 최유정
  * @Description : 매출관리 db
  */
public class RevenueVO {

	private Date order_date; // 주문날짜
	private int order_pay; // 주문 총가격
	private int order_d_amount; // 수량
	private int order_d_price; // 가격
	private String p_name; // 상품명
	private String c_top_name; // 상위 카테고리 이름

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

	public int getOrder_d_amount() {
		return order_d_amount;
	}

	public void setOrder_d_amount(int order_d_amount) {
		this.order_d_amount = order_d_amount;
	}

	public int getOrder_d_price() {
		return order_d_price;
	}

	public void setOrder_d_price(int order_d_price) {
		this.order_d_price = order_d_price;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public String getC_top_name() {
		return c_top_name;
	}

	public void setC_top_name(String c_top_name) {
		this.c_top_name = c_top_name;
	}

	@Override
	public String toString() {
		return "RevenueVO [order_date=" + order_date + ", order_pay=" + order_pay + ", order_d_amount=" + order_d_amount
				+ ", order_d_price=" + order_d_price + ", p_name=" + p_name + ", c_top_name=" + c_top_name + "]";
	}

}
