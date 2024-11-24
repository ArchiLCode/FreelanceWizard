<template>
  <div class="register">
    <Header />
    <div class="container">
      <form>
        <h2>REGISTER</h2>
        <input
          type="text"
          id="firstname"
          v-model="firstname"
          required
          placeholder="FirstName"
        />
        <input
          type="text"
          id="lastname"
          v-model="lastname"
          required
          placeholder="LastName"
        />
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
          @click="submit"
          btnText="Register"
        />
        <p>
          Already have an account?
          <span @click="router.push('/login')">Log in</span>
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
const firstname = ref('')
const lastname = ref('')
const globalState = useGlobalState()

const submit = async () => {
  if (!validateForm()) {
    return
  }

  const url = 'http://localhost:8081/auth/register'
  const userData = {
    first_name: firstname.value,
    last_name: lastname.value,
    email: email.value,
    password: password.value,
  }

  try {
    await AXIOS.post(url, userData)
    router.push('/login')
  } catch (error) {
    globalState.showNotification(
      'User with this email has already been registered, try again please try again!'
    )
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

.register {
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
