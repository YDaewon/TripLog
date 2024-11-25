<template>
  <div class="search-header">
    <form>
      <div class="select-group">
        <VSelect :selectOption="sidoList" @onKeySelect="onChangeSido" />
        <VSelect :selectOption="gugunList" @onKeySelect="onChangeGugun" />
        <VSelect
          :selectOption="contentList"
          @onKeySelect="onChangeContentType"
        />
      </div>
      <div class="d-flex align-items-center">
        <input
          class="form-control mb-1 me-3"
          type="search"
          placeholder="이름으로 검색..."
          aria-label="Search"
          v-model="searchParam.title"
        />
        <button
          type="button"
          class="btn btn-outline-primary mb-1"
          style="white-space: nowrap; height: 100%; width: 20%"
          @click="getAttractions"
        >
          검색
        </button>
      </div>
    </form>
  </div>
</template>
<script setup>
import { ref, onMounted, defineProps } from "vue";
import { listAttractions } from "@/api/attraction";
import { listSido, listGugun, listContentType } from "@/api/map";
import VSelect from "../common/VSelect.vue";

const emit = defineEmits();

const sidoList = ref([]);
const gugunList = ref([{ text: "구군선택", value: "" }]);
const contentList = ref([]);
const searchParam = ref({
  pageNo: 1,
  numOfRows: 20,
  siGunGuCode: null,
  areaCode: null,
  contentTypeId: null,
  title: null,
});

onMounted(() => {
  getSidoList();
  getContentTypeList();
});

const getSidoList = () => {
  listSido(
    ({ data }) => {
      let options = [];
      options.push({ text: "시도선택", value: "" });
      options.push({ text: "전체", value: null });
      data.forEach((sido) => {
        options.push({ text: sido.sido_name, value: sido.sido_code });
      });
      sidoList.value = options;
    },
    (err) => {
      console.log(err);
    }
  );
};

const onChangeSido = (val) => {
  listGugun(
    { sidoCode: val },
    ({ data }) => {
      let options = [];
      options.push({ text: "구군선택", value: "" });
      options.push({ text: "전체", value: null });
      data.forEach((gugun) => {
        options.push({ text: gugun.gugun_name, value: gugun.gugun_code });
      });
      gugunList.value = options;
    },
    (err) => {
      console.log(err);
    }
  );
  searchParam.value.areaCode = val;
};

const getContentTypeList = () => {
  listContentType(
    ({ data }) => {
      let options = [];
      options.push({ text: "유형선택", value: "" });
      options.push({ text: "전체", value: null });
      data.forEach((content) => {
        options.push({
          text: content.content_type_name,
          value: content.content_type_id,
        });
      });
      contentList.value = options;
    },
    (err) => {
      console.log(err);
    }
  );
};

const onChangeGugun = (val) => {
  searchParam.value.siGunGuCode = val;
};
const onChangeContentType = (val) => {
  searchParam.value.contentTypeId = val;
};

const getAttractions = () => {
  listContentType(
    (response) => {
      const contentTypes = response.data;
      console.log("Extracted content types:", contentTypes);

      listAttractions(
        searchParam.value,
        ({ data }) => {
          console.log("Raw attractions data:", data);

          // contentTypeName을 추가하여 새로운 배열 생성
          const updatedAttractions = data.map((attraction) => {
            const match = contentTypes.find(
              (type) => type.content_type_id === attraction.contentTypeId
            );
            return {
              ...attraction, // 기존 속성 복사
              contentTypeName: match ? match.content_type_name : null, // contentTypeName 추가
            };
          });

          // 결과를 저장
          emit("updateAttractions", updatedAttractions);
        },
        (err) => {
          console.log("Error fetching attractions:", err);
        }
      );
    },
    (err) => {
      console.log("Error fetching content types:", err);
    }
  );
};
</script>
<style>
.select-group {
  display: flex;
  gap: 5px;
  margin-bottom: 10px;
}

.search-container {
  position: absolute;
  top: 20px;
  left: 20px;
  background-color: rgba(255, 255, 255, 1);
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  width: 500px;
}

.toggle-button {
  width: 100%;
  background-color: #007bff;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
}

.results-list {
  height: 100vh;
  overflow-y: auto;
}

.results-list .attraction-card {
  display: flex;
  flex-direction: column;
  height: 100%;
}
</style>
