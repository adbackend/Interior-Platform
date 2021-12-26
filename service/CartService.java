package kr.spring.cart.service;

import java.util.List;

import kr.spring.cart.vo.CartVO;
import kr.spring.cart.vo.ProductCartVO;

public interface CartService {

	public void insertCart(CartVO cart);
	public List<ProductCartVO> selectCart(int mem_num);
	public void deleteCart(int p_no);
	public int selectDuplicationCart(int p_no);
	public int selectCartProduct(int p_no);
	public void UpdateDuplicationProduct(int p_no,int cart_amount,int mem_num);
	public void updateCart(CartVO cart);
}
