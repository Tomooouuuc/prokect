import { DEFAULT_USER } from "@/constant/user";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

const loginUserSlice = createSlice({
  name: "loginUser",
  initialState: DEFAULT_USER,
  reducers: {
    setLoginUser: (state, action: PayloadAction<API.LoginUserVO>) => {
      return {
        ...action.payload,
      };
    },
  },
});

export const { setLoginUser } = loginUserSlice.actions;
export default loginUserSlice.reducer;
