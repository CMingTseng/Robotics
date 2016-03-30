/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package io;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.object.MegaCell;
import model.object.Pixel;
import model.object.SubCell;
import model.object.robot.Robot;
import utils.Config;

/**
 * @author Thien Nguyen created on Mar 17, 2016
 */
public class ReadMap {

	public static int THRESHOLD = (int) (0.8 * Robot.SIZE_ROBOT * Robot.SIZE_ROBOT);

	private Pixel[][] mFloor;
	private SubCell[][] mSubCells;
	private MegaCell[][] megaCells;
	private Pixel[][] mCSpace;

	public ReadMap(String path) {
		mFloor = new Pixel[Config.MAX_PIXEL_H][Config.MAX_PIXEL_W];
		mSubCells = new SubCell[2 * Config.NUM_ROW][2 * Config.NUM_COL];
		megaCells = new MegaCell[Config.NUM_ROW][Config.NUM_COL];
		read(path);
	}

	private synchronized void read(String path) {
		File file = new File(path);
		try {
			BufferedImage bitmap = ImageIO.read(file);
			for (int r = 0; r < 2 * Config.NUM_ROW; r++) {
				for (int c = 0; c < 2 * Config.NUM_COL; c++) {
					mSubCells[r][c] = new SubCell(r, c);
					int _num = 0;
					for (int j = 0; j < Robot.SIZE_ROBOT; j++) {
						for (int i = 0; i < Robot.SIZE_ROBOT; i++) {
							int x = c * Robot.SIZE_ROBOT + i;
							int y = r * Robot.SIZE_ROBOT + j;
							mFloor[y][x] = new Pixel();
							if (x < bitmap.getWidth() && y < bitmap.getHeight()) {
								if (bitmap.getRGB(x, y) != Color.WHITE.getRGB()) {
									mFloor[y][x].setValue(Config.OBSTACLE);
									_num++;
								} else {
									mFloor[y][x].setValue(Config.FREE);
								}
							} else {
								mFloor[y][x].setValue(Config.OBSTACLE);
								_num++;
							}
						}
					}
					if (_num == 0) {
						mSubCells[r][c].setType(SubCell.FREE);
					} else if (_num > THRESHOLD) {
						mSubCells[r][c].setType(SubCell.FULLY_OBSTACLE);
					} else {
						mSubCells[r][c].setType(SubCell.PARTIALLY_OBSTACLE);
					}
				}
			}
			mCSpace = clone(mFloor);
			genCSpace(mCSpace);
		} catch (IOException e) {
			e.printStackTrace();
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
		int buff = Robot.SIZE_ROBOT / 2;
		int level = Config.OBSTACLE;
		while (level > Config.OBSTACLE - buff) {
			for (int r = 0; r < floor.length; r++) {
				for (int c = 0; c < floor[0].length; c++) {
					if (floor[r][c] != null)
						if (floor[r][c].getValue() == level) {
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
			}
			level--;
		}
	}

	public synchronized Pixel[][] getcSpace() {
		return mCSpace;
	}

	public synchronized Pixel[][] getFloor() {
		return mFloor;
	}

	public synchronized MegaCell[][] getMegaCells() {
		for (int r = 0; r < 2 * Config.NUM_ROW; r += 2) {
			for (int c = 0; c < 2 * Config.NUM_COL; c += 2) {
				megaCells[r / 2][c / 2] = new MegaCell(r / 2, c / 2);
				int num_full, num_par;
				num_full = 0;
				num_par = 0;
				for (int i = 0; i < 2; i++) {
					for (int j = 0; j < 2; j++) {
						megaCells[r / 2][c / 2].addSubCell(mSubCells[r + i][c + j]);
						if (mSubCells[r + i][c + j].getType() == SubCell.FULLY_OBSTACLE)
							num_full += 1;
						else if (mSubCells[r + i][c + j].getType() == SubCell.PARTIALLY_OBSTACLE)
							num_par += 1;
					}
				}
				if (num_full == 4)
					megaCells[r / 2][c / 2].setType(MegaCell.FULLY_OBSTACLE);
				else if (num_full == 0 && num_par == 0)
					megaCells[r / 2][c / 2].setType(MegaCell.FREE);
				else
					megaCells[r / 2][c / 2].setType(MegaCell.PARTIALLY_OBSTACLE);

			}
		}
		return megaCells;
	}

}