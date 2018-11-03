package ir.alirezabdn.wp7progress;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.LinearLayout;

class Indicator extends View {

    private ObjectAnimator objectAnimator;
    private int color;

    public Indicator(Context context, int indicatorHeight, int color, int radius) {
        super(context);
        this.color = color;
        initialize(indicatorHeight, radius);
    }

    private void initialize(int indicatorHeight, int radius) {
        this.setBackground(getCube(radius));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(px2dp(indicatorHeight), px2dp(indicatorHeight));
        layoutParams.rightMargin = px2dp((int) (1.7f * indicatorHeight));
        this.setLayoutParams(layoutParams);
        startAnim(0, 0);
        removeAnim();
    }

    private GradientDrawable getCube(int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setCornerRadius(px2dp(radius));
        return drawable;
    }

    private int px2dp(int px) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (px * scale + 0.5f);
    }

    public void startAnim(long animationDuration, long delay) {
        objectAnimator = ObjectAnimator.ofFloat(this, "translationX", +1000, -1000);
        objectAnimator.setInterpolator(new CubicInterpolator());
        objectAnimator.setDuration(animationDuration);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setStartDelay(delay);
        objectAnimator.start();
    }

    public void removeAnim() {
        objectAnimator.removeAllListeners();
        objectAnimator.cancel();
        objectAnimator.end();
    }

    private class CubicInterpolator implements android.view.animation.Interpolator {
        @Override
        public float getInterpolation(float input) {
            if (input > 0.3f && input < 0.70f)
                return (float) ((-(input - 0.5) / 6) + 0.5f);
            return (float) ((-4) * Math.pow(input - 0.5, 3) + 0.5);
        }
    }

}
