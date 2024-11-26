<script setup>
import { ref, watch, onMounted, computed } from "vue";

const props = defineProps({
  attractions: {
    type: Array,
    default: () => [],
  },
  selectedAttraction: {
    type: Object,
    default: null,
  },
  tempDestinations: {
    type: Array,
    default: () => [],
  },
  selectedDestination: {
    type: Object,
    default: null,
  },
});

const map = ref(null);
const positions = ref([]);
const markers = ref([]);
const isKakaoLoaded = ref(false);
const mapContainer = ref(null);

// 현재 표시할 위치들 계산
const currentPositions = computed(() => {
  const allPositions = [...props.attractions, ...props.tempDestinations];
  return allPositions;
});

// 현재 선택된 위치들 watch
const moveToLocation = (location) => {
  if (
    !isKakaoLoaded.value ||
    !map.value ||
    !location?.latitude ||
    !location?.longitude
  )
    return;

  const moveLatLon = new window.kakao.maps.LatLng(
    location.latitude,
    location.longitude
  );
  map.value.panTo(moveLatLon);
};

const temporaryMarker = ref(null); // 임시 마커 참조 저장

// 임시 마커 생성/갱신 함수
const updateTemporaryMarker = (location) => {
  if (!location?.latitude || !location?.longitude) return;

  // 기존 임시 마커가 있다면 제거
  if (temporaryMarker.value) {
    temporaryMarker.value.setMap(null);
  }

  const position = new window.kakao.maps.LatLng(
    location.latitude,
    location.longitude
  );

  temporaryMarker.value = new window.kakao.maps.Marker({
    map: map.value,
    position: position,
    title: location.title,
    zIndex: 2, // 다른 마커들보다 앞에 표시
  });

  // 지도 중심 이동
  map.value.panTo(position);
};

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

// 마커 로드 함수 수정
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

  // 마커가 있는 경우 지도 범위 재설정
  if (positions.value.length > 0) {
    const bounds = positions.value.reduce(
      (bounds, position) => bounds.extend(position.latlng),
      new window.kakao.maps.LatLngBounds()
    );
    map.value.setBounds(bounds);
  }
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};

// 위치 객체 생성 헬퍼 함수
const createPositionObject = (destination) => {
  if (!destination?.latitude || !destination?.longitude) return null;

  return {
    latlng: new window.kakao.maps.LatLng(
      destination.latitude,
      destination.longitude
    ),
    title: destination.title,
    image: destination.firstImage1,
    addr: destination.addr1,
  };
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

// selectedAttraction 변경 감시
watch(
  () => props.selectedAttraction,
  (newValue) => {
    if (newValue) {
      updateTemporaryMarker(newValue);
      moveToLocation(newValue);
    }
  },
  { deep: true }
);

// selectedDestination 변경 감시
watch(
  () => props.selectedDestination,
  (newValue) => {
    if (newValue) {
      moveToLocation(newValue);
    }
  },
  { deep: true }
);

// 표시할 위치들 변경 감시
watch(
  currentPositions,
  (newValue) => {
    if (!isKakaoLoaded.value) return;

    positions.value = newValue
      .map(createPositionObject)
      .filter((position) => position !== null);
    loadMarkers();
  },
  { deep: true, immediate: true }
);

// Kakao API 로드 상태 감시
watch(isKakaoLoaded, (newValue) => {
  if (newValue && currentPositions.value?.length > 0) {
    positions.value = currentPositions.value
      .map(createPositionObject)
      .filter((position) => position !== null);
    loadMarkers();
  }
});
</script>

<template>
  <div id="map" ref="mapContainer"></div>
</template>

<style scoped>
#map {
  width: 100%;
  height: 100%;
}
</style>
