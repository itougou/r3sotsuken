package com.example.demo.dto;
import java.io.Serializable;

import lombok.Data;
/**
 * ユーザー情報 検索フォームからのリクエストパラメータの入れ物
 */
@Data
public class CustomerSearchRequest implements Serializable {
  /**
   * ユーザーID
   */
  private int id;
}