import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function userConfirm(param, success, fail) {
  await local.post(`/user/login`, param).then(success).catch(fail);
}

async function userRegister(param, success, fail) {
  await local.post(`/user/join`, param).then(success).catch(fail);
}

async function userModify(userId, param, success, fail) {
  await local.put(`/user/info/${userId}`, param).then(success).catch(fail);
}

async function userRemove(userId, success, fail) {
  await local.delete(`/user/info/${userId}`).then(success).catch(fail);
}

async function findById(success, fail) {
  local.defaults.headers["accessToken"] = sessionStorage.getItem("accessToken");
  await local.get(`/user/info`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(success, fail) {
  await local.get(`/user/logout`).then(success).catch(fail);
}

export { userConfirm, userRegister, userModify, userRemove, findById, tokenRegeneration, logout };
