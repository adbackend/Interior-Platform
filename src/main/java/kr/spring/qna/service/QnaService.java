package kr.spring.qna.service;

import java.util.List;
import java.util.Map;

import kr.spring.qna.vo.QnaVO;

public interface QnaService {
	public void insertQna(QnaVO qna);
	public void insertQnaReply(QnaVO qna);
	public QnaVO selectDetailQna(int board_no);
	public List<QnaVO> selectAllQna(Map<String, Object> map);
	public void deleteQna(int board_no);
	public void updateQna(QnaVO qna);
	public int selectCountQna(int board_kind);
	public void insertProductQna(QnaVO qna);
	public int selectRowQnaCount(Map<String,Object> map);
	public List<QnaVO> selectAllProductQna(Map<String, Object> map);
	public int selectAllPrpductQnaCount(Map<String, Object> map);
}
