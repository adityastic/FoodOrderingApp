package com.adityagupta.foodorderingapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import android.view.WindowManager
import com.adityagupta.foodorderingapp.R
import com.adityagupta.foodorderingapp.adapters.CategoryItemAdapter
import com.adityagupta.foodorderingapp.data.CategoryInfo
import com.adityagupta.foodorderingapp.data.CategoryItem
import com.adityagupta.foodorderingapp.utils.Common
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class CategoryActivity : AppCompatActivity() {

    private var categoryItemAdapter: CategoryItemAdapter? = null
    private var categoryInfo : CategoryInfo? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        categoryInfo = Common.categoryList!![intent.getIntExtra("index",-1)]

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        refreshLayout()

        swipeRefresh.setOnRefreshListener { refreshLayout() }

        setToolbar()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        title = ""
        titleToolbar.text = categoryInfo!!.name

        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


    private fun refreshLayout() {
        swipeRefresh.isRefreshing = true
        recyclerView.adapter = null

        val list : ArrayList<CategoryItem> = categoryInfo!!.items

        categoryItemAdapter = CategoryItemAdapter(list,this)
        recyclerView.adapter = categoryItemAdapter
        swipeRefresh.isRefreshing = false
    }
}
