package sh.ajo.linkeye.linkeye;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LinkeyeApplication {

    public static final Logger LOGGER = LoggerFactory.getLogger(LinkeyeApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LinkeyeApplication.class, args);
    }

}
