package com.miaomi.fenbei.base.bean;

import java.util.List;

public class MSGRefreshMicBean extends MSGBaseBean{
    private List<UserInfo> users;
    private UserInfo operatorInfo;

    public UserInfo getOperatorInfo() {
        return operatorInfo;
    }

    public void setOperatorInfo(UserInfo operatorInfo) {
        this.operatorInfo = operatorInfo;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }
}
