package com.example.mm.services;

import com.example.mm.models.Product;
import com.example.mm.utils.FilterConstants;
import com.example.mm.utils.database.DatabaseQueriesNames;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.mm.models.User.getUserId;
import static com.example.mm.services.UserService.buildNewCartForCurrentUser;
import static com.example.mm.services.UserService.getCurrentCartIdForCurrentUser;
import static com.example.mm.utils.database.DatabaseQueries.getQuery;
import static com.example.mm.utils.database.DatabaseQueriesNames.*;

public class ProductsService {
    private static String connectionUrl = "jdbc:mysql://localhost:3306/ecommerce?user=root";
    private static Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;

    static {
        try {
            conn = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Product> getProducts(FilterConstants FILTER) throws SQLException {
        DatabaseQueriesNames query;
        switch (FILTER) {
            case ALL_PRODUCTS -> {
                query = GET_ALL_PRODUCTS;
                break;
            }
            case ALL_PRODUCTS_PRICE_ASC -> {
                query = GET_ALL_PRODUCTS_BY_PRICE_ASC;
                break;
            }
            case ALL_PRODUCTS_PRICE_DESC -> {
                query = GET_ALL_PRODUCTS_BY_PRICE_DESC;
                break;
            }
            default -> {
                query = null;
                break;
            }
        }
        ps = conn.prepareStatement(getQuery(query));
        rs = ps.executeQuery();
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            long id = rs.getLong("product_id");
            String name = rs.getString("product_name");
            String description = rs.getString("product_description");
            Double price = rs.getDouble("price");
            Product product = new Product(id, name, price, description);
            products.add(product);
        }
        return products;
    }

    public static List<Product> getProductsByName(String productName) throws SQLException {
        ps = conn.prepareStatement(getQuery(GET_PRODUCTS_BY_NAME));
        ps.setString(1, "%" + productName + "%");
        rs = ps.executeQuery();
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            long id = rs.getLong("product_id");
            String name = rs.getString("product_name");
            String description = rs.getString("product_description");
            Double price = rs.getDouble("price");
            Product product = new Product(id, name, price, description);
            products.add(product);
        }
        return products;
    }

    public static List<Product> getCartProductsForCurrentUser() throws SQLException {
        Long currentUserId = getUserId();
        if (currentUserId != null) {
            ps = conn.prepareStatement(String.format(getQuery(GET_CART_PRODUCTS_FOR_USER), currentUserId, getCurrentCartIdForCurrentUser()));
            rs = ps.executeQuery();
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                Long id = rs.getLong("product_id");
                int quantity = rs.getInt("quantity");
                Product product = new Product(id, quantity);
                products.add(product);
            }
            return products;
        }
        return Collections.emptyList();
    }

    /**
     * Adds product to cart.
     *
     * @param productId - the id of the product
     * @return success/failure of adding product to cart
     * @throws SQLException
     */
    public static boolean addProductToCurrentCart(Long productId) throws SQLException {
        Long currentUserId = getUserId();
        Long currentCartId = getCurrentCartIdForCurrentUser();

        if (currentUserId != null) {
            if (currentCartId != null) { // cart already exists -> check for product -> if exists -> increase quantity; else ADD_PRODUCT_TO_CART
                if (getNumberOfProductLinesInCurrentCart(productId) > 0) {
                    boolean increaseResult = incrementProductQuantityInCurrentCart(productId);
                    return increaseResult ? true : false;
                } else {
                    ps = conn.prepareStatement(String.format(getQuery(ADD_PRODUCT_TO_CART), currentCartId, productId, 1));
                    ps.execute();
                    return true;
                }
            } else {
                boolean newCartResult = buildNewCartForCurrentUser();
                if (newCartResult) {
                    currentCartId = getCurrentCartIdForCurrentUser();
                    ps = conn.prepareStatement(String.format(getQuery(ADD_PRODUCT_TO_CART), currentCartId, productId, 1));
                    ps.execute();
                    return true;
                }
            }
        }
        return false;
    }

    public static Product getProductById(Long productId) throws SQLException {
        ps = conn.prepareStatement(String.format(getQuery(GET_PRODUCT_BY_ID), productId));
        rs = ps.executeQuery();
        Product product = null;
        while (rs.next()) {
            long id = rs.getLong("product_id");
            String name = rs.getString("product_name");
            String description = rs.getString("product_description");
            Double price = rs.getDouble("price");
            product = new Product(id, name, price, description);
        }
        return product;
    }

    private static boolean incrementProductQuantityInCurrentCart(Long productId) throws SQLException {
        Long currentUserId = getUserId();
        Long currentCartId = getCurrentCartIdForCurrentUser();
        if (currentUserId != null) {
            if (currentCartId != null) {
                ps = conn.prepareStatement(String.format(getQuery(INCREASE_PRODUCT_QUANTITY_IN_CART), currentCartId, productId));
                ps.execute();
                return true;
            }
        }
        return false;
    }

    public static boolean deleteProductFromCurrentCart(Long productId) throws SQLException {
        Long currentUserId = getUserId();
        Long currentCartId = getCurrentCartIdForCurrentUser();
        if (currentUserId != null) {
            if (currentCartId != null) {
                ps = conn.prepareStatement(String.format(getQuery(DELETE_PRODUCT_FROM_CART), currentCartId, productId));
                ps.execute();
                return true;
            }
        }
        return false;
    }

    public static boolean changeProductQuantityInCurrentCart(int quantity, Long productId) throws SQLException {
        Long currentUserId = getUserId();
        Long currentCartId = getCurrentCartIdForCurrentUser();
        if (currentUserId != null) {
            if (currentCartId != null) {
                ps = conn.prepareStatement(String.format(getQuery(CHANGE_PRODUCT_QUANTITY_IN_CART), quantity, currentCartId, productId));
                ps.execute();
                return true;
            }
        }
        return false;
    }

    private static int getNumberOfProductLinesInCurrentCart(Long productId) throws SQLException {
        Long currentUserId = getUserId();
        Long currentCartId = getCurrentCartIdForCurrentUser();
        if (currentUserId != null) {
            if (currentCartId != null) {
                ps = conn.prepareStatement(String.format(getQuery(GET_NUMBER_OF_LINES_IN_CART_FOR_PRODUCT), currentCartId, productId));
                rs = ps.executeQuery();
                int result = 0;
                while (rs.next()) {
                    result = rs.getInt("COUNT(*)");
                }
                return result;
            }
        }
        return 0;
    }

}