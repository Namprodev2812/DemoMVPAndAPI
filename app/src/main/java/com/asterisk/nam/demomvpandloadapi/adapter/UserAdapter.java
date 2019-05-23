package com.asterisk.nam.demomvpandloadapi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asterisk.nam.demomvpandloadapi.R;
import com.asterisk.nam.demomvpandloadapi.model.User;
import com.bumptech.glide.Glide;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;
    private final static int VIEW_TYPE_LOADING = 0;
    private final static int VIEW_TYPE_ITEM = 1;
    private boolean mFeelViewHolder = false;

    public UserAdapter(Context context, List<User> persons) {
        this.mContext = context;
        this.mUsers = persons;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        if (i == VIEW_TYPE_ITEM) {
            mFeelViewHolder = true;
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview, viewGroup, false);
            return new ViewHolder(view);
        } else{
            mFeelViewHolder = false;
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_null_recyclerview, viewGroup, false);
            return new ViewHolder1(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        if (mFeelViewHolder) {
            UserAdapter.ViewHolder viewHolders = (UserAdapter.ViewHolder) viewHolder;
            viewHolders.bindData(i);
        } else if (!mFeelViewHolder){
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mUsers.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextName;
        private ImageView mImageAvarta;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            mTextName = itemView.findViewById(R.id.text_user_name);
            mImageAvarta = itemView.findViewById(R.id.image_avatar);
        }

        public void bindData(int position) {
            mTextName.setText("" + mUsers.get(position).getmName()+"---"+position);
            Glide.with(mContext)
                    .load(mUsers.get(position).getmAvatar())
                    .error(R.drawable.ic_cloud_off_black_24dp)
                    .centerCrop()
                    .into(mImageAvarta);
        }
    }
    class ViewHolder1 extends RecyclerView.ViewHolder {

        public ViewHolder1(@NonNull final View itemView) {
            super(itemView);
        }
    }
}
