package nacos.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class User extends Model<User> {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Date createdAt;

    public User(String name, Date date) {
        this.name = name;
        this.createdAt = date;
    }
}
