<%@page import="user.UserDao"%>
<%@page import="user.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>index.jsp</title>

<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="_main.css">

<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap.js"></script>

</head>
<body>

<!-- 인클루드 부분 -->
<div class="menu">
<jsp:include page="menuinclude.jsp">
<jsp:param name="actionPath" value="index.jsp"/>
</jsp:include>
</div>
	


<div class="wrap" align="center">
	

</div>
<%
	String messageContent = null;
	if(session.getAttribute("messageContent") != null){
		messageContent = (String)session.getAttribute("messageContent");
	}
	String messageType = null;
	UserDTO userDto=null;
	if(session.getAttribute("messageType") != null){
		messageType = (String)session.getAttribute("messageType");
		UserDao dao = UserDao.getInstance();
		userDto =  (UserDTO)session.getAttribute("login");
		System.out.println(userDto.getId());
	}
	if(messageContent != null){
	%>
	<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div class="modal-content <% if(messageType.equals("오류 메시지")) out.println("panel-warning"); else out.println("panel-success"); %> ">
					<div class="modal-header panel-heading">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times</span>
							<span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">
							<%=messageType.trim() %>
						</h4>
					</div>
					<div class="modal-body">
						<%=messageContent.trim() %>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		$('#messageModal').modal("show");
	</script>
	<%
	session.removeAttribute("messageContent");
	session.removeAttribute("messageType");
	}
	%>

<script type="text/javascript">
$(function() {
	$(function() {		
		$("#login").click(function() {
			location.href = "UserLogin?command=login";
		});
		$("#account").click(function() {
			location.href = "UserRegister?command=join";
		});
	});
	<%	
	if(userDto!=null){
	%>
	$("#techbbs").click(function() {
		location.href="TechbbsController?command=techbbs";
	});
	$("#qnabbs").click(function() {
		location.href = "qnaServlet?command=listQna";
	});
	<%
	}
	%>
});
</script>
</body>
</html>