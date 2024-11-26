import { attractionAxios } from "@/util/http-commons";

const attraction = attractionAxios();

function listAttractions(param, success, fail) {
  attraction.get(`/attraction`, { params: param }).then(success).catch(fail);
}
function getAttractions(planNo, success, fail) {
  attraction.get(`/attraction/${planNo}`).then(success).catch(fail);
}

export { listAttractions, getAttractions };
