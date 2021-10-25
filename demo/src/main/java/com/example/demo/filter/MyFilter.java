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
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.example.demo.dto.LoginRequest;

@Component
// スタッフ用のフィルタ （パスとの関連付は、config/JavaConfig.java）
public class MyFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 起動時に実行される
		System.out.println("★MyFilter （スタッフ用フィルタ） init");
	}
	 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// リクエスト発行時に実行される
		System.out.println("★MyFilter （スタッフ用フィルタ） doFilter start");
		HttpSession session = ((HttpServletRequest)request).getSession(false);
		System.out.println("★MyFilter （スタッフ用フィルタ） doFilter session:"+session);
		if( session == null ) {	//セッションスコープが入っていなければ
			RequestDispatcher dispatch = request.getRequestDispatcher("/staffLogin");	//ログイン画面へフォワード
			dispatch.forward(request, response);
		}else {
			LoginRequest login = (LoginRequest)session.getAttribute( "staffloginInfo" );	//セッションスコープ取り出し
			System.out.println("★MyFilter （スタッフ用フィルタ） doFilter start SESISON login="+login);
			if(login==null) {	//セッションスコープにスタッフのログイン情報が入っていない場合
				RequestDispatcher dispatch = request.getRequestDispatcher("/staffLogin");	//ログイン画面へフォワード
				dispatch.forward(request, response);
			}
		}
		
		
		chain.doFilter(request, response);
		
		System.out.println("★MyFilter （スタッフ用フィルタ） doFilter end");
	}

	@Override
	public void destroy() {
		// 終了時に実行される
		System.out.println("★MyFilter destroy");
	}
}
