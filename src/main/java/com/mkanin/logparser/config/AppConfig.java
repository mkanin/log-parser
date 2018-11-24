package com.mkanin.logparser.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 * This class was taken from here https://sivalabs.blogspot.com/2011/02/springhibernate-application-with-zero.html
 * @author SivaLabs
 * 
 */
@Import({PersistenceHibernateConfig.class})
@Configuration
public class AppConfig {
  //<context:property-placeholder location="classpath:application.properties"></context:property-placeholder>
  @Bean
  public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer()
  {
      PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
      ppc.setLocation(new ClassPathResource("application.properties"));
      ppc.setIgnoreUnresolvablePlaceholders(true);
      return ppc;
  }
}
