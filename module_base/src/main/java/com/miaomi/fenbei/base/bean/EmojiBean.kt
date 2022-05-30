package com.miaomi.fenbei.base.bean

data class EmojiBean (
    val emoji_list:List<EmojiGroupBean>
)

data class EmojiGroupBean(
        val emoji_group_id:Int,
        val emoji_group_icon:String,
        val emoji_item:List<List<EmojiItemBean>>,
        var isSelect:Boolean
)

data class EmojiItemBean(
        val emoji_gif:String,
        val emoji_id:Int,
        val emoji_group_id:Int,
        val emoji_icon:String,
        val emoji_text:String,
        var emoji_result:Int
)

data class EmojiIndex(
        val groupIndex:Int,
        val itemIndex:Int
)
