export const USER_ACCESS = {
  GUEST: "guest",
  ADMIN: "admin",
  USER: "user",
};

export const DEFAULT_USER: API.LoginUserVO = {
  userName: "未登录",
  userAvatar: "/assets/notLoginUser.png",
  userProfile: "暂无简介",
  userRole: USER_ACCESS.GUEST,
};
