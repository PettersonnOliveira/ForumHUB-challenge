package alura.com.ForumHUB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class ForumHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumHubApplication.class, args);
	}

}
