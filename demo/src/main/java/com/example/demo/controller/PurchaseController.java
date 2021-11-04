package com.example.demo.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dto.PurchaseAddRequest;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Purchase;
import com.example.demo.service.CustomerService;
import com.example.demo.service.PurchaseService;
/**
 * 注文情報 Controller 　　　　
 */
@Controller
public class PurchaseController {
  /*
   * Service
   */
  @Autowired
  CustomerService customerService;	//顧客情報サービス
  
  @Autowired
  PurchaseService purchaseService;	//注文情報サービス
  
  @Autowired
  ModelMapper modelMapper;	//モデルマッパー
  
  /*
   * Get へのマッピング
   */
  @GetMapping(value = "/purchase")
  public String top(Model model) {
    return "purchase/index";
  }
  
  @GetMapping(value = "/purchase/list")
  public String displaySearchAll(Model model) {
	List<Purchase> purchase = purchaseService.getPurchase();
	model.addAttribute("purchaselist", purchase);
    return "purchase/list";
  }
  
  @GetMapping(value = "/customer/purchase/add")
  public String displayAdd(Model model, HttpServletRequest request) {
	HttpSession session = request.getSession(false);	//セッションスコープ取り出し
	if( session == null) {	//セッションスコープなしの場合
		return "login/customerLogin";	//ログイン画面へ遷移
	}else {	//セッションスコープありの場合
		Customer c = (Customer)session.getAttribute("loginCustomer");//ｾｯｼｮﾝｽｺｰﾌﾟからログイン済み顧客情報取り出し
		model.addAttribute("loginCustomer", c);	//add.htmlへ渡す顧客情報をセット
		List<Customer> customer = customerService.getCustomer();	//顧客名プルダウン表示用に顧客情報を読み出す
		model.addAttribute("customerlist", customer);	//add.htmlへ渡す顧客情報をセット
		return "purchase/add";	//注文画面へ遷移
	}
  }


  /*
   * Post へのマッピング
   */
  @RequestMapping(value = "/purchase/add", method = RequestMethod.POST)
  public String add(@ModelAttribute PurchaseAddRequest purchaseAddRequest, Model model) {
	Purchase purchase = modelMapper.map( purchaseAddRequest, Purchase.class );	//フォームクラスからPURCHASEエンティティへデータ変換
    purchaseService.add( purchase ) ;	//注文情報1件追加処理ロジック
    
    //System.out.println("★★★★Customer-ID＝"+purchaseAddRequest.getCustomerId());
    Purchase p = purchaseService.getPurchaseOne( purchaseAddRequest.getPurchaseDate(), purchaseAddRequest.getCustomerId() ) ;
    											//登録完了したデータ（顧客名も含んだデータ）を読み出す
    //System.out.println("★★★★Purchase＝"+p);
	model.addAttribute( "purchaseData", p );	//登録完了したデータを表示用にaddResult.htmlへ渡す
    return "purchase/addResult";	//フォワード
  }
}