package leetcode.editor.cn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SolutionTest {

    TwoSum.Solution solution = new TwoSum().new Solution();

    @Test
    void testTwoSum() {
        int[] result = solution.twoSum(new int[]{2, 7, 11, 15}, 9);
        Assertions.assertArrayEquals(new int[]{0, 1}, result);
    }

}
