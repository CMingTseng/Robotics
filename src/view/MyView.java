/**
 * /** Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282 Giảng viên hướng dẫn:
 * TS. Phạm Văn Hải Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

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

/**
 * @author Thien Nguyen created on Mar 20, 2016
 */
public class MyView extends JPanel implements Runnable, MouseListener {

	private static final long serialVersionUID = -8642903032698699164L;

	private Thread mThread;
	private Config mConfig;
	private Pixel[][] mFloor;
	private MegaCell[][] mMap;
	private int mAlgorithm;
	private Vector<Point> mPath;
	private Vector<Edge> mEdges;
	private ReadMap mRead;
	private String mFile;
	private BufferedImage mPixelMap;

	public MyView(Config config, int algorithm) {
		this.mAlgorithm = algorithm;
		mConfig = config;
		mFile = "Stage/bitmaps/floor.png";
		initData();
		setVisible(true);
		addMouseListener(this);
		if (mThread == null) {
			mThread = new Thread(this, "Map View");
			mThread.start();
		}
	}

	private void initData() {
		mRead = new ReadMap(mFile);
		mMap = mRead.getMegaCells();
		if (mAlgorithm == Algorithm.STC_OFF || mAlgorithm == Algorithm.SSTC_ON) {
			mFloor = mRead.getFloor();
		} else if (mAlgorithm == Algorithm.SMOOTH_STC_OFF || mAlgorithm == Algorithm.SMOOTH_STC_ON) {
			mFloor = mRead.getcSpace();
		}
		mPixelMap = new BufferedImage(mFloor[0].length, mFloor.length, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < mFloor.length; i++) {
			for (int j = 0; j < mFloor[0].length; j++) {
				if (mFloor[i][j] != null)
					if (mFloor[i][j].getValue() == Config.FREE) {
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
			for (int r = 0; r < Config.NUM_ROW; r++) {
				for (int c = 0; c < Config.NUM_COL; c++) {
					g2d.setStroke(new BasicStroke(1));
					g2d.drawRect(c * Robot.SIZE_ROBOT * 2, r * Robot.SIZE_ROBOT * 2, Robot.SIZE_ROBOT * 2,
							Robot.SIZE_ROBOT * 2);
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

				mFile = mConfig.getFile();
				if (mFile == null)
					mFile = "Stage/bitmaps/floor.png";
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
					Robot robot = new Robot(mRead.getFloor(), mPath);
					new OffSTC(mMap, robot, mConfig.getStartPoint(), mEdges, mConfig);
				} else if (mAlgorithm == Algorithm.SMOOTH_STC_OFF) {
					ReadMap read = new ReadMap(mFile);
					Robot robot = new Robot(read.getcSpace(), mPath);
					new OffSmoothSTC(mMap, robot, mConfig.getStartPoint(), mEdges, mConfig);
				} else if (mAlgorithm == Algorithm.SMOOTH_STC_ON) {
					ReadMap read = new ReadMap(mFile);
					Robot robot = new Robot(read.getcSpace(), mPath);
					new OnSmoothSTC(mMap, robot, mConfig.getStartPoint(), mEdges, mConfig);

				} else if (mAlgorithm == Algorithm.SSTC_ON) {
					Robot robot = new Robot(mRead.getFloor(), mPath);
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
		int row = y / (2 * Robot.SIZE_ROBOT);
		int col = x / (2 * Robot.SIZE_ROBOT);
		if (row < Config.NUM_ROW && col < Config.NUM_COL) {
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
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
