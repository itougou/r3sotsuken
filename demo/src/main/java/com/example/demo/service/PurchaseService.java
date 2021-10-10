package com.example.demo.service;
import java.util.Date;
import java.util.List;

import com.example.demo.entity.Purchase;
/**
 * 注文情報 サービス　クラス　用　インターフェース
 */
public interface PurchaseService {

    void add(Purchase purchase);
    List<Purchase> getPurchase();
    Purchase getPurchaseOne( Date purchaseDate, int customerId );
}