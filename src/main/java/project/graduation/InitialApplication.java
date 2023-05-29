package project.graduation;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@ServletComponentScan
@SpringBootApplication
@EnableJpaAuditing
public class InitialApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(InitialApplication.class, args);
	}
	@PreDestroy
	private void onDestory() {
		log.info("method::onDestory ...");
		log.info("method::onDestory, OK");
	}

}
