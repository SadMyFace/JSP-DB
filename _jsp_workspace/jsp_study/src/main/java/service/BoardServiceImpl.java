package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import repository.BoardDAO;
import repository.BoardDAOImpl;

public class BoardServiceImpl implements BoardService {
	
	//로그 기록 객체 생성
	private static final Logger log = 
			LoggerFactory.getLogger(BoardServiceImpl.class);
	private BoardDAO bdao; // interface로 생성
	
	public BoardServiceImpl() {
		bdao = new BoardDAOImpl(); // class로 생성 bdao 구현 객체 생성
	}

	@Override
	public int register(BoardVO bvo) {
		// TODO Auto-generated method stub
		log.info(">>>> insert check 2");
		return bdao.insert(bvo);
	}

	@Override
	public List<BoardVO> getList() {
		// TODO Auto-generated method stub
		log.info(">>>> list check 3");
		return bdao.selectList();
	}

	@Override
	public BoardVO getDetail(int bno) {
		// TODO Auto-generated method stub
		log.info(">>>> detail check 2");
		//detail 체크시 readCount + 1
		int isOk = bdao.readCountUpdate(bno);
		return bdao.getDetail(bno);
	}

	@Override
	public int modify(BoardVO bvo) {
		// TODO Auto-generated method stub
		log.info(">>>> modify check 2");
		return bdao.update(bvo);
	}
}
