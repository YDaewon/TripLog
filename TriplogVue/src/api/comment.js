import { localAxios } from "@/util/http-commons";

const local = localAxios();

local.defaults.headers["accessToken"] = sessionStorage.getItem("accessToken");
function getComments(articleno, success, fail) {
  local.get(`/comment/${articleno}`).then(success).catch(fail);
}

function createComment(comment, success, fail) {
  local.defaults.headers.post["Content-Type"] = "application/json";
  console.log(comment)
  local.post(`/comment`, JSON.stringify(comment)).then(success).catch(fail);
}

function modifyComment(data, success, fail) {
  console.log(data)
  local.put(`/comment`, null, {
    params: data, // 쿼리 파라미터 전달
  }).then(success).catch(fail);
}

function deleteComment(commentno, success, fail) {
  local.put(`/comment/delete/${commentno}`).then(success).catch(fail);
}

export {
    getComments,
    createComment,
    modifyComment,
    deleteComment
};
