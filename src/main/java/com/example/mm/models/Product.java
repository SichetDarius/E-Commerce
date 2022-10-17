package com.example.mm.models;

public class Product {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private int quantity = 1;

    private static Long currentProductId;

    public Product(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Product(long id, String name, Double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static Long getCurrentProductId() {
        return currentProductId;
    }

    public static void setCurrentProductId(Long id) {
        currentProductId = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
