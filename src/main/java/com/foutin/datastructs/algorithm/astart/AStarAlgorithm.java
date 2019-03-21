package com.foutin.datastructs.algorithm.astart;

import java.util.*;

/**
 * A*算法
 *
 * @author xin_liu
 * @date 2017-3-27
 */
public class AStarAlgorithm {

    /**
     * 点是否可达
     */
    private static final int POINT_CAN_REACH = 1;
    /**
     * 分值的基数
     */
    private static final int SCORE_BASE = 10;

    private static final int SCORE_SIDE = 14;

    // 构造开启、关闭列表，并初始化开启列表
//    private TreeMap<Point, Point> openList = new TreeMap<>();
    private MinHeap<Point> minHeap;
    private Set<Point> closeList = new HashSet<>();
    private Point endPoint;

    public AStarAlgorithm() {
        this.minHeap = new MinHeap<>();
    }

    public SearchResult calculate(int[][] mapPoints, Point startPoint, Point endPoint) {
        this.endPoint = endPoint;
        startPoint.setgScore(0);
        startPoint.setfScore(0);
//        openList.put(startPoint, startPoint);
        minHeap.insert(startPoint);
        List<Point> points = minHeap.getmHeap();

        Point currentPoint = null;
        boolean isFindPath = false;

//        while (!openList.isEmpty()) {
        while (points.size() > 0) {
            currentPoint = points.get(0);
//            currentPoint = openList.pollFirstEntry().getValue();
//            openList.remove(currentPoint);
            minHeap.remove(currentPoint);
            closeList.add(currentPoint);


            if (endPoint.equals(currentPoint)) {
                // 已找到路径
                isFindPath = true;
                break;
            }

            List<Point> canReachPoints = getCanReachPoints(mapPoints, currentPoint, closeList);
            if (canReachPoints.isEmpty()) {
                continue;
            }

            canReachPoints.removeAll(closeList);
            if (canReachPoints.isEmpty()) {
                continue;
            }

//            calculateScore(canReachPoints, currentPoint, endPoint);
            for (int i = canReachPoints.size() - 1; i >= 0; i--) {
                Point canReachPoint = canReachPoints.get(i);
//                Point existsOpenPoint = openList.get(canReachPoint);
                Point existsOpenPoint = null;
                if (points.contains(canReachPoint)) {
                    for (Point point : points) {
                        if (!point.equals(canReachPoint)) {
                            continue;
                        }
                        existsOpenPoint = point;
                        break;
                    }
                    if (canReachPoint.getgScore() - existsOpenPoint.getgScore() >= 0) {
                        continue;
                    }
                }
               /* if (existsOpenPoint != null) {
                    // 已在开启列表，判断是否更优的选择
//                    if (existsOpenPoint.getgScore() - canReachPoint.getgScore() >= 0) {
                    if (canReachPoint.getgScore() - existsOpenPoint.getgScore() >= 0) {
                        continue;
                    }
                }*/
                canReachPoint.setParent(currentPoint);
//                openList.put(canReachPoint, canReachPoint);
                minHeap.insert(canReachPoint);
            }
        }

        if (isFindPath) {
            SearchResult result = new SearchResult();
            result.setPathStacks(getPathStack(currentPoint));
            //获取所有的路径节点
            result.setPathPoint(getPathPoint(currentPoint));
            result.setDistance(currentPoint.gScore);
            return result;
        }

        return null;
    }

    private Set<Point> getPathPoint(Point point) {

        Set<Point> set = new HashSet<>();
        Point currentPoint = point;
        while (currentPoint != null) {
            set.add(currentPoint);
            currentPoint = currentPoint.getParent();
        }
        return set;
    }

    private List<String> getPathStack(Point point) {

        List<String> pathPoints = new ArrayList<>();
        Point currentPoint = point;
        while (currentPoint != null) {
            pathPoints.add(currentPoint.getX() + "," + currentPoint.getY());
            currentPoint = currentPoint.getParent();
        }
        return pathPoints;
    }

    /**
     * 计算分值，没有用
     *
     * @param canReachPoints 可达点
     * @param currentPoint   可达点的开始点
     * @param endPoint       结束点
     */
    private void calculateScore(List<Point> canReachPoints, Point currentPoint,
                                Point endPoint) {
        for (Point point : canReachPoints) {
            point.setgScore(currentPoint.getgScore() + SCORE_BASE * 1);
            int hScore = calculateHScore(point, endPoint);
            point.sethScore(hScore);
            point.setfScore(point.getgScore() + point.gethScore());
        }
    }

    /**
     * 计算两个点之间的预估分值 (H值)
     *
     * @param point1
     * @param point2
     * @return
     */
    private int calculateHScore(Point point1, Point point2) {
        //X,Y 坐标差的绝对值
        return (Math.abs(point1.getX() - point2.getX()) + Math.abs(point1.getY() - point2.getY())) * SCORE_BASE;
    }

    /**
     * 计算不同的点g,h和f的值
     *
     * @param point          可达点
     * @param score          开始点到可达点的值
     * @param canReachPoints 可达点集合
     * @param currentPoint   开始点
     */
    private void calculateScore(Point point, int score, List<Point> canReachPoints, Point currentPoint) {
        //把在closeList中的去除
        if (closeList.contains(point)) {
            return;
        }
        point.setgScore(currentPoint.getgScore() + score);
        int hScore = calculateHScore(point, endPoint);
        point.sethScore(hScore);
        point.setfScore(point.getgScore() + point.gethScore());
        canReachPoints.add(point);
    }

    /**
     * 获取A点到所有的可达点；
     *
     * @param mapPoints
     * @param currentPoint
     * @return
     */
    private List<Point> getCanReachPoints(int[][] mapPoints, Point currentPoint, Set<Point> closeList) {

        int x = currentPoint.getX();
        int y = currentPoint.getY();

        List<Point> canReachPoints = new ArrayList<>(8);
        if (isReach(mapPoints, x - 1, y)) {
            calculateScore(new Point(x - 1, y), SCORE_BASE, canReachPoints, currentPoint);
        }
        if (isReach(mapPoints, x + 1, y)) {
            calculateScore(new Point(x + 1, y), SCORE_BASE, canReachPoints, currentPoint);
        }
        if (isReach(mapPoints, x, y - 1)) {
            calculateScore(new Point(x, y - 1), SCORE_BASE, canReachPoints, currentPoint);
        }
        if (isReach(mapPoints, x, y + 1)) {
            calculateScore(new Point(x, y + 1), SCORE_BASE, canReachPoints, currentPoint);
        }
        //添加斜对角四个点
        if (isReach(mapPoints, x + 1, y + 1)) {
            calculateScore(new Point(x + 1, y + 1), SCORE_SIDE, canReachPoints, currentPoint);
        }
        if (isReach(mapPoints, x - 1, y - 1)) {
            calculateScore(new Point(x - 1, y - 1), SCORE_SIDE, canReachPoints, currentPoint);
        }
        if (isReach(mapPoints, x + 1, y - 1)) {
            calculateScore(new Point(x + 1, y - 1), SCORE_SIDE, canReachPoints, currentPoint);
        }
        if (isReach(mapPoints, x - 1, y + 1)) {
            calculateScore(new Point(x - 1, y + 1), SCORE_SIDE, canReachPoints, currentPoint);
        }

        return canReachPoints;
    }

    private boolean isReach(int[][] mapPoints, int x, int y) {

        if (x < 0 || x >= mapPoints.length) {
            return false;
        }

        if (y < 0 || y >= mapPoints[x].length) {
            return false;
        }
        //数组里保存的是1 ，0这样的数，可达表示有没有障碍物
        return POINT_CAN_REACH == mapPoints[x][y];
    }

    public static class SearchResult {
        private Integer distance;
        private List<String> pathStacks;
        private Set<Point> pathPoint;

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public List<String> getPathStacks() {
            return pathStacks;
        }

        public void setPathStacks(List<String> pathStacks) {
            this.pathStacks = pathStacks;
        }

        public Set<Point> getPathPoint() {
            return pathPoint;
        }

        public void setPathPoint(Set<Point> pathPoint) {
            this.pathPoint = pathPoint;
        }

        @Override
        public String toString() {
            return "SearchResult [distance=" + distance + ", pathStacks="
                    + pathStacks + "]";
        }
    }

    public static class Point implements Comparable<Point> {

        private Integer x;
        private Integer y;
        private Integer gScore;
        private Integer fScore;
        private Integer hScore;
        private Point parent;

        public Point(Integer x, Integer y) {
            super();
            this.x = x;
            this.y = y;
        }

        /**
         * @param coordinate x,y
         */
        public Point(String coordinate) {
            String[] coordinates = coordinate.split(",");
            this.x = Integer.parseInt(coordinates[0]);
            this.y = Integer.parseInt(coordinates[1]);
        }

        @Override
        public int compareTo(Point point) {

            if (null == point) {
                return 1;
            }

            int result = 0;

            if ((result = compare(this.fScore, point.fScore)) == 0) {
                if ((result = compare(this.x, point.x)) == 0) {
                    result = compare(this.y, point.y);
                }
            }

            return result;
        }

        private int compare(Integer v1, Integer v2) {

            if (v1 == null && v2 == null) {
                return 0;
            }
            if (v2 == null) {
                return 1;
            }
            if (v1 == null) {
                return -1;
            }

            return v1 - v2;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
//			result = prime * result + ((fScore == null) ? 0 : fScore.hashCode());
            result = prime * result + ((x == null) ? 0 : x.hashCode());
            result = prime * result + ((y == null) ? 0 : y.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Point other = (Point) obj;
//			if (fScore == null) {
//				if (other.fScore != null)
//					return false;
//			} else if (!fScore.equals(other.fScore))
//				return false;
            if (x == null) {
                if (other.x != null) {
                    return false;
                }
            } else if (!x.equals(other.x)) {
                return false;
            }
            if (y == null) {
                if (other.y != null) {
                    return false;
                }
            } else if (!y.equals(other.y)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "NodeDTO [x=" + x + ", y=" + y + ", gScore=" + gScore
                    + ", fScore=" + fScore + ", hScore=" + hScore
//					+ ", parent=" + parent 
                    + "]";
        }

        public Integer getX() {
            return x;
        }

        public void setX(Integer x) {
            this.x = x;
        }

        public Integer getY() {
            return y;
        }

        public void setY(Integer y) {
            this.y = y;
        }

        public Integer getgScore() {
            return gScore;
        }

        public void setgScore(Integer gScore) {
            this.gScore = gScore;
        }

        public Integer getfScore() {
            return fScore;
        }

        public void setfScore(Integer fScore) {
            this.fScore = fScore;
        }

        public Integer gethScore() {
            return hScore;
        }

        public void sethScore(Integer hScore) {
            this.hScore = hScore;
        }

        public Point getParent() {
            return parent;
        }

        public void setParent(Point parent) {
            this.parent = parent;
        }

    }
}


