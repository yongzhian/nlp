package cn.zain.util;

public class SimilarityCalculationUtil {
    /**
     * pearson算法
     * @param ruleWeightArray
     * @param sentenceInfoWeightArray
     * @return 相似度值
     */
    public static double pearson(double[] ruleWeightArray, double[] sentenceInfoWeightArray){
        double sumX = 0.0;
        double sumY = 0.0;
        double sumX_Sq = 0.0;
        double sumY_Sq = 0.0;
        double sumXY = 0.0;
        int N = ruleWeightArray.length;

        for (int i = 0; i < N; i ++) {
            double p1 = ruleWeightArray[i];
            double p2 = sentenceInfoWeightArray[i];
            sumX += p1;
            sumY += p2;
            sumX_Sq += Math.pow(p1, 2);
            sumY_Sq += Math.pow(p2, 2);
            sumXY += p1 * p2;
        }

        double numerator = sumXY - sumX * sumY / N;
        double denominator = Math.sqrt((sumX_Sq - sumX * sumX / N) * (sumY_Sq - sumY * sumY / N));

        // 分母不能为0
        if (denominator == 0) {
            return 0;
        }

        return numerator / denominator;
    }
}
