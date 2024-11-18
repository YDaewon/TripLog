<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.ssafy.trip.member.model.MemberDto"%>
<!DOCTYPE html>
<html lang="en">
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
  </head>
  <body>
    <!-- 상단 navbar start -->
    <%@ include file="./common/nav.jsp" %>
    <!-- 상단 navbar end -->
    <!-- 중앙 content start -->
    <div class="container">
      <div class="row">
        <!-- 중앙 left content  start -->
        <div class="col-md-2">
          <!-- 프로필 사진 start -->
          <div class="text-center mt-3">
            <img src="./assets/img/profile.png" class="img-thumbnail" id = "img-profile" alt="프로필" />
          </div>
          <!-- 프로필 사진 end -->
          <!-- 지역 메뉴 start -->
          <div class="accordion mt-3" search-area>
          <div class="accordion-item">
            <h2 class="accordion-header" id="seoul">
              <button
                class="accordion-button"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#area-seoul"
                aria-expanded="true"
                aria-controls="area-seoul"
              >
                서울
              </button>
            </h2>
            <div
              id="area-seoul"
              class="accordion-collapse collapse show"
              aria-labelledby="seoul"
              data-bs-parent="#areas"
            >
              <div class="accordion-body">역삼점</div>
              <div class="accordion-body">선릉점</div>
            </div>
          </div>
          <div class="accordion-item">
            <h2 class="accordion-header" id="daejeon">
              <button
                class="accordion-button collapsed"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#area-daejeon"
                aria-expanded="true"
                aria-controls="area-daejeon"
              >
                대전
              </button>
            </h2>
            <div
              id="area-daejeon"
              class="accordion-collapse collapse"
              aria-labelledby="daejeon"
              data-bs-parent="#areas"
            >
              <div class="accordion-body">학하점</div>
              <div class="accordion-body">봉명점</div>
            </div>
          </div>
          <div class="accordion-item">
            <h2 class="accordion-header" id="gumi">
              <button
                class="accordion-button collapsed"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#area-gumi"
                aria-expanded="true"
                aria-controls="area-gumi"
              >
                구미
              </button>
            </h2>
            <div
              id="area-gumi"
              class="accordion-collapse collapse"
              aria-labelledby="gumi"
              data-bs-parent="#areas"
            >
              <div class="accordion-body">인동점</div>
              <div class="accordion-body">공단점</div>
            </div>
          </div>
          <div class="accordion-item">
            <h2 class="accordion-header" id="gwangju">
              <button
                class="accordion-button collapsed"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#area-gwangju"
                aria-expanded="true"
                aria-controls="area-gwangju"
              >
                광주
              </button>
            </h2>
            <div
              id="area-gwangju"
              class="accordion-collapse collapse"
              aria-labelledby="gwangju"
              data-bs-parent="#areas"
            >
              <div class="accordion-body">하남산단점</div>
              <div class="accordion-body">광주역점</div>
            </div>
          </div>
          <div class="accordion-item">
            <h2 class="accordion-header" id="buk">
              <button
                class="accordion-button collapsed"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#area-buk"
                aria-expanded="true"
                aria-controls="area-buk"
              >
                부울경
              </button>
            </h2>
            <div
              id="area-buk"
              class="accordion-collapse collapse"
              aria-labelledby="buk"
              data-bs-parent="#areas"
            >
              <div class="accordion-body">부산사업장점</div>
              <div class="accordion-body">녹산산단점</div>
            </div>
          </div>
        </div>
        <!-- 지역 메뉴 end -->
        <!-- 설문조사 start -->
        <div id="poll-area" class="border border-danger rounded mt-2 mb-2 p-3"></div>
        <!-- 설문조사 end -->
      </div>
      <!-- 중앙 left content end -->
      <!-- 중앙 center content end -->
      <div class="col-md-8">
      <img src="./img/IE002267473.png" class="img" id = "img" alt="메인" />
        <!-- 관광지 검색 start -->
        <!-- <form class="d-flex my-3" onsubmit="return false;" role="search">
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
        </form>-->
        
        <!-- kakao map start -->
        <!-- <div id="map" class="mt-3" style="width: 100%; height: 400px"></div> -->
        <!-- kakao map end -->
        <!--  <div class="row">
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
        </div> -->
        <!-- 관광지 검색 end -->
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
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
      crossorigin="anonymous"
    ></script>
    <script
      type="text/javascript"
      src="//dapi.kakao.com/v2/maps/sdk.js?appkey=04f1b580a65936948ba006281968bb90&libraries=services,clusterer,drawing"
    ></script>
    <!-- <script>
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

      const cafeArea = {
        서울: ["역삼점", "선릉점"],
        대전: ["학하점", "봉명점"],
        구미: ["인동점", "공단점"],
        광주: ["하남산단점", "광주역점"],
        부울경: ["부산사업장점", "녹산산단점"],
      };

      const officePosition = {
        역삼점: { lat: 37.5012767241426, lng: 127.039600248343 },
        선릉점: { lat: 37.5039452362982, lng: 127.048527611976 },
        학하점: { lat: 36.35536, lng: 127.298294 },
        봉명점: { lat: 36.358843, lng: 127.344192 },
        인동점: { lat: 36.098594, lng: 128.38977 },
        공단점: { lat: 36.109553, lng: 128.415011 },
        하남산단점: { lat: 35.204279, lng: 126.807198 },
        광주역점: { lat: 35.165476, lng: 126.909216 },
        부산사업장점: { lat: 35.095765, lng: 128.856344 },
        녹산산단점: { lat: 35.093641, lng: 128.855679 },
      };

      const officeAddress = {
        역삼점: "서울특별시 강남구 역삼동 테헤란로 212",
        선릉점: "서울특별시 강남구 역삼동 테헤란로 334 LG 화재빌딩",
        학하점: "대전광역시 유성구 덕명동 124",
        봉명점: "대전광역시 유성구 봉명동 대학로 60 봉명가든 6층",
        연수원점: "경상북도 구미시 공단동 259",
        구미사업장: "경상북도 구미시 임수동 94",
        하남산단점: "107 하남산단6번로 광산구 광주광역시",
        광주역점: "광주광역시 북구 중흥동 611",
        부산사업장점: "부산광역시 강서구 송정동 녹산산업중로 삼성전기 부산사업장사",
        녹산산단점: "부산광역시 강서구 송정동 삼성전기부속의원",
      };

      // 카카오지도
      var mapContainer = document.getElementById("map"), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(37.500613, 127.036431), // 지도의 중심좌표
          level: 5, // 지도의 확대 레벨
        };

      // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
      var map = new kakao.maps.Map(mapContainer, mapOption);

      var mapTypeControl = new kakao.maps.MapTypeControl();

      // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
      // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
      map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

      // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
      var zoomControl = new kakao.maps.ZoomControl();
      map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

      // HTML5의 geolocation으로 사용할 수 있는지 확인합니다
      if (navigator.geolocation) {
        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
        navigator.geolocation.getCurrentPosition(function (position) {
          var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도

          (myLatLng = new kakao.maps.LatLng(lat, lon)), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
            (message = '<div style="padding:5px;">나여기있어요</div>'); // 인포윈도우에 표시될 내용입니다

          // 마커와 인포윈도우를 표시합니다
          setMarker(myLatLng, message);
        });
      } else {
        // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다

        (myLatLng = new kakao.maps.LatLng(37.5012743, 127.039585)),
          (message = "geolocation을 사용할수 없어요..");

        setMarker(myLatLng, message);
      }

      function setMarker(myLatLng, message, markerImage) {
        // 마커를 생성합니다
        marker = new kakao.maps.Marker({
          map: map,
          position: myLatLng,
          image: markerImage, // 마커이미지 설정
        });

        var iwContent = message, // 인포윈도우에 표시할 내용
          iwRemoveable = true;

        // 인포윈도우를 생성합니다
        infowindow = new kakao.maps.InfoWindow({
          content: iwContent,
          removable: iwRemoveable,
        });

        // 인포윈도우를 마커위에 표시합니다
        // infowindow.open(map, marker);

        // 지도 중심좌표를 접속위치로 변경합니다
        map.setCenter(myLatLng);

        // 마커에 마우스오버 이벤트를 등록합니다
        kakao.maps.event.addListener(marker, "mouseover", function () {
          // 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
          infowindow.open(map, marker);
        });

        // 마커에 마우스아웃 이벤트를 등록합니다
        kakao.maps.event.addListener(marker, "mouseout", function () {
          // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
          infowindow.close();
        });
      }

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

        }

        // 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
        map.setCenter(positions[0].latlng);
      }

      let offices = document.querySelectorAll(".accordion-body");
      offices.forEach(function (office) {
        office.addEventListener("click", function () {
          viewMarker(this);
        });
      });

      function viewMarker(office) {
        officeLatLng = officePosition[office.textContent];
        marker.setMap(null);
        myLatLng = new kakao.maps.LatLng(officeLatLng.lat, officeLatLng.lng);
        let message = `<div style="padding:5px;">${office.textContent}</div>`;
        const imageSrc = "./assets/img/trip.png"; // 마커이미지의 주소입니다
        let imageSize = new kakao.maps.Size(50, 54); // 마커이미지의 크기입니다
        let imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

        // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

        setMarker(myLatLng, message, markerImage);
      }

      function moveCenter(lat, lng) {
        map.setCenter(new kakao.maps.LatLng(lat, lng));
      }
    </script>-->
  </body>
</html>
