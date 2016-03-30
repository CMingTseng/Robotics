/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package model.algorithm;

import model.object.Edge;
import model.object.MegaCell;
import model.object.Node;
import model.object.robot.Robot;
import utils.Config;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thien Nguyen created by Mar 23, 2016
 */
public abstract class Algorithm implements Runnable {
	public static final int STC_OFF = 0;
	public static final int SSTC_ON = 1;
	public static final int SMOOTH_STC_OFF = 2;
	public static final int SMOOTH_STC_ON = 3;
	public static final int FULL_SSTC_ON = 4;

    protected List<Edge> mEdge;
    protected Config mConfig;
    protected Robot mRobot;
    protected Node[][] mGraph;
	protected Node mRoot;
    private Thread mThread;

    public Algorithm(MegaCell[][] map, Robot robot, Point start, List<Edge> edge, Config mConfig) {
        this.mConfig = mConfig;
        this.mEdge = edge;
        this.mRobot = robot;

		mGraph = new Node[map.length][map[0].length];
		initData(map);
		mRoot = mGraph[start.x][start.y];
		if (mThread == null)
			mThread = new Thread(this);
		mThread.start();
	}

	abstract public void run();

	abstract protected void initData(MegaCell[][] arr);

    protected void dfs(Node current) {
        current.setVisited(true);
        List<Node> neighbor = getAdjacency(current);
        for (Node c : neighbor) {
			if (!c.isVisited()) {
				Edge e = new Edge();
				e.from = new Point(current.element().getNode().x, current.element().getNode().y);
				e.to = new Point(c.element().getNode().x, c.element().getNode().y);
				mEdge.add(e);
				c.setParent(current);
				current.addChildrent(c);
				if (c.element().getType() == MegaCell.FREE)
                    dfs(c);
                else {
                    c.setVisited(true);
                }
			}
		}

	}

	protected List<Node> getAdjacency(Node current) {
		Node parent = current.getParent();
		int r = current.element().row;
		int c = current.element().col;
        int r0 = r;
        int c0 = r;
        if (parent == null) {
            if (c < mGraph[0].length - 1 && mGraph[r][c + 1].element().getType() == MegaCell.FREE) {
                c0 = c + 1;
			} else if (c > 0 && mGraph[r][c - 1].element().getType() == MegaCell.FREE) {
				c0 = c - 1;
			} else if (r < mGraph.length - 1 && mGraph[r + 1][c].element().getType() == MegaCell.FREE) {
				r0 = r + 1;
			} else if (r > 0 && mGraph[r - 1][c].element().getType() == MegaCell.FREE) {
				r0 = r - 1;
			}
			current.initOrien.x = r0;
			current.initOrien.y = c0;
		} else {
			r0 = parent.element().row;
			c0 = parent.element().col;
		}
		List<Node> neighbor = new ArrayList<>();
		if (r0 == r) {
			if (c0 < c) {
				if ((r + 1) < mGraph.length && mGraph[r + 1][c].element().getType() != MegaCell.FULLY_OBSTACLE)
					neighbor.add(mGraph[r + 1][c]);
				if ((c + 1) < mGraph[0].length && mGraph[r][c + 1].element().getType() != MegaCell.FULLY_OBSTACLE)
					neighbor.add(mGraph[r][c + 1]);
				if (r > 0 && mGraph[r - 1][c].element().getType() != MegaCell.FULLY_OBSTACLE)
					neighbor.add(mGraph[r - 1][c]);
			} else {
				if (r > 0 && mGraph[r - 1][c].element().getType() != MegaCell.FULLY_OBSTACLE)
					neighbor.add(mGraph[r - 1][c]);
				if (c > 0 && mGraph[r][c - 1].element().getType() != MegaCell.FULLY_OBSTACLE)
					neighbor.add(mGraph[r][c - 1]);
				if ((r + 1) < mGraph.length && mGraph[r + 1][c].element().getType() != MegaCell.FULLY_OBSTACLE)
					neighbor.add(mGraph[r + 1][c]);
			}
		} else if (c0 == c) {
			if (r0 < r) {
				if (c > 0 && mGraph[r][c - 1].element().getType() != MegaCell.FULLY_OBSTACLE)
					neighbor.add(mGraph[r][c - 1]);
				if ((r + 1) < mGraph.length && mGraph[r + 1][c].element().getType() != MegaCell.FULLY_OBSTACLE)
					neighbor.add(mGraph[r + 1][c]);
				if ((c + 1) < mGraph[0].length && mGraph[r][c + 1].element().getType() != MegaCell.FULLY_OBSTACLE)
					neighbor.add(mGraph[r][c + 1]);
			} else {
				if ((c + 1) < mGraph[0].length && mGraph[r][c + 1].element().getType() != MegaCell.FULLY_OBSTACLE)
					neighbor.add(mGraph[r][c + 1]);
				if (r > 0 && mGraph[r - 1][c].element().getType() != MegaCell.FULLY_OBSTACLE)
					neighbor.add(mGraph[r - 1][c]);
				if (c > 0 && mGraph[r][c - 1].element().getType() != MegaCell.FULLY_OBSTACLE)
					neighbor.add(mGraph[r][c - 1]);
			}
		}
		return neighbor;
	}

	protected void setInitPostion(Node root) {
		Node preNode = root.getPreNode();
        int x0 = 0;
        int y0 = 0;
        int r0;
        int c0;
        int r = root.element().row;
        int c = root.element().col;
        if (preNode == null) {
			r0 = root.initOrien.x;
			c0 = root.initOrien.y;
		} else {
			r0 = preNode.element().row;
			c0 = preNode.element().col;
		}
		if (r0 == r && c0 > c) {
			x0 = root.element().getSubCell(1).getNode().x;
			y0 = root.element().getSubCell(1).getNode().y;
		} else if (r0 == r && c0 < c) {
			x0 = root.element().getSubCell(2).getNode().x;
			y0 = root.element().getSubCell(2).getNode().y;
		} else if (r0 < r && c0 == c) {
			x0 = root.element().getSubCell(0).getNode().x;
			y0 = root.element().getSubCell(0).getNode().y;
		} else if (r0 > r && c0 == c) {
			x0 = root.element().getSubCell(3).getNode().x;
			y0 = root.element().getSubCell(3).getNode().y;
		}
		mRobot.setX(x0);
		mRobot.setY(y0);
	}
}
