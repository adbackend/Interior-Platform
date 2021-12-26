package kr.spring.product.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.product.vo.ProductVO;

public interface ProductMapper {
	@Select("SELECT * FROM product")
	public List<ProductVO> ProductSelectAll();
	@Select("SELECT COUNT(*) FROM product")
	public int selectRowCount(Map<String, Object> map);
	@Select("Select * FROM product WHERE p_no=#{p_no}")
	public ProductVO ProductSelect(int p_no);
	@Select("select * from product where c_top_no = #{param1} AND c_sub_no = #{param2} order by decode(p_amount,0,1)DESC")
	public List<ProductVO> ProductCategorySelectAll(int param1, int param2);
	@Select("select count(*) from product where c_top_no = #{param1} AND c_sub_no = #{param2}")
	public int ProductCategorySelectCount(int param1, int param2);
	@Update("update product set p_amount=p_amount-#{param1} where p_no=#{param2}")
	public void productAmountUpdate(int param1, int param2);
	@Update("update product set p_amount=p_amount+#{param1} where p_no=#{param2}")
	public void productAmountPlusUpdate(int param1, int param2);
	@Delete("DELETE FROM product WHERE p_no=#{p_no}")
	public void deleteProduct(int p_no);
	@Insert("INSERT INTO product (p_no, p_name, p_price, p_amount, p_image_name, p_image, p_discount, p_sub_text, c_top_no, c_sub_no) "
			+ "VALUES (product_seq.nextval, #{p_name}, #{p_price}, #{p_amount}, #{p_image_name}, #{p_image}, #{p_discount}, #{p_sub_text}, #{c_top_no}, #{c_sub_no})")
	public void insertProduct(ProductVO product);
	public void updateProduct(ProductVO productVO);
	@Select("select count(*) from product where p_no = #{p_no}")
	public int countProduct(int p_no);
	public List<ProductVO> selectSearchProduct(Map<String, Object> map);
	public int selectCountSearchProduct(Map<String, Object> map);
	@Select("select count(*) from product")
	public int selectMainProduct();
	
	@Select("select * from product p where p.c_top_no=#{param1} and p.c_sub_no=#{param2} order by (p.p_price-p.p_price*p.p_discount/100) desc")
	public List<ProductVO> selectPriceHigh(int param1, int param2);
	
	@Select("select * from product p where p.c_top_no=#{param1} and p.c_sub_no=#{param2} order by (p.p_price-p.p_price*p.p_discount/100)")
	public  List<ProductVO> selectPriceRow(int param1, int param2);

	@Select("SELECT p.p_name, p.p_no, p.p_amount, p.p_discount,p.p_image_name,p.p_image,p.p_price,nvl(b.cnt,0) as p_snumber "
			+ "FROM product p LEFT OUTER JOIN (SELECT p_no, COUNT(*) AS CNT FROM sorder_detail GROUP BY p_no)b "
			+ "ON p.p_no = b.p_no WHERE p.c_top_no=#{param1} and p.c_sub_no=#{param2} ORDER BY p.p_no")
	public  List<ProductVO> selectPriceBest(int param1, int param2);
}

