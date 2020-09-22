package citrsw.core.definition;

import citrsw.core.common.CoreUtils;
import citrsw.core.common.TypeConversion;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 字段定义
 * <br/>
 * Created in 2019-09-02 22:29
 *
 * @author Zhenfeng Li
 */
@Data
public class ColumnDefinition implements Serializable {
    private static final long serialVersionUID = 3211805164021901088L;

    /**
     * 字段名
     */
    private String columnName;

    /**
     * 字段描述
     */
    private String columnRemark;
    /**
     * 是否是主键
     */
    private Boolean isPk;

    /**
     * 字段数据库类型
     */
    private String dbType;

    /**
     * 是否自动递增
     */
    private Boolean autoIncrement;

    /**
     * 返回java字段名 默认小写
     */
    public String getJavaFieldName() {
        return getJavaFieldName(null, null, false);
    }

    /**
     * 返回java字段名
     *
     * @param titleCase 首字母是否大写
     */
    public String getJavaFieldName(boolean titleCase) {
        return getJavaFieldName(null, null, titleCase);
    }

    /**
     * 返回java字段名
     *
     * @param prefix 前缀
     * @param suffix 后缀
     */
    public String getJavaFieldName(String prefix, String suffix, boolean titleCase) {
        String returnName = columnName;
        //首字母小写驼峰命名
        returnName = CoreUtils.getLowerCaseFirstName(returnName);
        if (StringUtils.isNotEmpty(prefix)) {
            returnName = prefix + "_" + returnName;
        }
        if (StringUtils.isNotEmpty(suffix)) {
            returnName = returnName + "_" + suffix;
        }
        if (titleCase) {
            return CoreUtils.toUpperFirstCase(returnName);
        }
        return returnName;
    }

    /**
     * 返回java字段类型
     */
    public Class<?> getJavaFieldClass() {
        return TypeConversion.getInstance().getJavaClass(this);
    }

    public String getDbTypeName() {
        if(getJavaFieldClass() == Integer.class){
            return "INTEGER";
        }
        if(getJavaFieldClass() == LocalDateTime.class){
            return "TIMESTAMP";
        }
        if(getJavaFieldClass() == LocalDate.class){
            return "DATE";
        }
        if(getJavaFieldClass() == LocalTime.class){
            return "TIME";
        }
        if(getJavaFieldClass() == String.class){
            return "VARCHAR";
        }
        return dbType;
    }
}
