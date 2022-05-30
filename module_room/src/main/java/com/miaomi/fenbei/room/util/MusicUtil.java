package com.miaomi.fenbei.room.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.MediaStore;

import com.miaomi.fenbei.base.bean.db.MusicBean;
import com.miaomi.fenbei.base.util.FileUtil;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MusicUtil {

    public interface ScanMusicCallback {
        void onFinish(ArrayList<MusicBean> musicList);
    }

    public interface LocalScanMusicCallback {
        void onFinish(ArrayList<MusicBean> musicList);
    }

    /**
     * 扫描歌曲
     */
    private static ArrayList<MusicBean> musicList = new ArrayList<>();
    public static void scanMusic2(final Context context, final LocalScanMusicCallback callback) {
        musicList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = context.getContentResolver().query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        new String[]{
                                BaseColumns._ID,
                                MediaStore.Audio.AudioColumns.IS_MUSIC,
                                MediaStore.Audio.AudioColumns.TITLE,
                                MediaStore.Audio.AudioColumns.ARTIST,
                                MediaStore.Audio.AudioColumns.ALBUM,
                                MediaStore.Audio.AudioColumns.ALBUM_ID,
                                MediaStore.Audio.AudioColumns.DATA,
                                MediaStore.Audio.AudioColumns.DISPLAY_NAME,
                                MediaStore.Audio.AudioColumns.SIZE,
                                MediaStore.Audio.AudioColumns.DURATION
                        },
                        null,
                        null,
                        MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
                if (cursor == null) {
                    return;
                }
                while (cursor.moveToNext()) {
                    // 是否为音乐
                    int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
                    if (isMusic == 0) {
                        continue;
                    }
                    long id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
                    // 标题
                    String title = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE)));
                    // 艺术家
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST));
                    // 专辑
                    String album = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)));
                    // 专辑封面id，根据该id可以获得专辑封面图片
                    long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_ID));
                    // 持续时间
                    long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    // 音乐文件路径
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
                    // 音乐文件名
                    String fileName = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DISPLAY_NAME)));
                    // 音乐文件大小
                    long fileSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                    String albumImg = loadCoverFromMediaStore(context,albumId);
                    if (albumImg == null){
                        albumImg = "";
                    }
                    if (duration >30000){
                        MusicBean bean = new MusicBean();
                        bean.setMusicId(id);
                        bean.setName("[本地]"+title);
                        bean.setSinger(artist);
                        bean.setSize(FileUtil.getFileMB(fileSize));
                        bean.setUploader(artist);
                        bean.setUrl(path);
                        musicList.add(bean);
                    }
                }
                cursor.close();
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFinish(musicList);
                    }
                });
            }
        }).start();
    }

//    /**
//     * 扫描歌曲
//     */
//    public static void scanMusic(Context context, ScanMusicCallback callback) {
//        LitePal.deleteAll(MusicBean.class);
//        ArrayList<MusicBean> musicList = new ArrayList<>();
//        Cursor cursor = context.getContentResolver().query(
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                new String[]{
//                        BaseColumns._ID,
//                        MediaStore.Audio.AudioColumns.IS_MUSIC,
//                        MediaStore.Audio.AudioColumns.TITLE,
//                        MediaStore.Audio.AudioColumns.ARTIST,
//                        MediaStore.Audio.AudioColumns.ALBUM,
//                        MediaStore.Audio.AudioColumns.ALBUM_ID,
//                        MediaStore.Audio.AudioColumns.DATA,
//                        MediaStore.Audio.AudioColumns.DISPLAY_NAME,
//                        MediaStore.Audio.AudioColumns.SIZE,
//                        MediaStore.Audio.AudioColumns.DURATION
//                },
//                null,
//                null,
//                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
//        if (cursor == null) {
//            return;
//        }
//        while (cursor.moveToNext()) {
//            // 是否为音乐
//            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
//            if (isMusic == 0) {
//                continue;
//            }
//            long id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
//            // 标题
//            String title = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE)));
//            // 艺术家
//            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST));
//            // 专辑
//            String album = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)));
//            // 专辑封面id，根据该id可以获得专辑封面图片
//            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_ID));
//            // 持续时间
//            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
//            // 音乐文件路径
//            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
//            // 音乐文件名
//            String fileName = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DISPLAY_NAME)));
//            // 音乐文件大小
//            long fileSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
//            String albumImg = loadCoverFromMediaStore(context,albumId);
//            if (albumImg == null){
//                albumImg = "";
//            }
//            if (duration >30000){
//                MusicBean music = new MusicBean(id, title, artist, album, albumId, duration, path, fileName, fileSize,albumImg);
//                musicList.add(music);
//                music.save();   //同步数据库
//            }
//        }
//        cursor.close();
//        callback.onFinish(musicList);
//    }

//    public static ArrayList<MusicBean> getAllMusic(){
//        return (ArrayList<MusicBean>) LitePal.findAll(MusicBean.class);
//    }
//    public static MusicBean getPlayingMusic(){
//        return  LitePal.where("status = 1").findFirst(MusicBean.class);
//    }
//    public static MusicBean getMusicById(Long id){
//        return LitePal.where("musicId = ?",id.toString()).findFirst(MusicBean.class);
//    }

    public static int addMusic(MusicBean bean){
        if (bean.isSaved()){
            return 0;
        }else{
            if (bean.save()){
                return 1;
            }else{
                return -1;
            }
        }
    }
    public static void deleteMusic(Long id){
        LitePal.deleteAll(MusicBean.class, "musicId = ?",id.toString());
    }

    public static List<MusicBean> getAddMusic(){
        return LitePal.findAll(MusicBean.class);
    }

//    public static void removeMusic(Long id){
//        int value = LitePal.deleteAll(MusicBean.class, "musicId = ?", id.toString());
//    }

    private static String loadCoverFromMediaStore(Context context, long albumId) {
        Cursor cur = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        String album_art = "";
        if (cur != null && cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
            cur.close();
        }
//        Bitmap bm = null;
//        if (album_art != null) {
//            bm = BitmapFactory.decodeFile(album_art);
//        } else {
//            bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.common_default);
//        }
        return album_art;
    }

}
