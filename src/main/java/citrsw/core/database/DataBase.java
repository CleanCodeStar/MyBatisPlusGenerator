package citrsw.core.database;

import citrsw.core.definition.ColumnDefinition;
import citrsw.core.definition.TableDefinition;
import citrsw.core.enumeration.TableType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * 数据库操作
 * <br/>
 * Created in 2019-09-03 12:21
 *
 * @author Zhenfeng Li
 */
@Slf4j
public class DataBase {
    /**
     * 单例
     */
    public static DataBase INSTANCE = new DataBase();
    private Connection connection;
    private DataSourceConfig dataSourceConfig;

    /**
     * 构造方法私有
     */
    private DataBase() {
    }

    /**
     * 新建数据库连接
     */
    public String createDataSourceConnect(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
        Properties props = new Properties();
        try {
            Class.forName(dataSourceConfig.getSourceDriver());
        } catch (ClassNotFoundException e) {
            log.error("数据库驱动加载失败");
            return e.getMessage();
        }
        props.put("user", dataSourceConfig.getSourceUser());
        props.put("password", dataSourceConfig.getSourcePassword());
        //Oracle数据库获取注释需要此配置
        props.put("remarksReporting", "true");
        //Mysql数据库获取注释需要此配置
        props.put("useInformationSchema", "true");
        log.info("数据库连接中");
        try {
            connection = DriverManager.getConnection(dataSourceConfig.getSourceUrl(), props);
            //设置Schema
            if (StringUtils.isNotEmpty(dataSourceConfig.getSourceSchema())) {
                connection.setSchema(dataSourceConfig.getSourceSchema());
            }
            log.info("数据库连接成功");
            return "Successful";
        } catch (SQLException e) {
            log.error("数据库连接失败");
            return e.getMessage();
        }
    }

    /**
     * 获取数据库连接
     */
    public Connection getDataSourceConnect() {
        return connection;
    }

    /**
     * 获取MetaData
     */
    public DatabaseMetaData getMetaData() {
        try {
            return connection.getMetaData();
        } catch (SQLException e) {
            log.error("获取MetaData异常");
            return null;
        }
    }

    /**
     * 注册所有表
     *
     * @param ignoreTablesName
     */
    public List<TableDefinition> registerAllTable(List<String> ignoreTablesName) {
        List<TableDefinition> tableDefinitions = new ArrayList<>();
        try {
            DatabaseMetaData metaData = getMetaData();
            ResultSet tableSet = metaData.getTables(connection.getCatalog(), connection.getSchema(), "%", new String[]{"TABLE", "VIEW"});
            while (tableSet.next()) {
                //表名称
                String tableName = tableSet.getString("TABLE_NAME");
                if (!ignoreTablesName.contains(tableName)) {
                    //跳过忽略的表
                    continue;
                }
                //表类型
                String tableType = tableSet.getString("TABLE_TYPE");
                //表注释信息
                String tableRemarks = tableSet.getString("REMARKS");
                if ("sqlServer".equals(dataSourceConfig.getSourceType()) && StringUtils.isBlank(tableRemarks)) {
                    String sql = "SELECT DISTINCT f.value AS remark  FROM syscolumns a LEFT JOIN systypes b ON a.xusertype= b.xusertype INNER JOIN sysobjects d ON d.name='" + tableName + "' LEFT JOIN syscomments e ON a.cdefault= e.id LEFT JOIN sys.extended_properties g ON a.id= G.major_id  AND a.colid= g.minor_id LEFT JOIN sys.extended_properties f ON d.id= f.major_id  AND f.minor_id= 0";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        tableRemarks = rs.getString("remark");

                    }
                }
                ResultSet primaryKeySet = metaData.getPrimaryKeys(connection.getCatalog(), connection.getSchema(), tableName);
                //创建字段定义集合
                List<ColumnDefinition> columnDefinitions = new ArrayList<>();
                //获取所有主键
                while (primaryKeySet.next()) {
                    String columnName = primaryKeySet.getString("COLUMN_NAME");
                    if (StringUtils.isNotEmpty(columnName)) {
                        ColumnDefinition columnDefinition = new ColumnDefinition();
                        columnDefinition.setColumnName(columnName.trim());
                        columnDefinitions.add(columnDefinition);
                    }
                }
                //创建表定义
                TableDefinition tableDefinition = new TableDefinition();
                if (StringUtils.isNotBlank(connection.getSchema())) {
                    tableDefinition.setSchema(connection.getSchema().trim());
                }
                tableDefinition.setTableName(tableName.trim());
                tableDefinition.setTableType(StringUtils.equals(TableType.TABLE.getValue(), tableType) ? TableType.TABLE : TableType.VIEW);
                if (StringUtils.isNotEmpty(tableRemarks)) {
                    tableDefinition.setTableRemark(tableRemarks.trim());
                }
                //添加主键字段集合
                tableDefinition.setPrimaryKeyColumnDefinitions(columnDefinitions);
                tableDefinitions.add(tableDefinition);
                log.info("[{}]-[{}]已注册成功", tableType, tableName);
            }
        } catch (SQLException e) {
            log.error("获取所有表异常");
            //清空已注册的表
            tableDefinitions.clear();
        }
        return tableDefinitions;
    }

    /**
     * 根据表注册所有字段
     */
    public void registerAllColumn(TableDefinition tableDefinition) {
        //主键字段定义转map
        Map<String, ColumnDefinition> fieldDefinitionMap = tableDefinition.getPrimaryKeyColumnDefinitions().parallelStream().collect(Collectors.toMap(ColumnDefinition::getColumnName, columnDefinition -> columnDefinition));
        //非主键字段定义
        List<ColumnDefinition> columnDefinitions = new ArrayList<>();
        try {
            DatabaseMetaData metaData = getMetaData();
            ResultSet columnSet = metaData.getColumns(connection.getCatalog(), connection.getSchema(), tableDefinition.getTableName(), "%");
            //sqlServer只能通过这种方式获取字段注释
            Map<String, String> columnRemarkMap = new HashMap<>(256);
            if ("sqlServer".equals(dataSourceConfig.getSourceType())) {
                String sql = "SELECT a.name AS name, isnull( g.[value], '-' ) AS remark  FROM sys.columns a LEFT JOIN sys.extended_properties g   ON ( a.object_id = g.major_id AND g.minor_id = a. column_id )  WHERE object_id = ( SELECT object_id  FROM sys.tables  WHERE name = '" + tableDefinition.getTableName() + "')";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    String key = rs.getString("name");
                    String remark = rs.getString("remark");
                    columnRemarkMap.put(key, remark);
                }
            }
            //获取字段
            while (columnSet.next()) {
                ColumnDefinition columnDefinition = new ColumnDefinition();
                boolean isPk = false;
                //字段名
                String colName = columnSet.getString("COLUMN_NAME");
                //判断是否为主键
                if (fieldDefinitionMap.containsKey(colName)) {
                    columnDefinition = fieldDefinitionMap.get(colName);
                    isPk = true;
                }
                columnDefinition.setColumnName(colName);
                //是否自动递增
                String isAutoincrement = columnSet.getString("IS_AUTOINCREMENT");
                columnDefinition.setAutoIncrement(Objects.equals(isAutoincrement, "YES"));
                //类型
                String columnType = columnSet.getString("TYPE_NAME");
                columnDefinition.setDbType(columnType);
                //字段注释
                String remarks = columnSet.getString("REMARKS");
                if (StringUtils.isNotEmpty(remarks)) {
                    columnDefinition.setColumnRemark(remarks);
                } else if (columnRemarkMap.containsKey(colName)) {
                    columnDefinition.setColumnRemark(columnRemarkMap.get(colName));
                }
                columnDefinition.setIsPk(isPk);
                if (!isPk) {
                    //添加为非主键字段
                    columnDefinitions.add(columnDefinition);
                }
            }
            tableDefinition.setUnPrimaryKeyColumnDefinitions(columnDefinitions);
            log.info("表[{}]所有字段已注册成功", tableDefinition.getTableName());
        } catch (SQLException e) {
            e.printStackTrace();
            log.info("表[{}]所有字段已注册失败", tableDefinition.getTableName());
        }
    }
}
