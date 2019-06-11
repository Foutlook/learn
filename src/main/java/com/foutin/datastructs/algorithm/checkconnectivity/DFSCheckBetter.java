package com.foutin.datastructs.algorithm.checkconnectivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @author xingkai.fan
 * @date 2019/6/5 20:02
 *      ┌─┐       ┌─┐
 *   ┌──┘ ┴───────┘ ┴──┐
 *   │                 │
 *   │       ───       │
 *   │  ─┬┘       └┬─  │
 *   │                 │
 *   │       ─┴─       │
 *   │                 │
 *   └───┐         ┌───┘
 *       │         │
 *       │         │
 *       │         │
 *       │         └──────────────┐
 *       │                        │
 *       │                        ├─┐
 *       │                        ┌─┘
 *       │                        │
 *       └─┐  ┐  ┌───────┬──┐  ┌──┘
 *         │ ─┤ ─┤       │ ─┤ ─┤
 *         └──┴──┘       └──┴──┘
 *                神兽保佑
 *               代码无BUG!
 */
public class DFSCheckBetter {

    private TreeNode[] treeNodes;

    private class TreeNode {
        int index;
        Object data;
        List<TreeNode> nextNote;
    }

    /**
     * 生成树
     *
     * @param points 顶点数组
     * @param edges  两顶点二维数组
     */
    public void generateTree(Object[] points, Object[][] edges) {

        // 初始化顶点数
        int vlen = points.length;

        // 初始化顶点
        treeNodes = new TreeNode[vlen];
        for (int i = 0; i < treeNodes.length; i++) {
            treeNodes[i] = new TreeNode();
            treeNodes[i].data = points[i];
            treeNodes[i].nextNote = new ArrayList<>();
            treeNodes[i].index = i;
        }

        // 初始化"边"
        for (Object[] edge : edges) {
            // 读取边的起始顶点和结束顶点
            Object c1 = edge[0];
            Object c2 = edge[1];
            // 读取边的起始顶点和结束顶点
            int p1 = getPosition(c1);
            int p2 = getPosition(c2);

            // 初始化node1
            TreeNode node1 = new TreeNode();
            node1.index = p2;
            // 将node1存到List中
            treeNodes[p1].nextNote.add(node1);

            // 初始化node2
            TreeNode node2 = new TreeNode();
            node2.index = p1;
            // 将node2存到List中
            treeNodes[p2].nextNote.add(node2);
        }
    }

    /**
     * 返回顶点位置
     */
    private int getPosition(Object lg) {
        for (int i = 0; i < treeNodes.length; i++) {
            if (treeNodes[i].data.equals(lg)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 深度优先搜索遍历图的递归实现
     */
    private void dfs(int i, boolean[] visited) {

        visited[i] = true;
        System.out.print(" " + treeNodes[i].data);

        if (!treeNodes[i].nextNote.isEmpty()) {
            for (TreeNode treeNode : treeNodes[i].nextNote) {
                if (!visited[treeNode.index]) {
                    dfs(treeNode.index, visited);
                }
            }
        }
    }

    /**
     * 深度优先搜索遍历图
     */
    public void dfs() {
        // 顶点访问标记
        boolean[] visited = new boolean[treeNodes.length];

        // 初始化所有顶点都没有被访问
        for (int i = 0; i < treeNodes.length; i++) {
            visited[i] = false;
        }

        System.out.print("DFS: ");
        for (int i = 0; i < treeNodes.length; i++) {
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


    /*public static void main(String[] args) {
        DFSCheckBetter dfsCheckBetter = new DFSCheckBetter();
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'a', 'b', 'c', 'd', 'e', 'f', 'G', 'H', 'I'};
        char[][] edges = new char[][]{
                {'A', 'B'}, {'A', 'E'}, {'B', 'A'}, {'B', 'C'}, {'C', 'B'}, {'C', 'D'}, {'D', 'C'}, {'E', 'A'}, {'E', 'F'}, {'F', 'B'}, {'F', 'E'},
                {'G', 'H'}, {'H', 'G'}, {'H', 'I'}, {'I', 'H'}, {'a', 'b'}, {'a', 'e'}, {'b', 'a'}, {'b', 'c'}, {'c', 'b'}, {'c', 'd'}, {'c', 'f'},
                {'d', 'c'}, {'e', 'a'}, {'e', 'f'}, {'f', 'e'}, {'f', 'c'},};
        dfsCheckBetter.generateTree(vexs, edges);
        dfsCheckBetter.dfs();
    }*/


}
