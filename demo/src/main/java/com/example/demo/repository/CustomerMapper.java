package com.example.demo.repository;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.CustomerSearchRequest;
import com.example.demo.entity.Customer;
/**
 * 顧客情報 Mapper  （CUSTOMERテーブルのアクセスとJavaのロジックを結びつけるクラス）
 */
@Mapper
public interface CustomerMapper {

    Customer search(CustomerSearchRequest customer);
    void add(Customer customer);
    List<Customer> findMany();
}