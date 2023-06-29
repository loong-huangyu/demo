package leetcode.editor.cn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RomanToIntegerTest {

    RomanToInteger.Solution solution = new RomanToInteger().new Solution();

    @Test
    void testRomanToInt() {
        int result = solution.romanToInt("s");
        Assertions.assertEquals(0, result);
    }

}
