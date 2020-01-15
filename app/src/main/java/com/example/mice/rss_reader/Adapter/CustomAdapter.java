package com.example.mice.rss_reader.Adapter;

import android.content.Context;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mice.rss_reader.modal.Docbao;
import com.example.mice.rss_reader.Main2Activity;
import com.example.mice.rss_reader.R;
import com.squareup.picasso.Picasso;



import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends ArrayAdapter<Docbao> {
    private int mode = 1;
    ArrayList<Docbao> items;

    ArrayList<Docbao> arrayList;
    ArrayList<ArrayList<Docbao>> arrayListsearch;

    public CustomAdapter(Context context, int resource, List<Docbao> items) {
        super(context, resource, items);

        this.items= (ArrayList<Docbao>) items;
        this.arrayList=new ArrayList<Docbao>();
        arrayListsearch = new ArrayList<ArrayList<Docbao>>();
        this.arrayList.addAll(items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.dong_layout_listview, null);
        }
        final Docbao p = getItem(position+1);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView txttitle = (TextView) view.findViewById(R.id.txtTitle);
            TextView txttitlett = view.findViewById(R.id.txttitlett);
            ImageView imgtt = view.findViewById(R.id.imgtt);
            LinearLayout linearLayout = view.findViewById(R.id.layoutclick);

            if (position == 0){
                txttitlett.setVisibility(View.VISIBLE);
                imgtt.setVisibility(View.VISIBLE);
                txttitlett.setText(items.get(0).getTitle());
                if (p.image != "") {
                    String image500_300 = items.get(0).image.replace("180x108.jpg","500x300.jpg");
                    Picasso.get().load(image500_300).into(imgtt);
                }
                imgtt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), Main2Activity.class);
                        intent.putExtra("link",items.get(0).link);
                        getContext().startActivity(intent);
                    }
                });


            }else {
                txttitlett.setVisibility(View.GONE);
                imgtt.setVisibility(View.GONE);
//                imgtt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
            }
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), Main2Activity.class);
                    intent.putExtra("link",p.link);
                    getContext().startActivity(intent);
                }
            });
            txttitle.setText(p.title);

            ImageView imageView= (ImageView) view.findViewById(R.id.imageView);
            Log.d("Manh", "getView: "+p.getLink());


            if (p.image != "") {

                Picasso.get().load(p.image).into(imageView);
            }

            TextView txtpubdate=(TextView) view.findViewById(R.id.txtPubDate);
            txtpubdate.setText(p.pubDate);

            TextView txtdescription = (TextView)view.findViewById(R.id.txtDescription);
            txtdescription.setText(p.description);
        }
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.anima_left_in);
        view.startAnimation(animation);
        return view;
    }


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        arrayListsearch.add(items);
        items.clear();
        if (charText.length() == 0) {
            items.addAll(arrayList);
        } else {
            for (Docbao docbao : arrayList) {
                Log.d("manh2", "filter: "+docbao.getLink());
                if (docbao.title.toLowerCase(Locale.getDefault())
                        .contains(charText.toLowerCase())) {
                    Log.d("manh1", "filter: "+docbao.title);
                    items.add(docbao);
                }else {
                    Log.d("manh3", "filter: "+docbao.title);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size()-1;
    }
}