package com.example.demo.entity;
import lombok.Data;
/**
 * スタッフ情報 Entity　（STAFFテーブルの1件分のデータの入れ物）
 */
@Data
public class Staff {
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

}