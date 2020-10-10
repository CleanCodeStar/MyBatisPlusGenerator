package citrsw.core.definition;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 配置
 *
 * @author Zhenfeng Li
 * @version 1.0
 * @date 2020-09-22 10:35
 */
@Data
@Accessors(chain = true)
@Slf4j
public class Config {

    /**
     * 替换字符，格式【old:new】,【old:】表示替换为空
     */
    private String replace;

    /**
     * 作者
     */
    private String author;

    /**
     * 版本号
     */
    private String version;

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    /**
     * entity包名
     */
    private String entityPackage = "default.entity";

    /**
     * entity输出目录
     */
    private String entityOutPath = System.getProperty("user.dir");

    /**
     * entity后缀
     */
    private String entitySuffix = "";

    /**
     * controller包名
     */
    private String controllerPackage = "default.controller";

    /**
     * controller后缀
     */
    private String controllerSuffix = "Controller";

    /**
     * controller输出目录
     */
    private String controllerOutPath = System.getProperty("user.dir");

    /**
     * service包名
     */
    private String servicePackage = "default.service";

    /**
     * service后缀
     */
    private String serviceSuffix = "Service";

    /**
     * service输出目录
     */
    private String serviceOutPath = System.getProperty("user.dir");

    /**
     * serviceImpl包名
     */
    private String serviceImplPackage = "default.service.impl";

    /**
     * serviceImpl后缀
     */
    private String serviceImplSuffix = "ServiceImpl";

    /**
     * serviceImp输出目录
     */
    private String serviceImplOutPath = System.getProperty("user.dir");

    /**
     * mapper包名
     */
    private String mapperPackage = "default.mapper";

    /**
     * mapper后缀
     */
    private String mapperSuffix = "Mapper";

    /**
     * mapper输出目录
     */
    private String mapperOutPath = System.getProperty("user.dir");

    /**
     * mapperXml输出目录
     */
    private String mapperXmlOutPath = System.getProperty("user.dir") + "/mapper";

    /**
     * 自定义entity模板全路径
     */
    private String entityTemplatePath;

    /**
     * 自定义controller模板全路径
     */
    private String controllerTemplatePath;

    /**
     * 自定义service模板全路径
     */
    private String serviceTemplatePath;

    /**
     * 自定义serviceImp模板全路径
     */
    private String serviceImplTemplatePath;

    /**
     * 自定义mapper模板全路径
     */
    private String mapperTemplatePath;

    /**
     * 自定义mapperXml模板全路径
     */
    private String mapperXmlTemplatePath;
    /**
     * 激活集成JavaApiDocs
     */
    private boolean enableApi = false;
}
