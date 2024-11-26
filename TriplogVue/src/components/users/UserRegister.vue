<script setup>
import { ref, watch } from "vue"
import { storeToRefs } from "pinia"
import { useRouter } from "vue-router"
import { useMemberStore } from "@/stores/member"

const router = useRouter()

const memberStore = useMemberStore()

const { isRegister, idCheckCnt, nicknameCheckCnt } = storeToRefs(memberStore)
const { userRegist, checkId, checkNickname } = memberStore

const RegisterUser = ref({
  userId: "",
  userPwd: "",
  userName: "",
  pwdCheck: "",
  nickname: "",
  emailId: "",
  emailDomain: "",
  userImage:"/default.png"
})

const ckId = ref("");
const ckNickname = ref("");

// watch로 userId 감시
watch(
  () => RegisterUser.value.userId,
  async (newVal, oldVal) => {
    if(newVal.length == 0){
      ckId.value = ""
      return;
    }
    else if(newVal.length < 6 || newVal.length > 12){
      ckId.value = "ID는 6글자 이상 12글자 이하로 설정해주세요"
    }
    else{
      await checkId(newVal);
      if(idCheckCnt.value == 1){
        ckId.value = "중복된 ID가 존재합니다."
      }
      else{
        ckId.value = "사용가능한 ID 입니다."
      }
    }
  }
);

// watch로 nickname 감시
watch(
  () => RegisterUser.value.nickname,
  async (newVal, oldVal) => {
    if(newVal.length == 0){
      ckNickname.value = ""
    }
    else if(newVal.length < 2 || newVal.length > 16){
      ckNickname.value = "닉네임은 2글자 이상 16글자 이하로 설정해주세요"
    }
    else{
      await checkNickname(newVal);
      if(nicknameCheckCnt.value == 1){
        ckNickname.value = "중복된 닉네임이 존재합니다."
      }
      else{
        ckNickname.value = "사용가능한 닉네임 입니다."
      }
    }
  }
);

const register = async () => {
  if (RegisterUser.value.userPwd !== RegisterUser.value.pwdCheck) {
    alert("비밀번호가 불일치 합니다.")
  }
  else {
    await userRegist(RegisterUser.value)
    if (isRegister.value) {
      alert("회원가입이 완료되었습니다.")
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
              <h2 class="text-center mb-4">Registar</h2>
              <form>
                <div class="mb-3">
                  <label for="userName" class="form-label">Name : </label>
                  <input type="text" v-model="RegisterUser.userName" class="form-control" placeholder="Name..." />
                </div>
                <div class="mb-3">
                  <label for="nickname" class="form-label">Nickname : {{ ckNickname }}</label>
                  <input type="text" v-model="RegisterUser.nickname" class="form-control" placeholder="Nickname..." />
                </div>
                <div class="mb-3">
                  <label for="userId" class="form-label">ID : {{ ckId }}</label>
                  <input type="text" v-model="RegisterUser.userId" class="form-control" placeholder="ID..." />
                </div>
                <div class="mb-3">
                  <label for="userPwd" class="form-label">Password : </label>
                  <input type="password" v-model="RegisterUser.userPwd" class="form-control" placeholder="Password..." />
                </div>
                <div class="mb-3">
                  <label for="pwdcheck" class="form-label">confirm Password : </label>
                  <input type="password" v-model="RegisterUser.pwdCheck" class="form-control" id="pwdcheck"
                    placeholder="confirm Password..." />
                </div>
                <div class="mb-3">
                  <label for="emailId" class="form-label">Email : </label>
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
