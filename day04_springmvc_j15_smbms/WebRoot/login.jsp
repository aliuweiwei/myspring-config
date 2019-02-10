<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>系统登录 - 超市订单管理系统</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/static/css/style.css" />
    <script type="text/javascript">
	/* if(top.location!=self.location){
	      top.location=self.location;
	 } */
    </script>
</head>
<body class="login_bg">
    <section class="loginBox">
        <header class="loginHeader">
            <h1>超市订单管理系统</h1>
        </header>
        <section class="loginCont">
        	
	        <%-- <form class="loginForm" action="${pageContext.request.contextPath }/user/login.bdqn"  name="actionForm" id="actionForm"  method="post" > --%>
			<form:form cssClass="loginForm" modelAttribute="user" action="${pageContext.request.contextPath }/user/login.bdqn"  id="actionForm"  method="post">
				<div class="info">${error }
				</div>
				<div class="inputbox">
                    <label for="user">用户名：</label>
                    <%-- <form:input path="userCode" cssClass="input-text" id="userCode"  placeholder="请输入用户名" /> --%>
					<input type="text" class="input-text" id="userCode" name="userCode" placeholder="请输入用户名" />
					 <form:errors path="userCode"/>
				</div>	
				<div class="inputbox">
                    <label for="mima">密码：</label>
                    <%-- <form:password path="userPassword" id="userPassword"  placeholder="请输入密码" /> --%>
                   <input type="password" id="userPassword" name="userPassword" placeholder="请输入密码" />
                   <form:errors path="userPassword"/>
                </div>	
				<div class="subBtn">
					
                    <input type="submit" value="登录"/>
                    <input type="reset" value="重置"/>
                </div>	
			</form:form>
        </section>
    </section>
</body>
</html>
