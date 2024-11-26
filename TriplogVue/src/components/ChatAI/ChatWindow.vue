<template>
  <div class="chat-window" ref="chatWindow">
    <div class="message-container">
      <div>
        <ChatMessage
          v-for="(message, index) in messages"
          :key="index"
          :message="message.text"
          :sender="message.sender"
          :timestamp="message.timestamp"
        />
      </div>
    </div>
  </div>
</template>

<script>
import ChatMessage from "./ChatMessage.vue";

export default {
  name: "ChatWindow",
  components: {
    ChatMessage,
  },
  props: {
    messages: {
      type: Array,
      required: true,
    },
  },
  methods: {
    scrollToBottom() {
      const chatWindow = this.$refs.chatWindow;
      if (chatWindow) {
        this.$nextTick(() => {
          chatWindow.scrollTop = chatWindow.scrollHeight;
        });
      }
    },
  },
  updated() {
    this.$nextTick(() => {
      this.scrollToBottom();
    });
  },
};
</script>

<style scoped>
.chat-window {
  flex-grow: 1;
  padding: 20px;
  overflow-y: auto;
  overflow-x: hidden;
  background-color: #fafafa;
  border-bottom: 1px solid #ddd;
  display: flex;
  flex-direction: column;
  height: calc(100% - 116px); /* Adjust for header and input area */
}

.message-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
  min-height: 100%;
  padding-bottom: 20px; /* Add some padding at the bottom */
}
</style>
