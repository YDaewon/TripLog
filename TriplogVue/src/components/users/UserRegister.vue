<script setup>
import { ref } from "vue"
import { storeToRefs } from "pinia"
import { useRouter } from "vue-router"
import { useMemberStore } from "@/stores/member"

const router = useRouter()

const memberStore = useMemberStore()

const { isRegister } = storeToRefs(memberStore)
const { userRegist } = memberStore

const RegisterUser = ref({
  userId: "dog",
  userPwd: "dog",
  userName: "dog",
  pwdCheck: "dog",
  nickname: "dog",
  emailId: "dog",
  emailDomain: "dog"
})

const register = async () => {
  if (RegisterUser.value.userPwd !== RegisterUser.value.pwdCheck) {
    alert("비밀번호가 불일치 합니다.")
  }
  else {
    await userRegist(RegisterUser.value)
    if (isRegister.value) {
      router.replace("/")
    }
  }
}
</script>

<template>

  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  </head>

  <body >
    <div class="container">
      <div style="text-align: left;" class="row justify-content-center align-items-center min-vh-80">
        <div class="col-md-6">
          <div class="card shadow">
            <div class="card-body p-5">
              <h2 class="text-center mb-4">회원가입</h2>
              <form>
                <div class="mb-3">
                  <label for="userName" class="form-label">이름 : </label>
                  <input type="text" v-model="RegisterUser.userName" class="form-control" placeholder="이름..." />
                </div>
                <div class="mb-3">
                  <label for="nickname" class="form-label">닉네임 : </label>
                  <input type="text" v-model="RegisterUser.nickname" class="form-control" placeholder="닉네임..." />
                </div>
                <div class="mb-3">
                  <label for="userId" class="form-label">아이디 : </label>
                  <input type="text" v-model="RegisterUser.userId" class="form-control" placeholder="아이디..." />
                </div>
                <div class="mb-3">
                  <label for="userPwd" class="form-label">비밀번호 : </label>
                  <input type="text" v-model="RegisterUser.userPwd" class="form-control" placeholder="비밀번호..." />
                </div>
                <div class="mb-3">
                  <label for="pwdcheck" class="form-label">비밀번호확인 : </label>
                  <input type="text" v-model="RegisterUser.pwdCheck" class="form-control" id="pwdcheck"
                    placeholder="비밀번호확인..." />
                </div>
                <div class="mb-3">
                  <label for="emailId" class="form-label">이메일 : </label>
                  <div class="input-group">
                    <input type="text" v-model="RegisterUser.emailId" class="form-control" placeholder="이메일아이디" />
                    <span class="input-group-text">@</span>
                    <select class="form-select" v-model="RegisterUser.emailDomain" aria-label="이메일 도메인 선택">
                      <option selected>선택</option>
                      <option value="ssafy.com">싸피</option>
                      <option value="google.com">구글</option>
                      <option value="naver.com">네이버</option>
                      <option value="kakao.com">카카오</option>
                    </select>
                  </div>
                </div>
                <div class="d-grid gap-2">
                  <button type="button" class="btn btn-dark" @click="register">회원가입</button>
                  <button type="button" class="btn btn-secondary">초기화</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</template>
