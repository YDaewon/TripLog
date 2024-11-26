<template>
  <div class="chat-input">
    <input
      v-model="userInput"
      placeholder="Type your message..."
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

      this.$emit('message-sent', {
        sender: "user", 
        text: this.userInput, 
        timestamp: new Date().toLocaleTimeString() 
      });

      try {
        const response = await local.post("/api/chat", { message: this.userInput });

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
          text: "Oops! Unable to process your request. Please try again later.",
          timestamp: new Date().toLocaleTimeString(),
        });
      }

      this.userInput = "";
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