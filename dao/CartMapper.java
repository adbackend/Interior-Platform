package kr.spring.cart.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.cart.vo.CartVO;
import kr.spring.cart.vo.ProductCartVO;

public interface CartMapper {
	@Insert ("INSERT INTO cart (cart_no,mem_num,p_no,cart_amount) VALUES(cart_seq.nextval, #{mem_num},#{p_no},#{cart_amount})" )
	public void insertCart(CartVO cart);
	@Select("SELECT * FROM CART c, Product p WHERE c.p_no=p.p_no AND mem_num = #{mem_num} ORDER BY cart_no DESC")
	public List<ProductCartVO> selectCart(int mem_num);
	@Delete("DELETE FROM CART WHERE p_no=#{p_no}")
	public void deleteCart(int p_no);
	@Select("SELECT count(*) FROM CART WHERE p_no = #{p_no}")
	public int selectDuplicationCart(int p_no);
	@Select("SELECT cart_amount FROM CART WHERE p_no=#{p_no}")
	public int selectCartProduct(int p_no);
	@Update("UPDATE cart SET cart_amount= #{param1} WHERE p_no=#{param2} AND mem_num=#{param3}")
	public void UpdateDuplicationProduct(int param1,int param2, int param3);
	@Update("UPDATE cart SET cart_amount = #{cart_amount} WHERE p_no=#{p_no} AND mem_num=#{mem_num}")
	public void updateCart(CartVO cart);
	
	
}
