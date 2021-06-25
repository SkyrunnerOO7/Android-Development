package com.crm.pvt.hapinicrm.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.models.Feedback;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {

    private ArrayList<Feedback> list;
    Context context;

    public FeedbackAdapter(ArrayList<Feedback> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_layout,parent,false);
        return new FeedbackAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.ViewHolder holder, int position) {

        holder.Problem.setText("Problem   :  "+list.get(position).getProblem());
        holder.Phone.setText("Phone     :  "+list.get(position).getPhone());
        holder.Remark.setText("Remark    :  "+list.get(position).getRemark());
        holder.Status.setText("Status    :  "+list.get(position).getStatus());
        holder.City.setText("City      :  "+list.get(position).getCity());
        holder.Name.setText("Name      :  "+list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Problem,Status,Name,Phone,City,Remark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Problem = itemView.findViewById(R.id.cus_pro);
            Phone = itemView.findViewById(R.id.cus_phone);
            Name = itemView.findViewById(R.id.cus_name);
            Status = itemView.findViewById(R.id.cus_status);
            Remark = itemView.findViewById(R.id.cus_remark);
            City = itemView.findViewById(R.id.cus_city);
        }
    }
}
