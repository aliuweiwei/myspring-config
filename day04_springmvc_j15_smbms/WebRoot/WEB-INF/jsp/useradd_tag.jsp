<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<%@include file="common/head.jsp"%>

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
                <div>
                    <label for="ruserPassword">确认密码：</label>
                    <form:password path="ruserPassword" id="ruserPassword" />
					<font color="red"></font>
                </div>
                <div>
                    <label >用户性别：</label>
                    <form:select path="gender" id="gender">
                    	<form:option value="1">男</form:option>
                    	<form:option value="2">女</form:option>
                    </form:select>
                </div>
                <div>
                    <label for="birthday">出生日期：</label>
                    <form:input path="birthday" id="birthday" cssClass="Wdate" readonly="readonly" onclick="WdatePicker();"/>
					<font color="red"></font>
                </div>
                <div>
                    <label for="phone">用户电话：</label>
                    <form:input path="phone" id="phone"/>
					<font color="red"></font>
                </div>
                <div>
                    <label for="address">用户地址：</label>
                    <form:input path="address" id="address"/>
                </div>
                <div>
                    <label >用户角色：</label>
                    <!-- 列出所有的角色分类 -->
                    <form:select path="userRole" id="userRole"/>
	        		<font color="red"></font>
                </div>
                <div>
                    <label for="a_idPicPath">证件照：</label>
                   	<input type="file" name="attachs" id="a_idPicPath"/>
                    <font color="red">${uploadFileError==null?"":uploadFileError}</font>
                </div>
               <div>
                    <label for="a_workPicPath">工作证照片：</label>
                   	<input type="file" name="attachs" id="a_workPicPath"/>
                    <font color="red">${uploadWpError==null?"":uploadWpError}</font>
                </div>
                <div class="providerAddBtn">
                    <input type="button" name="add" id="add" value="保存" >
					<input type="button" id="back" name="back" value="返回" >
                </div>
            </form:form>
           
        </div>
</div>
</section>
<%@include file="common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/useradd.js"></script>
