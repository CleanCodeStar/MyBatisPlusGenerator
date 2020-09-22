package citrsw;

import citrsw.core.common.CoreUtils;
import citrsw.core.database.DataBase;
import citrsw.core.database.DataSourceConfig;
import citrsw.core.definition.Config;
import citrsw.core.definition.TableDefinition;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 启动类
 *
 * @author Zhenfeng Li
 * @version 1.0
 * @date 2020-09-22 9:31
 */
public class Generator {
    public static void main(String[] args) throws IOException{
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setSourceType("mysql").setSourceUrl("jdbc:mysql://192.168.66.102:3306/citrsw?charset=utf8mb4&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true")
                .setSourceUser("root").setSourcePassword("cleancode").setSourceSchema("");
        dataSourceConfig.setSourceDriver("com.mysql.cj.jdbc.Driver");
        DataBase dataBase = DataBase.INSTANCE;
        dataBase.createDataSourceConnect(dataSourceConfig);
        //注册表
        List<TableDefinition> tableDefinitions = dataBase.registerAllTable();
        //注册表字段
        tableDefinitions.parallelStream().forEach(dataBase::registerAllColumn);

        //配置包名，包后缀名，输出路径
        Config config = new Config()
                .setAuthor("Zhenfeng Li")
                .setVersion("1.0.0")
                .setReplace("Tb:")
                .setEntityPackage("cn.citrsw.entity").setEntityOutPath("E:\\ideaProjects\\citrsw\\src\\main\\java")
                .setControllerPackage("cn.citrsw.controller").setControllerOutPath("E:\\ideaProjects\\citrsw\\src\\main\\java")
                .setServicePackage("cn.citrsw.service").setServiceOutPath("E:\\ideaProjects\\citrsw\\src\\main\\java")
                .setServiceImplPackage("cn.citrsw.service.impl").setServiceImplOutPath("E:\\ideaProjects\\citrsw\\src\\main\\java")
                .setMapperPackage("cn.citrsw.mapper").setMapperOutPath("E:\\ideaProjects\\citrsw\\src\\main\\java")
                .setMapperXmlOutPath("E:\\ideaProjects\\citrsw\\src\\main\\resources\\test\\mapper");

        //使用模版创建
        Configuration configuration = new Configuration(Configuration.getVersion());
        //配置模板所在目录
        configuration.setDirectoryForTemplateLoading(new File("E:\\ideaProjects\\MyBatisPlusGenerator\\src\\main\\resources\\template"));
        configuration.setDefaultEncoding("utf-8");
        //配置具体模板文件
        Template entityTemplate = configuration.getTemplate("entity.ftl");
        Template controllerTemplate = configuration.getTemplate("controller.ftl");
        Template serviceTemplate = configuration.getTemplate("service.ftl");
        Template serviceImplTemplate = configuration.getTemplate("serviceImpl.ftl");
        Template mapperTemplate = configuration.getTemplate("mapper.ftl");
        Template mapperXmlTemplate = configuration.getTemplate("mapperXml.ftl");

        //循环创建 并发循环
        tableDefinitions.parallelStream().forEach(entityDefinition -> {
            //替换前缀
            String replace = config.getReplace();
            if (StringUtils.isNotBlank(replace)) {
                String[] split = replace.split(":");
                String replaceFirst = entityDefinition.getJavaClassName(true).replaceFirst(split[0], split.length > 1 ? split[1] : "");
                entityDefinition.setJavaName(CoreUtils.toUpperFirstCase(replaceFirst));
                entityDefinition.setTableMappingName(CoreUtils.camelToUnderLine(entityDefinition.getObjectName()));
            }

            try {
                //创建Entity
                File file = new File(config.getEntityOutPath(),
                        config.getEntityPackage().replaceAll("\\.", "/")
                                + "/"
                                + entityDefinition.getClassName()
                                + config.getEntitySuffix()
                                + ".java");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                Writer out = new FileWriter(file);
                Map<String, Object> data = new HashMap<>();
                data.put("config", config);
                data.put("data", entityDefinition);
                entityTemplate.process(data, out);
                //创建controller
                file = new File(config.getControllerOutPath(),
                        config.getControllerPackage().replaceAll("\\.", "/")
                                + "/"
                                + entityDefinition.getClassName()
                                + config.getControllerSuffix()
                                + ".java");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                out = new FileWriter(file);
                data = new HashMap<>();
                data.put("config", config);
                data.put("data", entityDefinition);
                controllerTemplate.process(data, out);
                //创建service
                file = new File(config.getServiceOutPath(),
                        config.getServicePackage().replaceAll("\\.", "/")
                                + "/"
                                + entityDefinition.getClassName()
                                + config.getServiceSuffix()
                                + ".java");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                out = new FileWriter(file);
                data = new HashMap<>();
                data.put("config", config);
                data.put("data", entityDefinition);
                serviceTemplate.process(data, out);
                //创建serviceImpl
                file = new File(config.getServiceImplOutPath(),
                        config.getServiceImplPackage().replaceAll("\\.", "/")
                                + "/"
                                + entityDefinition.getClassName()
                                + config.getServiceImplSuffix()
                                + ".java");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                out = new FileWriter(file);
                data = new HashMap<>();
                data.put("config", config);
                data.put("data", entityDefinition);
                serviceImplTemplate.process(data, out);
                //创建mapper
                file = new File(config.getMapperOutPath(),
                        config.getMapperPackage().replaceAll("\\.", "/")
                                + "/"
                                + entityDefinition.getClassName()
                                + config.getMapperSuffix()
                                + ".java");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                out = new FileWriter(file);
                data = new HashMap<>();
                data.put("config", config);
                data.put("data", entityDefinition);
                mapperTemplate.process(data, out);
                //创建mapper.xml
                file = new File(config.getMapperXmlOutPath(),
                        entityDefinition.getClassName()
                                + config.getMapperSuffix()
                                + ".xml");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                out = new FileWriter(file);
                data = new HashMap<>();
                data.put("config", config);
                data.put("data", entityDefinition);
                mapperXmlTemplate.process(data, out);
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
            }
        });

    }
}
