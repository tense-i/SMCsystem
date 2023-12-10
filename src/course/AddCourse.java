package course;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import MainBody.DBUtil;
import selects.SelectsModel;
import stu.AddStu;

public class AddCourse extends JFrame implements ActionListener {
    JTextField cnoField;
    JTextField cnameField;
    JTextField creditField;
    public AddCourse() {
        setTitle("添加课程");
        setLayout(null);
        setBounds(300,300,500,500);
        //添加文本控件
        JLabel labCno=new JLabel("课程号");
        JLabel labCname=new JLabel("课程名");
        JLabel labCredit=new JLabel("学分");

        cnoField=new JTextField();
        cnameField=new JTextField();
        creditField=new JTextField();

        //设置控件位置和大小
        labCno.setBounds(50,20,100,100);
        cnoField.setBounds(100,50,200,30);

        labCname.setBounds(50,70,100,100);
        cnameField.setBounds(100,100,200,30);

        labCredit.setBounds(50,120,100,100);
        creditField.setBounds(100,150,200,30);

        JButton yesButton=new JButton("确定");
        yesButton.setBounds(100,300,80,30);
        JButton noButton=new JButton("取消");
        noButton.setBounds(200,300,80,30);

        add(labCno);
        add(cnoField);
        add(labCname);
        add(cnameField);
        add(labCredit);
        add(creditField);

        add(yesButton);
        add(noButton);
        //给添加用户设置的确定和取消按钮的监听事件
        yesButton.setActionCommand("确定");
        yesButton.addActionListener(this);
        noButton.setActionCommand("取消");
        noButton.addActionListener(this);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("确定")) {
            try {
                DBUtil.initst();
                String cno=cnoField.getText();
                String cname=cnameField.getText();
                String credit=creditField.getText();

                int n=DBUtil.st.executeUpdate("insert into course values('"+cno+"','"+cname+"','"+credit+"')");
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
            CourseModel.reload(null);
        }
    }
    public static void main(String[] args) {
        AddCourse AddCourse=new AddCourse();
    }
}