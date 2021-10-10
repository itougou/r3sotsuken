package com.example.demo.service;
import java.util.List;

import com.example.demo.dto.CustomerSearchRequest;
import com.example.demo.entity.Customer;
/**
 * 顧客情報 サービス　クラス　用インターフェース
 */
public interface CustomerService {

    Customer search(CustomerSearchRequest customer);
    void add(Customer customer);
    List<Customer> getCustomer();
}