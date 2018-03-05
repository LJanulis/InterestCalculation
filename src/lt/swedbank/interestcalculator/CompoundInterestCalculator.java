package lt.swedbank.interestcalculator;

import java.util.Scanner;

public class CompoundInterestCalculator {

    private static double principalAmount;
    private static int periodLengthYears;
    private static char compoundFrequencyCode;

    private static int[] interestRates = {};
    private static int interestRateCount;

    private static double[][] interestAmountTable;

    public static void main(String[] args) {
        readInput();
        int compoundFrequency = getCompoundingFrequency(compoundFrequencyCode);
        interestAmountTable = new double[interestRateCount][periodLengthYears * compoundFrequency];

        generateIntermediateAmountTable(compoundFrequency);

        displayIntermediateAmountTable();
    }

    private static void generateIntermediateAmountTable(int compoundFrequency) {
        for (int row = 0; row < interestRateCount; ++row) {
            int col = 0;
            for (int i = 1; i <= periodLengthYears * compoundFrequency; i++) {
                double interestRate = (double) interestRates[row] / 100;
                double calculatedInterestAmount = calculateAdditiveInterest(i, compoundFrequency, interestRate);
                double prevInterest = calculateAdditiveInterest(i - 1, compoundFrequency, interestRate);
                interestAmountTable[row][col] = calculatedInterestAmount - prevInterest;
                col++;
            }
        }
    }

    private static double calculateAdditiveInterest(int year, int compoundFrequency, double interestRatePercent) {
        return principalAmount * Math.pow((1 + interestRatePercent / compoundFrequency), year);
    }

    private static int getCompoundingFrequency(char compoundFrequencyCode) {
        compoundFrequencyCode = Character.toUpperCase(compoundFrequencyCode);
        switch (compoundFrequencyCode) {
            case 'D':
                return 365;
            case 'W':
                return 52;
            case 'M':
                return 12;
            case 'Q':
                return 4;
            case 'H':
                return 2;
            case 'Y':
                return 1;
            default:
                return 1;
        }
    }

    private static void displayIntermediateAmountTable() {
        for (double[] row : interestAmountTable) {
            for (double interest : row) {
                System.out.printf("%.2f ", interest);
            }
            System.out.println();
        }
    }

    private static void readInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter original(principal) amount: ");
        principalAmount = getPrincipalAmountInput(scanner);
        int interestRate;
        do {
            System.out.println("Enter interest rate (%): ");
            interestRate = getInterestRateInput(scanner);
            int[] interestRatesExpanded = new int[interestRates.length + 1];
            System.arraycopy(interestRates, 0, interestRatesExpanded, 0, interestRates.length);
            interestRates = interestRatesExpanded;
            interestRatesExpanded[interestRatesExpanded.length - 1] = interestRate;
        } while (interestRate != 0);
        interestRateCount = interestRates.length - 1;

        System.out.println("Enter period length (years): ");
        periodLengthYears = getPeriodLengthInput(scanner);
        System.out.println("Enter compound frequency: ");
        compoundFrequencyCode = getCompoundFrequencyCodeInput(scanner);
        scanner.close();
    }

    private static double getPrincipalAmountInput(Scanner scanner) {
        while (true) {
            try {
                double principalAmount = Double.parseDouble(scanner.nextLine());
                if (principalAmount <= 0) {
                    System.out.println("Invalid input! Try again");
                } else {
                    return principalAmount;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input! Try again");
            }
        }
    }

    private static int getInterestRateInput(Scanner scanner) {
        while (true) {
            try {
                int interestRate = Integer.parseInt(scanner.nextLine());
                if (interestRate < 0 || interestRate > 100) {
                    System.out.println("Invalid input! Try again");
                } else {
                    return interestRate;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input! Try again");
            }
        }
    }

    private static int getPeriodLengthInput(Scanner scanner) {
        while (true) {
            try {
                int periodLength = Integer.parseInt(scanner.nextLine());
                if (periodLength <= 0) {
                    System.out.println("Invalid input! Try again");
                } else {
                    return periodLength;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input! Try again");
            }
        }
    }

    private static char getCompoundFrequencyCodeInput(Scanner scanner) {
        while (true) {
            try {
                String input = scanner.nextLine();
                if (input.length() > 1 || input.length() == 0) {
                    System.out.println("Invalid input! Try again");
                } else {
                    char inputChar = input.charAt(0);
                    if ((getCompoundingFrequency(inputChar) == 1) && (inputChar != 'Y')) {
                        System.out.println("Invalid input! Try again");
                    } else {
                        return inputChar;
                    }
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input! Try again");
            }
        }
    }
}
