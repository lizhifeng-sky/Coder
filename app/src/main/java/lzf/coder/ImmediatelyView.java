package lzf.coder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/13 0013.
 */
public class ImmediatelyView extends View implements View.OnClickListener{
    private Paint startNumPaint;//不上下滑动的数字
    private Paint endNumPaint;//上下滑动的数字
    private Paint nextNumPaint;//即将进入的数字
    private int currentNum=339;

    private boolean isFavor=false;
    private String startNum=String.valueOf(currentNum);
    private String endNum="";
    private String nextNum="";
    private boolean isAnimatorStart=false;
    private int endNumY=50;
    private int nextNumY=0;
    public ImmediatelyView(Context context) {
        this(context,null);
    }

    public ImmediatelyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ImmediatelyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        startNumPaint=new Paint();
        startNumPaint.setStrokeWidth(2);
        startNumPaint.setTextSize(48);

        endNumPaint=new Paint();
        endNumPaint.setStrokeWidth(2);
        endNumPaint.setTextSize(48);

        nextNumPaint=new Paint();
        nextNumPaint.setStrokeWidth(2);
        nextNumPaint.setTextSize(48);
        this.setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画开始数字
        float offsetStart=0;
        if (startNum!=null) {
            canvas.drawText(startNum,50,100,startNumPaint);
            offsetStart=startNumPaint.measureText(startNum);
        }
        /*
        *paint.measureText(text) paint写上text的时候,像素为多少
        * */
        if (endNum!=null&&isAnimatorStart) {
            canvas.drawText(endNum,offsetStart+50,endNumY+50,endNumPaint);
        }
        if (isAnimatorStart) {
            //如果动画开始 画next
            canvas.drawText(nextNum,offsetStart+50,nextNumY+50,nextNumPaint);
        }
    }

    private void initNumber() {
        //如果已经点赞了 只能取消 即数字只能减
        if (isFavor){
            int numberSize=String.valueOf(currentNum).length();
            int test= (int) (currentNum/Math.pow(10,numberSize-1));
            int text= (int) ((currentNum-1)/Math.pow(10,numberSize-1));
            if (test>text){
                //如果发生了 数字位数的改变  如 100 到 99
                startNum=null;
                endNum=String.valueOf(currentNum);
                nextNum=String.valueOf(currentNum-1);
            }else {
                //如果数字位数不发生改变 且 在减的情况下
                if (currentNum%10!=0) {
                    //个位数不等于0 那么改变的只有个位数
                    startNum=String.valueOf(currentNum/10);
                    endNum=String.valueOf(currentNum%10);
                    nextNum=String.valueOf(currentNum%10-1);
                }else {
                    //改变的不只是个位数  310-309 111110-109999等
                    int numberLength=String.valueOf(currentNum).length();
                    for (int i = 1; i < numberLength; i++) {
                        if (currentNum%Math.pow(10,i)==0){
                            nextNum=9+nextNum;
                        }else {
                            //遇到不是9的位数 数字直接退出 不会继续进位了
                            break;
                        }
                    }
                    int result= (int) (currentNum/Math.pow(10,nextNum.length()+1));
                    startNum=String.valueOf(result);
                    endNum=String.valueOf(currentNum).substring(startNum.length());
                    int extra= (int) (currentNum/Math.pow(10,nextNum.length())%10-1);
                    nextNum=extra+nextNum;
                }
            }
        }else {
            //未点赞 则数字 增加
            int numberSize=String.valueOf(currentNum).length();
            int test= (int) (currentNum/Math.pow(10,numberSize-1));
            int text= (int) ((currentNum+1)/Math.pow(10,numberSize-1));
            if (test<text){
                //如果增加之后发生了数字位数的改变 如 99到100
                startNum=null;
                endNum=String.valueOf(currentNum);
                nextNum=String.valueOf(currentNum+1);
            }else {
                //如果增加之后数字位数未发生改变 且 在数字增加的情况下
                /*
                * 313 - 314
                * 309 - 310
                * 199 - 200
                * */
                //判断数字是几位数
//                int size=String.valueOf(currentNum).length();
                if (currentNum%10==9){
                    //加 1  会发生进位 及后续是否会进位
                    List<Integer> list=new ArrayList<>();
                    int size=String.valueOf(currentNum).length();
                    for (int i = 1; i <size; i++) {
                        if (currentNum%Math.pow(10,i)==9){
                            endNum=9+endNum;
                        }else {
                            //遇到不是9的位数 数字直接退出 不会继续进位了
                            break;
                        }
                    }
                    //加一 是即将进位的数字
                    if (currentNum/Math.pow(10,endNum.length()+1)!=0) {
                        int startNumber= (int) (currentNum/Math.pow(10,endNum.length()+1));
                        startNum=String.valueOf(startNumber);
                        endNum=String.valueOf(currentNum).substring(startNum.length());
                        nextNum=String.valueOf(currentNum+1).substring(startNum.length());
                    }else {
                        startNum=null;
                        endNum=String.valueOf(currentNum);
                        nextNum=String.valueOf(currentNum+1);
                    }

                }else {
                    startNum=String.valueOf(currentNum/10);
                    endNum=String.valueOf(currentNum%10);
                    nextNum=String.valueOf(currentNum%10+1);
                }
            }
        }
        Log.e("lzf_jike","currentNum: "+currentNum+"\n"
                         +"startNum:  "+startNum+"\n"
                         +"endNum:    "+endNum+"\n"
                         +"nextNum:   "+nextNum);
    }

    @Override
    public void onClick(View view) {
        //确定startNum和endNum
        initNumber();
        if (!isAnimatorStart){
            isAnimatorStart=true;
            ValueAnimator animator=null;
            if (isFavor) {
                //向下
                animator=ValueAnimator.ofInt(0,50);
            }else {
                //向上
                animator=ValueAnimator.ofInt(0,-50);
            }
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int value= (int) valueAnimator.getAnimatedValue();
                    endNumY=50+value;
                    nextNumY=(isFavor?Math.abs(value):(100+value));
                    postInvalidate();
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    isAnimatorStart=false;
                    //改变 点赞状态
                    isFavor=!isFavor;
                    //恢复状态
                    if (startNum!=null){
                        startNum=startNum+nextNum;
                    }else {
                        startNum=nextNum;
                    }
                    nextNum="";
                    endNum="";
                    currentNum=Integer.valueOf(startNum);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animator.setDuration(2000);
            animator.start();
        }else {
            Toast.makeText(getContext(),"动画进行中 等会吧",Toast.LENGTH_SHORT).show();
        }
    }
}
