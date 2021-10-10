package com.example.demo.dto;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * 注文フォームからの　リクエストパラメータの入れ物
 */
@Data
public class PurchaseAddRequest implements Serializable {
	  private Date purchaseDate;
	  private int customerId;
	  private int suu;
		  
}