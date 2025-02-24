"use client";
import { deleteUser, selectUser } from "@/api/userapi";
import { USER_ACCESS } from "@/constant/user";
import type { ActionType, ProColumns } from "@ant-design/pro-components";
import { PageContainer, ProTable } from "@ant-design/pro-components";
import { Button, message, Tag } from "antd";
import { useRef, useState } from "react";
import UpdateModal from "./components/UpdateModal";

export default () => {
  const actionRef = useRef<ActionType>(null);
  const [currentRow, setCurrentRow] = useState<API.SelectUser>();
  const [updateVisible, setUpdateVisible] = useState<boolean>(false);

  const handleDelete = async (row: API.SelectUserVO) => {
    const hide = message.loading("正在删除");
    try {
      const res = await deleteUser({
        id: row.id,
      });
      console.log("删除结果：", res);
      hide();
      message.success("删除成功");
      actionRef.current?.reload();
    } catch (e: any) {
      hide();
      message.error("删除失败");
    }
  };
  const columns: ProColumns<API.SelectUser>[] = [
    {
      title: "id",
      dataIndex: "id",
      valueType: "text",
      hideInSearch: true,
      hideInForm: true,
      hideInTable: true,
    },
    {
      title: "账号",
      dataIndex: "userAccount",
      valueType: "text",
      formItemProps: {
        rules: [
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
        ],
      },
    },
    {
      title: "用户名称",
      dataIndex: "userName",
      valueType: "text",
      formItemProps: {
        rules: [
          {
            max: 32,
            message: "用户名称过长",
          },
        ],
      },
    },
    {
      title: "用户头像",
      dataIndex: "userAvatar",
      valueType: "image",
      hideInSearch: true,
    },
    {
      title: "用户简介",
      dataIndex: "userProfile",
      valueType: "text",
      width: 320,
      formItemProps: {
        rules: [
          {
            max: 128,
            message: "用户简介过长",
          },
        ],
      },
    },
    {
      title: "用户权限",
      dataIndex: "userRole",
      valueEnum: {
        user: {
          text: "用户",
        },
        admin: {
          text: "管理员",
        },
      },
      render: (_, record) => {
        if (record.userRole === USER_ACCESS.USER) {
          return (
            <Tag bordered={false} color="success">
              用户
            </Tag>
          );
        }
        return (
          <Tag bordered={false} color="gold">
            管理员
          </Tag>
        );
      },
    },
    {
      title: "创建时间",
      sorter: true,
      dataIndex: "createTime",
      valueType: "dateTime",
      hideInSearch: true,
      hideInForm: true,
    },
    {
      title: "操作",
      valueType: "option",
      key: "option",
      render: (text, record, _, action) => [
        <Button
          color="cyan"
          variant="filled"
          size="small"
          onClick={() => {
            setUpdateVisible(true);
            setCurrentRow(record);
          }}
        >
          编辑
        </Button>,
        <Button
          color="danger"
          variant="filled"
          size="small"
          onClick={() => handleDelete(record)}
        >
          删除
        </Button>,
      ],
    },
  ];
  return (
    <PageContainer>
      <ProTable<API.SelectUser>
        columns={columns}
        actionRef={actionRef}
        request={async (params, sort, filter) => {
          const sortField = Object.keys(sort)?.[0];
          const sortOrder = sort?.[sortField];
          const { code, data } = await selectUser({
            ...params,
            sortField,
            sortOrder,
            ...filter,
          } as API.SelectUser);
          return {
            success: code === 0,
            data: data?.records || [],
            total: data?.total || 0,
          };
        }}
        rowKey="key"
        options={{
          setting: false,
          density: false,
        }}
        search={{
          span: 6,
          defaultCollapsed: false,
          labelWidth: "auto",
        }}
        pagination={{
          pageSize: 10,
        }}
      />
      <UpdateModal
        oldData={currentRow}
        visible={updateVisible}
        columns={columns}
        onSubmit={() => {
          setUpdateVisible(false);
          setCurrentRow(undefined);
          actionRef.current?.reload();
        }}
        onCancle={() => {
          setUpdateVisible(false);
        }}
      />
    </PageContainer>
  );
};
