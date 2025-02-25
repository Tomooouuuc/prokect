"use client";
import request from "@/lib/request";
import { LoadingOutlined, PlusOutlined } from "@ant-design/icons";
import type { GetProp, UploadProps } from "antd";
import { Button, Form, Input, Upload, message } from "antd";
import { Image } from "antd/lib";
import React, { useState } from "react";

type FileType = Parameters<GetProp<UploadProps, "beforeUpload">>[0];

const getBase64 = (img: FileType, callback: (url: string) => void) => {
  const reader = new FileReader();
  reader.addEventListener("load", () => callback(reader.result as string));
  reader.readAsDataURL(img);
};

const beforeUpload = (file: FileType) => {
  const isJpgOrPng = file.type === "image/jpeg" || file.type === "image/png";
  if (!isJpgOrPng) {
    message.error("You can only upload JPG/PNG file!");
  }
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isLt2M) {
    message.error("Image must smaller than 2MB!");
  }
  return isJpgOrPng && isLt2M;
};

const TestPage: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [imageUrl, setImageUrl] = useState<string>();
  const [file, setFile] = useState(null);
  const [form] = Form.useForm();

  const handleChange = (info) => {
    if (info.file.status === "done") {
      setFile(info.file.originFileObj);
      getBase64(info.file.originFileObj as FileType, (url) => {
        setLoading(false);
        setImageUrl(url);
      });
    }
  };

  const doSubmit = async (values) => {
    //在这里拿到图片和图片的描述和作者，调用后端的upload接口将图片和图片信息一起上传到后端
    if (!file) {
      message.error("请选择上传图片");
      return;
    }

    const formData = new FormData();
    formData.append("image", file);
    formData.append("name", values.name);
    formData.append("description", values.description);

    try {
      await request("/test/upload/test", {
        method: "POST",
        headers: {
          "Content-Type": "multipart/form-data",
        },
        data: formData,
      });
      message.success("上传成功");
      form.resetFields();
      setFile(null);
    } catch (error) {
      message.error("上传失败");
      console.error("上传错误:", error);
    }
  };

  const uploadButton = (
    <button style={{ border: 0, background: "none" }} type="button">
      {loading ? <LoadingOutlined /> : <PlusOutlined />}
      <div style={{ marginTop: 8 }}>Upload</div>
    </button>
  );
  const normFile = (e: any) => {
    if (Array.isArray(e)) {
      return e;
    }
    return e?.fileList;
  };
  return (
    <>
      <Form
        labelCol={{ span: 4 }}
        wrapperCol={{ span: 14 }}
        layout="horizontal"
        style={{ maxWidth: 600 }}
        onFinish={doSubmit}
        onChange={handleChange}
      >
        <Form.Item
          name="image"
          valuePropName="fileList"
          getValueFromEvent={normFile}
        >
          <Upload
            name="avatar"
            listType="picture-card"
            className="avatar-uploader"
            showUploadList={false}
            beforeUpload={beforeUpload}
            onChange={handleChange}
          >
            {imageUrl ? (
              <img src={imageUrl} alt="avatar" style={{ width: "100%" }} />
            ) : (
              uploadButton
            )}
          </Upload>
        </Form.Item>
        <Form.Item label="name" name="name">
          <Input />
        </Form.Item>
        <Form.Item label="description" name="description">
          <Input />
        </Form.Item>
        <Form.Item label={null}>
          <Button type="primary" htmlType="submit">
            提交
          </Button>
        </Form.Item>
      </Form>
      <Image
        src="http://localhost:8123/api/test/image/apple.jpeg"
        alt="aaa"
        width={44}
        height={44}
      ></Image>
    </>
  );
};

export default TestPage;
