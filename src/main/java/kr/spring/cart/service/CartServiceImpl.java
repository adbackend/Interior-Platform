package kr.spring.cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.cart.dao.CartMapper;
import kr.spring.cart.vo.CartVO;
import kr.spring.cart.vo.ProductCartVO;

@Service
@Transactional
public class CartServiceImpl implements CartService {
	
	@Autowired
	CartMapper cartMapper;

	@Override
	public void insertCart(CartVO cart) {
		cartMapper.insertCart(cart);
	}

	@Override
	public List<ProductCartVO> selectCart(int mem_num) {
		return cartMapper.selectCart(mem_num);
	}

	@Override
	public void deleteCart(int p_no) {
		cartMapper.deleteCart(p_no);
		
	}

	@Override
	public int selectDuplicationCart(int p_no, int mem_num) {
		return cartMapper.selectDuplicationCart(p_no, mem_num);
	}

	@Override
	public int selectCartProduct(int p_no, int mem_num) {
		return cartMapper.selectCartProduct(p_no, mem_num);
	}

	@Override
	public void UpdateDuplicationProduct(int p_no, int cart_amount, int mem_num) {
		cartMapper.UpdateDuplicationProduct(p_no,cart_amount,mem_num);
		
	}

	@Override
	public void updateCart(CartVO cart) {
		cartMapper.updateCart(cart);
		
	}

	@Override
	public void deleteAllCart(int mem_num) {
		cartMapper.deleteAllCart(mem_num);
	}

	@Override
	public int countCartAll(int mem_num) {
		return cartMapper.countCartAll(mem_num);
	}

	/*
	 * @Override public ProductCartVO selectOrderOneProduct(String order_no) {
	 * return cartMapper.selectOrderOneProduct(order_no); }
	 */
	

	
}
