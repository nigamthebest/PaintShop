package com.paint.shop;

import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

class Customer {

    private final String customerPreferencePairs;
    private CustomerChoice customerChoice;

    public Customer(String customerPreferencePairs) {
        this.customerPreferencePairs = customerPreferencePairs;
        this.customerChoice = null;
    }

    public CustomerChoice getCustomerChoice() {
        return customerChoice;
    }

    public CustomerChoice searchBestOptionForCustomer(int numberOfColors, Map<Integer, Integer> outputColorMap, boolean firstPass) {
        Scanner preferenceInputLineScanner = new Scanner(customerPreferencePairs).useDelimiter(PaintMaker.SPACE);
        int numberOfOptions = preferenceInputLineScanner.nextInt();
        Stack<CustomerChoice> customerChoices = new Stack<>();
        for (int k = 0; k < numberOfOptions; k++) {
            int colorCode = preferenceInputLineScanner.nextInt();
            int finishCode = preferenceInputLineScanner.nextInt();
            validateColorCode(colorCode, numberOfColors);
            validateFinishCode(finishCode);
            if (!outputColorMap.containsKey(colorCode) && PaintMaker.GLOSSY.equals(finishCode)) {
                outputColorMap.put(colorCode, PaintMaker.GLOSSY);
                this.customerChoice = new CustomerChoice(colorCode, PaintMaker.GLOSSY);
                return this.customerChoice;
            }else if(outputColorMap.containsKey(colorCode) && outputColorMap.get(colorCode).equals(finishCode) && firstPass){
                this.customerChoice = new CustomerChoice(colorCode, finishCode);
                return this.customerChoice;
            }
            customerChoices.push(new CustomerChoice(colorCode, finishCode));
        }
        while (!customerChoices.isEmpty()) {
            CustomerChoice customerChoice = customerChoices.pop();
            if (!outputColorMap.containsKey(customerChoice.getColorCode())) {
                outputColorMap.put(customerChoice.getColorCode(), customerChoice.getFinishCode());
                this.customerChoice = new CustomerChoice(customerChoice.getColorCode(), customerChoice.getFinishCode());
                return this.customerChoice;
            }
        }
        return null;

    }

    private void validateColorCode(int colorCode, int numberOfColors) {
        if (numberOfColors < colorCode) {
            throw new InvalidInputException("invalid colorCode " + colorCode);
        }
    }

    private void validateFinishCode(int finishCode) {
        if (!(PaintMaker.GLOSSY.equals(finishCode) || PaintMaker.MATTE.equals(finishCode))) {
            throw new InvalidInputException("invalid finishCode " + finishCode);
        }
    }
}
