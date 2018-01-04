package com.creeps.sl_app.quizapp.base_modules.blog.blog_views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.creeps.sl_app.quizapp.core_services.utils.modal.BlogItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohan on 26/12/17.
 */

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.BlogRecyclerViewHolder> {


    private ArrayList <BlogItem>mBlogItems;

    public BlogRecyclerAdapter(ArrayList<BlogItem> arrayList){
        this.mBlogItems=arrayList;

    }


    @Override
    public void onBindViewHolder(BlogRecyclerViewHolder holder, int position, List payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public BlogRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BlogRecyclerViewHolder holder, int position) {
        /* todo bind data elements to their respective views*/
    }

    @Override
    public int getItemCount() {
        return mBlogItems.size();
    }

     static class BlogRecyclerViewHolder extends RecyclerView.ViewHolder{

        public BlogRecyclerViewHolder(View view){
            super(view);
        }
    }

}
