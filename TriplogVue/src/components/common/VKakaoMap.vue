<script setup>
import { ref, watch, onMounted } from "vue";
import { useAttractionStore } from "@/stores/attraction";
import { storeToRefs } from "pinia";
const attractionStore = useAttractionStore();
const { selectedAttraction, attractions } = storeToRefs(attractionStore);

var map;
const positions = ref([]);
const markers = ref([]);
const isKakaoLoaded = ref(false); // Kakao API 로드 상태 추적

watch(
  () => selectedAttraction.value,
  () => {
    // 이동할 위도 경도 위치를 생성합니다
    console.log(
      selectedAttraction.value.latitude +
        " " +
        selectedAttraction.value.longitude
    );
    var moveLatLon = new kakao.maps.LatLng(
      selectedAttraction.value.latitude,
      selectedAttraction.value.longitude
    );

    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.panTo(moveLatLon);
  },
  { deep: true }
);

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
    isKakaoLoaded.value = true;
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }&libraries=services,clusterer`;

    script.onload = () => {
      if (window.kakao && window.kakao.maps) {
        kakao.maps.load(() => {
          initMap();
          isKakaoLoaded.value = true;
        });
      } else {
        console.error("Kakao Maps API is not available.");
      }
    };
    document.head.appendChild(script);
  }
});

watch(
  () => attractions,
  () => {
    if (!isKakaoLoaded.value) return; // API가 로드되지 않았다면 실행하지 않음

    positions.value = [];
    attractions.value.forEach((attraction) => {
      let obj = {};
      obj.latlng = new kakao.maps.LatLng(
        attraction.latitude,
        attraction.longitude
      );
      obj.title = attraction.title;
      obj.image = attraction.firstImage1 || attraction.firstImage1;
      obj.addr = attraction.addr1;

      positions.value.push(obj);
    });
    loadMarkers();
  },
  { deep: true }
);

watch(isKakaoLoaded, (newValue) => {
  if (newValue && attractions.value?.length) {
    // API가 로드되고 attractions 데이터가 있으면 마커 업데이트
    positions.value = [];
    attractions.value.forEach((destination) => {
      let obj = {};
      obj.latlng = new kakao.maps.LatLng(
        destination.latitude,
        destination.longitude
      );
      obj.title = destination.title;
      obj.image = destination.firstImage1 || destination.firstImage1;
      obj.addr = destination.addr1;

      positions.value.push(obj);
    });
    loadMarkers();
  }
});

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);
};

const loadMarkers = () => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();

  let previousMarker = null; // 마지막으로 클릭된 마커를 추적
  // 마커 이미지를 생성합니다
  //   const imgSrc = require("@/assets/map/markerStar.png");
  // 마커 이미지의 이미지 크기 입니다
  //   const imgSize = new kakao.maps.Size(24, 35);
  //   const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

  // 마커를 생성합니다
  markers.value = [];
  positions.value.forEach((position) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position.latlng, // 마커를 표시할 위치
      title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
    });

    kakao.maps.event.addListener(marker, "click", () => {
      map.panTo(marker.getPosition());
    });

    markers.value.push(marker);
  });

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};
</script>

<template>
  <div id="map"></div>
</template>

<style>
#map {
  width: 100%;
  height: 100%;
}
</style>
