<script setup>
import { onMounted, ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { deleteArticle, createStarArticle, IsStar, deleteStarArticle } from "@/api/board";
import { storeToRefs } from "pinia"
import { useMemberStore } from "@/stores/member"
import { useArticleStore } from "@/stores/article"

const route = useRoute();
const router = useRouter();

// const articleno = ref(route.params.articleno);
const { articleno } = route.params;


const memberStore = useMemberStore()
const { userInfo } = storeToRefs(memberStore)
const articleStore = useArticleStore()
const { articleInfo } = storeToRefs(articleStore)
const { getArticle } = articleStore;
const isStar = ref(false);
const isLoad = ref(false);

const article = ref({
  articleNo: 0,
  author: "",
  content: "",
  createdAt: "",
  deletedAt: null,
  hitCount: 0,
  planNo: 0,
  stars: 0,
  title: "",
  userNo: 0,
});

onMounted(async () => {
  await getArticle(articleno);
  await IsStar(
    articleno,
    (response) => {
      if (response.data != 0) {
        isStar.value = true;
        //console.log("현재 게시글 즐겨찾기 되어있음")
      }
      else {
        isStar.value = false;
      }
    },
    (error) => {
      console.error(error);
    }
  );
  isLoad.value = true; // 로드 완료 표시
});

watch(articleInfo, (newArticleInfo) => {
  if (newArticleInfo) {
    article.value = { ...newArticleInfo }; // articleInfo 값을 article로 갱신
  }
});

function moveList() {
  router.go(-1);
  //router.push({ name: "article-list" });
}

function moveModify() {
  //console.log(article.value.userNo + ", " + userInfo.value.userNo)
  if (article.value.userNo != userInfo.value.userNo) {
    alert("작성자 외 수정 금지!")
  }
  else router.push({ name: "article-modify", params: { articleno } });
}

function createStar() {
  createStarArticle(
    articleno,
    (response) => {
      if (response.status == 200) {
        console.log("즐겨찾기 추가 완료")
        isStar.value = true;
      }
    },
    (error) => {
      console.error(error);
    }
  );
}

function deleteStar() {
  deleteStarArticle(
    articleno,
    (response) => {
      if (response.status == 200) {
        console.log("즐겨찾기 삭제 완료")
        isStar.value = false;
      }
    },
    (error) => {
      console.error(error);
    }
  );
}

function onDeleteArticle() {
  if (article.value.userNo != userInfo.value.userNo) {
    alert("작성자 외 삭제 금지!")
  }
  else {
    deleteArticle(
      articleno,
      (response) => {
        if (response.status == 200) moveList();
      },
      (error) => {
        console.error(error);
      }
    );
  }
}
</script>

<template>
  <div v-if="isLoad" class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
      </div>
      <div class="col-lg-10 text-start">
        <div class="row my-2">
          <h2 class="text-secondary">{{ article.title }}</h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img class="avatar me-2 float-md-start bg-light p-2" :src="userInfo.userImage" style="
                 width: 50px;
                 height: 50px;
                 object-fit: cover;
                 object-position: center;
                 border-radius: 50%;
                " />
              <p>
                <span class="fw-bold">{{ article.author }}</span> <br />
                <span class="text-secondary fw-light">
                  {{ article.createdAt }}
                </span>
              </p>
            </div>
          </div>
          <div class="col-md-4 align-self-center text-end">조회수: {{ article.hitCount }}</div>
          <div class="divider mb-3"></div>
          <div v-html="articleInfo.content"></div>
          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
            <button type="button" v-if="!isStar" class="btn btn-outline-warning mb-3" @click="createStar">
              즐겨찾기 추가
            </button>
            <button type="button" v-if="isStar" class="btn btn-outline-warning mb-3" @click="deleteStar">
              즐겨찾기 삭제
            </button>
            <button type="button" class="btn btn-outline-primary mb-3" @click="moveList">
              글목록
            </button>
            <button type="button" class="btn btn-outline-success mb-3 ms-1" @click="moveModify">
              글수정
            </button>
            <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="onDeleteArticle">
              글삭제
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
