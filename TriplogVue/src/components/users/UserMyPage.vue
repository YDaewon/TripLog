<script setup>
import { ref } from "vue"
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

const { userInfo, isModify, isDelete, isChange } = storeToRefs(memberStore)
const { userUpdate, userDelete, userLogout, getUserInfo, changePassword } = memberStore;
const userData = ref({
  userName: userInfo.value.userName,
  nickname: userInfo.value.nickname,
  userId: userInfo.value.userId,
  userPwd: userInfo.value.userPwd,
  emailId: userInfo.value.emailId,
  emailDomain: userInfo.value.emailDomain,
  userImage: userInfo.value.userImage,
});

const update = async () => {
  await userUpdate(userData.value)
  if (isModify.value) {
    getUserInfo()
    alert("회원 정보 수정 완료!")
    router.replace("/")
  }
}

const remove = async () => {
  if (confirm("리얼 트루로다가 삭제?")) {
    await userDelete();
    if (isDelete.value) {
      userLogout()
      changeMenuState()
      router.replace("/")
    }
  }
}

const onFileChange = async (event) => {
  if (event.target.files[0] != null) {
    await Upload(userInfo.value.userId, event.target.files[0])
    if (isUp.value) {
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

// 상태 관리
const activeModal = ref('');
const currentPassword = ref('');
const newPassword = ref('');
const confirmPassword = ref('');

// 모달 열기
function openModal(modalId) {
  activeModal.value = modalId;
}

// 모달 닫기
function closeModal() {
  activeModal.value = '';
  clearInputs();
}

// 다음 모달로 이동
function proceedToNewPassword() {
  if (!currentPassword.value) {
    alert('현재 비밀번호를 입력해주세요.');
    return;
  }
  if (currentPassword.value !== userData.value.userPwd) {
    alert('현재 비밀번호와 일치하지 않습니다.');
    return;
  }
  activeModal.value = 'newPasswordModal';
}

// 비밀번호 업데이트
async function updatePassword() {
  if (!newPassword.value || !confirmPassword.value) {
    alert('모든 필드를 입력해주세요.');
    return;
  }
  if (newPassword.value !== confirmPassword.value) {
    alert('새 비밀번호가 일치하지 않습니다.');
    return;
  }
  await changePassword(confirmPassword.value)
  if (isChange.value) {
    userData.value.userPwd = confirmPassword.value;
    alert('비밀번호가 성공적으로 변경되었습니다!');
    closeModal();
  }
}

// 입력값 초기화
function clearInputs() {
  currentPassword.value = '';
  newPassword.value = '';
  confirmPassword.value = '';
}


// 이미지 제거 함수
function clearImage() {
  userData.value.userImage = "/default.png";
}

</script>



<template>
  <div class="container">
    <!-- 비밀번호 변경 모달 -->
    <div :class="['modal', 'fade', { show: activeModal === 'currentPasswordModal' }]" tabindex="-1" aria-hidden="true"
      v-show="activeModal === 'currentPasswordModal'">
      <div class="modal-dialog modal-dialog-centered custom-modal">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">현재 비밀번호 입력</h5>
            <button type="button" class="btn-close" @click="closeModal"></button>
          </div>
          <div class="modal-body">
            <input type="password" class="form-control" placeholder="현재 비밀번호" v-model="currentPassword" />
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" @click="closeModal">취소</button>
            <button class="btn btn-primary" @click="proceedToNewPassword">
              다음
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 새 비밀번호 입력 모달 -->
    <div :class="['modal', 'fade', { show: activeModal === 'newPasswordModal' }]" tabindex="-1" aria-hidden="true"
      v-show="activeModal === 'newPasswordModal'">
      <div class="modal-dialog modal-dialog-centered custom-modal">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">새 비밀번호 입력</h5>
            <button type="button" class="btn-close" @click="closeModal"></button>
          </div>
          <div class="modal-body">
            <input type="password" class="form-control mb-2" placeholder="새 비밀번호" v-model="newPassword" />
            <input type="password" class="form-control" placeholder="새 비밀번호 확인" v-model="confirmPassword" />
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" @click="closeModal">취소</button>
            <button class="btn btn-primary" @click="updatePassword">
              업데이트
            </button>
          </div>
        </div>
      </div>
    </div>


    <!-- 내 프로필 -->
    <div class="card shadow">
      <div class="card-body p-4">
        <h1 class="card-title text-center mb-4">My Profile</h1>
        <div class="row">
          <div class="col-md-4 text-center mb-4 d-flex flex-column justify-content-around">
            <div>
              <div
                class="bg-light rounded-circle mx-auto mb-3 d-flex align-items-center justify-content-center overflow-hidden"
                style="width: 200px; height: 200px; position: relative;">
                <div class="image-container" :class="{ 'no-hover': userData.userImage === '/default.png' }" @click="clearImage">
                  <img v-if="userData.userImage != null" :src="userData.userImage" :key="userData.userImage"
                    class="img-fluid" alt="..." />
                  <div v-if="userData.userImage != null && userData.userImage !== '/default.png'" class="overlay">
                    <span class="remove-text">X</span>
                  </div>
                </div>
              </div>
              <button class="btn btn-outline-secondary" @click="triggerFileInput">프로필 변경</button>
              <input type="file" @change="onFileChange" class="form-control" id="profileImage" ref="fileInput"
                style="display: none;" />
            </div>
            <div class="d-flex flex-column gap-2 mt-4 button-container">
              <router-link :to="{ name: 'star-article-list' }" class="navbar-brand">
                <button class="btn btn-outline-warning w-100">즐겨찾기 한 게시글</button>
              </router-link>
              <router-link :to="{ name: 'my-article-list' }" class="navbar-brand">
                <button class="btn btn-outline-dark w-100">내 게시글</button>
              </router-link>
            </div>
          </div>

          <div class="col-md-8">
            <div class="card-body text-start">
              <form>
                <div class="mb-3">
                  <label for="userName" class="form-label">이름 : </label>
                  <input type="text" v-model="userData.userName" class="form-control" disabled />
                </div>
                <div class="mb-3">
                  <label for="userId" class="form-label">아이디 : </label>
                  <input type="text" v-model="userData.userId" class="form-control" disabled />
                </div>
                <div class="mb-3">
                  <label for="nickname" class="form-label">닉네임 : </label>
                  <input type="text" v-model="userData.nickname" class="form-control" placeholder="닉네임..." />
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
                <button type="submit" class="btn btn-outline-primary me-2"
                  @click="openModal('currentPasswordModal')">비밀번호
                  변경</button>
                <button type="submit" class="btn btn-outline-primary me-2" @click="update">수정</button>
                <button type="button" class="btn btn-outline-danger " @click="remove">삭제</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
.card {
  max-width: 800px;
  width: 100%;
  margin: auto;
}

.modal {
  display: none;
  /* 기본적으로 숨김 처리 */
}

.modal.show {
  display: block;
  /* 활성화된 모달만 보이게 */
  background: rgba(0, 0, 0, 0.5);
  /* 배경 흐리기 */
}

.modal-dialog.custom-modal {
  max-width: 500px;
  width: 500px;
  height: auto;
}

.modal-content {
  width: 500px;
  height: auto;
  display: flex;
  flex-direction: column;
}

.modal-body {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

/* 이미지 컨테이너 */
.image-container {
  position: relative;
  width: 100%; /* 부모 요소의 크기에 맞게 조정 */
  height: 100%;
  cursor: pointer;
}

/* 이미지 스타일 */
.image-container img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
  border-radius: 50%;
  transition: filter 0.3s ease; /* 흐려지는 효과 */
}

/* 마우스 올렸을 때 흐려지기 */
.image-container:hover img {
  filter: blur(2px) brightness(0.7);
}

/* 오버레이 */
.overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  opacity: 0;
  transition: opacity 0.3s ease;
}

/* 마우스 올렸을 때 오버레이 표시 */
.image-container:hover .overlay {
  opacity: 1;
}

/* 'X' 텍스트 스타일 */
.remove-text {
  color: white;
  font-size: 24px;
  font-weight: bold;
  text-shadow: 0 0 5px black;
  user-select: none; /* 텍스트 선택 방지 */
}

.image-container.no-hover img {
  pointer-events: none; /* 마우스 이벤트 차단 */
  filter: none; /* 흐림 효과 제거 */
}
</style>