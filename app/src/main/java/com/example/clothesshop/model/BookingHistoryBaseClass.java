package com.example.clothesshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingHistoryBaseClass {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("booking_history")
    @Expose
    private List<BookingHistoryModel> bookingHistory = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<BookingHistoryModel> getBookingHistory() {
        return bookingHistory;
    }

    public void setBookingHistory(List<BookingHistoryModel> bookingHistory) {
        this.bookingHistory = bookingHistory;
    }
}