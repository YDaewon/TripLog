<script setup>
import { ref, watch, onMounted } from "vue";
import { usePlanStore } from "@/stores/plan";
import { storeToRefs } from "pinia";

const planStore = usePlanStore();
const { selectedDestination, tempDestinations, selectedDate } =
  storeToRefs(planStore);
const map = ref(null);
const positions = ref([]);
const markers = ref([]);
const isKakaoLoaded = ref(false);
const props = defineProps({ destinations: Array });

// Kakao 맵 초기화 함수
const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new window.kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map.value = new window.kakao.maps.Map(container, options);
};

// 마커 로드 함수
const loadMarkers = () => {
  if (!isKakaoLoaded.value || !map.value) return;

  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();
  markers.value = [];

  positions.value.forEach((position) => {
    const marker = new window.kakao.maps.Marker({
      map: map.value,
      position: position.latlng,
      title: position.title,
      clickable: true,
    });

    window.kakao.maps.event.addListener(marker, "click", () => {
      map.value.panTo(marker.getPosition());
    });

    markers.value.push(marker);
  });

  // 지도 범위 설정
  if (positions.value.length > 0) {
    const bounds = positions.value.reduce(
      (bounds, position) => bounds.extend(position.latlng),
      new window.kakao.maps.LatLngBounds()
    );
    // map.value.setBounds(bounds);
  }
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};

// 마운트 시 Kakao 맵 초기화
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
      window.kakao.maps.load(() => {
        initMap();
        isKakaoLoaded.value = true;
      });
    };
    document.head.appendChild(script);
  }
});

// 선택된 목적지 변경 감시
watch(
  () => selectedDestination.value,
  () => {
    if (!isKakaoLoaded.value || !map.value || !selectedDestination.value)
      return;

    const moveLatLon = new window.kakao.maps.LatLng(
      selectedDestination.value.latitude,
      selectedDestination.value.longitude
    );
    map.value.panTo(moveLatLon);
  },
  { deep: true }
);

// 임시 목적지 목록 변경 감시
watch(
  () => tempDestinations.value,
  () => {
    if (!isKakaoLoaded.value) return;

    positions.value = [];
    tempDestinations.value?.forEach((destination) => {
      let obj = {};
      obj.latlng = new window.kakao.maps.LatLng(
        destination.latitude,
        destination.longitude
      );
      obj.title = destination.title;
      obj.image = destination.firstImage1;
      obj.addr = destination.addr1;
      positions.value.push(obj);
    });
    loadMarkers();
  },
  { deep: true }
);

// 선택된 날짜 변경 감시
watch(
  () => selectedDate.value,
  () => {
    if (!isKakaoLoaded.value) return;

    positions.value = [];
    tempDestinations.value?.forEach((destination) => {
      if (
        (destination.visitDate === selectedDate.value &&
          selectedDate.value !== "") ||
        selectedDate.value === ""
      ) {
        let obj = {};
        obj.latlng = new window.kakao.maps.LatLng(
          destination.latitude,
          destination.longitude
        );
        obj.title = destination.title;
        obj.image = destination.firstImage1;
        obj.addr = destination.addr1;
        positions.value.push(obj);
      }
    });
    loadMarkers();
  },
  { deep: true }
);

// Kakao API 로드 상태 감시
watch(isKakaoLoaded, (newValue) => {
  if (newValue && tempDestinations.value?.length) {
    positions.value = [];
    tempDestinations.value.forEach((destination) => {
      let obj = {};
      obj.latlng = new window.kakao.maps.LatLng(
        destination.latitude,
        destination.longitude
      );
      obj.title = destination.title;
      obj.image = destination.firstImage1;
      obj.addr = destination.addr1;
      positions.value.push(obj);
    });
    loadMarkers();
  }
});
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
