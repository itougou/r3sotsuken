package com.example.demo.controller;
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
 * 動作確認用サンプル　Controller　
 */
@Controller
public class LoginController {
	  @Autowired
	  HttpSession session;  
	  /*
	   * Get へのマッピング
	   */
	  @GetMapping(value = "/login")
	  public String toLogin(Model model) {
	    return "login/login";
	  } 
	  @GetMapping(value = "/logout")
	  public String toLogout(Model model) {
	    return "login/logout";
	  } 
	  
	  /*
	   * Post へのマッピング
	   */
	  @RequestMapping( value = "/login", method = RequestMethod.POST )
	  public String doLogin( @ModelAttribute LoginRequest loginRequest, Model model ) {
		session.setAttribute( "loginInfo", loginRequest );
	    model.addAttribute( "loginInfo", loginRequest );
	    return "login/loginResult";
	  }
	  @RequestMapping( value = "/logout", method = RequestMethod.POST )
	  public String doLogout( Model model ) {
		session.invalidate();
	    return "login/logoutResult";
	  }

}