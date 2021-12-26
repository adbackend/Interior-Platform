package kr.spring.qna.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.qna.vo.QnaVO;

public interface QnaMapper {
	//시퀀스 가져오기
	@Select("SELECT qna_seq.nextval FROM dual")
	public int selectQna_seq();
	
	//글작성
	@Insert("insert into qna(board_no,grpno,board_title,board_content,mem_num,board_kind,board_parent) values(#{board_no},#{grpno}, #{board_title},#{board_content},#{mem_num},#{board_kind},#{board_parent})")
	public void insertQna(QnaVO qna);
	
	//문의글목록
	public List<QnaVO> selectAllQna(Map<String, Object> map);
	
	//글 상세
	@Select("SELECT * FROM member m, qna q left join  product p on q.p_no = p.p_no where board_no = #{board_no} and q.mem_num = m.mem_num ")
	public QnaVO selectDetailQna(int board_no);
	
	//글삭제
	@Delete("DELETE FROM qna WHERE board_no = #{board_no}")
	public void deleteQna(int board_no);
	
	//글삭제시, 답변글도 같이 삭제
	@Delete("DELETE FROM qna WHERE board_parent = #{board_parent}")
	public void deleteQnaReply(int board_parent);
	
	//글수정
	@Update("UPDATE qna set board_content=#{board_content} where board_no=#{board_no}")
	public void updateQna(QnaVO qnavo);
	
	//글 갯수
	@Select("select count(*) from qna where board_kind = #{board_kind}")
	public int selectCountQna(int board_kind);
	
	public int selectRowQnaCount(Map<String,Object> map);
	
	@Insert("insert into qna(board_no,grpno,board_title,board_content,mem_num,board_kind,board_parent,p_no) "
			+ "values(#{board_no},#{grpno}, #{board_title},#{board_content},#{mem_num},#{board_kind},#{board_parent},#{p_no})")
	public void insertProductQna(QnaVO qna);
	
	//특정상품 문의글목록
	public List<QnaVO> selectAllProductQna(Map<String, Object> map);
	
	//특정상품 문의글 갯수
	public int selectAllPrpductQnaCount(Map<String, Object> map);
	
	

}
