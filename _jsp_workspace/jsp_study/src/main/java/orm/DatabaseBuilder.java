package orm;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DatabaseBuilder {
	
	private static SqlSessionFactory factory;
	private static final String CONFIG = "orm/MybatisConfig.xml";
	
	//초기화 블럭을 사용하여 객체 생성
	static {
		try {
			factory = new SqlSessionFactoryBuilder()
					.build(Resources.getResourceAsReader(CONFIG));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static SqlSessionFactory getFactory() {
		return factory;
	}
}
