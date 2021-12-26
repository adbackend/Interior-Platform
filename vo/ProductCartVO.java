package kr.spring.cart.vo;

public class ProductCartVO {
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
		private int cart_no;
		private int mem_num;
		private int cart_amount;
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
		public int getCart_amount() {
			return cart_amount;
		}
		public void setCart_amount(int cart_amount) {
			this.cart_amount = cart_amount;
		}
		
}
