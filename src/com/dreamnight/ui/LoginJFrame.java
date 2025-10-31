package com.dreamnight.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class LoginJFrame extends JFrame implements ActionListener {

    // 组件声明
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel titleLabel;
    private JLabel statusLabel;

    // 用户数据文件路径
    private static final String USER_DATA_FILE = "users.dat";

    // 存储用户数据
    private Map<String, String> users;

    public LoginJFrame() {
        initJFrame();
        loadUserData();
        initComponents();
        this.setVisible(true);
    }

    private void initJFrame() {
        // 界面大小
        this.setSize(500, 400);

        // 界面标题
        this.setTitle("DreamNight Login v1.0");

        // 置顶界面
        this.setAlwaysOnTop(true);

        // 界面居中
        this.setLocationRelativeTo(null);

        // 设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 设置布局
        this.setLayout(null);

        // 设置欢快的浅蓝色背景
        this.getContentPane().setBackground(new Color(225, 240, 255));
    }

    private void initComponents() {
        // 标题
        titleLabel = new JLabel("DREAM NIGHT");
        titleLabel.setBounds(0, 40, 500, 50);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(70, 130, 180)); // 钢蓝色
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleLabel);

        // 副标题
        JLabel subtitleLabel = new JLabel("拼图游戏登录系统");
        subtitleLabel.setBounds(0, 85, 500, 25);
        subtitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(100, 149, 237)); // 矢车菊蓝
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(subtitleLabel);

        // 用户名标签
        JLabel usernameLabel = new JLabel("用户名");
        usernameLabel.setBounds(100, 130, 300, 20);
        usernameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        usernameLabel.setForeground(new Color(70, 130, 180));
        this.add(usernameLabel);

        // 用户名输入框
        usernameField = new JTextField();
        usernameField.setBounds(100, 150, 300, 40);
        setupTextField(usernameField);
        this.add(usernameField);

        // 密码标签
        JLabel passwordLabel = new JLabel("密码");
        passwordLabel.setBounds(100, 200, 300, 20);
        passwordLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        passwordLabel.setForeground(new Color(70, 130, 180));
        this.add(passwordLabel);

        // 密码输入框
        passwordField = new JPasswordField();
        passwordField.setBounds(100, 220, 300, 40);
        setupPasswordField(passwordField);
        this.add(passwordField);

        // 登录按钮
        loginButton = new JButton("登录");
        setupLoginButton(loginButton);
        loginButton.setBounds(150, 280, 200, 40);
        this.add(loginButton);

        // 注册按钮
        registerButton = new JButton("注册新账户");
        setupRegisterButton(registerButton);
        registerButton.setBounds(150, 330, 200, 30);
        this.add(registerButton);

        // 状态标签
        statusLabel = new JLabel("");
        statusLabel.setBounds(150, 370, 200, 20);
        statusLabel.setForeground(new Color(220, 20, 60)); // 深红色
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        this.add(statusLabel);
    }

    private void setupTextField(JTextField field) {
        field.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        field.setBackground(Color.WHITE);
        field.setForeground(new Color(70, 130, 180));
        field.setCaretColor(new Color(70, 130, 180));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(135, 206, 250), 2), // 浅蓝色边框
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.addActionListener(this);
    }

    private void setupPasswordField(JPasswordField field) {
        field.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        field.setBackground(Color.WHITE);
        field.setForeground(new Color(70, 130, 180));
        field.setCaretColor(new Color(70, 130, 180));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(135, 206, 250), 2), // 浅蓝色边框
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.addActionListener(this);
    }

    private void setupLoginButton(JButton button) {
        button.setFont(new Font("微软雅黑", Font.BOLD, 16));
        button.setBackground(new Color(100, 149, 237)); // 矢车菊蓝
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 添加圆角效果
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));

        // 添加悬停效果
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(65, 105, 225)); // 皇家蓝
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(30, 144, 255), 2),
                        BorderFactory.createEmptyBorder(10, 0, 10, 0)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
                        BorderFactory.createEmptyBorder(10, 0, 10, 0)
                ));
            }
        });

        button.addActionListener(this);
    }

    private void setupRegisterButton(JButton button) {
        button.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        button.setBackground(new Color(225, 240, 255));
        button.setForeground(new Color(70, 130, 180));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 透明背景
        button.setContentAreaFilled(false);

        // 添加悬停效果
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(30, 144, 255)); // 道奇蓝
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(30, 144, 255)),
                        BorderFactory.createEmptyBorder(5, 0, 4, 0)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(70, 130, 180));
                button.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            }
        });

        button.addActionListener(this);
    }

    // 加载用户数据
    private void loadUserData() {
        users = new HashMap<>();
        File file = new File(USER_DATA_FILE);

        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                users = (Map<String, String>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                // 如果文件损坏或不存在，使用默认账户
                users.put("admin", "admin123");
                saveUserData();
            }
        } else {
            // 创建默认账户
            users.put("admin", "admin123");
            saveUserData();
        }
    }

    // 保存用户数据
    private void saveUserData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_DATA_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            showError("保存用户数据失败: " + e.getMessage());
        }
    }

    // 验证登录
    private boolean validateLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            showError("用户名和密码不能为空");
            return false;
        }

        if (!users.containsKey(username)) {
            showError("用户名不存在");
            return false;
        }

        if (!users.get(username).equals(password)) {
            showError("密码错误");
            return false;
        }

        return true;
    }

    // 显示错误信息
    private void showError(String message) {
        statusLabel.setForeground(new Color(220, 20, 60)); // 深红色
        statusLabel.setText(message);
    }

    // 显示成功信息
    private void showSuccess(String message) {
        statusLabel.setForeground(new Color(50, 205, 50)); // 酸橙绿
        statusLabel.setText(message);
    }

    // 处理登录
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (validateLogin(username, password)) {
            showSuccess("登录成功！");

            // 延迟跳转，让用户看到成功消息
            Timer timer = new Timer(1000, e -> {
                this.dispose();
                new GameJFrame();
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton || e.getSource() == usernameField || e.getSource() == passwordField) {
            handleLogin();
        } else if (e.getSource() == registerButton) {
            // 打开注册界面
            this.dispose();
            new RegisterJFrame();
        }
    }

    public static void main(String[] args) {
        // 设置系统样式
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new LoginJFrame();
    }
}
