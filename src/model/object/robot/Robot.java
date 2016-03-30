/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package model.object.robot;

import model.object.MegaCell.Orientation;
import model.object.Node;
import model.object.Pixel;
import utils.Config;
import utils.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thien Nguyen created on Mar 20, 2016
 */
public class Robot {
    private static final int BIG = 100;
    private static final int SMALL = 101;
    private static final int NONE = 102;
    private Config mConfig;
    private List<Point> mPath;
    private Pixel[][] mFloor;
    private int range;
    private int x;
    private int y;
    private int xPre;
    private int yPre;

    public Robot(Pixel[][] floor, Config config) {
        this.mConfig = config;
        this.mFloor = floor;
        this.range = mConfig.getRange();
        mPath = new ArrayList<>();
    }

    public Robot(Pixel[][] floor, List<Point> trace, Config config) {
        this.mConfig = config;
        this.mFloor = floor;
        this.mPath = trace;
        this.range = mConfig.getRange();
    }

    public synchronized void genPath(Node current, Node next) {
        xPre = x;
        yPre = y;
        int r0;
        int c0;
        Node parent = current.getPreNode();
        if (parent == null) {
            r0 = current.initOrien.x;
            c0 = current.initOrien.y;
        } else {
            r0 = parent.element().row;
            c0 = parent.element().col;
        }
        int r = current.element().row, c = current.element().col, r1 = next.element().row, c1 = next.element().col;

        if (r0 < r && r < r1 && c0 == c && c == c1) {
            move(next.element().getSubCell(0).getNode(Orientation.NORTH), NONE);
        } else if (r0 < r && r == r1 && c0 == c && c > c1)
            move(next.element().getSubCell(1).getNode(Orientation.EAST), SMALL);
        else if (r0 < r && r == r1 && c0 == c && c < c1)
            move(next.element().getSubCell(2).getNode(Orientation.WEST), BIG);
        else if (r0 == r && r == r1 && c0 < c && c < c1)
            move(next.element().getSubCell(2).getNode(Orientation.WEST), NONE);
        else if (r0 == r && r < r1 && c0 < c && c == c1)
            move(next.element().getSubCell(0).getNode(Orientation.NORTH), SMALL);
        else if (r0 == r && r > r1 && c0 < c && c == c1)
            move(next.element().getSubCell(3).getNode(Orientation.SOUTH), BIG);
        else if (r0 > r && r == r1 && c0 == c && c < c1)
            move(next.element().getSubCell(2).getNode(Orientation.WEST), SMALL);
        else if (r0 > r && r > r1 && c0 == c && c == c1)
            move(next.element().getSubCell(3).getNode(Orientation.SOUTH), NONE);
        else if (r0 > r && r == r1 && c0 == c && c > c1)
            move(next.element().getSubCell(1).getNode(Orientation.EAST), BIG);
        else if (r0 == r && r > r1 && c0 > c && c == c1)
            move(next.element().getSubCell(3).getNode(Orientation.SOUTH), SMALL);
        else if (r0 == r && r == r1 && c0 > c && c > c1)
            move(next.element().getSubCell(1).getNode(Orientation.EAST), NONE);
        else if (r0 == r && r < r1 && c0 > c && c == c1)
            move(next.element().getSubCell(0).getNode(Orientation.NORTH), BIG);

        else if (r0 == r1 && c0 == c1 && c == c1) {
            if (r > r1) {
                move(current.element().getSubCell(3).getNode(), NONE);
                move(next.element().getSubCell(3).getNode(Orientation.SOUTH), NONE);
            } else if (r < r1) {
                move(current.element().getSubCell(0).getNode(), NONE);
                move(next.element().getSubCell(0).getNode(Orientation.NORTH), NONE);
            }
        } else if (r0 == r1 && c0 == c1 && r == r1) {
            if (c < c1) {
                move(current.element().getSubCell(2).getNode(), NONE);
                move(next.element().getSubCell(2).getNode(Orientation.WEST), NONE);
            } else {
                move(current.element().getSubCell(1).getNode(), NONE);
                move(next.element().getSubCell(1).getNode(Orientation.EAST), NONE);
            }
        }
    }

    public void move(Point end1, int type) {
        int x0 = x, y0 = y, x1 = end1.x, y1 = end1.y;
        mPath.add(new Point(x0, y0));
        if (mFloor[y1][x1].getValue() > range) {
            if (x0 < x1) {
                if (y0 < y1) {
                    int x2 = x1, y2 = y1;
                    int x3 = x1, y3 = y1;

                    // co hai vi tri moi can phai lay vi tri gan nhat so voi vi
                    // tri cu
                    while (mFloor[y2][x2].getValue() >= range && x0 < x2) {
                        x2--;
                        if (mFloor[y2][x2].getValue() < range)
                            break;
                    }
                    while (mFloor[y2][x2].getValue() >= range && y0 < y2) {
                        y2--;
                        if (mFloor[y2][x2].getValue() < range)
                            break;
                    }
                    while (mFloor[y3][x3].getValue() >= range && y0 < y3) {
                        y3--;
                        if (mFloor[y3][x3].getValue() < range)
                            break;
                    }
                    while (mFloor[y3][x3].getValue() >= range && x0 < x3) {
                        x3--;
                        if (mFloor[y3][x3].getValue() < range)
                            break;
                    }

                    Point newPoisition = Utils.compareDistance(new Point(x2, y2), new Point(x3, y3), new Point(x1, y1));
                    x1 = newPoisition.x;
                    y1 = newPoisition.y;
                } else if (y0 == y1) {

                    while (mFloor[y1][x1].getValue() >= range && x0 < x1) {
                        x1--;
                        if (mFloor[y1][x1].getValue() < range)
                            break;
                    }

                } else {
                    int x2 = x1, y2 = y1;
                    int x3 = x1, y3 = y1;

                    // co hai vi tri moi can phai lay vi tri gan nhat so voi vi
                    // tri cu
                    while (mFloor[y2][x2].getValue() >= range && x0 < x2) {
                        x2--;
                        if (mFloor[y2][x2].getValue() < range)
                            break;
                    }
                    while (mFloor[y2][x2].getValue() >= range && y0 > y2) {
                        y2++;
                        if (mFloor[y2][x2].getValue() < range)
                            break;
                    }
                    while (mFloor[y3][x3].getValue() >= range && y0 > y3) {
                        y3++;
                        if (mFloor[y3][x3].getValue() < range)
                            break;
                    }
                    while (mFloor[y3][x3].getValue() >= range && x0 < x3) {
                        x3--;
                        if (mFloor[y3][x3].getValue() < range)
                            break;
                    }

                    Point newPoisition = Utils.compareDistance(new Point(x2, y2), new Point(x3, y3), new Point(x1, y1));
                    x1 = newPoisition.x;
                    y1 = newPoisition.y;
                }
            } else if (x0 == x1) {
                if (y0 < y1) {
                    while (mFloor[y1][x1].getValue() >= range && y0 < y1) {
                        y1--;
                        if (mFloor[y1][x1].getValue() < range)
                            break;
                    }
                } else if (y0 == y1) {

                } else {
                    while (mFloor[y1][x1].getValue() >= range && y0 > y1) {
                        y1++;
                        if (mFloor[y1][x1].getValue() < range)
                            break;
                    }
                }
            } else {
                if (y0 < y1) {
                    int x2 = x1;
                    int y2 = y1;
                    int x3 = x1;
                    int y3 = y1;

                    // co hai vi tri moi can phai lay vi tri gan nhat so voi vi
                    // tri cu
                    while (mFloor[y2][x2].getValue() >= range && x0 > x2) {
                        x2++;
                        if (mFloor[y2][x2].getValue() < range)
                            break;
                    }
                    while (mFloor[y2][x2].getValue() >= range && y0 < y2) {
                        y2--;
                        if (mFloor[y2][x2].getValue() < range)
                            break;
                    }
                    while (mFloor[y3][x3].getValue() >= range && y0 < y3) {
                        y3--;
                        if (mFloor[y3][x3].getValue() < range)
                            break;
                    }
                    while (mFloor[y3][x3].getValue() >= range && x0 > x3) {
                        x3++;
                        if (mFloor[y3][x3].getValue() < range)
                            break;
                    }

                    final Point newPoisition = Utils.compareDistance(new Point(x2, y2), new Point(x3, y3),
                            new Point(x1, y1));
                    x1 = newPoisition.x;
                    y1 = newPoisition.y;
                } else if (y0 == y1) {
                    while (mFloor[y1][x1].getValue() >= range && x0 > x1) {
                        x1++;
                        if (mFloor[y1][x1].getValue() < range)
                            break;
                    }

                } else {
                    int x2 = x1, y2 = y1;
                    int x3 = x1, y3 = y1;

                    // co hai vi tri moi can phai lay vi tri gan nhat so voi vi
                    // tri cu
                    while (mFloor[y2][x2].getValue() >= range && x0 > x2) {
                        x2++;
                        if (mFloor[y2][x2].getValue() < range)
                            break;
                    }
                    while (mFloor[y2][x2].getValue() >= range && y0 > y2) {
                        y2++;
                        if (mFloor[y2][x2].getValue() < range) {
                            break;
                        }
                    }
                    while (mFloor[y3][x3].getValue() >= range && y0 > y3) {
                        y3++;
                        if (mFloor[y3][x3].getValue() < range) {
                            break;
                        }
                    }
                    while (mFloor[y3][x3].getValue() >= range && x0 > x3) {
                        x3++;
                        if (mFloor[y3][x3].getValue() < range) {
                            break;
                        }
                    }

                    Point newPoisition = Utils.compareDistance(new Point(x2, y2), new Point(x3, y3), new Point(x1, y1));
                    x1 = newPoisition.x;
                    y1 = newPoisition.y;
                }
            }
        }
        while (x0 != x1 || y0 != y1) {
            if (x0 < x1) {
                if (y0 > y1) {

                    if (mFloor[y0][x0].getValue() == range && !mFloor[y0][x0].isVisited()) {
                        mFloor[y0][x0].setVisited(true);
                        for (int i = -1; i < 1; i++) {
                            for (int j = -1; j < 2; j++) {
                                if (mFloor[y0 + i][x0 + j].getValue() == range && !mFloor[y0 + i][x0 + j].isVisited()) {
                                    x0 += j;
                                    y0 += i;
                                }
                            }
                        }
                        boolean founded = true;
                        mPath.add(new Point(x0, y0));
                        while (y0 > y1 && x0 < x1 && founded) {
                            founded = false;
                            if (!mFloor[y0][x0].isVisited()) {
                                mFloor[y0][x0].setVisited(true);
                                for (int i = -1; i < 1; i++) {
                                    for (int j = -1; j < 2; j++) {
                                        if (mFloor[y0 + i][x0 + j].getValue() == range
                                                && !mFloor[y0 + i][x0 + j].isVisited()) {
                                            x0 += j;
                                            y0 += i;
                                            founded = true;
                                            break;
                                        }
                                    }
                                }
                                mPath.add(new Point(x0, y0));
                            }
                        }
                        if (mFloor[y1][x1].getValue() > range)
                            break;
                    } else {
                        if (type == SMALL)
                            y0 -= 1;
                        else
                            x0 += 1;
                    }
                } else if (y0 < y1) {
                    if (mFloor[y0][x0].getValue() == range && !mFloor[y0][x0].isVisited()) {
                        mFloor[y0][x0].setVisited(true);
                        for (int i = 0; i < 2; i++) {
                            for (int j = -1; j < 2; j++) {
                                if (mFloor[y0 + j][x0 + i].getValue() == range && !mFloor[y0 + j][x0 + i].isVisited()) {
                                    x0 += i;
                                    y0 += j;
                                }
                            }
                        }
                        boolean founded = true;
                        mPath.add(new Point(x0, y0));
                        while (x0 < x1 && y0 < y1 && founded) {
                            founded = false;
                            if (!mFloor[y0][x0].isVisited()) {
                                mFloor[y0][x0].setVisited(true);
                                for (int i = 0; i < 2; i++) {
                                    for (int j = -1; j < 2; j++) {
                                        if (mFloor[y0 + j][x0 + i].getValue() == range
                                                && !mFloor[y0 + j][x0 + i].isVisited()) {
                                            x0 += i;
                                            y0 += j;
                                            founded = true;
                                        }
                                    }
                                }
                                mPath.add(new Point(x0, y0));
                            }
                        }
                        if (mFloor[y1][x1].getValue() > range)
                            break;
                    }
                    if (type == SMALL)
                        x0 += 1;
                    else
                        y0 += 1;
                } else if (y0 == y1) {
                    if (mFloor[y0][x0].getValue() == range && !mFloor[y0][x0].isVisited()) {
                        mFloor[y0][x0].setVisited(true);
                        if (y0 > yPre)
                            for (int i = 0; i < 2; i++) {
                                for (int j = -1; j < 2; j++) {
                                    if (y0 + j > 0 && x0 + i > 0 && y0 + j < mFloor.length && x0 + i < mFloor[0].length)
                                        if (mFloor[y0 + j][x0 + i].getValue() == range
                                                && !mFloor[y0 + j][x0 + i].isVisited()) {
                                            x0 += i;
                                            y0 += j;
                                        }
                                }
                            }

                        else
                            for (int i = -1; i < 1; i++) {
                                for (int j = -1; j < 2; j++) {
                                    if (mFloor[y0 + j][x0 + i].getValue() == range
                                            && !mFloor[y0 + j][x0 + i].isVisited()) {
                                        x0 += i;
                                        y0 += j;
                                    }
                                }
                            }
                    }
                    // xu ly tranh vat can tai day
                    else
                        x0 += 1;
                }
            } else if (x0 == x1) {
                if (y0 < y1) {
                    if (mFloor[y0][x0].getValue() == range && !mFloor[y0][x0].isVisited()) {
                        mFloor[y0][x0].setVisited(true);
                        if (x0 < xPre)
                            for (int i = 0; i < 2; i++) {
                                for (int j = -1; j < 2; j++) {
                                    if (mFloor[y0 + i][x0 + j].getValue() == range
                                            && !mFloor[y0 + i][x0 + j].isVisited()) {
                                        x0 += j;
                                        y0 += i;
                                    }
                                }
                            }

                        else
                            for (int i = -1; i < 1; i++) {
                                for (int j = -1; j < 2; j++) {
                                    if (mFloor[y0 + i][x0 + j].getValue() == range
                                            && !mFloor[y0 + i][x0 + j].isVisited()) {
                                        x0 += j;
                                        y0 += i;
                                    }
                                }
                            }
                    }
                    // xu ly tranh vat can tai day
                    else
                        y0 += 1;
                } else if (y0 == y1) {
                    y0 += 0;
                } else if (y0 > y1) {
                    if (mFloor[y0][x0].getValue() == range && !mFloor[y0][x0].isVisited()) {
                        mFloor[y0][x0].setVisited(true);
                        if (x0 < xPre)
                            for (int i = 0; i < 2; i++) {
                                for (int j = -1; j < 2; j++) {
                                    if (mFloor[y0 + j][x0 + i].getValue() == range
                                            && !mFloor[y0 + j][x0 + i].isVisited()) {
                                        x0 += i;
                                        y0 += j;
                                    }
                                }
                            }

                        else
                            for (int i = -1; i < 1; i++) {
                                for (int j = -1; j < 2; j++) {
                                    if (mFloor[y0 + j][x0 + i].getValue() == range
                                            && !mFloor[y0 + j][x0 + i].isVisited()) {
                                        x0 += i;
                                        y0 += j;
                                    }
                                }
                            }
                    }
                    // xu ly tranh vat can tai day
                    else
                        y0 -= 1;
                }
            } else {
                if (y0 < y1) {
                    if (mFloor[y0][x0].getValue() == range && !mFloor[y0][x0].isVisited()) {
                        mFloor[y0][x0].setVisited(true);
                        for (int i = 0; i < 2; i++) {
                            for (int j = -1; j < 2; j++) {
                                if (mFloor[y0 + i][x0 + j].getValue() == range && !mFloor[y0 + i][x0 + j].isVisited()) {
                                    x0 += j;
                                    y0 += i;
                                }
                            }
                        }
                        boolean founded = true;
                        mPath.add(new Point(x0, y0));
                        while (y0 < y1 && x0 > x1 && founded) {
                            founded = false;
                            if (!mFloor[y0][x0].isVisited()) {
                                mFloor[y0][x0].setVisited(true);
                                for (int i = 0; i < 2; i++) {
                                    for (int j = -1; j < 2; j++) {
                                        if (mFloor[y0 + i][x0 + j].getValue() == range
                                                && !mFloor[y0 + i][x0 + j].isVisited()) {
                                            x0 += j;
                                            y0 += i;
                                            founded = true;
                                        }
                                    }
                                }
                                mPath.add(new Point(x0, y0));
                            }
                        }
                        if (mFloor[y1][x1].getValue() > range)
                            break;
                    }
                    if (type == SMALL)
                        y0 += 1;
                    else
                        x0 -= 1;
                } else if (y0 == y1) {
                    if (mFloor[y0][x0].getValue() == range && !mFloor[y0][x0].isVisited()) {
                        mFloor[y0][x0].setVisited(true);
                        if (y0 < yPre)
                            for (int i = 0; i < 2; i++) {
                                for (int j = -1; j < 2; j++) {
                                    if (mFloor[y0 + i][x0 + j].getValue() == range
                                            && !mFloor[y0 + i][x0 + j].isVisited()) {
                                        x0 += j;
                                        y0 += i;
                                    }
                                }
                            }

                        else
                            for (int i = -1; i < 1; i++) {
                                for (int j = -1; j < 2; j++) {
                                    if (mFloor[y0 + i][x0 + j].getValue() == range
                                            && !mFloor[y0 + i][x0 + j].isVisited()) {
                                        x0 += j;
                                        y0 += i;
                                    }
                                }
                            }
                    }
                    // xu ly tranh vat can tai day
                    else
                        x0 -= 1;
                } else if (y0 > y1) {
                    if (mFloor[y0][x0].getValue() == range && !mFloor[y0][x0].isVisited()) {
                        mFloor[y0][x0].setVisited(true);
                        for (int i = -1; i < 1; i++) {
                            for (int j = -1; j < 2; j++) {
                                if (mFloor[y0 + j][x0 + i].getValue() == range && !mFloor[y0 + j][x0 + i].isVisited()) {
                                    x0 += i;
                                    y0 += j;
                                }
                            }
                        }
                        boolean founded = true;
                        mPath.add(new Point(x0, y0));
                        while (x0 > x1 && y0 > y1 && founded) {
                            founded = false;
                            if (!mFloor[y0][x0].isVisited()) {
                                mFloor[y0][x0].setVisited(true);
                                for (int i = -1; i < 1; i++) {
                                    for (int j = -1; j < 2; j++) {
                                        if (mFloor[y0 + j][x0 + i].getValue() == range
                                                && !mFloor[y0 + j][x0 + i].isVisited()) {
                                            x0 += i;
                                            y0 += j;
                                            founded = true;
                                        }
                                    }
                                }
                                mPath.add(new Point(x0, y0));
                            }
                        }
                        if (mFloor[y1][x1].getValue() > range)
                            break;
                    }
                    if (type == SMALL)
                        x0 -= 1;
                    else
                        y0 -= 1;

                }
            }
            mPath.add(new Point(x0, y0));
        }
        x = x0;
        y = y0;
    }

    public synchronized List<Point> getPath() {
        return mPath;
    }

    public synchronized void setPath(List<Point> path) {
        this.mPath = path;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
