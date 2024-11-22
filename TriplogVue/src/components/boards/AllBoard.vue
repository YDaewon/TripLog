<script setup>
import { listArticle } from "@/api/board.js";
import BoardList from "@/components/boards/BoardList.vue";

const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;

const initialParam = {
  pgno: 1,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: "",
  word: "",
};

const subtitle = "전체 게시판";

const selectOptions = [
  { text: "검색조건", value: "" },
  { text: "글번호", value: "article_no" },
  { text: "제목", value: "subject" },
  { text: "작성자아이디", value: "user_id" },
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