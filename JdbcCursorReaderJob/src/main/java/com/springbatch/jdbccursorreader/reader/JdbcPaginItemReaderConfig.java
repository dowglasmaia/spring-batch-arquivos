package com.springbatch.jdbccursorreader.reader;

import com.springbatch.jdbccursorreader.dominio.Cliente;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
public class JdbcPaginItemReaderConfig {


    @Bean
    public JdbcPagingItemReader<Cliente> jdbcPagingItemReader(
          @Qualifier("appDataSource") DataSource appDataSource,
          PagingQueryProvider queryProvider
    ){
        return new JdbcPagingItemReaderBuilder<Cliente>()
              .name("jdbcCursorReader")
              .dataSource(appDataSource)
              .queryProvider(queryProvider)
              .pageSize(1)
              .rowMapper(new BeanPropertyRowMapper<>(Cliente.class))
              .build();
    }

    @Bean
    public SqlPagingQueryProviderFactoryBean queryProvider(
          @Qualifier("appDataSource") DataSource appDataSource
    ){

        SqlPagingQueryProviderFactoryBean provider = new SqlPagingQueryProviderFactoryBean();
        provider.setDataSource(appDataSource);
        provider.setSelectClause("SELECT *");
        provider.setFromClause("FROM cliente");
        provider.setSortKey("email");
        return provider;
    }
}
