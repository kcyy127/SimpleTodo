package com.example.simpletodo2.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;


import com.example.simpletodo2.entities.Item;
import com.example.simpletodo2.viewholders.ItemViewHolder;

import org.jetbrains.annotations.NotNull;


public class ItemsAdapter extends ListAdapter<Item, ItemViewHolder> {
    public static ClickListener clickListener;

    public ItemsAdapter(@NonNull @NotNull DiffUtil.ItemCallback<Item> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return ItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemViewHolder holder, int position) {
        Item current = getItem(position);
        holder.bind(current);

    }

    public Item getItemAtPosition(int position) {
        return getItem(position);
    }

    public static class ItemDiff extends DiffUtil.ItemCallback<Item> {

        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Item oldItem, @NonNull @NotNull Item newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Item oldItem, @NonNull @NotNull Item newItem) {
            if (oldItem.getText().equals(newItem.getText())
            && oldItem.getYear() == newItem.getYear()
            && oldItem.getMonth() == newItem.getMonth()
            && oldItem.getDate() == newItem.getDate()
            && oldItem.getPriority() == newItem.getPriority()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public interface ClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ItemsAdapter.clickListener = clickListener;
    }
}
