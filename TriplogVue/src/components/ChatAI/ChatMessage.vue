<template>
  <div class="chat-container">
    <div class="message-wrapper" :class="{ 'user-message': sender === 'user' }">
      <div class="message" :class="sender">
        <div class="message-content">
          <p class="text" v-html="message"></p> <!-- HTML로 렌더링 -->
          <span class="timestamp">{{ formatTimestamp(timestamp) }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    message: String,
    sender: String,
    timestamp: String,
  },
  methods: {
    formatTimestamp(timestamp) {
      return new Date(timestamp).toLocaleTimeString('ko-KR', {
        hour: '2-digit',
        minute: '2-digit',
      });
    },
  },
};
</script>

<style scoped>
.chat-container {
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
  padding: 10px;
}

.message-wrapper {
  display: flex;
  margin-bottom: 12px;
  justify-content: flex-start;
}

.message-wrapper.user-message {
  justify-content: flex-end;
}

.message {
  max-width: 70%;
  padding: 12px 16px;
  border-radius: 16px;
  position: relative;
  word-wrap: break-word;
}

.message.bot {
  background-color: #f0f0f0;
  border-top-left-radius: 4px;
  margin-right: 40px;
}

.message.user {
  background-color: lightseagreen;
  color: white;
  border-top-right-radius: 4px;
  margin-left: 40px;
}

.message-content {
  position: relative;
}

.text {
  margin: 0;
  font-size: 14px;
  line-height: 1.4;
}

.timestamp {
  display: block;
  font-size: 11px;
  margin-top: 4px;
  opacity: 0.8;
}

.user .timestamp {
  color: rgba(255, 255, 255, 0.8);
}

.bot .timestamp {
  color: #666;
}
</style>
