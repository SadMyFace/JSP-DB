package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.PagingVO;
import orm.DatabaseBuilder;

public class BoardDAOImpl implements BoardDAO {
	
	//로그 기록 객체 생성
	private static final Logger log = 
			LoggerFactory.getLogger(BoardDAOImpl.class);
	
	// DB 연결
	private SqlSession sql;
	
	public BoardDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}

	@Override
	public int insert(BoardVO bvo) {
		// TODO Auto-generated method stub
		log.info(">>>> insert check 3");
		// 실제 DB에 저장 => mybatis / mapper
		//sql.insert(mapperNamespace.id로 인식)
		int isOk = sql.insert("BoardMapper.add", bvo);
		//insert, update, delete시 DB가 변경되는 구문은 commit이 필요
		if(isOk > 0 ) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public List<BoardVO> selectList(PagingVO pgvo) {
		// TODO Auto-generated method stub
		log.info(">>>> list check 3");
		return sql.selectList("BoardMapper.list", pgvo);
	}

	@Override
	public BoardVO getDetail(int bno) {
		// TODO Auto-generated method stub
		log.info(">>>> detail check 3");
		return sql.selectOne("BoardMapper.detail", bno);
	}

	@Override
	public int readCountUpdate(int bno) {
		// TODO Auto-generated method stub
		log.info(">>>> detail ReadCount update check");
		int isOk = sql.update("BoardMapper.read", bno);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int update(BoardVO bvo) {
		// TODO Auto-generated method stub
		log.info(">>>> modify check 3");
		int isOk = sql.update("BoardMapper.up", bvo);
		if(isOk > 0) {
			sql.commit();
		}	
		return isOk;
	}

	@Override
	public int remove(int bno) {
		// TODO Auto-generated method stub
		log.info(">>>> remove check 3");
		int isOk = sql.delete("BoardMapper.del", bno);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		log.info(">>> totalCount check 3");
		//count(*)을 하면 db 성능저하가 초래될 수도 있고, 
		//title이 null일 경우 값이 포함되지 않을 수도 있음
		return sql.selectOne("BoardMapper.getCount");
	}
	
}
