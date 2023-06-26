package test.util;

import java.time.LocalDate;
import test.model.ProductReal;
import test.model.ProductVirtual;

public class ProductFactory {
    public static ProductReal createRealProduct(String name, double price, int size, int weight) {
        return new ProductReal(name, price, size, weight);
    }

    public static ProductVirtual createVirtualProduct(String name, double price,
                                                      String code, LocalDate expirationDate) {
        return new ProductVirtual(name, price, code, expirationDate);
    }
}