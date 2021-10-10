package com.example.demo.dto;
import java.io.Serializable;

import lombok.Data;
/**
 * 顧客情報追加フォームからの　リクエストパラメータの入れ物
 */
@Data
public class CustomerAddRequest implements Serializable {
  /**
   * ユーザー名
   */
	  //private Long id;
	  private String name;
}