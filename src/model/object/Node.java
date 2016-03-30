/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package model.object;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thien Nguyen created by Mar 22, 2016
 */
public class Node {
	private MegaCell element;
	private Node parent;
	private boolean visited;
	private List<Node> childrent;
	private Node preNode;
	private int numVisited;

	public Point initOrien;

	public Node(MegaCell x) {
		element = x;
		visited = false;
		initOrien = new Point();
		numVisited = 0;
		childrent = new ArrayList<>();
	}

	public MegaCell element() {
		return element;
	}

	public void setElement(MegaCell c) {
		element = c;
	}

	public List<Node> getChildrent() {
		return childrent;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public void addChildrent(Node childrent) {
		this.childrent.add(childrent);
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public int getNumVisited() {
		return numVisited;
	}

	public void setNumVisited() {
		numVisited++;
	}

	public Node getPreNode() {
		return preNode;
	}

	public void setPreNode(Node preNode) {
		this.preNode = preNode;
	}
}
