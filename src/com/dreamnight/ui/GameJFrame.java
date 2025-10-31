package com.dreamnight.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {

    //管理数据
    int[][] data = new int[4][4];
    int x = 0;
    int y = 0;
    boolean isWin = false; // 标记游戏是否胜利

    int step = 0;

    String path = "img\\city\\1\\";
    String currentCategory = "city"; // 当前图片类别
    int currentFolder = 1; // 当前文件夹编号

    // 各类别的最大文件夹数量
    final int CITY_MAX = 7;
    final int ILLUSTRATION_MAX = 8;
    final int NATURE_MAX = 4;

    JMenuItem cityLabel = new JMenuItem("城市");
    JMenuItem illustration = new JMenuItem("插画");
    JMenuItem natruLabel = new JMenuItem("自然");

    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");

    JMenuItem accountItem = new JMenuItem("关于我们");

    // 计步器标签
    JLabel stepCount = new JLabel("步数：0");

    public GameJFrame() {

        initJFrame();

        initJmenu();

        initData();

        initImage();

        //显示窗口
        this.setVisible(true);

    }

    private void initJFrame() {
        //界面大小
        this.setSize(603, 680); // 稍微增加高度以容纳更好的计步器显示

        //界面标题
        this.setTitle("梦夜de拼图小游戏qwq v0.1");

        //置顶界面
        this.setAlwaysOnTop(true);

        //界面居中
        this.setLocationRelativeTo(null);

        //设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setLayout(null);

        this.addKeyListener(this);

        // 设置背景色为天蓝色
        this.getContentPane().setBackground(new Color(173, 216, 230));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 65) { // A键
            this.getContentPane().removeAll();

            // 加载完整图片并适当缩放
            ImageIcon originalFullIcon = new ImageIcon(path + "all.jpg");
            Image originalFullImage = originalFullIcon.getImage();

            // 计算缩放比例，确保图片完整显示在拼图区域内
            // 拼图区域大小是420x420，我们保持宽高比进行缩放
            int maxWidth = 420;
            int maxHeight = 420;

            int originalWidth = originalFullIcon.getIconWidth();
            int originalHeight = originalFullIcon.getIconHeight();

            // 计算缩放比例
            double widthRatio = (double) maxWidth / originalWidth;
            double heightRatio = (double) maxHeight / originalHeight;
            double scale = Math.min(widthRatio, heightRatio);

            int scaledWidth = (int) (originalWidth * scale);
            int scaledHeight = (int) (originalHeight * scale);

            // 计算居中位置
            int x = 83 + (420 - scaledWidth) / 2;
            int y = 134 + (420 - scaledHeight) / 2; // 调整y坐标以适应新的布局

            Image scaledFullImage = originalFullImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledFullIcon = new ImageIcon(scaledFullImage);

            JLabel all = new JLabel(scaledFullIcon);
            all.setBounds(x, y, scaledWidth, scaledHeight);
            this.getContentPane().add(all);

            // 重新添加计步器
            initStepCounter();

            JLabel background = new JLabel(new ImageIcon("img\\background.jpg"));
            background.setBounds(0, 0, 1000, 1700);
            this.getContentPane().add(background);
            this.getContentPane().repaint();
        } else if (code == 87) { // W键 - 自动完成拼图
            completePuzzle();
            initImage();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // 如果已经胜利，不响应移动操作
        if (isWin) {
            return;
        }

        if (code == 37) {
            //左
            if (y == 3) {
                return;
            }

            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;

            step++;

            initImage();
        } else if (code == 38) {
            //上
            if (x == 3) {
                return;
            }

            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;

            step++;

            initImage();

        } else if (code == 39) {
            //右
            if (y == 0) {
                return;
            }

            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;

            step++;

            initImage();
        } else if (code == 40) {
            //下
            if (x == 0) {
                return;
            }

            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;

            step++;

            initImage();
        } else if (code == 65) {
            initImage();
        }

        // 每次移动后检查是否胜利
        checkWin();
        if (isWin) {
            showWinMessage();
        }
    }

    // 自动完成拼图的方法
    private void completePuzzle() {
        int count = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (count < 16) {
                    data[i][j] = count;
                    count++;
                } else {
                    data[i][j] = 0; // 最后一个位置是空白
                    x = i;
                    y = j;
                }
            }
        }
        // 自动完成后直接设置为胜利状态
        isWin = true;
    }

    // 检查是否胜利的方法
    private void checkWin() {
        // 胜利条件：数字按顺序排列，最后一个位置是0
        int count = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // 最后一个位置应该是0
                if (i == 3 && j == 3) {
                    if (data[i][j] != 0) {
                        isWin = false;
                        return;
                    }
                } else {
                    if (data[i][j] != count) {
                        isWin = false;
                        return;
                    }
                    count++;
                }
            }
        }
        // 如果所有位置都正确，设置胜利状态
        isWin = true;
    }

    // 显示胜利消息的方法
    private void showWinMessage() {
        // 创建一个胜利标签
        JLabel winLabel = new JLabel("恭喜胜利！", JLabel.CENTER);
        winLabel.setBounds(150, 250, 300, 100);
        winLabel.setOpaque(true);

        // 设置胜利标签的样式
        winLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));
        winLabel.setForeground(Color.RED);
        winLabel.setBackground(new Color(255, 255, 255, 200));
        winLabel.setBorder(new BevelBorder(BevelBorder.RAISED));

        // 添加到内容面板
        this.getContentPane().add(winLabel);
        this.getContentPane().setComponentZOrder(winLabel, 0); // 确保在最上层
        this.getContentPane().repaint();

        System.out.println("胜利消息已显示！"); // 调试信息
    }

    private void initData() {
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        Random r = new Random();

        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);

            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;  // 修复了这里的错误，应该是交换两个元素
        }

        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = tempArr[i];
        }

        // 初始化时重置胜利状态
        isWin = false;
    }

    // 初始化计步器显示
    private void initStepCounter() {
        stepCount.setText("步数：" + step);
        stepCount.setBounds(200, 20, 200, 40);
        stepCount.setFont(new Font("微软雅黑", Font.BOLD, 20));
        stepCount.setForeground(Color.DARK_GRAY);
        stepCount.setOpaque(true);
        stepCount.setBackground(new Color(173, 216, 230, 200)); // 半透明天蓝色背景
        stepCount.setBorder(new BevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));
        stepCount.setHorizontalAlignment(JLabel.CENTER);

        this.getContentPane().add(stepCount);
    }

    private void initImage() {
        this.getContentPane().removeAll();

        // 初始化计步器
        initStepCounter();

        int imageSize = 105; // 图片大小

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int number = data[i][j];

                // 如果是0（空白格子），跳过不显示图片
                if (number == 0) {
                    continue;
                }

                String address;
                if (number < 10) {
                    address = "0" + String.valueOf(number);
                } else {
                    address = String.valueOf(number);
                }

                // 加载原始图片
                ImageIcon originalIcon = new ImageIcon(path + "part_" + address + ".jpg");

                // 缩放图片到指定大小
                Image originalImage = originalIcon.getImage();
                Image scaledImage = originalImage.getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                JLabel jLabel = new JLabel(scaledIcon);

                // 设置位置 - 调整y坐标以适应新的布局
                jLabel.setBounds(j * imageSize + 83, i * imageSize + 134, imageSize, imageSize);

                jLabel.setBorder(new BevelBorder(1));

                this.getContentPane().add(jLabel);
            }
        }

        JLabel background = new JLabel(new ImageIcon("img\\background.jpg"));
        background.setBounds(0, 0, 1000, 1700);
        this.getContentPane().add(background);

        // 如果胜利了，显示胜利消息
        if (isWin) {
            showWinMessage();
        }

        this.getContentPane().repaint();
    }

    private void initJmenu() {
        //初始化菜单
        //创建菜单对象
        JMenuBar jMenuBar = new JMenuBar();

        //创建选项对象
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于");

        //创建选项内功能
        JMenu changeMenu = new JMenu("切换图片");

        functionJMenu.add(changeMenu);
        changeMenu.add(cityLabel);
        changeMenu.add(illustration);
        changeMenu.add(natruLabel);

        //添加功能
        functionJMenu.add(replayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);

        aboutJMenu.add(accountItem);

        cityLabel.addActionListener(this);
        illustration.addActionListener(this);
        natruLabel.addActionListener(this);

        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);

        accountItem.addActionListener(this);

        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        this.setJMenuBar(jMenuBar);
    }

    // 随机选择文件夹的方法
    private void selectRandomFolder() {
        Random random = new Random();
        int maxFolder = 0;

        switch (currentCategory) {
            case "city":
                maxFolder = CITY_MAX;
                break;
            case "illustration":
                maxFolder = ILLUSTRATION_MAX;
                break;
            case "nature":
                maxFolder = NATURE_MAX;
                break;
        }

        if (maxFolder > 0) {
            currentFolder = random.nextInt(maxFolder) + 1;
            path = "img\\" + currentCategory + "\\" + currentFolder + "\\";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == replayItem) {
            step = 0;
            initData();
            initImage();
        } else if (obj == reLoginItem) {
            this.setVisible(false);
            new LoginJFrame();
        } else if (obj == closeItem) {
            System.exit(EXIT_ON_CLOSE);
        } else if (obj == accountItem) {
            showAboutDialog();
        } else if (obj == cityLabel) {
            currentCategory = "city";
            selectRandomFolder();
            initData();
            initImage();
        } else if (obj == illustration) {
            currentCategory = "illustration";
            selectRandomFolder();
            initData();
            initImage();
        } else if (obj == natruLabel) {
            currentCategory = "nature";
            selectRandomFolder();
            initData();
            initImage();
        }
    }

    // 显示关于我们对话框的方法 - 现代简约风格（优化蓝白logo版）
    private void showAboutDialog() {
        JDialog aboutDialog = new JDialog(this, "关于我们", true);
        aboutDialog.setSize(500, 400);
        aboutDialog.setLocationRelativeTo(this);
        aboutDialog.setLayout(null);

        // 创建渐变背景面板 - 调整为更适合蓝白logo的配色
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // 创建蓝白渐变背景，与蓝白logo协调
                Color color1 = new Color(200, 220, 255); // 淡蓝色
                Color color2 = new Color(220, 230, 255); // 更淡的蓝色
                GradientPaint gradient = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // 添加微妙的背景纹理
                g2d.setColor(new Color(255, 255, 255, 30));
                for (int i = 0; i < getWidth(); i += 20) {
                    for (int j = 0; j < getHeight(); j += 20) {
                        g2d.fillOval(i, j, 2, 2);
                    }
                }
            }
        };
        gradientPanel.setLayout(null);
        gradientPanel.setBounds(0, 0, 500, 400);
        aboutDialog.add(gradientPanel);

        // 加载并显示logo图片 - 针对透明底蓝白logo优化
        try {
            ImageIcon originalLogoIcon = new ImageIcon("C:\\Users\\DreamNight\\Documents\\java\\game_test\\game_test\\img\\about.png");
            Image originalLogoImage = originalLogoIcon.getImage();

            // 计算合适的缩放尺寸，保持宽高比
            int maxWidth = 280; // 稍微增大尺寸以展示更多细节
            int maxHeight = 280;
            int originalWidth = originalLogoIcon.getIconWidth();
            int originalHeight = originalLogoIcon.getIconHeight();

            double widthRatio = (double) maxWidth / originalWidth;
            double heightRatio = (double) maxHeight / originalHeight;
            double scale = Math.min(widthRatio, heightRatio);

            int scaledWidth = (int) (originalWidth * scale);
            int scaledHeight = (int) (originalHeight * scale);

            // 使用高质量缩放
            Image scaledLogoImage = originalLogoImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledLogoIcon = new ImageIcon(scaledLogoImage);

            JLabel logoLabel = new JLabel(scaledLogoIcon);
            logoLabel.setBounds((500 - scaledWidth) / 2, 50, scaledWidth, scaledHeight);

            // 移除边框，让透明底logo完美融入背景
            logoLabel.setBorder(null);

            // 添加微妙的阴影效果，增强立体感但不使用边框
            logoLabel.setOpaque(false);

            gradientPanel.add(logoLabel);
        } catch (Exception e) {
            // 如果图片加载失败，显示简约占位符
            JLabel errorLabel = new JLabel("Dream Night", JLabel.CENTER);
            errorLabel.setBounds(180, 80, 140, 140);
            errorLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
            errorLabel.setOpaque(false);
            errorLabel.setForeground(new Color(100, 149, 237));
            gradientPanel.add(errorLabel);
        }

        // 版本信息
        JLabel versionLabel = new JLabel("版本 v0.1", JLabel.CENTER);
        versionLabel.setBounds(0, 245, 500, 25);
        versionLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        versionLabel.setForeground(new Color(100, 100, 120));
        gradientPanel.add(versionLabel);

        // 作者信息
        JLabel authorLabel = new JLabel("作者: DreamNight", JLabel.CENTER);
        authorLabel.setBounds(0, 270, 500, 25);
        authorLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        authorLabel.setForeground(new Color(100, 100, 120));
        gradientPanel.add(authorLabel);

        // 分隔线 - 使用更淡的颜色
        JSeparator separator = new JSeparator();
        separator.setBounds(50, 305, 400, 1);
        separator.setForeground(new Color(200, 210, 220));
        gradientPanel.add(separator);

        // 关闭按钮 - 使用与logo协调的蓝色
        JButton closeButton = new JButton("关闭");
        closeButton.setBounds(200, 325, 100, 35);
        closeButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        closeButton.setBackground(new Color(100, 149, 237)); // 使用矢车菊蓝
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        closeButton.addActionListener(e -> aboutDialog.dispose());

        // 添加鼠标悬停效果
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(new Color(65, 105, 225)); // 更深的蓝色
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(new Color(100, 149, 237));
            }
        });

        gradientPanel.add(closeButton);

        aboutDialog.setVisible(true);
    }
}
