package kr.spring.category_top.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.category_top.dao.Category_topMapper;
import kr.spring.category_top.vo.Category_topVO;

@Service
@Transactional
public class Category_topServiceImpl implements Category_topService{

	@Autowired
	private Category_topMapper category_topMapper;
	
	@Override
	public void insertCategory_top(Category_topVO category_top) {
		category_topMapper.insertCategory_top(category_top);
	}

	@Override
	public List<Category_topVO> selectCategory_top() {
		return category_topMapper.selectCategory_top();
	}
	
	@Override
	public void updateCategory_top(Category_topVO category_topVO) {
		category_topMapper.updateCategory_top(category_topVO);
	}
	
	@Override
	public void deleteCategory_top(int c_top_no) {
		category_topMapper.deleteCategory_top(c_top_no);
	}

	@Override
	public Category_topVO selectCategory_top(int c_top_no) {
		return category_topMapper.chooseCategory_top(c_top_no);
	}

	@Override
	public Category_topVO selectCategoryOne(int c_top_no) {
		return category_topMapper.selectCategoryOne(c_top_no);
	}
	
}
