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
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <style>
        .input-text, .textarea {
            box-sizing: border-box;
            border: solid 1px #ddd;
            width: 100%;
            -webkit-transition: all 0.2s linear 0s;
            -moz-transition: all 0.2s linear 0s;
            -o-transition: all 0.2s linear 0s;
            transition: all 0.2s linear 0s
        }

        .textarea {
            height: auto;
            font-size: 14px;
            padding: 4px
        }

        .input-text:hover, .textarea:hover {
            border: solid 1px #3bb4f2
        }

        /*得到焦点后*/
        .input-text.focus, textarea.focus {
            border: solid 1px #0f9ae0 \9;
            border-color: rgba(82, 168, 236, 0.8);
            box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px rgba(102, 175, 233, 0.6)
        }

        /*不可点击*/
        .input-text.disabled, .textarea.disabled, .input-text.disabled.focus, .textarea.disabled.focus {
            background-color: #ededed;
            cursor: default;
            border-color: #ddd;
            -webkit-box-shadow: inset 0 2px 2px #e8e7e7;
            -moz-box-shadow: inset 0 2px 2px #e8e7e7;
            box-shadow: inset 0 2px 2px #e8e7e7
        }

        /*只读状态*/
        .input-text.disabled, .textarea.disabled {
            background-color: #e6e6e6;
            cursor: default
        }

        /*阴影*/
        .input-text.box-shadow, .textarea.box-shadow {
            -ms-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
            -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075)
        }

        .btn {
            display: inline-block;
            cursor: pointer;
            text-align: center;
            font-weight: 400;
            white-space: nowrap;
            vertical-align: middle;
            *zoom: 1;
            -webkit-transition: background-color .1s linear;
            -moz-transition: background-color .1s linear;
            -o-transition: background-color .1s linear;
            transition: background-color .1s linear
        }

        a.btn:hover, a.btn:focus, a.btn:active, a.btn.active, a.btn.disabled, a.btn[disabled] {
            text-decoration: none
        }

        .btn:active, .btn.active {
            background-color: #ccc
        }

        .btn:first-child {
            *margin-left: 0
        }

        .btn.active, .btn:active {
            -moz-box-shadow: 0 1px 8px rgba(0, 0, 0, 0.125) inset;
            -webkit-box-shadow: 0 1px 8px rgba(0, 0, 0, 0.125) inset;
            box-shadow: 0 1px 8px rgba(0, 0, 0, 0.125) inset
        }

        .btn-primary {
            color: #fff;
            background-color: #5a98de;
            border-color: #5a98de
        }

        .btn-primary:hover,
        .btn-primary:focus,
        .btn-primary:active,
        .btn-primary.active {
            color: #fff;
            background-color: #6aa2e0;
            border-color: #6aa2e0
        }</style>
</head>
<body>
<article>

    <form action="${ctx}${myurl}">
        <c:forEach items="${requestScope.list}" var="a">
            ${pageScope.a[0]}
            <input type="text" value="${pageScope.a[1]}" class="input-text radius size-M"><br>

            <input class="btn btn-primary size-M radius" type="submit" value="提交">
        </c:forEach>
    </form>
</article>
<c:if test="${not empty requestScope.msg}">
    <script>
        alert("${requestScope.msg}");
    </script>
</c:if>
</body>
</html>