<script setup>
import { ref, watch } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia"
import { useMemberStore } from "@/stores/member"
import { useArticleStore } from "@/stores/article"
import { registArticle, modifyArticle } from "@/api/board";
import ArticleEditor from "@/components/boards/item/ArticleEditor.vue";


const router = useRouter();

const props = defineProps({ type: String });

const memberStore = useMemberStore()

const { userInfo } = storeToRefs(memberStore)

const articleStore = useArticleStore()
const { articleInfo } = storeToRefs(articleStore)

const article = ref({
  articleNo: articleInfo.value.articleNo,
  title: articleInfo.value.title,
  content: articleInfo.value.content,
  userNo: userInfo.value.userNo,
});

if (props.type !== 'modify') {
  article.value.title = "";
  article.value.content = "";
  articleInfo.value.content = "";
}

const titleErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => article.value.title,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 50) {
      titleErrMsg.value = "제목을 확인해 주세요!!!";
    } else {
      articleInfo.value.title = value;
      titleErrMsg.value = "";
    }
  },
  { immediate: true }
);
watch(
  () => articleInfo.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30000000) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else {
      article.value.content = value;
      contentErrMsg.value = "";
    }
  },
  { immediate: true }
);

function onSubmit() {
  if (titleErrMsg.value) {
    alert(titleErrMsg.value);
  } else if (contentErrMsg.value) {
    console.log(article.value.content)
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  registArticle(
    article.value,
    (response) => {
      let msg = "글등록 처리시 문제 발생했습니다.";
      if (response.status == 201) msg = "글등록이 완료되었습니다.";
      alert(msg);
      moveList();
    },
    (error) => console.error(error)
  );
}

function updateArticle() {
  modifyArticle(
    article.value,
    (response) => {
      let msg = "글수정 처리시 문제 발생했습니다.";
      if (response.status == 200) msg = "글정보 수정이 완료되었습니다.";
      alert(msg);
      moveList();
      // router.push({ name: "article-view" });
      // router.push(`/board/view/${article.value.articleNo}`);
    },
    (error) => console.log(error)
  );
}

function moveList() {
  router.push({ name: "article-list" });
}
</script>


<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <label for="subject" class="form-label">제목 : </label>
      <input type="text" class="form-control" v-model="article.title" placeholder="제목..." />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <div class="editor-container editor-container_classic-editor editor-container_include-block-toolbar"
        ref="editorContainerElement">
        <div class="editor-container__editor">
          <div ref="editorElement">
            <ArticleEditor />
          </div>
        </div>
      </div>
    </div>
    <div class="col-auto text-center">
      <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'regist'">
        글작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>글수정</button>
      <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveList">
        목록으로이동...
      </button>
    </div>
  </form>
</template>
