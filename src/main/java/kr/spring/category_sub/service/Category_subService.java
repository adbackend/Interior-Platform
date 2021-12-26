package kr.spring.category_sub.service;

import java.util.List;

import kr.spring.category_sub.vo.Category_subVO;

public interface Category_subService {
	public void insertCategory_sub(Category_subVO category_sub);
	
	public List<Category_subVO> selectCategory_sub();
	
	public void updateCategory_sub(Category_subVO category_sub);
	public void deleteCategory_sub(int c_sub_no);
	
	public List<Category_subVO> category_subSelectAll();
	
	public List<Category_subVO> category_subWanted(int c_top_no);
	
	public Category_subVO category_subWant(int c_top_no);

	public Category_subVO category_subWanted();
	
	public Category_subVO selectCategoryOne(int c_sub_no);
}
