<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String ky = (String)request.getAttribute("key_word");
System.out.println("key_word="+ky);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アドレス帳一覧</title>
</head>
<body>
<h1><a href="/AddressBook/AddressBookController">アドレス帳一覧</a></h1>
<p><a href="/AddressBook/AddressBookController?action=form">登録画面</a></p>

<p>
<form action="/AddressBook/AddressBookController?action=search" method="post">
<input type="text" name="key_word" value="<c:out value="${requestScope.key_word}" />" >
<input type="submit" value="検索">
</form>
</p>

<table border="1">
<tr><th style="width:150px">名　前</th><th style="width:200px">住　所</th><th style="width:200px">電話番号</th></tr>

<c:forEach var="a" items="${requestScope.addressList}">
<tr><td>${a.name}</td><td>${a.address}</td><td>${a.tel}</td></tr>
</c:forEach>

</table>
</body>
</html>