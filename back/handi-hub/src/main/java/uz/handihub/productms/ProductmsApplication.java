package uz.handihub.productms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import uz.handihub.productms.properties.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class})
public class ProductmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductmsApplication.class, args);
    }

}
