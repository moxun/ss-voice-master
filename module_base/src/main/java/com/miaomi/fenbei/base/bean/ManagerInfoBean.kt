package com.miaomi.fenbei.base.bean


data class ManagerInfoBean (
        val manager_list:List<UserInfo>,
        val user_list:List<UserInfo>
)

data class RoleSetBean(
        val user_role:Int
)