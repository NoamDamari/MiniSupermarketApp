package com.example.myfragmentsapp;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfragmentsapp.activities.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.MyViewHolder> {
    private ArrayList<Product> products;
    private FirebaseAuth mAuth;
    private Context context;

    public CustomeAdapter(ArrayList<Product> products, Context context) {
        this.products = products;
        this.context = context;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName;
        ImageView productImage;
        Button plusButton;
        Button minusButton;
        Button addProductButton;
        TextView productQuantity;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.productName = itemView.findViewById(R.id.productName);
            this.productImage = itemView.findViewById(R.id.productImage);
            this.plusButton = itemView.findViewById(R.id.plusButton);
            this.minusButton = itemView.findViewById(R.id.minusButton);
            this.productQuantity = itemView.findViewById(R.id.textViewQuantity);
            this.addProductButton = itemView.findViewById(R.id.addProductButton);
        }
    }

    @NonNull
    @Override
    public CustomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
        return new MyViewHolder(view , parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull CustomeAdapter.MyViewHolder holder, int position) {
        Product currentProduct = products.get(position);
        holder.productName.setText(products.get(position).getProductName());
        holder.productImage.setImageResource(products.get(position).getImage());
        mAuth = FirebaseAuth.getInstance();
        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantityText = holder.productQuantity.getText().toString();
                int quantity = Integer.parseInt(quantityText);
                quantity++;
                holder.productQuantity.setText(String.valueOf(quantity));
            }
        });

        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantityText = holder.productQuantity.getText().toString();
                int quantity = Integer.parseInt(quantityText);
                if (quantity > 0) {
                    quantity--;
                }
                holder.productQuantity.setText(String.valueOf(quantity));
            }
        });

        holder.addProductButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String productName = holder.productName.getText().toString();
                String uid = mAuth.getCurrentUser().getUid();
                FirebaseUtils.addProductToUserListOnDB(uid ,productName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
