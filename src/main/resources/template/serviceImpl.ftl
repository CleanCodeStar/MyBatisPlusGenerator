package ${config.serviceImplPackage};

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${config.entityPackage}.${data.className}${config.entitySuffix};
import ${config.mapperPackage}.${data.className}${config.mapperSuffix};
import ${config.servicePackage}.${data.className}${config.serviceSuffix};
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* ${data.tableRemark}${config.serviceImplSuffix}
*
* @author Zhenfeng Li
* @version 0.0.1
* @date 2020-04-01 08:04:05
*/
@Service
@Slf4j
public class ${data.className}${config.serviceImplSuffix} extends ServiceImpl<${data.className}${config.mapperSuffix},${data.className}${config.entitySuffix}> implements  ${data.className}${config.serviceSuffix} {

    private final ${data.className}${config.mapperSuffix} ${data.objectName}${config.mapperSuffix};

    public ${data.className}${config.serviceImplSuffix}(${data.className}${config.mapperSuffix} ${data.objectName}${config.mapperSuffix}) {
        this.${data.objectName}${config.mapperSuffix} = ${data.objectName}${config.mapperSuffix};
    }

}