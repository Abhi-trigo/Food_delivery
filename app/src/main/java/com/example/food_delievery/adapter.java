package com.example.food_delievery;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.UserViewHolder>{
    private List<UserData> data;
    private List<UserData> filterdata;
    private Filter mfilter;
    public adapter(ArrayList<UserData> data){
        this.data=data;
        this.filterdata=data;
    }

    public adapter() {

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout= LayoutInflater.from(parent.getContext());
        View view=layout.inflate(R.layout.item_row,parent,false);


        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Log.d("tag","error");
        final UserData datas=filterdata.get(position);
        holder.txtname.setText(datas.getName());
        holder.rating.setText(datas.getRating().toString());
        holder.txtuser.setText(datas.getUser_rating().toString());
        Picasso.get().load(datas.getUrl()).error(R.drawable.ic_launcher_foreground).resize(100,100).centerCrop().into(holder.img);
    }

    @Override
    public int getItemCount() {
        return filterdata.size();
    }
    public Filter getFilter(){
        return new Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence charcs){
                String charstring=charcs.toString();
                if(charstring.isEmpty()|| charstring==null){
                    Log.d("tag","error1");
                    filterdata=data;
                }else{
                    Log.d("tag","error2");
                    List<UserData> filterdatas=new ArrayList<>();
                    for(UserData row: data){
                        if(row.getName().toLowerCase().contains(charstring.toLowerCase())){
                            filterdatas.add(row);
                        }
                    }
                    Log.d("tag","error3");
                    filterdata=filterdatas;
                }
                Log.d("tag","error4");
                FilterResults filter =new FilterResults();
                filter.values=filterdata;
                return filter;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterdata=(ArrayList<UserData>) results.values;
                notifyDataSetChanged();
            }

        };
    }
    class UserViewHolder extends RecyclerView.ViewHolder{
        public final View itemview;
        TextView txtname;
        TextView txtuser;
        TextView rating;
        ImageView img;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemview = itemView;
            txtname=itemview.findViewById(R.id.login);
            txtuser=itemview.findViewById(R.id.score);
            rating=itemview.findViewById(R.id.view_url);
            img=itemView.findViewById(R.id.img);
        }
    }

}
