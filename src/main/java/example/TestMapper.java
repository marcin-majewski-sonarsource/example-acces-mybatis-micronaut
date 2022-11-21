package example;

import jakarta.inject.Singleton;
import java.util.List;
import org.apache.ibatis.annotations.Select;

@Singleton
public interface TestMapper {

  @Select("select id, kee from projects")
  List<TestDto> selectAll();
}
