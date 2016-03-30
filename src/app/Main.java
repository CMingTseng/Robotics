package app;

/**
 * Mô phỏng thuật toán Smooth-STC trong đồ án tốt nghiệp năm học 2015-2016
 * Sinh viên thực hiện: Nguyễn Văn Thiện - MSSV: 20112282
 * Giảng viên hướng dẫn: TS. Phạm Văn Hải
 * Bộ môn Hệ thống thông tin - Viện CNTT & TT, ĐH BKHN
 */

import view.MainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Thien Nguyen created on Mar 20, 2016
 */
public class Main extends JFrame {
	private static final long serialVersionUID = 1L;

    private Main() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileBar = new JMenu("File");
		JMenu help = new JMenu("Help");

		JMenuItem itemExit = new JMenuItem("Exit");
		JMenuItem itemAbout = new JMenuItem("About");
		help.add(itemAbout);
		fileBar.add(itemExit);

        itemAbout.addActionListener(element -> new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,
						"                       Đồ án tốt nghiệp đại học \n SV thực hiện: Nguyễn Văn Thiện - MSSV: 20112282 \n Lớp: KSTN - CNTT - K56 \n GV hướng dẫn: TS. Phạm Văn Hải \n Chương trình mô phỏng robot tìm đường bao phủ. \n Cài đặt cho thuật toán đề xuất Smooth STC ",
						"Giới thiệu", JOptionPane.PLAIN_MESSAGE);
			}
		});
		itemExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		menuBar.add(fileBar);
		menuBar.add(help);
		setJMenuBar(menuBar);
		setResizable(false);
		setLayout(new FlowLayout());
		add(new MainView());
		pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Demo Smooth STC");
        setVisible(true);
	}
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        final Main main = new Main();

	}

}
