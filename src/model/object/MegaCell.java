/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package model.object;

import java.util.ArrayList;
import java.util.List;

import model.object.robot.Robot;

/**
 * @author Thien Nguyen created on Mar 17, 2016
 */
public class MegaCell extends Cell {
	public enum Orientation {
		EAST, WEST, SOUTH, NORTH;
	}

	public static final int UNVISITED = 056;
	public static final int VISITED = 146;
	private int status;
	private List<SubCell> subCells;

	public MegaCell(int r, int c) {
		super(r, c);
		node.y = (2 * r + 1) * Robot.SIZE_ROBOT;
		node.x = (2 * c + 1) * Robot.SIZE_ROBOT;
		status = UNVISITED;
		subCells = new ArrayList<>();
	}

	public MegaCell(int r, int c, int type) {
		this(r, c);
		this.type = type;
	}

	public MegaCell(MegaCell cell) {
		this(cell.row, cell.col, cell.type);
		setSubCell(cell.subCells);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public SubCell getSubCell(int position) {
		if (position >= subCells.size())
			position = subCells.size() - 1;
		return subCells.get(position);
	}

	public void addSubCell(SubCell cell) {
		subCells.add(cell);
	}

	public void setSubCell(List<SubCell> cells) {
		subCells = cells;
	}

}
