package example;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import javax.sql.DataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

@Factory
public class FactoryDef {

  private final DataSource dataSource;

  public FactoryDef(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Singleton
  public SqlSessionFactory sqlSessionFactory() {
    TransactionFactory transactionFactory = new JdbcTransactionFactory();

    Environment environment = new Environment("dev", transactionFactory, dataSource);
    Configuration configuration = new Configuration(environment);
    configuration.addMapper(TestMapper.class);

    return new SqlSessionFactoryBuilder().build(configuration);
  }
}
