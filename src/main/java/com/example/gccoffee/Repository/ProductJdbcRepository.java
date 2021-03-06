package com.example.gccoffee.Repository;

import com.example.gccoffee.Model.Category;
import com.example.gccoffee.Model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.gccoffee.Utills.toLocalDate;
import static com.example.gccoffee.Utills.toUUID;

@Repository
public class ProductJdbcRepository implements ProductRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {

        return jdbcTemplate.query("select * from products", productMapper);
    }

    @Override
    public Product insert(Product product) {
        var update=jdbcTemplate.update("INSERT INTO products(product_id, product_name,category,price,description,created_at,update_at) VALUES(UUID_TO_BIN(:productId),:productName,:category,:price,:description,:createdAt,:updateAt)",toParamMap(product));
        if(update!=1){
            throw new RuntimeException("Nothing was inserted");
        }
        return product;
    }

    @Override
    public Product update(Product product) {
        var update=jdbcTemplate.update("UPDATE products SET  product_name=:productName,category=:category,price=:price," +
                "description=:description,created_at=:createdAt,update_at=:updateAt " +
                "WHERE product_id=UUID_TO_BIN(:productId)",toParamMap(product));
        if(update!=1){
            throw new RuntimeException("Nothing was update");
        }
        return product;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from products where product_id= UUID_TO_BIN(:productId)",
                    Collections.singletonMap("productId", productId.toString().getBytes()), productMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findByName(String productName) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from products where product_name=:productName",
                    Collections.singletonMap("productName", productName), productMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findbyCategory(Category category) {
        //???????????? ???????????? ????????? ????????????..???????????? ????????? ??? ????????? ?????????
       return jdbcTemplate.query("select * from products where category=:category",Collections.singletonMap("category",category.toString()),productMapper);
    }

    @Override
    public void deleteAll() {
        var update=jdbcTemplate.update("DELETE FROM products",Collections.emptyMap());

    }

    private static final RowMapper<Product> productMapper = (resultSet, i) -> {
        var productId = toUUID(resultSet.getBytes("product_id"));
        var productName = resultSet.getString("product_name");
        //????????? ???????????? ?????? ????????? ??????
        var category = Category.valueOf(resultSet.getString("category"));
        var price = resultSet.getLong("price");
        var description = resultSet.getString("description");
        var createdAt = toLocalDate(resultSet.getTimestamp("created_at"));
        var updateAt = toLocalDate(resultSet.getTimestamp("update_at"));
        return new Product(productId, productName, category, price, description, createdAt, updateAt);
    };
    //????????? ?????? ???????????? ?????????????????? ???????????? ?????? ????????? ??????..?-> ????????? ????????? ???????????? ?????? product??? null???????????? ??? ?????? ??????. ????????? ?????? ??????????????? ???????????? ????????? ?????? ?????? ????????????
    private Map<String,Object> toParamMap(Product product){

        var paramMap=new HashMap<String,Object>();
                paramMap.put("productId",product.getProductId().toString().getBytes());
                paramMap.put("productName",product.getProductName());
                paramMap.put("category",product.getCategory().toString());
                paramMap.put("price", product.getPrice());
                paramMap.put("description",product.getDescription());
                //????????? ??????????????? ?????? ???????????? ?????? ????????????
                paramMap.put("createdAt", product.getCreatedAt());
                paramMap.put("updateAt",Timestamp.valueOf(product.getUpdateAt()));
        return paramMap;

    }

}
