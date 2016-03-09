<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import="org.json.simple.JSONObject"%>
<%
	request.setCharacterEncoding("utf-8");
	JSONObject info = new JSONObject();
	info = (JSONObject)session.getAttribute("info");
	
	out.println(info.toJSONString());
%>