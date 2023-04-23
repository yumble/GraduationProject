package project.graduation.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    @Lazy
    private DataSource dataSource;

}

