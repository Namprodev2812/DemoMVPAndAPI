package com.asterisk.nam.demomvpandloadapi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asterisk.nam.demomvpandloadapi.R;
import com.asterisk.nam.demomvpandloadapi.model.User;
import com.bumptech.glide.Glide;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> mUsers;
    private LayoutInflater mInflater;

    public UserAdapter(Context context, List<User> users) {
        this.mUsers = users;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_recyclerview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.bindData(mUsers.get(i));
    }

    @Override
    public int getItemCount() {
        return mUsers == null ? 0 : mUsers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private StringBuilder mBuilder;
        private TextView mTextName;
        private ImageView mImageAvarta;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            mTextName = itemView.findViewById(R.id.text_user_name);
            mImageAvarta = itemView.findViewById(R.id.image_avatar);
        }

        public void bindData(User user) {
            mTextName.setText(mBuilder.append(user.getName()));
            Glide.with(mImageAvarta)
                    .load(user.getAvatar())
                    .error(R.drawable.ic_cloud_off_black_24dp)
                    .centerCrop()
                    .into(mImageAvarta);
        }
    }
}
