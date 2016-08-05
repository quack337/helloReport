<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/" var="R" />
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="http://maxcdn.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet" media="screen">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
<style>
iframe { width: 800px; border: 1px solid #ddd; height: 900px; }
</style>
<script>
function htmlReport() {
	var did = $("[name=did]").val();
	var url = "/helloReport/report/studentList.do?type=html&did=" + did;
	$("iframe").attr("src", url);
}
</script>
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
        <button class="btn" type="button" onclick="htmlReport()">조회</button>
        <button class="btn" type="submit" name="type" value="pdf">인쇄</button>
        <button class="btn" type="submit" name="type" value="xlsx">액셀</button>
        <a class="btn" href="${R}">홈으로</a>
    </form>
    <iframe></iframe>
</div>

</body>
</html>