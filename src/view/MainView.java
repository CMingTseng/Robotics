/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016 Sinh
 * viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282 Giảng viên hướng dẫn: TS.
 * Phạm Văn Hải Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */
package view;

import model.algorithm.Algorithm;
import utils.Config;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * @author Thien Nguyen created on Mar 20, 2016
 */
public class MainView extends JPanel implements Runnable {
	private static final long serialVersionUID = -7403669424377122747L;
	private Config mConfig;
	private ControlPanel controller;
	private int[] setting;
	private JPanel contain1;
	private JPanel contain2;

	public MainView() {
		setPreferredSize(new Dimension(Config.WIDTH_WINDOW, Config.HEIGHT_WINDOW));
		setLayout(null);
		initData();
		initComponent();
		new Thread(this).start();
	}

	private void initData() {
		mConfig = new Config();

		controller = new ControlPanel(mConfig);
	}

	private void initComponent() {
		contain1 = new JPanel();
		contain2 = new JPanel();
		JPanel viewer = new JPanel();
		viewer.setBounds(0, 0, Config.WIDTH_WINDOW, 480);
		controller.setBounds(0, 480, Config.WIDTH_WINDOW, Config.HEIGHT_WINDOW - 480);
		viewer.setLayout(new GridLayout(1, 2));
		contain1.setLayout(new GridLayout(1, 1));
		contain2.setLayout(new GridLayout(1, 1));

		contain1.setBorder(new TitledBorder("Offline"));
		contain2.setBorder(new TitledBorder("Offline"));

		viewer.add(contain1);
		viewer.add(contain2);
		add(viewer);
		add(controller);
	}

	@Override
	public void run() {
		while (true) {
			setting = mConfig.getSettingView();
			if (mConfig.isSelectAlgorithm()) {
				if (setting[0] == Algorithm.STC_OFF && setting[1] == Algorithm.SMOOTH_STC_OFF) {
					MyView view1 = new MyView(mConfig, Algorithm.SMOOTH_STC_OFF);
					MyView view2 = new MyView(mConfig, Algorithm.STC_OFF);
					contain1.setBorder(new TitledBorder("Smooth STC offline"));
					contain2.setBorder(new TitledBorder("STC offline"));
					contain1.removeAll();
					contain2.removeAll();
					contain1.validate();
					contain2.validate();
					contain1.add(view1);
					contain2.add(view2);
					validate();
				} else if (setting[0] == Algorithm.STC_OFF && setting[1] == Algorithm.SMOOTH_STC_ON) {
					MyView view1 = new MyView(mConfig, Algorithm.SMOOTH_STC_ON);
					MyView view2 = new MyView(mConfig, Algorithm.STC_OFF);
					contain1.setBorder(new TitledBorder("Smooth STC online"));
					contain2.setBorder(new TitledBorder("STC offline"));
					contain1.removeAll();
					contain2.removeAll();
					contain1.validate();
					contain2.validate();
					contain1.add(view1);
					contain2.add(view2);
					validate();
				} else if (setting[0] == Algorithm.SSTC_ON && setting[1] == Algorithm.SMOOTH_STC_ON) {
					MyView view1 = new MyView(mConfig, Algorithm.SMOOTH_STC_ON);
					MyView view2 = new MyView(mConfig, Algorithm.SSTC_ON);
					contain1.setBorder(new TitledBorder("Smooth STC online"));
					contain2.setBorder(new TitledBorder("STC online"));
					contain1.removeAll();
					contain2.removeAll();
					contain1.validate();
					contain2.validate();
					contain1.add(view1);
					contain2.add(view2);
					validate();
				}
				mConfig.setSelectAlgorithm(false);
			}
		}
	}
}
