package com.example.demo.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.Customer;
/**
 * 顧客情報 サービス　クラス　用インターフェース
 */
public interface CustomerService {

    //Customer search(CustomerSearchRequest customer);
    Customer searchById( int id );
    Customer findBySession(String sessionid);
    void add(Customer customer);
    List<Customer> getCustomer();
    Customer cookieCheck( HttpServletRequest request );
    boolean login( LoginRequest loginRequest , HttpServletRequest request, HttpServletResponse response );
    boolean logout( HttpServletRequest request, HttpServletResponse response );
    boolean authCheck( AuthRequest authRequest, HttpServletRequest request, HttpServletResponse response );
}