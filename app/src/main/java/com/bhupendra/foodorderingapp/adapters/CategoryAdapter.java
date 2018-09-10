package com.bhupendra.foodorderingapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhupendra.foodorderingapp.R;
import com.bhupendra.foodorderingapp.data.CategoryInfo;
import com.bhupendra.foodorderingapp.utils.FirebaseConnector;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    ArrayList<CategoryInfo> list;
    Context context;

    public CategoryAdapter(ArrayList<CategoryInfo> categoryInfo, Context context) {
        this.list = categoryInfo;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(context).inflate(R.layout.recycler_category,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryHolder holder, int position) {
        CategoryInfo categoryInfo = list.get(position);

        holder.image.setVisibility(View.INVISIBLE);
        StorageReference load = FirebaseConnector.getStorageReference().child("menu").child(categoryInfo.getImageURL());

        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Bitmap newBitmap = addGradient(bitmap);

                holder.image.setImageBitmap(newBitmap);
                holder.image.setVisibility(View.VISIBLE);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                holder.image.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };

        load.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("DATA",uri.toString());

                Picasso.get().load(uri.toString()).placeholder(R.drawable.none).into(target);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        holder.textTitle.setText(categoryInfo.getName());
    }

    public Bitmap addGradient(Bitmap originalBitmap) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        Bitmap updatedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(updatedBitmap);

        canvas.drawBitmap(originalBitmap, 0, 0, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, 0, width, 0, context.getResources().getColor(R.color.gradientendColor), context.getResources().getColor(R.color.gradientstartColor), Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(0, 0, width, height, paint);

        return updatedBitmap;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder
    {
        TextView textTitle;
        ImageView image;
        public CategoryHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            image = itemView.findViewById(R.id.image);
        }
    }
}
