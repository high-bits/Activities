import java.util.Scanner;
import java.util.Arrays;

public class CustomerTransactionManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== RetailEdge Solutions: Customer Transaction Management System ===");
        System.out.print("Enter number of customer transactions to process: ");
        int count = scanner.nextInt();
        scanner.nextLine(); 

       
        String[] transactionIds = new String[count];
        double[] transactionAmounts = new double[count];
        double[][] itemDetails = new double[count][3]; 

        // Input Loop using Scanner (System.in)
        for (int i = 0; i < count; i++) {
            System.out.println("\n--- Customer " + (i + 1) + " ---");
            System.out.print("Enter Transaction ID (e.g., TXN101): ");
            transactionIds[i] = scanner.nextLine();

            System.out.print("Enter Total Transaction Amount ($): ");
            transactionAmounts[i] = scanner.nextDouble();

            System.out.println("Enter breakdown for 3 item categories (e.g., Apparel, Electronics, Groceries):");
            for (int j = 0; j < 3; j++) {
                System.out.print("  Category " + (j + 1) + amountPrompt(j) + ": $");
                itemDetails[i][j] = scanner.nextDouble();
            }
            scanner.nextLine(); 
        }

     
        System.out.println("\n==================================================");
        System.out.println("RAW TRANSACTION SUMMARY TABLE");
        System.out.println("==================================================");
        System.out.printf("%-15s %-15s %-12s %-12s %-12s%n", "Txn ID", "Total ($)", "Cat 1", "Cat 2", "Cat 3");
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < count; i++) {
            System.out.printf("%-15s $%-14.2f $%-11.2f $%-11.2f $%-11.2f%n",
                    transactionIds[i],
                    transactionAmounts[i],
                    itemDetails[i][0],
                    itemDetails[i][1],
                    itemDetails[i][2]);
        }

      
        System.out.println("\n==================================================");
        System.out.println("SELECT SORTING ALGORITHM");
        System.out.println("==================================================");
        System.out.println("[A] Bubble Sort");
        System.out.println("[B] Selection Sort");
        System.out.println("[C] Insertion Sort");
        System.out.println("[D] Merge Sort");
        System.out.print("Enter choice (A/B/C/D): ");
        char sortChoice = scanner.next().toUpperCase().charAt(0);

        System.out.print("Enter order - [1] Ascending or [2] Descending: ");
        int orderChoice = scanner.nextInt();
        boolean ascending = (orderChoice == 1);

        
        switch (sortChoice) {
            case 'A':
                bubbleSort(transactionAmounts, transactionIds, itemDetails, ascending);
                System.out.println("\n--- Sorted using Bubble Sort ---");
                break;
            case 'B':
                selectionSort(transactionAmounts, transactionIds, itemDetails, ascending);
                System.out.println("\n--- Sorted using Selection Sort ---");
                break;
            case 'C':
                insertionSort(transactionAmounts, transactionIds, itemDetails, ascending);
                System.out.println("\n--- Sorted using Insertion Sort ---");
                break;
            case 'D':
                mergeSortWrapper(transactionAmounts, transactionIds, itemDetails, ascending);
                System.out.println("\n--- Sorted using Merge Sort ---");
                break;
            default:
                System.out.println("Invalid selection. Defaulting to Bubble Sort ascending.");
                bubbleSort(transactionAmounts, transactionIds, itemDetails, true);
        }

       
        System.out.println("==================================================");
        System.out.println("ORGANIZED TRANSACTION SUMMARY TABLE");
        System.out.println("==================================================");
        System.out.printf("%-15s %-15s %-12s %-12s %-12s%n", "Txn ID", "Total ($)", "Cat 1", "Cat 2", "Cat 3");
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < count; i++) {
            System.out.printf("%-15s $%-14.2f $%-11.2f $%-11.2f $%-11.2f%n",
                    transactionIds[i],
                    transactionAmounts[i],
                    itemDetails[i][0],
                    itemDetails[i][1],
                    itemDetails[i][2]);
        }

        scanner.close();
    }

    private static String amountPrompt(int index) {
        if (index == 0) return " (Apparel)";
        if (index == 1) return " (Electronics)";
        return " (Groceries)";
    }

    // Helper method to swap parallel arrays (Amounts, IDs, and 2D Row Data)
    private static void swap(double[] amounts, String[] ids, double[][] items, int i, int j) {
        double tempAmount = amounts[i];
        amounts[i] = amounts[j];
        amounts[j] = tempAmount;

        String tempId = ids[i];
        ids[i] = ids[j];
        ids[j] = tempId;

        double[] tempRow = items[i];
        items[i] = items[j];
        items[j] = tempRow;
    }

   
    public static void bubbleSort(double[] amounts, String[] ids, double[][] items, boolean asc) {
        int n = amounts.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                boolean condition = asc ? (amounts[j] > amounts[j + 1]) : (amounts[j] < amounts[j + 1]);
                if (condition) {
                    swap(amounts, ids, items, j, j + 1);
                }
            }
        }
    }


    public static void selectionSort(double[] amounts, String[] ids, double[][] items, boolean asc) {
        int n = amounts.length;
        for (int i = 0; i < n - 1; i++) {
            int targetIdx = i;
            for (int j = i + 1; j < n; j++) {
                boolean condition = asc ? (amounts[j] < amounts[targetIdx]) : (amounts[j] > amounts[targetIdx]);
                if (condition) {
                    targetIdx = j;
                }
            }
            if (targetIdx != i) {
                swap(amounts, ids, items, i, targetIdx);
            }
        }
    }


    public static void insertionSort(double[] amounts, String[] ids, double[][] items, boolean asc) {
        int n = amounts.length;
        for (int i = 1; i < n; ++i) {
            double keyAmount = amounts[i];
            String keyId = ids[i];
            double[] keyRow = items[i];
            int j = i - 1;

            while (j >= 0 && (asc ? (amounts[j] > keyAmount) : (amounts[j] < keyAmount))) {
                amounts[j + 1] = amounts[j];
                ids[j + 1] = ids[j];
                items[j + 1] = items[j];
                j = j - 1;
            }
            amounts[j + 1] = keyAmount;
            ids[j + 1] = keyId;
            items[j + 1] = keyRow;
        }
    }


    public static void mergeSortWrapper(double[] amounts, String[] ids, double[][] items, boolean asc) {
        mergeSort(amounts, ids, items, 0, amounts.length - 1, asc);
    }

    private static void mergeSort(double[] amounts, String[] ids, double[][] items, int left, int right, boolean asc) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(amounts, ids, items, left, mid, asc);
            mergeSort(amounts, ids, items, mid + 1, right, asc);
            merge(amounts, ids, items, left, mid, right, asc);
        }
    }

    private static void merge(double[] amounts, String[] ids, double[][] items, int left, int mid, int right, boolean asc) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        double[] LAmounts = new double[n1];
        double[] RAmounts = new double[n2];
        String[] LIds = new String[n1];
        String[] RIds = new String[n2];
        double[][] LItems = new double[n1][3];
        double[][] RItems = new double[n2][3];

        for (int i = 0; i < n1; ++i) {
            LAmounts[i] = amounts[left + i];
            LIds[i] = ids[left + i];
            LItems[i] = items[left + i];
        }
        for (int j = 0; j < n2; ++j) {
            RAmounts[j] = amounts[mid + 1 + j];
            RIds[j] = ids[mid + 1 + j];
            RItems[j] = items[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            boolean condition = asc ? (LAmounts[i] <= RAmounts[j]) : (LAmounts[i] >= RAmounts[j]);
            if (condition) {
                amounts[k] = LAmounts[i];
                ids[k] = LIds[i];
                items[k] = LItems[i];
                i++;
            } else {
                amounts[k] = RAmounts[j];
                ids[k] = RIds[j];
                items[k] = RItems[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            amounts[k] = LAmounts[i];
            ids[k] = LIds[i];
            items[k] = LItems[i];
            i++;
            k++;
        }

        while (j < n2) {
            amounts[k] = RAmounts[j];
            ids[k] = RIds[j];
            items[k] = RItems[j];
            j++;
            k++;
        }
    }
}