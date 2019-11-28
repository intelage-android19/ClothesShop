package com.example.clothesshop.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clothesshop.R;
import com.example.clothesshop.interfaces.IResult;
import com.example.clothesshop.model.BookingHistoryModel;
import com.example.clothesshop.utility.SessionManager;
import com.example.clothesshop.utility.VolleyService;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.clothesshop.utility.ConstantVariables.CANCEL_BOOKING;
import static com.example.clothesshop.utility.SessionManager.KEY_ID;

public class BookHistoryAdapter extends RecyclerView.Adapter<BookHistoryAdapter.ViewHolder> {

    private Context context;
    private List<BookingHistoryModel> bookeHistoryModels;

    //Volley service
    private IResult mResultCallback;
    private VolleyService mVolleyService;

    String bookingId;
    String cancelBooking = "CancelBooking";

    SessionManager sessionManager;
    String id;
    CardView cvPgBar;

    AlertDialog alertDialog;

    TextView txtBookingname, txtBookingPrice, txtBookingGuest, txtBookingCheckin, txtCheckOut, txtCancel;
    ImageView imgCancelledStamp;

    public BookHistoryAdapter(Context context, List<BookingHistoryModel> bookingHistoryModelList) {

        this.context = context;
        this.bookeHistoryModels = bookingHistoryModelList;

    }


    public BookHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.booking_history_adapter, null);
        return new BookHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHistoryAdapter.ViewHolder viewHolder, int i) {

        BookingHistoryModel bookingHistoryModel = bookeHistoryModels.get(i);

        String imageURL = context.getResources().getString(R.string.product_image_url) + bookingHistoryModel.getRoomBanner();


        if(bookingHistoryModel.getBookingStatus().equals("Cancelled")) {

            imgCancelledStamp.setVisibility(View.VISIBLE);
            txtCancel.setVisibility(View.GONE);
            Picasso.with(context)
                    .load(imageURL)
                    .into(viewHolder.imgBookingHistory);

            txtBookingname.setText(bookingHistoryModel.getRoomType());
            txtBookingCheckin.setText(bookingHistoryModel.getCheckinDate());
            txtCheckOut.setText(bookingHistoryModel.getCheckoutDate());
            txtBookingPrice.setText("£" + String.valueOf(bookingHistoryModel.getTotalPayable()));
            txtBookingGuest.setText("Up to guest" + " " + String.valueOf(bookingHistoryModel.getUptoGuest()));

            bookingId = bookingHistoryModel.getBookingId();

        }

        else{
            Picasso.with(context)
                    .load(imageURL)
                    .into(viewHolder.imgBookingHistory);

            txtBookingname.setText(bookingHistoryModel.getRoomType());
            txtBookingCheckin.setText(bookingHistoryModel.getCheckinDate());
            txtCheckOut.setText(bookingHistoryModel.getCheckoutDate());
            txtBookingPrice.setText("£" + String.valueOf(bookingHistoryModel.getTotalPayable()));
            txtBookingGuest.setText("Up to guest" + " " + String.valueOf(bookingHistoryModel.getUptoGuest()));

            bookingId = bookingHistoryModel.getBookingId();
        }
    }


    @Override
    public int getItemCount() {
        return bookeHistoryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBookingHistory;


        public ViewHolder(View itemView) {
            super(itemView);

            imgBookingHistory = itemView.findViewById(R.id.imgbookedHistory);
            txtBookingname = itemView.findViewById(R.id.txtBookingRoom);
            txtBookingGuest = itemView.findViewById(R.id.txtBookingUpToGest);
            txtBookingCheckin = itemView.findViewById(R.id.txtCheckIn);
            txtCheckOut = itemView.findViewById(R.id.txtCheckOut);
            txtBookingPrice = itemView.findViewById(R.id.txtBookingPrice);
            txtCancel = itemView.findViewById(R.id.txtCancel);
            cvPgBar = itemView.findViewById(R.id.cvPgBar);
            imgCancelledStamp = itemView.findViewById(R.id.imgCancelledStamp);

            sessionManager = new SessionManager(context);


            txtCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogNoData();
                }
            });
        }

    }


    private void getHistory() {


        HashMap<String, String> userInfo = sessionManager.getUserDetails();

        id = userInfo.get(KEY_ID);

        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback, context);

        Map<String, String> params = new HashMap<>();
        params.put("customer_auto_id", id);
        params.put("booking_id", bookingId);

        mVolleyService.postDataVolleyParameters(CANCEL_BOOKING,
                context.getResources().getString(R.string.base_url) + cancelBooking, params);

    }


    private void dialogNoData() {


        alertDialog = new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.app_name))
                .setMessage("Are you sure cancel this booking")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getHistory();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                }).show();
        alertDialog.setCancelable(false);


    }


    private void initVolleyCallback() {

        cvPgBar.setVisibility(View.VISIBLE);

        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestId, String response) {
                JSONObject jsonObj = null;

                switch (requestId) {

                    case CANCEL_BOOKING:
                        try {
                            jsonObj = new JSONObject(response);
                            int status = jsonObj.getInt("status");
                            String message = jsonObj.getString("msg");

                            if (status == 0) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            } else if (status == 1) {
                                Toast.makeText(context, "Hotel booking has been cancelled successfully, please check your wallet", Toast.LENGTH_SHORT).show();
                                txtCancel.setVisibility(View.GONE);
                                imgCancelledStamp.setVisibility(View.VISIBLE);


                            }

                            // txtNoAmenities.setVisibility(View.VISIBLE);
                        } catch (Exception e) {

                            Log.v("Customer Activity", e.toString());
                        }

                        break;

                }
                cvPgBar.setVisibility(View.GONE);
            }

            @Override
            public void notifyError(int requestId, VolleyError error) {
                System.out.println(error);
                //   imgNoDataFound.setVisibility(View.VISIBLE);
                Toast.makeText(context, "Something went wrong. Please try again !!!", Toast.LENGTH_LONG).show();
                Log.v("Volley requestid", String.valueOf(requestId));
                Log.v("Volley Error", String.valueOf(error));
                cvPgBar.setVisibility(View.GONE);

            }
        };
    }

}

