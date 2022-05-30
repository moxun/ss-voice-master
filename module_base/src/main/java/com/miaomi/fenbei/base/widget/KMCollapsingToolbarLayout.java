package com.miaomi.fenbei.base.widget;//package com.miaomi.fenbei.base.widget;
//
//import android.animation.ValueAnimator;
//import android.content.Context;
//import android.content.res.ColorStateList;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Rect;
//import android.graphics.Typeface;
//import android.graphics.drawable.ColorDrawable;
//import android.graphics.drawable.Drawable;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//import android.widget.FrameLayout;
//
//import com.google.android.material.animation.AnimationUtils;
//import com.google.android.material.appbar.AppBarLayout;
//import com.google.android.material.appbar.KMCollapsingToolbarLayout;
//import com.google.android.material.appbar.ViewOffsetHelper;
//import com.google.android.material.internal.CollapsingTextHelper;
//import com.google.android.material.internal.DescendantOffsetUtils;
//import com.google.android.material.internal.ThemeEnforcement;
//
//import androidx.annotation.ColorInt;
//import androidx.annotation.DrawableRes;
//import androidx.annotation.IntRange;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.annotation.StyleRes;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.content.ContextCompat;
//import androidx.core.graphics.drawable.DrawableCompat;
//import androidx.core.math.MathUtils;
//import androidx.core.util.ObjectsCompat;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class KMCollapsingToolbarLayout extends FrameLayout {
//    private static final int DEFAULT_SCRIM_ANIMATION_DURATION = 600;
//    private boolean refreshToolbar;
//    private int toolbarId;
//    private Toolbar toolbar;
//    private View toolbarDirectChild;
//    private View dummyView;
//    private int expandedMarginStart;
//    private int expandedMarginTop;
//    private int expandedMarginEnd;
//    private int expandedMarginBottom;
//    private final Rect tmpRect;
//    final CollapsingTextHelper collapsingTextHelper;
//    private boolean collapsingTitleEnabled;
//    private boolean drawCollapsingTitle;
//    private Drawable contentScrim;
//    Drawable statusBarScrim;
//    private int scrimAlpha;
//    private boolean scrimsAreShown;
//    private ValueAnimator scrimAnimator;
//    private long scrimAnimationDuration;
//    private int scrimVisibleHeightTrigger;
//    private AppBarLayout.OnOffsetChangedListener onOffsetChangedListener;
//    int currentOffset;
//    WindowInsetsCompat lastInsets;
//
//    public KMCollapsingToolbarLayout(Context context) {
//        this(context, (AttributeSet)null);
//    }
//
//    public KMCollapsingToolbarLayout(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public KMCollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        this.refreshToolbar = true;
//        this.tmpRect = new Rect();
//        this.scrimVisibleHeightTrigger = -1;
//        this.collapsingTextHelper = new CollapsingTextHelper(this);
//        this.collapsingTextHelper.setTextSizeInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
//        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, styleable.KMCollapsingToolbarLayout, defStyleAttr, style.Widget_Design_CollapsingToolbar, new int[0]);
//        this.collapsingTextHelper.setExpandedTextGravity(a.getInt(styleable.CollapsingToolbarLayout_expandedTitleGravity, 8388691));
//        this.collapsingTextHelper.setCollapsedTextGravity(a.getInt(styleable.CollapsingToolbarLayout_collapsedTitleGravity, 8388627));
//        this.expandedMarginStart = this.expandedMarginTop = this.expandedMarginEnd = this.expandedMarginBottom = a.getDimensionPixelSize(styleable.CollapsingToolbarLayout_expandedTitleMargin, 0);
//        if (a.hasValue(styleable.CollapsingToolbarLayout_expandedTitleMarginStart)) {
//            this.expandedMarginStart = a.getDimensionPixelSize(styleable.CollapsingToolbarLayout_expandedTitleMarginStart, 0);
//        }
//
//        if (a.hasValue(styleable.CollapsingToolbarLayout_expandedTitleMarginEnd)) {
//            this.expandedMarginEnd = a.getDimensionPixelSize(styleable.CollapsingToolbarLayout_expandedTitleMarginEnd, 0);
//        }
//
//        if (a.hasValue(styleable.CollapsingToolbarLayout_expandedTitleMarginTop)) {
//            this.expandedMarginTop = a.getDimensionPixelSize(styleable.CollapsingToolbarLayout_expandedTitleMarginTop, 0);
//        }
//
//        if (a.hasValue(styleable.CollapsingToolbarLayout_expandedTitleMarginBottom)) {
//            this.expandedMarginBottom = a.getDimensionPixelSize(styleable.CollapsingToolbarLayout_expandedTitleMarginBottom, 0);
//        }
//
//        this.collapsingTitleEnabled = a.getBoolean(styleable.CollapsingToolbarLayout_titleEnabled, true);
//        this.setTitle(a.getText(styleable.CollapsingToolbarLayout_title));
//        this.collapsingTextHelper.setExpandedTextAppearance(style.TextAppearance_Design_CollapsingToolbar_Expanded);
//        this.collapsingTextHelper.setCollapsedTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Widget_ActionBar_Title);
//        if (a.hasValue(styleable.CollapsingToolbarLayout_expandedTitleTextAppearance)) {
//            this.collapsingTextHelper.setExpandedTextAppearance(a.getResourceId(styleable.CollapsingToolbarLayout_expandedTitleTextAppearance, 0));
//        }
//
//        if (a.hasValue(styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance)) {
//            this.collapsingTextHelper.setCollapsedTextAppearance(a.getResourceId(styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance, 0));
//        }
//
//        this.scrimVisibleHeightTrigger = a.getDimensionPixelSize(styleable.CollapsingToolbarLayout_scrimVisibleHeightTrigger, -1);
//        this.scrimAnimationDuration = (long)a.getInt(styleable.CollapsingToolbarLayout_scrimAnimationDuration, 600);
//        this.setContentScrim(a.getDrawable(styleable.CollapsingToolbarLayout_contentScrim));
//        this.setStatusBarScrim(a.getDrawable(styleable.CollapsingToolbarLayout_statusBarScrim));
//        this.toolbarId = a.getResourceId(styleable.CollapsingToolbarLayout_toolbarId, -1);
//        a.recycle();
//        this.setWillNotDraw(false);
//        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() {
//            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
//                return KMCollapsingToolbarLayout.this.onWindowInsetChanged(insets);
//            }
//        });
//    }
//
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        ViewParent parent = this.getParent();
//        if (parent instanceof AppBarLayout) {
//            ViewCompat.setFitsSystemWindows(this, ViewCompat.getFitsSystemWindows((View)parent));
//            if (this.onOffsetChangedListener == null) {
//                this.onOffsetChangedListener = new KMCollapsingToolbarLayout.OffsetUpdateListener();
//            }
//
//            ((AppBarLayout)parent).addOnOffsetChangedListener(this.onOffsetChangedListener);
//            ViewCompat.requestApplyInsets(this);
//        }
//
//    }
//
//    protected void onDetachedFromWindow() {
//        ViewParent parent = this.getParent();
//        if (this.onOffsetChangedListener != null && parent instanceof AppBarLayout) {
//            ((AppBarLayout)parent).removeOnOffsetChangedListener(this.onOffsetChangedListener);
//        }
//
//        super.onDetachedFromWindow();
//    }
//
//    WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat insets) {
//        WindowInsetsCompat newInsets = null;
//        if (ViewCompat.getFitsSystemWindows(this)) {
//            newInsets = insets;
//        }
//
//        if (!ObjectsCompat.equals(this.lastInsets, newInsets)) {
//            this.lastInsets = newInsets;
//            this.requestLayout();
//        }
//
//        return insets.consumeSystemWindowInsets();
//    }
//
//    public void draw(Canvas canvas) {
//        super.draw(canvas);
//        this.ensureToolbar();
//        if (this.toolbar == null && this.contentScrim != null && this.scrimAlpha > 0) {
//            this.contentScrim.mutate().setAlpha(this.scrimAlpha);
//            this.contentScrim.draw(canvas);
//        }
//
//        if (this.collapsingTitleEnabled && this.drawCollapsingTitle) {
//            this.collapsingTextHelper.draw(canvas);
//        }
//
//        if (this.statusBarScrim != null && this.scrimAlpha > 0) {
//            int topInset = this.lastInsets != null ? this.lastInsets.getSystemWindowInsetTop() : 0;
//            if (topInset > 0) {
//                this.statusBarScrim.setBounds(0, -this.currentOffset, this.getWidth(), topInset - this.currentOffset);
//                this.statusBarScrim.mutate().setAlpha(this.scrimAlpha);
//                this.statusBarScrim.draw(canvas);
//            }
//        }
//
//    }
//
//    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
//        boolean invalidated = false;
//        if (this.contentScrim != null && this.scrimAlpha > 0 && this.isToolbarChild(child)) {
//            this.contentScrim.mutate().setAlpha(this.scrimAlpha);
//            this.contentScrim.draw(canvas);
//            invalidated = true;
//        }
//
//        return super.drawChild(canvas, child, drawingTime) || invalidated;
//    }
//
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        if (this.contentScrim != null) {
//            this.contentScrim.setBounds(0, 0, w, h);
//        }
//
//    }
//
//    private void ensureToolbar() {
//        if (this.refreshToolbar) {
//            this.toolbar = null;
//            this.toolbarDirectChild = null;
//            if (this.toolbarId != -1) {
//                this.toolbar = (Toolbar)this.findViewById(this.toolbarId);
//                if (this.toolbar != null) {
//                    this.toolbarDirectChild = this.findDirectChild(this.toolbar);
//                }
//            }
//
//            if (this.toolbar == null) {
//                Toolbar toolbar = null;
//                int i = 0;
//
//                for(int count = this.getChildCount(); i < count; ++i) {
//                    View child = this.getChildAt(i);
//                    if (child instanceof Toolbar) {
//                        toolbar = (Toolbar)child;
//                        break;
//                    }
//                }
//
//                this.toolbar = toolbar;
//            }
//
//            this.updateDummyView();
//            this.refreshToolbar = false;
//        }
//    }
//
//    private boolean isToolbarChild(View child) {
//        return this.toolbarDirectChild != null && this.toolbarDirectChild != this ? child == this.toolbarDirectChild : child == this.toolbar;
//    }
//
//    private View findDirectChild(View descendant) {
//        View directChild = descendant;
//
//        for(ViewParent p = descendant.getParent(); p != this && p != null; p = p.getParent()) {
//            if (p instanceof View) {
//                directChild = (View)p;
//            }
//        }
//
//        return directChild;
//    }
//
//    private void updateDummyView() {
//        if (!this.collapsingTitleEnabled && this.dummyView != null) {
//            ViewParent parent = this.dummyView.getParent();
//            if (parent instanceof ViewGroup) {
//                ((ViewGroup)parent).removeView(this.dummyView);
//            }
//        }
//
//        if (this.collapsingTitleEnabled && this.toolbar != null) {
//            if (this.dummyView == null) {
//                this.dummyView = new View(this.getContext());
//            }
//
//            if (this.dummyView.getParent() == null) {
//                this.toolbar.addView(this.dummyView, -1, -1);
//            }
//        }
//
//    }
//
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        this.ensureToolbar();
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int mode = MeasureSpec.getMode(heightMeasureSpec);
//        int topInset = this.lastInsets != null ? this.lastInsets.getSystemWindowInsetTop() : 0;
//        if (mode == 0 && topInset > 0) {
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(this.getMeasuredHeight() + topInset, 1073741824);
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
//
//    }
//
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        int i;
//        int z;
//        if (this.lastInsets != null) {
//            i = this.lastInsets.getSystemWindowInsetTop();
//            z = 0;
//
//            for(int z = this.getChildCount(); z < z; ++z) {
//                View child = this.getChildAt(z);
//                if (!ViewCompat.getFitsSystemWindows(child) && child.getTop() < i) {
//                    ViewCompat.offsetTopAndBottom(child, i);
//                }
//            }
//        }
//
//        if (this.collapsingTitleEnabled && this.dummyView != null) {
//            this.drawCollapsingTitle = ViewCompat.isAttachedToWindow(this.dummyView) && this.dummyView.getVisibility() == 0;
//            if (this.drawCollapsingTitle) {
//                boolean isRtl = ViewCompat.getLayoutDirection(this) == 1;
//                z = this.getMaxOffsetForPinChild((View)(this.toolbarDirectChild != null ? this.toolbarDirectChild : this.toolbar));
//                DescendantOffsetUtils.getDescendantRect(this, this.dummyView, this.tmpRect);
//                this.collapsingTextHelper.setCollapsedBounds(this.tmpRect.left + (isRtl ? this.toolbar.getTitleMarginEnd() : this.toolbar.getTitleMarginStart()), this.tmpRect.top + z + this.toolbar.getTitleMarginTop(), this.tmpRect.right + (isRtl ? this.toolbar.getTitleMarginStart() : this.toolbar.getTitleMarginEnd()), this.tmpRect.bottom + z - this.toolbar.getTitleMarginBottom());
//                this.collapsingTextHelper.setExpandedBounds(isRtl ? this.expandedMarginEnd : this.expandedMarginStart, this.tmpRect.top + this.expandedMarginTop, right - left - (isRtl ? this.expandedMarginStart : this.expandedMarginEnd), bottom - top - this.expandedMarginBottom);
//                this.collapsingTextHelper.recalculate();
//            }
//        }
//
//        i = 0;
//
//        for(z = this.getChildCount(); i < z; ++i) {
//            getViewOffsetHelper(this.getChildAt(i)).onViewLayout();
//        }
//
//        if (this.toolbar != null) {
//            if (this.collapsingTitleEnabled && TextUtils.isEmpty(this.collapsingTextHelper.getText())) {
//                this.setTitle(this.toolbar.getTitle());
//            }
//
//            if (this.toolbarDirectChild != null && this.toolbarDirectChild != this) {
//                this.setMinimumHeight(getHeightWithMargins(this.toolbarDirectChild));
//            } else {
//                this.setMinimumHeight(getHeightWithMargins(this.toolbar));
//            }
//        }
//
//        this.updateScrimVisibility();
//    }
//
//    private static int getHeightWithMargins(@NonNull View view) {
//        android.view.ViewGroup.LayoutParams lp = view.getLayoutParams();
//        if (lp instanceof MarginLayoutParams) {
//            MarginLayoutParams mlp = (MarginLayoutParams)lp;
//            return view.getHeight() + mlp.topMargin + mlp.bottomMargin;
//        } else {
//            return view.getHeight();
//        }
//    }
//
//    static ViewOffsetHelper getViewOffsetHelper(View view) {
//        ViewOffsetHelper offsetHelper = (ViewOffsetHelper)view.getTag(id.view_offset_helper);
//        if (offsetHelper == null) {
//            offsetHelper = new ViewOffsetHelper(view);
//            view.setTag(id.view_offset_helper, offsetHelper);
//        }
//
//        return offsetHelper;
//    }
//
//    public void setTitle(@Nullable CharSequence title) {
//        this.collapsingTextHelper.setText(title);
//        this.updateContentDescriptionFromTitle();
//    }
//
//    @Nullable
//    public CharSequence getTitle() {
//        return this.collapsingTitleEnabled ? this.collapsingTextHelper.getText() : null;
//    }
//
//    public void setTitleEnabled(boolean enabled) {
//        if (enabled != this.collapsingTitleEnabled) {
//            this.collapsingTitleEnabled = enabled;
//            this.updateContentDescriptionFromTitle();
//            this.updateDummyView();
//            this.requestLayout();
//        }
//
//    }
//
//    public boolean isTitleEnabled() {
//        return this.collapsingTitleEnabled;
//    }
//
//    public void setScrimsShown(boolean shown) {
//        this.setScrimsShown(shown, ViewCompat.isLaidOut(this) && !this.isInEditMode());
//    }
//
//    public void setScrimsShown(boolean shown, boolean animate) {
//        if (this.scrimsAreShown != shown) {
//            if (animate) {
//                this.animateScrim(shown ? 255 : 0);
//            } else {
//                this.setScrimAlpha(shown ? 255 : 0);
//            }
//
//            this.scrimsAreShown = shown;
//        }
//
//    }
//
//    private void animateScrim(int targetAlpha) {
//        this.ensureToolbar();
//        if (this.scrimAnimator == null) {
//            this.scrimAnimator = new ValueAnimator();
//            this.scrimAnimator.setDuration(this.scrimAnimationDuration);
//            this.scrimAnimator.setInterpolator(targetAlpha > this.scrimAlpha ? AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR : AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
//            this.scrimAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                public void onAnimationUpdate(ValueAnimator animator) {
//                    KMCollapsingToolbarLayout.this.setScrimAlpha((Integer)animator.getAnimatedValue());
//                }
//            });
//        } else if (this.scrimAnimator.isRunning()) {
//            this.scrimAnimator.cancel();
//        }
//
//        this.scrimAnimator.setIntValues(new int[]{this.scrimAlpha, targetAlpha});
//        this.scrimAnimator.start();
//    }
//
//    void setScrimAlpha(int alpha) {
//        if (alpha != this.scrimAlpha) {
//            Drawable contentScrim = this.contentScrim;
//            if (contentScrim != null && this.toolbar != null) {
//                ViewCompat.postInvalidateOnAnimation(this.toolbar);
//            }
//
//            this.scrimAlpha = alpha;
//            ViewCompat.postInvalidateOnAnimation(this);
//        }
//
//    }
//
//    int getScrimAlpha() {
//        return this.scrimAlpha;
//    }
//
//    public void setContentScrim(@Nullable Drawable drawable) {
//        if (this.contentScrim != drawable) {
//            if (this.contentScrim != null) {
//                this.contentScrim.setCallback((Drawable.Callback)null);
//            }
//
//            this.contentScrim = drawable != null ? drawable.mutate() : null;
//            if (this.contentScrim != null) {
//                this.contentScrim.setBounds(0, 0, this.getWidth(), this.getHeight());
//                this.contentScrim.setCallback(this);
//                this.contentScrim.setAlpha(this.scrimAlpha);
//            }
//
//            ViewCompat.postInvalidateOnAnimation(this);
//        }
//
//    }
//
//    public void setContentScrimColor(@ColorInt int color) {
//        this.setContentScrim(new ColorDrawable(color));
//    }
//
//    public void setContentScrimResource(@DrawableRes int resId) {
//        this.setContentScrim(ContextCompat.getDrawable(this.getContext(), resId));
//    }
//
//    @Nullable
//    public Drawable getContentScrim() {
//        return this.contentScrim;
//    }
//
//    public void setStatusBarScrim(@Nullable Drawable drawable) {
//        if (this.statusBarScrim != drawable) {
//            if (this.statusBarScrim != null) {
//                this.statusBarScrim.setCallback((Drawable.Callback)null);
//            }
//
//            this.statusBarScrim = drawable != null ? drawable.mutate() : null;
//            if (this.statusBarScrim != null) {
//                if (this.statusBarScrim.isStateful()) {
//                    this.statusBarScrim.setState(this.getDrawableState());
//                }
//
//                DrawableCompat.setLayoutDirection(this.statusBarScrim, ViewCompat.getLayoutDirection(this));
//                this.statusBarScrim.setVisible(this.getVisibility() == 0, false);
//                this.statusBarScrim.setCallback(this);
//                this.statusBarScrim.setAlpha(this.scrimAlpha);
//            }
//
//            ViewCompat.postInvalidateOnAnimation(this);
//        }
//
//    }
//
//    protected void drawableStateChanged() {
//        super.drawableStateChanged();
//        int[] state = this.getDrawableState();
//        boolean changed = false;
//        Drawable d = this.statusBarScrim;
//        if (d != null && d.isStateful()) {
//            changed |= d.setState(state);
//        }
//
//        d = this.contentScrim;
//        if (d != null && d.isStateful()) {
//            changed |= d.setState(state);
//        }
//
//        if (this.collapsingTextHelper != null) {
//            changed |= this.collapsingTextHelper.setState(state);
//        }
//
//        if (changed) {
//            this.invalidate();
//        }
//
//    }
//
//    protected boolean verifyDrawable(Drawable who) {
//        return super.verifyDrawable(who) || who == this.contentScrim || who == this.statusBarScrim;
//    }
//
//    public void setVisibility(int visibility) {
//        super.setVisibility(visibility);
//        boolean visible = visibility == 0;
//        if (this.statusBarScrim != null && this.statusBarScrim.isVisible() != visible) {
//            this.statusBarScrim.setVisible(visible, false);
//        }
//
//        if (this.contentScrim != null && this.contentScrim.isVisible() != visible) {
//            this.contentScrim.setVisible(visible, false);
//        }
//
//    }
//
//    public void setStatusBarScrimColor(@ColorInt int color) {
//        this.setStatusBarScrim(new ColorDrawable(color));
//    }
//
//    public void setStatusBarScrimResource(@DrawableRes int resId) {
//        this.setStatusBarScrim(ContextCompat.getDrawable(this.getContext(), resId));
//    }
//
//    @Nullable
//    public Drawable getStatusBarScrim() {
//        return this.statusBarScrim;
//    }
//
//    public void setCollapsedTitleTextAppearance(@StyleRes int resId) {
//        this.collapsingTextHelper.setCollapsedTextAppearance(resId);
//    }
//
//    public void setCollapsedTitleTextColor(@ColorInt int color) {
//        this.setCollapsedTitleTextColor(ColorStateList.valueOf(color));
//    }
//
//    public void setCollapsedTitleTextColor(@NonNull ColorStateList colors) {
//        this.collapsingTextHelper.setCollapsedTextColor(colors);
//    }
//
//    public void setCollapsedTitleGravity(int gravity) {
//        this.collapsingTextHelper.setCollapsedTextGravity(gravity);
//    }
//
//    public int getCollapsedTitleGravity() {
//        return this.collapsingTextHelper.getCollapsedTextGravity();
//    }
//
//    public void setExpandedTitleTextAppearance(@StyleRes int resId) {
//        this.collapsingTextHelper.setExpandedTextAppearance(resId);
//    }
//
//    public void setExpandedTitleColor(@ColorInt int color) {
//        this.setExpandedTitleTextColor(ColorStateList.valueOf(color));
//    }
//
//    public void setExpandedTitleTextColor(@NonNull ColorStateList colors) {
//        this.collapsingTextHelper.setExpandedTextColor(colors);
//    }
//
//    public void setExpandedTitleGravity(int gravity) {
//        this.collapsingTextHelper.setExpandedTextGravity(gravity);
//    }
//
//    public int getExpandedTitleGravity() {
//        return this.collapsingTextHelper.getExpandedTextGravity();
//    }
//
//    public void setCollapsedTitleTypeface(@Nullable Typeface typeface) {
//        this.collapsingTextHelper.setCollapsedTypeface(typeface);
//    }
//
//    @NonNull
//    public Typeface getCollapsedTitleTypeface() {
//        return this.collapsingTextHelper.getCollapsedTypeface();
//    }
//
//    public void setExpandedTitleTypeface(@Nullable Typeface typeface) {
//        this.collapsingTextHelper.setExpandedTypeface(typeface);
//    }
//
//    @NonNull
//    public Typeface getExpandedTitleTypeface() {
//        return this.collapsingTextHelper.getExpandedTypeface();
//    }
//
//    public void setExpandedTitleMargin(int start, int top, int end, int bottom) {
//        this.expandedMarginStart = start;
//        this.expandedMarginTop = top;
//        this.expandedMarginEnd = end;
//        this.expandedMarginBottom = bottom;
//        this.requestLayout();
//    }
//
//    public int getExpandedTitleMarginStart() {
//        return this.expandedMarginStart;
//    }
//
//    public void setExpandedTitleMarginStart(int margin) {
//        this.expandedMarginStart = margin;
//        this.requestLayout();
//    }
//
//    public int getExpandedTitleMarginTop() {
//        return this.expandedMarginTop;
//    }
//
//    public void setExpandedTitleMarginTop(int margin) {
//        this.expandedMarginTop = margin;
//        this.requestLayout();
//    }
//
//    public int getExpandedTitleMarginEnd() {
//        return this.expandedMarginEnd;
//    }
//
//    public void setExpandedTitleMarginEnd(int margin) {
//        this.expandedMarginEnd = margin;
//        this.requestLayout();
//    }
//
//    public int getExpandedTitleMarginBottom() {
//        return this.expandedMarginBottom;
//    }
//
//    public void setExpandedTitleMarginBottom(int margin) {
//        this.expandedMarginBottom = margin;
//        this.requestLayout();
//    }
//
//    public void setScrimVisibleHeightTrigger(@IntRange(from = 0L) int height) {
//        if (this.scrimVisibleHeightTrigger != height) {
//            this.scrimVisibleHeightTrigger = height;
//            this.updateScrimVisibility();
//        }
//
//    }
//
//    public int getScrimVisibleHeightTrigger() {
//        if (this.scrimVisibleHeightTrigger >= 0) {
//            return this.scrimVisibleHeightTrigger;
//        } else {
//            int insetTop = this.lastInsets != null ? this.lastInsets.getSystemWindowInsetTop() : 0;
//            int minHeight = ViewCompat.getMinimumHeight(this);
//            return minHeight > 0 ? Math.min(minHeight * 2 + insetTop, this.getHeight()) : this.getHeight() / 3;
//        }
//    }
//
//    public void setScrimAnimationDuration(@IntRange(from = 0L) long duration) {
//        this.scrimAnimationDuration = duration;
//    }
//
//    public long getScrimAnimationDuration() {
//        return this.scrimAnimationDuration;
//    }
//
//    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
//        return p instanceof KMCollapsingToolbarLayout.LayoutParams;
//    }
//
//    protected KMCollapsingToolbarLayout.LayoutParams generateDefaultLayoutParams() {
//        return new KMCollapsingToolbarLayout.LayoutParams(-1, -1);
//    }
//
//    public android.widget.FrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
//        return new KMCollapsingToolbarLayout.LayoutParams(this.getContext(), attrs);
//    }
//
//    protected android.widget.FrameLayout.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams p) {
//        return new KMCollapsingToolbarLayout.LayoutParams(p);
//    }
//
//    final void updateScrimVisibility() {
//        if (this.contentScrim != null || this.statusBarScrim != null) {
//            this.setScrimsShown(this.getHeight() + this.currentOffset < this.getScrimVisibleHeightTrigger());
//        }
//
//    }
//
//    final int getMaxOffsetForPinChild(View child) {
//        ViewOffsetHelper offsetHelper = getViewOffsetHelper(child);
//        KMCollapsingToolbarLayout.LayoutParams lp = (KMCollapsingToolbarLayout.LayoutParams)child.getLayoutParams();
//        return this.getHeight() - offsetHelper.getLayoutTop() - child.getHeight() - lp.bottomMargin;
//    }
//
//    private void updateContentDescriptionFromTitle() {
//        this.setContentDescription(this.getTitle());
//    }
//
//    private class OffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {
//        OffsetUpdateListener() {
//        }
//
//        public void onOffsetChanged(AppBarLayout layout, int verticalOffset) {
//            KMCollapsingToolbarLayout.this.currentOffset = verticalOffset;
//            int insetTop = KMCollapsingToolbarLayout.this.lastInsets != null ? KMCollapsingToolbarLayout.this.lastInsets.getSystemWindowInsetTop() : 0;
//            int expandRange = 0;
//
//            for(int z = KMCollapsingToolbarLayout.this.getChildCount(); expandRange < z; ++expandRange) {
//                View child = KMCollapsingToolbarLayout.this.getChildAt(expandRange);
//                KMCollapsingToolbarLayout.LayoutParams lp = (KMCollapsingToolbarLayout.LayoutParams)child.getLayoutParams();
//                ViewOffsetHelper offsetHelper = KMCollapsingToolbarLayout.getViewOffsetHelper(child);
//                switch(lp.collapseMode) {
//                    case 1:
//                        offsetHelper.setTopAndBottomOffset(MathUtils.clamp(-verticalOffset, 0, KMCollapsingToolbarLayout.this.getMaxOffsetForPinChild(child)));
//                        break;
//                    case 2:
//                        offsetHelper.setTopAndBottomOffset(Math.round((float)(-verticalOffset) * lp.parallaxMult));
//                }
//            }
//
//            KMCollapsingToolbarLayout.this.updateScrimVisibility();
//            if (KMCollapsingToolbarLayout.this.statusBarScrim != null && insetTop > 0) {
//                ViewCompat.postInvalidateOnAnimation(KMCollapsingToolbarLayout.this);
//            }
//
//            expandRange = KMCollapsingToolbarLayout.this.getHeight() - ViewCompat.getMinimumHeight(KMCollapsingToolbarLayout.this) - insetTop;
//            KMCollapsingToolbarLayout.this.collapsingTextHelper.setExpansionFraction((float)Math.abs(verticalOffset) / (float)expandRange);
//        }
//    }
//
//    public static class LayoutParams extends android.widget.FrameLayout.LayoutParams {
//        private static final float DEFAULT_PARALLAX_MULTIPLIER = 0.5F;
//        public static final int COLLAPSE_MODE_OFF = 0;
//        public static final int COLLAPSE_MODE_PIN = 1;
//        public static final int COLLAPSE_MODE_PARALLAX = 2;
//        int collapseMode = 0;
//        float parallaxMult = 0.5F;
//
//        public LayoutParams(Context c, AttributeSet attrs) {
//            super(c, attrs);
//            TypedArray a = c.obtainStyledAttributes(attrs, styleable.CollapsingToolbarLayout_Layout);
//            this.collapseMode = a.getInt(styleable.CollapsingToolbarLayout_Layout_layout_collapseMode, 0);
//            this.setParallaxMultiplier(a.getFloat(styleable.CollapsingToolbarLayout_Layout_layout_collapseParallaxMultiplier, 0.5F));
//            a.recycle();
//        }
//
//        public LayoutParams(int width, int height) {
//            super(width, height);
//        }
//
//        public LayoutParams(int width, int height, int gravity) {
//            super(width, height, gravity);
//        }
//
//        public LayoutParams(android.view.ViewGroup.LayoutParams p) {
//            super(p);
//        }
//
//        public LayoutParams(MarginLayoutParams source) {
//            super(source);
//        }
//
//        @RequiresApi(19)
//        public LayoutParams(android.widget.FrameLayout.LayoutParams source) {
//            super(source);
//        }
//
//        public void setCollapseMode(int collapseMode) {
//            this.collapseMode = collapseMode;
//        }
//
//        public int getCollapseMode() {
//            return this.collapseMode;
//        }
//
//        public void setParallaxMultiplier(float multiplier) {
//            this.parallaxMult = multiplier;
//        }
//
//        public float getParallaxMultiplier() {
//            return this.parallaxMult;
//        }
//    }
//}
