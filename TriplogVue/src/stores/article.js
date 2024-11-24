import { ref } from "vue"
import { defineStore } from "pinia"
import { detailArticle } from "@/api/board"
import { httpStatusCode } from "@/util/http-status"

export const useArticleStore = defineStore("articleStore", () => {
  const articleInfo = ref(null)

  const getArticle = async (articleNo) => {
    await detailArticle(
      articleNo,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          articleInfo.value = response.data
          //console.log(response.data)
        } else {
          console.log("게시글 정보 없음!!!!")
        }
      },
      async (error) => {
        console.log(error)
      }
    )
  }

  return {
    articleInfo,
    getArticle,
  }
},
  { persist: { storage: localStorage } })
