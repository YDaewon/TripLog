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
    // 컴포넌트가 로드될 때 자동으로 인사 메시지 추가
    this.messages.push({
      sender: "bot",
      text: "현재 상담사가 부재중이므로 23:00 이후 요청주세요",
      timestamp: new Date().toLocaleTimeString(),
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
