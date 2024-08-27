package TestFileUpload;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class TestFileUploadApplication {

    public static void main(String[] args) {

        try {
            Dotenv dotenv = Dotenv.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SpringApplication.run(TestFileUploadApplication.class, args);
    }

}
