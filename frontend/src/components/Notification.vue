<template>
  <transition name="fade">
    <div v-if="globalState.notification.value" class="notification">
      <span>{{ globalState.notification.value }}</span>
      <button @click="close">✕</button>
    </div>
  </transition>
</template>

<script setup>
import { onMounted, onUnmounted, watch } from 'vue'
import { useGlobalState } from '@/stores/GlobalStore'

const globalState = useGlobalState()

const close = () => {
  globalState.hideNotification()
}

let timer

const startTimer = () => {
  clearTimeout(timer)
  timer = setTimeout(() => {
    close()
  }, 5000)
}

onMounted(() => {
  if (globalState.notification.value) {
    startTimer()
  }
})

onUnmounted(() => {
  clearTimeout(timer)
})

watch(
  () => globalState.notification.value,
  (newValue) => {
    if (newValue) {
      startTimer()
    }
  }
)
</script>

<style lang="scss" scoped>
$main-bg-color: #1b1b23;
$elem-bg-color: #212636;
$accent-color: #882ee6;
$text-color: #757575;

.notification {
  position: fixed;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  background-color: rgba($elem-bg-color, 0.7);
  backdrop-filter: blur(2px);
  color: $accent-color;
  font-weight: 500;
  font-size: 16px;
  padding: 20px 30px;
  border-radius: 5px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 300px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.notification button {
  background: none;
  border: none;
  color: $accent-color;
  font-size: 20px;
  cursor: pointer;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s, transform 0.3s;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateX(-50%) translateY(-20px);
}
</style>
