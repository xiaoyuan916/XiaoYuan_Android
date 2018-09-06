package com.sgcc.pda.jszp.bean;

import java.util.List;

/**
 * Created by Beckham on 2017/5/18.
 */
public class UserList extends BaseEntity {


    public List<user> USERS;

    public List<user> getUSERS() {
        return USERS;
    }

    public void setUSERS(List<user> USERS) {
        this.USERS = USERS;
    }

    public static class user {

        public String OPER_ID;
        public String USER_CODE;//使用人账号
        public String USER_NAME;//使用人姓名

        public String getOPER_ID() {
            return OPER_ID;
        }

        public void setOPER_ID(String OPER_ID) {
            this.OPER_ID = OPER_ID;
        }

        public String getUSER_CODE() {
            return USER_CODE;
        }

        public void setUSER_CODE(String USER_CODE) {
            this.USER_CODE = USER_CODE;
        }

        public String getUSER_NAME() {
            return USER_NAME;
        }

        public void setUSER_NAME(String USER_NAME) {
            this.USER_NAME = USER_NAME;
        }
    }

}
