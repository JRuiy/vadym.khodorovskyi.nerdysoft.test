package test.model;

import java.time.LocalDate;

public class ProductVirtual extends Product {
    private String code;
    private LocalDate expirationDate;

    public ProductVirtual(String name, double price, String code, LocalDate expirationDate) {
        super(name, price);
        this.code = code;
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "ProductVirtual{"
                + super.toString()
                + ", code='" + code + '\''
                + ", expirationDate=" + expirationDate
                + '}';
    }
}