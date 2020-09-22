package citrsw.core.database;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * 数据库配置
 * <br/>
 * Created in 2019-09-03 12:17
 *
 * @author Zhenfeng Li
 */
@Data
@Accessors(chain = true)
public class DataSourceConfig implements Serializable {
    private static final long serialVersionUID = -9025135621743856859L;
    /**
     * 数据库名称
     */
    private String sourceName;
    /**
     * 数据库类型
     */
    private String sourceType = "mysql";
    /**
     * 数据库连接
     */
    private String sourceUrl;
    /**
     * 数据库用户名
     */
    private String sourceUser;
    /**
     * 数据库密码
     */
    private String sourcePassword;
    /**
     * 数据库驱动
     */
    private String sourceDriver;
    /**
     * 数据库Schema
     */
    private String sourceSchema;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataSourceConfig that = (DataSourceConfig) o;

        return new EqualsBuilder()
                .append(sourceType, that.sourceType)
                .append(sourceUrl, that.sourceUrl)
                .append(sourceUser, that.sourceUser)
                .append(sourcePassword, that.sourcePassword)
                .append(sourceDriver, that.sourceDriver)
                .append(sourceSchema, that.sourceSchema)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(sourceType)
                .append(sourceUrl)
                .append(sourceUser)
                .append(sourcePassword)
                .append(sourceDriver)
                .append(sourceSchema)
                .toHashCode();
    }
}
