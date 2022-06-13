package com.hujie.javalearn;

import java.util.*;

class Test {

    public static void main(String[] args) {
        int [][] heights = new int[][]{{1,10,6,7,9,10,4,9}};
        System.out.println(new Test().minimumEffortPath(heights));
    }
    public int minimumEffortPath(int[][] heights) {

        int m = heights.length;
        int n = heights[0].length;

        //定义 起点（0，0） 到坐标（x,y） 的最小消耗为effortTo[x][y]
        int [][] effortTo = new int[m][n];

        // 初始化结果数组为最大消耗，后期过程中遇到有耕地的消耗情况，就更新对应单元格
        for(int i=0; i < m; i++){
            Arrays.fill(effortTo[i], Integer.MAX_VALUE);
        }

        // 建立优先级队列用于发散式的遍历图
        Queue<State> queue = new PriorityQueue<>((a, b) -> {
            return a.effortFromStart - b.effortFromStart;
        });

        // 将起点放入队列中
        queue.offer(new State(0,0,0));

        while(queue.size() > 0){
            State curState = queue.poll();
            int curX = curState.x;
            int curY = curState.y;
            int curEffortFromStart = curState.effortFromStart;

            if(curX == m-1 && curY == n-1){ // 当前节点就是终点
                return curEffortFromStart;
            }

            for(int [] neighbor: adj(heights, curX, curY)){
                int nx = neighbor[0];
                int ny = neighbor[1];

                int effortToNextNode = Math.max(
                        curEffortFromStart,
                        Math.abs(heights[curX][curY] - heights[nx][ny])
                );

                if(effortToNextNode < effortTo[nx][ny]){
                    System.out.println("找到到节点（" + nx + "," + ny + "）的更小消耗, 为" + effortToNextNode);
                    effortTo[nx][ny] = effortToNextNode;
                    queue.offer(new State(nx,ny, effortToNextNode));
                }

            }
        }


        return -1;  // 正常情况不可能到达这里
    }


    // 获取一个节点的邻接节点的方法
    public List<int[]>  adj(int [][] matrix, int x, int y){
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dirs = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
        List<int[]> neighbors = new ArrayList<>();
        for(int[] dir: dirs){
            int nx = x + dir[0];
            int ny = y + dir[1];

            if(nx < 0 || ny < 0 || nx >= m || ny >= n ){
                continue;
            }
            neighbors.add(new int[] {nx, ny});
        }

        return neighbors;
    }

    class State{
        int x,y ; // 矩阵中一个点
        int effortFromStart ;   // 从起点到当前节点的最小体力值消耗

        State(int x, int y, int effortFromStart){
            this.x = x;
            this.y = y;
            this.effortFromStart= effortFromStart;
        }
    }
}
