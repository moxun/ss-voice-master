//package com.miaomi.fenbei.voice.ui.main.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.miaomi.fenbei.voice.ImgUtil;
//import com.miaomi.fenbei.voice.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class PersonRoomMicListAdapter extends RecyclerView.Adapter<PersonRoomMicListAdapter.ViewHodler> {
//    private Context context;
//    private List<String> mList = new ArrayList<>();
//
//    public PersonRoomMicListAdapter(Context context) {
//        this.context = context;
//    }
//
//    public void setData(List<String> list){
//        mList.clear();
//        mList.addAll(list);
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public PersonRoomMicListAdapter.ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ViewHodler(LayoutInflater.from(context).inflate(R.layout.item_person_room_mic, parent, false));
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull PersonRoomMicListAdapter.ViewHodler holder, final int position) {
//
//        if (position < mList.size()){
//            ImgUtil.INSTANCE.loadFaceIcon(context,mList.get(position),holder.avterIv);
//        }else{
//            ImgUtil.INSTANCE.loadCircleImg(context,R.drawable.icon_home_mic_add,holder.avterIv, R.drawable.icon_home_mic_add);
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 5;
//    }
//
//    static class ViewHodler extends RecyclerView.ViewHolder{
//        ImageView avterIv;
//        public ViewHodler(View itemView) {
//            super(itemView);
//            avterIv = itemView.findViewById(R.id.iv_avter);
//        }
//    }
//}
