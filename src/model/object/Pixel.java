/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package model.object;

import utils.Config;

import java.io.Serializable;

/**
 * @author Thien Nguyen created by Mar 26, 2016
 */
public class Pixel implements Serializable {
	private int value;
	private boolean visited;

	public Pixel() {
		value = Config.OBSTACLE;
		visited = false;
	}

	public Pixel(Pixel p) {
		this.value = p.value;
		this.visited = p.visited;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
