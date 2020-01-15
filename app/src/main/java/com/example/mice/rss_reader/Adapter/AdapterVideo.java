package com.example.mice.rss_reader.Adapter;

import com.example.mice.rss_reader.PlayVideoActivity;
import com.example.mice.rss_reader.modal.Docbao;
import com.example.mice.rss_reader.R;
import com.example.mice.rss_reader.modal.VideoYoutube;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.ViewHolder> {
    ArrayList<VideoYoutube> listaccount;
    Context mcontext;

    public AdapterVideo(Context mcontext, ArrayList<VideoYoutube> listaccount) {
        this.listaccount = listaccount;
        this.mcontext = mcontext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemview = layoutInflater.inflate(R.layout.item_video, parent, false);

        Animation animation = AnimationUtils.loadAnimation(mcontext,R.anim.anima_in_top);
        itemview.startAnimation(animation);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder v, int position) {
        final VideoYoutube listvideo = listaccount.get(position);
        v.txtTitel.setText(listvideo.getTitle());
        if (listvideo.image != "") {
            Picasso.get().load(listvideo.image).into(v.img);
        }
        v.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, PlayVideoActivity.class);
                intent.putExtra("idvideo",listvideo.id);
                mcontext.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return listaccount.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitel,txtbody;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitel = itemView.findViewById(R.id.txttitle);
            img = itemView.findViewById(R.id.imgvideo);
        }
    }

}
