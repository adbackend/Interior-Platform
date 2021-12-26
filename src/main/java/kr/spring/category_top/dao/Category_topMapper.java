package kr.spring.category_top.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.category_top.vo.Category_topVO;

public interface Category_topMapper {
	@Select("SELECT category_top_seq.nextval FROM dual")
	public int selectC_top_no();
	@Insert("INSERT INTO category_top (c_top_no, c_top_name) VALUES (category_top_seq.nextval, #{c_top_name})")
	public void insertCategory_top(Category_topVO category_top);
	
	@Select("SELECT * FROM category_top ORDER BY c_top_no")
	public List<Category_topVO> selectCategory_top();
	
	@Update("UPDATE category_top SET c_top_name=#{c_top_name} WHERE c_top_no=#{c_top_no}")
	public void updateCategory_top(Category_topVO category_topVO);
	
	@Delete("DELETE FROM category_top WHERE c_top_no=#{c_top_no}")
	public void deleteCategory_top(int c_top_no);
	
	@Select("SELECT * FROM category_top WHERE c_top_no=#{c_top_no}")
	public Category_topVO chooseCategory_top(int c_top_no);
	
	@Select("SELECT * FROM category_top WHERE c_top_no = #{c_top_no}")
	public Category_topVO selectCategoryOne(int c_top_no);
}
