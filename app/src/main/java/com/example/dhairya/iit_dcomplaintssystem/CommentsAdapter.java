//Adapter for displaying comments in a thread
package com.example.dhairya.complaintsystem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dhairya on 24-02-2016.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder>{
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView NameTextView;
        public TextView CommentTextView;
        public TextView TimeTextView;
        public MyViewHolder(View itemview)
        {
            super(itemview);
            NameTextView = (TextView)itemview.findViewById(R.id.user);
            CommentTextView = (TextView)itemview.findViewById(R.id.comment);
            TimeTextView = (TextView)itemview.findViewById(R.id.time);

        }
    }
    private ArrayList<recycler_comments> commentArray;

    public CommentsAdapter(ArrayList<recycler_comments> allcomments)
    {
        commentArray = allcomments;
    }

    @Override
    public CommentsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View gradeView = inflater.inflate(R.layout.item_comment, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(gradeView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(CommentsAdapter.MyViewHolder holder, int position) {
        recycler_comments comment1 = commentArray.get(position);
        TextView textView1 = holder.NameTextView;
        textView1.setText(comment1.name);
        TextView textView2 = holder.CommentTextView;
        textView2.setText(comment1.comment);
        TextView textView3 = holder.TimeTextView;
        textView3.setText(comment1.time);

    }
    @Override
    public int getItemCount() {
        return commentArray.size();
    }
}











