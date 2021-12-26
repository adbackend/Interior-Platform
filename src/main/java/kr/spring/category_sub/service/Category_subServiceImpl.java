package kr.spring.category_sub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.category_sub.dao.Category_subMapper;
import kr.spring.category_sub.vo.Category_subVO;

@Service
@Transactional
public class Category_subServiceImpl implements Category_subService{
	
	@Autowired
	private Category_subMapper category_subMapper;
	
	@Override
	public void insertCategory_sub(Category_subVO category_sub) {
		//category_sub.setC_sub_no(category_subMapper.selectC_sub_no());
		category_subMapper.insertCategory_sub(category_sub);
	}

	@Override
	public List<Category_subVO> selectCategory_sub() {
		return category_subMapper.selectCategory_sub();
	}

	@Override
	public void updateCategory_sub(Category_subVO category_sub) {
		category_subMapper.updateCategory_sub(category_sub);
	}

	@Override
	public void deleteCategory_sub(int c_sub_no) {
		category_subMapper.deleteCategory_sub(c_sub_no);
	}

	@Override
	public List<Category_subVO> category_subSelectAll() {
		return category_subMapper.category_subSelectAll();
	}

	@Override
	public List<Category_subVO> category_subWanted(int c_top_no) {
		return category_subMapper.category_subWanted(c_top_no);
	}

	@Override
	public Category_subVO category_subWanted() {
		return category_subMapper.chooseCategory_top(0);
	}

	@Override
	public Category_subVO category_subWant(int c_top_no) {
		return category_subMapper.chooseCategory_top(c_top_no);
	}

	@Override
	public Category_subVO selectCategoryOne(int c_sub_no) {
		return category_subMapper.selectCategoryOne(c_sub_no);
	}
	
}
