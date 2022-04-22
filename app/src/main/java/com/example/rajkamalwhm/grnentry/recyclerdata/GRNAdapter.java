package com.example.rajkamalwhm.grnentry.recyclerdata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rajkamalwhm.R;

import java.util.List;

public class GRNAdapter extends RecyclerView.Adapter<GRNAdapter.ViewHolder> {

    List<GRNPojo> grnPojoList;
    Context context;

    public GRNAdapter(List<GRNPojo> grnPojoList, Context context) {
        this.grnPojoList = grnPojoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GRNPojo grnPojo = grnPojoList.get(position);

        holder.itemcode.setText(grnPojo.getItemcode());
        holder.serialno.setText(grnPojo.getSerialno());
        holder.lotno.setText(grnPojo.getLotno());
        holder.qty.setText(grnPojo.getQty());
        holder.piece.setText(grnPojo.getPiece());
        holder.uniquecode.setText(grnPojo.getUniquecode());

    }

    @Override
    public int getItemCount() {
        return grnPojoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemcode,serialno,lotno,qty,piece,uniquecode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemcode = itemView.findViewById(R.id.itemcode);
            serialno = itemView.findViewById(R.id.serialno);
            lotno = itemView.findViewById(R.id.lotno);
            qty = itemView.findViewById(R.id.qty);
            piece = itemView.findViewById(R.id.piece);
            uniquecode = itemView.findViewById(R.id.uniquecode);

        }
    }
}
