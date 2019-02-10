<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    

<div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>用户管理页面 >> 用户添加页面</span>
        </div>
        <div class="providerAdd">
                <form:form modelAttribute="user" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath }/user/add.bdqn">
                <!--div的class 为error是验证错误，ok是验证成功-->
                <div>
                    <label for="userCode">用户编码：</label>
                	<form:input path="userCode" id="userCode" />
					<!-- 放置提示信息 -->
					<font color="red"></font>
                </div>
                <div>
                    <label for="userName">用户名称：</label>
                    <form:input path="userName" id="userCode" />
					<font color="red"></font>
                </div>
                <div>
                    <label for="userPassword">用户密码：</label>
                    <form:password path="userPassword" id="userPassword" />
					<font color="red"></font>
                </div>
               
                <div class="providerAddBtn">
                    <input type="button" name="add" id="add" value="保存" >
					<input type="button" id="back" name="back" value="返回" >
                </div>
            </form:form>
           
        </div>
</div>
</section>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/useradd.js"></script>
