<%--
  Created by IntelliJ IDEA.
  User: wooni
  Date: 2024/04/01
  Time: 7:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="kr">
<head>
  <%@ include file="/include/header.jspf" %>
</head>
<body>
<%@ include file="/include/navigation.jspf" %>

<div class="container" id="main">
  <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
    <div class="panel panel-default content-main">
      <form name="question" method="post" action="/qna/update">
        <div class="form-group">
          <input type="hidden" id="questionId" name="questionId" value="${question.questionId}">
        </div>
        <div class="form-group">
          <label for="writer">글쓴이</label>
          <input class="form-control" id="writer" name="writer" placeholder="글쓴이" value="${question.writer}"/>
        </div>
        <div class="form-group">
          <label for="title">제목</label>
          <input type="text" class="form-control" id="title" name="title" placeholder="제목" value="${question.title}"/>
        </div>
        <div class="form-group">
          <label for="contents">내용</label>
          <textarea name="contents" id="contents" rows="5" class="form-control">${question.contents}</textarea>
        </div>
        <button type="submit" class="btn btn-success clearfix pull-right">수정하기</button>
        <div class="clearfix" />
      </form>
    </div>
  </div>
</div>

<%@ include file="/include/footer.jspf" %>
</body>
</html>