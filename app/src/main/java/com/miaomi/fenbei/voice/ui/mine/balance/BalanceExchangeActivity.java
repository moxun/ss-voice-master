//package com.miaomi.fenbei.voice.ui.mine.balance;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.miaomi.fenbei.common.bean.BaseBean;
//import com.miaomi.fenbei.common.bean.ExchangeDiamondsBean;
//import com.miaomi.fenbei.common.core.BaseActivity;
//import com.miaomi.fenbei.common.core.dialog.CommonDialog;
//import com.miaomi.fenbei.common.net.Callback;
//import com.miaomi.fenbei.common.net.NetService;
//import com.miaomi.fenbei.common.util.DataHelper;
//import com.miaomi.fenbei.common.util.LoadHelper;
//import com.miaomi.fenbei.common.util.StringUtil;
//import com.miaomi.fenbei.common.util.ToastUtil;
//import com.miaomi.fenbei.voice.R;
//import com.miaomi.fenbei.common.VerifyPwdDialog;
//import com.miaomi.fenbei.voice.ui.ExchangePwdSettingActivity;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class BalanceExchangeActivity extends BaseActivity implements View.OnClickListener {
//    private TextView tvMoney;
//    private RecyclerView recyclerView;
//    private List<ExchangeDiamondsBean.GoodsBean> mGoodsList = new ArrayList<>();
////    private ExchangeDiamondsBean.GoodsBean choseBean;
//    private DiamondsListAdapter adapter;
//    private TextView pwdsetting;
//    private double earning;
//    private LinearLayout parentLL;
//    private LoadHelper loadHelper;
//
//    public static Intent getIntent(Context context) {
//        return new Intent(context, BalanceExchangeActivity.class);
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.user_activity_balance_exchange;
//    }
//
//    @Override
//    public void initView() {
//        tvMoney = findViewById(R.id.tv_money);
//        recyclerView = findViewById(R.id.recycler_view);
//        pwdsetting = findViewById(R.id.tv_pwd_setting);
//        parentLL = findViewById(R.id.ll_parent);
//        pwdsetting.setOnClickListener(this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new DiamondsListAdapter();
//        recyclerView.setAdapter(adapter);
//        findViewById(R.id.tv_exchange_history).setOnClickListener(this);
//        if (DataHelper.INSTANCE.getUserInfo().getExchange_pwd() == ExchangePwdSettingActivity.EXCHANGE_PWD_SETTING){
//            pwdsetting.setText("设置密码");
//        }else {
//            pwdsetting.setText("修改密码");
//        }
//        loadHelper = new LoadHelper();
//        loadHelper.registerLoad(parentLL);
//        getData();
//    }
//
//
//    private void getData() {
//        NetService.Companion.getInstance(this).getIncomeInfo(DataHelper.INSTANCE.getLoginToken(), new Callback<ExchangeDiamondsBean>() {
//            @Override
//            public boolean isAlive() {
//                return false;
//            }
//
//            @Override
//            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                loadHelper.setErrorCallback(code, v -> getData());
//            }
//
//            @Override
//            public void onSuccess(int nextPage, ExchangeDiamondsBean bean, int code) {
//                loadHelper.bindView(code);
//                mGoodsList.clear();
//                earning = bean.getEarning();
//                tvMoney.setText(StringUtil.formatDouble(bean.getEarning()));
//                if (bean.getList() != null) {
//                    mGoodsList.addAll(bean.getList());
//                }
//                adapter.notifyDataSetChanged();
//            }
//        });
//    }
//
//    void submitOrder(String pwd,String id) {
//
////        NetService.Companion.getInstance(this).exchangeDiamonds(pwd, id, new Callback<BaseBean>() {
////
////            @Override
////            public boolean isAlive() {
////                return false;
////            }
////
////            @Override
////            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
////                ToastUtil.INSTANCE.suc(BalanceExchangeActivity.this, msg);
////            }
////
////            @Override
////            public void onSuccess(int nextPage, BaseBean bean, int code) {
////                getData();
////                ToastUtil.INSTANCE.suc(BalanceExchangeActivity.this, "兑换成功");
////            }
////        });
//    }
//
//
//    private CommonDialog mCommonDialog;
//
//    @Override
//    public void onClick(View v) {
//       int id = v.getId();
//       if (id == R.id.tv_pwd_setting){
//           ExchangePwdSettingActivity.start(this,DataHelper.INSTANCE.getUserInfo().getExchange_pwd());
//       }
//       if (id == R.id.tv_exchange_history){
//            startActivity(BalanceHistoryActivity.getIntent(this));
//       }
//    }
//
//    private void exchage(ExchangeDiamondsBean.GoodsBean bean){
//        if (bean != null) {
//            if (earning < bean.getPrice()){
//                ToastUtil.INSTANCE.suc(this, "收益余额不足,无法兑换");
//            }else{
//                if (DataHelper.INSTANCE.getUserInfo().getExchange_pwd() == ExchangePwdSettingActivity.EXCHANGE_PWD_SETTING){
//                    mCommonDialog = new CommonDialog(this);
//                    mCommonDialog.setTitle("友情提示");
//                    mCommonDialog.setContent("确定兑换" + bean.getDiamond() + "钻石吗？");
//                    mCommonDialog.setLeftBt("取消", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mCommonDialog.dismiss();
//                        }
//                    });
//                    mCommonDialog.setRightBt("确定", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            submitOrder("",String.valueOf(bean.getId()));
//                            mCommonDialog.dismiss();
//                        }
//                    });
//                    mCommonDialog.show();
//                }else{
//                    new VerifyPwdDialog(BalanceExchangeActivity.this).setOnClickListener(new VerifyPwdDialog.OnClickListener() {
//                        @Override
//                        public void onRefuseClick() {
//
//                        }
//
//                        @Override
//                        public void onAgreeClick(String pwd) {
//                            submitOrder(pwd,String.valueOf(bean.getId()));
//                        }
//                    }).show();
//                }
//            }
//        } else {
//            ToastUtil.INSTANCE.suc(this, "请选择要兑换的钻石");
//        }
//    }
//
//    class DiamondsListAdapter extends RecyclerView.Adapter<ItemHolder> {
//
//        @NonNull
//        @Override
//        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_balance_exchange, parent, false);
//            return new ItemHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull final ItemHolder holder, final int position) {
//            holder.tvPriceGood.setText(String.format("%s钻石", mGoodsList.get(position).getDiamond()));
//            holder.tvGiveGood.setText(String.format("+%s钻石", mGoodsList.get(position).getSend()));
//            holder.tvPriceMoney.setText(String.format("¥%s", mGoodsList.get(position).getPrice()));
//
//
////            holder.itemView.setSelected(mGoodsList.get(position).isSelected());
//
//            holder.tvPriceMoney.setOnClickListener(v -> exchage(mGoodsList.get(position)));
//        }
//
//        @Override
//        public int getItemCount() {
//            return mGoodsList.size();
//        }
//    }
//
//    class ItemHolder extends RecyclerView.ViewHolder {
//        private TextView tvPriceGood, tvPriceMoney, tvGiveGood;
//        private ImageView ivDiamondIcon;
//
//        public ItemHolder(View itemView) {
//            super(itemView);
//            tvPriceGood = itemView.findViewById(R.id.tv_price_good);
//            tvPriceMoney = itemView.findViewById(R.id.tv_price_money);
//            tvGiveGood = itemView.findViewById(R.id.tv_give_good);
//            ivDiamondIcon = itemView.findViewById(R.id.iv_diamond_icon);
//        }
//    }
//}
