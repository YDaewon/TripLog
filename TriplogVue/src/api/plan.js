import { localAxios } from "@/util/http-commons";

const local = localAxios();

function getPlans(param, success, fail) {
  local.defaults.headers["accessToken"] = sessionStorage.getItem("accessToken");
  const query = new URLSearchParams({param}).toString();
  local.get(`/plan?${query}`).then(success).catch(fail);
}

function getPlan(planNo, success, fail) {
  local.defaults.headers["accessToken"] = sessionStorage.getItem("accessToken");
  local.get(`/plan/${planNo}`).then(success).catch(fail);
}

function createPlan(plan, success, fail) {
  local
    .post(`/plan`, JSON.stringify(plan), {
      headers: { "Content-Type": "application/json" },
    })
    .then(success)
    .catch(fail);
}

function updatePlan(plan, success, fail) {
  local
    .put(`/plan`, JSON.stringify(plan), {
      headers: { "Content-Type": "application/json" },
    })
    .then(success)
    .catch(fail);
}

function deletePlan(planNo, success, fail) {
  local.delete(`/plan/${planNo}`).then(success).catch(fail);
}

export { getPlans, getPlan, createPlan, updatePlan, deletePlan };
