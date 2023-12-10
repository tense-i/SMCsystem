package stu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import MainBody.DBUtil;
import selects.SelectsModel;

public class AddStu extends JFrame implements ActionListener {
    JTextField snoField;
    JTextField nameField;
    JTextField sexField;
    JTextField ageField;
    JTextField sdeptField;
    public AddStu() {
        setTitle("添加用户");
        setLayout(null);
        setDefaultCloseOperation(AddStu.DISPOSE_ON_CLOSE);
        setBounds(300,300,500,500);
        JLabel snoJLabel=new JLabel("学号");
        JLabel nameJLabel=new JLabel("姓名");
        JLabel sexJLabel=new JLabel("性别");
        JLabel ageJLabel=new JLabel("年龄");
        JLabel sdeptJLabel=new JLabel("系别");

        snoField=new JTextField();
        nameField=new JTextField();
        sexField=new JTextField();
        ageField=new JTextField();
        sdeptField=new JTextField();
        //设置控件位置和大小
        snoJLabel.setBounds(50,20,100,100);
        snoField.setBounds(100,50,200,30);
        nameJLabel.setBounds(50,70,100,100);
        nameField.setBounds(100,100,200,30);
        sexJLabel.setBounds(50,120,100,100);
        sexField.setBounds(100,150,200,30);
        ageJLabel.setBounds(50,170,100,100);
        ageField.setBounds(100,200,200,30);
        sdeptJLabel.setBounds(50,220,100,100);
        sdeptField.setBounds(100,250,200,30);
        JButton yesButton=new JButton("确定");
        yesButton.setBounds(100,300,80,30);
        JButton noButton=new JButton("取消");
        noButton.setBounds(200,300,80,30);
        add(snoJLabel);
        add(snoField);
        add(nameJLabel);
        add(nameField);
        add(sexJLabel);
        add(sexField);
        add(ageJLabel);
        add(ageField);
        add(sdeptJLabel);
        add(sdeptField);
        add(yesButton);
        add(noButton);
        //给添加用户设置的确定和取消按钮的监听事件
        yesButton.setActionCommand("确定");
        yesButton.addActionListener(this);
        noButton.setActionCommand("取消");
        noButton.addActionListener(this);

        setVisible(true);
       // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("确定")) {

            try {
                DBUtil.initst();
                String sno=snoField.getText();
                String regex= "[1-9]\\d*";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(sno);
                // 进行匹配
                if (matcher.matches()) {
                } else {
                    // 输入不符合正则表达式
                    JOptionPane.showMessageDialog(AddStu.this, "无效学号", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String stuname=nameField.getText();
                regex="[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+";
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(stuname);
                if (matcher.matches()) {
                } else {
                    // 输入不符合正则表达式
                    JOptionPane.showMessageDialog(AddStu.this, "无效姓名", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String sex=sexField.getText();
                if(!sex.equals("男")&&!sex.equals("女"))
                {
                    JOptionPane.showMessageDialog(AddStu.this, "无效性别", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String age=ageField.getText();
                if (!age.trim().isEmpty()) {
                    // 在这里解析整数
                    int ageInt = Integer.parseInt(age.trim());
                    if(ageInt<1&&ageInt>=90)
                    {
                        JOptionPane.showMessageDialog(AddStu.this, "无效年龄", "提示", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } else {
                    // 处理输入为空或仅包含空格的情况
                    JOptionPane.showMessageDialog(AddStu.this, "无效年龄", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String sdept=sdeptField.getText();
                if(sdept==null)
                {
                    JOptionPane.showMessageDialog(AddStu.this, "无效院系", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int n=DBUtil.st.executeUpdate("insert into stu values('"+sno+"','"+stuname+"','"+sex+"','"+age+"','"+sdept+"')");
                if (n>0) {
                    JOptionPane.showMessageDialog(null, "添加成功");
                } else {
                    JOptionPane.showMessageDialog(null, "添加失败");
                }
                DBUtil.closeDB();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            DBUtil.closeDB();
        }else {
            this.hide();
            UserModel.reload(null);
        }
    }
    public static void main(String[] args) {
        AddStu addStu=new AddStu();
    }
}