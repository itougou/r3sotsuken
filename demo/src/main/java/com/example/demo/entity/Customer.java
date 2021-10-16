package com.example.demo.entity;
import java.util.Date;

import lombok.Data;
/**
 * 顧客情報 Entity　（CUSTOMERテーブルの1件分のデータの入れ物）
 */
@Data
public class Customer {
    /**
     * ID
     */
    private Long id;
    /**
     * 名前
     */
    private String name;
    /**
     * パスワード
     */
    private String pass;
    /**
     * kubun
     */
    private int kubun;
    /**
     * 住所
     */
    private String address;
    /**
     * 電話番号
     */
    private String phone;
    /**
     * 更新日時
     */
    private Date updateDate;
    /**
     * 登録日時
     */
    private Date createDate;
    /**
     * 削除日時
     */
    private Date deleteDate;
    /**
     * セッションID
     */
    private String sessionId;
}