<script setup>
import { ref } from "vue";
import { RouterView } from "vue-router";
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
import ChatBot from "./components/ChatAI/ChatBot.vue";

// 챗봇 표시 여부 상태 관리
const isChatbotVisible = ref(false);

const toggleChatbot = () => {
  isChatbotVisible.value = !isChatbotVisible.value;
};
</script>

<template>
  <div>
    <TheHeadingNavbar></TheHeadingNavbar>
    <router-view></router-view>
    <!-- 이미지가 담긴 동그란 버튼 -->
    <button
      @click="toggleChatbot"
      class="position-fixed rounded-circle chatbot-toggle-btn"
    >
      <img src="/chatbot.jpg" alt="Chat Icon" class="img-fluid" />
    </button>

    <!-- 챗봇 컴포넌트 -->
    <div
      v-if="isChatbotVisible"
      class="position-fixed bg-white border shadow-lg chatbot-wrapper"
    >
      <ChatBot />
    </div>
  </div>
</template>

<style>
/* 동그란 버튼 스타일 */
.chatbot-toggle-btn {
  bottom: 20px;
  right: 20px;
  width: 60px;
  height: 60px;
  padding: 0;
  background-color: lightseagreen;
  border: none;
  z-index: 1050;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

.chatbot-toggle-btn img {
  width: 70%; /* 이미지 크기 조정 */
  height: 70%;
  object-fit: contain;
  border-radius: 50%;
}

.chatbot-toggle-btn:hover {
  background-color: rgb(16, 94, 90);
}

/* 챗봇 컴포넌트 스타일 */
.chatbot-wrapper {
  bottom: 80px;
  right: 20px;
  width: 300px;
  height: 400px;
  z-index: 1050;
  border-radius: 10px;
  overflow: hidden;
}

/* 반응형 위치 조정 */
@media (max-width: 768px) {
  .chatbot-toggle-btn {
    bottom: 10px;
    right: 10px;
    width: 50px;
    height: 50px;
  }
  .chatbot-wrapper {
    bottom: 70px;
    right: 10px;
    width: 250px;
    height: 350px;
  }
}

@media (max-width: 480px) {
  .chatbot-toggle-btn {
    bottom: 5px;
    right: 5px;
    width: 40px;
    height: 40px;
  }
  .chatbot-wrapper {
    bottom: 60px;
    right: 5px;
    width: 220px;
    height: 300px;
  }
}
</style>
