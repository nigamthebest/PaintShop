package com.paint.shop;

class CustomerChoice {

    private final int colorCode;
    private final int finishCode;

    public CustomerChoice(int colorCode, int finishCode) {
        this.colorCode = colorCode;
        this.finishCode = finishCode;
    }

    public int getColorCode() {
        return colorCode;
    }

    public int getFinishCode() {
        return finishCode;
    }
}
