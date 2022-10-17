package com.example.mm.utils.database;

import java.util.Map;

import static com.example.mm.utils.database.DatabaseQueriesNames.*;

/**
 * Map for holding all the necessary DB queries.
 * Used in ... for obtaining the queries,
 * and then interpolating them with the necessary data, before sending them to the DB.
 */
public class DatabaseQueries {
    private static Map<DatabaseQueriesNames, String> queries = Map.ofEntries(
            Map.entry(GET_ALL_PRODUCTS, "SELECT * FROM products"),
            Map.entry(GET_ALL_USERS, "SELECT * FROM users"),
            Map.entry(GET_USERID_FROM_USERNAME, "SELECT user_id FROM users WHERE username='%s'"),
            Map.entry(GET_ALL_PRODUCTS_BY_PRICE_ASC, "SELECT * FROM products ORDER BY price ASC"),
            Map.entry(GET_ALL_PRODUCTS_BY_PRICE_DESC, "SELECT * FROM products ORDER BY price DESC"),
            Map.entry(GET_USER_ID_AND_PASSWORD_FROM_USERNAME, "SELECT user_id, password FROM users WHERE user_name='%s'"),
            Map.entry(BUILD_USER_FROM_USERNAME_AND_PASSWORD, "INSERT INTO users(user_name, password) VALUES('%s', '%s')"),
            Map.entry(GET_PRODUCTS_BY_NAME, "SELECT * FROM products WHERE product_name LIKE ?"),
            Map.entry(GET_CART_PRODUCTS_FOR_USER, "SELECT product_id, quantity FROM carts_details JOIN carts c on c.cart_id = carts_details.cart_id JOIN ecommerce.users u on c.user_id = u.user_id WHERE c.user_id=%d AND c.cart_id=%d"),
            Map.entry(GET_LAST_CART_ID_FOR_USER, "SELECT cart_id FROM carts WHERE user_id='%s' ORDER BY cart_id DESC LIMIT 1"),
            Map.entry(BUILD_USER_INVOICE, "INSERT INTO invoices(cart_id, name, address, phone) VALUES(%d, '%s', '%s', '%s')"),
            Map.entry(BUILD_NEW_CART_FOR_USER, "INSERT INTO carts(user_id) VALUES(%d)"),
            Map.entry(INCREASE_PRODUCT_QUANTITY_IN_CART, "UPDATE carts_details SET quantity=quantity+1 WHERE cart_id=%d AND product_id=%d"),
            Map.entry(DELETE_PRODUCT_FROM_CART, "DELETE FROM carts_details WHERE cart_id=%d AND product_id=%d"),
            Map.entry(ADD_PRODUCT_TO_CART, "INSERT INTO carts_details(cart_id, product_id, quantity) VALUES(%d, %d, %d)"),
            Map.entry(GET_PRODUCT_BY_ID, "SELECT * FROM products WHERE product_id=%d LIMIT 1"),
            Map.entry(CHANGE_PRODUCT_QUANTITY_IN_CART, "UPDATE carts_details SET quantity=%d WHERE cart_id=%d AND product_id=%d"),
            Map.entry(GET_NUMBER_OF_LINES_IN_CART_FOR_PRODUCT, "SELECT COUNT(*) FROM ecommerce.carts_details WHERE cart_id=%d AND product_id=%d")
    );

    public static String getQuery(DatabaseQueriesNames databaseQueriesName) {
        return queries.get(databaseQueriesName);
    }
}

