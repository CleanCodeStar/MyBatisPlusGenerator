package ${config.entityPackage};

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
<#list data.imports as import>
import ${import};
</#list>

import java.io.Serializable;


/**
* ${data.tableRemark}
*
<#if config.author??>
* @author ${config.author}
</#if>
<#if config.author??>
* @version ${config.version}
</#if>
* @date ${config.createTime?string('yyyy-MM-dd hh:mm:ss')}
*/
@TableName("${data.tableName}")
@Accessors(chain = true)
@Data
public class ${data.className}${config.entitySuffix} implements Serializable {

    private static final long serialVersionUID = 1L;
    <#list data.allFieldDefinitions as fieldDefinition>

    /**
    * ${fieldDefinition.columnRemark}
    */
    private ${fieldDefinition.javaFieldClass.simpleName} ${fieldDefinition.javaFieldName};
    </#list>
}