<template>
  <div class="chatbot">
    <ChatHeader />
    <ChatWindow :messages="messages" />
    <ChatInput
      @message-sent="addUserMessage"
      @bot-reply="addBotMessage"
    />
  </div>
</template>

<script>
import ChatHeader from './ChatHeader.vue';
import ChatWindow from './ChatWindow.vue';
import ChatInput from './ChatInput.vue';

export default {
  data() {
    return {
      messages: []
    };
  },
  mounted() {
    const now = new Date();
    const formattedDate = now.toLocaleString("ko-KR", {
      timeZone: "Asia/Seoul", // 한국 시간대
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
      hour: "2-digit",
      minute: "2-digit",
      second: "2-digit",
      hour12: false, // 24시간 형식
    }).replace(/\./g, "-").replace(/ /, " "); // 포맷 조정

    // 컴포넌트가 로드될 때 자동으로 인사 메시지 추가
    this.messages.push({
      sender: "bot",
      text: "무엇이든 물어보세요",
      timestamp: formattedDate,
    });
  },
  methods: {
    addUserMessage(message) {
      this.messages.push(message);
    },
    addBotMessage(message) {
      this.messages.push(message);
    }
  },
  components: {
    ChatHeader,
    ChatWindow,
    ChatInput
  }
}
</script>

<style scoped>
.chatbot {
  flex-direction: column;
  height: 80vh;
  max-height:100%;
  max-width: 600px;
  margin: 0 auto;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  position: relative;
}
</style>
