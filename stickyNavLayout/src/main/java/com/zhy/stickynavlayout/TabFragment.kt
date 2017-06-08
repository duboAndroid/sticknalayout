package com.zhy.stickynavlayout

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zhy.base.adapter.ViewHolder
import com.zhy.base.adapter.recyclerview.CommonAdapter

import java.util.ArrayList

class TabFragment : Fragment() {
    private var mTitle = "Defaut Value"
    private var mRecyclerView: RecyclerView? = null
    // private TextView mTextView;
    private val mDatas = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mTitle = arguments.getString(TITLE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_tab, container, false)
        mRecyclerView = view.findViewById(R.id.id_stickynavlayout_innerscrollview) as RecyclerView
        mRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        // mTextView = (TextView) view.findViewById(R.id.id_info);
        // mTextView.setText(mTitle);
        for (i in 0..19) {
            mDatas.add(mTitle + " -> " + i)
        }
        mRecyclerView!!.adapter = object : CommonAdapter<String>(activity, R.layout.item, mDatas) {
            override fun convert(holder: ViewHolder, o: String) {
                holder.setText(R.id.id_info, o)
            }
        }

        return view

    }

    companion object {
        val TITLE = "title"

        fun newInstance(title: String): TabFragment {
            val tabFragment = TabFragment()
            val bundle = Bundle()
            bundle.putString(TITLE, title)
            tabFragment.arguments = bundle
            return tabFragment
        }
    }

}
