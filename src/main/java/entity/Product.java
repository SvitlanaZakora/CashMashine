package entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    public Product(String code,String name, String capacityType, double capacity, double price) {
        this.name = name;
        this.capacityType = capacityType;
        this.capacity = capacity;
        this.price = price;
        this.code = code;
    }
}
