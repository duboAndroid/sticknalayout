package com.zhy.stickynavlayout.view

import android.content.Context
import android.support.v4.view.NestedScrollingParent
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.OverScroller

import com.zhy.stickynavlayout.R


class StickyNavLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs), NestedScrollingParent {

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        Log.e(TAG, "onStartNestedScroll")
        return true
    }

    override fun onNestedScrollAccepted(child: View, target: View, nestedScrollAxes: Int) {
        Log.e(TAG, "onNestedScrollAccepted")
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        Log.e(TAG, "onNestedPreScroll")
        val hiddenTop = dy > 0 && scrollY < mTopViewHeight
        val showTop = dy < 0 && scrollY >= 0 && !ViewCompat.canScrollVertically(target, -1)

        if (hiddenTop || showTop) {
            scrollBy(0, dy)
            consumed[1] = dy
        }
    }

    override fun onStopNestedScroll(target: View) {
        Log.e(TAG, "onStopNestedScroll")
    }


    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        Log.e(TAG, "onNestedScroll")
    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        Log.e(TAG, "onNestedFling")
        return false
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        Log.e(TAG, "onNestedPreFling")
        //down - //up+
        if (scrollY >= mTopViewHeight) return false
        fling(velocityY.toInt())
        return true
    }

    override fun getNestedScrollAxes(): Int {
        Log.e(TAG, "getNestedScrollAxes")
        return 0
    }

    private var mTop: View? = null
    private var mNav: View? = null
    private var mViewPager: ViewPager? = null

    private var mTopViewHeight: Int = 0

    private val mScroller: OverScroller
    private val mVelocityTracker: VelocityTracker? = null
    private val mTouchSlop: Int = 0
    private val mMaximumVelocity: Int = 0
    private val mMinimumVelocity: Int = 0

    private val mLastY: Float = 0.toFloat()
    private val mDragging: Boolean = false

    init {
        orientation = LinearLayout.VERTICAL
        mScroller = OverScroller(context)
        /*mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();*/

    }

    /*private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }*/


    /*@Override
    public boolean onTouchEvent(MotionEvent event)
    {
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(event);
        int action = event.getAction();
        float y = event.getY();

        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                mLastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;

                if (!mDragging && Math.abs(dy) > mTouchSlop)
                {
                    mDragging = true;
                }
                if (mDragging)
                {
                    scrollBy(0, (int) -dy);
                }

                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                mDragging = false;
                recycleVelocityTracker();
                if (!mScroller.isFinished())
                {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_UP:
                mDragging = false;
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityY = (int) mVelocityTracker.getYVelocity();
                if (Math.abs(velocityY) > mMinimumVelocity)
                {
                    fling(-velocityY);
                }
                recycleVelocityTracker();
                break;
        }

        return super.onTouchEvent(event);
    }*/


    override fun onFinishInflate() {
        super.onFinishInflate()
        mTop = findViewById(R.id.id_stickynavlayout_topview)
        mNav = findViewById(R.id.id_stickynavlayout_indicator)
        val view = findViewById(R.id.id_stickynavlayout_viewpager) as? ViewPager ?: throw RuntimeException(
                "id_stickynavlayout_viewpager show used by ViewPager !")
        mViewPager = view
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        getChildAt(0).measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        val params = mViewPager!!.layoutParams
        params.height = measuredHeight - mNav!!.measuredHeight
        setMeasuredDimension(measuredWidth, mTop!!.measuredHeight + mNav!!.measuredHeight + mViewPager!!.measuredHeight)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mTopViewHeight = mTop!!.measuredHeight
    }

    fun fling(velocityY: Int) {
        mScroller.fling(0, scrollY, 0, velocityY, 0, 0, 0, mTopViewHeight)
        invalidate()
    }

    override fun scrollTo(x: Int, y: Int) {
        var y = y
        if (y < 0) {
            y = 0
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight
        }
        if (y != scrollY) {
            super.scrollTo(x, y)
        }
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.currY)
            invalidate()
        }
    }

    companion object {
        private val TAG = "StickyNavLayout"
    }
}
