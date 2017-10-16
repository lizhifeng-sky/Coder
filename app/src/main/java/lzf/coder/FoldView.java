package lzf.coder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import lzf.coder.help.BottomFoldHelper;
import lzf.coder.help.FoldHelper;
import lzf.coder.help.LeftFoldHelper;
import lzf.coder.help.RightFoldHelper;
import lzf.coder.help.TopFoldHelper;
import lzf.coder.help.UpdateListener;

/**
 * Created by Administrator on 2017/10/13 0013.
 */
public class FoldView extends View implements View.OnClickListener, UpdateListener {
    private FoldHelper foldHelper;
    private int left, top, right, bottom;
    private Bitmap bitmap;

    public FoldView(Context context) {
        this(context, null);
    }

    public FoldView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoldView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        left = 100;
        top = 100;
        right = bitmap.getWidth() + 100;
        bottom = bitmap.getHeight() + 100;
        this.setOnClickListener(this);
        /*
        * 默认的
        * */
        foldHelper = new LeftFoldHelper();
        foldHelper.setDegree(60, 55)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setUpdateListener(this)
                .setLocation(left, top, right, bottom)
                .setBitmap(bitmap);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bitmap.getWidth()+200, bitmap.getHeight()+200);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        foldHelper.onDraw(canvas);
    }

    @Override
    public void onClick(View view) {
        foldHelper.startAnimator();
    }

    @Override
    public void update() {
        postInvalidate();
    }

    public void left() {
        foldHelper = new LeftFoldHelper();
        foldHelper.setDegree(60, 55)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setUpdateListener(this)
                .setLocation(left, top, right, bottom)
                .setBitmap(bitmap);
    }

    public void right() {
        foldHelper = new RightFoldHelper();
        foldHelper.setDegree(60, 55)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setUpdateListener(this)
                .setLocation(left, top, right, bottom)
                .setBitmap(bitmap);
    }

    public void top() {
        foldHelper = new TopFoldHelper();
        foldHelper.setDegree(60, 55)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setUpdateListener(this)
                .setLocation(left, top, right, bottom)
                .setBitmap(bitmap);
    }

    public void bottom() {
        foldHelper = new BottomFoldHelper();
        foldHelper.setDegree(60, 55)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setUpdateListener(this)
                .setLocation(left, top, right, bottom)
                .setBitmap(bitmap);
    }
}
