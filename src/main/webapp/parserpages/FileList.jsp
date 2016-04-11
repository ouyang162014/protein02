<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>文件列表页面</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/resource/css/FileList.css">
<script src="<%=basePath%>/resource/js/FileList.js"></script>
</head>

<body style="background:#e1e9eb;">
	<div id="show" style="display:none;z-index:2;">
		<table align="center" style="line-height:50px;">
			<tr>
				<td>确定删除？</td>
			</tr>
			<tr>
				<td><a href="javascript:void(0)" onclick="cancelDelete()">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
					id="confirmId" href="#" onclick="confirmDelete()">确认</a>
				</td>
			</tr>
		</table>
	</div>
	<div id="mask" style="position:absolute;z-index:1;width:100%;height:100%;background-color:silver;display:none;opacity:.5;"></div>
	<form action="<%=basePath%>FileList.action" id="mainForm" method="get">
		<div class="right">
			<div class="current">
				当前位置：<a href="javascript:void(0)" style="color:6e6e6e;">文件管理->文件列表</a>
			</div>
			<div class="rightCont">
				<table class="tab1">
					<tbody>
						<tr>
							<td width="90" align="right">文件名称：</td>
							<td><input type="text" class="allInput" name="fileName"
								value="" /></td>
							<td width="90" align="right">上传时间：</td>
							<td><input type="text" class="allInput" name="inputTime"
								value="" /></td>
							<td width="85" align="right"><input type="submit"
								class="tabSub" value="查询"></td>
						</tr>
					</tbody>
				</table>
				<div class="zixun fix">
					<table class="tab2" width="100%">
						<tbody>
							<tr>
								<th><input type="checkbox" id="all"
									onclick="javascript:void(0)" /></th>
								<th>序号</th>
								<th>文件名称</th>
								<th>上传时间</th>
								<th>发布时间</th>
								<th>操作</th>
							</tr>
							<c:forEach items="${fileList}" var="fileInfo" >
								<tr
									<c:if test="${fileInfo.id % 2 !=0}">style="background-color:ECF6EE;"</c:if>>
									<td><input type="checkbox" name="id" value="${fileInfo.name}" />
									</td>
									<td>${fileInfo.id}</td>
									<td>${fileInfo.name }</td>
									<td>${fileInfo.time }</td>
									<td></td>
									<td><a href="#">发布</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
										href="javascript:void(0)"
										onclick="javascript:void(0)">撤销</a> <%-- <a href="<%=basePath%>Delete.action?id=${message.id}">删除</a> --%>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="page fix">
						共<b>5</b>条 <a href="##" class="first">首页</a> <a href="##"
							class="pre">上一页</a> 当前第<span>1</span>页 <a href="##" class="next">下一页</a>
						<a href="##" class="last">末页</a> 跳转至&nbsp;<input type="text"
							value="1" class="allInput w28" />&nbsp;页&nbsp; <a href="##"
							class="go">GO</a>
					</div>
				</div>
			</div>
		</div>

	</form>
</body>
</html>
