package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
		Cookie[] cookies = ((HttpServletRequest)request).getCookies();	//cookie情報取り出し
		String customerSessionId="";
		if (cookies != null){
			for (int i = 0 ; i < cookies.length ; i++){	//cookieの件数分繰り返す
				System.out.println(cookies[i].getName()+"、");
				if ( cookies[i].getName().equals("customerSessionId") ){	//クッキー名が「customerSessionId」の場合
					customerSessionId = cookies[i].getValue();	//値を取り出す
				}
			}
		}
		System.out.println("★MyFilter2r （顧客用フィルタ）  doFilter start customerService:"+customerService);
		System.out.println("★MyFilter2r （顧客用フィルタ）  doFilter start customerSessionId（cookie）:"+customerSessionId);
		if ( customerSessionId.equals("")==false ) {	//cookie内にセッションIDが入っている場合
			Customer c = customerService.findBySession( customerSessionId );			
			if( c==null ) {	//CUSTOMERテーブルにcooieのセッションIDと一致する顧客が無ければ
				RequestDispatcher dispatch = request.getRequestDispatcher("/customerLogin");	//ログイン画面へフォワード
				dispatch.forward( request, response );
			}
			System.out.println("★MyFilter2r （顧客用フィルタ）  doFilter start customerSessionId（DB）:"+c.getSessionId());
		}else {	//cookie内にセッションIDが入っていなかった場合
			RequestDispatcher dispatch = request.getRequestDispatcher("/customerLogin");	//ログイン画面へフォワード
			dispatch.forward( request, response );
		}
		
		
		chain.doFilter(request, response);
		
		System.out.println("★MyFilter2 （顧客用フィルタ）  doFilter end");
	}

	@Override
	public void destroy() {
		// 終了時に実行される
		System.out.println("★MyFilter destroy");
	}
}
