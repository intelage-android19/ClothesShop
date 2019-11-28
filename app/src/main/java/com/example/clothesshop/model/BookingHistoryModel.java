package com.example.clothesshop.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class BookingHistoryModel {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("room_id")
    @Expose
    private String roomId;
    @SerializedName("room_number")
    @Expose
    private String roomNumber;
    @SerializedName("room_type")
    @Expose
    private String roomType;
    @SerializedName("room_banner")
    @Expose
    private String roomBanner;
    @SerializedName("upto_guest")
    @Expose
    private String uptoGuest;
    @SerializedName("booking_base_amount")
    @Expose
    private String bookingBaseAmount;
    @SerializedName("taxes")
    @Expose
    private String taxes;
    @SerializedName("checkin_date")
    @Expose
    private String checkinDate;
    @SerializedName("checkout_date")
    @Expose
    private String checkoutDate;
    @SerializedName("total_days")
    @Expose
    private Integer totalDays;
    @SerializedName("total_payable")
    @Expose
    private Float totalPayable;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("discount_price")
    @Expose
    private String discountPrice;
    @SerializedName("wallet_deduction")
    @Expose
    private Integer walletDeduction;
    @SerializedName("total_paid")
    @Expose
    private Float totalPaid;
    @SerializedName("advance_amount")
    @Expose
    private Float advanceAmount;
    @SerializedName("remaining_amount")
    @Expose
    private String remainingAmount;
    @SerializedName("booking_status")
    @Expose
    private String bookingStatus;
    @SerializedName("r_date")
    @Expose
    private String rDate;
    @SerializedName("cancellation_date")
    @Expose
    private String cancellationDate;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomBanner() {
        return roomBanner;
    }

    public void setRoomBanner(String roomBanner) {
        this.roomBanner = roomBanner;
    }

    public String getUptoGuest() {
        return uptoGuest;
    }

    public void setUptoGuest(String uptoGuest) {
        this.uptoGuest = uptoGuest;
    }

    public String getBookingBaseAmount() {
        return bookingBaseAmount;
    }

    public void setBookingBaseAmount(String bookingBaseAmount) {
        this.bookingBaseAmount = bookingBaseAmount;
    }

    public String getTaxes() {
        return taxes;
    }

    public void setTaxes(String taxes) {
        this.taxes = taxes;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public Float getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(Float totalPayable) {
        this.totalPayable = totalPayable;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getWalletDeduction() {
        return walletDeduction;
    }

    public void setWalletDeduction(Integer walletDeduction) {
        this.walletDeduction = walletDeduction;
    }

    public Float getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Float totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Float getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(Float advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public String getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(String remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getRDate() {
        return rDate;
    }

    public void setRDate(String rDate) {
        this.rDate = rDate;
    }

    public String getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(String cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}