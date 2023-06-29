//给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。 
//
// 算法的时间复杂度应该为 O(log (m+n)) 。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums1 = [1,3], nums2 = [2]
//输出：2.00000
//解释：合并数组 = [1,2,3] ，中位数 2
// 
//
// 示例 2： 
//
// 
//输入：nums1 = [1,2], nums2 = [3,4]
//输出：2.50000
//解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
// 
//
// 
//
// 
//
// 提示： 
//
// 
// nums1.length == m 
// nums2.length == n 
// 0 <= m <= 1000 
// 0 <= n <= 1000 
// 1 <= m + n <= 2000 
// -10⁶ <= nums1[i], nums2[i] <= 10⁶ 
// 
//
// Related Topics 数组 二分查找 分治 👍 6602 👎 0

package leetcode.editor.cn;

class MedianOfTwoSortedArrays {

    public static void main(String[] args) {
        Solution solution = new MedianOfTwoSortedArrays().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {

        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int lengthOne = nums1.length;
            int lengthTwo = nums2.length;
            double res = 0.0D;
            int i = 0, j = 0;
            int key = (lengthOne + lengthTwo) / 2 + 1;
            int[] resList = new int[key];
            for (int len = 0; len < key; len++) {
                int temp;
                if ((i < lengthOne && j < lengthTwo && nums1[i] < nums2[j])
                    || (i < lengthOne && j >= lengthTwo)) {
                    temp = nums1[i];
                    i = i + 1;
                } else {
                    temp = nums2[j];
                    j = j + 1;
                }
                resList[len] = temp;
            }
            if ((lengthOne + lengthTwo) % 2 == 0) {
                res = (resList[key - 1] + resList[key - 2]) / 2D;
            } else {
                res = resList[key - 1];
            }
            return res;
        }

    }
//leetcode submit region end(Prohibit modification and deletion)

}
