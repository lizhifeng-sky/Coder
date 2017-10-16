package lzf.coder.help;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by Administrator on 2017/10/16 0016.
 */
public abstract class FoldHelper {
    public int fromDegree = 60;
    public int toDegree = 55;
    public long duration = 200;
    public TimeInterpolator interpolator = new AccelerateInterpolator();
    public Matrix matrix;
    public int degree = 0;
    public int lastDegree = 0;
    public int left, top, right, bottom;
    public UpdateListener updateListener;
    public Bitmap bitmap;
    public Paint paint;

    public FoldHelper() {
        paint = new Paint();
        matrix = new Matrix();
    }

    public FoldHelper setDegree(int from, int to) {
        this.fromDegree = from;
        this.toDegree = to;
        return this;
    }

    public FoldHelper setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public FoldHelper setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        return this;
    }

    public FoldHelper setInterpolator(TimeInterpolator timeInterpolator) {
        this.interpolator = timeInterpolator;
        return this;
    }

    public FoldHelper setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
        return this;
    }

    public FoldHelper setLocation(int mLeft, int mTop, int mRight, int mBottom) {
        this.top = mTop;
        this.left = mLeft;
        this.right = mRight;
        this.bottom = mBottom;
        return this;
    }

    public abstract void startAnimator();

    public abstract void onDraw(Canvas canvas);
}
