<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import="org.json.simple.JSONObject"%>
<%
   request.setCharacterEncoding("utf-8");
   JSONObject result = new JSONObject();
   result = (JSONObject)session.getAttribute("result");

   out.println(result.toJSONString());
%>