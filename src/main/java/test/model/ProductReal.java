package test.model;

import lombok.Getter;

@Getter
public class ProductReal extends Product {
    private int size;
    private int weight;

    public ProductReal(String name, double price, int size, int weight) {
        super(name, price);
        this.size = size;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "ProductReal{"
                + super.toString()
                + ", size=" + size
                + ", weight=" + weight
                + '}';
    }
}