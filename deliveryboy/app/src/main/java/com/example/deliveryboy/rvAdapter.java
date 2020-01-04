package com.example.deliveryboy;


import androidx.annotation.NonNull;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.Button;


import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class rvAdapter extends RecyclerView.Adapter<rvAdapter.MyViewHolder> {
    Context context;
    String name,no,price;
    ArrayList<Model> profiles;
    DatabaseReference db,postsref;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    public rvAdapter(Context c, ArrayList<Model> p) {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Model profile = profiles.get(position);
        postsref = firebaseDatabase.getReference("Uploads");
        postsref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Model p = profiles.get(position);
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String u = dataSnapshot1.child("id").getValue().toString();
                    if ((dataSnapshot1.child("id").getValue().toString()).equals(p.getPid())) {
                        name = dataSnapshot1.child("title").getValue(String.class);
                        no = dataSnapshot1.child("quantity").getValue(String.class);
                        price = dataSnapshot1.child("price").getValue(String.class);
                        System.out.println(name + no + price);

                    }

                }
                holder.name.setText(name);
                holder.price.setText(price);
                holder.quantity.setText(no);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //holder.name.setText(profile.getName());
        //String imgUrl = profile.getImage();
        // Log.d("image"   ,imgUrl);
        //holder.price.setText(profile.getPrice());

        // Picasso.with(context).load(imgUrl).into(holder.image);


        // holder.onClick(position);
        holder.setItemClickListener(
                new itemClickListener() {
                                        @Override
                                        public void onItemClick(View v, int pos) {
Intent intent = new Intent(context, accept.class);
                                            //ByteArrayOutputStream stream=new ByteArrayOutputStream();
                                            //byte[] bytes=stream.toByteArray();
                                            //intent.putExtra("Image", image1);
                                              Model p=profiles.get(pos);
                                             intent.putExtra("Pid",p.getPid());
                                             intent.putExtra("Cid",p.getCid());
                                            intent.putExtra("Id",p.getId());
                                            context.startActivity(intent);

                                        }
                                    }
        );
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name, price,quantity;
       // public ImageView image;
        public Button btn;
        itemClickListener itemClickListener;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            //image =  itemView.findViewById(R.id.image);
            price = (TextView) itemView.findViewById(R.id.menu_price);
            itemView.setOnClickListener(this);
        }
        public  void setItemClickListener(itemClickListener ic){
            this.itemClickListener=ic;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
        //  public void onClick(final int position) {
       // Intent intent = new Intent(context, accept.class);
            //ByteArrayOutputStream stream=new ByteArrayOutputStream();
            //byte[] bytes=stream.toByteArray();
            //intent.putExtra("Image", image1);
         //   Model p=profiles.get(position);
          // intent.putExtra("Pid",p.getPid());
           // intent.putExtra("Cid",p.getCid());
            //intent.putExtra("Id",p.getId());
            //context.startActivity(intent);





        //}

    }
}