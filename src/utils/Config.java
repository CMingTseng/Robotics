/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package utils;

import java.awt.*;
import java.io.Serializable;

/**
 * @author Thien Nguyen created on Mar 17, 2016
 */
public class Config implements Serializable {
    public static final int WIDTH_WINDOW = 1340;
    public static final int HEIGHT_WINDOW = 680;
    public static final int OBSTACLE = 100;
    public static final int FREE = 0;
    private static final int W = 640;
    private static final int H = 440;
    private int maxPixelWidth;
    private int maxPixelHeight;
    private int sizeRobot;
    private int range;
    private int numRow;
    private int numCol;
    private int speed;
    private boolean selectAlgorithm;
    private boolean start;
    private boolean restart;
    private boolean showGrid;
    private boolean showTree;
    private boolean showTrace;
    private boolean stop;
    private Point startCell;
    private int[] settingView;
    private String file;
    private boolean changeMap;
    private boolean changeSize;

    public Config() {
        sizeRobot = 20;
        range = Config.OBSTACLE - sizeRobot / 2;
        numRow = H / (2 * sizeRobot);
        numCol = W / (2 * sizeRobot);
        file = "Stage/bitmaps/floor.png";
        if (numRow * 2 * sizeRobot < H) {
            numRow += 1;
            maxPixelHeight = numRow * 2 * sizeRobot;
        } else if (numRow * 2 * sizeRobot >= H) {
            maxPixelHeight = numRow * 2 * sizeRobot;
        }
        if (numCol * 2 * sizeRobot < W) {
            numCol += 1;
            maxPixelWidth = numCol * 2 * sizeRobot;
        } else if (numCol * 2 * sizeRobot >= W) {
            maxPixelWidth = numCol * 2 * sizeRobot;
        }
        speed = 25;
        selectAlgorithm = false;
        stop = true;
        showTree = false;
        showTrace = true;
        showGrid = false;
        settingView = new int[]{0, 0};
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

    public synchronized void setStop(boolean stop) {
        this.stop = stop;
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

    public int getMaxPixelWidth() {
        return maxPixelWidth;
    }

    public int getMaxPixelHeight() {
        return maxPixelHeight;
    }

    public int getNumRow() {
        return numRow;
    }

    public int getNumCol() {
        return numCol;
    }

    public int getSizeRobot() {
        return sizeRobot;
    }

    public void setSizeRobot(int sizeRobot) {
        this.sizeRobot = sizeRobot;
        this.range = Config.OBSTACLE - sizeRobot / 2;
        this.numRow = H / (2 * sizeRobot);
        this.numCol = W / (2 * sizeRobot);
        if (numRow * 2 * sizeRobot < H) {
            this.numRow += 1;
            maxPixelHeight = numRow * 2 * sizeRobot;
        } else if (numRow * 2 * sizeRobot >= H) {
            maxPixelHeight = numRow * 2 * sizeRobot;
        }
        if (numCol * 2 * sizeRobot < W) {
            this.numCol += 1;
            maxPixelWidth = numCol * 2 * sizeRobot;
        } else if (numCol * 2 * sizeRobot >= W) {
            maxPixelWidth = numCol * 2 * sizeRobot;
        }
    }

    public int getRange() {
        return range;
    }
}
