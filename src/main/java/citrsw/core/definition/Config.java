package citrsw.core.definition;

import lombok.Data;
import lombok.experimental.Accessors;

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
    private String entityPackage;

    /**
     * entity输出目录
     */
    private String entityOutPath;

    /**
     * entity后缀
     */
    private String entitySuffix = "";

    /**
     * controller包名
     */
    private String controllerPackage;

    /**
     * controller后缀
     */
    private String controllerSuffix = "Controller";

    /**
     * controller输出目录
     */
    private String controllerOutPath;

    /**
     * service包名
     */
    private String servicePackage;

    /**
     * service后缀
     */
    private String serviceSuffix = "Service";

    /**
     * service输出目录
     */
    private String serviceOutPath;

    /**
     * serviceImpl包名
     */
    private String serviceImplPackage;

    /**
     * serviceImpl后缀
     */
    private String serviceImplSuffix = "ServiceImpl";

    /**
     * serviceImp输出目录
     */
    private String serviceImplOutPath;

    /**
     * mapper包名
     */
    private String mapperPackage;

    /**
     * mapper后缀
     */
    private String mapperSuffix = "Mapper";

    /**
     * mapper输出目录
     */
    private String mapperOutPath;

    /**
     * mapperXml输出目录
     */
    private String mapperXmlOutPath;

}
