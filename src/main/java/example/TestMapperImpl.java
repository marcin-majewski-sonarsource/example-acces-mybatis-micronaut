package example;

import jakarta.inject.Singleton;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

@Singleton
public class TestMapperImpl implements TestMapper {

  private final SqlSessionFactory sessionFactory;

  public TestMapperImpl(SqlSessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public List<TestDto> selectAll() {
    try (SqlSession sqlSession = sessionFactory.openSession()) {
      return getMapper(sqlSession).selectAll();
    }
  }

  private TestMapper getMapper(SqlSession sqlSession) {
    return sqlSession.getMapper(TestMapper.class);
  }
}
