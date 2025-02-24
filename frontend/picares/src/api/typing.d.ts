declare namespace API {
  type UserLogin = {
    userAccount: string;
    userPassword: string;
  };
  type UserRegister = {
    userAccount: string;
    userPassword: string;
    checkPassword: string;
  };
  type UpdateUser = {
    id?: number;
    userName?: string;
    userAvatar?: string;
    userProfile?: string;
    userRole?: string;
  };
  type DeleteUser = {
    id?: number;
  };
  type SelectUser = {
    userAccount?: string;
    userName?: string;
    userProfile?: string;
    userRole?: string;
    current?: string;
    pageSize?: string;
    sortField?: string;
    sortOrder?: string;
  };
  type LoginUserVO = {
    id?: number;
    userAccount?: string;
    userName?: string;
    userAvatar?: string;
    userProfile?: string;
    userRole?: string;
  };
  type SelectUserVO = {
    id?: number;
    userAccount?: string;
    userName?: string;
    userAvatar?: string;
    userProfile?: string;
    userRole?: string;
    createTime?: string;
  };
  type SelectUserPageVO = {
    records?: SelectUserVO[];
    total?: number;
  };

  type BaseResponse<T> = {
    code?: number;
    data?: T;
    message?: string;
  };
}
