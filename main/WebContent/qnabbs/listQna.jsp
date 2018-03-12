<%@page import="java.util.ArrayList"%>
<%@page import="paging.dto.PagingBean"%>
<%@page import="user.UserDTO"%>
<%@page import="qna.Service.QnaService"%>
<%@page import="qna.Service.QnaServiceImpl"%>
<%@page import="qna.dao.qnaBbsDao"%>
<%@page import="qna.dao.qnaBbsDaoImpl"%>
<%@page import="java.util.List"%>
<%@page import="qna.dto.QnaDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
String findWord = request.getParameter("findWord"); 
String choice = request.getParameter("choice"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>listQna.jsp</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script> 
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/custom.css">
	<link rel="stylesheet" type="text/css" href="_main.css">
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap.js"></script>
</head>
<body>

	<div class="menu">
		<input type="button" class="login" id="login">
		<input type="button" class="account" id="account">
		<input type="button" class="bbs1" id="qnabbs">
		<input type="button" class="techbbs_hjh" id="techbbs">
		<input type="button" class="bbs3">
		<input type="button" class="bbs4">
		<input type="button" class="bbs5">
	</div>

<%

//로그인정보
Object ologin = session.getAttribute("login");
UserDTO mem = (UserDTO)ologin;
/* qnaBbsDaoImpl dao = qnaBbsDao.getInstance();
List<QnaDto> qnalist = dao.getQnaList(); */

QnaServiceImpl service = QnaService.getInstance();
List<QnaDto> qnalist = service.getQnaList();
%>

<%
PagingBean paging = new PagingBean();
if(request.getParameter("nowPage") == null){
	paging.setNowPage(1);		// 처음에는 1페이지로 셋팅
}else{
	paging.setNowPage(Integer.parseInt(request.getParameter("nowPage")));
}
%>

<%--
if(findWord == null){
	findWord = "";
}
int cho = 0;

if(choice == null) cho = 0;
else if(choice.equals("title")) cho = 0;
else if(choice.equals("writer")) cho = 1;

QnaServiceImpl service = QnaService.getInstance();

List<QnaDto> qnalist = service.getQnaPagingList(paging, findWord, cho);

--%>


<div align="center">

<h3>여기는 Q&A 게시판</h3>
<a href="./qnabbs/writeQna.jsp">새 글쓰기</a>
<!-- 
<button onclick="location.href='qnaAdd?command=addQna'">새 글쓰기</button>
-->

</div>
<br><br>

<div align="center">
<!-- 관리자 공지 위치 -->
<table border="1" >
<!-- (게시물번호)(태그)(좋아요)(답변수)(작성자정보) -->
<col width="50"><col width="300"><col width="50"><col width="50"><col width="100">
	<tr>
		<td>번호</td>
		<td>테그</td>
		<td rowspan="2">좋아요</td>
		<td rowspan="2">답변수</td>
		<td rowspan="2">작성자정보 </td>		
	</tr>
	<tr>
		<td colspan="2">게시물 타이틀</td>		
	</tr>
	
	
</table>
</div>
<br><br>
	


<!-- 실질적인 qna게시판  -->
<div align="center">
<table border="1">
<!-- (게시물번호)(태그)(좋아요)(답변수)(작성자정보) -->
<col width="50"><col width="300"><col width="50"><col width="50"><col width="100">
	<%
	if(qnalist == null || qnalist.size() == 0){
	
	%>	
	<tr>
		<td colspan="5">작성된 글이 없습니다.</td>
	</tr>
	<%
	}
	for(int i = 0; i<qnalist.size(); i++){
		QnaDto qna = qnalist.get(i);		
	%>
	
	<tr>
		<td>번호:<%=qna.getSeq() %></td>
		<td>테그:<%=qna.getTag() %></td>
		<td rowspan="2">좋아요:<%=qna.getFavor() %></td>
		<td rowspan="2">답변수:<%=qna.getReadcount() %></td>
		<td rowspan="2">작성자정보:<%=qna.getId() %> </td>		
	</tr>
	<tr>
		<td colspan="2">게시물 타이틀<%=qna.getTitle() %></td>		
	</tr>	
	
	<%
	}
	%>
	
	
</table>
</div>





</body>
</html>