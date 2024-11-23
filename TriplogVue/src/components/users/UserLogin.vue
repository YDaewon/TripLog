<script setup>
import { ref } from "vue"
import { storeToRefs } from "pinia"
import { useRouter } from "vue-router"
import { useMemberStore } from "@/stores/member"
import { useMenuStore } from "@/stores/menu"

const router = useRouter()

const memberStore = useMemberStore()

const { isLogin, isLoginError } = storeToRefs(memberStore)
const { userLogin, getUserInfo } = memberStore
const { changeMenuState } = useMenuStore()

const loginUser = ref({
  userId: "",
  userPwd: "",
})

const login = async () => {
  await userLogin(loginUser.value)
  let token = sessionStorage.getItem("accessToken")
  console.log("Access-Token: " + token)
  //console.log("isLogin: " + isLogin.value)
  if (isLogin.value) {
    getUserInfo()
    changeMenuState(true)
    router.replace("/")
  }
}
</script>

<template>
  <div class="container">
    <div class="card shadow">
        <div class="card-body p-4">
            <h2 class="text-center mb-4">로그인</h2>
            <form>
              <div class="form-check mb-3 float-end">
                <input class="form-check-input" type="checkbox" />
                <label class="form-check-label" for="saveid"> 아이디저장 </label>
              </div>
              <div class="mb-3 text-start">
                <label for="userid" class="form-label">ID : </label>
                <input
                  type="text"
                  class="form-control"
                  v-model="loginUser.userId"
                  placeholder="ID..."
                />
              </div>
              <div class="mb-3 text-start">
                <label for="userpwd" class="form-label">Password : </label>
                <input
                  type="password"
                  class="form-control"
                  v-model="loginUser.userPwd"
                  @keyup.enter="login"
                  placeholder="Password..."
                />
              </div>
              <div class="mb-3 text-start" v-if="isLoginError === true">
                <div class="alert alert-danger" role="alert">아이디 또는 비밀번호 확인해 주세요</div>
              </div>
            </form>
            <div class="d-grid">
              <button type="submit" class="btn btn-dark" @click="login">
                로그인
              </button>
            </div>
            <div class="text-center mt-3">
              <router-link :to="{ name: 'user-join' }" class="navbar-brand">
                <a href="#" class="text-decoration-none text-muted">회원가입</a>
              </router-link>
            </div>
        </div>
    </div>
  </div>
</template>

<style scoped>
        html, body {
            height: 100%;
        }
        body {
            display: flex;
            align-items: center;
            background-color: #f8f9fa;
        }
        .card {
            max-width: 400px;
            width: 100%;
            margin: auto;
        }
</style>
