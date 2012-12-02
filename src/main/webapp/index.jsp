<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>llll</title>
</head>
<body>
<div>
	<div><a href="deviceIndex.x">设备列表</a></div>
	<div><a href="deviceDataIndex.x">设备数据列表</a></div>
	<div><a href="rawDataIndex.x">原始数据列表</a></div>
	<div>
		<form action="deviceDataConvert.x" method="post" enctype="multipart/form-data">
			<input type="file" name="txtFile">
			<input type="submit" value="txt转换excel">
		</form>
	</div>
</div>

</body>
</html>