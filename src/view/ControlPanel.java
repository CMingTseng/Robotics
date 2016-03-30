package view;

import model.algorithm.Algorithm;
import utils.Config;
import utils.Utils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;

/**
 * @author Thien Nguyen created by Mar 22, 2016
 */
class ControlPanel extends JPanel implements Runnable, ActionListener, ChangeListener, Serializable {

    private static final int MAX_SPEED = 100;
    private static final int MIN_SPEED = 0;
    private static final long serialVersionUID = 4231304967321746399L;
    private Config mConfig;
    private JButton btnStart;
    private JButton btnStop;
    private JCheckBox cbShowTrace;
    private JCheckBox cbShowGrid;
    private JCheckBox cbShowTree;
    private JTextField tfStart;
    private JCheckBox[] checkBox;
    private int flag1 = 0;
    private int flag2 = 0;
    private JButton btnSelectAlgorithm;
    private JButton btnOpen;
    private JTextField tfSizeRobot;
    private JButton btnOK;
    private JSlider slider;
    private JTextField speed;
    private boolean stop;

    ControlPanel(Config config) {
        mConfig = config;
        setLayout(new BorderLayout());
        initComponent();
        initData();
        setVisible(true);
    }

    private void initData() {
        stop = false;
        new Thread(this).start();
    }

    private void initComponent() {
        JPanel control = new JPanel();
        JPanel info = new JPanel();
        control.setBorder(new TitledBorder("Điều khiển"));
        info.setBorder(new TitledBorder("Thông tin"));
        info.setLayout(new BorderLayout());
        control.setLayout(new BorderLayout());
        @SuppressWarnings("serial")
        JTable table = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                if (col == 0) {
                    return this.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(this,
                            this.getValueAt(row, col), false, false, row, col);
                } else {
                    return super.prepareRenderer(renderer, row, col);
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        table.setAutoCreateRowSorter(false);
        table.setModel(
                new DefaultTableModel(new Object[][]{}, new String[]{"", "STC", "Full Spiral STC", "Smooth STC"}));
        final JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(table));
        JScrollPane scrollPanel = new JScrollPane(table);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{"Diện tích bao phủ", new Double(1), new Double(1)});
        model.addRow(new Object[]{"Diện tích lặp", new Double(1), new Double(1)});
        model.addRow(new Object[]{"Số lượng nút", new Double(1), new Double(1)});
        model.addRow(new Object[]{"Số lượng cạnh", new Double(1), new Double(1)});
        model.addRow(new Object[]{"Thời gian bao phủ", new Double(1), new Double(1)});
        model.addRow(new Object[]{"Tốc độ di chuyển", new Double(1), new Double(1)});

        JPanel containAction = new JPanel();
        JPanel containOption = new JPanel();
        JPanel containAlgo1 = new JPanel();
        JPanel containAlgo2 = new JPanel();
        JPanel container = new JPanel();
        JPanel containerSub = new JPanel();
        JPanel contain1 = new JPanel();
        JPanel contain2 = new JPanel();
        JPanel contain3 = new JPanel();
        JPanel contain4 = new JPanel();
        JPanel contain5 = new JPanel();
        JPanel contain6 = new JPanel();
        contain1.setLayout(new GridLayout(4, 1, 5, 5));
        contain2.setLayout(new FlowLayout());
        containAlgo1.setBorder(new TitledBorder("Off-line Algorithm"));
        containAlgo2.setBorder(new TitledBorder("On-line Algorithm"));
        containOption.setBorder(new TitledBorder("Tùy chỉnh"));
        contain6.setBorder(new TitledBorder("Kích thước Robot"));
        container.setLayout(new GridLayout(2, 1));
        containerSub.setLayout(new GridLayout(1, 2));
        containAlgo1.setLayout(new GridLayout(3, 1));
        containAlgo2.setLayout(new GridLayout(3, 1));
        containAction.setLayout(new BorderLayout());
        containOption.setLayout(new GridLayout(1, 2));
        contain3.setLayout(new BorderLayout());
        contain4.setLayout(new GridLayout(2, 1));
        contain5.setLayout(new GridLayout(3, 1));
        contain6.setLayout(new FlowLayout());
        btnStart = new JButton("Bắt đầu");
        btnStop = new JButton("Tạm dừng");
        btnSelectAlgorithm = new JButton("Khởi tạo");
        btnOpen = new JButton("Chọn bản đồ");
        btnOK = new JButton("OK");
        btnOK.addActionListener(this);
        btnSelectAlgorithm.addActionListener(this);
        btnOpen.addActionListener(this);
        btnStart.addActionListener(this);
        btnStop.addActionListener(this);
        btnStop.setEnabled(false);

        speed = new JTextField(15);
        speed.setEnabled(false);
        checkBox = new JCheckBox[5];
        checkBox[0] = new JCheckBox("STC ofline");
        checkBox[1] = new JCheckBox("STC online");
        checkBox[2] = new JCheckBox("Full Spiral STC online");
        checkBox[3] = new JCheckBox("Smooth STC offline");
        checkBox[4] = new JCheckBox("Smooth STC online");
        cbShowGrid = new JCheckBox("Hiển thị ô lưới");
        cbShowTree = new JCheckBox("Hiển thị cây");
        cbShowTrace = new JCheckBox("Hiển thị đường đi");
        cbShowTrace.setSelected(true);
        cbShowGrid.addActionListener(this);
        cbShowTrace.addActionListener(this);
        cbShowTree.addActionListener(this);

        tfStart = new JTextField(15);
        tfSizeRobot = new JTextField(5);
        tfStart.setEditable(false);
        slider = new JSlider(JSlider.HORIZONTAL, MIN_SPEED, MAX_SPEED, MAX_SPEED / 2);
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(slider.createStandardLabels(10));
        slider.addChangeListener(this);
        contain2.add(new JLabel("Ô bắt đầu: "));
        contain2.add(tfStart);
        contain2.add(new JLabel("Vận tốc Robot:"));
        contain2.add(speed);

        contain5.add(cbShowGrid);
        contain5.add(cbShowTree);
        contain6.add(tfSizeRobot);
        contain5.add(cbShowTrace);
        contain6.add(btnOK);
        contain3.add(contain5, BorderLayout.CENTER);
        contain3.add(contain6, BorderLayout.EAST);
        contain4.setBorder(new TitledBorder("Điều chỉnh vận tốc:"));
        contain4.add(slider);
        contain4.setLayout(new GridLayout(1, 1));
        containOption.add(contain3);
        containOption.add(contain4);
        containAlgo1.add(checkBox[0]);
        containAlgo1.add(checkBox[3]);
        containAlgo2.add(checkBox[1]);
        containAlgo2.add(checkBox[2]);
        containAlgo2.add(checkBox[4]);
        contain1.add(btnStart);
        contain1.add(btnStop);
        contain1.add(btnSelectAlgorithm);
        contain1.add(btnOpen);
        containerSub.add(containAlgo1);
        containerSub.add(containAlgo2);

        containAction.add(contain1, BorderLayout.NORTH);
        containAction.add(contain2, BorderLayout.CENTER);
        info.add(scrollPanel, BorderLayout.CENTER);
        info.add(contain2, BorderLayout.SOUTH);
        container.add(containerSub);
        container.add(containOption);
        control.add(containAction, BorderLayout.WEST);
        control.add(container, BorderLayout.CENTER);
        add(control, BorderLayout.CENTER);
        add(info, BorderLayout.EAST);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStart) {
            handleClickStart();
        } else if (e.getSource() == btnStop) {
            handleClickStop();
        } else if (e.getSource() == btnSelectAlgorithm) {
            handleSelectAlgorithm();
        } else if (e.getSource() == btnOpen) {
            handleOpenFile();
        } else if (e.getSource() == btnOK) {
            handleChangeSizeRobot();
        }
    }

    private void handleChangeSizeRobot() {
        String size = tfSizeRobot.getText();
        if (size == null || size.isEmpty() || !Utils.isNumeric(size)) {
            JOptionPane.showMessageDialog(null, "Kích thước robot rỗng hoặc không phải là một số",
                    "Lỗi: Số không hợp lệ", JOptionPane.ERROR_MESSAGE);
        } else {
            mConfig.setSizeRobot(Integer.parseInt(size));
            mConfig.setChangeSize(true);
        }
    }

    private void handleOpenFile() {
        JFileChooser fileopen = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Map File", "txt");
        fileopen.addChoosableFileFilter(filter);
        fileopen.setCurrentDirectory(new File("./Stage/bitmaps"));
        int ret = fileopen.showDialog(null, "Open file");
        fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (ret == JFileChooser.APPROVE_OPTION) {
            String file = fileopen.getSelectedFile().getPath();
            mConfig.setFile(file);
        }
        mConfig.setChangeMap(true);
    }

    private void handleSelectAlgorithm() {
        int count = 0;
        for (JCheckBox aCheckBox : checkBox) {
            if (aCheckBox.isSelected())
                count++;
        }
        if (count == 0 || count > 2) {
            JOptionPane.showMessageDialog(null, "Lựa chọn 2 giải thuật!", "Lỗi: Số thuật toán không hợp lệ",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            if (checkBox[0].isSelected() && checkBox[1].isSelected()) {
                mConfig.setSettingView(new int[]{Algorithm.STC_OFF, Algorithm.SSTC_ON});
            } else if (checkBox[0].isSelected() && checkBox[2].isSelected()) {
                mConfig.setSettingView(new int[]{Algorithm.STC_OFF, Algorithm.FULL_SSTC_ON});
            } else if (checkBox[0].isSelected() && checkBox[3].isSelected()) {
                mConfig.setSettingView(new int[]{Algorithm.STC_OFF, Algorithm.SMOOTH_STC_OFF});
            } else if (checkBox[0].isSelected() && checkBox[4].isSelected()) {
                mConfig.setSettingView(new int[]{Algorithm.STC_OFF, Algorithm.SMOOTH_STC_ON});
            } else if (checkBox[1].isSelected() && checkBox[2].isSelected()) {
                mConfig.setSettingView(new int[]{Algorithm.SSTC_ON, Algorithm.FULL_SSTC_ON});
            } else if (checkBox[1].isSelected() && checkBox[3].isSelected()) {
                mConfig.setSettingView(new int[]{Algorithm.SSTC_ON, Algorithm.SMOOTH_STC_OFF});
            } else if (checkBox[1].isSelected() && checkBox[4].isSelected()) {
                mConfig.setSettingView(new int[]{Algorithm.SSTC_ON, Algorithm.SMOOTH_STC_ON});
            } else if (checkBox[2].isSelected() && checkBox[3].isSelected()) {
                mConfig.setSettingView(new int[]{Algorithm.FULL_SSTC_ON, Algorithm.SMOOTH_STC_OFF});
            } else if (checkBox[2].isSelected() && checkBox[4].isSelected()) {
                mConfig.setSettingView(new int[]{Algorithm.FULL_SSTC_ON, Algorithm.SMOOTH_STC_ON});
            } else if (checkBox[3].isSelected() && checkBox[4].isSelected()) {
                mConfig.setSettingView(new int[]{Algorithm.SMOOTH_STC_OFF, Algorithm.SMOOTH_STC_ON});
            }
            mConfig.setSelectAlgorithm(true);
        }
    }

    private void handleClickStop() {
        flag2++;
        if (flag2 % 2 == 1) {
            btnStop.setText("Tiếp tục");
            mConfig.setStop(true);
            validate();
        } else {
            btnStop.setText("Tạm dừng");
            mConfig.setStop(false);
            validate();
        }
    }

    private void handleClickStart() {
        flag1++;
        if (flag1 % 2 == 1) {
            if (mConfig.getStartPoint() == null) {
                JOptionPane.showMessageDialog(null, "Chưa chọn vị trí bắt đầu! Kích chuột để chọn!",
                        "Lỗi: Chưa khởi tạo vị trí ban đầu", JOptionPane.ERROR_MESSAGE);
                flag1++;
            } else {
                btnStart.setText("Khởi động lại");
                for (JCheckBox aCheckBox : checkBox) {
                    aCheckBox.setEnabled(false);
                }
                btnSelectAlgorithm.setEnabled(false);
                btnStop.setEnabled(true);
                btnOpen.setEnabled(false);
                mConfig.setRestart(false);
                mConfig.setStart(true);
                mConfig.setStop(false);

            }
        } else {
            btnStart.setText("Bắt đầu");
            btnSelectAlgorithm.setEnabled(true);
            btnStop.setEnabled(false);
            mConfig.setRestart(true);
            mConfig.setStart(false);
            btnOpen.setEnabled(true);
            mConfig.setStop(true);
            for (JCheckBox aCheckBox : checkBox) {
                aCheckBox.setEnabled(true);
            }
        }
        mConfig.setSelectAlgorithm(false);
        validate();
    }

    @Override
    public void run() {
        while (!stop) {
            if (cbShowGrid.isSelected()) {
                mConfig.setShowGrid(true);
            } else {
                mConfig.setShowGrid(false);
            }
            if (cbShowTrace.isSelected()) {
                mConfig.setShowTrace(true);
            } else {
                mConfig.setShowTrace(false);
            }
            if (cbShowTree.isSelected()) {
                mConfig.setShowTree(true);
            } else {
                mConfig.setShowTree(false);
            }
            Point n = mConfig.getStartPoint();
            if (n != null) {
                tfStart.setText("[" + n.x + " - " + n.y + "]");
                speed.setText(MAX_SPEED - mConfig.getSpeed() + 1 + " km/h");
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            validate();
        }
    }

    @Override
    public synchronized void stateChanged(ChangeEvent e) {
        mConfig.setSpeed(MAX_SPEED - slider.getValue() + 1);
    }

    private static class HeaderRenderer implements TableCellRenderer {

        TableCellRenderer renderer;

        HeaderRenderer(JTable table) {
            renderer = table.getTableHeader().getDefaultRenderer();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int col) {
            return renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        }
    }

}
