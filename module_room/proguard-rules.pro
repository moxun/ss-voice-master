
#声网
#-libraryjars libs/agora-rtc-sdk.jar
-keep class io.agora.**{*;}

#litepal
-keep class org.litepal.** {*;}
-keep class * extends org.litepal.crud.DataSupport {*;}
-keep class * extends org.litepal.crud.LitePalSupport {*;}

