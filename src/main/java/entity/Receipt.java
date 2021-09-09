package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Receipt {
    private int id;
    private LocalDateTime creationDateTime;
    private int ownerId;
    private List<Product> products;
    private boolean active;


}
