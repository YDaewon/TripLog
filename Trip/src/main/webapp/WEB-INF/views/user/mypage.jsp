<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="com.ssafy.trip.member.model.MemberDto" %>
<%@ include file="../common/header.jsp" %>

<%
    // 세션에서 회원 정보 가져오기
    HttpSession httpSession = request.getSession();
	MemberDto memberDto = (MemberDto) session.getAttribute("userinfo"); // 세션에서 회원 정보 가져오기
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이페이지</title>
    <link rel="shortcut icon" href="./assets/img/favicon.ico" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
    />
    <script src="./assets/js/key.js"></script>
</head>
<body>
<%@ include file="../common/nav.jsp" %>
      <div class="container">
        <div style="height: 100px"></div>
        <div class="card">
          <div class="card-header">
            <h4 class="modal-title my-2 ms-2" style="position: relative; display: inline-block; z-index: 1;">
                회원정보
                <span style="position: absolute; left: 0; bottom: 3px; width: 100%; height: 7px; background-color: orange; z-index: -1;"></span>
            </h4>
          </div>
           <div class="card-body ms-2">
           <form id = "info" method="POST" action="">
            <div class="row">
        <div class="col my-3">
            <label for="user-id" class="form-label">아이디 : </label>
            <input type="text" class="form-control" id="user-id" name="userId" value="<%= memberDto != null ? memberDto.getUserId() : "" %>" readonly />
        </div>
        <div class="col my-3">
            <label for="user-password" class="form-label">비밀번호 : </label>
            <input type="password" class="form-control" id="user-password" name="userPwd" value="<%= memberDto != null ? memberDto.getUserPwd() : "" %>" readonly />
        </div>
        <div class="col my-3">
            <label for="user-email" class="form-label">이메일 : </label>
            <div class="input-group mb-3">
           		<input type="text" class="form-control" id="user-email" name="emailId" value="<%= memberDto != null ? memberDto.getEmailId() : "" %>" readonly />            
                <span class="input-group-text">@</span>
				<input type="text" class="form-control" id="user-email-domain" name="emailDomain" value="<%= memberDto != null ? memberDto.getEmailDomain() : "" %>" readonly />
            </div>
        </div>
        </div>
        </form>
        </div>
          <div class="card-footer">
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
              <button id="user-modify" class="btn btn-primary me-md-2" type="button"><a href="<%= root %>/member/modify">회원 수정</button>
              <button id="user-delete" class="btn btn-danger me-md-2" type="button">회원 탈퇴</button>
            </div>
          </div>
        </div>
      </div>
      <script src="./assets/js/mypage.js"></script>
      
      <script>
      document.querySelector("#user-modify").addEventListener("click", function () {
    	  let form = document.querySelector("#info");
          form.setAttribute("action", "<%= root %>/member/modify");
          form.submit();
      });
      
      document.querySelector("#user-delete").addEventListener("click", function () {
          // 확인 대화상자 표시
          if (confirm("정말로 회원 탈퇴하시겠습니까?")) {
              // 회원탈퇴 후 메인 페이지로 이동
              let form = document.querySelector("#info"); // 폼 선택
              form.setAttribute("action", "<%= root %>/member/delete"); // 액션 설정
              form.submit(); // 폼 제출
          }
      });
      </script>
      
</body>
</html>