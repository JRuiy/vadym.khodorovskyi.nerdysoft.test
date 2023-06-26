package test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import test.model.Order;
import test.model.Product;
import test.model.User;
import test.util.ProductFactory;
import test.util.Statistic;
import test.util.VirtualProductCodeManager;


public class Main {
    public static void main(String[] args) {
        User user1 = User.createUser("Alice", 32);
        User user2 = User.createUser("Bob", 19);
        User user3 = User.createUser("Charlie", 20);
        User user4 = User.createUser("John", 27);

        Product realProduct1 = ProductFactory.createRealProduct("Product A",20.50,10,25);
        Product realProduct2 = ProductFactory.createRealProduct("Product B",50,6,17);

        Product virtualProduct1 = ProductFactory
                .createVirtualProduct("Product C", 100, "xxx", LocalDate.of(2023, 5, 12));
        Product virtualProduct2 = ProductFactory
                .createVirtualProduct("Product D", 81.25, "yyy", LocalDate.of(2024, 6, 20));

        List<Order> orders = new ArrayList<>() {{
                add(Order.createOrder(user1, List.of(realProduct1, virtualProduct1, virtualProduct2)));
                add(Order.createOrder(user2, List.of(realProduct1, realProduct2)));
                add(Order.createOrder(user3, List.of(realProduct1, virtualProduct2)));
                add(Order.createOrder(user4, List.of(virtualProduct1, virtualProduct2, realProduct1, realProduct2)));
            }};

        System.out.println("1. Create singleton class VirtualProductCodeManager \n");
        VirtualProductCodeManager codeManager = VirtualProductCodeManager.getInstance();
        codeManager.useCode("xxx");
        boolean isUsedX = codeManager.isCodeUsed("xxx");
        boolean isUsedY = codeManager.isCodeUsed("yyy");
        System.out.println("Is code xxx used: " + isUsedX + "\n");
        System.out.println("Is code yyy used: " + isUsedY + "\n");

        Product mostExpensive = Statistic.getMostExpensiveProduct(orders);
        System.out.println("2. Most expensive product: " + mostExpensive + "\n");

        Product mostPopular = Statistic.getMostPopularProduct(orders);
        System.out.println("3. Most popular product: " + mostPopular + "\n");

        double averageAge = Statistic.calculateAverageAge(realProduct2, orders);
        System.out.println("4. Average age is: " + averageAge + "\n");

        Map<Product, List<User>> productUserMap = Statistic.getProductUserMap(orders);
        System.out.println("5. Map with products as keys and list of users as value \n");
        productUserMap.forEach((key, value)
                -> System.out.println("key: " + key + " " + "value: " + value + "\n"));

        List<Product> productsByPrice =
                Statistic.sortProductsByPrice(List.of(realProduct1, realProduct2,
                        virtualProduct1, virtualProduct2));
        System.out.println("6. a) List of products sorted by price: " + productsByPrice + "\n");
        List<Order> ordersByUserAgeDesc = Statistic.sortOrdersByUserAgeDesc(orders);
        System.out.println("6. b) List of orders sorted by user agge in descending order: "
                + ordersByUserAgeDesc + "\n");

        Map<Order, Integer> result = Statistic.calculateWeightOfEachOrder(orders);
        System.out.println("7. Calculate the total weight of each order \n");
        result.forEach((key, value) -> System.out.println("order: " + key + " " + "total weight: "
                + value + "\n"));
    }
}
