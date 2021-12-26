package kr.spring.qna.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.qna.dao.QnaMapper;
import kr.spring.qna.vo.QnaVO;

@Service
@Transactional
public class QnaServiceImpl implements QnaService {
	
	@Autowired
	private QnaMapper qnaMapper;

	//글작성
	@Override
	public void insertQna(QnaVO qna) {
		//시퀀스 셋팅
		qna.setBoard_no(qnaMapper.selectQna_seq());
		qna.setGrpno(qnaMapper.selectQna_seq());
		qnaMapper.insertQna(qna);
	}
	
	//답글작성
	@Override
	public void insertQnaReply(QnaVO qna) {
		qna.setBoard_no(qnaMapper.selectQna_seq());
		qnaMapper.insertQna(qna);
	}

	//글 전체목록
	@Override
	public List<QnaVO> selectAllQna(Map<String, Object> map) {
		return qnaMapper.selectAllQna(map);
	}
	
	//글 상세목록
	@Override
	public QnaVO selectDetailQna(int board_no) {
		return qnaMapper.selectDetailQna(board_no);
	}

	@Override
	public void deleteQna(int board_no) {
		qnaMapper.deleteQnaReply(board_no);
		qnaMapper.deleteQna(board_no);
		
	}

	@Override
	public void updateQna(QnaVO qna) {
		qnaMapper.updateQna(qna);
		
	}

	@Override
	public int selectCountQna(int board_kind) {
		return qnaMapper.selectCountQna(board_kind);
	}

	@Override
	public void insertProductQna(QnaVO qna) {
		qna.setBoard_no(qnaMapper.selectQna_seq());
		qna.setGrpno(qnaMapper.selectQna_seq());
		qnaMapper.insertProductQna(qna);
		
	}

	@Override
	public int selectRowQnaCount(Map<String, Object> map) {
		return qnaMapper.selectRowQnaCount(map);
	}

	@Override
	public List<QnaVO> selectAllProductQna(Map<String, Object> map) {
		return qnaMapper.selectAllProductQna(map);
	}

	@Override
	public int selectAllPrpductQnaCount(Map<String, Object> map) {
		return qnaMapper.selectAllPrpductQnaCount(map);
	}
}
