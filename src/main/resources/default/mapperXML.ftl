<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${config.mapperPackage}.${data.className}${config.mapperSuffix}">
    <resultMap id="resultMap" type="${config.entityPackage}.${data.className}${config.entitySuffix}">
        <#list data.allFieldDefinitions as fieldDefinition>
        <#if fieldDefinition.isPk>
        <id column="${fieldDefinition.columnName}" property="${fieldDefinition.javaFieldName}" jdbcType="${fieldDefinition.dbTypeName}"/>
        <#else>
        <result column="${fieldDefinition.columnName}" property="${fieldDefinition.javaFieldName}" jdbcType="${fieldDefinition.dbTypeName}"/>
        </#if>
        </#list>
    </resultMap>
    <sql id="column_List" >
        <!--@sql select -->
        <#list data.allFieldDefinitions as fieldDefinition>${fieldDefinition.columnName}<#if fieldDefinition_has_next>, </#if></#list>
        <!--@sql from ${data.tableName}-->
    </sql>

</mapper>
