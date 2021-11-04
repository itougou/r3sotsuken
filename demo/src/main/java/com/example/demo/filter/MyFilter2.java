package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;

@Component
//顧客用のフィルタ （パスとの関連付は、config/JavaConfig.java）
public class MyFilter2 implements Filter {
	
	//　顧客情報 Service
	@Autowired
	//@Qualifier("customerService")
	CustomerService customerService;
	  
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 起動時に実行される
		System.out.println("★MyFilter2 （顧客用フィルタ） init");
	}
	 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// リクエスト発行時に実行される
		System.out.println("★MyFilter2 （顧客用フィルタ）  doFilter start");

		Customer c  = customerService.cookieCheck( (HttpServletRequest)request, (HttpServletResponse)response );	//クッキーチェック処理
		System.out.println("★MyFilter2 （顧客用フィルタ）  doFilter customer="+c);
		if(c==null) {//cookie内にセッションIDが入っていなかった場合
			RequestDispatcher dispatch = request.getRequestDispatcher("/customerLogin");	//ログイン画面へフォワード
			dispatch.forward( request, response );
			return;
		}
		HttpSession session = ((HttpServletRequest)request).getSession(true);//セッションスコープ新規生成
		session.setAttribute("loginCustomer", c);	//ログイン中の顧客情報をセット
		
		chain.doFilter(request, response);
		
		System.out.println("★MyFilter2 （顧客用フィルタ）  doFilter end");
	}

	@Override
	public void destroy() {
		// 終了時に実行される
		System.out.println("★MyFilter destroy");
	}
}
