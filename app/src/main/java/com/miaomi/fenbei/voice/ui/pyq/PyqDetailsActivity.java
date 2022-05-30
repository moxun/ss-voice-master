package com.miaomi.fenbei.voice.ui.pyq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.alibaba.android.arouter.facade.annotation.Route;

import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.FindBean;
import com.miaomi.fenbei.base.bean.FollowBean;
import com.miaomi.fenbei.base.bean.HeartBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.msg.MsgManager;
import com.miaomi.fenbei.base.core.msg.SendMsgListener;
import com.miaomi.fenbei.base.dialog.ApplyDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.StatusBarUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.HeartMeView;
import com.miaomi.fenbei.base.widget.HeartView;

import com.miaomi.fenbei.imkit.ui.PrivateChatActivity;

import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.dialog.UserOperateDialog;
import com.miaomi.fenbei.voice.ui.mine.user_homepage.SendPersonGiftDialog;

import com.tencent.imsdk.TIMMessage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
@Route(path = "/app/pyqdetails")
public class PyqDetailsActivity extends BaseActivity {



    private List<Fragment> mTabList = new ArrayList<>();
    private String friendscircleId;
    private int parent_id=0;
    private FindChildDetailsAdapter findChildDetailsAdapter;
    private RecyclerView childFindRv;
    private List<FindBean> list;
    private RecyclerView commentRv;
    private CommentListAdapter commentListAdapter;
    private TextView commentnumTv;
    private EditText commentEt;
    private TextView sendcommentTv;

    public static void start(Activity context,int type) {
        Intent intent = new Intent(context, PyqDetailsActivity.class);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_pyq_details;
    }

    @Override
    public void initView() {
//        StatusBarUtil.setStatusBarTextColor(this,true);
        StatusBarUtil.setColorNoTranslucent(this, Color.parseColor("#642192"));
        friendscircleId = getIntent().getStringExtra("friendscircleId");
        commentnumTv=findViewById(R.id.tv_comment_num);
        childFindRv = findViewById(R.id.rv_find);
        commentRv=findViewById(R.id.rv_comment);
        commentEt=findViewById(R.id.et_comment);
        sendcommentTv=findViewById(R.id.tv_send_comment);
        list=new ArrayList<>();
        findChildDetailsAdapter = new FindChildDetailsAdapter();
//        findChildDetailsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);

        childFindRv.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        childFindRv.setAdapter(findChildDetailsAdapter);
        commentListAdapter=new CommentListAdapter(getBaseContext());
        commentRv.setAdapter(commentListAdapter);
        commentRv.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        getFriendsCircle();
        commentListAdapter.setOnOnCommentClickListener(new CommentListAdapter.OnCommentClickListener() {
            @Override
            public void onReply(int uid) {
                parent_id=uid;
                commentEt.setFocusable(true);
                commentEt.setFocusableInTouchMode(true);
                commentEt.requestFocus();//获取焦点 光标出现
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void onDel(int uid) {
                final ApplyDialog outDialog = new ApplyDialog(PyqDetailsActivity.this);
                outDialog.setOutIcon("0");
                outDialog.setContent("确定要删除评论?");
                outDialog.setLeftBt("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        outDialog.dismiss();
                    }
                });
                outDialog.setRightBt("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        outDialog.dismiss();
                        delComment(uid);

                    }
                });
                outDialog.show();

            }
        });
        findChildDetailsAdapter.setOnFindItemOprateListener(new FindChildDetailsAdapter.OnFindItemOprateListener() {
            @Override
            public void onSendGift(String uid) {
                if (DataHelper.INSTANCE.getUserInfo() == null){
                    return;
                }

                SendPersonGiftDialog dialog = new SendPersonGiftDialog(uid);
                dialog.show(getSupportFragmentManager());
            }

            @Override
            public void onReply(String id) {
                commentEt.setFocusable(true);
                commentEt.setFocusableInTouchMode(true);
                commentEt.requestFocus();//获取焦点 光标出现
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            }

            @Override
            public void onTalk(String uid,String face) {
                if (DataHelper.INSTANCE.getUserInfo() == null){
                    return;
                }
                PrivateChatActivity.startActivity(getBaseContext(), uid, face);
            }

            @Override
            public void onHeart(FindBean item, HeartMeView heartMeView, HeartView heartView, TextView heartNum) {
                NetService.Companion.getInstance(getBaseContext()).heartPYQ(item.getId(), new Callback<HeartBean>() {
                    @Override
                    public void onSuccess(int nextPage, HeartBean bean, int code) {

                        if (bean.isHeart_status()){
                            heartNum.setVisibility(View.VISIBLE);
                            heartMeView.setVisibility(View.GONE);
//                            heartMeView.setContent(bean.getHearts());
                            heartNum.setText((item.getHeart_num() + 1) +"");
                            item.setHeart_num(item.getHeart_num() + 1);
                            heartView.success();
                        }else{
                            if (item.getHeart_num() > 0){
                                heartNum.setVisibility(View.VISIBLE);
                                heartMeView.setVisibility(View.GONE);
//                                heartMeView.setContent(bean.getHearts());
                                heartNum.setText((item.getHeart_num() - 1) +"");
                                item.setHeart_num(item.getHeart_num() - 1);
                                heartView.faile();
                            }
                        }
                    }

                    @Override
                    public void onSuccessHasMsg(@NotNull String msg, HeartBean bean, int code) {
                        super.onSuccessHasMsg(msg, bean, code);
                        ToastUtil.INSTANCE.suc(getBaseContext(), msg);
                    }

                    @Override
                    public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                        ToastUtil.INSTANCE.suc(getBaseContext(), msg);
                    }

                    @Override
                    public boolean isAlive() {
                        return isLive();
                    }
                });
            }



            @Override
            public void onCare(String uid) {
                follow(uid);

            }

            @Override
            public void onDelate(String id) {
                final ApplyDialog outDialog = new ApplyDialog(PyqDetailsActivity.this);
                outDialog.setOutIcon("0");
                outDialog.setContent("真的要删除这条动态吗?");
                outDialog.setLeftBt("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        outDialog.dismiss();
                    }
                });
                outDialog.setRightBt("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        outDialog.dismiss();
                        NetService.Companion.getInstance(getBaseContext()).delFriendsCircle(id, new Callback<BaseBean>() {
                            @Override
                            public void onSuccess(int nextPage, BaseBean bean, int code) {
                                finish();
                            }

                            @Override
                            public void onSuccessHasMsg(@NotNull String msg, BaseBean bean, int code) {
                                super.onSuccessHasMsg(msg, bean, code);
                                ToastUtil.INSTANCE.suc(getBaseContext(), msg);
                            }

                            @Override
                            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                                ToastUtil.INSTANCE.suc(getBaseContext(), msg);
                            }

                            @Override
                            public boolean isAlive() {
                                return isLive();
                            }
                        });

                    }
                });
                outDialog.show();

            }

            @Override
            public void onMoreOparate(String id) {
                UserOperateDialog dialog = new UserOperateDialog();
                dialog.setOnUserOperateClickListener(new UserOperateDialog.OnUserOperateClickListener() {
                    @Override
                    public void onItemClick(int operateType) {
                        if (operateType == UserOperateDialog.USER_OPERATE_GZ){
                        }
                        if (operateType == UserOperateDialog.USER_OPERATE_JB){
                            ToastUtil.INSTANCE.error(getBaseContext(),"举报成功");
                        }
                    }
                });
//                dialog.show(getFragmentManager());
            }
        });

        sendcommentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFriendsCircleComment();
            }
        });
    }
    private void delComment(int id) {

        NetService.Companion.getInstance(getBaseContext()).delFriendsComment( friendscircleId,id, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                getFriendsCircle();

            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(getBaseContext(), msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
    private void sendFriendsCircleComment() {
        String comment= commentEt.getText().toString();
        if (TextUtils.isEmpty(comment)){
            ToastUtil.INSTANCE.i(this,"评论内容不能为空");
            return;
        }
        commentEt.setText("");
        commentEt.setFocusable(false);
        commentEt.setFocusableInTouchMode(false);
//        commentEt.requestFocus();//获取焦点 光标出现
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        NetService.Companion.getInstance(getBaseContext()).sendFriendsCircleComment( friendscircleId,comment,parent_id, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                getFriendsCircle();

            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(getBaseContext(), msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
    private void follow(String uid) {
        NetService.Companion.getInstance(getBaseContext()).addConcern(DataHelper.INSTANCE.getLoginToken(), uid, new Callback<FollowBean>() {
            @Override
            public void onSuccess(int nextPage, FollowBean bean, int code) {
                //                0 未关注 1已关注
                if (bean.getIs_follow() == 1) {
                    ToastUtil.INSTANCE.suc(getBaseContext(), "关注成功");
                    MsgManager.INSTANCE.sendFollowMsg(uid, new SendMsgListener() {
                        @Override
                        public void onSendSuc(TIMMessage timMessage) {

                        }

                        @Override
                        public void onSendFail(int i, String s) {

                        }
                    });
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(getBaseContext(), msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }


    private void getFriendsCircle(){

        NetService.Companion.getInstance(getBaseContext()).getFriendsCircle(friendscircleId, new Callback<FindBean>() {
            @Override
            public void onSuccess(int nextPage, FindBean findBean, int code) {
                commentnumTv.setText("全部评论 "+findBean.getComment_number());

                list.clear();
                list.add(findBean);
                findChildDetailsAdapter.setNewData(list);
                commentListAdapter.setData(findBean.getCommentBeans(),findBean.isIs_own());
            }


            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {


            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
}
