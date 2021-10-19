package com.example.demo.dto;
import java.io.Serializable;

import lombok.Data;
/**
 * 認証コードフォームからの　リクエストパラメータの入れ物
 */
@Data
public class AuthRequest implements Serializable {
	  private String id;
	  private String pass;
	  private String authCode;
}