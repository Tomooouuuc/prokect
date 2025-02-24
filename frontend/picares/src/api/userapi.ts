import request from "@/lib/request";

export function userLogin(body: API.UserLogin) {
  return request("/user/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
  });
}

export function userRegister(body: API.UserRegister) {
  return request("/user/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
  });
}

export function getLoginUser() {
  return request("/user/get/login", {
    method: "GET",
  });
}

export function userLogout() {
  return request("/user/logout", {
    method: "POST",
  });
}

export function updateUser(body: API.UpdateUser) {
  return request("/user/update", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
  });
}

export function deleteUser(body: API.DeleteUser) {
  console.log("body:", body);
  return request("/user/delete", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
  });
}

export function selectUser(
  body: API.SelectUser
): Promise<API.BaseResponse<API.SelectUserPageVO>> {
  return request("/user/get", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
  });
}
