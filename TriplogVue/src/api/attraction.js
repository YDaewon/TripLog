import { attractionAxios,localAxios } from "@/util/http-commons";

const local = localAxios();
const attraction = attractionAxios();

function listAttractions(param, success, fail) {
  attraction.get(`/attraction`, { params: param }).then(success).catch(fail);
}
function getAttractions(planNo, success, fail) {
  attraction.get(`/attraction/${planNo}`).then(success).catch(fail);
}

function getSidosCount(success, fail) {
  local.get(`/attraction/sido-counts`).then(success).catch(fail);
}


export { listAttractions, getAttractions, getSidosCount };
