package sg.com.officecleanings.workwise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class SelectedPackage {
    public enum PackageType {
        WEEKLY, BI_WEEKLY
    }

    public enum HouseSize {
        TWO_ROOM, THREE_ROOM, FOUR_ROOM, FIVE_ROOM, MAISONETTE, LANDED
    }

    public enum HouseType {
        HDB, CONDO, LANDED
    }

    @Id
    private String packageId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PackageType packageType; 

    @Min(0)
    private int price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private HouseSize houseSize; // can change to enum

    @NotNull
    @Enumerated(EnumType.STRING)
    private HouseType houseType; // can change to enum

    @Min(0)
    private int hourlyRate;

    @Min(0)
    private int pax;

    @Min(0)
    private int hours;

    public SelectedPackage() {
    }

    public SelectedPackage(String packageId, PackageType packageType, int price, HouseSize houseSize, HouseType houseType, int hourlyRate,
            int pax, int hours) {
        this.packageId = packageId;
        this.packageType = packageType;
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

    public PackageType getType() {
        return packageType;
    }

    public void setType(PackageType packageType) {
        this.packageType = packageType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public HouseSize getHouseSize() {
        return houseSize;
    }

    public void setHouseSize(HouseSize houseSize) {
        this.houseSize = houseSize;
    }

    public HouseType getHouseType() {
        return houseType;
    }

    public void setHouseType(HouseType houseType) {
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
                ", packageType='" + packageType + '\'' +
                ", price=" + price +
                ", houseSize='" + houseSize + '\'' +
                ", houseType='" + houseType + '\'' +
                ", hourlyRate=" + hourlyRate +
                ", pax=" + pax +
                ", hours=" + hours +
                '}';
    }
}
