/**
 * /** Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282 Giảng viên hướng dẫn:
 * TS. Phạm Văn Hải Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package view;

import io.ReadMap;
import model.algorithm.Algorithm;
import model.algorithm.offline.OffSTC;
import model.algorithm.offline.OffSmoothSTC;
import model.algorithm.online.OnSmoothSTC;
import model.algorithm.online.SSTC;
import model.object.Edge;
import model.object.MegaCell;
import model.object.Pixel;
import model.object.robot.Robot;
import utils.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Vector;

/**
 * @author Thien Nguyen created on Mar 20, 2016
 */
class MyView extends JPanel implements Runnable, MouseListener {

    private static final long serialVersionUID = -8642903032698699164L;

    private Config mConfig;
    private Pixel[][] mFloor;
    private MegaCell[][] mMap;
    private int mAlgorithm;
    private List<Point> mPath;
    private List<Edge> mEdges;
    private ReadMap mRead;
    private BufferedImage mPixelMap;

    MyView(Config config, int algorithm) {
        this.mAlgorithm = algorithm;
        mConfig = config;
        initData();
        setVisible(true);
        addMouseListener(this);
        new Thread(this).start();
    }

    private void initData() {
        mRead = new ReadMap(mConfig);
        mMap = mRead.getMegaCells();
        if (mAlgorithm == Algorithm.STC_OFF || mAlgorithm == Algorithm.SSTC_ON) {
            mFloor = mRead.getFloor();
        } else if (mAlgorithm == Algorithm.SMOOTH_STC_OFF || mAlgorithm == Algorithm.SMOOTH_STC_ON) {
            mFloor = mRead.getCSpace();
        }
        mPixelMap = new BufferedImage(mFloor[0].length, mFloor.length, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < mFloor.length; i++) {
            for (int j = 0; j < mFloor[0].length; j++) {
                if (mFloor[i][j] == null) {
                    //nothing
                } else if (mFloor[i][j].getValue() == Config.FREE) {
                    mPixelMap.setRGB(j, i, Color.LIGHT_GRAY.getRGB());
                } else if (mFloor[i][j].getValue() == Config.OBSTACLE) {
                    mPixelMap.setRGB(j, i, Color.BLACK.getRGB());
                } else {
                    mPixelMap.setRGB(j, i, Color.GRAY.getRGB());
                }
            }
        }
        mPath = new Vector<>();
        mEdges = new Vector<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(mPixelMap, 0, 0, null);
        if (mConfig.isShowGrid())
            for (int r = 0; r < mConfig.getNumRow(); r++) {
                for (int c = 0; c < mConfig.getNumCol(); c++) {
                    g2d.setStroke(new BasicStroke(1));
                    g2d.drawRect(c * mConfig.getSizeRobot() * 2, r * mConfig.getSizeRobot() * 2, mConfig.getSizeRobot() * 2,
                            mConfig.getSizeRobot() * 2);
                }
            }

        if (mConfig.isShowTree())
            synchronized (mEdges) {
                for (Edge e : mEdges) {
                    g2d.setColor(Color.BLUE);
                    g2d.setStroke(new BasicStroke(5));
                    g2d.drawLine(e.from.x, e.from.y, e.to.x, e.to.y);

                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.fillArc(e.from.x - 5, e.from.y - 5, 10, 10, 0, 360);
                    g2d.fillArc(e.to.x - 5, e.to.y - 5, 10, 10, 0, 360);
                }
            }
        if (mConfig.isShowTrace())
            synchronized (mPath) {
                for (Point p : mPath) {
                    g2d.setColor(Color.WHITE);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.drawLine(p.x, p.y, p.x, p.y);
                }
            }

    }

    @Override
    public void run() {
        while (true) {
            if (mConfig.isChangeMap() || mConfig.isChangeSize()) {
                initData();
                mConfig.setChangeMap(false);
                mConfig.setChangeSize(false);
            }
            if (mConfig.isRestart()) {
                mPath = new Vector<>();
                mEdges = new Vector<>();
            }
            if (mConfig.isStart() && mConfig.getStartPoint() != null) {
                if (mAlgorithm == Algorithm.STC_OFF) {
                    ReadMap read = new ReadMap(mConfig);
                    Robot robot = new Robot(read.getFloor(), mPath, mConfig);
                    new OffSTC(mMap, robot, mConfig.getStartPoint(), mEdges, mConfig);
                } else if (mAlgorithm == Algorithm.SMOOTH_STC_OFF) {
                    ReadMap read = new ReadMap(mConfig);
                    Robot robot = new Robot(read.getCSpace(), mPath, mConfig);
                    new OffSmoothSTC(mMap, robot, mConfig.getStartPoint(), mEdges, mConfig);
                } else if (mAlgorithm == Algorithm.SMOOTH_STC_ON) {
                    ReadMap read = new ReadMap(mConfig);
                    Robot robot = new Robot(read.getCSpace(), mPath, mConfig);
                    new OnSmoothSTC(mMap, robot, mConfig.getStartPoint(), mEdges, mConfig);

                } else if (mAlgorithm == Algorithm.SSTC_ON) {
                    Robot robot = new Robot(mRead.getFloor(), mPath, mConfig);
                    new SSTC(mMap, robot, mConfig.getStartPoint(), mEdges, mConfig);
                }
                mConfig.setStart(false);
            }
            repaint();

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();
        int row = y / (2 * mConfig.getSizeRobot());
        int col = x / (2 * mConfig.getSizeRobot());
        if (row < mConfig.getNumRow() && col < mConfig.getNumCol()) {
            if (mMap[row][col].getType() == MegaCell.FREE) {
                synchronized (this) {
                    mConfig.setStartCell(new Point(row, col));
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ô lựa chọn không hợp lệ! Vui lòng chọn lại!", "Invalid",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //do nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //do nothing
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //do nothing
    }
}
