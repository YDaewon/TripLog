<script setup>
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";

import VSelect from "@/components/common/VSelect.vue";
import BoardListItem from "@/components/boards/item/BoardListItem.vue";
import VPageNavigation from "@/components/common/VPageNavigation.vue";


const props = defineProps({
  fetchArticles: Function, // 게시글 데이터를 가져오는 함수
  initialParam: Object,    // 초기 `param` 값
  selectOptions: Array,    // 검색 옵션
  subtitle: String,
});

const router = useRouter();

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(1);

const param = ref({ ...props.initialParam }); // 초기값 복사

// 검색 키 변경

const changeKey = (val) => {
  param.value.key = val;
};


// 게시글 데이터 가져오기
const getArticles = () => {
  props.fetchArticles(

    param.value,
    ({ data }) => {
      articles.value = data.articles;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;

      console.log(articles.value)

    },
    (error) => {
      console.error(error);
    }
  );
};


// 페이지 변경 처리
const onPageChange = (val) => {
  currentPage.value = val;
  param.value.pgno = val;
  getArticles();
};

// 글쓰기 페이지로 이동
const moveWrite = () => {
  router.push({ name: "article-write" });
};

onMounted(() => {
  getArticles();
});

// `initialParam`이 변경될 경우 데이터 다시 로드
watch(() => props.initialParam, (newParam) => {
  Object.assign(param.value, newParam);
  getArticles();
}, { deep: true });
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          {{ subtitle }}
        </h2>
      </div>
      <div class="col-lg-10">
        <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start">
            <button type="button" class="btn btn-outline-primary btn-sm" @click="moveWrite">
              글쓰기
            </button>
          </div>
          <div class="col-md-5 offset-5">
            <form class="d-flex">

              <VSelect :selectOption="selectOptions" @onKeySelect="changeKey" />

              <div class="input-group input-group-sm ms-1">
                <input
                  type="text"
                  class="form-control"
                  v-model="param.word"
                  placeholder="검색어..."
                />

                <button class="btn btn-dark" type="button" @click="getArticles">검색</button>

              </div>
            </form>
          </div>
        </div>
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">글번호</th>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">조회수</th>
              <th scope="col">⭐</th>
              <th scope="col">작성일</th>
            </tr>
          </thead>
          <tbody>
            <BoardListItem
              v-for="article in articles"
              :key="article.articleNo"
              :article="article"
            ></BoardListItem>
          </tbody>
        </table>
      </div>
      <VPageNavigation
        :current-page="currentPage"
        :total-page="totalPage"
        @pageChange="onPageChange"
      ></VPageNavigation>
    </div>
  </div>
</template>

<style scoped></style>

