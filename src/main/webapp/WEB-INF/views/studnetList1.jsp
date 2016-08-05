<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/" var="R" />
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="http://maxcdn.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>

<div class="container">
    <h1>학생 조회</h1>
    
    <form class="form-inline" action="/helloReport/report/studentList.do">
        <select name="did">
            <option value="1">국어국문학</option>
            <option value="2">영어영문학</option>
            <option value="3">불어불문학</option>
        </select>
        <select name="type">
            <option>html</option>
            <option>pdf</option>
            <option>xlsx</option>
        </select>
        <button class="btn" type="submit">보고서 생성</button>
        <a class="btn" href="${R}">홈으로</a>
    </form>
</div>

</body>
</html>
