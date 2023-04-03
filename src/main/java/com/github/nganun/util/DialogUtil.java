package com.github.nganun.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DialogUtil {

    private static String FONT_NAME = "宋体";
    private TipWindow tipWindow = null; // 提示框
    private JPanel headPan = null, feaPan = null;
    private JLabel title = null; // 栏目名称
    private JLabel head = null; // 蓝色标题
    private JLabel titleClose = null; // 关闭按钮
    private JTextArea feature = null; // 内容
    private JScrollPane jfeaPan = null;
    private String titleValue = null;
    private String contentValue = null;
// private SimpleDateFormat sdf = new
// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void init() {
// 新建300x180的消息提示框
        tipWindow = new TipWindow(300, 120);
        headPan = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        feaPan = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        title = new JLabel("欢迎使用本系统");
        head = new JLabel(titleValue);
        titleClose = new JLabel(" x");
        feature = new JTextArea(contentValue);
        jfeaPan = new JScrollPane(feature);
// 设置提示框的边框,宽度和颜色
        tipWindow.getRootPane().setBorder(
                BorderFactory.createMatteBorder(2, 2, 2, 2, Color.lightGray));
        title.setPreferredSize(new Dimension(260, 26));
        title.setVerticalTextPosition(JLabel.CENTER);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setFont(new Font(FONT_NAME, Font.PLAIN, 12));
        title.setForeground(Color.black);

        titleClose.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        titleClose.setPreferredSize(new Dimension(20, 20));
        titleClose.setVerticalTextPosition(JLabel.CENTER);
        titleClose.setHorizontalTextPosition(JLabel.CENTER);
        titleClose.setCursor(new Cursor(12));
        titleClose.setToolTipText("Close");

        head.setPreferredSize(new Dimension(250, 26));
        head.setVerticalTextPosition(JLabel.CENTER);
        head.setHorizontalTextPosition(JLabel.CENTER);
        head.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        head.setForeground(Color.black);

        feature.setEditable(false);
        feature.setForeground(Color.BLACK);
        feature.setFont(new Font(FONT_NAME, Font.PLAIN, 13));
        feature.setBackground(new Color(255, 255, 255));
        // 设置文本域自动换行
        feature.setLineWrap(true);

        jfeaPan.setPreferredSize(new Dimension(260, 100));
        jfeaPan.setBorder(null);
        jfeaPan.setBackground(Color.black);
        tipWindow.setBackground(Color.white);
        // 为了隐藏文本域，加个空的JLabel将他挤到下面去
        JLabel jsp = new JLabel();
        jsp.setPreferredSize(new Dimension(300, 15));

        // headPan.add(title);
        headPan.add(head);
        headPan.add(titleClose);
        feaPan.add(jsp);
        feaPan.add(jfeaPan);
        // feaPan.add(releaseLabel);
        headPan.setBackground(new Color(104, 141, 177));
        feaPan.setBackground(Color.white);
        tipWindow.add(headPan, BorderLayout.NORTH);
        tipWindow.add(feaPan, BorderLayout.CENTER);
    }
    public void handle() {
        // 右上角关闭按钮事件
        titleClose.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tipWindow.tipClose();
            }
            public void mouseEntered(MouseEvent e) {
                titleClose.setBorder(BorderFactory.createLineBorder(Color.gray));
            }
            public void mouseExited(MouseEvent e) {
                titleClose.setBorder(null);
            }
        });
    }

    public void show(String titleValue, String contentValue) {
        this.show(titleValue, contentValue, 5000);
    }
    public void show(String titleValue, String contentValue, int showTime) {
        this.titleValue = titleValue;
        this.contentValue = contentValue;

        init();
        handle();
        tipWindow.setShowTime(showTime);
        tipWindow.setAlwaysOnTop(false);
        tipWindow.setUndecorated(true);
        tipWindow.setResizable(false);
        tipWindow.setVisible(true);
        tipWindow.run();
    }

    public static void main(String[] args) {
        DialogUtil test = new DialogUtil();
        test.show("Alert", "This is a Alert Testing...");
    }
}
class TipWindow extends JDialog {
    private static final long serialVersionUID = 8541659783234673950L;
    private static Dimension dimension;
    public  int showTime = 5000;

    public void setShowTime(int showTime) {
        this.showTime = showTime;
    }
    private int x, y;
    private int width, height;
    private static Insets screenInsets;
    public TipWindow(int width, int height) {
        this.width = width;
        this.height = height;
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
        x = (int) (dimension.getWidth() - width - 3);
        y = (int) (dimension.getHeight() - screenInsets.bottom - 3);

        this.setSize(width, height);
        this.setLocation(x, y);
        this.setBackground(Color.black);
    }
    public void run() {
        for (int i = 0; i <= height; i += 20) {
            try {
                this.setLocation(x, y - i);
                Thread.sleep(50);
            } catch (InterruptedException ex) {
            }
        }
        // 此处代码用来实现让消息提示框 3 秒后自动消失
        try {
            Thread.sleep(showTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tipClose();
    }

    public void tipClose() {
        x = this.getX();
        y = this.getY();
        int ybottom = (int) dimension.getHeight() - screenInsets.bottom;
        for (int i = 0; i <= ybottom - y; i += 20) {
            try {
                setLocation(x, y + i);
                Thread.sleep(50);
            } catch (InterruptedException ex) {
            }
        }
        dispose();
    }
}
