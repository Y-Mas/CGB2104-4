package cn.tedu.sp08;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine
public class Sp08TurbineApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp08TurbineApplication.class, args);
    }

}
