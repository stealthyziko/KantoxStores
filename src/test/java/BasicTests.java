import billing.Bill;
import data.DataHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import data.Article;

public class BasicTests {
    static DataHandler dataHandler;
    @BeforeAll
    static void init() {
        dataHandler = new DataHandler("src/test/resources/articles.xml");
    }

    @Test
    void getArticle(){
        Article article = dataHandler.getByCode("GR1");
        Assertions.assertEquals("Green tea", article.getName());
        Assertions.assertEquals(3.11f, article.getPrice());
    }

    @Test
    void getSum(){
        Bill bill = new Bill(dataHandler);
        bill.add("GR1");
        bill.add("SR1");
        bill.add("CF1");
        Assertions.assertTrue(floatEqualsToTheCent(19.34f, bill.sum()));
    }

    @Test
    void getSum2(){
        Bill bill = new Bill(dataHandler);
        bill.add("GR1");
        bill.add("SR1");
        bill.add("CF1");
        bill.remove("SR1");
        Assertions.assertTrue(floatEqualsToTheCent(14.34f, bill.sum()));
    }


    @Test
    void buy1GR1(){
        Bill bill = new Bill(dataHandler);
        bill.add("GR1");
        Assertions.assertTrue(floatEqualsToTheCent(3.11f, bill.sum()));
    }

    @Test
    void buy2GR1(){
        Bill bill = new Bill(dataHandler);
        bill.add("GR1");
        bill.add("GR1");
        Assertions.assertTrue(floatEqualsToTheCent(3.11f, bill.sum()));
    }

    @Test
    void buy3GR1(){
        Bill bill = new Bill(dataHandler);
        bill.add("GR1");
        bill.add("GR1");
        bill.add("GR1");
        Assertions.assertTrue(floatEqualsToTheCent(6.22f, bill.sum()));
    }

    @Test
    void buy4GR1(){
        Bill bill = new Bill(dataHandler);
        bill.add("GR1");
        bill.add("GR1");
        bill.add("GR1");
        bill.add("GR1");
        Assertions.assertTrue(floatEqualsToTheCent(6.22f, bill.sum()));
    }

    @Test
    void buy2SR1(){
        Bill bill = new Bill(dataHandler);
        bill.add("SR1");
        bill.add("SR1");
        Assertions.assertTrue(floatEqualsToTheCent(10f, bill.sum()));
    }

    @Test
    void buy3SR1(){
        Bill bill = new Bill(dataHandler);
        bill.add("SR1");
        bill.add("SR1");
        bill.add("SR1");
        Assertions.assertTrue(floatEqualsToTheCent(13.5f, bill.sum()));
    }

    @Test
    void buy4SR1(){
        Bill bill = new Bill(dataHandler);
        bill.add("SR1");
        bill.add("SR1");
        bill.add("SR1");
        bill.add("SR1");
        Assertions.assertTrue(floatEqualsToTheCent(18f, bill.sum()));
    }

    @Test
    void buy2CF1(){
        Bill bill = new Bill(dataHandler);
        bill.add("CF1");
        bill.add("CF1");
        Assertions.assertTrue(floatEqualsToTheCent(22.46f, bill.sum()));
    }

    @Test
    void buy3CF1(){
        Bill bill = new Bill(dataHandler);
        bill.add("CF1");
        bill.add("CF1");
        bill.add("CF1");
        Assertions.assertTrue(floatEqualsToTheCent(22.46f, bill.sum()));
    }

    @Test
    void buy4CF1(){
        Bill bill = new Bill(dataHandler);
        bill.add("CF1");
        bill.add("CF1");
        bill.add("CF1");
        bill.add("CF1");
        Assertions.assertTrue(floatEqualsToTheCent(29.95f, bill.sum()));
    }

    @Test
    void buyAMix(){
        Bill bill = new Bill(dataHandler);
        bill.add("CF1");
        bill.add("SR1");
        bill.add("CF1");
        bill.add("GR1");
        bill.add("CF1");
        bill.add("SR1");
        bill.add("GR1");
        bill.add("SR1");
        Assertions.assertTrue(floatEqualsToTheCent(39.07f, bill.sum()));
    }


    @AfterAll
    static void teardown() {
        dataHandler.stopFileWatcher();
    }

    private static boolean floatEqualsToTheCent(float f1, float f2){
        return Math.abs(f1 - f2) < 0.01;
    }
}
