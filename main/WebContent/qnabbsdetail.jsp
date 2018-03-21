<%@page import="qna.PagingBean"%>
<%@page import="java.util.List"%>
<%@page import="qna.QnaAnswerDto"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.Remove"%>
<%@page import="user.UserDto"%>
<%@page import="qna.QnaDto"%>
<%@page import="qna.QnaService"%>
<%@page import="qna.QnaServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<% request.setCharacterEncoding("utf-8");%>
<fmt:requestEncoding value="utf-8" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>qnabbsdetail.jsp</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<!-- include summernote libraries(jQuery, bootstrap) -->
	<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 

	<!-- include summernote css/js-->
	<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script> 
	
	<!-- 태그 이용하기위한-->
	<link rel="stylesheet" href="css/bootstrap-tagsinput.css">
	<script src="js/bootstrap-tagsinput.js"></script>
	
	<!-- include summernote-ko-KR -->
	<script src="lang/summernote-ko-KR.js"></script>
	
<link rel="stylesheet" type="text/css" href="_detail.css?ver=1.44">

<link rel="stylesheet" type="text/css" href="_main.css?ver=1.31">
		

</head>
<body>
<script type="text/javascript">
function change() {		//좋아요취소		-> 		싫어요활성화
	$("#likeimg").attr('src','image/likeoff.PNG');
}
function change1() {	//좋아요할래요		->		싫어요비활성화
	$("#likeimg").attr('src','image/likeon.PNG');
	$("#changedisli").css({ 'pointer-events': 'none' });
}
function change2() {	//싫어요취소		->		좋아요활성화
	$("#dislikeimg").attr('src','image/dislikeoff.PNG');
}
function change3() {	//싫어요할래요		->		좋아요비활성화
	$("#dislikeimg").attr('src','image/dislikeon.PNG');
	$("#changeli").css({ 'pointer-events': 'none' });
}
var myVar;
$(function() {
	$("#repcancel").click(function() {
		myVar=setTimeout(_refresh,1000);
	});
});
function _refresh() {
	location.reload;		
	clearTimeout(myVar);
}
</script>
<%
// 로그인한 정보
Object ologin = session.getAttribute("login");
UserDto mem = (UserDto)ologin;
%>



<%
QnaDto bbs = (QnaDto)session.getAttribute("detailDto");

%>





<div align="center">
<h3>여기는 qnabbsdetail.jsp</h3>
</div>

<div class="border" align="center">

<table border="1">
<col width="100"><col width="400"><col width="150">
<tr>
	<td colspan="2" >작성자: <%=bbs.getId() %> </td>
	<td >답변수:<%=bbs.getCommentcount() %>/조회수:<%=bbs.getReadcount() %></td>
</tr>
<tr>
	<td colspan="2">번호:<%= %></td>
	<td rowspan="3">좋아요 구현</td>
</tr>
<tr>
	<td colspan="2">
		<input type="text" name="tAg" id="tAg" data-role="tagsinput" value="<%=bbs.getTagname() %>" ><br><br>
	</td>	
</tr>
<tr>
	<td colspan="2" >
		<textarea rows="10" cols="50" name="cOntent" id="summernote" >
			<%=bbs.getContent() %>
		</textarea>		
		<textarea  rows="30" cols="50" id="summernote" readonly="readonly" style="width: 100%;" >		
		<%=bbs.getContent() %> 
		</textarea>		
		<br><br><br>
		<article>
		<%=bbs.getContent() %>
		</article> 
	</td>
</tr>
</table> 

<% if(bbs.getId().equals(mem.getId())){ %>
<button type="button" 
	onclick="updatebbs('<%=bbs.getSeq() %>')">수정</button>
<button type="button"
	onclick="deletebbs('<%=bbs.getSeq() %>')">삭제</button>	
<%} %>

</div>
<br><br><br><br><br><br>

<!-- 답변 시작 -->
<%
//페이징 처리
PagingBean paging = new PagingBean();
if(request.getParameter("nowPage") == null){
	paging.setNowPage(1);		// 처음에는 1페이지로 셋팅
}else{
	paging.setNowPage(Integer.parseInt(request.getParameter("nowPage")));
}
QnaServiceImpl service = QnaService.getInstance();
List<QnaDto> qnalist = service.getBbsPagingList(paging);

%>

<!-- 리스트 보여주기 -->
<div align="center">
<table border="1">
<col width="500"><col width="150">
	
	
	<%
	if(qnalist == null || qnalist.size() == 0){
	
	%>	
	<tr>
		<td colspan="5">작성된 글이 없습니다.</td>
	</tr>
	<%
	}
	%>
	<tr>
		<td align="left">	</td>		
	</tr>
	
	<%
	for(int i = 0; i<qnalist.size(); i++){
		QnaDto qna = qnalist.get(i);	
		if(bbs.getSeq()==qna.getParent()){
	%>
	
	<tr>
		<td>
			답변작성자<%=qna.getId() %>
		</td>		
		
	</tr>	
	<tr>
		<td>
		<article>
			<%=qna.getContent() %>
		</article>
		</td>
	</tr>
	
	<%
	}}
	%>


</table>
</div>
<br><br>


<div id="comment">
<form action="qnaServlet">
<table border="1" align="center">
<col width="500"><col width="150">
<tr>
	<td>
		<input type="hidden" name="command" value="writeAnswer" >
		<input type="hidden" name="iD" value="<%=mem.getId() %>">	<!-- 답변 작성 아이디 -->		
		<input type="hidden" name="aNswerCount" value="<%=bbs.getCommentcount() %>"> <!-- 현재의 답변 카운트를 넘겨준다 -->
		<input type="hidden" name="seq" value="<%=bbs.getSeq() %>">
	</td>
</tr>
<tr>
	<td> 이글에 대한 답변수:<%=qnalist.get(qnalist.size()-1).getCommentcount() %> </td>
	<td rowspan="2"> 
		<input type="submit" value="등록">
	</td> 
</tr>
<tr>
	<td>
		<textarea id="summernote" name="cOntent"></textarea>
	</td>
</tr>	
</table>
</form>
</div>

<br><br><br><br><br><br>


<!-- 답변 끝 -->



<script type="text/javascript">
// 업데이트와 델리트를 위한 함수설정
function updatebbs( seq ) {
	//location.href = "qnaServlet?command=updateQna&seq=" +seq;
	location.href = "qnaServlet?command=qnaBbsDetail&action=update&seq="+seq;
}
function deletebbs(seq) {	
	// 추후 추가
}

</script>



<script type="text/javascript">
$(document).ready(function() {
    $('#summernote').summernote({
            height: 150,                 // set editor height
            minHeight: null,             // set minimum height of editor
            maxHeight: null,             // set maximum height of editor
            focus: true                  // set focus to editable area after initializing summernote
    });
    //$('#summernote').summernote('disable');
   // $(".note-editable").attr("contenteditable","false")
});
</script>



</body>
</html>