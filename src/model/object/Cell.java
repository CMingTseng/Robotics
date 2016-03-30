/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package model.object;

import java.awt.Point;

/**
 * @author Thien Nguyen created on Mar 17, 2016
 */
public abstract class Cell {
	public static final int FREE = 0;
	public static final int FULLY_OBSTACLE = 1;
	public static final int PARTIALLY_OBSTACLE = 2;

	public int row;
	public int col;
	protected Point node;
	protected int type;

	public Cell(int r, int c) {
		row = r;
		col = c;
		node = new Point();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Point getNode() {
		return node;
	}
}
