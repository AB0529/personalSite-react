export type TStatus = "OK" | "UNAUTHORIZED" | "BAD_REQUEST";
export const STATUS_OK = "OK";
export const STATUS_UNAUTHORIZED = "UNAUTHORIZED";
export const STATUS_BAD_REQUEST = "BAD_REQUEST";

export type TRole = "ROLE_USER" | "ROLE_ADMIN";
export const ADMIN_ROLE = "ROLE_ADMIN";
export interface IUser {
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  authorities: [
    {
      authority: TRole;
    }
  ];
}
export interface IDBUser {
  id: number;
  username: string;
  password: string;
  email: string;
  firstName: string;
  lastName: string;
  roles: [
    {
      id: number;
      name: TRole;
    }
  ];
}

export interface IApiResponse {
  status: TStatus;
  message: string;
  result: any;
}
