package com.paint.shop;

class PaintShopResult {

    private final int caseNumber;
    private final String response;

    public PaintShopResult(int caseNumber, String response) {
        this.caseNumber = caseNumber;
        this.response = response;
    }

    public int getCaseNumber() {
        return caseNumber;
    }

    public String getResponse() {
        return response;
    }
}
