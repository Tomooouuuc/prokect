import { updateUser } from "@/api/userapi";
import { ProColumns, ProTable } from "@ant-design/pro-components";
import { message, Modal } from "antd";
import React from "react";

interface Props {
  oldData?: API.SelectUserVO;
  visible: boolean;
  columns: ProColumns<API.SelectUser>[];
  onSubmit: () => void;
  onCancle: () => void;
}

const UpdateModal: React.FC<Props> = (props) => {
  const { oldData, visible, columns, onSubmit, onCancle } = props;
  return (
    <Modal
      destroyOnClose
      title="更新用户"
      open={visible}
      onCancel={onCancle}
      footer={null}
    >
      <ProTable
        type="form"
        columns={columns}
        form={{
          initialValues: oldData,
        }}
        onSubmit={async (values: API.UpdateUser) => {
          console.log("更新的值：", values);
          console.log("id:", oldData?.id);
          try {
            await updateUser({
              ...values,
              id: oldData?.id,
            });
            message.success("更新成功");
            onSubmit();
          } catch (e: any) {
            message.error("更新失败，" + e.message);
          }
        }}
      />
    </Modal>
  );
};

export default UpdateModal;
