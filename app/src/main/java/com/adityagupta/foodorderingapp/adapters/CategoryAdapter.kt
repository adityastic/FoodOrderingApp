package com.adityagupta.foodorderingapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.support.v7.widget.RecyclerView
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
import com.adityagupta.foodorderingapp.activities.CategoryActivity
import com.adityagupta.foodorderingapp.data.CategoryItem
import com.bumptech.glide.request.RequestOptions
import java.util.*


class CategoryAdapter(private val list: ArrayList<CategoryInfo>, private val context: Context) : RecyclerView.Adapter<CategoryHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CategoryHolder {
        return CategoryHolder(LayoutInflater.from(context).inflate(R.layout.recycler_category, p0, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class compMin : Comparator<CategoryItem> {
        override fun compare(a: CategoryItem, b: CategoryItem): Int {
            if (a.price > b.price)
                return -1 // highest value first
            return if (a.price === b.price) 0 else 1
        }
    }

    inner class compMax : Comparator<CategoryItem> {
        override fun compare(a: CategoryItem, b: CategoryItem): Int {
            if (a.price < b.price)
                return -1 // highest value first
            return if (a.price === b.price) 0 else 1
        }
    }

    override fun onBindViewHolder(holder: CategoryHolder, p1: Int) {
        val category: CategoryInfo = list[p1]

        holder.textTitle.text = category.name

        var min : CategoryItem = Collections.max(category.items, compMin())
        var max : CategoryItem = Collections.max(category.items, compMax())
        if( min != max)
            holder.textPriceRange.text = "₹${min.price} - ₹${max.price}"
        else
            holder.textPriceRange.text = "₹${min.price}"

        val req = RequestOptions()
        req.placeholder(R.drawable.none)
        req.error(R.drawable.none)

        Glide.with(context)
                .setDefaultRequestOptions(req)
                .load(category.imageURL).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        Log.e("FailedGlide", "YES")
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        Log.e("FailedGlide", "NO")

                        val bit: Bitmap = drawableToBitmap(resource!!)!!
                        val netBit = addGradient(bit)

                        holder.image.setImageBitmap(netBit)
                        holder.image.visibility = View.VISIBLE
                        return true
                    }

                }).into(holder.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("index", p1)
            context.startActivity(intent)
        }
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap? {
        var bitmap: Bitmap? = null

        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }

        if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    fun addGradient(originalBitmap: Bitmap): Bitmap {
        val width = originalBitmap.width
        val height = originalBitmap.height
        val updatedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(updatedBitmap)

        canvas.drawBitmap(originalBitmap, 0f, 0f, null)

        val paint = Paint()
        val shader = LinearGradient(0f, 0f, width.toFloat(), 0f, context.resources.getColor(R.color.gradientendColor), context.resources.getColor(R.color.gradientstartColor), Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        return updatedBitmap
    }

}

class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textTitle = itemView.textTitle!!
    val image: ImageView = itemView.image as ImageView
    val textPriceRange = itemView.pricerange
}