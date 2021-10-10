package com.example.demo.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CustomerSearchRequest;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerMapper;
/**
 * 顧客情報 Service　（処理ロジック）　クラス　
 */
@Service
public class CustomerServiceImpl implements CustomerService{
    /**
     * 顧客情報 サービス　クラス
     */
    @Autowired
    private CustomerMapper customerMapper;

    //検索
    @Override
    public Customer search( CustomerSearchRequest customerSearchRequest) {
        return customerMapper.search( customerSearchRequest );
    }
    
    //データ1件追加
    @Override
    public void add( Customer customerAddRequest ) {
    	customerMapper.add( customerAddRequest );
    }
    
    //全件読み出し
    @Override
    public List<Customer> getCustomer(){
    	return customerMapper.findMany();
    }
}