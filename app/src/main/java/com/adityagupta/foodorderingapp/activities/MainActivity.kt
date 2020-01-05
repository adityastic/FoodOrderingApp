package com.adityagupta.foodorderingapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.WindowManager
import com.adityagupta.foodorderingapp.adapters.CategoryAdapter
import com.adityagupta.foodorderingapp.data.CategoryInfo
import com.adityagupta.foodorderingapp.utils.FirebaseConnector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.adityagupta.foodorderingapp.R
import com.adityagupta.foodorderingapp.data.CategoryItem
import com.adityagupta.foodorderingapp.utils.Common


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var categoryAdapter: CategoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        refreshLayout()

        swipeRefresh.setOnRefreshListener { refreshLayout() }

        toolbar.setNavigationIcon(R.drawable.ic_mobile_menu)
        setSupportActionBar(toolbar)
        title = ""

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toggle.isDrawerIndicatorEnabled = false
        toggle.setToolbarNavigationClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun refreshLayout() {
        swipeRefresh.isRefreshing = true
        categoryAdapter = null
        recyclerView.adapter = null

        Common.categoryList = ArrayList()

        val db = FirebaseConnector.firebaseFirestore

        db.collection("menu")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {

                            var list: ArrayList<CategoryItem> = ArrayList()
                            for (item in  document.data["items"] as ArrayList<*>)
                            {
                                var map:HashMap<*,*> = (item as HashMap<*, *>)
                                var name :String = map.get("name") as String
                                var cat : String = map.get("category") as String
                                var price : Double = (map.get("price") as Any).toString().toDouble()

                                list.add(CategoryItem(name,cat,price))
                            }

                            Common.categoryList!!.add(CategoryInfo(document.id, document.data["image"] as String,list))

                            categoryAdapter = CategoryAdapter(Common.categoryList!!, this@MainActivity)
                            recyclerView.adapter = categoryAdapter
                            swipeRefresh.isRefreshing = false
                        }
                    } else {
                        Log.e("DataSnap", "Error getting documents.", task.exception)
                    }
                }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
