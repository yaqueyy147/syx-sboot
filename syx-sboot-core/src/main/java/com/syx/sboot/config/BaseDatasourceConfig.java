package com.syx.sboot.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by suyx on 2018/11/22 0022.
 */
@Configuration
@MapperScan(basePackages = "com.syx.sboot.mapper.base", sqlSessionTemplateRef = "baseSqlSessionTemplate")
public class BaseDatasourceConfig {

    @Bean(name = "baseDataSource")
    @Qualifier("baseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.base")
    @Primary
    public DataSource baseDataSource(){
        DataSource dataSource = DataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean(name = "baseSqlSessionFactory")
    @Primary
    public SqlSessionFactory baseSqlSessionFactory(@Qualifier("baseDataSource")DataSource baseDataSource) throws Exception{
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(baseDataSource);
//        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(""));
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "baseTransactionManager")
    @Primary
    public DataSourceTransactionManager baseTransactionManager(@Qualifier("baseDataSource") DataSource baseDataSource) {
        return new DataSourceTransactionManager(baseDataSource);
    }

    @Bean(name = "baseSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate baseSqlSessionTemplate(@Qualifier("baseSqlSessionFactory") SqlSessionFactory baseSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(baseSqlSessionFactory);
    }

    @Bean(name = "baseJdbcTemplate")
    @Primary
    public JdbcTemplate baseJdbcTemplate(@Qualifier("baseDataSource")DataSource baseDataSource){
        return new JdbcTemplate(baseDataSource);
    }

}
