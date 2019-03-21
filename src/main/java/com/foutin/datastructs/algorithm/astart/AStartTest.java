package com.foutin.datastructs.algorithm.astart;

/**
 * @author xingkai.fan
 * @date 2019/1/30 13:23
 */
public class AStartTest {

    public static void main(String[] args) {

        int[][] mapPoints = new int[70][70];

        for (int i = 0; i < mapPoints.length; i++) {
            for (int j = 0; j < mapPoints[i].length; j++) {
                mapPoints[i][j] = 1;
            }
        }

        for (int j = 30; j > 15; j--) {
            mapPoints[j][20] = 0;
        }
        for (int i = 20; i < 50; i++) {
            mapPoints[30][i] = 0;
        }

        for (int i = 20; i < 55; i++) {
            mapPoints[15][i] = 0;
        }
        long start = System.currentTimeMillis();

        AStarAlgorithm.Point startPoint = new AStarAlgorithm.Point(10, 25);
        AStarAlgorithm.Point endPoint = new AStarAlgorithm.Point(28, 40);
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm();
        AStarAlgorithm.SearchResult result = aStarAlgorithm.calculate(mapPoints, startPoint, endPoint);
        System.out.println(result.toString());

        System.out.println("-----------------------------------");
        for (int i = 0; i < 70; i++) {
            for (int j = 0; j < 70; j++) {
                AStarAlgorithm.Point p = new AStarAlgorithm.Point(i, j);
                if (p.equals(endPoint)) {
                    System.out.print("o");
                } else if (p.equals(startPoint)) {
                    System.out.print("^");
                } else {
                    if (result.getPathPoint().contains(p)) {
                        System.out.print("@");
                    } else if (mapPoints[i][j]==0) {
                        System.out.print("#");
                    } else {
                        System.out.print("*");
                    }

                }
                System.out.print(" ");
            }
            System.out.println();
        }
        long end = System.currentTimeMillis();
        System.out.println("========="+(end - start)+"=========");

    }
}
