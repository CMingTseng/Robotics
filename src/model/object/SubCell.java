/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package model.object;

import model.object.MegaCell.Orientation;

import java.awt.*;

/**
 * @author Thien Nguyen created on Mar 17, 2016
 */
public class SubCell extends Cell {
    public SubCell(int r, int c, int sizeRobot) {
        super(r, c, sizeRobot);
        node.y = (r * 2 + 1) * sizeRobot / 2;
        node.x = (c * 2 + 1) * sizeRobot / 2;
    }

    public Point getNode(Orientation ori) {
        Point p = new Point();
        if (ori == Orientation.WEST) {
            p.x = col * sizeRobot;
            p.y = (row * 2 + 1) * sizeRobot / 2;

        } else if (ori == Orientation.EAST) {
            p.x = (col + 1) * sizeRobot;
            p.y = (row * 2 + 1) * sizeRobot / 2;
        } else if (ori == Orientation.SOUTH) {
            p.x = (col * 2 + 1) * sizeRobot / 2;
            p.y = (row + 1) * sizeRobot;
        } else if (ori == Orientation.NORTH) {
            p.x = (col * 2 + 1) * sizeRobot / 2;
            p.y = row * sizeRobot;
        }
        return p;
    }
}