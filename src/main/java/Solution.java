public class Solution {
    public static void main(String[] args) {
        int [][] edges = { { 1, 2 }, { 5, 1 }, { 1, 3 }, { 1, 4 } };
        System.out.println(findCenter(edges));
    }

    public static int findCenter(int[][] edges) {
        int firstNumber = edges[0][0];
        int secondNumber = edges[0][1];
        if(edges[1][0] == firstNumber || edges[1][1] == firstNumber){
            return firstNumber;
        }
        else return secondNumber;
    }
}
