import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function idCheck(userId, success, fail) {
  await local.get(`/user/checkid/${userId}`).then(success).catch(fail);
}

async function nicknameCheck(nickname, success, fail) {
  await local.get(`/user/checknickname/${nickname}`).then(success).catch(fail);
}

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

async function changePwd(userId, param, success, fail) {
  const query = new URLSearchParams({param}).toString();
  await local.put(`/user/info/pwd/${userId}?${query}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userId, success, fail) {
  await local.get(`/user/logout/${userId}`).then(success).catch(fail);
}

export { idCheck, nicknameCheck, userConfirm, userRegister, userModify, userRemove, findById, changePwd, tokenRegeneration, logout };

