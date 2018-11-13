package com.calc.web;


import junit.framework.TestCase;
import static com.jayway.restassured.RestAssured.given;



public class СalculatorControllerTest extends TestCase {


    public void testAdd() throws Exception {

        assertEquals("103.0", given().when().get("http://localhost:8080/api/add?first=100&second=3").thenReturn().asString());
    }

    public void testSubtract() throws Exception {
        assertEquals("97.0", given().when().get("http://localhost:8080/api/subtract?first=100&second=3").thenReturn().asString());
    }

    public void testMultiply() throws Exception {
        assertEquals("300.0", given().when().get("http://localhost:8080/api/multiply?first=100&second=3").thenReturn().asString());
    }

    public void testDivide() throws Exception {
        assertEquals("33.3", given().when().get("http://localhost:8080/api/divide?first=100&second=3").thenReturn().asString().substring(0, 4));
    }
    boolean flag = true;
    public void testManyThreads() throws Exception {
        Thread t = new Thread(() ->{
           if(!("2.0".equals(given().when().get("http://localhost:8080/api/subtract?first=5&second=3").thenReturn().asString())&&
                    ("60.0".equals(given().when().get("http://localhost:8080/api/multiply?first=20&second=3").thenReturn().asString()))&&
                            ("12.0".equals(given().when().get("http://localhost:8080/api/add?first=9&second=3").thenReturn().asString())))) flag = false;
        });
        Thread t1 = new Thread(() ->{
            if(!("5.0".equals(given().when().get("http://localhost:8080/api/divide?first=10&second=2").thenReturn().asString())&&
                    ("240.0".equals(given().when().get("http://localhost:8080/api/multiply?first=120&second=2").thenReturn().asString()))&&
                           ("-1.0".equals(given().when().get("http://localhost:8080/api/subtract?first=1&second=2").thenReturn().asString())))) flag = false;
        });
        Thread t2 = new Thread(() ->{
            if(!("162.0".equals(given().when().get("http://localhost:8080/api/multiply?first=54&second=3").thenReturn().asString())&&
                    ("26.0".equals(given().when().get("http://localhost:8080/api/add?first=23&second=3").thenReturn().asString()))&&
                            ("3.0".equals(given().when().get("http://localhost:8080/api/divide?first=9&second=3").thenReturn().asString())))) flag = false;
        });
        Thread t3 = new Thread(() ->{
            if(!( "42.0".equals(given().when().get("http://localhost:8080/api/subtract?first=45&second=3").thenReturn().asString())&&
                    ("15.0".equals(given().when().get("http://localhost:8080/api/multiply?first=5&second=3").thenReturn().asString()))&&
                            ("4.0".equals(given().when().get("http://localhost:8080/api/divide?first=12&second=3").thenReturn().asString())))) flag = false;
        });
        t.start();
        t1.start();
        t2.start();
        t3.start();
        t.join();
        t1.join();
        t2.join();
        t3.join();

        assertEquals(true, flag);

    }

}