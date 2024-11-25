import { localAxios } from "@/util/http-commons";

const local = localAxios();

local.defaults.headers["accessToken"] = sessionStorage.getItem("accessToken");
function getComments(articleno, success, fail) {
  local.get(`/comment/${articleno}`).then(success).catch(fail);
}

function createCommnet(comment, success, fail) {
  local.defaults.headers.post["Content-Type"] = "application/json";
  local.post(`/comment`, JSON.stringify(comment)).then(success).catch(fail);
}

function modifyCommnet(comment, success, fail) {
  local.defaults.headers.put["Content-Type"] = "application/json";
  local.put(`/comment`, JSON.stringify(comment)).then(success).catch(fail);
}

function deleteCommnet(commentno, success, fail) {
  local.delete(`/comment/${commentno}`).then(success).catch(fail);
}

export {
    getComments,
    createCommnet,
    modifyCommnet,
    deleteCommnet
};
