package repository;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
}
