import Forbidden from "@/app/forbidden";
import { USER_ACCESS } from "@/constant/user";
import { RootState } from "@/stores";
import { usePathname } from "next/navigation";
import { useSelector } from "react-redux";
import { findMenuByPath } from "../../config/menus";
import checkAccess from "./checkAccess";

const AccessLayout: React.FC<
  Readonly<{
    children: React.ReactNode;
  }>
> = ({ children }) => {
  const pathname = usePathname();
  const loginUser = useSelector((state: RootState) => state.loginUser);
  const menu = findMenuByPath(pathname);
  const needAccess = menu?.access ?? USER_ACCESS.USER;
  const canAccess = checkAccess(loginUser, needAccess);
  if (!canAccess) {
    return <Forbidden />;
  }
  return <>{children}</>;
};

export default AccessLayout;
