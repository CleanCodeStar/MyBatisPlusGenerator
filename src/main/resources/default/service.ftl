package ${config.servicePackage};

import com.baomidou.mybatisplus.extension.service.IService;
import ${config.entityPackage}.${data.className};

/**
 * ${data.tableRemark}${config.serviceSuffix}
 *
<#if config.author??>
 * @author ${config.author}
</#if>
<#if config.author??>
 * @version ${config.version}
</#if>
 * @date ${config.createTime?string('yyyy-MM-dd hh:mm:ss')}
 */
public interface ${data.className}${config.serviceSuffix} extends IService<${data.className}> {

}