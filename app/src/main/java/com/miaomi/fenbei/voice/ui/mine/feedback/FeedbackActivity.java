package com.miaomi.fenbei.voice.ui.mine.feedback;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

public class FeedbackActivity extends BaseActivity {
    private EditText etFeedbackContent;
    private EditText etContactWay;
    private TextView tvFeedbackSubmit;
    private CharSequence content;

    public static Intent getIntent(Context context) {
        return new Intent(context, FeedbackActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_feedback;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        etFeedbackContent =  findViewById(R.id.et_feedback_content);
        etContactWay =  findViewById(R.id.et_contact_way);
        tvFeedbackSubmit =  findViewById(R.id.tv_feedback_submit);
        tvFeedbackSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });
//        setTextNumber();

    }

//    private void setTextNumber() {
//        etFeedbackContent.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                content = s;
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String strs = etFeedbackContent.getText().toString();
//                String str = StringUtil.removePunctuation(strs.replace(" ", ""));
//                if (!strs.equals(str)) {
//                    etFeedbackContent.setText(str);
//                    etFeedbackContent.setSelection(start);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String number = etFeedbackContent.getText().toString().length() + "/200";
//                final SpannableString spannableString = new SpannableString(number);
//                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(
//                        Color.parseColor("#FD7F8F"));
//                spannableString.setSpan(foregroundColorSpan, 0, number.length() - 4,
//                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//                tvContentNumber.setText(spannableString);
//            }
//        });
//    }

    private void submitFeedback() {
        String content = etFeedbackContent.getText().toString().replace(" ", "");
        if (content.isEmpty()) {
            ToastUtil.INSTANCE.error(FeedbackActivity.this, "内容不能为空");
            return;
        }
        NetService.Companion.getInstance(FeedbackActivity.this).submitFeedback(DataHelper.INSTANCE.getLoginToken()
                , content, etContactWay.getText().toString(), new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(FeedbackActivity.this, "提交成功");
                finish();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(FeedbackActivity.this, msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
}
