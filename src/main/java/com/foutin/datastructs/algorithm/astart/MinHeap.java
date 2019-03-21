package com.foutin.datastructs.algorithm.astart;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xingkai.fan
 * @date 2019/1/31 11:55
 */
public class MinHeap<T extends Comparable<T>> {

    /**
     * 存放堆的数组
     */
    private List<T> mHeap;

    public MinHeap() {
        this.mHeap = new ArrayList<>();
    }

    /**
     * 最小堆的向下调整算法
     * <p>
     * 注：数组实现的堆中，第N个节点的左孩子的索引值是(2N+1)，右孩子的索引是(2N+2)。
     * <p>
     * 参数说明：
     *
     * @param start -- 被下调节点的起始位置(一般为0，表示从第1个开始)
     * @param end   -- 截至范围(一般为数组中最后一个元素的索引)
     */
    private void filterDown(int start, int end) {
        // 当前(current)节点的位置
        int current = start;
        // 左(left)孩子的位置
        int left = 2 * current + 1;
        // 当前(current)节点的大小
        T tmp = mHeap.get(current);

        while (left <= end) {
            int cmp;
            // "left"是左孩子，"left+1"是右孩子
            if (mHeap.size() > (left + 1)) {
                cmp = mHeap.get(left).compareTo(mHeap.get(left + 1));
                if (left < end && cmp > 0) {
                    // 左右两孩子中选择较小者，即mHeap[left+1]
                    left++;
                }
            }

            cmp = tmp.compareTo(mHeap.get(left));
            if (cmp <= 0) {
                break;
            } else {
                mHeap.set(current, mHeap.get(left));
                current = left;
                left = 2 * left + 1;
            }
        }
        mHeap.set(current, tmp);
    }

    /**
     * 最小堆的删除
     * <p>
     * 返回值：
     * 成功，返回被删除的值
     * 失败，返回null
     */
    public int remove(T data) {
        // 如果"堆"已空，则返回-1
        if (mHeap.isEmpty()) {
            return -1;
        }

        // 获取data在数组中的索引
        int index = mHeap.indexOf(data);
        if (index == -1) {
            return -1;
        }

        int size = mHeap.size();
        // 用最后元素填补
        mHeap.set(index, mHeap.get(size - 1));
        // 删除最后的元素
        mHeap.remove(size - 1);

        if (mHeap.size() > 1) {
            // 从index号位置开始自上向下调整为最小堆
            filterDown(index, mHeap.size() - 1);
        }

        return 0;
    }

    /**
     * 最小堆的向上调整算法(从start开始向上直到0，调整堆)
     * <p>
     * 注：数组实现的堆中，第N个节点的左孩子的索引值是(2N+1)，右孩子的索引是(2N+2)。
     * <p>
     * 参数说明：
     * start -- 被上调节点的起始位置(一般为数组中最后一个元素的索引)
     */
    private void filterUp(int start) {
        // 当前节点(current)的位置
        int current = start;
        // 父(parent)结点的位置
        int parent = (current - 1) / 2;
        // 当前节点(current)的大小
        T tmp = mHeap.get(current);

        while (current > 0) {
            int cmp = mHeap.get(parent).compareTo(tmp);
            if (cmp <= 0) {
                break;
            } else {
                mHeap.set(current, mHeap.get(parent));
                current = parent;
                parent = (parent - 1) / 2;
            }
        }
        mHeap.set(current, tmp);
    }

    /**
     * 将data插入到二叉堆中
     */
    public void insert(T data) {
        int size = mHeap.size();

        // 将"数组"插在表尾
        mHeap.add(data);
        // 向上调整堆
        filterUp(size);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T aMHeap : mHeap) {
            sb.append(aMHeap).append(" ");
        }

        return sb.toString();
    }

    public List<T> getmHeap() {
        return mHeap;
    }

    public static void main(String[] args) {
        int i;
        int a[] = {80, 40, 30, 60, 90, 70, 10, 50, 20};
        MinHeap<Integer> tree = new MinHeap<Integer>();

        System.out.printf("== 依次添加: ");
        for (i = 0; i < a.length; i++) {
            System.out.printf("%d ", a[i]);
            tree.insert(a[i]);
        }

        System.out.printf("\n== 最 小 堆: %s", tree);

        i = 15;
        tree.insert(i);
        System.out.printf("\n== 添加元素: %d", i);
        System.out.printf("\n== 最 小 堆: %s", tree);

        i = 10;
        tree.remove(i);
        System.out.printf("\n== 删除元素: %d", i);
        System.out.printf("\n== 最 小 堆: %s", tree);
        System.out.printf("\n");
    }
}
