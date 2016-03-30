/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package utils;

import java.awt.Point;

import model.object.robot.Robot;

/**
 * @author Thien Nguyen created on Mar 17, 2016
 */
public class Config {
	public static final int WIDTH_WINDOW = 1340;
	public static final int HEIGHT_WINDOW = 680;
	public static int MAX_PIXEL_W;
	public static int MAX_PIXEL_H;
	public static final int W = 640;
	public static final int H = 440;
	public static int NUM_ROW = H / (2 * Robot.SIZE_ROBOT);
	public static int NUM_COL = W / (2 * Robot.SIZE_ROBOT);
	public static final int OBSTACLE = 100;
	public static final int FREE = 0;
	static {
		if (NUM_ROW * 2 * Robot.SIZE_ROBOT < H) {
			NUM_ROW += 1;
			MAX_PIXEL_H = NUM_ROW * 2 * Robot.SIZE_ROBOT;
		} else if (NUM_ROW * 2 * Robot.SIZE_ROBOT >= H) {
			MAX_PIXEL_H = NUM_ROW * 2 * Robot.SIZE_ROBOT;
		}
		if (NUM_COL * 2 * Robot.SIZE_ROBOT < W) {
			NUM_COL += 1;
			MAX_PIXEL_W = NUM_COL * 2 * Robot.SIZE_ROBOT;
		} else if (NUM_COL * 2 * Robot.SIZE_ROBOT >= W) {
			MAX_PIXEL_W = NUM_COL * 2 * Robot.SIZE_ROBOT;
		}
	}
	private int speed;
	private boolean selectAlgorithm;
	private boolean start;
	private boolean restart;
	private boolean showGrid;
	private boolean showTree;
	private boolean showTrace;
	private boolean stop;
	private int[] algorithm;
	private Point startCell;
	private int[] settingView;
	private String file;
	private boolean changeMap;
	private boolean changeSize;

	public Config() {
		speed = 25;
		selectAlgorithm = false;
		stop = true;
		showTree = false;
		showTrace = true;
		showGrid = false;
		settingView = new int[] { 0, 0 };
	}

	public synchronized boolean isShowGrid() {
		return showGrid;
	}

	public synchronized void setShowGrid(boolean showGrid) {
		this.showGrid = showGrid;
	}

	public synchronized boolean isShowTree() {
		return showTree;
	}

	public synchronized void setShowTree(boolean showTree) {
		this.showTree = showTree;
	}

	public synchronized boolean isShowTrace() {
		return showTrace;
	}

	public synchronized void setShowTrace(boolean showTrace) {
		this.showTrace = showTrace;
	}

	public synchronized boolean isStop() {
		return stop;
	}

	public synchronized void setStop(boolean stop) {
		this.stop = stop;
	}

	public synchronized int[] getAlgorithm() {
		return algorithm;
	}

	public synchronized void setAlgorithm(int[] algorithm) {
		this.algorithm = algorithm;
	}

	public synchronized Point getStartPoint() {
		return startCell;
	}

	public synchronized void setStartCell(Point startCell) {
		this.startCell = startCell;
	}

	public synchronized boolean isStart() {
		return start;
	}

	public synchronized void setStart(boolean start) {
		this.start = start;
	}

	public synchronized boolean isRestart() {
		return restart;
	}

	public synchronized void setRestart(boolean restart) {
		this.restart = restart;
	}

	public synchronized int[] getSettingView() {
		return settingView;
	}

	public synchronized void setSettingView(int[] settingView) {
		this.settingView = settingView;
	}

	public synchronized boolean isSelectAlgorithm() {
		return selectAlgorithm;
	}

	public synchronized void setSelectAlgorithm(boolean selectAlgorithm) {
		this.selectAlgorithm = selectAlgorithm;
	}

	public synchronized String getFile() {
		return file;
	}

	public synchronized void setFile(String file) {
		this.file = file;
	}

	public synchronized boolean isChangeMap() {
		return changeMap;
	}

	public synchronized void setChangeMap(boolean changeMap) {
		this.changeMap = changeMap;
	}

	public synchronized int getSpeed() {
		return speed;
	}

	public synchronized void setSpeed(int speed) {
		this.speed = speed;
	}

	public synchronized boolean isChangeSize() {
		return changeSize;
	}

	public synchronized void setChangeSize(boolean changeSize) {
		this.changeSize = changeSize;
	}

}
