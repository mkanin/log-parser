package com.mkanin.logparser.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * This class was taken from here https://sivalabs.blogspot.com/2011/02/springhibernate-application-with-zero.html
 * @author SivaLabs
 * 
 */
@Configuration
@EnableTransactionManagement
public class PersistenceHibernateConfig {
  
  @Value("${jdbc.driver-class-name}")
  private String driverClassName;
  
  @Value("${jdbc.url}")
  private String url;
  
  @Value("${jdbc.username}")
  private String username;
  
  @Value("${jdbc.password}")
  private String password;
  
  @Value("${hibernate.dialect}")
  private String hibernateDialect;
  
  @Value("${hibernate.show-sql}")
  private String hibernateShowSql;
  
  @Value("${hibernate.hbm2ddl.auto}")
  private String hibernateHbm2ddlAuto;
  
  @Bean
  public DataSource getDataSource() {
     DriverManagerDataSource dataSource = new DriverManagerDataSource();
     dataSource.setDriverClassName(driverClassName);
     dataSource.setUrl(url);
     dataSource.setUsername(username);
     dataSource.setPassword(password);
     return dataSource;
  }

  @Bean
  @Autowired
  public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
      HibernateTransactionManager htm = new HibernateTransactionManager();
      htm.setSessionFactory(sessionFactory);
      return htm;
  }
  
  @Bean
  @Autowired
  public HibernateTemplate getHibernateTemplate(SessionFactory sessionFactory) {
      HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
      return hibernateTemplate;
  }
      
  @Bean(name="sessionFactory")
  public LocalSessionFactoryBean getSessionFactory() {
     LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
     factoryBean.setDataSource(getDataSource());

     factoryBean.setHibernateProperties(getHibernateProperties());
     
     factoryBean.setPackagesToScan(new String[]{"com.mkanin.logparser.model"});
     return factoryBean;
  }

  @Bean
  public Properties getHibernateProperties() {
      Properties properties = new Properties();
      properties.put("hibernate.dialect", hibernateDialect);
      properties.put("hibernate.show_sql", hibernateShowSql);
      properties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
      
      return properties;
  }
}
