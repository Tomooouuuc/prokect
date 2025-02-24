"use client";
import { userLogout } from "@/api/userapi";
import { DEFAULT_USER } from "@/constant/user";
import { RootState } from "@/stores";
import { setLoginUser } from "@/stores/loginUser";
import {
  GithubFilled,
  InfoCircleFilled,
  LogoutOutlined,
  QuestionCircleFilled,
} from "@ant-design/icons";
import { ProLayout } from "@ant-design/pro-components";
import { Dropdown, message } from "antd";
import Image from "next/image";
import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { getAccessMenu, menus } from "../../../config/menus";
import GlobalFooter from "./components/GlobalFooter";
import "./index.css";

interface Props {
  children: React.ReactNode;
}

export default function BasicLayout({ children }: Props) {
  const loginUser = useSelector((state: RootState) => state.loginUser);
  const pathname = usePathname();
  const dispatch = useDispatch();
  const router = useRouter();

  const doLogout = async () => {
    try {
      await userLogout();
      message.success("已退出登录");
      dispatch(setLoginUser(DEFAULT_USER));
      router.push("/user/login");
    } catch (e: any) {
      message.error("操作失败，" + e.message);
    }
  };

  return (
    <div
      id="basicLayout"
      style={{
        height: "100vh",
        overflow: "auto",
      }}
    >
      <ProLayout
        layout="top"
        title="picares"
        breadcrumbRender={() => {
          return [];
        }}
        logo={
          <Image
            src={"/assets/logo.jpg"}
            alt="picares"
            width={32}
            height={32}
          />
        }
        location={{
          pathname,
        }}
        avatarProps={{
          src: loginUser.userAvatar || DEFAULT_USER.userAvatar,
          size: "small",
          title: loginUser.userName || DEFAULT_USER.userName,
          render: (props, dom) => {
            return (
              <Dropdown
                menu={{
                  items: [
                    {
                      key: "logout",
                      icon: <LogoutOutlined />,
                      label: "退出登录",
                    },
                  ],
                  onClick: async (event: { key: React.Key }) => {
                    const { key } = event;
                    if (key === "logout") {
                      doLogout();
                    }
                  },
                }}
              >
                {dom}
              </Dropdown>
            );
          },
        }}
        actionsRender={(props) => {
          if (props.isMobile) return [];
          return [
            <InfoCircleFilled key="InfoCircleFilled" />,
            <QuestionCircleFilled key="QuestionCircleFilled" />,
            <GithubFilled key="GithubFilled" />,
          ];
        }}
        headerTitleRender={(logo, title, _) => {
          const defaultDom = (
            <a href="/" target="_blank">
              {logo}
              {title}
            </a>
          );

          return <>{defaultDom}</>;
        }}
        footerRender={() => {
          return <GlobalFooter />;
        }}
        onMenuHeaderClick={(e) => console.log(e)}
        menuDataRender={() => {
          return getAccessMenu(loginUser, menus);
        }}
        menuItemRender={(item, dom) => (
          <Link href={item.path || "/"} target={item.target}>
            {dom}
          </Link>
        )}
      >
        {children}
      </ProLayout>
    </div>
  );
}
