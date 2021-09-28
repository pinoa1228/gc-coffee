package com.example.gccoffee.Repository;

import com.example.gccoffee.Model.Category;
import com.example.gccoffee.Model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.view.script.ScriptTemplateViewResolver;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.config.MysqldConfig.*;

//스프링 부트 컨피규레이션 찾아준다.
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//테스트 야물을 읽어올 수 있다.
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductJdbcRepositoryTest {
    static EmbeddedMysql embeddedMysql;


    @BeforeAll
    static void setup(){

     var config=aMysqldConfig(Version.v8_0_11)
        .withCharset(Charset.UTF8)
        .withPort(2215)
        .withUser("test","test1234!")
        .withTimeZone("Asia/Seoul")
        .build();
       embeddedMysql=anEmbeddedMysql(config)
               .addSchema("test-order-mgmt", ScriptResolver.classPathScript("schema.sql"))
               .start();
    }

    @AfterAll
    static  void cleanup(){
        embeddedMysql.stop();
    }

    @Autowired
    ProductRepository repository;

    //계속해서 랜덤 아이디가 추가 되고 있다->static으로 해줘야지 매번 인스턴스가 새로 만들어 지지 않는다. 하나의 인스턴스를 주어 사용되도록 해야한다
    // -> 지금  테스트 인스턴스 단위를 지정해 두지 않아서 method 단위로 실행 그래서 계속해서 인스턴스가 만들어지고 있다.-> 클래스단위로 바꿔주면 문제가 없을지도
    private  final Product newProduct= new Product(UUID.randomUUID(),"newcoffee", Category.Coffee_Bean_Package,1000L);

    @Test
    @Order(1)
    @DisplayName("상품을 추가할 수 있다")
    public void testInsert(){
        repository.insert(newProduct);
        var all=repository.findAll();
        assertThat(all.isEmpty(),is(false));

    }


    @Test
    @Order(2)
    @DisplayName("상품을 이름으로 조회할 수 있다")
    public void testfindbyname(){
        var find=repository.findByName(newProduct.getProductName());
        assertThat(find.isEmpty(),is(false));
//        assertThat(find.get(),is(samePropertyValuesAs(newProduct)));

    }
    @Test
    @Order(3)
    @DisplayName("상품을 id으로 조회할 수 있다")
    public void testfindbyId(){
        var find=repository.findById(newProduct.getProductId());
        assertThat(find.isEmpty(),is(false));
//        assertThat(find.get(),is(samePropertyValuesAs(newProduct)));

    }
    @Test
    @Order(4)
    @DisplayName("상품을 카테고리로 조회할 수 있다")
    public void testfindbycategory(){
        var find=repository.findbyCategory(newProduct.getCategory());
        assertThat(find.isEmpty(),is(false));
//        assertThat(find.get(),is(samePropertyValuesAs(newProduct)));

    }
    @Test
    @Order(5)
    @DisplayName("상품을  수정할 수 있다.")
    public void testupdate(){
        newProduct.setProductName("update-product");
        repository.update(newProduct);
        var all=repository.findById(newProduct.getProductId());
        assertThat(all.isEmpty(),is(false));
        assertThat(all.get(), is(samePropertyValuesAs(newProduct)));
    }
    @Test
    @Order(6)
    @DisplayName("상품을 전체 삭제한다")
    public void testdelete(){
        repository.deleteAll();
       var all=repository.findAll();
       assertThat(all.isEmpty(),is(true));
    }


}