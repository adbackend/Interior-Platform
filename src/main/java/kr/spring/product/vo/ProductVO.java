package kr.spring.product.vo;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public class ProductVO {
	private int p_no;
	private String p_name;
	private int p_price;
	private int p_amount;
	private String p_image_name;
	private byte[] p_image;
	private int p_discount;
	private String p_sub_text;
	private int c_top_no;
	private int c_sub_no;
	
	//========이미지 BLOB 처리 ===========//
	public void setUpload(MultipartFile upload)throws IOException{
		//MultipartFile -> byte[]
		setP_image(upload.getBytes());
		//파일 이름
		setP_image_name(upload.getOriginalFilename());
	}
	
	public int getP_no() {
		return p_no;
	}
	public void setP_no(int p_no) {
		this.p_no = p_no;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public int getP_price() {
		return p_price;
	}
	public void setP_price(int p_price) {
		this.p_price = p_price;
	}
	public int getP_amount() {
		return p_amount;
	}
	public void setP_amount(int p_amount) {
		this.p_amount = p_amount;
	}
	public String getP_image_name() {
		return p_image_name;
	}
	public void setP_image_name(String p_image_name) {
		this.p_image_name = p_image_name;
	}
	public byte[] getP_image() {
		return p_image;
	}
	public void setP_image(byte[] p_image) {
		this.p_image = p_image;
	}
	public int getP_discount() {
		return p_discount;
	}
	public void setP_discount(int p_discount) {
		this.p_discount = p_discount;
	}
	public String getP_sub_text() {
		return p_sub_text;
	}
	public void setP_sub_text(String p_sub_text) {
		this.p_sub_text = p_sub_text;
	}
	public int getC_top_no() {
		return c_top_no;
	}
	public void setC_top_no(int c_top_no) {
		this.c_top_no = c_top_no;
	}
	public int getC_sub_no() {
		return c_sub_no;
	}
	public void setC_sub_no(int c_sub_no) {
		this.c_sub_no = c_sub_no;
	}

	@Override
	public String toString() {
		return "ProductVO [p_no=" + p_no + ", p_name=" + p_name + ", p_price=" + p_price + ", p_amount=" + p_amount
				+ ", p_image_name=" + p_image_name + ", p_discount=" + p_discount + ", p_sub_text=" + p_sub_text
				+ ", c_top_no=" + c_top_no + ", c_sub_no=" + c_sub_no + "]";
	}
	
}
