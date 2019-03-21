package com.foutin.floyd.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Foyd算法--生成map存到redis；
 * Created by lin.yang on 2017-07-11.
 */
public class FloydIngraphAlgorithm {
    //顶点i 到 j的最短路径长度，初值是i到j的边的权重
    private int[][] path;
    //路径矩阵
    private List<Integer> result = new ArrayList<Integer>();

    private final static double INF = 100000000d;
    /**
     * 点到点的距离矩阵
     */
    private double[][] dist;
    /**
     * 点到点的路径三维矩阵
     */
    private int[][][] pathMatrix;

    public double[][] getDist() {
        return dist;
    }

    public void setDist(double[][] dist) {
        this.dist = dist;
    }

    public int[][][] getPathMatrix() {
        return pathMatrix;
    }

    public void setPathMatrix(int[][][] pathMatrix) {
        this.pathMatrix = pathMatrix;
    }

    public int[][] getPath() {
        return path;
    }

    public void setPath(int[][] path) {
        this.path = path;
    }

    /**
     * 没有考虑单行道，双行道问题，
     *
     * @param disMatrixFloyd
     * @return
     */
    public double[][] dist(double[][] disMatrixFloyd) {
        int length = disMatrixFloyd.length;
        //点A到点B所需要进过的路径点的索引
        pathMatrix = new int[length][length][];
        dist = floyd(disMatrixFloyd);

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (i == j) {
                    pathMatrix[i][j] = new int[]{i, j};
                    pathMatrix[j][i] = new int[]{i, j};
                    break;//这里跳出循环了，后面的就不执行了
                }
                findCheapestPath(i, j, disMatrixFloyd);
                //Integer[] b = result.toArray(new Integer[result.size()]);
                //直接顺序保存在集合中就可以了，没有必要单独创建一个三维数组
                int[] b1 = new int[result.size()];
                for (int k = 0; k < result.size(); k++) {
                    b1[k] = result.get(k);
                }
                pathMatrix[i][j] = b1;
                result.clear();
            }
        }
        return dist;
    }

    public void console(double[][] dist) {
        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist[0].length; j++) {
                System.out.println("i=>" + i + "j=>" + j + "结果=>" + dist[i][j]);
            }
        }

    }

    private double[][] floyd(double[][] disMatrixFloyd) {
        int size = disMatrixFloyd.length;
        path = new int[size][size];
        double[][] dist = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                path[i][j] = -1;
                dist[i][j] = disMatrixFloyd[i][j];
            }
        }

        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (dist[i][k] != INF &&
                            dist[k][j] != INF &&
                            dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        path[i][j] = k;
                    }
                }
            }
        }
        return dist;
    }

    /**
     * @param i 开始点
     * @param j 结束点
     */
    private void findPath(int i, int j) {
        int k = path[i][j];
        if (k == -1) {
            return;
        }
        findPath(i, k);   //递归
        result.add(k);
        findPath(k, j);
    }

    /**
     * 从A到B 需要经过哪些点。
     *
     * @param begin
     * @param end
     * @param matrix
     */
    private void findCheapestPath(int begin, int end, double[][] matrix) {
        // floyd(matrix);
        result.add(begin);
        findPath(begin, end);
        result.add(end);
    }

}
