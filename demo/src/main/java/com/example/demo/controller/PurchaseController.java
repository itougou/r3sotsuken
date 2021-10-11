package com.example.demo.controller;
import java.util.List;

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
  CustomerService customerService;
  @Autowired
  PurchaseService purchaseService;
  @Autowired
  ModelMapper modelMapper;
  
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
  @GetMapping(value = "/purchase/add")
  public String displayAdd(Model model) {
	List<Customer> customer = customerService.getCustomer();
	model.addAttribute("customerlist", customer);
    return "purchase/add";
  }


  /*
   * Post へのマッピング
   */
  @RequestMapping(value = "/purchase/add", method = RequestMethod.POST)
  public String add(@ModelAttribute PurchaseAddRequest purchaseAddRequest, Model model) {
	Purchase purchase = modelMapper.map( purchaseAddRequest, Purchase.class );	//リクエスト受け渡しデータからPURCHASEエンティティへデータ変換
    purchaseService.add( purchase ) ;
    
    //System.out.println("★★★★Customer-ID＝"+purchaseAddRequest.getCustomerId());
    Purchase p = purchaseService.getPurchaseOne( purchaseAddRequest.getPurchaseDate(), purchaseAddRequest.getCustomerId() ) ;
    											//登録完了したデータ（顧客名も含んだデータ）を読み出す
    //System.out.println("★★★★Purchase＝"+p);
	model.addAttribute( "purchaseData", p );	//登録完了したデータを表示用にaddResult.htmlへ渡す
    return "purchase/addResult";	//フォワード
  }
}