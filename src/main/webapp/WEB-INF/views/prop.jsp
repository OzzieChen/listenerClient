<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
</head>
<body>
<article style="margin-left: 5%;margin-right: 5%;width: 86%;min-width:400px;">

    <form method="post" action="${ctx}${myurl}" style="width:100%;">
        <table>
            <tbody>
            <c:forEach items="${requestScope.list}" var="a">
               <tr> <td style="text-align: right">${pageScope.a[0]}&nbsp;&nbsp;&nbsp;</td>
                <td ><input style="font-size: 14px;width: 250px;" type="text" name="${pageScope.a[0]}" value="${pageScope.a[1]}" class="input-text radius size-M"></td>
               </tr>
            </c:forEach></tbody>
            <tr><td></td><td><input style="padding: 2px;font-size: 14px;" type="submit" value="提交"></td></tr>
        </table>
    </form>
</article>
<c:if test="${not empty requestScope.msg}">
    <script>
        alert("${requestScope.msg}");
    </script>
</c:if>
</body>
</html>