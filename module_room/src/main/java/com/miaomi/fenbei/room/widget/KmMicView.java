package com.miaomi.fenbei.room.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.room.ui.callback.MicClickListener;
import com.miaomi.fenbei.base.bean.EmojiItemBean;
import com.miaomi.fenbei.base.bean.MsgBean;
import com.miaomi.fenbei.base.bean.UserInfo;
import com.miaomi.fenbei.gift.GiftManager;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;

public class KmMicView extends ConstraintLayout {

    private LinearLayout mMicArea1Layout;
    private LinearLayout mMicArea2Layout;

    private MicItemView mMicLayout1;
    private MicItemView mMicLayout2;
    private MicItemView mMicLayout3;
    private MicItemView mMicLayout4;
    private MicItemView mMicLayout5;
    private MicItemView mMicLayout6;
    private MicItemView mMicLayout7;
    private MicItemView mMicLayout8;

    private MicItemView[] mMicLayouts;

    private MicClickListener listener;

    private List<UserInfo> mList = new ArrayList<>();

    public KmMicView(Context context) {
        super(context);
        initView(context);
    }

    public KmMicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public KmMicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context context) {


        View view = LayoutInflater.from(context).inflate(R.layout.room_layout_chatting_mic_view, this,true);

        mMicLayout1 = view.findViewById(R.id.mic_1_layout);
        mMicLayout2 = view.findViewById(R.id.mic_2_layout);
        mMicLayout3 = view.findViewById(R.id.mic_3_layout);
        mMicLayout4 = view.findViewById(R.id.mic_4_layout);
        mMicLayout5 = view.findViewById(R.id.mic_5_layout);
        mMicLayout6 = view.findViewById(R.id.mic_6_layout);
        mMicLayout7 = view.findViewById(R.id.mic_7_layout);
        mMicLayout8 = view.findViewById(R.id.mic_8_layout);

        mMicLayouts = new MicItemView[]{mMicLayout1, mMicLayout2, mMicLayout3, mMicLayout4, mMicLayout5, mMicLayout6, mMicLayout7, mMicLayout8};

        mMicLayout1.setMicPosition(1);
        mMicLayout2.setMicPosition(2);
        mMicLayout3.setMicPosition(3);
        mMicLayout4.setMicPosition(4);
        mMicLayout5.setMicPosition(5);
        mMicLayout6.setMicPosition(6);
        mMicLayout7.setMicPosition(7);
        mMicLayout8.setMicPosition(8);
        mMicArea1Layout = view.findViewById(R.id.mic_area1_layout);
        mMicArea2Layout = view.findViewById(R.id.mic_area2_layout);
        mMicLayout1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.size() >= 1) {
                    listener.onMicItemClick(v, mList.get(0), 0);
                }
            }
        });
        mMicLayout2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.size() >= 2) {
                    listener.onMicItemClick(v, mList.get(1), 1);
                }
            }
        });
        mMicLayout3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.size() >= 3) {
                    listener.onMicItemClick(v, mList.get(2), 2);
                }
            }
        });
        mMicLayout4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.size() >= 4) {
                    listener.onMicItemClick(v, mList.get(3), 3);
                }
            }
        });
        mMicLayout5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.size() >= 5) {
                    listener.onMicItemClick(v, mList.get(4), 4);
                }
            }
        });
        mMicLayout6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.size() >= 6) {
                    listener.onMicItemClick(v, mList.get(5), 5);
                }
            }
        });
        mMicLayout7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.size() >= 7) {
                    listener.onMicItemClick(v, mList.get(6), 6);
                }
            }
        });
        mMicLayout8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.size() >= 8) {
                    listener.onMicItemClick(v, mList.get(7), 7);
                }
            }
        });

    }

    public void addItemClickListener(MicClickListener listener) {
        this.listener = listener;

    }

    public void clearPreInfo(UserInfo userInfo) {
        if (userInfo == null){
            return;
        }
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getUser_id() > 0 && mList.get(i).getUser_id() == userInfo.getUser_id()) {
                UserInfo userInfo1 = new UserInfo();
                userInfo1.setNickname((i + 1) + "麦");
                userInfo1.setType(i + 1);
                userInfo1.setStatus(mList.get(i).getStatus() == 2 ? 2 : 0);
                userInfo1.setMike(mList.get(i).getMike());
                updateItem(i, userInfo1);

            }
        }
    }

    public void showEmoji(final int position, EmojiItemBean emojiItemBean){
        if (position < 0 || position > mList.size()) {
            return;
        }
        mMicLayouts[position].showEmoji(emojiItemBean);
    }

    public void showVoiceWave(int position, UserInfo userInfo) {
        if (userInfo.getMic_speaking()) {
            mMicLayouts[position].showVoiceWave();
        } else {
            mMicLayouts[position].stopVoiceWave();
        }
    }

    public void clearItemMike(int position) {
        if (position == 8) {
            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).setMike(0);
                mMicLayouts[i].clearItemMike();
            }
        } else {
            mList.get(position).setMike(0);
            mMicLayouts[position].clearItemMike();
        }
    }

    public void updateItemMike(MsgBean bean) {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getUser_id() == bean.getToUserInfo().getUser_id()) {
                int mike = bean.getGiftBean().getGiftPrice();
                mList.get(i).setMike(mMicLayouts[i].getCharm() + mike * bean.getGiftBean().getGiftNum());
                mMicLayouts[i].setCharm(mList.get(i).getMike());
            }
        }
    }

    public void updateItem(int position, UserInfo userInfo, Boolean isUpdateMike) {
        updateItem(position, userInfo);
        mMicLayouts[position].setCharm(userInfo.getMike());
    }

    public void updateItem(int position, UserInfo userInfo) {
        if (position < 0 || position >= mList.size()) {
            return;
        }
        mMicLayouts[position].updata(userInfo);
        mList.set(position, userInfo);

    }

    public boolean hasEmpty() {
        for (UserInfo userInfo : mList) {
            if (userInfo.getUser_id() == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
    }

    private void addGiftEndPoint(final int position, final MicItemView item){
        item.post(new Runnable() {
            @Override
            public void run() {
                int[] endLocation = new int[2];
                item.getLocationOnScreen(endLocation);
                final int endX = endLocation[0] + item.getWidth()/2 - 30;
                final int endY = endLocation[1] + item.getHeight()/2 - 30;
                GiftManager.getInstance().addMicPoint(position,endX,endY);
            }
        });

//        send_gift_btn.post(new Runnable() {
//            @Override
//            public void run() {
//                int[] endLocation = new int[2];
//                item.getLocationOnScreen(endLocation);
//                final int endX = endLocation[0] + item.getWidth()/2 - 30;
//                final int endY = endLocation[1] + item.getHeight()/2 - 30;
//                GiftManager.getInstance().addMicPoint(endX,endY);
//            }
//        });
    }


    //获取麦序位置
    public int getItemLayout(int userId){
        for (int i = 0;i<mList.size();i++){
            if (userId == mList.get(i).getUser_id()){
                return i+1;
            }
        }
        return GiftManager.GIFT_POSITION_NO;
    }

    public List<UserInfo> getData() {
        return mList;
    }

    public void refreshPosition(){
        addGiftEndPoint(1,mMicLayout1);
        addGiftEndPoint(2,mMicLayout2);
        addGiftEndPoint(3,mMicLayout3);
        addGiftEndPoint(4,mMicLayout4);
        addGiftEndPoint(5,mMicLayout5);
        addGiftEndPoint(6,mMicLayout6);
        addGiftEndPoint(7,mMicLayout7);
        addGiftEndPoint(8,mMicLayout8);
    }

    public void setData(List<UserInfo> list) {
        this.mList = list;
        if (list.size() == 4) {
            mMicArea2Layout.setVisibility(GONE);
        } else {
            mMicArea2Layout.setVisibility(VISIBLE);
        }

        for (int i = 0; i < list.size(); i++) {
            updateItem(i, list.get(i), true);
            if (!ChatRoomManager.INSTANCE.isReJoin()) {
                mMicLayouts[i].clearItem();
            }
        }
        invalidate();
    }

    public void refreshData(List<UserInfo> list) {
        for (int i = 0; i < list.size(); i++) {
            updateItem(i, list.get(i), true);
        }
        invalidate();
    }


}
