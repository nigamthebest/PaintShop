package com.paint.shop;

import static org.junit.Assert.*;

public class PaintMakerTest {

    @org.junit.Test
    public void testSearch() throws Exception {
        final String expectedResponse = "Case #1  1 0 0 0 0\n" +
                "Case #2 IMPOSSIBLE\n";
        assertEquals(expectedResponse, new PaintMaker().processCustomerInput("paintMakerInput"));

    }
}