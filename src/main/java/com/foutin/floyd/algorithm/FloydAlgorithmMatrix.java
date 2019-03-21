package com.foutin.floyd.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xingkai.fan
 * @date 2019/2/13 15:48
 */
public class FloydAlgorithmMatrix {
    private int MAXSIZE;
    //保存最短路径
    private int path[][];
    private static int inf = 9999;

    //a[i][j]表示当前顶点i到j的最短距离
    private int A[][];

    public FloydAlgorithmMatrix(int size) {
        this.MAXSIZE = size;
        this.path = new int[size][size];
        A = new int[size][size];
    }

    public int[][] getPath() {
        return path;
    }

    public int[][] getA() {
        return A;
    }

    public void floydCalculate(int initA[][]) {

        //数据初始化
        for (int i = 0; i < MAXSIZE; i++) {
            for (int j = 0; j < MAXSIZE; j++) {
                A[i][j] = initA[i][j];
                //初始化为-1
                path[i][j] = -1;
            }
        }

        //左对角线
        for (int diagonal = 0; diagonal < MAXSIZE; diagonal++) {
            //行
            for (int k = 0; k < MAXSIZE; k++) {
                //除去此行所有的点
                if (k != diagonal) {
                    //列
                    for (int j = 0; j < MAXSIZE; j++) {
                        //除去此列所以的点
                        if (j != diagonal) {
                            //除去对角线的点
                            if (k != j) {
                                //满足条件
                                if (A[k][j] > A[diagonal][j] + A[k][diagonal]) {
                                    A[k][j] = A[diagonal][j] + A[k][diagonal];
                                    path[k][j] = diagonal;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void getDistanceAndtrace(int[][] path) {
        List<Integer> pathA = new ArrayList<>();
        for (int i = 0; i < MAXSIZE; i++) {
            for (int j = 0; j < MAXSIZE; j++) {
                pathA.clear();
                if (A[i][j] == inf) {
                    System.out.println("顶点 " + (i+1) + "-->" + (j+1) + " 的路径不存在");
                } else {
                    pathA.add(j);
                    int ok1 = i;
                    while (true) {
                        ok1 = path[ok1][j];
                        if (ok1 == -1) {
                            break;
                        }
                        pathA.add(0, ok1);
                    }

                    //把起点插入
                    pathA.add(0, i);
                    System.out.println();
                    System.out.print("顶点 " + (i+1) + "-->" + (j+1) + " 的路径为:");
                    for (int z = 0; z < pathA.size(); z++) {
                        System.out.print(pathA.get(z)+1+" ");
                    }

                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        //这个可以修改，有有向图和无向图两种
        /*int initA[][] = {
                {0, 5, inf, 7},
                {inf, 0, 4, 2},
                {3, 3, 0, 2},
                {inf, inf, 1, 0}
        };*/
        int initA[][] = {
                {0,12,inf,inf,inf,16,14},
                {12,0,10,inf,inf,7,inf},
                {inf,10,0,3,5,6,inf},
                {inf,inf,3,0,4,inf,inf},
                {inf,inf,5,4,0,2,8},
                {16,7,6,inf,2,0,9},
                {14,inf,inf,inf,8,9,0}
        };
        FloydAlgorithmMatrix fa = new FloydAlgorithmMatrix(initA.length);
        fa.floydCalculate(initA);
        int[][] faA = fa.getA();
        int[][] path = fa.getPath();
        System.out.println("A矩阵值:");
        for (int[] aFaA : faA) {
            for (int anAFaA : aFaA) {
                System.out.print(anAFaA + " ");
            }
            System.out.println();
        }

        System.out.println("path矩阵值:");
        for (int[] ints : path) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }

        //获取路径
        fa.getDistanceAndtrace(path);

    }
}
