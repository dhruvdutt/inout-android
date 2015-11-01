package com.example.jay.inout_try1.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jay.inout_try1.R;


/**
 * Created by jay on 01/11/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private String dataSource[][];
    int position;
    boolean flag=true;

    View view;

    public RecyclerAdapter(String[][] dataArgs,View view){
        if(dataArgs==null){
            dataSource[0][0]="No Past Order.";
            flag=false;
        }else{
            dataSource=dataArgs;
            flag=true;
        }
        this.view=view;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //create a new view
        if(flag) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_past, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_records_layout, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        holder.orderId.setText("Order ID : " + dataSource[position][0]);
        Log.i("DATA", dataSource[position][0]);
        holder.orderDate.setText("Order Date : " + dataSource[position][1]);
        Log.i("DATA", dataSource[position][1]);
        holder.orderDate.setText("Duration : " + dataSource[position][2]);
        Log.i("DATA", dataSource[position][2]);
        holder.orderDate.setText("Job Skill : " + dataSource[position][3]);
        Log.i("DATA", dataSource[position][3]);
        holder.orderDate.setText("Quantity : " + dataSource[position][4]);
        Log.i("DATA", dataSource[position][4]);
        holder.orderDate.setText("Company Name : " + dataSource[position][5]);
        Log.i("DATA", dataSource[position][5]);
        holder.orderDate.setText("Description : " + dataSource[position][6]);
        Log.i("DATA", dataSource[position][6]);
        //holder.orderDate.setText("Status : " + dataSource[position][7]);
        Log.i("DATA", dataSource[position][7]);
        this.position=position;
    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected TextView orderId,orderDate,duration,jobSkill,qty,companyName,desc,status;

        public ViewHolder(View itemView){
            super(itemView);
            orderId=(TextView)itemView.findViewById(R.id.orderId_tv);
            orderDate=(TextView)itemView.findViewById(R.id.orderDate_tv);
            duration=(TextView)itemView.findViewById(R.id.duration_tv);
            jobSkill=(TextView)itemView.findViewById(R.id.jobSkill);
            qty=(TextView)itemView.findViewById(R.id.qty);
            companyName=(TextView)itemView.findViewById(R.id.company_name);
            desc=(TextView)itemView.findViewById(R.id.desc);
            //status=(TextView)itemView.findViewById(R.id.status);
        }
    }
}
