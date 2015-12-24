package com.paint.shop;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class PaintMaker {

    public static final String IMPOSSIBLE = "IMPOSSIBLE";
    public static final String SPACE = " ";
    public static final Integer MATTE = 1;
    public static final Integer GLOSSY = 0;
    private List<PaintShopResult> paintMakerOutputList = Lists.newArrayList();

    public String processCustomerInput(String filename) {

        try (Scanner customerInputScanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream(filename))) {
            int numberOfCases = customerInputScanner.nextInt();
            for (int caseCounter = 1; caseCounter <= numberOfCases; caseCounter++) {
                findPaintCombinationForCase(customerInputScanner, caseCounter);
            }
        }
        return createResponse(paintMakerOutputList);
    }

    private void findPaintCombinationForCase(Scanner input, int caseCounter) {
        Map<Integer, Integer> outputColorMap = Maps.newHashMap();
        int numberOfColors = input.nextInt();
        int numberOfCustomers = input.nextInt();
        input.nextLine();
        if (numberOfCustomers > numberOfColors) {
            paintMakerOutputList.add(new PaintShopResult(caseCounter, IMPOSSIBLE));
            return;
        }
        Queue<Customer> customersQueue = new ArrayDeque<>();
        int customerCount = 0;
        while (customerCount < numberOfCustomers) {
            customerCount++;
            final String customerPreferenceInput = input.nextLine();
            boolean matchFound = false;
            boolean possible = true;
            while (!matchFound && possible) {
                final Customer customer = new Customer(customerPreferenceInput);
                CustomerChoice bestMatchForCustomer = customer.searchBestOptionForCustomer(numberOfColors, outputColorMap);
                if (bestMatchForCustomer != null) {
                    customersQueue.add(customer);
                    matchFound = true;
                } else {
                    int retryCount = 0;
                    boolean newChoiceFound = false;
                    while (retryCount < customersQueue.size() && !newChoiceFound) {
                        retryCount++;
                        Customer previousCustomer = customersQueue.remove();
                        CustomerChoice newChoice = previousCustomer.searchBestOptionForCustomer(numberOfColors, outputColorMap);
                        if (newChoice != null) {
                            outputColorMap.remove(previousCustomer.getCustomerChoice().getColorCode());
                            newChoiceFound = true;
                        }
                        customersQueue.add(previousCustomer);

                    }
                    if (!newChoiceFound) {
                        possible = false;
                        paintMakerOutputList.add(new PaintShopResult(caseCounter, IMPOSSIBLE));
                        outputColorMap.clear();
                    }
                }
            }
        }

        if (!outputColorMap.entrySet().isEmpty())
            paintMakerOutputList.add(new PaintShopResult(caseCounter, createColorOptions(numberOfColors, outputColorMap)));
    }

    private String createResponse(List<PaintShopResult> paintMakerOutputList) {
        StringBuffer responseBuffer = new StringBuffer();
        paintMakerOutputList.stream()
                .forEach(paintShopResult -> responseBuffer.append("Case #")
                        .append(paintShopResult.getCaseNumber())
                        .append(SPACE)
                        .append(paintShopResult.getResponse())
                        .append("\n"));
        return responseBuffer.toString();
    }

    private String createColorOptions(int numberOfColors, Map<Integer, Integer> outputColorMap) {
        StringBuffer responseBuffer = new StringBuffer();
        for (int colorCounter = 1; colorCounter <= numberOfColors; colorCounter++) {
            if (outputColorMap.containsKey(colorCounter)) {
                responseBuffer.append(SPACE).append(outputColorMap.get(colorCounter));
            } else {
                responseBuffer.append(SPACE).append(GLOSSY);
            }
        }
        return responseBuffer.toString();
    }


}
