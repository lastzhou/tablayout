package com.ex.tabview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class TabView extends HorizontalScrollView {
    LinearLayout linearLayout;

    int curIdx=0;
    List<TextView> viewList=new ArrayList<>();
    Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    int offset=0;
    int textSize =0;
    int textColorId =0;
    int lineSize;
    int lineColorId;

    public void setLineSize(int lineSize) {
        this.lineSize = lineSize;
        paint.setStrokeWidth(lineSize);
    }

    public void setLineColorId(int lineColorId) {
        this.lineColorId = lineColorId;
        paint.setColor(lineColorId);
    }

    public void setTextBold(boolean textBold) {
        this.textBold = textBold;
    }

    boolean textBold;

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        setHorizontalScrollBarEnabled(false);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        lineColorId=Color.GREEN;
        paint.setColor(lineColorId);
        lineSize=dp2px(2);
        paint.setStrokeWidth(lineSize);
        offset=dp2px(20);
        textSize =20;
        textColorId=Color.BLUE;
    }
    public void init(){
         linearLayout=new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        addView(linearLayout);

    }

    public void setTextSyle(int sp,int colorId){
        textSize=sp;
        textColorId=colorId;
    }

    public void fillData(String[] datas){

        viewList.clear();
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.rightMargin=offset;
        for (int i1 = 0; i1 < datas.length; i1++) {

            TextView textView=new TextView(getContext());
            textView.setTextSize(textSize);
            textView.setTextColor(textColorId);
            if (textBold){
                TextPaint paint= textView.getPaint();
                paint.setFakeBoldText(true);
            }

            if (i1==datas.length-1){
                params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                params.rightMargin=offset/3;
            }
            textView.setLayoutParams(params);
            textView.setText(datas[i1]);
            viewList.add(textView);
            textView.setOnClickListener(new SlideListener(i1));
            linearLayout.addView(textView);
        }

    }

    class SlideListener implements OnClickListener{
        public SlideListener(int clickIndex) {
            this.clickIndex = clickIndex;
        }

        int clickIndex;
        @Override
        public void onClick(View v) {
            curIdx=clickIndex;
            invalidate();
            int x=getScrollX1();
            scrollTo(x,0);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
      int h=  getHeight()-dp2px(5);
     TextView textView= viewList.get(curIdx);
      int sx=getLeft(curIdx);
      int txtW=textView.getWidth();
      int stopX=sx+txtW;
      canvas.drawLine(sx,h,stopX,h,paint);


    }

        public int getLeft(int i){

        int left=0;
            for (int j = 0; j <i ; j++) {
               TextView textView= viewList.get(j);
               left+=textView.getWidth();
               left+=offset;
            }
        return left;
        }



        public int getScrollX1(){
            View selectedChild=linearLayout.getChildAt(curIdx);
          return   selectedChild.getLeft()
                    - (getWidth() / 2);
        }
    private  int dp2px(float dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, Resources.getSystem().getDisplayMetrics());
    }



    private   int sp2px( float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, Resources.getSystem().getDisplayMetrics());
    }


}
