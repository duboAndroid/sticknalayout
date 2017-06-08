package com.zhy.stickynavlayout

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.widget.RelativeLayout
import android.widget.TextView

import com.zhy.stickynavlayout.view.SimpleViewPagerIndicator


class MainActivity : FragmentActivity() {
    private val mTitles = arrayOf("11简介", "22评价", "33相关")
    private var mIndicator: SimpleViewPagerIndicator? = null
    private var mViewPager: ViewPager? = null
    private var mAdapter: FragmentPagerAdapter? = null
    private val mFragments = arrayOfNulls<TabFragment>(mTitles.size)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initDatas()
        initEvents()
    }

    private fun initEvents() {
        mViewPager!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float,
                                        positionOffsetPixels: Int) {
                mIndicator!!.scroll(position, positionOffset)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }

    private fun initDatas() {
        mIndicator!!.setTitles(mTitles)

        for (i in mTitles.indices) {
            mFragments[i] = TabFragment.newInstance(mTitles[i]) as TabFragment
        }

        mAdapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount(): Int {
                return mTitles.size
            }

            override fun getItem(position: Int): TabFragment? {
                return mFragments[position]
            }

        }

        mViewPager!!.adapter = mAdapter
        mViewPager!!.currentItem = 0
    }

    private fun initViews() {
        mIndicator = findViewById(R.id.id_stickynavlayout_indicator) as SimpleViewPagerIndicator
        mViewPager = findViewById(R.id.id_stickynavlayout_viewpager) as ViewPager

        val ll = findViewById(R.id.id_stickynavlayout_topview) as RelativeLayout
        val tv = TextView(this)
        tv.text = "我的动态添加的"
        tv.setBackgroundColor(0x4400ff00)//#4400ff00
        ll.addView(tv, RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, 600))
    }


}
