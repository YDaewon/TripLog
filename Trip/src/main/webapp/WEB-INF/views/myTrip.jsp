<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <script
      type="text/javascript"
      src="//dapi.kakao.com/v2/maps/sdk.js?appkey=04f1b580a65936948ba006281968bb90"
    ></script>
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
    <style>
      .dotOverlay {
        position: relative;
        bottom: 10px;
        border-radius: 6px;
        border: 1px solid #ccc;
        border-bottom: 2px solid #ddd;
        float: left;
        font-size: 12px;
        padding: 5px;
        background: #fff;
      }
      .dotOverlay:after {
        content: "";
        position: absolute;
        margin-left: -6px;
        left: 50%;
        bottom: -8px;
        width: 11px;
        height: 8px;
        background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white_small.png");
      }
      .distanceInfo {
        position: relative;
        top: 5px;
        left: 5px;
        list-style: none;
        margin: 0;
      }
      .distanceInfo .label {
        display: inline-block;
        width: 50px;
      }
      .distanceInfo:after {
        content: none;
      }
      .number {
        font-weight: bold;
        color: #ee6152;
      }
      .locationList {
        margin: 20px 0;
        font-family: Arial, sans-serif;
      }
      .locationList ul {
        padding: 0;
        list-style: none;
      }
      .locationList li {
        margin: 5px 0;
      }
      #total-distance-info {
        margin-top: 20px;
      }
    </style>
  </head>

  <body>
    <!-- 상단 navbar start -->
    <%@ include file="/common/nav.jsp" %>
    <!-- 상단 navbar end -->
    <!-- 중앙 content start -->
    <div class="container">
      <div style="height: 70px"></div>
      <div class="row">
        <!-- 중앙 left content  start -->
        <div class="row-md-2"></div>
      </div>
      <!-- 중앙 left content end -->
      <!-- 중앙 center content end -->
      <div class="row-md-8">
        <div class="alert alert-primary mt-3 text-center fw-bold" role="alert">
          전국 관광지 정보
        </div>
        <!-- 관광지 검색 start -->
        <form class="d-flex my-3" onsubmit="return false;" role="search">
          <select id="search-area" class="form-select me-2">
            <option value="0" selected>검색 할 지역 선택</option>
          </select>
          <select id="search-content-id" class="form-select me-2">
            <option value="0" selected>관광지 유형</option>
            <option value="12">관광지</option>
            <option value="14">문화시설</option>
            <option value="15">축제공연행사</option>
            <option value="25">여행코스</option>
            <option value="28">레포츠</option>
            <option value="32">숙박</option>
            <option value="38">쇼핑</option>
            <option value="39">음식점</option>
          </select>
          <input
            id="search-keyword"
            class="form-control me-2"
            type="search"
            placeholder="검색어"
            aria-label="검색어"
          />
          <button id="btn-search" class="btn btn-outline-success" type="button">검색</button>
        </form>
        <!-- kakao map start -->
        <div id="map" class="mt-3" style="width: 100%; height: 800px"></div>
        <!-- kakao map end -->
        <div class="row">
          <table class="table table-striped" style="display: none">
            <thead>
              <tr>
                <th>대표이미지</th>
                <th>관광지명</th>
                <th>주소</th>
                <th>위도</th>
                <th>경도</th>
              </tr>
            </thead>
            <tbody id="trip-list"></tbody>
          </table>
        </div>
        <!-- 관광지 검색 end -->
      </div>
      <div class="row-md-2" style="display: flex">
        <div
          class="alert text-center fw-bold"
          style="margin: 10px 30px 10px 0px; box-shadow: 5px 5px 10px gray; width: 200px"
        >
          여행 경로
        </div>
        <div id="tripPath"></div>
      </div>
    </div>
    <!-- 중앙 content end -->

    <!-- 하단 footer start -->
    <footer class="navbar navbar-expand-lg navbar-light bg-light container justify-content-end">
      <div class="row">
        <ul class="navbar-nav">
          <li><a href="#" class="nav-link text-secondary">카페소개</a></li>
          <li><a href="#" class="nav-link text-secondary">개인정보처리방침</a></li>
          <li><a href="#" class="nav-link text-secondary">이용약관</a></li>
          <li><a href="#" class="nav-link text-secondary">오시는길</a></li>
          <li><label class="nav-link text-secondary">&copy; SSAFY Corp.</label></li>
        </ul>
      </div>
    </footer>
    <!-- 하단 footer end -->

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
            <form action="">
              <div class="mb-3 mt-3">
                <label for="email" class="form-label">아이디:</label>
                <input
                  type="text"
                  class="form-control"
                  id="userid"
                  placeholder="Enter ID"
                  name="userid"
                />
              </div>
              <div class="mb-3">
                <label for="pwd" class="form-label">비밀번호:</label>
                <input
                  type="password"
                  class="form-control"
                  id="userpwd"
                  placeholder="Enter password"
                  name="userpwd"
                />
              </div>
            </form>
          </div>

          <!-- Modal footer -->
          <div class="modal-footer">
            <button
              type="button"
              id="btn-findpwd"
              class="btn btn-primary"
              data-bs-toggle="modal"
              data-bs-target="#findModal"
            >
              비밀번호찾기
            </button>
            <button type="button" id="btn-login" class="btn btn-primary">로그인</button>
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
            <form action="">
              <div class="mb-3 mt-3">
                <label for="email" class="form-label">아이디:</label>
                <input
                  type="text"
                  class="form-control"
                  id="registerid"
                  placeholder="Enter ID"
                  name="registerid"
                />
              </div>
              <div class="mb-3">
                <label for="pwd" class="form-label">비밀번호:</label>
                <input
                  type="password"
                  class="form-control"
                  id="registerpwd"
                  placeholder="Enter password"
                  name="registerpwd"
                />
              </div>
              <div class="mb-3">
                <label for="pwd" class="form-label">비밀번호 확인:</label>
                <input
                  type="password"
                  class="form-control"
                  id="confirmpwd"
                  placeholder="Enter password"
                  name="confirmpwd"
                />
              </div>
            </form>
          </div>

          <!-- Modal footer -->
          <div class="modal-footer">
            <button type="button" id="btn-register" class="btn btn-primary">회원가입</button>
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
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
      crossorigin="anonymous"
    ></script>
    <script src="./assets/js/main.js"></script>
    <script
      type="text/javascript"
      src="//dapi.kakao.com/v2/maps/sdk.js?appkey=04f1b580a65936948ba006281968bb90&libraries=services,clusterer,drawing"
    ></script>
    <script>
      // index page 로딩 후 전국의 시도 설정.
      let areaUrl =
        "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
        serviceKey +
        "&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";

      // fetch(areaUrl, { method: "GET" }).then(function (response) { return response.json() }).then(function (data) { makeOption(data); });
      fetch(areaUrl, { method: "GET" })
        .then((response) => response.json())
        .then((data) => makeOption(data));

      function makeOption(data) {
        let areas = data.response.body.items.item;
        // console.log(areas);
        let sel = document.getElementById("search-area");
        areas.forEach((area) => {
          let opt = document.createElement("option");
          opt.setAttribute("value", area.code);
          opt.appendChild(document.createTextNode(area.name));
          sel.appendChild(opt);
        });
      }

      // 검색 버튼을 누르면..
      // 지역, 유형, 검색어 얻기.
      // 위 데이터를 가지고 공공데이터에 요청.
      // 받은 데이터를 이용하여 화면 구성.
      document.getElementById("btn-search").addEventListener("click", () => {
        let baseUrl = `https://apis.data.go.kr/B551011/KorService1/`;
        // let searchUrl = `https://apis.data.go.kr/B551011/KorService1/searchKeyword1?serviceKey=${serviceKey}&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
        // let searchUrl = `https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=${serviceKey}&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;

        let queryString = `serviceKey=${serviceKey}&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
        let areaCode = document.getElementById("search-area").value;
        let contentTypeId = document.getElementById("search-content-id").value;
        let keyword = document.getElementById("search-keyword").value;

        if (parseInt(areaCode)) queryString += `&areaCode=${areaCode}`;
        if (parseInt(contentTypeId)) queryString += `&contentTypeId=${contentTypeId}`;
        // if (!keyword) {
        //   alert("검색어 입력 필수!!!");
        //   return;
        // } else searchUrl += `&keyword=${keyword}`;
        let service = ``;
        if (keyword) {
          service = `searchKeyword1`;
          queryString += `&keyword=${keyword}`;
        } else {
          service = `areaBasedList1`;
        }
        let searchUrl = baseUrl + service + "?" + queryString;

        fetch(searchUrl)
          .then((response) => response.json())
          .then((data) => makeList(data));
      });

      var positions; // marker 배열.
      function makeList(data) {
        console.log(data);
        document.querySelector("table").setAttribute("style", "display: ;");
        let trips = data.response.body.items.item;
        let tripList = ``;
        positions = [];
        trips.forEach((area) => {
          tripList += `
            <tr onclick="moveCenter(${area.mapy}, ${area.mapx});">
              <td><img src="${area.firstimage}" width="100px"></td>
              <td>${area.title}</td>
              <td>${area.addr1} ${area.addr2}</td>
              <td>${area.mapy}</td>
              <td>${area.mapx}</td>
            </tr>
          `;
          document.getElementById("trip-list").innerHTML = tripList;

          let markerInfo = {
            title: area.title,
            latlng: new kakao.maps.LatLng(area.mapy, area.mapx),
          };
          positions.push(markerInfo);
        });
        displayMarker();
      }

      // 카카오지도
      var mapContainer = document.getElementById("map"), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(36.5, 127.5), // 지도의 중심좌표
          level: 12, // 지도의 확대 레벨
        };

      // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
      var map = new kakao.maps.Map(mapContainer, mapOption);

      function displayMarker() {
        // 마커 이미지의 이미지 주소입니다
        var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

        for (var i = 0; i < positions.length; i++) {
          // 마커 이미지의 이미지 크기 입니다
          var imageSize = new kakao.maps.Size(24, 35);

          // 마커 이미지를 생성합니다
          var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

          // 마커를 생성합니다
          let marker = new kakao.maps.Marker({
            map: map,
            position: positions[i].latlng, // 마커를 표시할 위치
            title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            image: markerImage, // 마커 이미지
            clickable: true,
          });

          kakao.maps.event.addListener(marker, "click", function () {});
        }

        // 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
        map.setCenter(positions[0].latlng);
      }

      // 미리 정의된 위치 배열
      var predefinedLocations = [
        { name: "강남역", lat: 37.4970465429, lng: 127.0281573537 },
        { name: "석적체육공원", lat: 36.0717905658, lng: 128.3912306354 },
        { name: "가야산 국립공원", lat: 35.7911141625, lng: 128.0909093551 },
      ];

      var lines = []; // 선 객체를 저장할 배열
      var distanceOverlays = []; // 거리 정보를 표시할 오버레이 배열
      var totalDistanceOverlay; // 총 거리 정보를 표시할 오버레이

      // 지도에 선을 그리는 함수
      function drawLinesOnMap(locations) {
        deleteAllLines(); // 기존 선 삭제
        deleteAllDistanceOverlays(); // 기존 거리 정보 삭제

        if (locations.length < 2) {
          return;
        }

        var totalDistance = 0;

        for (var i = 0; i < locations.length - 1; i++) {
          var start = new kakao.maps.LatLng(locations[i].lat, locations[i].lng);
          var end = new kakao.maps.LatLng(locations[i + 1].lat, locations[i + 1].lng);

          var line = new kakao.maps.Polyline({
            map: map,
            path: [start, end],
            strokeWeight: 3,
            strokeColor: "#db4040",
            strokeOpacity: 1,
            strokeStyle: "solid",
          });

          lines.push(line);

          // 각 선에 거리 정보를 표시합니다
          var distance = Math.round(line.getLength());
          totalDistance += distance;
          displayDistanceOverlay(start, end, distance);
        }

        // 총 거리 정보를 표시합니다
        displayTotalDistance(totalDistance, locations[locations.length - 1]);
      }

      // 미리 정의된 위치 목록을 화면에 출력하는 함수
      function displayLocationList(locations) {
        let listContainer = document.querySelector("#tripPath");
        listContainer.innerHTML = "현위치"; // 기존 내용 지우기
        locations.forEach(function (location) {
          let listItem = document.createElement("span");
          listItem.innerText = " → " + location.name;
          listContainer.appendChild(listItem);
        });
      }

      // 개별 거리 정보를 표시하는 오버레이를 생성하는 함수
      function displayDistanceOverlay(start, end, distance) {
        var content =
          '<div class="dotOverlay distanceInfo">구간거리 <span class="number">' +
          distance +
          "</span>m</div>";
        var overlay = new kakao.maps.CustomOverlay({
          content: content,
          map: map,
          position: new kakao.maps.LatLng(
            (start.getLat() + end.getLat()) / 2,
            (start.getLng() + end.getLng()) / 2
          ),
          yAnchor: 1,
          zIndex: 2,
        });

        distanceOverlays.push(overlay);

        // 각 구간의 시작점과 끝점에 점을 표시합니다
        var startMarker = new kakao.maps.CustomOverlay({
          content: '<div style="color: #db4040; font-size: 24px;">●</div>', // 선과 동일한 색상(#db4040) 및 크기 설정
          map: map,
          position: start,
          yAnchor: 0.5,
          zIndex: 3,
        });

        var endMarker = new kakao.maps.CustomOverlay({
          content: '<div style="color: #db4040; font-size: 24px;">●</div>',
          map: map,
          position: end,
          yAnchor: 0.5,
          zIndex: 3,
        });

        distanceOverlays.push(startMarker);
        distanceOverlays.push(endMarker);
      }

      // 총 거리 정보를 화면에 표시하는 함수
      function displayTotalDistance(totalDistance, lastLocation) {
        var content = getTimeHTML(totalDistance);

        if (totalDistanceOverlay) {
          totalDistanceOverlay.setPosition(
            new kakao.maps.LatLng(lastLocation.lat, lastLocation.lng)
          );
          totalDistanceOverlay.setContent(content);
        } else {
          totalDistanceOverlay = new kakao.maps.CustomOverlay({
            map: map,
            content: content,
            position: new kakao.maps.LatLng(lastLocation.lat, lastLocation.lng),
            xAnchor: 0,
            yAnchor: 0,
            zIndex: 3,
          });
        }
      }

      // 지도에서 모든 선을 삭제하는 함수
      function deleteAllLines() {
        lines.forEach(function (line) {
          line.setMap(null);
        });
        lines = [];
      }

      // 지도에서 모든 거리 정보를 삭제하는 함수
      function deleteAllDistanceOverlays() {
        distanceOverlays.forEach(function (overlay) {
          overlay.setMap(null);
        });
        distanceOverlays = [];
      }
      // 장소 리스트를 화면에 출력하는 함수
      function displayLocationList(locations) {
        var listElement = document.getElementById("location-list");
        listElement.innerHTML = "";

        locations.forEach(function (location) {
          var listItem = document.createElement("li");
          listItem.textContent =
            location.name + " (위도: " + location.lat + ", 경도: " + location.lng + ")";
          listElement.appendChild(listItem);
        });
      }

      // 거리와 도보, 자전거 시간 정보를 HTML로 생성하는 함수
      function getTimeHTML(distance) {
        var walkTime = Math.round(distance / 67);
        var walkHour = "",
          walkMin = "";

        if (walkTime > 60) {
          walkHour = '<span class="number">' + Math.floor(walkTime / 60) + "</span>시간 ";
        }
        walkMin = '<span class="number">' + (walkTime % 60) + "</span>분";

        var bikeTime = Math.round(distance / 227);
        var bikeHour = "",
          bikeMin = "";

        if (bikeTime > 60) {
          bikeHour = '<span class="number">' + Math.floor(bikeTime / 60) + "</span>시간 ";
        }
        bikeMin = '<span class="number">' + (bikeTime % 60) + "</span>분";

        var content = '<ul class="dotOverlay distanceInfo">';
        content +=
          '    <li><span class="label">총거리</span><span class="number">' +
          distance +
          "</span>m</li>";
        content += '    <li><span class="label">도보</span>' + walkHour + walkMin + "</li>";
        content += '    <li><span class="label">자전거</span>' + bikeHour + bikeMin + "</li>";
        content += "</ul>";

        return content;
      }

      // 장소 리스트를 화면에 출력하는 함수
      function displayLocationList(locations) {
        var listElement = document.getElementById("tripPath");
        listElement.innerHTML = "현재 위치";

        locations.forEach(function (location, index) {
          var listItem = document.createElement("span");
          listItem.textContent = " → " + location.name;
          listElement.appendChild(listItem);
        });
      }

      // 거리와 도보, 자전거 시간 정보를 HTML로 생성하는 함수
      function getTimeHTML(distance) {
        var walkTime = Math.round(distance / 67);
        var walkHour = "",
          walkMin = "";

        if (walkTime > 60) {
          walkHour = '<span class="number">' + Math.floor(walkTime / 60) + "</span>시간 ";
        }
        walkMin = '<span class="number">' + (walkTime % 60) + "</span>분";

        var bikeTime = Math.round(distance / 227);
        var bikeHour = "",
          bikeMin = "";

        if (bikeTime > 60) {
          bikeHour = '<span class="number">' + Math.floor(bikeTime / 60) + "</span>시간 ";
        }
        bikeMin = '<span class="number">' + (bikeTime % 60) + "</span>분";

        var content = '<ul class="dotOverlay distanceInfo">';
        content +=
          '    <li><span class="label">총거리</span><span class="number">' +
          distance +
          "</span>m</li>";
        content += '    <li><span class="label">도보</span>' + walkHour + walkMin + "</li>";
        content += '    <li><span class="label">자전거</span>' + bikeHour + bikeMin + "</li>";
        content += "</ul>";

        return content;
      }

      // 문서가 로드될 때 선을 자동으로 그립니다
      kakao.maps.event.addListener(map, "tilesloaded", function () {
        drawLinesOnMap(predefinedLocations);
        displayLocationList(predefinedLocations);
      });

      function moveCenter(lat, lng) {
        map.setCenter(new kakao.maps.LatLng(lat, lng));
      }
    </script>
  </body>
</html>
