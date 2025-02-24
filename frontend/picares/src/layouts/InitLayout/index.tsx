"use client";
import { getLoginUser } from "@/api/userapi";
import { setLoginUser } from "@/stores/loginUser";
import React, { useCallback, useEffect } from "react";
import { useDispatch } from "react-redux";

const InitLayout: React.FC<
  Readonly<{
    children: React.ReactNode;
  }>
> = ({ children }) => {
  const dispatch = useDispatch();

  const doInit = useCallback(async () => {
    const res = await getLoginUser();
    dispatch(setLoginUser(res.data));
  }, []);

  useEffect(() => {
    doInit();
  }, []);

  return <>{children}</>;
};

export default InitLayout;
