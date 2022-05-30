package com.miaomi.fenbei.voice.ui.mine.user_homepage;

import android.view.View;

import com.miaomi.fenbei.base.bean.GiftBean;
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.gift.fragment.PrivateGiftFragment;
import com.miaomi.fenbei.voice.R;

public class SendPersonGiftDialog extends BaseBottomDialog {

    private String userId;

    public SendPersonGiftDialog(String id) {
        userId = id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_send_person_gift;
    }

    @Override
    public void bindView(View v) {
        PrivateGiftFragment privateGiftFragment = PrivateGiftFragment.newInstance(userId);
        privateGiftFragment.setOnSendSuccess(new PrivateGiftFragment.OnSendSuccess() {
            @Override
            public void onSuc(GiftBean.DataBean gift) {
                if(gift != null && getActivity() != null){
                    ToastUtil.INSTANCE.suc(getActivity(),"送出"+gift.getNumber()+"个【"+gift.getName()+"】");
                }
                dismiss();
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.INSTANCE.suc(getActivity(),msg);
            }
        });
        getChildFragmentManager().beginTransaction()
                .add(R.id.content, privateGiftFragment)
                .commit();
    }
}
