package ${config.mapperPackage};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${config.entityPackage}.${data.className}${config.entitySuffix};

/**
* ${data.tableRemark}${config.mapperSuffix}
*
<#if config.author??>
* @author ${config.author}
</#if>
<#if config.author??>
* @version ${config.version}
</#if>
* @date ${config.createTime?string('yyyy-MM-dd hh:mm:ss')}
*/
public interface ${data.className}${config.mapperSuffix} extends BaseMapper<${data.className}${config.entitySuffix}> {

}