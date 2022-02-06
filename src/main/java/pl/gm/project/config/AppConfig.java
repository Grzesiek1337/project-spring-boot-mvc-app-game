package pl.gm.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Validator;

@Configuration
@ComponentScan(basePackages = "pl.gm")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "pl.gm")
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

}
