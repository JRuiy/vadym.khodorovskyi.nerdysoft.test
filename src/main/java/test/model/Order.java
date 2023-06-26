package test.model;

import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Order {
    private User user;
    private List<Product> products;

    private Order(User user, List<Product> products) {
        this.user = user;
        this.products = products;
    }

    public static Order createOrder(User user, List<Product> products) {
        return new Order(user, products);
    }
}