package com.example.demo.config;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.filter.MyFilter;
import com.example.demo.filter.MyFilter2;

@Configuration
public class JavaConfig {
	@Autowired MyFilter myFilter;
	@Autowired MyFilter2 myFilter2;
	
	/*　ModelMapper
	　Mapperのクラス（画面との受け渡し用のデータとエンティティの変換を行うクラス）
	　使用するには、pow.xmlに以下の記述追加必要
	　<dependency>
	　<groupId>org.modelmapper.extensions</groupId>
	　<artifactId>modelmapper-spring</artifactId>
	　<version>0.7.3</version> 
	　</dependency>
	*/
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	//フィルタの登録、フィルタパスの指定
	@Bean
	public FilterRegistrationBean<MyFilter> myFilterBean() {	//スタッフ用フィルターの登録
		// ServletContextInitializerBeansに格納される
		//FilterRegistrationBean bean = new FilterRegistrationBean( new MyFilter() ); 自作クラスnewすると動作しない？代わりに@Autowierdを入れる
		FilterRegistrationBean<MyFilter> bean = new FilterRegistrationBean<MyFilter>( myFilter );
		// <url-pattern/>
		bean.addUrlPatterns("/customer/list");	//顧客一覧
		bean.addUrlPatterns("/customer/add");	//顧客追加
		bean.addUrlPatterns("/customer/search");	//顧客検索
		bean.addUrlPatterns("/purchase/list");	//顧客一覧
		bean.addUrlPatterns("/purchase/edit");	//注文数変更
		// MyFilterがMyFilter2より先に呼ばれる
		//bean.setOrder(1);
		return bean;
	}
	@Bean
	public FilterRegistrationBean<MyFilter2> myFilterBean2() {	//顧客用フィルターの登録
		// ServletContextInitializerBeansに格納される
		//FilterRegistrationBean bean = new FilterRegistrationBean( new MyFilter2() ); 自作クラスnewすると動作しない？代わりに@Autowierdを入れる
		FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<MyFilter2>( myFilter2 );
		// <url-pattern/>
		bean.addUrlPatterns("/customer/purchase/add");	//注文
		// MyFilterがMyFilter2より先に呼ばれる
		//bean.setOrder(1);
		return bean;
	}
}
