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
					<h1>设备列表</h1>
					</div>
				</div>
				<div id="tabCont" class="tabCont">
					<ec:table>
						<ec:column title="设备编号" property="deviceId" width="30%" sortable="true" styleClass="cent"/>
						<ec:column title="接收数据总量" property="dataCount" width="20%" styleClass="cent">
							<a href="deviceDataIndex.x?deviceId=${var.deviceId }">${var.dataCount }</a>
						</ec:column>
						<ec:column title="最近收到数据时间" property="lastData.created" width="40%" styleClass="cent"/>
						<ec:column title="连接编号" property="channelId"/>
					</ec:table>
				</div>
				<ec:toolbar></ec:toolbar>
			</div>
		</div>
	</div>
</body>
</html>