package com.foutin.datastructs.algorithm.checkconnectivity;


/**
 * @author xingkai.fan
 * @description 判断两点之间是否可达
 * @date 2019/6/3 16:03
 */
public class DFSCheck {

    /**
     * 顶点数组
     */
    private VNode[] mVexs;

    /**
     * 顶点
     */
    private class VNode {
        char data;
        TreeNode connectionPoint;
    }

    private class TreeNode {
        /**
         * 该边所指向的顶点的位置
         */
        int index;
        TreeNode nextNote;
    }

    /**
     * 生成树
     * @param points  顶点数组
     * @param edges  两顶点二维数组
     */
    private void generateTree(char[] points, char[][] edges) {

        // 初始化顶点数
        int vlen = points.length;

        // 初始化顶点
        mVexs = new VNode[vlen];
        for (int i = 0; i < mVexs.length; i++) {
            mVexs[i] = new VNode();
            mVexs[i].data = points[i];
            mVexs[i].connectionPoint = null;
        }

        // 初始化"边"
        for (char[] edge : edges) {
            // 读取边的起始顶点和结束顶点
            char c1 = edge[0];
            char c2 = edge[1];
            // 读取边的起始顶点和结束顶点
            int p1 = getPosition(c1);
            int p2 = getPosition(c2);

            // 初始化node1
            TreeNode node1 = new TreeNode();
            node1.index = p2;
            // 将node1链接到"p1所在链表的末尾"
            if (mVexs[p1].connectionPoint == null) {
                mVexs[p1].connectionPoint = node1;
            } else {
                linkLast(mVexs[p1].connectionPoint, node1);
            }
            // 初始化node2
            TreeNode node2 = new TreeNode();
            node2.index = p1;
            // 将node2链接到"p2所在链表的末尾"
            if (mVexs[p2].connectionPoint == null) {
                mVexs[p2].connectionPoint = node2;
            } else {
                linkLast(mVexs[p2].connectionPoint, node2);
            }
        }
    }

    /**
     * 返回顶点位置
     */
    private int getPosition(char lg) {
        for (int i = 0; i < mVexs.length; i++) {
            if (mVexs[i].data == lg) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 找另一个相连顶点
     */
    private void linkLast(TreeNode list, TreeNode node) {
        TreeNode p = list;

        while (p.nextNote != null) {
            p = p.nextNote;
        }
        p.nextNote = node;
    }


    /**
     * 深度优先搜索遍历图的递归实现
     */
    private void dfs(int i, boolean[] visited) {
        TreeNode node;

        visited[i] = true;
        System.out.print(" " + mVexs[i].data);
        node = mVexs[i].connectionPoint;
        while (node != null) {
            if (!visited[node.index]) {
                dfs(node.index, visited);
            }
            node = node.nextNote;
        }
    }

    /**
     * 深度优先搜索遍历图
     */
    private void dfs() {
        // 顶点访问标记
        boolean[] visited = new boolean[mVexs.length];

        // 初始化所有顶点都没有被访问
        for (int i = 0; i < mVexs.length; i++) {
            visited[i] = false;
        }

        System.out.print("DFS: ");
        for (int i = 0; i < mVexs.length; i++) {
            // 这里只为了输出换行输出结果好看
            if (!visited[i]) {
                System.out.println();
            }
            if (!visited[i]) {
                dfs(i, visited);
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        DFSCheck dfsCheck = new DFSCheck();
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'a', 'b', 'c', 'd', 'e', 'f', 'G', 'H', 'I'};
        char[][] edges = new char[][]{
                {'A', 'B'}, {'A', 'E'}, {'B', 'A'}, {'B', 'C'}, {'C', 'B'}, {'C', 'D'}, {'D', 'C'}, {'E', 'A'}, {'E', 'F'}, {'F', 'B'}, {'F', 'E'},
                {'G', 'H'}, {'H', 'G'}, {'H', 'I'}, {'I', 'H'}, {'a', 'b'}, {'a', 'e'}, {'b', 'a'}, {'b', 'c'}, {'c', 'b'}, {'c', 'd'}, {'c', 'f'},
                {'d', 'c'}, {'e', 'a'}, {'e', 'f'}, {'f', 'e'}, {'f', 'c'},};
        dfsCheck.generateTree(vexs, edges);
        dfsCheck.dfs();
    }
}
