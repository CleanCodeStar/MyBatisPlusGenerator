package ${config.entityPackage};

<#if config.enableApi??>
import com.citrsw.annatation.ApiModel;
import com.citrsw.annatation.ApiProperty;
</#if>
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
<#if config.enableApi??>
@ApiModel("${data.tableRemark}")
</#if>
public class ${data.className}${config.entitySuffix} implements Serializable {

    private static final long serialVersionUID = 1L;
    <#list data.allFieldDefinitions as fieldDefinition>

    /**
     * ${fieldDefinition.columnRemark}
     */
    <#if config.enableApi??>
    @ApiProperty(description = "${fieldDefinition.columnRemark}")
    </#if>
    private ${fieldDefinition.javaFieldClass.simpleName} ${fieldDefinition.javaFieldName};
    </#list>
}