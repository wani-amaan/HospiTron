package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Modern Java 0/1 Knapsack Dynamic Programming Allocator for hospital equipment and resources.
 */
public final class KnapsackAllocator {

    public record Result(int maxBenefit, List<Integer> selectedItems) {
        public int getMaxBenefit() {
            return maxBenefit;
        }

        public List<Integer> getSelectedItems() {
            return Collections.unmodifiableList(selectedItems);
        }
    }

    public int maximizeBenefit(int capacity, int[] weight, int[] value, int n) {
        validateInput(capacity, weight, value, n);
        return maximizeBenefitInternal(capacity, weight, value, n).maxBenefit();
    }

    public Result maximizeBenefitWithSelection(int capacity, int[] weight, int[] value) {
        validateInput(capacity, weight, value, weight.length);
        return maximizeBenefitInternal(capacity, weight, value, weight.length);
    }

    private Result maximizeBenefitInternal(int capacity, int[] weight, int[] value, int n) {
        var dp = new int[n + 1][capacity + 1];

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

        var selected = new ArrayList<Integer>();
        int w = capacity;

        for (int i = n; i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                selected.addFirst(i - 1); // SequencedCollection method
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