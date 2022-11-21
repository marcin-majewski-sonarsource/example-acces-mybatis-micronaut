package example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import javax.sql.DataSource;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

@Factory
public class FactoryDef {

  @Singleton
  public DataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName("org.postgresql.Driver");
    hikariConfig.setUsername("<username>");
    hikariConfig.setPassword("<password>");
    hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/<db>");
    return new HikariDataSource(hikariConfig);
  }

  @Singleton
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) {
    TransactionFactory transactionFactory = new JdbcTransactionFactory();

    Environment environment = new Environment("dev", transactionFactory, dataSource);
    Configuration configuration = new Configuration(environment);
    configuration.addMapper(TestMapper.class);

    return new SqlSessionFactoryBuilder().build(configuration);
  }
}
