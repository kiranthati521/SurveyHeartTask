package com.mvvm.surveyheartcontacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;



public class SearchActivityAdapter extends RecyclerView.Adapter<SearchActivityAdapter.MYHolder> {
    private Context context;
    private ArrayList<ContactModel> dto;
    private final OnBookletClickListener listener;

    public SearchActivityAdapter(Context context, ArrayList<ContactModel> dto, OnBookletClickListener listener) {
        this.context = context;
        this.dto = dto;
        this.listener = listener;
    }

    @Override
    public MYHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_activity_adapter, parent, false);
        return new MYHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(MYHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (dto!=null && dto.size()>0)
            return dto.size();
        else
            return 0;
    }

    public class MYHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_label_one, tv_label_two, tv_label_three, tv_label_four, tv_label_five;
        private TextView tv_value_one, tv_value_two, tv_value_three, tv_value_four, tv_value_five;
        private Button btnUpdate, btnDelete;

        public MYHolder(View itemView) {
            super(itemView);
            tv_label_one = itemView.findViewById(R.id.tv_label_one);
            tv_label_two = itemView.findViewById(R.id.tv_label_two);
            tv_label_three = itemView.findViewById(R.id.tv_label_three);
            tv_label_four = itemView.findViewById(R.id.tv_label_four);
            tv_label_five = itemView.findViewById(R.id.tv_label_five);

            tv_value_one = itemView.findViewById(R.id.tv_value_one);
            tv_value_two = itemView.findViewById(R.id.tv_value_two);
            tv_value_three = itemView.findViewById(R.id.tv_value_three);
            tv_value_four = itemView.findViewById(R.id.tv_value_four);
            tv_value_five = itemView.findViewById(R.id.tv_value_five);

            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            tv_label_one.setText("Id");
            tv_label_two.setText("Name");
            tv_label_three.setText("Email");
            tv_label_four.setText("Mobile Number");
            tv_label_five.setText("Contact Type");

        }

        public void bind(int position) {
            ContactModel model = dto.get(position);
            tv_value_one.setText(model.getId()+"");
            tv_value_two.setText(model.getName());
            tv_value_three.setText(model.getEmail());
            tv_value_four.setText(model.getMobileNumber());
            tv_value_five.setText(model.getContactType());

            btnUpdate.setOnClickListener(this);
            btnUpdate.setTag(position);

            btnDelete.setOnClickListener(this);
            btnDelete.setTag(position);
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            listener.onItemClick(v, position);
        }
    }
}
