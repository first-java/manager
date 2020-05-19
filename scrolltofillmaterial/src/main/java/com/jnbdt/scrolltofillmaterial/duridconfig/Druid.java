package com.jnbdt.scrolltofillmaterial.duridconfig;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassNmame S.java
 * @author 奥特虾不会写代码
 * @author E-mail:pengshiliang@latticepower.com
 * @version 1.0
 * @time 创建时间：2020年4月17日 上午10:57:47
 * @Copyright © 2020 by 奥特虾不会写代码
 */

@Configuration
public class Druid {
    public Druid() {}

    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {

        ServletRegistrationBean<StatViewServlet> bean =
            new ServletRegistrationBean<StatViewServlet>(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>(10);
        initParams.put("loginUsername", "psl");
        initParams.put("loginPassword", "psl123");
        /** 默认就是允许所有访问 */
        initParams.put("allow", "192.168.6.207,127.0.1.0");
        /** 黑名单的IP */
        initParams.put("deny", "192.168.15.21");
        bean.setInitParameters(initParams);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> statFilter() {
        FilterRegistrationBean<WebStatFilter> bean = new FilterRegistrationBean<WebStatFilter>();
        bean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<>(10);
        initParams.put("exclusions", "/static/*,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}