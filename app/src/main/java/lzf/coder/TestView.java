package lzf.coder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/10/13 0013.
 */
public class TestView extends View implements View.OnClickListener{
    private Bitmap bitmap;
    private Matrix matrix;
    private float[] pointsSrc ;
    private float[] pointsDst ;
    private float left;
    private float top;
    private float right;
    private float bottom;
    private Paint paint;
    public TestView(Context context) {
        this(context,null);
    }

    public TestView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        matrix=new Matrix();
        paint=new Paint();
        left=100;
        top=100;
        right=bitmap.getWidth()+100;
        bottom=bitmap.getHeight()+100;
        this.setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
//        canvas.concat(matrix);
        canvas.clipRect(
                left,
                top,
                left/2+right/2,
                bottom);
        canvas.drawBitmap(bitmap, 100,100, paint);
        canvas.restore();

        canvas.save();
        canvas.concat(matrix);
        canvas.clipRect(
                left/2+right/2,
                top,
                right,
                bottom);
        canvas.drawBitmap(bitmap, 100,100, paint);
        canvas.restore();
    }

    @Override
    public void onClick(View view) {
        pointsSrc= new float[]{
                left, top,
                right, top,
                left, bottom,
                right, bottom};
        pointsDst=new float[]{
                left+10, top+20,
                right-10, top-20,
                left+10, bottom-20,
                right-10, bottom+20};
        matrix.reset();
        matrix.setPolyToPoly(pointsSrc, 0, pointsDst, 0, 4);
        postInvalidate();
    }
}
