package com.awei.product;

import com.netflix.discovery.DiscoveryManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Description:
 * @Author: Awei
 * @Create: 2021-08-09 15:03
 **/
@SpringBootApplication
@MapperScan("com.awei.product.mapper")
@EnableEurekaClient
@EnableDiscoveryClient
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("老子被干了");
                DiscoveryManager.getInstance().shutdownComponent();
            }
        }));
    }
}
