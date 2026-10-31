package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KnapsackAllocator {

    public static class Result {
        private final int maxBenefit;
        private final List<Integer> selectedItems;

        private Result(int maxBenefit, List<Integer> selectedItems) {
            this.maxBenefit = maxBenefit;
            this.selectedItems = selectedItems;
        }

        public int getMaxBenefit() {
            return maxBenefit;
        }

        public List<Integer> getSelectedItems() {
            return Collections.unmodifiableList(selectedItems);
        }
    }

    public int maximizeBenefit(int capacity, int[] weight, int[] value, int n) {
        validateInput(capacity, weight, value, n);
        return maximizeBenefitInternal(capacity, weight, value, n).getMaxBenefit();
    }

    public Result maximizeBenefitWithSelection(int capacity, int[] weight, int[] value) {
        validateInput(capacity, weight, value, weight.length);
        return maximizeBenefitInternal(capacity, weight, value, weight.length);
    }

    private Result maximizeBenefitInternal(int capacity, int[] weight, int[] value, int n) {
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (weight[i - 1] <= w) {
                    dp[i][w] = Math.max(
                            value[i - 1] + dp[i - 1][w - weight[i - 1]],
                            dp[i - 1][w]
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        ArrayList<Integer> selected = new ArrayList<>();
        int w = capacity;

        for (int i = n; i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                selected.add(0, i - 1);
                w -= weight[i - 1];
            }
        }

        return new Result(dp[n][capacity], selected);
    }

    private void validateInput(int capacity, int[] weight, int[] value, int n) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative.");
        }
        if (weight == null || value == null) {
            throw new IllegalArgumentException("Weight and value arrays cannot be null.");
        }
        if (weight.length != value.length) {
            throw new IllegalArgumentException("Weight and value arrays must have the same length.");
        }
        if (n < 0 || n > weight.length) {
            throw new IllegalArgumentException("Invalid item count.");
        }
    }
}