import checkAccess from "@/access/checkAccess";
import { USER_ACCESS } from "@/constant/user";
import { MenuDataItem } from "@ant-design/pro-components";

export const menus = [
  {
    path: "/",
    name: "主页",
  },
  {
    path: "/test",
    name: "图片",
    access: USER_ACCESS.GUEST,
  },
  {
    path: "/user/login",
    hideInMenu: true,
    access: USER_ACCESS.GUEST,
  },
  {
    path: "/user/register",
    hideInMenu: true,
    access: USER_ACCESS.GUEST,
  },
  {
    path: "/admin",
    name: "管理",
    access: USER_ACCESS.ADMIN,
    children: [
      {
        path: "/admin/user",
        name: "用户管理",
        access: USER_ACCESS.ADMIN,
      },
    ],
  },
] as MenuDataItem[];

export const findMenuByPath = (path: string): MenuDataItem | null => {
  return findMenuItemByPath(menus, path);
};

const findMenuItemByPath = (
  menus: MenuDataItem[],
  path: string
): MenuDataItem | null => {
  for (const menu of menus) {
    if (menu.path === path) {
      return menu;
    }
    if (menu.children) {
      const matchMenu = findMenuItemByPath(menu.children, path);
      if (matchMenu) {
        return matchMenu;
      }
    }
  }
  return null;
};

export const getAccessMenu = (loginUser: API.LoginUserVO, menu = menus) => {
  return menu.filter((item) => {
    if (!checkAccess(loginUser, item.access)) {
      return false;
    }
    if (item.children) {
      getAccessMenu(loginUser, item.children);
    }
    return true;
  });
};
