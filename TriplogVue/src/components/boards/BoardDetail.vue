<script setup>
import { onMounted, onUnmounted, ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { deleteArticle, createStarArticle, IsStar, deleteStarArticle } from "@/api/board";
import { storeToRefs } from "pinia"
import { useMemberStore } from "@/stores/member"
import { useArticleStore } from "@/stores/article"
import ThePlanView from "@/views/ThePlanView.vue";
import Comment from "./Comment/CommentView.vue";
import PlanCard from "../plan/PlanCard.vue";
import { getPlan } from "@/api/plan";

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
const plan = ref({});

// const article = ref({
//   articleNo: articleno,
//   author: "",
//   authorImage: "",
//   content: "",
//   createdAt: "",
//   deletedAt: null,
//   hitCount: 0,
//   planNo: 1,
//   stars: 0,
//   title: "",
//   userNo: 0,
// });

const article = ref(null);

onMounted(async () => {
  try {
    // 첫 번째 비동기 작업: 게시글 정보 가져오기
    await getArticle(articleno);

    // 두 번째 비동기 작업: 즐겨찾기 상태 확인
    await IsStar(
      articleno,
      (response) => {
        isStar.value = response.data === 1;
      },
      (error) => {
        console.error(error);
      })
  } catch (error) {
    console.error("에러 발생:", error);
  }
});

onUnmounted(async () => {
  isStar.value = false;
});

watch(articleInfo, (newArticleInfo) => {
  if (newArticleInfo) {
    article.value = { ...newArticleInfo }; // articleInfo 값을 article로 갱신
    getPlan(
      article.value.planNo,
      (response) => {
        plan.value = response.data;
        isLoad.value = true; // 로드 완료 표시
      },
      (err) => {
        console.error(err);
      }
    );
    isLoad.value = true;
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
  article.value.stars = article.value.stars + 1;
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
  article.value.stars = article.value.stars - 1;
}

function onDeleteArticle() {
  if (userInfo.value.userId !== "admin" && article.value.userNo != userInfo.value.userNo) {
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

function showPlan() {
  router.push({
    name: "planDetail",
    params: { planNo: plan.value.planNo, isArticle: true },
  });
}

</script>

<template>
  <div v-if="isLoad" class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
      </div>
      <div class="col-lg-10 text-start">
        <div class="row my-2">
          <h2 class="text-secondary fw-bold">{{ article.title }}</h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img class="avatar me-2 float-md-start bg-light p-2" :src="articleInfo.authorImage" style="
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
          <div class="col-md-4 align-self-center text-end">
            <p>
              <span>조회수: {{ article.hitCount }}</span> <br />
              <span>
                ⭐: {{ article.stars }}
              </span>
            </p>

          </div>
          <div class="divider mb-3"></div>
          <div class="clearfix align-content-center content-container" v-html="articleInfo.content"></div>
          <div class="divider mt-3 mb-3"></div>
          <div v-if="article.planNo != 0">
            <PlanCard :plan="plan" @click="showPlan"></PlanCard>
          </div>
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
        <Comment :a_no=articleno />
      </div>
    </div>
  </div>
</template>

<style scoped>
.content-container {
  max-width: 100%;
  /* 부모 요소의 너비를 제한 */
  overflow: hidden;
  /* 콘텐츠가 벗어나지 않도록 제한 */
}

/* v-html에서 렌더링된 이미지 스타일 */
.content-container img {
  max-width: 100%;
  /* 이미지를 부모 요소의 너비에 맞춤 */
  height: auto;
  /* 비율을 유지하며 크기를 조정 */
  display: block;
  /* 이미지가 inline 요소로 간주되지 않도록 설정 */
  margin: 0 auto;
  /* 이미지 중앙 정렬 */
}
</style>
