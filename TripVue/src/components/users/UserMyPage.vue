<script setup>
import { ref, onMounted } from "vue"
import { storeToRefs } from "pinia"
import { useRouter } from "vue-router"
import { useMemberStore } from "@/stores/member"
import { useMenuStore } from "@/stores/menu"

const router = useRouter()
const { changeMenuState } = useMenuStore()
const memberStore = useMemberStore()

const { userInfo, isModify, isDelete } = storeToRefs(memberStore)
const { userUpdate, userDelete, userLogout, getUserInfo } = memberStore;
const userData = ref({
      userName: userInfo.value.userName,
      nickname: userInfo.value.nickname,
      userId: userInfo.value.userId,
      userPwd: userInfo.value.userPwd,
      emailId: userInfo.value.emailId,
      emailDomain: userInfo.value.emailDomain,
    });
const pwdCheck = ref(userInfo.value.userPwd);

const update = async () => {
  //console.log(pwdCheck.value + ", " + userData.value.userPwd)
  if(pwdCheck.value !== userData.value.userPwd){
    alert("비밀번호가 불일치 합니다.")
  }
  else{
    await userUpdate(userData.value)
    if(isModify.value) {
      getUserInfo()
      router.replace("/")
    }
  }
}

const remove = async () => {
  if(confirm("리얼 트루로다가 삭제?")){
    await userDelete();
    if(isDelete.value) {
      userLogout()
      changeMenuState()
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
          My Profile
        </h2>
      </div>
      <div class="col-lg-10">
        <div class="card mt-3 m-auto" style="max-width: 700px">
          <div class="row g-0">
            <div class="col-md-4">
              <img
                src="https://source.unsplash.com/random/250x250/?food"
                class="img-fluid rounded-start"
                alt="..."
              />
            </div>
            <div class="col-md-8">
              <div class="card-body text-start">
                <form>
                  <div class="mb-3">
                    <label for="userName" class="form-label">이름 : </label>
                    <input type="text"  v-model="userData.userName" class="form-control" disabled/> 
                  </div>
                  <div class="mb-3">
                    <label for="userId" class="form-label">아이디 : </label>
                    <input type="text" v-model="userData.userId" class="form-control" disabled/>
                  </div>
                  <div class="mb-3">
                    <label for="nickname" class="form-label">닉네임 : </label>
                    <input type="text"  v-model="userData.nickname" class="form-control" placeholder="닉네임..." />
                  </div>
                  <div class="mb-3">
                    <label for="userPwd" class="form-label">비밀번호 : </label>
                    <input type="text" v-model="userData.userPwd" class="form-control" placeholder="비밀번호..." />
                  </div>
                  <div class="mb-3">
                    <label for="pwdcheck" class="form-label">비밀번호확인 : </label>
                    <input type="text" v-model="pwdCheck" class="form-control" id="pwdcheck" placeholder="비밀번호확인..." />
                  </div>
                  <div class="mb-3">
                    <label for="emailId" class="form-label">이메일 : </label>
                    <div class="input-group">
                      <input type="text" v-model="userData.emailId" class="form-control" placeholder="이메일아이디" />
                      <span class="input-group-text">@</span>
                      <select class="form-select" v-model="userData.emailDomain" aria-label="이메일 도메인 선택">
                        <option selected>선택</option>
                        <option value="ssafy.com">싸피</option>
                        <option value="google.com">구글</option>
                        <option value="naver.com">네이버</option>
                        <option value="kakao.com">카카오</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-auto text-center">
                    <button type="button" class="btn btn-outline-primary mb-3" @click="update">수정</button>
                    <button type="button" class="btn btn-outline-success ms-1 mb-3" @click="remove">삭제</button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
