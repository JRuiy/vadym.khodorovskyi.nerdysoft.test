package test.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import test.model.Order;
import test.model.Product;
import test.model.User;

class StatisticTest {
    private static User alice;
    private static User bob;
    private static User charlie;
    private static User john;
    private static Product realProduct1;
    private static Product realProduct2;
    private static Product virtualProduct1;
    private static Product virtualProduct2;
    private static List<Order> orders;
    private static Order aliceOrder;
    private static Order bobOrder;
    private static Order charlieOrder;
    private static Order johnOrder;

    @BeforeAll
    static void beforeAll() {
        alice = User.createUser("Alice", 32);
        bob = User.createUser("Bob", 19);
        charlie = User.createUser("Charlie", 20);
        john = User.createUser("John", 27);

        realProduct1 = ProductFactory.createRealProduct("Product A",20.50,10,25);
        realProduct2 = ProductFactory.createRealProduct("Product B",50,6,17);
        virtualProduct1 = ProductFactory
                .createVirtualProduct("Product C", 100, "xxx", LocalDate.of(2023, 5, 12));
        virtualProduct2 = ProductFactory
                .createVirtualProduct("Product D", 81.25, "yyy", LocalDate.of(2024, 6, 20));

        aliceOrder = Order.createOrder(alice, List.of(realProduct1, virtualProduct1, virtualProduct2));
        bobOrder = Order.createOrder(bob, List.of(realProduct1, realProduct2));
        charlieOrder = Order.createOrder(charlie, List.of(realProduct1, virtualProduct2));
        johnOrder = Order.createOrder(john, List.of(virtualProduct1, virtualProduct2, realProduct1, realProduct2));

        orders = List.of(aliceOrder, bobOrder, charlieOrder, johnOrder);
    }

    @Test
    void getMostExpensiveProduct_emptyOrderList_notOk() {
        List<Order> emptyOrderList = Collections.emptyList();
        Assertions.assertThrows(RuntimeException.class,
                () -> Statistic.getMostExpensiveProduct(emptyOrderList),
                "Method should throw exception if list is empty");
    }

    @Test
    void getMostExpensiveProduct_validOrderList_ok() {
        Product expected = virtualProduct1;
        Product actual = Statistic.getMostExpensiveProduct(orders);

        Assertions.assertEquals(expected.getName(), actual.getName(),
                "Names of products don't match. Expected: " + expected.getName()
                        + ", actual: " + actual.getName());
        Assertions.assertEquals(expected.getPrice(), actual.getPrice(),
                "Prices of products don't match. Expected: " + expected.getPrice()
                        + ", actual: " + actual.getPrice());
    }

    @Test
    void getMostPopularProduct_emptyOrderList_notOk() {
        List<Order> emptyOrderList = Collections.emptyList();
        Assertions.assertThrows(RuntimeException.class,
                () -> Statistic.getMostPopularProduct(emptyOrderList),
                "Method should throw exception if list is empty");
    }

    @Test
    void getMostPopularProduct_validOrderList_ok() {
        Product expected = realProduct1;
        Product actual = Statistic.getMostPopularProduct(orders);

        Assertions.assertEquals(expected.getName(), actual.getName(),
                "Names of products don't match. Expected: " + expected.getName()
                        + ", actual: " + actual.getName());
        Assertions.assertEquals(expected.getPrice(), actual.getPrice(),
                "Prices of products don't match. Expected: " + expected.getPrice()
                        + ", actual: " + actual.getPrice());
    }

    @Test
    void calculateAverageAge_emptyOrderList_notOk() {
        List<Order> emptyOrderList = Collections.emptyList();
        Assertions.assertThrows(RuntimeException.class,
                () -> Statistic.calculateAverageAge(realProduct2, emptyOrderList),
                "Method should throw exception if list is empty");
    }

    @Test
    void calculateAverageAge_nullProduct_notOk() {
        List<Order> emptyOrderList = Collections.emptyList();
        Assertions.assertThrows(RuntimeException.class,
                () -> Statistic.calculateAverageAge(null, emptyOrderList),
                "Method should throw exception if product is null");
    }

    @Test
    void calculateAverageAge_nullOrderList_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> Statistic.calculateAverageAge(realProduct2, null),
                "Method should throw exception if order list is null");
    }

    @Test
    void calculateAverageAge_validValue_ok() {
        double expected = 23.0;
        double actual = Statistic.calculateAverageAge(realProduct2, orders);
        Assertions.assertEquals(expected, actual, 
                "Average age doesn't equals. Expected: " + expected + ", actual: " + actual);
    }

    @Test
    void getProductUserMap_validValue_Ok() {
        Map<Product, List<User>> expected = new HashMap<>();
        expected.put(virtualProduct2, List.of(alice, charlie, john));
        expected.put(realProduct2, List.of(bob, john));
        expected.put(realProduct1, List.of(alice, bob, charlie, john));
        expected.put(virtualProduct1, List.of(alice, john));

        Map<Product, List<User>> actual = Statistic.getProductUserMap(orders);
        Assertions.assertEquals(actual.size(), expected.size(),
                "Size of result should be equals. Expected size: "
                        + expected.size() + ", actual: " + actual.size());
        Assertions.assertEquals(actual.get(virtualProduct1), expected.get(virtualProduct1),
                "List of users for product " + virtualProduct1 + " doesn't equals");
        Assertions.assertEquals(actual.get(virtualProduct2), expected.get(virtualProduct2),
                "List of users for product " + virtualProduct2 + " doesn't equals");
        Assertions.assertEquals(actual.get(realProduct1), expected.get(realProduct1),
                "List of users for product " + realProduct1 + " doesn't equals");
        Assertions.assertEquals(actual.get(realProduct2), expected.get(realProduct2),
                "List of users for product " + realProduct2 + " doesn't equals");
    }

    @Test
    void sortProductsByPrice_validValue_ok() {
        List<Product> expected = List.of(realProduct1, realProduct2, virtualProduct2, virtualProduct1);

        List<Product> actual = Statistic.sortProductsByPrice(List.of(virtualProduct2, realProduct1,
                virtualProduct1, realProduct2));

        Assertions.assertEquals(expected.get(0), actual.get(0), "Product at index 0 doesn't equals");
        Assertions.assertEquals(expected.get(1), actual.get(1), "Product at index 1 doesn't equals");
        Assertions.assertEquals(expected.get(2), actual.get(2), "Product at index 2 doesn't equals");
        Assertions.assertEquals(expected.get(3), actual.get(3), "Product at index 3 doesn't equals");
    }

    @Test
    void sortOrdersByUserAgeDesc_validValue_ok() {
        List<Order> expected = new ArrayList<>() {{
            add(Order.createOrder(alice, List.of(realProduct1, virtualProduct1, virtualProduct2)));
            add(Order.createOrder(john, List.of(virtualProduct1, virtualProduct2, realProduct1, realProduct2)));
            add(Order.createOrder(charlie, List.of(realProduct1, virtualProduct2)));
            add(Order.createOrder(bob, List.of(realProduct1, realProduct2)));
        }};

        List<Order> actual = Statistic.sortOrdersByUserAgeDesc(orders);

        Assertions.assertEquals(expected.get(0), actual.get(0), "Order at index 0 doesn't equals");
        Assertions.assertEquals(expected.get(1), actual.get(1), "Order at index 1 doesn't equals");
        Assertions.assertEquals(expected.get(2), actual.get(2), "Order at index 2 doesn't equals");
        Assertions.assertEquals(expected.get(3), actual.get(3), "Order at index 3 doesn't equals");
    }

    @Test
    void calculateWeightOfEachOrder_validValue_ok() {
        Map<Order, Integer> expected = new HashMap<>();
        expected.put(aliceOrder, 25);
        expected.put(johnOrder, 42);
        expected.put(bobOrder, 42);
        expected.put(charlieOrder, 25);

        Map<Order, Integer> actual = Statistic.calculateWeightOfEachOrder(orders);

        Assertions.assertEquals(expected.size(), actual.size(), "Size doesn't match");
        Assertions.assertEquals(expected.get(aliceOrder), actual.get(aliceOrder),
                "Order weight for user + " + aliceOrder.getUser() + "doesn't match");
        Assertions.assertEquals(expected.get(johnOrder), actual.get(johnOrder),
                "Order weight for user + " + johnOrder.getUser() + "doesn't match");
        Assertions.assertEquals(expected.get(bobOrder), actual.get(bobOrder),
                "Order weight for user + " + bobOrder.getUser() + "doesn't match");
        Assertions.assertEquals(expected.get(charlieOrder), actual.get(charlieOrder),
                "Order weight for user + " + charlieOrder.getUser() + "doesn't match");
    }
}