<%@page import="user.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>writeQna.jsp</title>

</head>
<body>




<a href="logoutAction.jsp">로그아웃</a>

<h3>여기는 writeQna입니다.</h3>

<%
Object ologin = session.getAttribute("login");
UserDTO mem = (UserDTO)ologin;
%>



<div>
<form action="/main/qnaServlet" method="get">
<table>

<tr>현제 접속 아이디는:<%=mem.getId() %> </tr>

<tr>
	<td>
		<input type="hidden" name="command" value="writeQna">
		<input type="hidden" name="iD" value="<%=mem.getId() %>">
		
	</td>
</tr>

<tr>
	<td>
		<input type="text" name="tItle" placeholder="제목을 입력하세요">
	</td>	
</tr>

<tr>
	<td>
		<input type="text" name="tAg" placeholder="tag를 입력하세요">
	</td>
</tr>	

<tr>
	<td>
		<textarea rows="10" cols="50" name="cOntent" style="resize: none;"></textarea>
	</td>
</tr>

<tr>
	<td>
		<input type="reset" value="취소" onclick="location.href='/okh/qnaServlet?command=listQna'">
		<input type="submit" value="등록">
	</td>
</tr>

</table>
</form>
</div>




</body>
</html>