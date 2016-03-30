/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package model.algorithm.offline;

import java.awt.Point;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import model.algorithm.Algorithm;
import model.object.Edge;
import model.object.MegaCell;
import model.object.Node;
import model.object.robot.Robot;
import utils.Config;

/**
 * @author Thien Nguyen created by Mar 23, 2016
 */
public class OffSTC extends Algorithm {

	public OffSTC(MegaCell[][] map, Robot robot, Point start, Vector<Edge> edge, Config mConfig) {
		super(map, robot, start, edge, mConfig);
		DFS(mRoot);
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

	@Override
	protected void initData(MegaCell[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				MegaCell element = new MegaCell(arr[i][j]);
				mGraph[i][j] = new Node(element);
				if (element.getType() == MegaCell.FREE)
					mGraph[i][j].element().setType(MegaCell.FREE);
				else
					mGraph[i][j].element().setType(MegaCell.FULLY_OBSTACLE);
			}
		}
	}

}
