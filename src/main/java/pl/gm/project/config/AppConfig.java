package pl.gm.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.gm.project.converters.HeroConverter;
import javax.validation.Validator;

@Configuration
@ComponentScan(basePackages = "pl.gm")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "pl.gm")
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(heroConverter());

    }

    @Bean
    public HeroConverter heroConverter() {
        return new HeroConverter();
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }





}
