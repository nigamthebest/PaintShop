package com.paint.shop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class PaintMaker {

    public static final String IMPOSSIBLE = "IMPOSSIBLE";
    public static final String SPACE = " ";
    public static final Integer MATTE = 1;
    public static final Integer GLOSSY = 0;
    private List<PaintShopResult> paintMakerOutputList = new ArrayList();

    public String processCustomerInput(InputStream inputFileStream) {
        try (Scanner customerInputScanner = new Scanner(inputFileStream)) {
            int numberOfCases = customerInputScanner.nextInt();
            for (int caseCounter = 1; caseCounter <= numberOfCases; caseCounter++) {
                findPaintCombinationForCase(customerInputScanner, caseCounter);
            }
        }
        return createResponse(paintMakerOutputList);
    }

    private void findPaintCombinationForCase(Scanner input, int caseCounter) {
        Map<Integer, Integer> outputColorMap = new HashMap();
        int numberOfColors = input.nextInt();
        int numberOfCustomers = input.nextInt();
        input.nextLine();
        if (numberOfCustomers > numberOfColors) {
            paintMakerOutputList.add(new PaintShopResult(caseCounter, IMPOSSIBLE));
            return;
        }
        Queue<Customer> customersQueue = new ArrayDeque<>();
        int customerCount = 0;

        boolean possible = true;
        while (customerCount < numberOfCustomers) {
            customerCount++;
            final String customerPreferenceInput = input.nextLine();
            boolean matchFound = false;
            int retryCount = 0;
            while (!matchFound && possible && retryCount <= customersQueue.size()) {
                final Customer customer = new Customer(customerPreferenceInput);
                CustomerChoice bestMatchForCustomer = customer.searchBestOptionForCustomer(numberOfColors, outputColorMap);
                if (bestMatchForCustomer != null) {
                    customersQueue.add(customer);
                    matchFound = true;
                } else {

                    boolean newChoiceFound = false;

                    Customer previousCustomer = customersQueue.remove();
                    CustomerChoice previousCustomerChoice = previousCustomer.getCustomerChoice();
                    if (previousCustomer.searchBestOptionForCustomer(numberOfColors, outputColorMap) != null) {
                        outputColorMap.remove(previousCustomerChoice.getColorCode());
                        newChoiceFound = true;
                    }
                    customersQueue.add(previousCustomer);

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

    public static void main(String[] args) {
        String inputFileName = args[0];
        try {
            System.out.println(new PaintMaker().processCustomerInput(new FileInputStream(new File(inputFileName))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
