### 基于freemarker模板的Spring Boot + MyBatisPlus的代码生成器
1. 一次生成entity,controller,service,serviceImpl,mapper,mapper.xml文件，省去做无意义的体力活
2. 简化到最精简配置，使用方便，配置简单
3. 同时生成常用的保存，修改，删除，分页查询方法
### 支持的数据库
   MySql、SqlServer、PostgreSQL、Oracle
### 使用方法
1. 配置

   1. 配置数据库

      ```java
      DataSourceConfig dataSourceConfig = new DataSourceConfig().setSourceType("mysql")
                      .setSourceSchema("").setSourceDriver("com.mysql.cj.jdbc.Driver")
                      .setSourceUrl("jdbc:mysql://192.168.66.102:3306/citrsw?charset=utf8mb4&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true")
                      .setSourceUser("root").setSourcePassword("cleancode");
      ```

   2. 配置作者、版本号、需要替换的类文件前缀、包名，包后缀名，输出路径,自定义模板路径

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
                        .setMapperXmlOutPath("E:\\ideaProjects\\citrsw\\src\\main\\resources\\test\\mapper")
            		    //模板可进行自定义，不配置则使用默认
                        .setEntityTemplatePath("D:\\Users\\15706\\Desktop\\template\\entity.ftl")
                        .setControllerTemplatePath("D:\\Users\\15706\\Desktop\\template\\controller.ftl")
                        .setServiceTemplatePath("D:\\Users\\15706\\Desktop\\template\\service.ftl")
                        .setServiceImplTemplatePath("D:\\Users\\15706\\Desktop\\template\\serviceImpl.ftl")
                        .setMapperTemplatePath("D:\\Users\\15706\\Desktop\\template\\mapper1.ftl")
                        .setMapperXmlTemplatePath("D:\\Users\\15706\\Desktop\\template\\mapperXML.ftl");
        ```

2. 运行

     1. 第一种：最全的配置方式

          ```java
          new Generator().execute(dataSourceConfig, config);
          ```

     2. 第二种：最简单的配置方式，只配置数据库，其他均使用默认配置，该方式会将生成的文件输出到当前工程目录下

          ```java
          new Generator().execute(dataSourceConfig);
          ```

3. 完整示例

   ```java
   public static void main(String[] args) throws IOException {
       //配置数据库
       DataSourceConfig dataSourceConfig = new DataSourceConfig().setSourceType("mysql")
               .setSourceSchema("").setSourceDriver("com.mysql.cj.jdbc.Driver")
               .setSourceUrl("jdbc:mysql://192.168.66.102:3306/citrsw?charset=utf8mb4&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true")
               .setSourceUser("root").setSourcePassword("cleancode");
       //配置作者、版本号、需要替换的类文件前缀、包名，包后缀名，输出路径,自定义模板路径
       Config config = new Config()
               .setAuthor("Zhenfeng Li")
               .setVersion("1.0.0")
               .setReplace("Tb:")
               .setEntityPackage("cn.citrsw.entity").setEntityOutPath("E:\\ideaProjects\\citrsw\\src\\main\\java")
               .setControllerPackage("cn.citrsw.controller").setControllerOutPath("E:\\ideaProjects\\citrsw\\src\\main\\java")
               .setServicePackage("cn.citrsw.service").setServiceOutPath("E:\\ideaProjects\\citrsw\\src\\main\\java")
               .setServiceImplPackage("cn.citrsw.service.impl").setServiceImplOutPath("E:\\ideaProjects\\citrsw\\src\\main\\java")
               .setMapperPackage("cn.citrsw.mapper").setMapperOutPath("E:\\ideaProjects\\citrsw\\src\\main\\java")
               .setMapperXmlOutPath("E:\\ideaProjects\\citrsw\\src\\main\\resources\\test\\mapper")
               //模板可进行自定义，不配置则使用默认
               .setEntityTemplatePath("D:\\Users\\15706\\Desktop\\template\\entity.ftl")
               .setControllerTemplatePath("D:\\Users\\15706\\Desktop\\template\\controller.ftl")
               .setServiceTemplatePath("D:\\Users\\15706\\Desktop\\template\\service.ftl")
               .setServiceImplTemplatePath("D:\\Users\\15706\\Desktop\\template\\serviceImpl.ftl")
               .setMapperTemplatePath("D:\\Users\\15706\\Desktop\\template\\mapper1.ftl")
               .setMapperXmlTemplatePath("D:\\Users\\15706\\Desktop\\template\\mapperXML.ftl");
       //运行
       //第一种：最全的配置方式
       new Generator().execute(dataSourceConfig, config);
       //第二种：最简单的配置方式，只配置数据库，其他均使用默认配置，该方式会将生成的文件输出到当前工程目录下
       // new Generator().execute(dataSourceConfig);
   }
   ```