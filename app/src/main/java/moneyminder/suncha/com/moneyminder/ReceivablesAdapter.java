package moneyminder.suncha.com.moneyminder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class ReceivablesAdapter extends RecyclerView.Adapter<ReceivablesAdapter.ReceivablesModelVH> {
    Context context;
    List<ReceivablesModel> receivablesModel;

    OnItemClickListener clickListener;

    public ReceivablesAdapter(Context context, List<ReceivablesModel> receivablesModel) {
        this.context = context;
        this.receivablesModel = receivablesModel;
    }

    @Override
    public ReceivablesModelVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receivablesummarylayout,parent,false);
        ReceivablesModelVH viewHolder = new ReceivablesModelVH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReceivablesModelVH holder, int position) {
        holder.nameOfMoneyTaker.setText(receivablesModel.get(position).getName());
        holder.amountOfMoneyThatTakerHasTaken.setText(receivablesModel.get(position).getLentAmount());
        holder.dateWhenTheMoneyWasLent.setText(receivablesModel.get(position).getLentDate());
    }

    @Override
    public int getItemCount() {
        return receivablesModel.size();
    }

    class ReceivablesModelVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameOfMoneyTaker, amountOfMoneyThatTakerHasTaken,dateWhenTheMoneyWasLent;

        public ReceivablesModelVH(View itemView) {
            super(itemView);
            nameOfMoneyTaker = (TextView) itemView.findViewById(R.id.NameOfMoneyTaker);
            amountOfMoneyThatTakerHasTaken = (TextView) itemView.findViewById(R.id.amountOfMoneyThatTakerHasTaken);
            dateWhenTheMoneyWasLent = (TextView) itemView.findViewById(R.id.showLentDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}
