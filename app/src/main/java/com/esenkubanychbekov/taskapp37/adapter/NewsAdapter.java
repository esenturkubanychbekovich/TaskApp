package com.esenkubanychbekov.taskapp37.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esenkubanychbekov.taskapp37.R;
import com.esenkubanychbekov.taskapp37.databinding.ItemNewsBinding;
import com.esenkubanychbekov.taskapp37.interfaces.OnItemClickListener;
import com.esenkubanychbekov.taskapp37.model.News;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> list;
    private OnItemClickListener clickListener;

    public NewsAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(News news) {
        list.add(0, news);
        notifyItemChanged(0);
    }

    //возврашает элемент из листа
    public News getItem(int pos) {
        return list.get(pos);
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemNewsBinding binding;

        public ViewHolder(ItemNewsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(v -> clickListener.onClick(getAdapterPosition()));
            binding.getRoot().setOnLongClickListener(v -> {
                clickListener.onLongClick(getAdapterPosition());
                return true;
            });
        }

        public void onBind(News news) {
            binding.textTitle.setText(news.getTitle());
        }
    }
}
