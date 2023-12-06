package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.CommentVO;
import orm.DatabaseBuilder;

public class CommentDAOImpl implements CommentDAO {
	
	private static final Logger log = LoggerFactory.getLogger(CommentDAOImpl.class);
	
	private SqlSession sql;
	private int isOk;
	
	public CommentDAOImpl(){
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}

	@Override
	public int post(CommentVO cvo) {
		// TODO Auto-generated method stub
		log.info(">>> post check 3");
		int isOk = sql.insert("CommentMapper.post", cvo);
		
		if(isOk > 0) {
			sql.commit();
		}
		
		return isOk;
	}

	@Override
	public List<CommentVO> getList(int bno) {
		// TODO Auto-generated method stub
		log.info(">>> list check 3");
		return sql.selectList("CommentMapper.list", bno);
	}

	@Override
	public int remove(int cno) {
		// TODO Auto-generated method stub
		log.info(">>> remove check 3");
		
		int isOk = sql.delete("CommentMapper.remove", cno);
		
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int modify(CommentVO cvo) {
		// TODO Auto-generated method stub
		log.info(">>> modify check 3");
		
		int isOk = sql.update("CommentMapper.modify", cvo);
		
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}

}
