package com.example.birlesmishali;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {



    //vars
    CircleImageView imageview;
    TextView name;


    private RecyclerView parentRecycler;


     List <Slider> sliders;
    private Context mContext;

    public RecyclerViewAdapter(Context context,  List<Slider> sliders) {

        this.sliders=sliders;
        this.mContext = context;
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parentRecycler = recyclerView;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


       Slider slider=sliders.get(position);

        Glide.with(mContext)
//                .asBitmap()
                .load(slider.getCityIcon())
                .into(holder.imageview);



//        holder.image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return sliders.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CircleImageView imageview;
        private TextView name;


        public ViewHolder(View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.name);
        }

        @Override
        public void onClick(View v) {

                parentRecycler.smoothScrollToPosition(getAdapterPosition());
            }
        public void ortadaBuyume() {
            int parentHeight = ((View) imageview.getParent()).getHeight();
            float scale = (80+parentHeight - name.getHeight()) / (float) imageview.getHeight();
            imageview.setPivotX(imageview.getWidth() * 0.52f);
            imageview.setPivotY(imageview.getWidth() * 0.52f);
            imageview.animate().scaleX(scale)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            //textView.setVisibility(View.VISIBLE);
//                            imageView.setColorFilter(Color.BLACK);
                        }
                    })
                    .scaleY(scale).setDuration(200)
                    .start();
        }

    }




}