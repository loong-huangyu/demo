//给你一个字符串 s，找到 s 中最长的回文子串。 
//
// 如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "babad"
//输出："bab"
//解释："aba" 同样是符合题意的答案。
// 
//
// 示例 2： 
//
// 
//输入：s = "cbbd"
//输出："bb"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 1000 
// s 仅由数字和英文字母组成 
// 
//
// Related Topics 字符串 动态规划 👍 6590 👎 0

package leetcode.editor.cn;

class LongestPalindromicSubstring {

    public static void main(String[] args) {
        Solution solution = new LongestPalindromicSubstring().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {

        public String longestPalindrome(String s) {
            if (s == null || s.length() == 0) {
                return "";
            }
            int[] range = new int[2];
            char[] str = s.toCharArray();
            for (int i = 0; i < str.length; i++) {
                i = findLongest(str, i, range);
            }
            return s.substring(range[0], range[1] + 1);
        }

        private int findLongest(char[] str, int low, int[] range) {
            //         查找中间部分
            int high = low;
            while (high < str.length - 1 && str[high + 1] == str[low]) {
                high++;
            }
            //         定位中间部分的最后一个字符
            int ans = high;
            //         从中间向左右扩散
            while (low > 0 && high < str.length - 1 && str[low - 1] == str[high + 1]) {
                low--;
                high++;
            }
            //         记录最大长度
            if (high - low > range[1] - range[0]) {
                range[0] = low;
                range[1] = high;
            }
            return ans;
        }

    }
//leetcode submit region end(Prohibit modification and deletion)

}
