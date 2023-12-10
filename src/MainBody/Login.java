package MainBody;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class Login extends JFrame implements ActionListener {
    public static Main MainWindow;
    static boolean flg=false;
    //editUserName
    JTextField nameField;
    JPasswordField passwordField;
    private  boolean insertIntoDatabase(String name,String psw) throws SQLException {

        String sql = "INSERT INTO user (name, password) VALUES ('"+name+"' ,"+psw+")";
        DBUtil.initst();

        int rowsAffected = DBUtil.st.executeUpdate(sql);

        if (rowsAffected > 0) {
            System.out.println("插入数据库成功。");
            return true;
        } else {
            System.out.println("插入失败。");
            return false;
        }
    }
    private  boolean DeleteIntoDatabase(String name,String psw) throws SQLException {

        String sql = "delete from user where `name`= '"+name+"'";
        DBUtil.initst();
        int result = JOptionPane.showConfirmDialog(Login.this, "确定删除管理员--"+name, "提示",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.out.println("User clicked Yes");
        } else if (result == JOptionPane.NO_OPTION) {
            System.out.println("User clicked No");
            return false;
        }
        int rowsAffected = DBUtil.st.executeUpdate(sql);

        if (rowsAffected > 0) {
            System.out.println("注销成功。");
            return true;
        } else {
            System.out.println("注销失败。");
            return false;
        }
    }

    public static   Image getImage(ImageIcon originalIcon, int x, int y){

        Image originalImage = originalIcon.getImage();
        // 获取当前屏幕的分辨率，以便计算缩放比例
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int screenWidth = toolkit.getScreenSize().width;
        int screenHeight = toolkit.getScreenSize().height;

        // 计算缩放比例
        double scaleFactorWidth = (double) screenWidth / originalImage.getWidth(null);
        double scaleFactorHeight = (double) screenHeight / originalImage.getHeight(null);
        double scaleFactor = Math.min(scaleFactorWidth, scaleFactorHeight);

        // 缩放图像
        Image scaledImage = originalImage.getScaledInstance(
                x,
                y,
                Image.SCALE_SMOOTH
        );
        return scaledImage;
    }
    public Login() {

        JPanel panel = new ImagePanel();
        add(panel);
        panel.setLayout(null);

        setTitle("系统登录");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500,200,420,300);
        //添加账号密码文本及文本框
        //JLabel labUserIcon=new JLabel();
        ImageIcon icon1=new ImageIcon("res/Icon/icon_ih4qrlvm887/yonghuming_2.png");
        ImageIcon IconUser = new ImageIcon(getImage(icon1,20,20));

        ImageIcon icon2=new ImageIcon("D:\\Code\\Stu_3\\res\\Icon\\icon_ih4qrlvm887\\mima_2.png");
        ImageIcon IconUserPswd = new ImageIcon(getImage(icon2,20,20));

        JLabel labEyes=new JLabel();

        ImageIcon icon3=new ImageIcon("D:\\Code\\Stu_3\\res\\Icon\\icon_ih4qrlvm887\\guanbi.png");
          icon3 = new ImageIcon(getImage(icon3,30,30));
        JLabel nameJLabel=new JLabel("账号");
        nameField=new JTextField();
        JLabel passJLabel=new JLabel("密码");
        passwordField=new JPasswordField();
       // labUserIcon.setBounds(30,15,50,100);
        nameJLabel.setBounds(80,15,100,100);
        nameField.setBounds(145,50,200,30);
        passJLabel.setBounds(80,70,100,100);
        passwordField.setBounds(145,100,200,30);
        labEyes.setBounds(350,100,30,30);
        labEyes.setIcon(icon3);
        nameJLabel.setIcon(IconUser);
        passJLabel.setIcon(IconUserPswd);
        //登录取消按钮
        JButton loginButton=new JButton("登录");
        JButton cancelButton=new JButton("取消");
        JButton signinBtn=new JButton("注册");
        JButton unsubscribeBtn=new JButton("注销");
        loginButton.setBounds(20,150,80,30);
        cancelButton.setBounds(120,150,80,30);
        signinBtn.setBounds(220,150,80,30);
        unsubscribeBtn.setBounds(320,150,80,30);
        //添加到面版

        add(nameJLabel);
        add(nameField);
        add(passJLabel);
        add(passwordField);
        add(labEyes);
        add(signinBtn);
        add(loginButton);
        add(cancelButton);
        add(unsubscribeBtn);

        //给登录取消按钮添加控件
        loginButton.setActionCommand("登录");
        cancelButton.setActionCommand("取消");
        signinBtn.setActionCommand("注册");
        unsubscribeBtn.setActionCommand("注销");
        loginButton.addActionListener(this);
        cancelButton.addActionListener(this);
        signinBtn.addActionListener(this);
        unsubscribeBtn.addActionListener(this);
        setVisible(true);

        labEyes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("点击");
                if(!flg){
                    passwordField.setEchoChar('\0');
                    flg=true;
                }
                else {
                    passwordField.setEchoChar('*');
                    flg=false;
                }
            }
        });
    }

    //登录
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("登录")) {
            try {
                DBUtil.initst();
                String stuname=nameField.getText();
                String stupwd=passwordField.getText();
                if (stuname.isEmpty() || stupwd.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this,
                            "用户名和密码不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //根据数据库表中的用户名查密码进行匹配
                DBUtil.rs=DBUtil.st.executeQuery("select password from user where name='"+stuname+"'");
                if (DBUtil.rs.next()) {
                    if (DBUtil.rs.getString(1).equals(stupwd)) {
                        //如果密码正确就显示主页面
                        this.hide();
                        MainWindow=new Main();
                        MainWindow.show();
                    } else {
                        //如果密码错误弹出框
                        JOptionPane.showMessageDialog(null, "密码错误，请联系管理员");
                    }
                }else {
                    //姓名不对弹出提示框
                    JOptionPane.showMessageDialog(null, "用户不存在");
                }
                DBUtil.closeDB();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            //注册
        }else if(e.getActionCommand().equals("注册"))
        {
            DBUtil.initst();
            String username = nameField.getText().trim();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);
            // 验证用户输入
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(Login.this,
                        "用户名和密码不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                ResultSet s=DBUtil.st.executeQuery("select `name` from user");
                while (s.next())
                {
                    String name=s.getString("name");
                    if(name.equals(username))
                    {
                        JOptionPane.showMessageDialog(Login.this, "用户名重复、换一个试试");
                        return ;
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            // 将注册信息插入数据库
            try {
                if (insertIntoDatabase(username, password)) {
                    JOptionPane.showMessageDialog(Login.this, "注册成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(Login.this,
                            "注册失败，请重试。", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            //注销
        }else if(e.getActionCommand().equals("注销")){
            DBUtil.initst();
            String username = nameField.getText().trim();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);
            // 验证用户输入
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(Login.this,
                        "注销用户名和密码不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // 将注册信息插入数据库
            try {
                if (DeleteIntoDatabase(username, password)) {
                    JOptionPane.showMessageDialog(Login.this,
                            "注册成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(Login.this,
                            "注册失败，请重试。", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        else {
            System.exit(0);
        }
    }

    class ImagePanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon icon = new ImageIcon("icon/1.jpg");
            g.drawImage(icon.getImage(), 0, 0, null);
        }
    }
    public static void main(String[] args) {
        Login login=new Login();
    }
}
























