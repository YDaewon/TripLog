<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.ssafy.trip.member.model.MemberDto"%>
<%
    String root = request.getContextPath();
	MemberDto user = (MemberDto) session.getAttribute("userinfo");
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>SSAFY BOOK CAFE</title>
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
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow">
      <div class="container">
        <a class="navbar-brand text-primary fw-bold" href="<%=root %>/triplist.jsp">
          <img src="./img/trip.png" alt="" width="60" />
          Enjoy Trip
        </a>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-lg-0">
            <li class="nav-item">
              <a class="nav-link" aria-current="page" href="#">공지사항</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" aria-current="page" href="<%= root %>/areatrip.jsp">여행지 검색</a>
            </li>
           <% if (user != null){ %>
            <li class="nav-item">
              <a class="nav-link" aria-current="page" href="<%= root %>/myTrip.jsp">내 여행 경로</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" aria-current="page" href="<%= root %>/article?action=list&pgno=1&key=&word=">게시판</a>
            </li>
          <%} %>
          </ul>
  <!-- 로그인 전 -->
  		<% if (user == null){ %>
          <ul id="login-pre" class="navbar-nav mb-2 me-2 mb-lg-0">
            <li class="nav-item">
              <a
	            class="nav-link" 
	            aria-current="page" 
	            data-bs-toggle="modal" 
	            data-bs-target="#registerModal"
	            href="#">
	            회원가입
	          </a>
            </li>
            <li class="nav-item">
              <a
                class="nav-link"
                aria-current="page"
                data-bs-toggle="modal"
                data-bs-target="#loginModal"
                href="#"
                >로그인</a
              >
            </li>
          </ul>
          <%} else { %>
          <!-- 로그인 후 -->
          <ul id="login-after" class="navbar-nav mb-2 me-2 mb-lg-0">
          	<li class="nav-item">
              <%= user.getUserName() %>님 안녕하세요.
            </li>
            <li class="nav-item">
              <a class="nav-link" aria-current="page" href="<%= root %>/member/logout">로그아웃</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" aria-current="page" href="<%= root %>/member/mypage">마이페이지</a>
            </li>
            <li class="nav-item dropdown">
              <a
                class="nav-link dropdown-toggle"
                href="#"
                id="navbarDropdown"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                관리자
              </a>
              <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                <li>
                  <a
                    class="dropdown-item"
                    href="#"
                    data-bs-toggle="modal"
                    data-bs-target="#pollModal"
                    >투표만들기</a
                  >
                </li>
                <li><a class="dropdown-item" href="#">차트보기</a></li>
                <li><a class="dropdown-item" href="#">회원관리</a></li>
              </ul>
            </li>
          </ul>
        <% } %>
        </div>
      </div>
    </nav>
        <!-- 로그인 modal start -->
    <div class="modal" id="loginModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <!-- Modal Header -->
          <div class="modal-header">
            <h4 class="modal-title">로그인</h4>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>

          <!-- Modal body -->
          <div class="modal-body">
            <form id="form-login" method="POST" action="">
              <div class="mb-3 mt-3">
                <label for="email" class="form-label">아이디:</label>
                <input
                  type="text"
                  class="form-control"
                  id="login-Id"
                  placeholder="Enter ID"
                  name="userId"
                />
              </div>
              <div class="mb-3">
                <label for="pwd" class="form-label">비밀번호:</label>
                <input
                  type="password"
                  class="form-control"
                  id="login-Pwd"
                  placeholder="Enter password"
                  name="userPwd"
                />
              </div>
            </form>
          </div>

          <!-- Modal footer -->
          <div class="modal-footer">
            <button type="button" id="btn-findpwd" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#findModal">비밀번호찾기</button>
            <button type="button" id="query-login" class="btn btn-primary">로그인</button>
            <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
    <!-- 로그인 modal end -->

    <!-- 회원가입 modal start -->
    <div class="modal" id="registerModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <!-- Modal Header -->
          <div class="modal-header">
            <h4 class="modal-title">회원가입</h4>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>

          <!-- Modal body -->
          <div class="modal-body">
            <form id="form-join" method = "POST" action="">
              <div class="mb-3 mt-3">
                <label for="name" class="form-label">이름:</label>
                <input
                  type="text"
                  class="form-control"
                  id="regist-Name"
                  placeholder="Enter Name"
                  name="userName"
                />
              </div>
              <div class="mb-3 mt-3">
                <label for="id" class="form-label">아이디:</label>
                <input
                  type="text"
                  class="form-control"
                  id="regist-Id"
                  placeholder="Enter ID"
                  name="userId"
                />
                <input id="check_id" type="hidden" value="false">
                <p id="checkid_status">아이디를 입력하세요!!</p>
              </div>
              <div class="mb-3">
                <label for="pwd" class="form-label">비밀번호:</label>
                <input
                  type="password"
                  class="form-control"
                  id="regist-Pwd"
                  placeholder="Enter password"
                  name="userPwd"
                />
              </div>
              <div class="mb-3">
                <label for="pwd" class="form-label">비밀번호 확인:</label>
                <input
                  type="password"
                  class="form-control"
                  id="confirm-Pwd"
                  placeholder="Enter password"
                />
              </div>
              <div class="mb-3">
              <label for="emailid" class="form-label">이메일 : </label>
              <div class="input-group">
                <input
                  type="text"
                  class="form-control"
                  id="regist-emailId"
                  name="emailId"
                  placeholder="이메일아이디"
                />
                <span class="input-group-text">@</span>
                <select
                  class="form-select"
                  id="regist-emailDomain"
                  name="emailDomain"
                  aria-label="이메일 도메인 선택"
                >
                  <option selected>선택</option>
                  <option value="ssafy.com">싸피</option>
                  <option value="google.com">구글</option>
                  <option value="naver.com">네이버</option>
                  <option value="kakao.com">카카오</option>
                </select>
              </div>
            </div>
            </form>
          </div>
          <!-- Modal footer -->
          <div class="modal-footer">
            <button type="button" id="btn-join" class="btn btn-primary">회원가입</button>
            <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
    <!-- 회원가입 modal end -->

    <!-- 비밀번호 찾기 modal start -->
    <div class="modal" id="findModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <!-- Modal Header -->
          <div class="modal-header">
            <h4 class="modal-title">비밀번호찾기</h4>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>

          <!-- Modal body -->
          <div class="modal-body">
            <form action="">
              <div class="mb-3 mt-3">
                <label for="email" class="form-label">아이디:</label>
                <input
                  type="text"
                  class="form-control"
                  id="findid"
                  placeholder="Enter ID"
                  name="findid"
                />
              </div>
            </form>
          </div>

          <!-- Modal footer -->
          <div class="modal-footer">
            <button type="button" id="btn-find" class="btn btn-primary">비밀번호 변경하기</button>
            <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
    <!-- 비밀번호 찾기 modal end -->

    <!-- 설문조사 modal start -->
    <div
      class="modal fade"
      id="pollModal"
      data-bs-backdrop="static"
      data-bs-keyboard="false"
      tabindex="-1"
      aria-labelledby="staticBackdropLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <!-- Modal Header -->
          <div class="modal-header">
            <h4 class="modal-title">
              <i class="bi bi-chat-left-dots-fill text-info"> 설문조사 만들기</i>
            </h4>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>

          <!-- Modal body -->
          <div class="modal-body">
            <form action="">
              <div class="row mb-3 mt-3">
                <div class="col-md-6">
                  <i class="bi bi-calendar2-date text-primary"></i>
                  <label for="start-date" class="form-label">시작일:</label>
                  <input type="date" class="form-control" id="start-date" name="start-date" />
                </div>
                <div class="col-md-6">
                  <i class="bi bi-calendar2-date text-danger"></i>
                  <label for="end-date" class="form-label">종료일:</label>
                  <input type="date" class="form-control" id="end-date" name="end-date" />
                </div>
              </div>
              <div class="mb-3">
                <i class="bi bi-patch-question" style="color: rgb(121, 2, 119)"></i>
                <label for="question" class="form-label">질문제목:</label>
                <input
                  type="text"
                  class="form-control"
                  id="question"
                  placeholder="질문제목 입력..."
                  name="question"
                />
              </div>
              <div class="mb-3">
                <i class="bi bi-pencil-square" style="color: rgb(14, 2, 121)"></i>
                <label for="question" class="form-label">답변항목:</label>
                <button type="button" id="btn-answer-add" class="btn btn-outline-primary btn-sm">
                  <i class="bi bi-plus-square-fill"></i> 추가
                </button>
              </div>
              <div id="poll-answer-list" class="row mb-3">
                <div class="row mb-1 poll-answer-list-item">
                  <div class="col-md-10">
                    <input type="text" class="form-control" name="answer" />
                  </div>
                </div>
              </div>
            </form>
          </div>

          <!-- Modal footer -->
          <div class="modal-footer">
            <button type="button" id="btn-poll-create" class="btn btn-primary btn-sm">
              투표생성
            </button>
            <button type="button" class="btn btn-outline-danger btn-sm" data-bs-dismiss="modal">
              Close
            </button>
          </div>
        </div>
      </div>
    </div>
    <!-- 설문조사 modal end -->
	</body>
	<script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
      crossorigin="anonymous"
    ></script>
	<script>
		      document.querySelector("#query-login").addEventListener("click", function () {
			        if (!document.querySelector("#login-Id").value) {
			          alert("아이디 입력!!");
			          return;
			        } else if (!document.querySelector("#login-Pwd").value) {
			          alert("비밀번호 입력!!");
			          return;
			        }
				    else {
			          let form = document.querySelector("#form-login");
			          form.setAttribute("action", "<%= root %>/member/login");
			          form.submit();
			        }
			      });
		      
		      
		      document.querySelector("#btn-join").addEventListener("click", function () {
		        if (!document.querySelector("#regist-Name").value) {
		          alert("이름 입력!!");
		          return;
		        } else if (!document.querySelector("#regist-Id").value) {
		          alert("아이디 입력!!");
		          return;
		        } else if (!document.querySelector("#regist-Pwd").value) {
		          alert("비밀번호 입력!!");
		          return;
		        } else if (document.querySelector("#confirm-Pwd").value != document.querySelector("#regist-Pwd").value) {
		          alert("비밀번호 확인!!");
		          return;
		        } else if (!document.querySelector("#regist-emailId").value) {
			          alert("이메일 입력!!");
			          return;
		        } else if (document.querySelector("#check_id").value != "true") {
			          alert("중복된 아이디!!");
			          return;
		        } else {
		          let form = document.querySelector("#form-join");
		          form.setAttribute("action", "<%= root %>/member/join");
		          form.submit();
		        }
		      });
		      
		      let id_check_text = document.querySelector("#checkid_status");
		      var userid = document.querySelector("#regist-Id").value;
		      document.querySelector("#regist-Id").addEventListener("keyup", function () {
		    	userid = document.querySelector("#regist-Id").value;
		    	id_check_text.innerHTML = "";
		  		
		  		if(userid.length < 4 || userid.length > 16){
		  			document.querySelector("#check_id").value = "false";
		  			id_check_text.style.color = "black"
		  			id_check_text.innerHTML = "아이디는 4자이상 16자이하입니다.";
		  		}
		  		else{
		  			let url = "${root}<%=root%>/member/" + userid;
		  			fetch(url)
	                .then((response) => response.json())
	                .then(data => resultViewJSON(data))
		  		}
		  	});
		      
		    function resultViewJSON(data) {
		            if (data.cnt == 0) {
		              document.querySelector("#check_id").value = "true";
		              id_check_text.style.color = "blue"
		              id_check_text.innerHTML = userid + "은 사용할 수 있습니다.";
		            } else {
		            	document.querySelector("#check_id").value = "false";
		            	id_check_text.style.color = "red"
		            	id_check_text.innerHTML = userid + "은 사용할 수 없습니다.";
		            }
		    }
		    
       </script>
</html>