/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package model.algorithm.offline;

import model.algorithm.Algorithm;
import model.object.Edge;
import model.object.MegaCell;
import model.object.Node;
import model.object.robot.Robot;
import utils.Config;

import java.awt.*;
import java.util.List;
import java.util.Stack;

/**
 * @author Thien Nguyen created on Mar 17, 2016
 */
public class OffSmoothSTC extends Algorithm {

    public OffSmoothSTC(MegaCell[][] map, Robot robot, Point start, List<Edge> edge, Config mConfig) {
        super(map, robot, start, edge, mConfig);
        dfs(mRoot);
    }

    public void run() {
        setInitPostion(mRoot);

        Stack<Node> stack = new Stack<>();
        mRoot.setPreNode(mRoot.getParent());
        mRoot.setNumVisited();
        stack.push(mRoot);
        while (!stack.empty()) {
            mRoot = stack.pop();
            if (mRoot.getNumVisited() <= 1) {
                if (mRoot.getParent() != null)
                    stack.push(mRoot.getParent());
                List<Node> neighbor = mRoot.getChildrent();
                for (int i = neighbor.size() - 1; i >= 0; i--) {
                    Node n = neighbor.get(i);
                    n.setNumVisited();
                    stack.push(n);
                }
            }
            if (!stack.empty()) {
                Node next = stack.peek();
                next.setPreNode(mRoot);
                mRobot.genPath(mRoot, next);
            }
            mRoot.setNumVisited();
            try {
                Thread.sleep(mConfig.getSpeed());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    protected void initData(MegaCell[][] arr) {

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                mGraph[i][j] = new Node(arr[i][j]);
            }
        }
    }

}
