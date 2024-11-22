<script setup>
import { ref } from "vue"
import { storeToRefs } from "pinia"
import { useRouter } from "vue-router"
import { useMemberStore } from "@/stores/member"

const router = useRouter()

const memberStore = useMemberStore()

const { isRegister, isRegisterError } = storeToRefs(memberStore)
const { userRegist } = memberStore

const RegisterUser = ref({
  userId: "dog",
  userPwd: "dog",
  userName:"dog",
  pwdCheck: "dog",
  nickname: "dog",
  emailId: "dog",
  emailDomain:"dog"
})

const onFileChange = (event) => {
  RegisterUser.value.userImage = event.target.files[0]; // 선택된 파일을 userImage에 저장
  //console.log(RegisterUser.value.userImage)
};

const register = async () => {
  if(RegisterUser.value.userPwd !== RegisterUser.value.pwdCheck){
    alert("비밀번호가 불일치 합니다.")
  }
  else{
    await userRegist(RegisterUser.value)
    if (isRegister.value) {
      router.replace("/")
    }
  }
}
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">회원가입</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <form>
          <div class="mb-3">
            <label for="userName" class="form-label">이름 : </label>
            <input type="text"  v-model="RegisterUser.userName" class="form-control" placeholder="이름..." />
          </div>
          <div class="mb-3">
            <label for="nickname" class="form-label">닉네임 : </label>
            <input type="text"  v-model="RegisterUser.nickname" class="form-control" placeholder="닉네임..." />
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
            <input type="text" v-model="RegisterUser.pwdCheck" class="form-control" id="pwdcheck" placeholder="비밀번호확인..." />
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
          <div class="col-auto text-center">
            <button type="button" class="btn btn-outline-primary mb-3" @click="register">회원가입</button>
            <button type="button" class="btn btn-outline-success ms-1 mb-3">초기화</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
