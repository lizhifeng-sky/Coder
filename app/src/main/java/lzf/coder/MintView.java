package lzf.coder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/10/13 0013.
 */
public class MintView extends View {
    private Paint kgPaint;//kg 数字显示 及 绿色的线
    private Paint scalePaint;//刻度
    private Paint numberPaint;//刻度下方的数字
    private Rect targetRect;
    private String text = "111";
    private int defaultValue = 50;
    private int max = 200;
    private int margin = 15;
    private int height = 50;
    private Scroller scroller;
    private int lastScrollX;
    private int distanceXCount=0;
    public MintView(Context context) {
        this(context, null);
    }

    public MintView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        kgPaint = new Paint();
        kgPaint.setStrokeWidth(4);
        kgPaint.setTextSize(36);
        kgPaint.setColor(Color.GREEN);

        numberPaint = new Paint();
        numberPaint.setStrokeWidth(3);
        numberPaint.setColor(Color.BLACK);

        scalePaint = new Paint();
        scalePaint.setStrokeWidth(2);
        scalePaint.setColor(Color.GRAY);
        scalePaint.setTextSize(36);

        targetRect = new Rect(50, 50, 1000, 200);
        scroller=new Scroller(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画重量显示 kg 绿线
        targetRect.right = getMeasuredWidth() - 50+distanceXCount;

        Paint.FontMetricsInt fontMetrics = kgPaint.getFontMetricsInt();
        int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        kgPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text,  getMeasuredWidth() / 2+distanceXCount, baseline, kgPaint);
        canvas.drawText("kg", kgPaint.measureText(text)  + getMeasuredWidth() / 2+distanceXCount, baseline, kgPaint);

        int left = getMeasuredWidth() / 2;
        canvas.drawLine(left+distanceXCount+2, 250, left+distanceXCount+2, 450, kgPaint);
        //画刻度  width=32个刻度
        //横线
        canvas.drawLine(0, 250, getMeasuredWidth()+distanceXCount, 250, scalePaint);
        //竖线
        for (int i = 0; i < max; i++) {
            if (i != 0 && i != max) {
                if (i % 10 == 0) {
                    //整值
                    canvas.drawLine(i * margin, 250, i * margin, 250 + height, scalePaint);
                    //整值文字
                    canvas.drawText(String.valueOf(i),
                            i * margin - scalePaint.measureText(String.valueOf(i)) / 2,
                            250 + height + 30, scalePaint);
                } else {
                    canvas.drawLine(i * margin, 250, i * margin, 250 + height / 2, scalePaint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (scroller!=null&&!scroller.isFinished()){
                    scroller.abortAnimation();
                }
                lastScrollX= (int) event.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                int distanceX= (int) (lastScrollX-event.getX());
                distanceXCount+=distanceX;
                smoothScrollBy(distanceX,0);
                lastScrollX= (int) event.getX();
                postInvalidate();
                return true;
            case MotionEvent.ACTION_UP:
                return true;
        }
        return super.onTouchEvent(event);
    }

    public void smoothScrollBy(int dx, int dy){
        scroller.startScroll(scroller.getFinalX(), scroller.getFinalY(), dx, dy);
    }

    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(), 0);
            postInvalidate();
        }
        super.computeScroll();
    }
}
