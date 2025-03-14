<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*"%>
    
<%
  session.invalidate();
  response.sendRedirect("Home.html");
%>