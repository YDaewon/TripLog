import { localAxios } from "@/util/http-commons";

const local = localAxios();

function getDestinations(planNo, success, fail) {
  local.defaults.headers["accessToken"] = sessionStorage.getItem("accessToken");
  local.get(`/plan/dest/${planNo}`).then(success).catch(fail);
}
function createDestination(destination, success, fail) {
  local
    .post(`/plan/dest`, JSON.stringify(destination))
    .then(success)
    .catch(fail);
}
function deleteDestination(destNo, success, fail) {
  local.delete(`/plan/dest/${destNo}`).then(success).catch(fail);
}
function updateDestination(plan, success, fail) {
  local.put(`/plan/dest`, JSON.stringify(plan)).then(success).catch(fail);
}
export {
  getDestinations,
  createDestination,
  deleteDestination,
  updateDestination,
};
