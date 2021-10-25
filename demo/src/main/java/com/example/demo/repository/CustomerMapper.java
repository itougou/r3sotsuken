package com.example.demo.repository;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Customer;
/**
 * 顧客情報 Mapper  （CUSTOMERテーブルのアクセスとJavaのロジックを結びつけるクラス）
 */
@Mapper
public interface CustomerMapper {

    //Customer search(CustomerSearchRequest customer);
    Customer searchById( int id );
    Customer searchByIdPass( int id, String pass );
    Customer searchByIdPassAuthCode( int id, String pass, String auth );
    Customer findBySession(String sessionId);
    void add(Customer customer);
    List<Customer> findMany();
    boolean setSession( int id, String sessionId );
    boolean removeSession( String sessionId );
    boolean setAuthCode( int id, String authCode );
    boolean setAuthTime( int id, Date authTime );
}