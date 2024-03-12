package com.example.myfragmentsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<ListItem> itemsList;

    public ItemAdapter(List<ListItem> itemsList) {
        this.itemsList = itemsList;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemQuantity;
        Button removeButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemName = itemView.findViewById(R.id.listItemName);
            this.itemQuantity = itemView.findViewById(R.id.listItemQuantityTV);
            this.removeButton = itemView.findViewById(R.id.removeItemButton);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ListItem listItem = itemsList.get(holder.getAdapterPosition());
        holder.itemName.setText(listItem.getItemName());
        holder.itemQuantity.setText(String.valueOf(listItem.getItemQuantity()));

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    itemsList.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);

                    String uid = FirebaseAuth.getInstance().getUid();
                    String itemName = listItem.getItemName();
                    FirebaseUtils.removeItemFromUserListInDB(uid , itemName);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}



