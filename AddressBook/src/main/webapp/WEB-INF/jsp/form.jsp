<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アドレス帳入力フォーム</title>
</head>
<body>
<h1>アドレス帳入力フォーム</h1>
<form action="/AddressBook/AddressBookController" method="POST">
名　　前：<input type="text" name="name"><br>
住　　所：<input type="text" name="address"><br>
電話番号：<input type="text" name="tel"><br>
<input type="hidden" name="key_word" value="">
<input type="submit" value="登録"><br>
</form>
</body>
</html>