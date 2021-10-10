package com.example.demo.repository;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Purchase;
/**
 * 注文情報 Mapper  （PURCHASEテーブルのアクセスとJavaのロジックを結びつけるクラス）
 */
@Mapper
public interface PurchaseMapper {

    void add( Purchase purchase );
    List<Purchase> findMany();
    Purchase findByDateId( Date purchaseDate, int customerId );
}