package com.example.demo.config;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//　Mapperのクラス（画面との受け渡し用のデータとエンティティの変換を行うクラス）
//使用するには、pow.xmlに以下の記述追加必要
//<dependency>
//<groupId>org.modelmapper.extensions</groupId>
//<artifactId>modelmapper-spring</artifactId>
//<version>0.7.3</version> 
//</dependency>

@Configuration
public class JavaConfig {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
