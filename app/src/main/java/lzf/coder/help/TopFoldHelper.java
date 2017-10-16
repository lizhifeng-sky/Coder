package lzf.coder.help;

import android.animation.ValueAnimator;
import android.graphics.Canvas;

/**
 * Created by Administrator on 2017/10/16 0016.
 */
public class TopFoldHelper extends FoldHelper {
    @Override
    public void startAnimator() {
        ValueAnimator animator = ValueAnimator.ofInt(fromDegree, toDegree);
        animator.setInterpolator(interpolator);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                degree = (int) valueAnimator.getAnimatedValue();
                if (degree != lastDegree) {
                    lastDegree = degree;
                    float distance = (float) ((bottom - top) * (1 - 1 / (2 * Math.cos(Math.toRadians(degree)))));
                    float[] pointsSrc = new float[]{
                            left, top,
                            right, top,
                            left, bottom,
                            right, bottom};
                    float[] pointsDst = new float[]{
                            left - distance, top + distance,
                            right + distance, top + distance,
                            left + distance, bottom - distance,
                            right - distance, bottom - distance};
                    matrix.reset();
                    matrix.setPolyToPoly(pointsSrc, 0, pointsDst, 0, 4);
                    if (updateListener!=null){
                        updateListener.update();
                    }
                }
            }
        });
        animator.start();
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.save();
        canvas.concat(matrix);
        canvas.clipRect(
                left,
                top,
                right,
                top/2+bottom/2);
        canvas.drawBitmap(bitmap, 100,100, paint);
        canvas.restore();

        canvas.save();
        canvas.clipRect(
                left,
                top/2+bottom/2,
                right,
                bottom
        );
        canvas.drawBitmap(bitmap, 100,100, paint);
        canvas.restore();
    }
}
