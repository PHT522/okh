<%@page import="org.apache.jasper.tagplugins.jstl.core.Remove"%>
<%@page import="user.UserDto"%>
<%@page import="qna.QnaDto"%>
<%@page import="qna.QnaService"%>
<%@page import="qna.QnaServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>qnabbsdetail.jsp</title>
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


</head>
<body>


<%
// 로그인한 정보
Object ologin = session.getAttribute("login");
UserDto mem = (UserDto)ologin;
%>

<%--
String sseq = request.getParameter("seq").trim();
int seq = Integer.parseInt(sseq);


QnaServiceImpl service = QnaService.getInstance();
QnaDto bbs = service.getBbs(seq);

//UserDto mem = (UserDto)session.getAttribute("login");

/* String str = service.RemoveHTMLTag(bbs.getContent());

String content = str.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
System.out.println("str:"+content); */
--%>

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
	<td >답변수:<%=bbs.getAnswercount() %>/조회수:<%=bbs.getReadcount() %></td>
</tr>
<tr>
	<td colspan="2">번호:<%=bbs.getRef() %></td>
	<td rowspan="3">좋아요 구현</td>
</tr>
<tr>
	<td colspan="2">
		<input type="text" name="tAg" id="tAg" data-role="tagsinput" value="<%=bbs.getTag() %>" ><br><br>
	</td>	
</tr>
<tr>
	<td colspan="2" >
		<%-- <textarea rows="10" cols="50" name="cOntent" id="summernote" >
			<%=bbs.getContent() %>
		</textarea>		 --%>
		<%-- <textarea  rows="30" cols="50" id="summernote" readonly="readonly" style="width: 100%;" >		
		<%=bbs.getContent() %> 
		</textarea> --%>		
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
<div>
<form action="">
<table border="1" align="center">
<col width="500"><col width="150">
<tr>
	<td>
		<input type="hidden" name="iD" value="<%=mem.getId()%>">
		
	</td>
</tr>
<tr>
	<td> 이글에 대한답변 수 : <%=bbs.getAnswercount() %> </td>
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