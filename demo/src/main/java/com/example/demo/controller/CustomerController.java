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

import com.example.demo.dto.CustomerAddRequest;
import com.example.demo.dto.CustomerSearchRequest;
import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;
/**
 * 顧客情報 Controller　
 */
@Controller
public class CustomerController {
  /**
   * ユーザー情報 Service
   */
	  @Autowired
	  CustomerService customerService;
	  
	  @Autowired
	  ModelMapper modelMapper;
  
  /*
   * Get へのマッピング
   */
  @GetMapping(value = "/customer/search")
  public String displaySearch(Model model) {
    return "customer/search";
  }
  
  @GetMapping(value = "/customer/list")
  public String displaySearchAll(Model model) {
	//System.out.println("customerService="+customerService);
	List<Customer> customer = customerService.getCustomer();	//プルダウン表示用に顧客情報を読み出す
	model.addAttribute("customerlist", customer);	//list.htmlへ顧客情報を渡す
    return "customer/list";
  }
  
  @GetMapping(value = "/customer/add")
  public String displayAdd(Model model) {
    return "customer/add";
  }

  /*
   * Post へのマッピング
   */
  @RequestMapping(value = "/customer/id_search", method = RequestMethod.POST)
  public String search(@ModelAttribute CustomerSearchRequest customerSearchRequest, Model model) {
	Customer customer = customerService.search(customerSearchRequest);
    model.addAttribute("customerinfo", customer);
    return "customer/search";
  }
  @RequestMapping(value = "/customer/add", method = RequestMethod.POST)
  public String add(@ModelAttribute CustomerAddRequest customerAddRequest, Model model) {
	Customer customer = modelMapper.map( customerAddRequest, Customer.class );
	customerService.add(customer);
    
	model.addAttribute("customerData", customer );
	 return "customer/addResult"; 	//フォワード
    
  }
}