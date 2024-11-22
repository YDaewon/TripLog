import { localAxios } from "@/util/http-commons";

const local = localAxios();

function getPlans(param, success, fail) {
  local.get(`/plan`, { params: param }).then(success).catch(fail);
}

function getPlan(planNo, success, fail) {
  local.get(`/plan/${planNo}`).then(success).catch(fail);
}

function createPlan(plan, success, fail) {
  local.post(`/plan`, JSON.stringify(plan)).then(success).catch(fail);
}

function updatePlan(plan, success, fail) {
  local.put(`/plan`, JSON.stringify(plan)).then(success).catch(fail);
}

function deletePlan(planNo, success, fail) {
  local.delete(`/plan/${articleno}`).then(success).catch(fail);
}

export {
  listArticle,
  detailArticle,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
};
