/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package io;

import model.object.MegaCell;
import model.object.Pixel;
import model.object.SubCell;
import utils.Config;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author Thien Nguyen created on Mar 17, 2016
 */
public class ReadMap implements Serializable {

    private int threshold;
    private int maxRow;
    private int maxCol;
    private int mSizeRobot;
    private Pixel[][] mFloor;
    private SubCell[][] mSubCells;
    private MegaCell[][] megaCells;
    private Pixel[][] mCSpace;
    private BufferedImage bitmap;

    public ReadMap(Config mConfig) {
        mSizeRobot = mConfig.getSizeRobot();
        threshold = (int) (0.8 * mSizeRobot * mSizeRobot);
        maxCol = mConfig.getNumCol();
        maxRow = mConfig.getNumRow();
        mFloor = new Pixel[mConfig.getMaxPixelHeight()][mConfig.getMaxPixelWidth()];
        mSubCells = new SubCell[2 * maxRow][2 * maxCol];
        megaCells = new MegaCell[maxRow][maxCol];
        read(mConfig.getFile());
    }

    private synchronized void read(String path) {
        File file = new File(path);
        try {
            bitmap = ImageIO.read(file);
            for (int r = 0; r < 2 * maxRow; r++) {
                for (int c = 0; c < 2 * maxCol; c++) {
                    initSubCell(r, c);
                }
            }
            mCSpace = clone(mFloor);
            genCSpace(mCSpace);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initSubCell(int r, int c) {
        mSubCells[r][c] = new SubCell(r, c, mSizeRobot);
        int count = 0;
        for (int j = 0; j < mSizeRobot; j++)
            for (int i = 0; i < mSizeRobot; i++) {
                int x = c * mSizeRobot + i;
                int y = r * mSizeRobot + j;
                mFloor[y][x] = new Pixel();
                if (x < bitmap.getWidth() && y < bitmap.getHeight()) {
                    if (bitmap.getRGB(x, y) != Color.WHITE.getRGB()) {
                        mFloor[y][x].setValue(Config.OBSTACLE);
                        count++;
                    } else {
                        mFloor[y][x].setValue(Config.FREE);
                    }
                } else {
                    mFloor[y][x].setValue(Config.OBSTACLE);
                    count++;
                }
            }
        if (count == 0) {
            mSubCells[r][c].setType(SubCell.FREE);
        } else if (count > threshold) {
            mSubCells[r][c].setType(SubCell.FULLY_OBSTACLE);
        } else {
            mSubCells[r][c].setType(SubCell.PARTIALLY_OBSTACLE);
        }
    }

    private synchronized Pixel[][] clone(Pixel[][] floor) {
        Pixel[][] clone = new Pixel[floor.length][floor[0].length];
        for (int r = 0; r < floor.length; r++) {
            for (int c = 0; c < floor[0].length; c++) {
                if (floor[r][c] != null)
                    clone[r][c] = new Pixel(floor[r][c]);
            }
        }
        return clone;
    }

    private synchronized void genCSpace(Pixel[][] floor) {
        int buff = mSizeRobot / 2;
        int level = Config.OBSTACLE;
        while (level > Config.OBSTACLE - buff) {
            for (int r = 0; r < floor.length; r++) {
                for (int c = 0; c < floor[0].length; c++) {
                    setCSpace(floor, r, c, level);
                }
            }
            level--;
        }
    }

    private void setCSpace(Pixel[][] floor, int r, int c, int level) {
        if (floor[r][c] != null && floor[r][c].getValue() == level) {
            if (c > 0 && floor[r][c - 1].getValue() == Config.FREE)
                floor[r][c - 1].setValue(level - 1);
            if (c < floor[0].length - 1 && floor[r][c + 1].getValue() == Config.FREE)
                floor[r][c + 1].setValue(level - 1);
            if (r > 0 && floor[r - 1][c].getValue() == Config.FREE)
                floor[r - 1][c].setValue(level - 1);
            if (r < floor.length - 1 && floor[r + 1][c].getValue() == Config.FREE)
                floor[r + 1][c].setValue(level - 1);
        }
    }

    public synchronized Pixel[][] getCSpace() {
        return mCSpace;
    }

    public synchronized Pixel[][] getFloor() {
        return mFloor;
    }

    public synchronized MegaCell[][] getMegaCells() {
        for (int r = 0; r < 2 * maxRow; r += 2) {
            for (int c = 0; c < 2 * maxCol; c += 2) {
                initMegaCell(r, c);
            }
        }
        return megaCells;
    }

    private void initMegaCell(int r, int c) {
        megaCells[r / 2][c / 2] = new MegaCell(r / 2, c / 2, mSizeRobot);
        int numFullCell = 0;
        int numPartiallyCell = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                megaCells[r / 2][c / 2].addSubCell(mSubCells[r + i][c + j]);
                if (mSubCells[r + i][c + j].getType() == SubCell.FULLY_OBSTACLE)
                    numFullCell += 1;
                else if (mSubCells[r + i][c + j].getType() == SubCell.PARTIALLY_OBSTACLE)
                    numPartiallyCell += 1;
            }
        }
        if (numFullCell == 4)
            megaCells[r / 2][c / 2].setType(MegaCell.FULLY_OBSTACLE);
        else if (numFullCell == 0 && numPartiallyCell == 0)
            megaCells[r / 2][c / 2].setType(MegaCell.FREE);
        else
            megaCells[r / 2][c / 2].setType(MegaCell.PARTIALLY_OBSTACLE);

    }

}