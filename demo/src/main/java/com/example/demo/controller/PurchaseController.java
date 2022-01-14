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
import org.springframework.web.bind.annotation.RequestParam;

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
  
  @Autowired
  HttpSession session;  
  
  
  /*
   * Get へのマッピング
   */
  @GetMapping(value = "/purchase")
  public String top(Model model) {
    return "purchase/index";
  }
  
  @GetMapping(value = "/purchase/list")
  public String displaySearchAll(Model model, HttpServletRequest request ) {
	  
	List<Purchase> purchase = purchaseService.getPurchase();
	model.addAttribute("purchaselist", purchase);
	
	//HttpSession session = request.getSession(false);	//セッションスコープ取り出し
	if( session == null) {	//セッションスコープなしの場合
		return "login/staffLogin";	//ログイン画面へ遷移
	}else {	//セッションスコープありの場合
		session.setAttribute("puechaseList",purchase );//ｾｯｼｮﾝｽｺｰﾌﾟへ注文リスト格納
		//session.setAttribute("puechaseListDummy","purchaseDummy" );//ｾｯｼｮﾝｽｺｰﾌﾟへ注文リスト格納
	}
	
    return "purchase/list";
  }
  

  @GetMapping(value = "/customer/purchase/add")
  public String displayAdd(Model model, HttpServletRequest request) {
	//HttpSession session = request.getSession(false);	//セッションスコープ取り出し
	if( session == null) {	//セッションスコープなしの場合
		return "login/staffLogin";	//ログイン画面へ遷移
	}else {	//セッションスコープありの場合
		Customer c = (Customer)session.getAttribute("loginCustomer");//ｾｯｼｮﾝｽｺｰﾌﾟからログイン済み顧客情報取り出し
		model.addAttribute("loginCustomer", c);	//add.htmlへ渡す顧客情報をセット
		List<Customer> customer = customerService.getCustomer();	//顧客名プルダウン表示用に顧客情報を読み出す
		model.addAttribute("customerlist", customer);	//add.htmlへ渡す顧客情報をセット
		return "purchase/add";	//注文画面へ遷移
	}
  }
  @GetMapping(value = "/purchase/edit")
  public String displayEdit(Model model, HttpServletRequest request,@RequestParam("index") String index) {
	//HttpSession session = request.getSession(false);	//セッションスコープ取り出し
	if( session == null) {	//セッションスコープなしの場合
		return "login/staffLogin";	//ログイン画面へ遷移
	}else {	//セッションスコープありの場合
		//System.out.println("★session staffloginInfo :"+(LoginRequest)session.getAttribute( "staffloginInfo" ));
		//System.out.println("★session purchaseListDummy :"+session.getAttribute( "puechaseListDummy" ));
		
		@SuppressWarnings("unchecked")
		List<Purchase> purchaseList = (List<Purchase>)session.getAttribute("puechaseList");//ｾｯｼｮﾝｽｺｰﾌﾟから注文リスト情報取り出し
		//System.out.println("★session purchaseList :"+purchaseList);
		model.addAttribute("purchase", purchaseList.get( Integer.parseInt(index) ) );	//edit.htmlへ渡す注文情報をセット
		
		Customer c = customerService.searchById( purchaseList.get( Integer.parseInt(index) ).getCustomerId() );
		model.addAttribute("customer", c);	//add.htmlへ渡す顧客情報をセット
		
		return "purchase/edit";	//注文編集画面へ遷移
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
  
  @RequestMapping(value = "/purchase/edit", method = RequestMethod.POST)
  public String edit(@ModelAttribute PurchaseAddRequest purchaseAddRequest, Model model) {
	Purchase purchase = modelMapper.map( purchaseAddRequest, Purchase.class );	//フォームクラスからPURCHASEエンティティへデータ変換
    purchaseService.edit( purchase ) ;	//注文情報1件変更処理ロジック
    
    //System.out.println("★★★★Customer-ID＝"+purchaseAddRequest.getCustomerId()+"★★★★PurchaseDate＝"+purchaseAddRequest.getPurchaseDate());
    Purchase p = purchaseService.getPurchaseOne( purchaseAddRequest.getPurchaseDate(), purchaseAddRequest.getCustomerId() ) ;
    											//登録完了したデータ（顧客名も含んだデータ）を読み出す
    //System.out.println("★★★★Purchase＝"+p);
	model.addAttribute( "purchaseData", p );	//登録完了したデータを表示用にeditResult.htmlへ渡す
    return "purchase/editResult";	//フォワード
  }
}