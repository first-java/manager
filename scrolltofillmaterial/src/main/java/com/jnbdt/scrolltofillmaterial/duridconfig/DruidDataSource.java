package com.jnbdt.scrolltofillmaterial.duridconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;

/**
 * @ClassNmame WW.java
 * @author 奥特虾不会写代码
 * @author E-mail:pengshiliang@latticepower.com
 * @version 1.0
 * @time 创建时间：2020年4月17日 上午10:57:53
 * @Copyright © 2020 by 奥特虾不会写代码
 */
public class DruidDataSource {
    public DruidDataSource() {}

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public WallFilter wallFilter() {
        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(wallConfig());
        return wallFilter;
    }

    @Bean
    public WallConfig wallConfig() {
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);// 允许一次执行多条语句
        config.setNoneBaseStatementAllow(true);// 允许非基本语句的其他语句
        return config;
    }

}