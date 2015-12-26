package com.paint.shop;

import static org.junit.Assert.*;

public class PaintMakerTest {

    @org.junit.Test
    public void testProcessCustomerInput() throws Exception {
        final String expectedResponse = "Case #1  1 0 0 0 0\n" +
                "Case #2 IMPOSSIBLE\n";
        assertEquals(expectedResponse, new PaintMaker().processCustomerInput("paintMakerInput"));

    }

    @org.junit.Test
    public void testProcessCustomerInputWithMultipleRollBacks() throws Exception {
        final String expectedResponse = "Case #1  1 0 1\n"+
                "Case #2  1 0 1\n";
        assertEquals(expectedResponse, new PaintMaker().processCustomerInput("multipleRollBacksPaintMakerInput"));

    }

    @org.junit.Test
    public void testProcessCustomerInputWithImpossibleInput() throws Exception {
        final String expectedResponse = "Case #1 IMPOSSIBLE\n";
        assertEquals(expectedResponse, new PaintMaker().processCustomerInput("ImpossiblePaintMakerInput"));

    }
}