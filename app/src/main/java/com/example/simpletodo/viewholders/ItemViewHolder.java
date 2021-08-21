    package com.example.simpletodo.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletodo.R;
import com.example.simpletodo.adapters.ItemsAdapter;
import com.example.simpletodo.entities.Item;

import org.jetbrains.annotations.NotNull;

public class ItemViewHolder extends RecyclerView.ViewHolder{
    private TextView textItem;
    private TextView textDueDate;
    private TextView imagePriority;

    public ItemViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        textItem = itemView.findViewById(R.id.textItem);
        textDueDate = itemView.findViewById(R.id.textDueDate);
        imagePriority = itemView.findViewById(R.id.imagePriority);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemsAdapter.clickListener.onItemClick(view, getAdapterPosition());
            }
        });
    }

    public void bind(Item item) {
        textItem.setText(item.getText());
        textDueDate.setText("Due date: " + String.format("%d/%d/%d",
                item.getYear(), item.getMonth(), item.getDate()));

        int p = item.getPriority();
        imagePriority.setText(String.valueOf(p));
        switch (p) {
            case (1):
                imagePriority.setBackgroundResource(R.drawable.background_priority_1);
                break;
            case (2):
                imagePriority.setBackgroundResource(R.drawable.background_priority_2);
                break;
            case (3):
                imagePriority.setBackgroundResource(R.drawable.background_priority_3);
                break;
            case (4):
                imagePriority.setBackgroundResource(R.drawable.background_priority_4);
                break;
            default:
        }

//        textItem.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                longClickListener.onItemLongClicked(getAdapterPosition());
//                return true;
//            }
//        });

    }

    public static ItemViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent ,false);
        return new ItemViewHolder(view);
    }

}
