import { localAxios } from "@/util/http-commons";

const local = localAxios();

local.defaults.headers["accessToken"] = sessionStorage.getItem("accessToken");
function listArticle(param, success, fail) {
  local.get(`/article`, { params: param }).then(success).catch(fail);
}

function listMyArticle(param, success, fail) {
  local.get(`/article/my`, { params: param }).then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
  local.get(`/article/${articleno}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  local.defaults.headers.post["Content-Type"] = "application/json";
  local.post(`/article`, JSON.stringify(article)).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  local.defaults.headers.put["Content-Type"] = "application/json";
  local.put(`/article`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
  local.delete(`/article/${articleno}`).then(success).catch(fail);
}

function IsStar(articleno, success, fail) {
  local.get(`/article/star/${articleno}`).then(success).catch(fail);
}

function createStarArticle(articleno, success, fail) {
  local.post(`/article/star/${articleno}`).then(success).catch(fail);
}

function deleteStarArticle(articleno, success, fail) {
  local.delete(`/article/star/${articleno}`).then(success).catch(fail);
}

function linkPlan(param, success, fail) {
  local.get(`/article/link`, { params: param }).then(success).catch(fail);
}

export {
  listArticle,
  listMyArticle,
  detailArticle,
  registArticle,
  modifyArticle,
  deleteArticle,
  IsStar,
  createStarArticle,
  deleteStarArticle,
  linkPlan,
};
