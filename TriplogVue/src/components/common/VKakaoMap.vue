<script setup>
import { ref, watch, onMounted } from "vue";

var map;
const positions = ref([]);
const markers = ref([]);
const selectedAttraction = ref(null); // 클릭된 마커의 정보를 저장

const props = defineProps({ attractions: Array, selectAttraction: Object });

watch(
  () => props.selectAttraction.value,
  () => {
    // 이동할 위도 경도 위치를 생성합니다
    console.log(props.selectAttraction.latitude + " "+ props.selectAttraction.longitude);
    var moveLatLon = new kakao.maps.LatLng(props.selectAttraction.latitude, props.selectAttraction.longitude);

    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.panTo(moveLatLon);
  },
  { deep: true }
);

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }&libraries=services,clusterer`;
    
    script.onload = () => {
      if (window.kakao && window.kakao.maps) {
        kakao.maps.load(() => initMap());
      } else {
        console.error("Kakao Maps API is not available.");
      }
    };
    document.head.appendChild(script);
  }
});

watch(
  () => props.attractions.value,
  () => {
    positions.value = [];
    props.attractions.forEach((attraction) => {
      let obj = {};
      obj.latlng = new kakao.maps.LatLng(attraction.latitude, attraction.longitude);
      obj.title = attraction.title;
      obj.image = attraction.firstImage1||attraction.firstImage1;
      obj.addr = attraction.addr1;

      positions.value.push(obj);
    });
    loadMarkers();
  },
  { deep: true }
);

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);
  // loadMarkers();
};

const loadMarkers = () => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();

  let previousMarker = null;  // 마지막으로 클릭된 마커를 추적
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

    kakao.maps.event.addListener(marker, 'click', () => {
      // 이전 마커가 있으면 원래 크기로 복원
      if (previousMarker) {
        previousMarker.setImage(new kakao.maps.MarkerImage(
          '@/assets/marker.png', // 기본 마커 이미지 경로
          new kakao.maps.Size(29, 42)  // 원래 크기
        ));
      }

      // 현재 클릭한 마커 크기 증가
      marker.setImage(new kakao.maps.MarkerImage(
        '@/assets/marker.png', // 사용자 정의 마커 이미지 경로
        new kakao.maps.Size(50, 60)  // 클릭한 마커 크기 (크게)
      ));

      // 이전 마커를 현재 마커로 업데이트
      previousMarker = marker;
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
