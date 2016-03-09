<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	request.setCharacterEncoding("utf-8");
	String message = session.getAttribute("message").toString();
%>	

<%out.print(message);%>
    