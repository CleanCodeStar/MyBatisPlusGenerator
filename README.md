### 基于freemarker模板的Spring Boot + MyBatisPlus的代码生成器
1. 一次生成entity,controller,service,serviceImpl,mapper,mapper.xml文件，省去做无意义的体力活
2. 简化到最精简配置，使用方便，配置简单
3. 同时生成常用的保存，修改，删除，分页查询方法
### 使用方法
1. 配置数据库信息，连接、用户名、密码、驱动
   
     ```java
   DataSourceConfig dataSourceConfig = new DataSourceConfig();
   dataSourceConfig.setSourceType("mysql").setSourceUrl("jdbc:mysql://192.168.66.102:3306/citrsw?charset=utf8mb4&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true")
       .setSourceUser("root").setSourcePassword("cleancode").setSourceSchema("").setSourceDriver("com.mysql.cj.jdbc.Driver");
   ```
   
2. 配置作者，版本号，替换类文件名前缀，包名，包后缀名，输出路径

     ```java
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
     ```

3. 配置模板所在目录

     ```java
     configuration.setDirectoryForTemplateLoading(new File("E:\\ideaProjects\\MyBatisPlusGenerator\\src\\main\\resources\\template"));
     ```

4. 配置具体模板文件

     ```java
     Template entityTemplate = configuration.getTemplate("entity.ftl");
     Template controllerTemplate = configuration.getTemplate("controller.ftl");
     Template serviceTemplate = configuration.getTemplate("service.ftl");
     Template serviceImplTemplate = configuration.getTemplate("serviceImpl.ftl");
     Template mapperTemplate = configuration.getTemplate("mapper.ftl");
     Template mapperXmlTemplate = configuration.getTemplate("mapperXml.ftl");
     ```

5. 运行