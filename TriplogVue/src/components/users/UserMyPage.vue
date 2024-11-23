<script setup>
import { ref} from "vue"
import { storeToRefs } from "pinia"
import { useRouter } from "vue-router"
import { useMemberStore } from "@/stores/member"
import { useFileStore } from "@/stores/file"
import { useMenuStore } from "@/stores/menu"

const router = useRouter()
const { changeMenuState } = useMenuStore()
const memberStore = useMemberStore()

const fileStore = useFileStore()
const { isUp, imgurl } = storeToRefs(fileStore)
const { Upload } = fileStore;

const { userInfo, isModify, isDelete } = storeToRefs(memberStore)
const { userUpdate, userDelete, userLogout, getUserInfo } = memberStore;
const userData = ref({
      userName: userInfo.value.userName,
      nickname: userInfo.value.nickname,
      userId: userInfo.value.userId,
      userPwd: userInfo.value.userPwd,
      emailId: userInfo.value.emailId,
      emailDomain: userInfo.value.emailDomain,
      userImage: userInfo.value.userImage,
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

const onFileChange = async (event) => {
    if(event.target.files[0] != null) {
      await Upload(userInfo.value.userId, event.target.files[0])
      if(isUp.value){
        userData.value.userImage = imgurl.value
        console.log("현재 url: " + userData.value.userImage)
      }
    }
}

const fileInput = ref(null);

const triggerFileInput = () => {
  if (fileInput.value) {
    fileInput.value.click();
  } else {
    console.error("파일 입력 요소를 찾을 수 없습니다.");
  }
};
</script>


<style>
.card {
    max-width: 800px;
    width: 100%;
    margin: auto;
}
</style>

<template>
<div class="container">
        <div class="card shadow">
            <div class="card-body p-4">
                <h1 class="card-title text-center mb-4">My Profile</h1>
                <div class="row">
                    <div class="col-md-4 text-center mb-4">
                      <div
                        class="bg-light rounded-circle mx-auto mb-3 d-flex align-items-center justify-content-center overflow-hidden"
                        style="width: 200px; height: 200px; position: relative;"
                      >
                        <img
                          v-if="userData.userImage != null"
                          :src="userData.userImage"
                          :key="userData.userImage"
                          class="img-fluid"
                          alt="..."
                          style="
                            width: 100%;
                            height: 100%;
                            object-fit: cover;
                            object-position: center;
                            border-radius: 50%;
                          "
                        />
                      </div>
                        <button class="btn btn-outline-secondary" @click="triggerFileInput">프로필 변경</button>
                        <input type="file" @change="onFileChange" class="form-control" id="profileImage" ref="fileInput" style="display: none;"/>
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
                        </form>
                        <div class="text-end">
                          <button type="submit" class="btn btn-primary me-2" @click="update">수정</button>
                          <button type="button" class="btn btn-danger" @click="remove">삭제</button>
                        </div>
                      </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

