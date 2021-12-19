package atos.net.netbull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class NetbullApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetbullApplication.class, args);
	}

}
