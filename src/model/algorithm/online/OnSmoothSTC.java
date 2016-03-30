/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package model.algorithm.online;

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
 * @author Thien Nguyen created on Mar 17, 2016
 */
public class OnSmoothSTC extends Algorithm {

	public OnSmoothSTC(MegaCell[][] map, Robot robot, Point start, Vector<Edge> edge, Config mConfig) {
		super(map, robot, start, edge, mConfig);
	}

	@Override
	protected void initData(MegaCell[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				mGraph[i][j] = new Node(arr[i][j]);
			}
		}
	}

	@Override
	public void run() {
		getAdjacency(mRoot);
		setInitPostion(mRoot);
		Stack<Node> stack = new Stack<>();
		mRoot.setVisited(true);
		stack.push(mRoot);
		while (!stack.empty()) {
			mRoot = stack.peek();
			if (mRoot.element().getType() == MegaCell.PARTIALLY_OBSTACLE) {
				mRoot = stack.pop();
				if (!stack.empty()) {
					Node n = stack.peek();
					n.setPreNode(mRoot);
					mRobot.genPath(mRoot, n);
				}
			} else {
				Node next = getNext(mRoot);
				if (next == null) {
					mRoot = stack.pop();
					if (!stack.empty()) {
						Node n = stack.peek();
						n.setPreNode(mRoot);
						mRobot.genPath(mRoot, n);
					}
				} else {
					next.setParent(mRoot);
					stack.push(next);
					next.setVisited(true);
					next.setPreNode(mRoot);
					mRobot.genPath(mRoot, next);

					Edge e = new Edge();
					e.from = new Point(mRoot.element().getNode().x, mRoot.element().getNode().y);
					e.to = new Point(next.element().getNode().x, next.element().getNode().y);
					mEdge.add(e);
				}
			}
			try {
				Thread.sleep(mConfig.getSpeed());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Node getNext(Node current) {
		List<Node> neighbor = getAdjacency(current);
		if (neighbor.size() > 0)
			for (int i = 0; i < neighbor.size(); i++) {
				if (!neighbor.get(i).isVisited())
					return neighbor.get(i);
			}
		return null;
	}

}
