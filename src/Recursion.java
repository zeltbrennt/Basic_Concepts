import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * some example recursive functions for different problems
 * use the same memoization-technique
 */
public class Recursion {

    /**
     * @param n index of Fibonacci sequence
     * @return Fibonacci number at index n
     */
    public static long fib(long n) {
        return fib(n, new HashMap<>());
    }

    private static long fib(long n, Map<Long, Long> memo) {
        //check memo
        if (memo.containsKey(n)) return memo.get(n);
        //return base case
        if (n <= 1) return 1;
        //recursive call
        memo.put(n, fib(n - 1, memo) + fib(n - 2, memo));
        //store result in memo
        return memo.get(n);
    }

    /**
     * checks if a number can be constructed from a list of other numbers
     *
     * @param n       Number to be tested
     * @param numbers numbers to choose from, any number can be chosen more than once
     * @return true, if the number can be constructed as sum of any given choices
     */
    public static boolean canSum(int n, int[] numbers) {
        return canSum(n, numbers, new HashMap<>());
    }

    private static boolean canSum(int n, int[] numbers, Map<Integer, Boolean> memo) {
        if (memo.containsKey(n)) return memo.get(n);
        if (n == 0) return true;
        if (n < 0) return false;
        for (int i : numbers) {
            int r = n - i;
            if (canSum(r, numbers, memo)) {
                memo.put(n, true);
                return true;
            }
        }
        memo.put(n, false);
        return false;
    }

    /**
     * returns any combination of numbers that make up a certain sum
     *
     * @param targetSum sum to be reached
     * @param numbers   array of numbers to chose from
     * @return either an array of a possible solution, or null
     */
    public static List<Integer> howSum(int targetSum, int[] numbers) {
        return howSum(targetSum, numbers, new HashMap<>());
    }

    private static List<Integer> howSum(int targetSum, int[] numbers, Map<Integer, List<Integer>> memo) {
        if (memo.containsKey(targetSum)) return memo.get(targetSum);
        if (targetSum == 0) return new ArrayList<>();
        if (targetSum < 0) return null;
        for (int num : numbers) {
            int remainder = targetSum - num;
            List<Integer> remainderResult = howSum(remainder, numbers, memo);
            if (remainderResult != null) {
                remainderResult.add(num);
                memo.put(targetSum, new ArrayList<>(remainderResult));
                return remainderResult;
            }
        }
        memo.put(targetSum, null);
        return null;
    }

    /**
     * calculates the optimal combination, that means, with the least amount of elements, of numbers to get a certain sum.
     *
     * @param targetSum target
     * @param numbers   numbers
     * @return array of numbers or null
     */
    public static List<Integer> bestSum(int targetSum, int[] numbers) {
        return bestSum(targetSum, numbers, new HashMap<>());
    }

    private static List<Integer> bestSum(int targetSum, int[] numbers, Map<Integer, List<Integer>> memo) {
        if (memo.containsKey(targetSum)) return memo.get(targetSum);
        if (targetSum == 0) return new ArrayList<>();
        if (targetSum < 0) return null;
        List<Integer> shortestCombo = null;
        for (int number : numbers) {
            int remainder = targetSum - number;
            List<Integer> remainderCombo = bestSum(remainder, numbers, memo);
            if (remainderCombo != null) {
                List<Integer> combo = new ArrayList<>(remainderCombo);
                combo.add(number);
                if (shortestCombo == null || combo.size() < shortestCombo.size()) {
                    shortestCombo = new ArrayList<>(combo);
                }
            }
        }
        memo.put(targetSum, shortestCombo);
        return shortestCombo;
    }

    public static boolean canConstruct(String target, String[] wordBank) {
        return canConstruct(target, wordBank, new HashMap<>());
    }

    private static boolean canConstruct(String target, String[] wordBank, Map<String, Boolean> memo) {
        if (memo.containsKey(target)) return memo.get(target);
        if (target.equals("")) return true;
        for (String prefix : wordBank) {
            if (target.indexOf(prefix) == 0) {
                String suffix = target.substring(prefix.length());
                if (canConstruct(suffix, wordBank, memo)) {
                    memo.put(target, true);
                    return true;
                }
            }
        }
        memo.put(target, false);
        return false;
    }

    /**
     * checks if a word can be constructed from a list of substrings. substrings can be reused
     *
     * @param target   word to construct
     * @param wordBank list of words
     * @return true, if word can be constructed
     */
    public static int countConstruct(String target, String[] wordBank) {
        return countConstruct(target, wordBank, new HashMap<>());
    }

    private static int countConstruct(String target, String[] wordBank, Map<String, Integer> memo) {
        if (memo.containsKey(target)) return memo.get(target);
        if (target.equals("")) return 1;
        int total = 0;
        for (String prefix : wordBank) {
            if (target.indexOf(prefix) == 0) {
                int temp = countConstruct(target.substring(prefix.length()), wordBank, memo);
                total += temp;
            }
        }
        memo.put(target, total);
        return total;
    }

    /**
     * Given a target string and a list of substrings, checks if target string can be constructed by the substrings and
     *
     * @param target   target string
     * @param wordBank list of substrings
     * @return 2D-ArrayList with substring combinations or empty ArrayList
     */
    public static List<List<String>> allConstruct(String target, String[] wordBank) {
        return allConstruct(target, wordBank, new HashMap<>());
    }

    private static List<List<String>> allConstruct(String target, String[] wordBank, Map<String, List<List<String>>> memo) {
        if (memo.containsKey(target)) return memo.get(target);
        if (target.equals("")) return List.of(new ArrayList<>());
        List<List<String>> result = new ArrayList<>();
        for (String word : wordBank) {
            if (target.indexOf(word) == 0) {
                String suffix = target.substring(word.length());
                List<List<String>> suffixCombo = allConstruct(suffix, wordBank, memo);
                List<List<String>> targetCombo = suffixCombo.stream().map(ArrayList::new).collect(Collectors.toList());
                targetCombo.forEach(combo -> combo.add(0, word));
                result.addAll(targetCombo);
            }
        }
        memo.put(target, result);
        return result;
    }

}
