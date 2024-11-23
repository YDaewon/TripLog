import { ref } from "vue"
import { useRouter } from "vue-router"
import { defineStore } from "pinia"


import { userRegister, userConfirm, userModify, userRemove, findById, tokenRegeneration, logout } from "@/api/user"
import { httpStatusCode } from "@/util/http-status"

export const useMemberStore = defineStore("memberStore", () => {
  const router = useRouter()

  const isLogin = ref(false)
  const isLoginError = ref(false)
  const isRegister = ref(false)
  const isRegisterError = ref(false)
  const isModify = ref(false)
  const isModifyError = ref(false)
  const isDelete = ref(false)
  const isDeleteError = ref(false)
  const userInfo = ref(null)
  const isValidToken = ref(false)


  const userRegist = async (RegisterUser) => {
    await userRegister(
      RegisterUser,
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          //console.log("회원가입 성공!!!!")
          isRegister.value = true
          isRegisterError.value = false
        }
      },
      (error) => {
        //console.log("회원가입 실패!!!!")
        isRegister.value = false
        isRegisterError.value = true
        console.error(error)
      }
    )
  }

  const userLogin = async (loginUser) => {
    await userConfirm(
      loginUser,
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          //console.log("로그인 성공!!!!")
          let { data } = response
          let accessToken = data["access-token"]
          let refreshToken = data["refresh-token"]
          isLogin.value = true
          isLoginError.value = false
          isValidToken.value = true
          sessionStorage.setItem("accessToken", accessToken)
          sessionStorage.setItem("refreshToken", refreshToken)
        }
      },
      (error) => {
        //console.log("로그인 실패!!!!")
        isLogin.value = false
        isLoginError.value = true
        isValidToken.value = false
        console.error(error)
      }
    )
  }

  const userUpdate = async (userData) => {
    await userModify(
      userInfo.value.userId,
      userData,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          //console.log("회원 정보 수정 성공!!!!")
          isModify.value = true
          isModifyError.value = false
        }
        else {
          isModify.value = false
          isModifyError.value = false
        }
      },
      (error) => {
        //console.log("회원 정보 수정 실패!!!!")
        console.error(error)
        isModify.value = false
        isModifyError.value = true
      }
    )
  }

  const userDelete = async () => {
    await userRemove(
      userInfo.value.userId,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          //console.log("회원 정보 삭제 성공!!!!")
          isDelete.value = true
          isDeleteError.value = false
          isLogin.value = false
          isValidToken.value = false
          sessionStorage.removeItem("accessToken")
          sessionStorage.removeItem("refreshToken")
        }
        else {
          isDelete.value = false
          isDeleteError.value = false
        }
      },
      (error) => {
        //console.log("회원 정보 삭제 실패!!!!")
        console.error(error)
        isDelete.value = false
        isDeleteError.value = true
      }
    )
  }

  const getUserInfo = async () => {
    await findById(
      (response) => {
        if (response.status === httpStatusCode.OK) {
          userInfo.value = response.data.userInfo
        } else {
          //console.log("유저 정보 없음!!!!")
        }
      },
      async (error) => {
        console.error(
          "g[토큰 만료되어 사용 불가능.] : ",
          error.response.status,
          error.response.statusText
        )
        isValidToken.value = false

        await tokenRegenerate()
      }
    )
  }

  const tokenRegenerate = async () => {
    await tokenRegeneration(
      JSON.stringify(userInfo.value),
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          let accessToken = response.data["access-token"]
          sessionStorage.setItem("accessToken", accessToken)
          isValidToken.value = true
        }
      },
      async (error) => {
        // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
        if (error.response.status === httpStatusCode.UNAUTHORIZED) {
          // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
          await logout(
            userInfo.value.userid,
            (response) => {
              if (response.status === httpStatusCode.OK) {
                console.log("리프레시 토큰 제거 성공")
              } else {
                console.log("리프레시 토큰 제거 실패")
              }
              alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.")
              isLogin.value = false
              userInfo.value = null
              isValidToken.value = false
              router.push({ name: "user-login" })
            },
            (error) => {
              console.error(error)
              isLogin.value = false
              userInfo.value = null
            }
          )
        }
      }
    )
  }

  const userLogout = async () => {
    await logout(
      (response) => {
        if (response.status === httpStatusCode.OK) {
          isLogin.value = false
          userInfo.value = null
          isValidToken.value = false
          //console.log("로그아웃 완료!!!")
          sessionStorage.removeItem("accessToken")
          sessionStorage.removeItem("refreshToken")
        } else {
          //console.error("유저 정보 없음!!!!")
        }
      },
      (error) => {
        console.log(error)
      }
    )
  }

  return {
    isLogin,
    isLoginError,
    isRegister,
    isRegisterError,
    isModify,
    isModifyError,
    isDelete,
    isDeleteError,
    userInfo,
    isValidToken,
    userRegist,
    userLogin,
    userUpdate,
    userDelete,
    getUserInfo,
    tokenRegenerate,
    userLogout,
  }
},
  { persist: { storage: localStorage } })
