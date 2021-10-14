package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class MyFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 起動時に実行される
		System.out.println("★Sample Filter init");
	}
	 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// リクエスト発行時に実行される
		System.out.println("★MyFilter doFilter start");
		chain.doFilter(request, response);
		System.out.println("★MyFilter doFilter end");
	}

	@Override
	public void destroy() {
		// 終了時に実行される
		System.out.println("★MyFilter destroy");
	}
}
