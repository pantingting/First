package cn.campsg.com.hello.JavaClass;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 八月 on 2017/8/4.
 */

public class FilterPopUpWindow extends PopupWindow {
    private ViewGroup mContentRootView;
    private View mShowView;
    private int mShowViewHeight;

    public FilterPopUpWindow() {
        super();
    }

    public FilterPopUpWindow(int width, int height) {
        super(width, height);
    }

    public FilterPopUpWindow(View contentView) {
        super(contentView);
    }

    public void setContentHeight(int height) {
        mShowViewHeight = height;
    }

    public void setCustomContentView(View view) {
        mShowView = view;
        setContentView(createPopupWindowContentView(view));
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        showViewWithVerticalAnimator();
    }

    @Override
    public void dismiss() {
        hideListViewWithVerticalAnimator();
    }

    public void dismissReally() {
        if (isShowing()) {
            super.dismiss();
        }
    }

    protected View createPopupWindowContentView(View child) {
        FrameLayout rootView = new FrameLayout(child.getContext());
        final int matchParent = ViewGroup.LayoutParams.MATCH_PARENT;
        rootView.setLayoutParams(new ViewGroup.LayoutParams(matchParent,
                matchParent));
        rootView.addView(child);

        rootView.setOnTouchListener(new View.OnTouchListener() {

            boolean mbPressed = false;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    mbPressed = true;
                } else if (action == MotionEvent.ACTION_UP) {
                    if (mbPressed) {
                        dismiss();
                    }
                    mbPressed = false;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    mbPressed = false;
                }
                return mbPressed;
            }
        });
        mContentRootView = rootView;
        return mContentRootView;
    }

    @SuppressLint("NewApi")
    private void showViewWithVerticalAnimator() {
        View view = mShowView;
        if (view == null) {
            return;
        }
        final int height = mShowViewHeight;
        view.setTranslationY(-height);
        final ObjectAnimator bgAnimator = new ObjectAnimator();
        final PropertyValuesHolder bgoffset = PropertyValuesHolder.ofFloat(
                "TranslationY", -height, 0f);
        final PropertyValuesHolder bgAlpha = PropertyValuesHolder.ofFloat(
                "alpha", 0.0f, 1.0f);
        bgAnimator.setTarget(view);
        bgAnimator.setValues(bgoffset, bgAlpha);
        bgAnimator.setInterpolator(new DecelerateInterpolator(1.5f));
        bgAnimator.setDuration(400);
        bgAnimator.addListener(new DefaultAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                mContentRootView.setBackgroundColor(0x0E000000);
            }
        });
        bgAnimator.start();
    }

    @SuppressLint("NewApi")
    private void hideListViewWithVerticalAnimator() {
        View view = mShowView;
        if (view == null) {
            return;
        }
        final int height = mShowViewHeight;
        final ObjectAnimator bgAnimator = new ObjectAnimator();
        final PropertyValuesHolder bgoffset = PropertyValuesHolder.ofFloat(
                "TranslationY", 0f, -height);
        final PropertyValuesHolder bgAlpha = PropertyValuesHolder.ofFloat(
                "alpha", 1.0f, 0.5f);
        bgAnimator.setTarget(view);
        bgAnimator.setValues(bgoffset, bgAlpha);
        bgAnimator.setInterpolator(new AccelerateInterpolator(1.5f));
        bgAnimator.setDuration(400);
        bgAnimator.start();
        bgAnimator.addListener(new DefaultAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                dismissReally();
            }
        });
    }

    public void setAllowScrollingAnchorParentEx(boolean enabled) {
        Class<?> clazz = getClass().getSuperclass();
        if (clazz != null) {
            try {
                Method methodSetAllowScrollingAnchorParent = clazz
                        .getDeclaredMethod("setAllowScrollingAnchorParent",
                                new Class<?>[] { boolean.class });
                methodSetAllowScrollingAnchorParent.setAccessible(true);
                methodSetAllowScrollingAnchorParent.invoke(this,
                        new Object[] { enabled });
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("NewApi")
    public static class DefaultAnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }

        @Override
        public void onAnimationStart(Animator animator) {

        }
    }
}
