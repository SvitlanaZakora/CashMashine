package entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    private int id;
    private String name;
    private String capacityType;
    private double capacity;
    private double price;
    private String code;

    public Product(String name, String capacityType, String code, double capacity, double price) {
        this.name = name;
        this.capacityType = capacityType;
        this.capacity = capacity;
        this.price = price;
        this.code = code;
    }
}
