package com.miaomi.fenbei.base.net

class Data <T>{

    companion object {
        const val CODE_SUC = 0
        const val CODE_HTTP = 500
        const val CODE_EMPTY = 501
        const val CODE_EMPTY_ROOM = 5001
        const val CODE_EMPTY_FIND = 5002
        const val CODE_ERROR = 1000
        const val CODE_ROOM_CLOSED = 1008
        const val CODE_TOKEN_INVALID = 1001
        const val CODE_LOADING = 503
        const val JOIN_ROOM_NEED_PWD = 10000
        const val CODE_ILLEGAL_REQUEST = 1011
        const val CODE_ACCOUNT_LOGOFF = 1100
        const val CODE_MYSTERY_MAN = 1504
    }

    var code : Int = 0
    var msg: String = ""
    var page: Page = Page()
    var data: T? = null
    var rule: String = ""

    inner class Page {
        var current = 1
        var last = 1
    }
}