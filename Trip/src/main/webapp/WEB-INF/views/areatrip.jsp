<%@page import="com.ssafy.Trip.model.Attraction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List,com.ssafy.Trip.controller.TripController,com.ssafy.Trip.model.guguns,com.ssafy.Trip.model.Sido"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
    <!-- 상단 navbar start -->
    <%@ include file="/common/nav.jsp" %>
    <!-- 상단 navbar end -->
    <div class="col-md-8">
        <div class="alert alert-primary mt-3 text-center fw-bold" role="alert">
            전국 관광지 정보
        </div>
        <!-- 관광지 검색 start -->
        <form class="d-flex my-4" action="<%=request.getContextPath()%>/goarea?action=mvsearch" method="post">
            <select id="sido" class="form-select me-2" onchange="getCode('gugun', this.value)">
                <option value="" selected>검색 할 지역 선택</option>
            </select>
            <select id="gugun" class="form-select me-2">
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
            <input id="search-keyword" class="form-control me-2" type="search" placeholder="검색어" aria-label="검색어" />

    <button id="btn-search" class="btn btn-outline-success" type="button" onclick="fetchAttractions()">검색</button>

        </form>
        <!-- 관광지 목록 start -->
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
                <tbody id="trip-list">
                </tbody>
            </table>
        </div>
        <!-- 관광지 목록 end -->
    </div>
    <script>
    function fetchAttractions() {
        const sido = document.getElementById("sido").value;
        const gugun = document.getElementById("gugun").value;
        const contentTypeId = document.getElementById("search-content-id").value;
        const searchKeyWord = document.getElementById("search-keyword").value;

		console.log(sido, gugun, contentTypeId, searchKeyWord)
	    const url = `<%=request.getContextPath()%>/goarea?action=mvsearch&sido=\${sido}&gugun=\${gugun}&contentTypeId=\${contentTypeId}&searchKeyWord=\${searchKeyWord}`;
	    
	    fetch(url)
	        .then(response => {
	            if (!response.ok) {
	                throw new Error('네트워크 응답에 문제가 있습니다.');
	            }
	            return response.json();
	        })
	        .then(attractions => {
	            // 데이터를 받아오면 테이블에 렌더링
	            renderAttractions(attractions);
	        })
	        .catch(error => {
	            console.error('Fetch 에러:', error);
	        });
    }

    function renderAttractions(attractions) {
    	console.log(attractions)
        const tripList = document.getElementById("trip-list");
        tripList.innerHTML = '';
		console.log(tripList)
        if (attractions.length > 0) {
            document.querySelector("table").style.display = '';
            attractions.forEach(attraction => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td><img src="\${attraction.image}" alt="\${attraction.name}" width="100"></td>
                    <td>\${attraction.name}</td>
                    <td>\${attraction.address}</td>
                    <td>\${attraction.latitude}</td>
                    <td>\${attraction.longitude}</td>
                `;
                tripList.appendChild(row);
            });
        } else {
            const noResultsRow = document.createElement("tr");
            noResultsRow.innerHTML = '<td colspan="5">No attractions found.</td>';
            tripList.appendChild(noResultsRow);
        }
    }
		function getCode(type = "sido", code = "") {
			console.log(type, code)
			fetch(`<%=request.getContextPath()%>/area/search?type=\${type}&code=\${code}`)
			.then(response => response.json())
			.then(items => {
				let codeBox = document.getElementById(type);
				codeBox.options.length = 1;
				items.forEach(item => codeBox.options[codeBox.options.length] = new Option(item.name, item.code));
				
				setInitAndEvent(type);
			});
		}
		function setInitAndEvent() {
			
		}
		getCode();
	</script>
</body>
</html>