import { USER_ACCESS } from "@/constant/user";

const checkAccess = (
  loginUser: API.LoginUserVO,
  needAccess = USER_ACCESS.USER
) => {
  const userAccess = loginUser.userRole ?? USER_ACCESS.GUEST;
  if (needAccess === USER_ACCESS.GUEST) {
    if (userAccess === USER_ACCESS.GUEST) {
      return true;
    }
  }
  if (needAccess === USER_ACCESS.USER) {
    if (userAccess === USER_ACCESS.GUEST) {
      return false;
    }
  }
  if (needAccess === USER_ACCESS.ADMIN) {
    if (userAccess !== USER_ACCESS.ADMIN) {
      return false;
    }
  }
  return true;
};

export default checkAccess;
