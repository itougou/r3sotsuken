package com.example.demo.service;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Purchase;
import com.example.demo.repository.PurchaseMapper;
/**
 * 注文情報 Service　（処理ロジック）　クラス
 */
@Service
public class PurchaseServiceImpl implements PurchaseService{
    /**
     * 注文情報 サービス　クラス
     */
    @Autowired
    private PurchaseMapper purchaseMapper;

    //データ1件追加
    @Override
    public void add( Purchase purchase ) {
        purchaseMapper.add( purchase );
    }
    
    //全件読み出し
    @Override
    public List<Purchase> getPurchase(){
    	return purchaseMapper.findMany();
    }

    //1件検索
    @Override
    public Purchase getPurchaseOne( Date purchaseDate, int userId ){
    	return purchaseMapper.findByDateId( purchaseDate, userId );
    }
}