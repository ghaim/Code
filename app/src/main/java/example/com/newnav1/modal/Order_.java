
package example.com.newnav1.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order_ implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("Customer_phone_no")
    @Expose
    private String customerPhoneNo;
    @SerializedName("Customer_neighborhood")
    @Expose
    private String customerNeighborhood;
    @SerializedName("Custome_location")
    @Expose
    private String customeLocation;
    @SerializedName("Seller_neighborhood")
    @Expose
    private String sellerNeighborhood;
    @SerializedName("Seller_location")
    @Expose
    private String sellerLocation;
    @SerializedName("Product_Name")
    @Expose
    private String productName;
    @SerializedName("Products_price")
    @Expose
    private String productsPrice;
    @SerializedName("Delivery_price")
    @Expose
    private String deliveryPrice;
    @SerializedName("Car_size")
    @Expose
    private String carSize;
    @SerializedName("Payment_type")
    @Expose
    private String paymentType;
    @SerializedName("Picking_time")
    @Expose
    private String pickingTime;
    @SerializedName("Delivering_time")
    @Expose
    private String deliveringTime;
    @SerializedName("catogery_id")
    @Expose
    private String catogeryId;
    @SerializedName("Delivery_man_id")
    @Expose
    private String deliveryManId;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerPhoneNo() {
        return customerPhoneNo;
    }

    public void setCustomerPhoneNo(String customerPhoneNo) {
        this.customerPhoneNo = customerPhoneNo;
    }

    public String getCustomerNeighborhood() {
        return customerNeighborhood;
    }

    public void setCustomerNeighborhood(String customerNeighborhood) {
        this.customerNeighborhood = customerNeighborhood;
    }

    public String getCustomeLocation() {
        return customeLocation;
    }

    public void setCustomeLocation(String customeLocation) {
        this.customeLocation = customeLocation;
    }

    public String getSellerNeighborhood() {
        return sellerNeighborhood;
    }

    public void setSellerNeighborhood(String sellerNeighborhood) {
        this.sellerNeighborhood = sellerNeighborhood;
    }

    public String getSellerLocation() {
        return sellerLocation;
    }

    public void setSellerLocation(String sellerLocation) {
        this.sellerLocation = sellerLocation;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductsPrice() {
        return productsPrice;
    }

    public void setProductsPrice(String productsPrice) {
        this.productsPrice = productsPrice;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getCarSize() {
        return carSize;
    }

    public void setCarSize(String carSize) {
        this.carSize = carSize;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPickingTime() {
        return pickingTime;
    }

    public void setPickingTime(String pickingTime) {
        this.pickingTime = pickingTime;
    }

    public String getDeliveringTime() {
        return deliveringTime;
    }

    public void setDeliveringTime(String deliveringTime) {
        this.deliveringTime = deliveringTime;
    }

    public String getCatogeryId() {
        return catogeryId;
    }

    public void setCatogeryId(String catogeryId) {
        this.catogeryId = catogeryId;
    }

    public String getDeliveryManId() {
        return deliveryManId;
    }

    public void setDeliveryManId(String deliveryManId) {
        this.deliveryManId = deliveryManId;
    }

}
