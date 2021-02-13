package com.vikination.imagemachine.ui.detail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vikination.imagemachine.R;
import com.vikination.imagemachine.databinding.LayoutImageThumbnailBinding;
import com.vikination.imagemachine.model.Image;
import com.vikination.imagemachine.ui.detail.OnThumbnailClickListener;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class ImageThumbAdapter extends RecyclerView.Adapter<ImageThumbAdapter.ImageThumbViewHolder> {

    private List<Image> images = Collections.emptyList();
    private final OnThumbnailClickListener onThumbnailClickListener;
    private final Context context;

    public ImageThumbAdapter(Context context,OnThumbnailClickListener onThumbnailClickListener){
        this.onThumbnailClickListener = onThumbnailClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageThumbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutImageThumbnailBinding binding = LayoutImageThumbnailBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ImageThumbViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageThumbViewHolder holder, int position) {
        holder.bind(context, images.get(position), onThumbnailClickListener);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    static class ImageThumbViewHolder extends RecyclerView.ViewHolder{
        LayoutImageThumbnailBinding binding;

        public ImageThumbViewHolder(@NonNull LayoutImageThumbnailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Context context, Image image, OnThumbnailClickListener onThumbnailClickListener){
            Glide.with(context)
                    .load(new File(image.uri))
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(binding.imageThumb);
            if (image.isSelected) binding.maskSelectedimage.setVisibility(View.VISIBLE); else binding.maskSelectedimage.setVisibility(View.GONE);
            binding.imageThumb.setOnClickListener(view -> onThumbnailClickListener.onClickThumbnail(image.uri));
            binding.imageThumb.setOnLongClickListener(view -> {
                onThumbnailClickListener.onLongClickThumbnail(image);
                return true;
            });
        }
    }

    public void updateData(List<Image> images){
        this.images = images;
        notifyDataSetChanged();
    }
}
