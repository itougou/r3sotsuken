package com.example.demo.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dto.LoginRequest;
/**
 * 　ログインController　
 */
import com.example.demo.service.CustomerService;
import com.example.demo.service.StaffService;
/**
 * ログイン Controller　
 */
@Controller
public class LoginController {
	  @Autowired
	  HttpSession session;  
	  
	  @Autowired
	  CustomerService customerService;	//　顧客情報 Service
	
	  @Autowired
	  StaffService staffService;	//　スタッフ情報 Service
	  /*
	   * Get へのマッピング
	   */
	  @GetMapping(value = "/customerLogin")
	  public String toLogin(Model model) {
	    return "login/customerLogin";
	  } 
	  @GetMapping(value = "/customerLogout")
	  public String toLogout(Model model) {
	    return "login/customerLogout";
	  } 
	  @GetMapping(value = "/staffLogin")
	  public String toStaffLogin(Model model) {
	    return "login/staffLogin";
	  } 
	  @GetMapping(value = "/staffLogout")
	  public String tostaffLogout(Model model) {
	    return "login/staffLogout";
	  } 
	  /*
	   * Post へのマッピング
	   */
	  //スタッフログイン
	  @RequestMapping( value = "/staffLogin", method = RequestMethod.POST )
	  public String doLogin( @ModelAttribute LoginRequest loginRequest, Model model ) {
		boolean result = staffService.login( loginRequest, model );	//staffログインロジック呼び出し
		if( result ) {	//ログイン成功時
		    model.addAttribute( "loginInfo", loginRequest );	//HTMLへ渡すログイン情報をセット
		    return "login/staffLoginResult";
		}else {	//ログイン失敗時
		    return "login/staffLogin";
		}
	  }
	  //スタッフログアウト
	  @RequestMapping( value = "/staffLogout", method = RequestMethod.POST )
	  public String doLogout( Model model ) {
		staffService.logout();	//staffログアウトロジック呼び出し
	    return "login/staffLogoutResult";
	  }
	  
	  //顧客ログイン
	  @RequestMapping( value = "/customerLogin", method = RequestMethod.POST )
	  public String doStaffLogin( @ModelAttribute LoginRequest loginRequest, HttpServletResponse response , Model model ) {

		boolean result = customerService.login( loginRequest, response );	//顧客ログイン（セッション登録処理）ロジック
		if( result ) {	//ログイン成功時
		    model.addAttribute( "loginInfo", loginRequest );	//HTMLへ渡すログイン情報をセット
		    return "login/customerLoginResult";
		}else {	//ログイン失敗時
		    return "login/customerLogin";
		}
	  }
	  //顧客ログアウト
	  @RequestMapping( value = "/customerLogout", method = RequestMethod.POST )
	  public String doStaffLogout( HttpServletRequest request, HttpServletResponse response, Model model ) {

	    customerService.logout( request, response );	//顧客ログアウト（セッション削除処理）ロジック
	    
	    return "login/customerLogoutResult";
	  }

}