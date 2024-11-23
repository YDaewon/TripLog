<style scoped></style>
<script setup>
import { storeToRefs } from "pinia"
import { useMemberStore } from "@/stores/member"
import { listArticle } from "@/api/board.js";
import BoardList from "@/components/boards/BoardList.vue";

const memberStore = useMemberStore()

const { userInfo } = storeToRefs(memberStore)
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;

const initialParam = {
  pgno: 1,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: "",
  userNo: userInfo.value.userNo,
  word: "",
};

const subtitle = "내 게시글";

const selectOptions = [
  { text: "검색조건", value: "" },
  { text: "글번호", value: "article_no" },
  { text: "제목", value: "title" },
];

</script>

<template>
  <BoardList
    :fetch-articles="listArticle"
    :initial-param="initialParam"
    :select-options="selectOptions"
    :subtitle="subtitle"
  />
</template>
