package com.example.demo.entity;


import java.util.Date;

import lombok.Data;
/**
 * 注文データ Entity　（PURCHASEテーブルの1件分のデータの入れ物）
 */
@Data
public class Purchase {
    /**
     * ID
     */
    private Date purchaseDate;
    /**
     * 名前
     */
    private int customerId;
    /**
     * パスワード
     */
    private int suu;
    
    //ユーザー名（userテーブル）
    private Customer customer;
}