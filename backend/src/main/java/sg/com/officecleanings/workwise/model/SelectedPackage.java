package sg.com.officecleanings.workwise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class SelectedPackage {
    @Id
    private String packageId;

    @NotNull
    private String type; // can change to enum

    @Min(0)
    private int price;

    @NotNull
    private String houseSize; // can change to enum

    @NotNull
    private String houseType; // can change to enum

    @Min(0)
    private int hourlyRate;

    @Min(0)
    private int pax;

    @Min(0)
    private int hours;

    public SelectedPackage() {
    }

    public SelectedPackage(String packageId, String type, int price, String houseSize, String houseType, int hourlyRate,
            int pax, int hours) {
        this.packageId = packageId;
        this.type = type;
        this.price = price;
        this.houseSize = houseSize;
        this.houseType = houseType;
        this.hourlyRate = hourlyRate;
        this.pax = pax;
        this.hours = hours;
    }

    // Getters and Setters
    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getHouseSize() {
        return houseSize;
    }

    public void setHouseSize(String houseSize) {
        this.houseSize = houseSize;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getPax() {
        return pax;
    }

    public void setPax(int pax) {
        this.pax = pax;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "SelectedPackage{" +
                "packageId='" + packageId + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", houseSize='" + houseSize + '\'' +
                ", houseType='" + houseType + '\'' +
                ", hourlyRate=" + hourlyRate +
                ", pax=" + pax +
                ", hours=" + hours +
                '}';
    }
}
