package ${config.controllerPackage};

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${config.entityPackage}.${data.className}${config.entitySuffix};
import ${config.servicePackage}.${data.className}Service;
import com.citrsw.apiexample.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
<@if config.enableApi??>
import com.citrsw.annatation.*;
</@if>

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
@RestController
@Slf4j
<@if config.enableApi??>
@ApiClass(value = "${data.tableRemark}")
</@if>
public class ${data.className}${config.controllerSuffix} {

    private final ${data.className}${config.serviceSuffix} ${data.objectName}${config.serviceSuffix};

    public ${data.className}${config.controllerSuffix}(${data.className}${config.serviceSuffix} ${data.objectName}${config.serviceSuffix}) {
        this.${data.objectName}${config.serviceSuffix} = ${data.objectName}${config.serviceSuffix};
    }

    /**
    * 保存
    */
    <@if config.enableApi??>
    @ApiMethod("保存")
    </@if>
    @PostMapping("/${data.tableMappingName}")
    public Result<Boolean> save(@RequestBody ${data.className}${config.entitySuffix} ${data.objectName}${config.entitySuffix}, HttpSession session) {
        boolean save = ${data.objectName}${config.serviceSuffix}.save(${data.objectName}${config.entitySuffix});
        return Result.buildSaveOk(save);
    }

    /**
    * 根据Id修改
    */
    <@if config.enableApi??>
    @ApiMethod("根据Id修改")
    </@if>
    @PutMapping("/${data.tableMappingName}")
    public Result<Boolean> modify(${data.className}${config.entitySuffix} ${data.objectName}${config.entitySuffix}, HttpSession session) {
        boolean modify = ${data.objectName}${config.serviceSuffix}.updateById(${data.objectName}${config.entitySuffix});
        return Result.buildUpdateOk(modify);
    }

    /**
    * 根据Id删除
    */
    <@if config.enableApi??>
    @ApiMethod("根据Id删除")
    </@if>
    @DeleteMapping("/${data.tableMappingName}/{id}")
    public Result<Boolean> delete(@PathVariable Long id, HttpSession session) {
        boolean delete = ${data.objectName}${config.serviceSuffix}.removeById(id);
        return Result.buildDeleteOk(delete);
    }

    /**
    * 根据条件分页查询
    */
    <@if config.enableApi??>
    @ApiMethod("根据条件分页查询")
    </@if>
    @GetMapping("/${data.tableMappingName}/page/all")
    public Result<Page<${data.className}${config.entitySuffix}>> pageAll(${data.className}${config.entitySuffix} ${data.objectName}${config.entitySuffix}, Page<${data.className}${config.entitySuffix}> page) {
        LambdaQueryWrapper<${data.className}${config.entitySuffix}> wrapper = Wrappers.lambdaQuery();
        return Result.buildQueryOk(${data.objectName}${config.serviceSuffix}.page(page, wrapper));
    }
}