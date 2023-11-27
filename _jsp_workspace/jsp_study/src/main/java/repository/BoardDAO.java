package repository;

import java.util.List;

import domain.BoardVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

	List<BoardVO> selectList();

	BoardVO getDetail(int bno);

	int readCountUpdate(int bno);

	int update(BoardVO bvo);

	int remove(int bno);

}
