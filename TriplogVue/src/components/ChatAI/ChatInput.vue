<template>
  <div class="chat-input">
    <input
      v-model="userInput"
      placeholder="메시지를 입력해주세요"
      @keyup.enter="sendMessage"
    />
    <button @click="sendMessage">
      <img :src="sendIconPath" alt="Send" />
    </button>
  </div>
</template>

<script>
import { localAxios } from "@/util/http-commons";

const local = localAxios();

export default {
  name: 'ChatInput',
  data() {
    return {
      userInput: '',
      sendIconPath: new URL('/send.png', import.meta.url).href,
    };
  },
  methods: {
    async sendMessage() {
      if (!this.userInput.trim()) return;
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
      this.$emit('message-sent', {
        sender: "user",
        text: this.userInput,
        timestamp: formattedDate
      });

      try {
        const temp = this.userInput;
        this.userInput = "";
        const response = await local.post("/api/chat", { message: temp });

        if (response.data && response.data.botReply) {
          this.$emit('bot-reply', {
            sender: "bot",
            text: this.formatResponse(response.data.botReply),
            timestamp: response.data.timestamp || new Date().toLocaleTimeString(),
          });
        } else {
          this.$emit('bot-reply', {
            sender: "bot",
            text: "No reply found in the response.",
            timestamp: new Date().toLocaleTimeString(),
          });
        }
      } catch (error) {
        console.error("Error:", error);
        this.$emit('bot-reply', {
          sender: "bot",
          text: "에러 발생! 나중에 다시 질문해주세요",
          timestamp: new Date().toLocaleTimeString(),
        });
      }

    },

    formatResponse(response) {
      response = response.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');
      response = response.replace(/\*(.*?)\*/g, '<em>$1</em>');
      response = response.replace(/\n/g, '<br>');
      return response;
    }
  }
}
</script>

<style scoped>
.chat-input {
  display: flex;
  justify-content: space-between;
  padding: 10px;
  background-color: #fff;
  border-top: 1px solid #ddd;
  position: sticky;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 1;
}

.chat-input input {
  width: 80%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 24px;
  font-size: 14px;
  outline: none;
}

.chat-input button {
  width: 15%;
  padding: 5px;
  padding-right: 6px;
  background-color: lightseagreen;
  border: none;
  border-radius: 50%;
  color: white;
  cursor: pointer;
}

.chat-input button img {
  width: 25px;
  height: 25px;
}

.chat-input button:hover {
  background-color: lightseagreen;
}
</style>
