package com.syx.sboot.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by suyx on 2018/11/22 0022.
 */
@Configuration
@MapperScan(basePackages = "com.syx.sboot.mapper.family", sqlSessionTemplateRef = "familySqlSessionTemplate")
public class FamilyDatasourceConfig {

    @Bean(name = "familyDataSource")
    @Qualifier("familyDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.family")
    public DataSource familyDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "familySqlSessionFactory")
    public SqlSessionFactory familySqlSessionFactory(@Qualifier("familyDataSource")DataSource familyDataSource) throws Exception{
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(familyDataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactoryBean.setConfiguration(configuration);
//        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(""));
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "familyTransactionManager")
    public DataSourceTransactionManager familyTransactionManager(@Qualifier("familyDataSource") DataSource familyDataSource) {
        return new DataSourceTransactionManager(familyDataSource);
    }

    @Bean(name = "familySqlSessionTemplate")
    public SqlSessionTemplate familySqlSessionTemplate(@Qualifier("familySqlSessionFactory") SqlSessionFactory familySqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(familySqlSessionFactory);
    }

    @Bean(name = "familyJdbcTemplate")
    public JdbcTemplate familyJdbcTemplate(@Qualifier("familyDataSource")DataSource familyDataSource){
        return new JdbcTemplate(familyDataSource);
    }

}
