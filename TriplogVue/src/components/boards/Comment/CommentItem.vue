<script setup>
import { createComment, modifyComment, deleteComment } from "@/api/comment"
import { useMemberStore } from "@/stores/member"
import { storeToRefs } from "pinia"
import { ref } from 'vue';
import { useRoute } from "vue-router";
const props = defineProps({
  comment: {
    type: Object,
    required: true,
  },
});
const route = useRoute();
const { articleno } = route.params;
const memberStore = useMemberStore()
const { userInfo } = storeToRefs(memberStore)
const emit = defineEmits(["fetch-comments"]);

const isEditing = ref(false); // 수정 상태 여부
const isReplying = ref(false); // 답글 작성 상태 여부
const editedContent = ref(""); // 수정 중인 댓글 내용

function editComment() {
  if(userInfo.value.userNo !== props.comment.userNo){
    alert("작성자만 수정 가능!!!")
    return;
  }
  isEditing.value = true;
  editedContent.value = props.comment.content; // 현재 댓글 내용을 초기값으로 설정
}

function cancelEdit() {
  isEditing.value = false;
  editedContent.value = "";
}


async function updateComment() {
  await modifyComment(
    { commentNo: props.comment.commentNo, comment: editedContent.value, },
    (response) => {
      if (response.status == 200) {
        isEditing.value = false;
        emit("fetch-comments");
        editedContent.value = ""; // 작성란 초기화

      }
    },
    (error) => {
      console.error("댓글 수정 중 에러 발생:", error);
    }
  )
}

// 댓글 삭제
async function removeComment() {
  if(userInfo.value.userId !== 'admin' && userInfo.value.userNo !== props.comment.userNo){
    alert("작성자만 삭제 가능!!!")
    return;
  }
  await deleteComment(
    props.comment.commentNo,
    (response) => {
      if (response.status == 200) {
        emit("fetch-comments");
      }
    },
    (error) => {
      console.error("댓글 수정 중 에러 발생:", error);
    }
  );
}

// 답글 입력 토글
function toggleReplyInput() {
  isReplying.value = !isReplying.value;
  Reply.value.content = ""; // 입력란 초기화
}

// 답글 작성 취소
function cancelReply() {
  isReplying.value = false;
  Reply.value.content = "";
}

const Reply = ref({
  articleNo: articleno,
  content: "",
  parent: props.comment.commentNo,
  userNo: userInfo.value.userNo,

})

// 답글 작성
async function submitReply() {
  createComment(
    Reply.value,
    (response) => {
      if (response.status == 200) {
        isReplying.value = false;
        Reply.value.content = "";
        emit("fetch-comments");
      }
    },
    (error) => {
      console.error("댓글 작성 중 에러 발생:", error);
    }
  )
}

function updateEmit(){
  emit("fetch-comments");
}
</script>

<template>
  <li class="comment-item">
    <div>
      <img class="avatar me-2 float-md-start bg-light p-2" :src="comment.authorImage" style="
                 width: 50px;
                 height: 50px;
                 object-fit: cover;
                 object-position: center;
                 border-radius: 50%;
                " />
      <strong>{{ comment.author }}</strong>
      <span class="text-muted small">{{ comment.createAt }}</span>
      <p>
        <span v-if="comment.parent"></span>
        <span v-if="isEditing">
          <textarea v-model="editedContent" class="form-control mb-2"></textarea>
          <button class="btn btn-success btn-sm me-2" @click="updateComment">저장</button>
          <button class="btn btn-secondary btn-sm" @click="cancelEdit">취소</button>
        </span>
        <span v-else>
          <div v-if="comment.deleteAt === null"> {{ comment.content }}</div>
          <div style="color:lightgray" v-else>삭제된 사용자의 메시지 입니다.</div>
        </span>
      </p>
      <div class="d-flex justify-content-end">
        <button class="btn btn-primary btn-sm me-2" @click="editComment"
          v-if="!isReplying && !isEditing && comment.deleteAt === null">수정</button>
        <button class="btn btn-danger btn-sm me-2" v-if="!isReplying && !isEditing && comment.deleteAt === null" @click="removeComment">삭제</button>
        <button class="btn btn-success btn-sm" @click="toggleReplyInput" v-if="!isReplying && !isEditing && comment.deleteAt === null && comment.parent === 0">답글</button>
      </div>
      <!-- 답글 입력 -->
      <div v-if="isReplying" class="mt-2">
        <textarea v-model="Reply.content" class="form-control mb-2" rows="2" placeholder="답글을 입력하세요"></textarea>
        <button class="btn btn-success btn-sm me-2" @click="submitReply">답글 작성</button>
        <button class="btn btn-secondary btn-sm" @click="cancelReply">취소</button>
      </div>
    </div>
    <ul v-if="comment.children.length" class="replies">
      <CommentItem v-for="child in comment.children" :key="child.commentNo" :comment="child" @fetch-comments="updateEmit"/>
    </ul>
  </li>
</template>


<style>
.comment-item {
  margin-bottom: 1rem;
  border-bottom: 1px solid #ddd;
  padding-bottom: 1rem;
}

.replies {
  margin-left: 2rem;
  margin-top: 1rem;
}

textarea {
  width: 100%;
  resize: none;
}

.comment-list {
  list-style: none;
  padding: 0;
}

.comment-item {
  margin-bottom: 1rem;
  padding-left: 1rem;
}

.replies {
  padding-left: 1.5rem;
  border-left: 2px solid #ddd;
}
</style>
