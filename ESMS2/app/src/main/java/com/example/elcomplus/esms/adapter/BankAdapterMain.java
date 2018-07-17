package com.example.elcomplus.esms.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.elcomplus.esms.R;
import com.example.elcomplus.esms.models.Bank;

import java.util.List;

public class BankAdapterMain extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Bank> bankList;

    public BankAdapterMain(List<Bank> bankList) {
        this.bankList = bankList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank_main,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Holder holder1=(Holder) holder;
        holder1.txt_bankPhone.setText(bankList.get(position).getBankPhone());
    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView txt_bankPhone, txt_bankTotal;
        public Holder(View itemView) {
            super(itemView);
            txt_bankPhone=(TextView) itemView.findViewById(R.id.txt_bankName);
            txt_bankPhone=(TextView) itemView.findViewById(R.id.txt_totalBalance);
        }
    }


}
