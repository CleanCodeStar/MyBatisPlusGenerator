package citrsw.core.definition;

import citrsw.core.common.CoreUtils;
import citrsw.core.enumeration.TableType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * 表/视图定义
 * <br/>
 * Created in 2019-09-02 22:28
 *
 * @author Zhenfeng Li
 */
@Data
public class TableDefinition implements Serializable {
    private static final long serialVersionUID = -5566892087844989774L;
    /**
     * Schema
     */
    private String schema;

    /**
     * 表名
     */
    private String tableName;

    private String tableMappingName;
    /**
     * 表描述
     */
    private String tableRemark = "";
    /**
     * 类型[表/视图]
     */
    private TableType tableType;

    /**
     * 主键字段集合 (目的：方便的取到所有主键字段)
     */
    private List<ColumnDefinition> primaryKeyColumnDefinitions = new ArrayList<>();

    /**
     * java类名
     */
    private String javaName;

    /**
     * java类名
     */
    private String objectName;

    /**
     * 非主键字段集合
     */
    private List<ColumnDefinition> unPrimaryKeyColumnDefinitions = new ArrayList<>();

    /**
     * 返回java类名
     *
     * @param titleCase 首字母是否大写
     */
    public String getJavaClassName(boolean titleCase) {
        return getJavaClassName(null, null, titleCase);
    }

    /**
     * 返回java类名
     *
     * @param prefix    前缀
     * @param suffix    后缀
     * @param titleCase 首字母是否大写
     */
    public String getJavaClassName(String prefix, String suffix, boolean titleCase) {
        String returnName = Objects.isNull(javaName) ? tableName : javaName;
        //首字母小写驼峰命名
        returnName = CoreUtils.getLowerCaseFirstName(returnName);
        if (StringUtils.isNotEmpty(prefix)) {
            returnName = prefix + "_" + returnName;
        }
        if (StringUtils.isNotEmpty(suffix)) {
            returnName = returnName + "_" + suffix;
        }
        if (titleCase) {
            //首字母大写
            returnName = CoreUtils.toUpperFirstCase(returnName);
        }
        return returnName;
    }

    public String getClassName() {
        return getJavaClassName(true);
    }

    public String getObjectName() {
        return getJavaClassName(false);
    }

    /**
     * 返回所有字段
     */
    public List<ColumnDefinition> getAllFieldDefinitions() {
        List<ColumnDefinition> columnDefinitions = new ArrayList<>(primaryKeyColumnDefinitions);
        columnDefinitions.addAll(unPrimaryKeyColumnDefinitions);
        return columnDefinitions;
    }

    /**
     * 字段中需要导入的类型
     */
    public Set<String> getImports() {
        Set<String> imports = new HashSet<>();
        getAllFieldDefinitions().parallelStream().forEach(columnDefinition -> {
            String name = columnDefinition.getJavaFieldClass().getName();
            if (!name.startsWith("java.lang.")) {
                imports.add(name);
            }
        });
        return imports;
    }
}
