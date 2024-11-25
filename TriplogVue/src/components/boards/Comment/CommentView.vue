<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import CommentItem from "./CommentItem.vue";
import { getComments, createComment } from "@/api/comment"
import { useMemberStore } from "@/stores/member"
import { storeToRefs } from "pinia"

const route = useRoute();

const memberStore = useMemberStore()
const { userInfo } = storeToRefs(memberStore)

// 댓글 데이터 관리
const comments = ref([]);
const commentTree = ref([]);
const { articleno } = route.params;
// 새 댓글 작성
const Comment = ref({
  articleNo: articleno,
  content: "",
  parent: 0,
  userNo: userInfo.value.userNo,
});

// 댓글 추가 함수
async function addComment() {
  createComment(
    Comment.value,
    (response) => {
      if (response.status == 200) {
        fetchComments(); // 댓글 목록 업데이트
        Comment.value.content = ""; // 작성란 초기화
      }
    },
    (error) => {
      console.error("댓글 작성 중 에러 발생:", error);
    }
  )
}

onMounted(() => {
  fetchComments();
});

// 댓글 목록 가져오기 함수
async function fetchComments() {
  getComments(
    articleno,
    (response) => {
      if (response.status == 200) {
        comments.value = response.data;
        console.log(response.data)
        commentTree.value = createCommentTree(comments.value);
      }
    },
    (error) => {
      console.error(error);
    }
  );
}


function createCommentTree(comments) {
  const commentMap = new Map();
  const roots = [];

  comments.forEach((comment) => {
    commentMap.set(comment.commentNo, { ...comment, children: [] });
  });

  comments.forEach((comment) => {
    if (comment.parent) {
      const parent = commentMap.get(comment.parent);
      if (parent) {
        parent.children.push(commentMap.get(comment.commentNo));
      }
    } else {
      roots.push(commentMap.get(comment.commentNo));
    }
  });

  return roots;
}
</script>

<template>
  <!-- 댓글 기능 구현 -->
  <div>
    <div class="row mt-4">
      <div class="col-12">
        <h4 class="fw-bold">댓글 작성</h4>
        <textarea v-model="Comment.content" class="form-control" rows="3" placeholder="댓글을 입력하세요."></textarea>
        <div class="d-flex justify-content-end mt-2">
          <button type="button" class="btn btn-primary" :disabled="!Comment.content" @click="addComment">
            댓글 작성
          </button>
        </div>
      </div>
    </div>

    <div>
      <h4>댓글</h4>
      <ul class="comment-list">
        <CommentItem v-for="comment in commentTree" :key="comment.commentNo" :comment="comment" @fetch-comments="fetchComments" />
      </ul>
    </div>
  </div>

</template>

<style scoped>
.list-group-item {
  border: 1px solid #ddd;
  border-radius: 5px;
}

textarea:focus {
  outline: none;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}
</style>
