<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="${assets}/styles/app.css"  rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${assets }/js/jquery.min.js"></script>
</head>
<body>
	<div class="content">
		<div>
			<h1><a href="index.jsp">回首页</a></h1>
		</div>
		 <div class="main_cont">
			<div id="tabWrap" class="tab_wrap">
				<div class="bd mb20">
					<div class="sub_bar"> 
					<h1>设备数据列表</h1>
					</div>
				</div>
				<div id="tabCont" class="tabCont">
					<ec:table>
						<ec:column title="分机设备ID" property="hta" width="10%"></ec:column>
						<ec:column title="数据发送时间" property="tm" width="30%" sortable="true" styleClass="cent"/>
						<ec:column title="发送的数据次数" property="cnt" width="20%" />
						<ec:column title="上传给服务器的数据" property="dat" width="30%" />
						<ec:column title="当前电池电压" property="bat" width="10%"/>
					</ec:table>
				</div>
				<ec:toolbar></ec:toolbar>
			</div>
		</div>
	</div>
</body>
</html>