package kr.spring.review.vo;

import java.io.IOException;
import java.sql.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

public class ReviewVO {
	private int review_no; //게시판 번호
	@NotEmpty
	private int p_no; //제목
	@NotEmpty
	private String review_content; //내용
	private int order_no; 
	private byte[] review_image; //업로드 파일
	private String review_image_name; //파일
	private int mem_num; //회원 번호
	private int review_rating; 
	
	public int getReview_no() {
		return review_no;
	}
	
	//업로드 파일 처리
	public void setUpload(MultipartFile upload)throws IOException{
		//MultipartFile -> byte[]
		setReview_image(upload.getBytes());
		//파일명 구하기
		setReview_image_name(upload.getOriginalFilename());
	}
	
	public void setReview_no(int review_no) {
		this.review_no = review_no;
	}
	public int getP_no() {
		return p_no;
	}
	public void setP_no(int p_no) {
		this.p_no = p_no;
	}
	public String getReview_content() {
		return review_content;
	}
	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public byte[] getReview_image() {
		return review_image;
	}
	public void setReview_image(byte[] review_image) {
		this.review_image = review_image;
	}
	public String getReview_image_name() {
		return review_image_name;
	}
	public void setReview_image_name(String review_image_name) {
		this.review_image_name = review_image_name;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	
	public int getReview_rating() {
		return review_rating;
	}

	public void setReview_rating(int review_rating) {
		this.review_rating = review_rating;
	}

	@Override
	public String toString() {
		return "ReviewVO [review_no=" + review_no + ", p_no=" + p_no + ", review_content=" + review_content
				+ ", order_no=" + order_no + ", review_image_name=" + review_image_name + ", mem_num=" + mem_num
				+ ", review_rating=" + review_rating + "]";
	}
}
