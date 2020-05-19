package com.jnbdt.scrolltofillmaterial;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages ="com.jnbdt.scrolltofillmaterial.dao")
public class ScrolltofillmaterialApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrolltofillmaterialApplication.class, args);
    }

}
