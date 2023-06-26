package test.util;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import test.model.Order;
import test.model.Product;
import test.model.ProductReal;
import test.model.User;


public class Statistic {
    public static Product getMostExpensiveProduct(List<Order> orders) {
        return orders.stream()
                .flatMap(o -> o.getProducts().stream())
                .max(Comparator.comparingDouble(Product::getPrice))
                .orElseThrow(() -> new RuntimeException("No one order present"));
    }

    public static Product getMostPopularProduct(List<Order> orders) {
        Map<Product, Long> countedProducts = orders.stream()
                .flatMap(o -> o.getProducts().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return countedProducts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new RuntimeException("No one order present"))
                .getKey();
    }

    public static double calculateAverageAge(Product product, List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getProducts().contains(product))
                .map(Order::getUser)
                .mapToInt(User::getAge)
                .average()
                .orElseThrow(() -> new RuntimeException("Can't calculate average age for product "
                        + product + " and orders " + orders));
    }

    public static Map<Product, List<User>> getProductUserMap(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getProducts().stream()
                        .map(product -> new AbstractMap.SimpleEntry<>(product, order.getUser())))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }

    public static List<Product> sortProductsByPrice(List<Product> products) {
        return products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
    }

    public static List<Order> sortOrdersByUserAgeDesc(List<Order> orders) {
        Comparator<Order> comparator = Comparator.comparingInt(o -> o.getUser().getAge());
        return orders.stream()
                .sorted(comparator.reversed())
                .collect(Collectors.toList());
    }

    public static Map<Order, Integer> calculateWeightOfEachOrder(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.toMap(Function.identity(), o -> o.getProducts().stream()
                        .filter(p -> p instanceof ProductReal)
                        .mapToInt(p -> ((ProductReal) p).getWeight())
                        .sum()));
    }
}
