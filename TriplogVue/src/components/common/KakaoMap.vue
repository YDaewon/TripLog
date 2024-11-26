<script setup>
import { ref, watch, onMounted } from "vue";
import { useAttractionStore } from "@/stores/attraction";
import { storeToRefs } from "pinia";

const props = defineProps({
  attractions: Array,
  selected: Object,
});

const attractiontore = useAttractionStore();
const map = ref(null);
const positions = ref([]);
const markers = ref([]);
const isKakaoLoaded = ref(false);
const mapContainer = ref(null);

// Kakao 맵 초기화 함수
const initMap = () => {
  if (!mapContainer.value) return;

  const options = {
    center: new window.kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map.value = new window.kakao.maps.Map(mapContainer.value, options);
};

// 스크립트 로드 함수
const loadKakaoMapScript = () => {
  return new Promise((resolve) => {
    if (window.kakao && window.kakao.maps) {
      resolve();
      return;
    }

    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }&libraries=services,clusterer`;

    script.onload = () => {
      window.kakao.maps.load(() => {
        resolve();
      });
    };
    document.head.appendChild(script);
  });
};

// 마커 로드 함수
const loadMarkers = () => {
  if (!isKakaoLoaded.value || !map.value) return;

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

  if (positions.value.length > 0) {
    const bounds = positions.value.reduce(
      (bounds, position) => bounds.extend(position.latlng),
      new window.kakao.maps.LatLngBounds()
    );
  }
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};

// 마운트 시 Kakao 맵 초기화
onMounted(async () => {
  try {
    await loadKakaoMapScript();
    initMap();
    isKakaoLoaded.value = true;
  } catch (error) {
    console.error("Failed to initialize Kakao Map:", error);
  }
});

// 선택된 목적지 변경 감시
watch(
  () => props.selected,
  () => {
    if (!isKakaoLoaded.value || !map.value || !props.selected) return;

    const moveLatLon = new window.kakao.maps.LatLng(
      props.selected.latitude,
      props.selected.longitude
    );
    map.value.panTo(moveLatLon);
  },
  { deep: true }
);

// attractions 변경 감시
watch(
  () => props.attractions,
  () => {
    if (!isKakaoLoaded.value) return;

    positions.value = [];
    props.attractions?.forEach((destination) => {
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
  { deep: true, immediate: true }
);

// Kakao API 로드 상태 감시
watch(isKakaoLoaded, (newValue) => {
  if (newValue && props.attractions?.length) {
    positions.value = [];
    props.attractions.forEach((destination) => {
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
  <div id="map" ref="mapContainer"></div>
</template>
