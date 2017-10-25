package com.example.githubusers.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.githubusers.R;
import com.example.githubusers.model.User;

import java.util.ArrayList;
import java.util.List;



public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CustomViewHolder> {


    private List<User> users = new ArrayList<User>();

    private final Context mContext;



    public ListAdapter(Context context, List<User> users) {
        this.mContext = context;
        this.users = users;

    }

    @Override
    public ListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item, parent, false);
        return new CustomViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ListAdapter.CustomViewHolder holder, int position) {
        User user = users.get(position);
        holder.name.setText(user.login);
        Glide.with(mContext).load(user.avatarUrl).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder  {


        TextView name;
        ImageView image;

        public CustomViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.tvName);
            image = (ImageView) view.findViewById(R.id.UserImage);

        }



    }
}