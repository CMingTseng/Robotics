/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package model.algorithm.online;

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
 * @author Thien Nguyen created by Mar 23, 2016
 */
public class SSTC extends Algorithm {

	public SSTC(MegaCell[][] map, Robot robot, Point start, List<Edge> edge, Config mConfig) {
		super(map, robot, start, edge, mConfig);
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

	@Override
	public void run() {
		getAdjacency(mRoot);
		setInitPostion(mRoot);
		Stack<Node> stack = new Stack<>();
		mRoot.setVisited(true);
		stack.push(mRoot);
		while (!stack.empty()) {
			mRoot = stack.peek();
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
			try {
				Thread.sleep(mConfig.getSpeed());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private Node getNext(Node current) {
		List<Node> neighbor = getAdjacency(current);
		if (!neighbor.isEmpty())
			for (Node aNeighbor : neighbor) {
				if (!aNeighbor.isVisited())
					return aNeighbor;
			}
		return null;
	}

}
