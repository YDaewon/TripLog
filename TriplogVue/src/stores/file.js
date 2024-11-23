import { ref } from "vue"
import { defineStore } from "pinia"
import { httpStatusCode } from "@/util/http-status"

import { fileupload, filedownload } from "@/api/file"
export const useFileStore = defineStore("fileStore", () => {

  const isUp = ref(false)
  const isUpError = ref(false)
  const isDown = ref(false)
  const isDownError = ref(false)
  const imgurl = ref(null)

  const Upload = async (userId, file) => {
    const formData = new FormData();
    formData.append("userImage", file);  // 파일을 FormData에 추가
    await fileupload(
      userId,
      formData,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          console.log("파일 업로드 완료: " + response.data)
          isUp.value = true
          isUpError.value = false
          imgurl.value = response.data
        }
      },
      (error) => {
        console.log("파일 업로드 실패")
        isUp.value = false
        isUpError.value = true
        console.error(error)
      }
    )
  }

  const Download = async (filename) => {
    await filedownload(
      filename,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          console.log("파일 다운로드 완료")
          isDown.value = true
          isDownError.value = false
        }
      },
      (error) => {
        console.log("파일 다운로드 실패")
        isDown.value = false
        isDownError.value = true
        console.error(error)
      }
    )
  }
  return {
    isUp,
    isUpError,
    isDown,
    isDownError,
    imgurl,
    Upload,
    Download,
  }
}, { persist: { storage: localStorage } })
