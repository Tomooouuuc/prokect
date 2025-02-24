"use client";
import { userLogin } from "@/api/userapi";
import { setLoginUser } from "@/stores/loginUser";
import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { LoginForm, ProForm, ProFormText } from "@ant-design/pro-components";
import { message } from "antd";
import Image from "next/image";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useDispatch } from "react-redux";
import "./index.css";

const UserLoginPage: React.FC = () => {
  const [form] = ProForm.useForm();
  const dispatch = useDispatch();
  const router = useRouter();
  const doSubmit = async (values: API.UserLogin) => {
    try {
      const res = await userLogin(values);
      if (res.data) {
        message.success("登录成功");
        dispatch(setLoginUser(res.data));
        router.replace("/");
      }
    } catch (e: any) {
      message.error("登录失败，" + e.message);
    }
  };

  return (
    <div id="userLoginPage">
      <LoginForm
        form={form}
        logo={
          <Image
            src={"/assets/logo.jpg"}
            alt="picares"
            width={44}
            height={44}
          />
        }
        title="picares"
        subTitle="ares云图库"
        onFinish={doSubmit}
      >
        <ProFormText
          name="userAccount"
          fieldProps={{
            size: "large",
            prefix: <UserOutlined className={"prefixIcon"} />,
          }}
          placeholder={"请输入账号"}
          rules={[
            {
              required: true,
              message: "请输入账号!",
            },
            {
              min: 4,
              message: "账号过短",
            },
            {
              max: 32,
              message: "账号过长",
            },
            {
              pattern: /^[a-zA-Z0-9]+$/,
              message: "包含非法字符",
            },
          ]}
        />
        <ProFormText.Password
          name="userPassword"
          fieldProps={{
            size: "large",
            prefix: <LockOutlined className={"prefixIcon"} />,
          }}
          placeholder={"请输入密码"}
          rules={[
            {
              required: true,
              message: "请输入密码！",
            },
          ]}
        />

        <div
          style={{
            marginBlockEnd: 24,
            textAlign: "right",
          }}
        >
          <Link href={"/user/register"} prefetch={false}>
            注册账号
          </Link>
        </div>
      </LoginForm>
    </div>
  );
};

export default UserLoginPage;
