package com.example.demo.config;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.filter.MyFilter;

//　Mapperのクラス（画面との受け渡し用のデータとエンティティの変換を行うクラス）
//　使用するには、pow.xmlに以下の記述追加必要
//<dependency>
//<groupId>org.modelmapper.extensions</groupId>
//<artifactId>modelmapper-spring</artifactId>
//<version>0.7.3</version> 
//</dependency>

@Configuration
public class JavaConfig {
	//ModelMapper
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	//フィルタのパスの指定
    @Bean
    public FilterRegistrationBean myWebMvcFilter() {
        // ServletContextInitializerBeansに格納される
        FilterRegistrationBean bean = new FilterRegistrationBean(new MyFilter());
        // <url-pattern/>
        bean.addUrlPatterns("/customer/list");
        // MyFilterがMyFilter2より先に呼ばれる
        //bean.setOrder(1);
        return bean;
    }
}
