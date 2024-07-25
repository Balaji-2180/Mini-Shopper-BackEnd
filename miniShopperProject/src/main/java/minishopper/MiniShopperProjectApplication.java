package minishopper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Mini Shopper API", version = "1.0", description = "Mini Shopper"))
public class MiniShopperProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniShopperProjectApplication.class, args);
	}

}
