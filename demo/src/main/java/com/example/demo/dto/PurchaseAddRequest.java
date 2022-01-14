package com.example.demo.dto;
import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
/**
 * 注文フォームからの　リクエストパラメータの入れ物
 */
@Data
public class PurchaseAddRequest implements Serializable {
	// This is "org.springframework.format.annotation.DateTimeFormat"
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date purchaseDate;
	private int customerId;
	private int suu;
		  
}