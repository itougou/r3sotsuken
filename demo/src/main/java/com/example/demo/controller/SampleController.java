package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 動作確認用サンプル　Controller　
 */
@Controller
public class SampleController {

  /*
   * Get へのマッピング
   */
  
  //http://localhost:8080/sample/XXXX　の形式で実行
  @RequestMapping(path="/sample/{data}", method=RequestMethod.GET)
  public String read(@PathVariable String data, Model model) {
      System.out.println("GETでリクエストパラメータを受け取りました："+data);
      model.addAttribute("data", data);	//sample.htmlへ渡す情報をセット
      return "/sample/page";
  }
  
  //http://localhost:8080/sample?data=XXXX　の形式で実行
  @RequestMapping(path="/sample", method=RequestMethod.GET)
  public String read2( @RequestParam("data") String data, Model model ) {
      System.out.println("GETでリクエストパラメータを受け取りました："+data);
      model.addAttribute("data", data);	//sample.htmlへ渡す情報をセット
      return "/sample/page";
  }

}