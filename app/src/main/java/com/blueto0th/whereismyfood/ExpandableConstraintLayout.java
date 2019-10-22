package com.blueto0th.whereismyfood;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

/**
 *  Important
 *      Expand horizontal need layout_width set to wrap_content or a specific number
 *      Expand vertical need layout_height set to wrap_content or a specific number
 */

public class ExpandableConstraintLayout extends ConstraintLayout {

    private enum State {IDLE, EXPANDING, COLLAPSING}

    private ExpandHandler handler = WIPE_VERTICAL;
    private boolean isExpanded = true;
    private State state = State.IDLE;
    private long duration = 300;
    private float expansion = 1f;
    private float childMovementPercent = 0f;
    private float minimumSpace = 0f;
    private ValueAnimator valueAnimator;
    private TimeInterpolator timeInterpolator = new FastOutSlowInInterpolator();

    public ExpandableConstraintLayout(Context context) {
        super(context);
    }
    public ExpandableConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ExpandableConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setInstantClose() {
        isExpanded = false;
        expansion = 0;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setMinimumSpace(float percent) {
        this.minimumSpace = percent;
    }

    public void setExpandHandler(ExpandHandler handler) {
        this.handler = handler;
    }

    public void setTimeInterpolator(TimeInterpolator timeInterpolator) {
        this.timeInterpolator = timeInterpolator;
    }

    public void setChildMovementPercent(float percent) {
        this.childMovementPercent = percent;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setVisibility(expansion == 0 ? View.INVISIBLE : View.VISIBLE);
        handler.update(this);
    }

    public void toggle() {
        if (isExpanded) {
            collapse();
        } else {
            expand();
        }
    }

    public void expand() {
        isExpanded = true;
        toggleExpansion(1);
    }

    public void collapse() {
        isExpanded = false;
        toggleExpansion(minimumSpace);
    }

    private void setExpansion(float value) {
        if (expansion == value) return;
        expansion = value;
        requestLayout();
    }

    private void toggleExpansion(final float newExpansion) {
        if (expansion == newExpansion) return;
        if (newExpansion == 1 && state == State.EXPANDING) return;
        if (newExpansion == 0 && state == State.COLLAPSING) return;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        valueAnimator = ValueAnimator.ofFloat(expansion, newExpansion);
        valueAnimator.setInterpolator(timeInterpolator);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setExpansion((float) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    private interface ExpandHandler {
        void update(ExpandableConstraintLayout layout);
    }

    public static ExpandHandler WIPE_HORIZONTAL = new ExpandHandler() {
        @Override
        public void update(ExpandableConstraintLayout layout) {
            int width = layout.getMeasuredWidth();
            int widthDelta = width - Math.round(width * layout.expansion);
            int count = layout.getChildCount();
            for (int i = 0; i < count; i++) {
                layout.getChildAt(i).setTranslationX(-widthDelta * layout.childMovementPercent);
            }
            layout.setMeasuredDimension(width - widthDelta, layout.getMeasuredHeight());
        }
    };

    public static ExpandHandler WIPE_VERTICAL = new ExpandHandler() {
        @Override
        public void update(ExpandableConstraintLayout layout) {
            int height = layout.getMeasuredHeight();
            int heightDelta = height - Math.round(height * layout.expansion);
            int count = layout.getChildCount();
            for (int i = 0; i < count; i++) {
                layout.getChildAt(i).setTranslationY(-heightDelta * layout.childMovementPercent);
            }
            layout.setMeasuredDimension(layout.getMeasuredWidth(), height - heightDelta);
        }
    };
}
