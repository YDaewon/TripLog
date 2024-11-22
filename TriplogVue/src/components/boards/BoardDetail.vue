<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/board";
import { storeToRefs } from "pinia"
import { useMemberStore } from "@/stores/member"


const route = useRoute();
const router = useRouter();

// const articleno = ref(route.params.articleno);
const { articleno } = route.params;


const memberStore = useMemberStore()
const { userInfo } = storeToRefs(memberStore)
const article = ref({});

onMounted(() => {
  getArticle();
});

const getArticle = () => {
  detailArticle(
    articleno,
    ({ data }) => {
      article.value = data;
    },
    (error) => {
      console.error(error);
    }
  );
};

function moveList() {
  router.push({ name: "article-list" });
}

function moveModify() {
  //console.log(article.value.userNo + ", " + userInfo.value.userNo)
  if(article.value.userNo != userInfo.value.userNo){
    alert("작성자 외 수정 금지!")
  }
  else router.push({ name: "article-modify", params: { articleno } });
}

function onDeleteArticle() {
  if(article.value.userNo != userInfo.value.userNo){
    alert("작성자 외 삭제 금지!")
  }
  else{
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
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글보기</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <div class="row my-2">
          <h2 class="text-secondary px-5">{{ article.articleNo }}. {{ article.title }}</h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img
                class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
              />
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
          <div class="text-secondary">
            {{ article.content }}
          </div>
          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
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
