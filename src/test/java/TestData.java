import billing.Bill;
import data.DataHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestData {
    static DataHandler dataHandler;
    @BeforeAll
    static void init() {
        dataHandler = new DataHandler("src/test/resources/articles.xml");
    }


    @Test
    void testData1(){
        Bill bill = new Bill(dataHandler);
        bill.add("GR1");
        bill.add("SR1");
        bill.add("GR1");
        bill.add("GR1");
        bill.add("CF1");
        Assertions.assertTrue(floatEqualsToTheCent(22.45f, bill.sum()));
    }

    @Test
    void testData2(){
        Bill bill = new Bill(dataHandler);
        bill.add("GR1");
        bill.add("GR1");
        Assertions.assertTrue(floatEqualsToTheCent(3.11f, bill.sum()));
    }

    @Test
    void testData3(){
        Bill bill = new Bill(dataHandler);
        bill.add("SR1");
        bill.add("SR1");
        bill.add("GR1");
        bill.add("SR1");
        Assertions.assertTrue(floatEqualsToTheCent(16.61f, bill.sum()));
    }

    @Test
    void testData4(){
        Bill bill = new Bill(dataHandler);
        bill.add("GR1");
        bill.add("CF1");
        bill.add("SR1");
        bill.add("CF1");
        bill.add("CF1");
        Assertions.assertTrue(floatEqualsToTheCent(30.57f, bill.sum()));
    }

    @AfterAll
    static void teardown() {
        dataHandler.stopFileWatcher();
    }

    private static boolean floatEqualsToTheCent(float f1, float f2){
        return Math.abs(f1 - f2) < 0.01;
    }
}
