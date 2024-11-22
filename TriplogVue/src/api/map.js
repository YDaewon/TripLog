import { localAxios } from "@/util/http-commons";

const local = localAxios();

// function listSido(success, fail) {
//   local.get(`/attraction/sido`).then(success).catch(fail);
// }
const token =
  "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJpYXQiOjE3MzIxOTUzMDUsImV4cCI6MTczMjIzMTMwNSwidXNlcklkIjoic3NhZnkiLCJ1c2VyTm8iOjEsInJvbGUiOjB9.z4387Ia6oBxWLp3mGkZkwjRKQ2OC1ct5P0Wp_6jdofU";

function listSido(success, fail) {
  local
    .get(`http://localhost/attraction/sido`, {
      headers: {
        accessToken: `Bearer ${token}`,
      },
    })
    .then(success)
    .catch(fail);
}

function listGugun(param, success, fail) {
  local
    .get(`http://localhost/attraction/gugun`, {
      params: param,
      headers: {
        accessToken: `Bearer ${token}`,
      },
    })
    .then(success)
    .catch(fail);
}

function listContentType(success, fail) {
  local
    .get(`http://localhost/attraction/content`, {
      headers: {
        accessToken: `Bearer ${token}`,
      },
    })
    .then(success)
    .catch(fail);
}

export { listSido, listGugun, listContentType };
