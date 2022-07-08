package nacos.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import nacos.demo.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
