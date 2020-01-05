package com.adityagupta.foodorderingapp.adapters

import android.content.Context
import android.graphics.*
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adityagupta.foodorderingapp.R
import com.adityagupta.foodorderingapp.data.CategoryInfo
import kotlinx.android.synthetic.main.recycler_category.view.*
import android.graphics.drawable.Drawable
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import android.graphics.drawable.BitmapDrawable
import com.adityagupta.foodorderingapp.data.CategoryItem
import com.bumptech.glide.request.RequestOptions


class CategoryItemAdapter(private val list: ArrayList<CategoryItem>, private val context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<CategoryItemHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CategoryItemHolder {
        return CategoryItemHolder(LayoutInflater.from(context).inflate(R.layout.recycler_category_item, p0, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryItemHolder, p1: Int) {
        val category: CategoryItem = list[p1]
        holder.textTitle.text = category.name
    }

}

class CategoryItemHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    val textTitle = itemView.textTitle!!
}