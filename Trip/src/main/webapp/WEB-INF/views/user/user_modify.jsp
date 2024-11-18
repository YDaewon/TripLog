<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="com.ssafy.member.model.MemberDto" %>
<%@ include file="../common/header.jsp" %>

<%
	HttpSession httpSession = request.getSession();
    MemberDto memberDto = (MemberDto) session.getAttribute("userinfo"); // 세션에서 회원 정보 가져오기
%>
<!DOCTYPE html>
<html lang="en">
<body>
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
                <form id="form-modify" method="POST" action="">
                    <div class="col my-3">
                        <label for="user-id" class="form-label">아이디는 변경할 수 없습니다. </label>
                        <input type="text" class="form-control" id="user-id" name="userid" value="<%= memberDto != null ? memberDto.getUserId() : "" %>" readonly />
                    </div>
                    <div class="col my-3">
                        <label for="user-password" class="form-label">비밀번호 : </label>
                        <input type="password" class="form-control" id="user-password" name="userpwd" value="<%= memberDto != null ? memberDto.getUserPwd() : "" %>" />
                    </div>
                    <div class="col my-3">
                        <label for="user-email" class="form-label">이메일 : </label>
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" id="user-email" name="emailid" value="<%= memberDto != null ? memberDto.getEmailId() : "" %>" />
                            <span class="input-group-text">@</span>
                            <select class="form-select" id="email-domain" name="emaildomain" aria-label="이메일 도메인 선택">
                                <option value="ssafy.com" <%= memberDto != null && memberDto.getEmailDomain().equals("ssafy.com") ? "selected" : "" %>>싸피</option>
                                <option value="naver.com" <%= memberDto != null && memberDto.getEmailDomain().equals("naver.com") ? "selected" : "" %>>네이버</option>
                                <option value="google.com" <%= memberDto != null && memberDto.getEmailDomain().equals("google.com") ? "selected" : "" %>>구글</option>
                                <option value="kakao.com" <%= memberDto != null && memberDto.getEmailDomain().equals("kakao.com") ? "selected" : "" %>>카카오</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="card-footer">
                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    <button class="btn btn-success me-md-2" type="button" onclick="location.href='mypage.jsp'" >수정 취소</button>
                    <button id="user-modify-complete" class="btn btn-primary me-md-2" type="button">수정하기</button>
                </div> 
            </div>
        </div>
    </div>
    
    <script>
    document.querySelector("#user-modify-complete").addEventListener("click", function () {
        // 비밀번호 입력 확인
        if (!document.querySelector("#user-password").value) {
            alert("비밀번호 입력!!");
            return;
        } else {
            // 폼의 액션을 설정하고 제출
            let form = document.querySelector("#form-modify");
            form.setAttribute("action", "${root}/user?action=modify");
            form.submit();
        }
    });

    
</script>

</body>
</html>
