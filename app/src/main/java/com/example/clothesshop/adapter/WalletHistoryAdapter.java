package com.example.clothesshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clothesshop.R;
import com.example.clothesshop.activity.WalletActivity;
import com.example.clothesshop.model.WalletHistorymodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.ViewHolder>{

    private Context context;
    private List<WalletHistorymodel> walletHistorymodel;

    Date dt_1;

    public WalletHistoryAdapter(WalletActivity context, List<WalletHistorymodel> walletHistorymodelList) {

        this.context = context;
        this.walletHistorymodel = walletHistorymodelList;
    }


    public WalletHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.wallet_history_adapter, null);
        return new WalletHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletHistoryAdapter.ViewHolder viewHolder, int i) {

        SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-mm-dd");
        try {
            dt_1 = objSDF.parse(walletHistorymodel.get(i).getRdate());
            System.out.println(dt_1);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (walletHistorymodel.get(i).getReferenceId().equals("") && walletHistorymodel.get(i).getName().equals("") &&
                walletHistorymodel.get(i).getCountryCode().equals("") && walletHistorymodel.get(i).getContact().equals("")
                && walletHistorymodel.get(i).getStatus().equals("credit")){

           // viewHolder.imgWalletHistory.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
            viewHolder.txtHistoryName.setText("System Credited");
            viewHolder.txtHistoryNumber.setText("System Credited");
            viewHolder.txtHistoryDate.setText(walletHistorymodel.get(i).getRdate());
            viewHolder.txtHistoryPrice.setText("₹"+ " " +walletHistorymodel.get(i).getTransactionAmount());
            viewHolder.txtHistoryStatus.setText(walletHistorymodel.get(i).getStatus());

        }
        else  if(walletHistorymodel.get(i).getStatus().equals("credit")){

          //  viewHolder.imgWalletHistory.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
            viewHolder.txtHistoryName.setText("System Credited");
            viewHolder.txtHistoryNumber.setText("System Credited");
            viewHolder.txtHistoryDate.setText(walletHistorymodel.get(i).getRdate());
            viewHolder.txtHistoryPrice.setText("₹"+ " " +walletHistorymodel.get(i).getTransactionAmount());
            viewHolder.txtHistoryStatus.setText(walletHistorymodel.get(i).getStatus());


        }
        else if (walletHistorymodel.get(i).getStatus().equals("debit")) {
           // viewHolder.imgWalletHistory.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
            viewHolder.txtHistoryName.setText(walletHistorymodel.get(i).getName());
            viewHolder.txtHistoryNumber.setText(walletHistorymodel.get(i).getContact());
            viewHolder.txtHistoryDate.setText(walletHistorymodel.get(i).getRdate());
            viewHolder.txtHistoryPrice.setText("₹"+ " " +walletHistorymodel.get(i).getTransactionAmount());
            viewHolder.txtHistoryStatus.setText(walletHistorymodel.get(i).getStatus());
        }

    }


    @Override
    public int getItemCount() {
        return walletHistorymodel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgWalletHistory;
        TextView txtHistoryName,txtHistoryNumber,txtHistoryDate,txtHistoryPrice,txtHistoryStatus;


        public ViewHolder(View itemView) {
            super(itemView);

            imgWalletHistory = itemView.findViewById(R.id.imgwallet);
            txtHistoryName = itemView.findViewById(R.id.txtHistoryName);
            txtHistoryNumber = itemView.findViewById(R.id.txtHistoryNo);
            txtHistoryDate = itemView.findViewById(R.id.txtHistoryDate);
            txtHistoryPrice = itemView.findViewById(R.id.txtHistoryPrice);
            txtHistoryStatus = itemView.findViewById(R.id.txtHistoryStatus);



        }
    }
}
