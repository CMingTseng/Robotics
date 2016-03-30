/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package model.object;

import java.awt.Point;

import model.object.MegaCell.Orientation;
import model.object.robot.Robot;

/**
 * @author Thien Nguyen created on Mar 17, 2016
 */
public class SubCell extends Cell {

	public SubCell(int r, int c) {
		super(r, c);
		node.y = (r * 2 + 1) * Robot.SIZE_ROBOT / 2;
		node.x = (c * 2 + 1) * Robot.SIZE_ROBOT / 2;
	}

	public Point getNode(Orientation ori) {
		Point p = new Point();
		if (ori == Orientation.WEST) {
			p.x = col * Robot.SIZE_ROBOT;
			p.y = (row * 2 + 1) * Robot.SIZE_ROBOT / 2;

		} else if (ori == Orientation.EAST) {
			p.x = (col + 1) * Robot.SIZE_ROBOT;
			p.y = (row * 2 + 1) * Robot.SIZE_ROBOT / 2;
		} else if (ori == Orientation.SOUTH) {
			p.x = (col * 2 + 1) * Robot.SIZE_ROBOT / 2;
			p.y = (row + 1) * Robot.SIZE_ROBOT;
		} else if (ori == Orientation.NORTH) {
			p.x = (col * 2 + 1) * Robot.SIZE_ROBOT / 2;
			p.y = row * Robot.SIZE_ROBOT;
		}
		return p;
	}
}