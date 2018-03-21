
<%-- 
<%@page import="qna.PagingBean"%>
<%@page import="qna.QnaDto"%>
<%@page import="qna.QnaService"%>
<%@page import="qna.QnaServiceImpl"%>
<%@page import="user.UserDto"%>
<%@page import="java.util.List"%>

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
	<!-- <link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/custom.css">
	<link rel="stylesheet" type="text/css" href="_main.css">
	
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap.js"></script> -->
<style type="text/css">
table {
	width: 100%;
	border: 1px solid #444444;
}
tr,td {
	border: 1px ;
	
}
</style>
	
</head>
<body>

<div class="menu">
<jsp:include page="menuinclude.jsp">
<jsp:param name="actionPath" value="index.jsp"/>
</jsp:include>
</div>



<!-- //로그인정보
Object ologin = session.getAttribute("login");

UserDto mem = (UserDto)ologin;
/* qnaBbsDaoImpl dao = qnaBbsDao.getInstance();
List<QnaDto> qnalist = dao.getQnaList(); */


//QnaServiceImpl service = QnaService.getInstance();
//List<QnaDto> qnalist = service.getQnaList(); -->


<%
//페이징 처리
PagingBean paging = new PagingBean();
if(request.getParameter("nowPage") == null){
	paging.setNowPage(1);		// 처음에는 1페이지로 셋팅
}else{
	paging.setNowPage(Integer.parseInt(request.getParameter("nowPage")));
}
%>


<!-- // 리스트 뿌려주기위해
QnaServiceImpl service = QnaService.getInstance();
List<QnaDto> qnalist = service.getBbsPagingList(paging); -->




<%
if(findWord == null){
	findWord = "";
}
int cho = 0;

if(choice == null) cho = 0;
else if(choice.equals("title")) cho = 0;
else if(choice.equals("writer")) cho = 1;

QnaServiceImpl service = QnaService.getInstance();
List<QnaDto> qnalist = service.getQnaPagingList(paging, findWord, cho);

%>
<div class="wrap">

<div align="center">

<h3>여기는 Q&A 게시판</h3>
<a href="qnabbswrite.jsp">새 글쓰기</a>
<!-- 
<button onclick="location.href='qnaAdd?command=addQna'">새 글쓰기</button>
-->

</div>
<br><br>

<div align="center">
<!-- 관리자 공지 위치 -->
<table>
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
<table >
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
		<td>번호 :<%=i+1 %></td>
		<td>테그 : <%=qna.getTagname() %></td>
		<td rowspan="2" style="border-bottom: 1px solid #444444; ">좋아요 : <%=qna.getLikecount() %></td>
		<td rowspan="2" style="border-bottom: 1px solid #444444; ">답변수 : <%=qna.getCommentcount() %></td>
		<td rowspan="2" style="border-bottom: 1px solid #444444; ">작성자정보 : <%=qna.getId() %> </td>		
	</tr>
	<tr>
		<td colspan="2" style="border-bottom: 1px solid #444444; ">
			게시물 타이틀 :			
			<a href="qnabbsdetail.jsp?seq=<%=qna.getSeq() %>"> 
			<a href="qnaServlet?command=qnaBbsDetail&seq=<%=qna.getSeq() %>&action=detail">
			 <%=qna.getTitle() %>
			</a>
		</td>		
	</tr>	
	
	<%
	}
	%>
	
	
</table>
 
<jsp:include page="paging.jsp">
	<jsp:param name="actionPath" value="qnabbslist.jsp"/>
	<jsp:param name="nowPage" value="<%=String.valueOf(paging.getNowPage()) %>" />
	<jsp:param name="totalCount" value="<%=String.valueOf(paging.getTotalCount()) %>" />
	<jsp:param name="countPerPage" value="<%=String.valueOf(paging.getCountPerPage()) %>" />
	<jsp:param name="blockCount" value="<%=String.valueOf(paging.getBlockCount()) %>" />
</jsp:include> 


</div>

</div>



</body>
</html>  --%>

 
<%@page import="qna.PagingBean"%>
<%@page import="qna.QnaDto"%>
<%@page import="qna.QnaService"%>
<%@page import="qna.QnaServiceImpl"%>
<%@page import="user.UserDto"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("utf-8");
String findWord = request.getParameter("findWord"); 
String choice = request.getParameter("choice"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>listQna.jsp</title>
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script> 
	<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 
	<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script>
		
	
	<!-- 폰트  -->
	<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="_techbbs.css?ver=1.62">
	<link rel="stylesheet" type="text/css" href="_main.css?ver=1.3">
	
		<!-- 태그 이용하기위한-->
	<link rel="stylesheet" href="css/bootstrap-tagsinput.css">
	<script src="js/bootstrap-tagsinput.js"></script>
		
</head>
<body bgcolor="#fcfbfb">
<%
//로그인정보
Object ologin = session.getAttribute("login");
UserDto mem = null;
List<QnaDto> qnalist = (List<QnaDto>)request.getAttribute("listQna");
mem = (UserDto)ologin;
%>
<!-- 인클루드 부분 -->
	<div class="menu">
		<%
		if(ologin == null){	
			%>
			<input type="button" class="homebtn" onclick="location.href='index.jsp'">
			<input type="button" class="login" id="login">
			<input type="button" class="account" id="account">

				<%
		}else{
			
		%>
		<input type="button" class="homebtn" id="homebtn">
		<div class="actionlogin">
			<span><%=mem.getId() %></span>
			<img class="settingbtn" alt="" src="image/mainsetting.PNG" style="cursor: pointer" id="btnPopover">
			<img class="alarmbtn" alt="" src="image/alarm.PNG" style="cursor: pointer" id="btnPopover">	
		</div>
		<%
		}
		
		%>
		<input type="button" class="bbs1" id="qnabbs">
		<input type="button" class="techbbs_hjh" id="techbbs">
		<input type="button" class="bbs3" ><!-- 정재흥 -->
		<input type="button" class="bbs4" >
		<input type="button" class="bbs5" id="jobs"><!-- 나효진 -->
		<input type="button" class="bbs6" id="life"><!-- 병찬 사는얘기 -->
	</div>	<!-- .menu end -->

	
	<script type="text/javascript">
		$(function() {//좌측 메뉴바 누르는 곳.

			$("#login").click(function() {
				location.href="User?command=login";
			});
	
			$("#account").click(function() {
				location.href="User?command=join";
			});
			
			//QNA
			$("#qnabbs").click(function() {
				location.href="qnaServlet?command=listQna";
			});
			
			$("#second").click(function() {
				location.href="second.jsp";
			});
	
			$("#techbbs").click(function() {
				location.href="TechbbsController?command=techbbs";
			});
	
			$("#life").click(function() {
				location.href="LifeBbs?command=life";
			});
			
			
	/* 
			//columns
			$("#").click(function() {
				location.href="";
			});
	 */
	 
			//게시판5 나효진 jobs 부분.
/* 			$("#jobs").click(function() {
				location.href="main.BBSHWCodingController";
			});
 */	 
			
			$("#jobs").click(function name() {
				location.href="jobs";
			});

		
		});
	</script>
<!-- 페이징 처리 정보 교환 -->


<%
//페이징 처리
PagingBean paging = new PagingBean();
if(request.getParameter("nowPage") == null){
	paging.setNowPage(1);		// 처음에는 1페이지로 셋팅
}else{
	paging.setNowPage(Integer.parseInt(request.getParameter("nowPage")));
}
%>

<!-- 
// 리스트 뿌려주기위해
QnaServiceImpl service = QnaService.getInstance();
List<QnaDto> qnalist = service.getBbsPagingList(paging); -->




<%
if(findWord == null){
	findWord = "";
}
int cho = 0;

if(choice == null) cho = 3;
else if(choice.equals("title")) cho = 0;
else if(choice.equals("writer")) cho = 1;
else if(choice.equals("content")) cho = 2;
else if(choice.equals("tagname")) cho = 3;

QnaServiceImpl service = QnaService.getInstance();
/* List<QnaDto>  qnalist = service.getQnaPagingList(paging, findWord, cho); */

List<QnaDto> sortlist = (List<QnaDto>)request.getAttribute("sorthe");
String whatsort=(String)request.getAttribute("whatsort");
if(sortlist==null||whatsort==null||sortlist.size()==0){	//sort안했다
	System.out.println("기본페이지");
	qnalist = service.getQnaPagingList(paging, findWord, cho);
}else{
	System.out.println("sort페이지");
	qnalist= service.getqnaBbssortPagingList(paging, whatsort);
			
}

%>



<div class="titlediv"><span class="titi">Q&A게시판</span>
<%
	if(ologin == null){
			%>
	
	<button class="create btn btn-success btn-wide pull-right " type="button" id="loginhe">게시글쓰기</button></div>
	
	<%
		}else{
	%>
	<button class="create btn btn-success btn-wide pull-right " type="button" id="qnawrite">게시글쓰기</button></div>
	
	<%
	}
	%>

<div class="wrap">
<div class="sortingmenu">
	 <ul class="list-sort pull-left">
         <li><a onclick="location.href='qnaServlet?command=sorthe&whatthings=wdate'"  class="category-sort-link active">최신순</a></li>
         <li><a onclick="location.href='qnaServlet?command=sorthe&whatthings=likecount'" class="category-sort-link">좋아요순</a></li>
         <li><a onclick="location.href='qnaServlet?command=sorthe&whatthings=contentcount'" class="category-sort-link">댓글순</a></li>
         <li><a onclick="location.href='qnaServlet?command=sorthe&whatthings=scrapcount'" class="category-sort-link">스크랩순</a></li>
         <li><a onclick="location.href='qnaServlet?command=sorthe&whatthings=readcount'" class="category-sort-link">조회순</a></li>
     </ul>
	
</div>

<div class="sercharea">
<select id="choice" style="height: 30px">
		<option value="tagname" <%if(cho==3){ out.println("selected");}%>>선택하세요</option>
		<option value="title" <%if(cho==0){ out.println("selected");}%>>제목</option>
		<option value="writer" <%if(cho==1){ out.println("selected");}%>>작성자</option>
		<option value="content" <%if(cho==2){ out.println("selected");}%>>내용</option>
		</select>
<input type="text" class="inputField" id="search" value="<%=findWord %>">
<button name="search" id="serchbtn" class="input-group-btn" onclick="searchBbs()"><img alt="" src="image/serchbtn.PNG"></button>
</div>


<!-- 실질적인 qna게시판  -->
<div class="board">
<table border="1" class="techtable">
<!-- (게시물번호)(태그)(좋아요)(답변수)(작성자정보) -->
<col width="450"><col width="80"><col width="80"><col width="80"><col width="150">
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
		String[] tagnames = service.getTagName(qna.getTagname());
		
		boolean checkcomment = service.checkcomment(qnalist.get(i).getSeq());
		if(checkcomment){
		
	%>
	<tr>
		<th> 
		#<%=qnalist.get(i).getSeq() %>
	<%
		}else{
	 %>
	 <tr>
	 	<th style="border-left: 5px solid #808080">
	 	#<%=qnalist.get(i).getSeq() %>
	 <%}
	for(int j=0;j<tagnames.length;j++){
	%>	
		<span><button class="hjhtag" name="tag<%=j%>" id="tag<%=j%>" onclick="searchBbs1(this)" value="<%=tagnames[j]%>"><%=tagnames[j] %></button></span>
		
	<%	
	}
	if(ologin == null){
		%>
		
		<p style="font-size: 15px; margin-top: 5px;"><a href="qnaServlet?command=qnaBbsDetail&action=detail&likeid=&seq=<%=qna.getSeq()%>"><%=qna.getTitle() %></a></p>
		
		<%
			}else{
		%>
		<p style="font-size: 15px; margin-top: 5px;"><a href="qnaServlet?command=qnaBbsDetail&action=detail&likeid=<%=mem.getId() %>&seq=<%=qna.getSeq()%>"><%=qna.getTitle() %></a></p>
		
		<%
		}
		%>
		</th>
		<%if(qna.getCommentcount()>0){
		%>
		<td><img src="image/repleon.PNG"><span class="textalig"> <%=qna.getCommentcount() %></span></td>
		<%
		}else{
		%>
		<td><img src="image/repleoff.png"><span class="textalig"> <%=qna.getCommentcount() %></span></td>
		<%
		}
		%>
		<%if(qna.getLikecount()>0){
		%>
		<td style="padding-top: 3px"><img src="image/likeeon.png"><span class="textalig"> <%=qna.getLikecount() %></span></td>
		<%
		}else{
		%>
		<td style="padding-top: 3px"><img src="image/likeeoff.png"><span class="textalig"> <%=qna.getLikecount() %></span></td>
		<%
		}
		%>
		<%if(qna.getReadcount()>0){
		%>
		<td><img src="image/readcounton.PNG"><span class="textalig"> <%=qna.getReadcount() %></span></td>
		<%
		}else{
		%>
		<td><img src="image/readcountoff.png"><span class="textalig"> <%=qna.getReadcount() %></span></td>
		<%
		}
		%>
		
		<td>
		<%=qna.getId() %>
		<p style="font-size: 10px"><%=qna.getWdate() %></p>
		</td>
		</tr>
		<%
		}
		%>
	</table>
 
<jsp:include page="paging.jsp">
	<jsp:param name="actionPath" value="qnabbslist.jsp"/>
	<jsp:param name="findWord" value="<%=findWord %>"/>
	<jsp:param name="choice" value="<%=choice %>"/>
	<jsp:param name="nowPage" value="<%=String.valueOf(paging.getNowPage()) %>" />
	<jsp:param name="totalCount" value="<%=String.valueOf(paging.getTotalCount()) %>" />
	<jsp:param name="countPerPage" value="<%=String.valueOf(paging.getCountPerPage()) %>" />
	<jsp:param name="blockCount" value="<%=String.valueOf(paging.getBlockCount()) %>" />
</jsp:include> 




</div>	<!-- .board end -->

</div>	<!-- .wrap end -->

<script type="text/javascript">
if(document.getElementById("choice").value=="tagname"){
	$("#search").val("");
}
</script>


<script type="text/javascript">
function searchBbs() {
	if(document.getElementById("choice").value=="tagname"){
		alert("선택해주세요");
		$("#search").val("");
		return;
	}
	if(document.getElementById("search").value==""){//빈문자열에서검색시
		location.href = "qnabbslist.jsp?findWord=TechTips&choice=tagname";	
		return;
	}
	var word = document.getElementById("search").value;
	var choice = document.getElementById("choice").value;
	$("#select_id").val("<%=cho %>").prop("selected", true);
	location.href = "qnabbslist.jsp?findWord=" + word + "&choice=" + choice;	
}
function searchBbs1(e) {
	$("#search").val("");
	var word = e.value;
	var choice = document.getElementById("choice").value;
	location.href = "qnabbslist.jsp?findWord=" + word + "&choice=tagname";	
	
}

//location.href='qnaServlet?command=listQna'
$(function() {
	$("#qnabbs").click(function() {
		location.href="qnaServlet?command=listQna";
	});
	$("#techbbs").click(function() {
		location.href="TechbbsController?command=techbbs";
	});
	$("#qnawrite").click(function() {
		location.href="qnabbswrite.jsp";
	});
	$("#loginhe").click(function() {
		alert("로그인해주세요");
		location.href="qnaServlet?command=listQna";
	});
});
</script>
<script>
      $(function() {
         // initialize popover with dynamic content
         $('#btnPopover').popover({
            placement: 'right',
            container: 'body',
            html: true,
            trigger: 'hover',
            content: '<button onclick="logout()" type="button" class="btn btn-default popover-dismiss">logout</button><button onclick="upmydetail()" type="button" class="btn btn-default popover-dismiss">정보수정</button>'
         });
         // prevent popover from being hidden on mouseout.
         // only dismiss when explicity clicked (e.g. has .hide-popover)
         $('#btnPopover').on('hide.bs.popover', function(evt) {
            if(!$(evt.target).hasClass('hide-popover')) {
               evt.preventDefault();
               evt.stopPropagation();
               evt.cancelBubble = true;
            }
         });
         // reset helper class when dismissed
         $('#btnPopover').on('hidden.bs.popover', function(evt) {
            $(this).removeClass('hide-popover');
         });
         $('body').on('click', '.popover-dismiss', function() {
            // add helper class to force dismissal
            $('#btnPopover').addClass('hide-popover');
            // call method to hide popover
            $('#btnPopover').popover('hide');
         });
          
          $('#btnPopover').data('overButton', false);
          $('#btnPopover').data('overPopover', false);
          $.fn.closePopover = function(){
            var $this = $(this);
            
            if(!$this.data('overPopover') && !$this.data('overButton')){
              $this.addClass('hide-popover');
              $this.popover('hide');              
            }
          }
          
          //set flags when mouse enters the button or the popover.
          //When the mouse leaves unset immediately, wait a second (to allow the mouse to enter again or enter the other) and then test to see if the mouse is no longer over either. If not, close popover.
          $('#btnPopover').on('mouseenter', function(evt){
            $(this).data('overButton', true);
          });
          $('#btnPopover').on('mouseleave', function(evt){
            var $btn = $(this);
            $btn.data('overButton', false);
            
            setTimeout(function() {$btn.closePopover();}, 200);
            
          });
          $('#btnPopover').on('shown.bs.popover', function () {
            var $btn = $(this);
            $('.popover-content').on('mouseenter', function (evt){
              $btn.data('overPopover', true);
            });
            $('.popover-content').on('mouseleave', function (evt){
              $btn.data('overPopover', false);
              
              setTimeout(function() {$btn.closePopover();}, 200);
            });
          });
        });
   </script>


</body>
</html>