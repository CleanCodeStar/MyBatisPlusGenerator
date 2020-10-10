package citrsw;

import citrsw.core.common.CoreUtils;
import citrsw.core.database.DataBase;
import citrsw.core.database.DataSourceConfig;
import citrsw.core.definition.Config;
import citrsw.core.definition.TableDefinition;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成器类
 *
 * @author Zhenfeng Li
 * @version 1.0
 * @date 2020-09-22 9:31
 */
@Slf4j
public class Generator {

    public static void main(String[] args) throws IOException {
        //配置数据库
        DataSourceConfig dataSourceConfig = new DataSourceConfig().setSourceType("mysql")
                .setSourceSchema("").setSourceDriver("com.mysql.cj.jdbc.Driver")
                .setSourceUrl("jdbc:mysql://39.105.50.107:3306/api-example?charset=utf8mb4&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true")
                .setSourceUser("cleancode").setSourcePassword("QBg2@YdB^CQWlAg*yStG45oui#*pG#");
        //配置作者、版本号、需要替换的类文件前缀、包名，包后缀名，输出路径,自定义模板路径
        Config config = new Config()
                .setAuthor("Zhenfeng Li")
                .setVersion("1.0.0")
                .setReplace("Tb:")
                .setEntityPackage("com.citrsw.apiexample.entity").setEntityOutPath("E:\\ideaProjects\\api-example\\src\\main\\java")
                .setControllerPackage("com.citrsw.apiexample.controller").setControllerOutPath("E:\\ideaProjects\\api-example\\src\\main\\java")
                .setServicePackage("com.citrsw.apiexample.service").setServiceOutPath("E:\\ideaProjects\\api-example\\src\\main\\java")
                .setServiceImplPackage("com.citrsw.apiexample.service.impl").setServiceImplOutPath("E:\\ideaProjects\\api-example\\src\\main\\java")
                .setMapperPackage("com.citrsw.apiexample.mapper").setMapperOutPath("E:\\ideaProjects\\api-example\\src\\main\\java")
                .setMapperXmlOutPath("E:\\ideaProjects\\api-example\\src\\main\\resources\\mapper")
                .setEnableApi(true);

//                //模板可进行自定义，不配置则使用默认
//                .setEntityTemplatePath("D:\\Users\\15706\\Desktop\\template\\entity.ftl")
//                .setControllerTemplatePath("D:\\Users\\15706\\Desktop\\template\\controller.ftl")
//                .setServiceTemplatePath("D:\\Users\\15706\\Desktop\\template\\service.ftl")
//                .setServiceImplTemplatePath("D:\\Users\\15706\\Desktop\\template\\serviceImpl.ftl")
//                .setMapperTemplatePath("D:\\Users\\15706\\Desktop\\template\\mapper1.ftl")
//                .setMapperXmlTemplatePath("D:\\Users\\15706\\Desktop\\template\\mapperXML.ftl");
        //运行
        //第一种：最全的配置方式
        new Generator().execute(dataSourceConfig, config);
        //第二种：最简单的配置方式，只配置数据库，其他均使用默认配置，该方式会将生成的文件输出到当前工程目录下
        // new Generator().execute(dataSourceConfig);
    }

    /**
     * 执行
     */
    public void execute(DataSourceConfig dataSourceConfig) throws IOException {
        execute(dataSourceConfig, new Config());
    }

    /**
     * 执行
     */
    public void execute(DataSourceConfig dataSourceConfig, Config config) throws IOException {
        DataBase dataBase = DataBase.INSTANCE;
        dataBase.createDataSourceConnect(dataSourceConfig);
        //注册表
        List<TableDefinition> tableDefinitions = dataBase.registerAllTable();
        //注册表字段
        tableDefinitions.parallelStream().forEach(dataBase::registerAllColumn);
        log.info("======表注册完成======");
        //使用模版创建
        Configuration configuration = new Configuration(Configuration.getVersion());
        //配置模板所在目录
        configuration.setDefaultEncoding("utf-8");
        //配置具体模板文件
        Template entityTemplate;
        if (StringUtils.isNotBlank(config.getEntityTemplatePath()) && new File(config.getEntityTemplatePath()).exists()) {
            //使用指定额模板
            configuration.setDirectoryForTemplateLoading(new File(config.getEntityTemplatePath()).getParentFile());
            entityTemplate = configuration.getTemplate(new File(config.getEntityTemplatePath()).getName());
        } else {
            //未配置则使用默认的模板
            configuration.setClassForTemplateLoading(this.getClass(), "/default");
            entityTemplate = configuration.getTemplate("entity.ftl");
        }
        Template controllerTemplate;
        if (StringUtils.isNotBlank(config.getControllerTemplatePath()) && new File(config.getControllerTemplatePath()).exists()) {
            //使用指定额模板
            configuration.setDirectoryForTemplateLoading(new File(config.getControllerTemplatePath()).getParentFile());
            controllerTemplate = configuration.getTemplate(new File(config.getControllerTemplatePath()).getName());
        } else {
            //未配置则使用默认的模板
            configuration.setClassForTemplateLoading(this.getClass(), "/default");
            controllerTemplate = configuration.getTemplate("controller.ftl");
        }
        Template serviceTemplate;
        if (StringUtils.isNotBlank(config.getServiceTemplatePath()) && new File(config.getServiceTemplatePath()).exists()) {
            //使用指定额模板
            configuration.setDirectoryForTemplateLoading(new File(config.getServiceTemplatePath()).getParentFile());
            serviceTemplate = configuration.getTemplate(new File(config.getServiceTemplatePath()).getName());
        } else {
            //未配置则使用默认的模板
            configuration.setClassForTemplateLoading(this.getClass(), "/default");
            serviceTemplate = configuration.getTemplate("service.ftl");
        }
        Template serviceImplTemplate;
        if (StringUtils.isNotBlank(config.getServiceImplTemplatePath()) && new File(config.getServiceImplTemplatePath()).exists()) {
            //使用指定额模板
            configuration.setDirectoryForTemplateLoading(new File(config.getServiceImplTemplatePath()).getParentFile());
            serviceImplTemplate = configuration.getTemplate(new File(config.getServiceImplTemplatePath()).getName());
        } else {
            //未配置则使用默认的模板
            configuration.setClassForTemplateLoading(this.getClass(), "/default");
            serviceImplTemplate = configuration.getTemplate("serviceImpl.ftl");
        }
        Template mapperTemplate;
        if (StringUtils.isNotBlank(config.getMapperTemplatePath()) && new File(config.getMapperTemplatePath()).exists()) {
            //使用指定额模板
            configuration.setDirectoryForTemplateLoading(new File(config.getMapperTemplatePath()).getParentFile());
            mapperTemplate = configuration.getTemplate(new File(config.getMapperTemplatePath()).getName());
        } else {
            //未配置则使用默认的模板
            configuration.setClassForTemplateLoading(this.getClass(), "/default");
            mapperTemplate = configuration.getTemplate("mapper.ftl");
        }
        Template mapperXmlTemplate;
        if (StringUtils.isNotBlank(config.getMapperXmlTemplatePath()) && new File(config.getMapperXmlTemplatePath()).exists()) {
            //使用指定额模板
            configuration.setDirectoryForTemplateLoading(new File(config.getMapperXmlTemplatePath()).getParentFile());
            mapperXmlTemplate = configuration.getTemplate(new File(config.getMapperXmlTemplatePath()).getName());
        } else {
            //未配置则使用默认的模板
            configuration.setClassForTemplateLoading(this.getClass(), "/default");
            mapperXmlTemplate = configuration.getTemplate("mapperXML.ftl");
        }
        log.info("======模板加载完成======");
        //循环创建 并发循环
        tableDefinitions.parallelStream().forEach(tableDefinition -> {
            //替换前缀
            String replace = config.getReplace();
            if (StringUtils.isNotBlank(replace)) {
                String[] split = replace.split(":");
                String replaceFirst = tableDefinition.getJavaClassName(true).replaceFirst(split[0], split.length > 1 ? split[1] : "");
                tableDefinition.setJavaName(CoreUtils.toUpperFirstCase(replaceFirst));
            }
            tableDefinition.setTableMappingName(CoreUtils.camelToUnderLine(tableDefinition.getObjectName()));
            try {
                //创建Entity
                File file = new File(config.getEntityOutPath(), config.getEntityPackage().replaceAll("\\.", "/") + "/" + tableDefinition.getClassName() + config.getEntitySuffix() + ".java");
                doExecute(config, tableDefinition, entityTemplate, file);
                //创建controller
                file = new File(config.getControllerOutPath(), config.getControllerPackage().replaceAll("\\.", "/") + "/" + tableDefinition.getClassName() + config.getControllerSuffix() + ".java");
                doExecute(config, tableDefinition, controllerTemplate, file);
                //创建service
                file = new File(config.getServiceOutPath(), config.getServicePackage().replaceAll("\\.", "/") + "/" + tableDefinition.getClassName() + config.getServiceSuffix() + ".java");
                doExecute(config, tableDefinition, serviceTemplate, file);
                //创建serviceImpl
                file = new File(config.getServiceImplOutPath(), config.getServiceImplPackage().replaceAll("\\.", "/") + "/" + tableDefinition.getClassName() + config.getServiceImplSuffix() + ".java");
                doExecute(config, tableDefinition, serviceImplTemplate, file);
                //创建mapper
                file = new File(config.getMapperOutPath(), config.getMapperPackage().replaceAll("\\.", "/") + "/" + tableDefinition.getClassName() + config.getMapperSuffix() + ".java");
                doExecute(config, tableDefinition, mapperTemplate, file);
                //创建mapper.xml
                file = new File(config.getMapperXmlOutPath(), tableDefinition.getClassName() + config.getMapperSuffix() + ".xml");
                doExecute(config, tableDefinition, mapperXmlTemplate, file);
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
            }
        });
        log.info("======执行完成======");
    }

    /**
     * 执行创建
     */
    private void doExecute(Config config, TableDefinition tableDefinition, Template template, File file) throws IOException, TemplateException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Writer out = new FileWriter(file);
        Map<String, Object> data = new HashMap<>();
        data.put("config", config);
        data.put("data", tableDefinition);
        template.process(data, out);
        log.info("文件[" + file.getName() + "]创建成功");
    }
}
