package com.zhy.stickynavlayout.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class SimpleViewPagerIndicator
    constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    private var mTitles: Array<String>? = null
    private var mTabCount: Int = 0
    private val mIndicatorColor = COLOR_INDICATOR_COLOR
    private var mTranslationX: Float = 0.toFloat()

    private val mPaint = Paint()
    private var mTabWidth: Int = 0

    private var test :Byte = 1
    private var test2 :Short = 1
    private var test3 :Long = 1
    private var test4 :Double = 1.toDouble()
    private var test5 :Char = 1.toChar()
    private var test6 :Boolean = true
    private var test7 :Float = 0f

    private val im = TextView(getContext())

    init {
        mPaint.color = mIndicatorColor
        mPaint.strokeWidth = 9.0f
    }

    fun setTitles(titles: Array<String>) {
        mTitles = titles
        mTabCount = titles.size
        generateTitleView()

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mTabWidth = w / mTabCount
    }


    /*public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
    }*/

    override fun dispatchDraw(canvas: Canvas) {//下划线
        super.dispatchDraw(canvas)
        canvas.save()
        canvas.translate(mTranslationX, height.toFloat())
        canvas.drawLine(0f, 0f, mTabWidth.toFloat(), 0f, mPaint)
        canvas.restore()
    }

    fun scroll(position: Int, offset: Float) {
        /**
         * <pre>
         * 0-1:position=0 ;1-0:postion=0;
        </pre> *
         */
        mTranslationX = width / mTabCount * (position + offset)//0+ 1 *3
        /* Log.i(TAG,"positon" + position);
        Log.i(TAG,"offset" + offset);
        Log.i(TAG,"//////////" + mTabCount * (position + offset));
        Log.i(TAG,"getWidth" + getWidth());  //1080*/
        invalidate()
    }


    /*@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }*/

    private fun generateTitleView() {
        if (childCount > 0)
            this.removeAllViews()
        val count = mTitles!!.size

        weightSum = count.toFloat()
        for (i in 0..count - 1) {
            val tv = TextView(context)
            val lp = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
            lp.weight = 1f
            tv.layoutParams = lp
            tv.gravity = Gravity.CENTER
            tv.setTextColor(COLOR_TEXT_NORMAL)
            tv.text = mTitles!![i]
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            tv.setOnClickListener { }
            addView(tv)
        }
    }

    companion object {
        private val COLOR_TEXT_NORMAL = 0xFF000000.toInt()
        private val COLOR_INDICATOR_COLOR = Color.GREEN
        private val TAG = SimpleViewPagerIndicator::class.java!!.getSimpleName()
    }

}
