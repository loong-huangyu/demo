package leetcode.editor.cn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LongestSubstringWithoutRepeatingCharactersTest {

    LongestSubstringWithoutRepeatingCharacters.Solution solution =
        new LongestSubstringWithoutRepeatingCharacters().new Solution();
    //leetcode submit region begin(Prohibit modification and deletion)
    @Test
    void testRomanToInt() {
        int result = solution.lengthOfLongestSubstring("au");
        Assertions.assertEquals(1, result);
    }

}