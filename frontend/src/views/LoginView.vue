<template>
  <div class="login">
    <Header />
    <div class="container">
      <form>
        <h2>LOG IN</h2>
        <input
          type="email"
          id="email"
          v-model="email"
          required
          placeholder="Email"
        />
        <input
          type="password"
          id="password"
          v-model="password"
          required
          placeholder="Password"
        />
        <FilledButton
          style="width: 40%; padding: 15px; font-size: 20px; margin-top: 10px"
          btnText="Log In"
          @click="submit"
        />
        <p>
          No account? <span @click="router.push('/register')">Register</span>
        </p>
      </form>
    </div>
  </div>
</template>

<script setup>
import AXIOS from '@/axios'
import FilledButton from '@/components/FilledButton.vue'
import Header from '@/components/Header.vue'
import router from '@/router'
import { useGlobalState } from '@/stores/GlobalStore'
import { ref, computed } from 'vue'

const email = ref('')
const password = ref('')
const globalState = useGlobalState()

const submit = async () => {
  if (!validateForm()) {
    return
  }

  const url = 'http://localhost:8081/auth/login'
  const userData = {
    email: email.value,
    password: password.value,
  }

  try {
    const response = await AXIOS.post(url, userData)

    globalState.setToken(response.data.token)
    globalState.setUserId(response.data.user_id)
    router.push('/')
  } catch (error) {
    globalState.showNotification('Invalid email or password, please try again!')
  }
}

const validateForm = () => {
  if (!email.value || !password.value) {
    globalState.showNotification('Please fill in all fields!')
    return false
  }

  if (!isEmailValid.value) {
    globalState.showNotification('Please enter a valid email address!')
    return false
  }

  if (!isPasswordValid.value) {
    globalState.showNotification(
      'Password must be at least 8 characters long and contain both letters and numbers!'
    )
    return false
  }

  return true
}

const isEmailValid = computed(() => {
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  return re.test(email.value)
})

const isPasswordValid = computed(() => {
  const re = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/
  return re.test(password.value)
})
</script>

<style scoped lang="scss">
$main-bg-color: #1b1b23;
$elem-bg-color: #212636;
$accent-color: #882ee6;
$text-color: #757575;

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 100%;
  height: calc(100vh - 108.45px);
  max-width: 1228px;
  margin: 0 auto;
}

.login {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  min-height: 100vh;
  width: 80%;
  max-width: 1228px;
  margin: 0 auto;
}

p {
  font-size: 16px;
  color: $text-color;

  span {
    cursor: pointer;
    color: $accent-color;
  }
}

h2 {
  color: $accent-color;
  font-weight: 600;
  font-size: 32px;
  margin-bottom: 10px;
}

form {
  width: 35%;
  margin: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

input {
  width: 100%;
  color: $accent-color;
  font-size: 18px;
  font-weight: 500;
  font-family: Gilroy;
  background-color: transparent;
  border: 2px solid $accent-color;
  border-radius: 5px;
  padding: 10px;
  transition: 0.2s;

  &:focus {
    outline: none;
    background-color: $elem-bg-color;
    box-shadow: 0px 2px 8px #212121;
  }
}
</style>
