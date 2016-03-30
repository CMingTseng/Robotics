/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package model.object;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thien Nguyen created on Mar 17, 2016
 */
public class MegaCell extends Cell {
    private List<SubCell> subCells;

    public MegaCell(int r, int c, int sizeRobot) {
        super(r, c, sizeRobot);
        node.y = (2 * r + 1) * sizeRobot;
        node.x = (2 * c + 1) * sizeRobot;
        subCells = new ArrayList<>();
    }

    public MegaCell(int r, int c, int type, int sizeRobot) {
        this(r, c, sizeRobot);
        this.type = type;
    }

    public MegaCell(MegaCell cell) {
        this(cell.row, cell.col, cell.type, cell.sizeRobot);
        setSubCell(cell.subCells);
    }

    public SubCell getSubCell(int position) {
        if (position >= subCells.size())
            return subCells.get(subCells.size() - 1);
        return subCells.get(position);
    }

    public void addSubCell(SubCell cell) {
        subCells.add(cell);
    }

    private void setSubCell(List<SubCell> cells) {
        subCells = cells;
    }

    public enum Orientation {
        EAST, WEST, SOUTH, NORTH
    }

}
