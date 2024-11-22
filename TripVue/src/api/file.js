import { localAxios } from "@/util/http-commons";

const local = localAxios();
local.defaults.headers.post["Content-Type"] = "multipart/form-data";

async function fileupload(userId, param, success, fail) {
  await local.post(`/file/${userId}`, param).then(success).catch(fail);
}

async function filedownload(param, success, fail) {
  await local.get(`/file`, param).then(success).catch(fail);
}

export { fileupload, filedownload };
