package com.miaomi.fenbei.base.config;


public interface BaseConfig {
    //性别
    int USER_INFO_GENDER_MAN = 1;  //男
    int USER_INFO_GENDER_FEMALE = 2;  //女

    int LOGIN_BY_USERNAME = 1;
    int LOGIN_BY_WX = 2;
    int LOGIN_BY_QQ = 3;
    int LOGIN_BY_PWD = 4;



    String QQ_APPID = "101950712";
    String QQ_APPSCREAT = "5d4ab953a323ba5672a577007f059c58";

    //    String APPID_WX = "wx551f7c20a69da3d4";
//    String APPID_WX_APPSCREAT = "0e84f39bf36b5d143464d86dc2c4c1a1";
    String UM_APPID = "6084dd4c9e4e8b6f61806e33";

    String APPID_WX = "wx2f72b4caaeb5c1a7";
    String APPID_WX_APPSCREAT = "aa122e60bc0d4dbe78a22ef9b18f12d2";

    String BASE_FP = "com.miaomi.liya.voice.FileProvider";

    long MAX_CACHE_MEMORY_SIZE = 10*1024*1024;

    String XY_YHXY = "https://apidecibel.cnciyin.com/html/official/ly_yhxy";
    String XY_YSXY = "https://apidecibel.cnciyin.com/html/official/ly_ysxy";
    String XY_YHCZXY = "https://apidecibel.cnciyin.com/html/official/ly_yhczxy";
    String URL_BLIND_RULE = "http://decibel-web.cnciyin.com/rule_love";
    String URL_STAR_EXPALIN = "http://decibel-web.cnciyin.com/rule_start";
    String H5_URL = "http://decibel-web.cnciyin.com/";

//    //km正式服
//    String BASE_URL = "https://kmi.94miao.com";
//    int BASE_IM_ID = 1400396844;
//    String BASE_BIG_GROUNP_ID = "@TGS#aKF4P5XGY";
//    String AGORA_APPID = "2b6b397041dc48109eda7d7aa54941df";


    //测试服地址
//    String BASE_URL = "http://test-new-km.94miao.com";
//    int BASE_IM_ID = 1400523941;
//    String BASE_BIG_GROUNP_ID = "@TGS#aNC6EKFHU";
//    String AGORA_APPID = "2b6b397041dc48109eda7d7aa54941df";

    //正式服地址
//    String BASE_URL = "https://apidecibel.cnciyin.com";
//    int BASE_IM_ID = 1400518115;
//    String BASE_BIG_GROUNP_ID = "@TGS#aP2PRTEHV";
//    String AGORA_APPID = "fe6a1bbf93934e8c85c82172b9fd18a3";
    String BASE_URL = "http://139.196.50.224:9001";
    int BASE_IM_ID = 1400671290;
    String BASE_BIG_GROUNP_ID = "@TGS#aSOOQJGIN";
    String AGORA_APPID = "9d89f6cdc47a4798949e494813343bc1";

    /**
     * 红包类型
     */
    // 拼手气红包
    int RED_PACKET_TYPE_LUCK = 1;
    // 普通红包
    int RED_PACKET_TYPE_COMMON = 2;
    /**
     * Banner类型
     */
    // 砸蛋
    int BANNER_MOLD_SMASH_EGG = 0;

    // 修仙
    int BANNER_MOLD_GAME_XX = 4;
    // 首冲
//    int BANNER_MOLD_FIRST_RECHARGE = 1;

    // 周星
    int BANNER_MOLD_WEEK_STAR = 2;

    // web广告
    int BANNER_MOLD_WEB = 3;

    // 藏宝
    int BANNER_MOLD_GAME_CB = 8;
    // 山水语音森林
    int BANNER_MOLD_GAME_ZS = 109;

    //贵族等级
    int NOBLE_LEVEL_ZJ = 8;
    int NOBLE_LEVEL_BJ = 9;
    int NOBLE_LEVEL_HJ = 10;
    int NOBLE_LEVEL_GJ = 11;
    int NOBLE_LEVEL_GW = 12;
    int NOBLE_LEVEL_DH = 13;
    int NOBLE_LEVEL_HS = 14;

    //进场特效
    int NOBLE_JC_LEVEL_ZJ = 156;
    int NOBLE_JC_LEVEL_BJ = 157;
    int NOBLE_JC_LEVEL_HJ = 158;
    int NOBLE_JC_LEVEL_GJ = 159;
    int NOBLE_JC_LEVEL_GW = 160;
    int NOBLE_JC_LEVEL_DH = 161;
    int NOBLE_JC_LEVEL_HS = 162;


    // 座位框
    int DRESS_TYPE_ZWK = 0;

    // 进程特效
    int DRESS_TYPE_JCTX = 1;

    // 座驾
    int DRESS_TYPE_ZJ = 2;
    // 勋章
    int DRESS_TYPE_XZ = 3;

}
