package MainBody;
import java.awt.event.*;
import java.net.URL;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.file.Watchable;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.print.attribute.standard.JobName;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;

import course.AddCourse;
import course.CourseModel;
import course.UpdateCourse;
import selects.AddSelect;
import selects.SelectsModel;
import selects.UpdateSelect;
import stu.AddStu;
import stu.UpdateStu;
import stu.UserModel;

public class Main extends JFrame {
    public   JTable jTable;
    public static Main mainWindow;
    public static UserModel userModel;
    public static CourseModel coursemodel;
    public static SelectsModel selectsmodel;
    {
        jTable=new JTable();
    }
    JTextField exit;

    public JTable getjTable() {
        return jTable;
    }

    private static void updateTime(JLabel timeLabel) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());
        timeLabel.setText(currentTime);
    }
    private static void updateDateLabel(JLabel dateLabel) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = sdf.format(new Date());
        dateLabel.setText(currentDate);
    }
    private static String readSqlScript(String filePath) throws Exception {
        StringBuilder scriptContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scriptContent.append(line).append("\n");
            }
        }
        return scriptContent.toString();
    }

    private static void executeSqlScript( String sqlScript) throws Exception {
             DBUtil.initst();
            // 拆分 SQL 脚本中的语句
            String[] sqlStatements = sqlScript.split(";");
            // 逐条执行 SQL 语句
            for (String sqlStatement : sqlStatements) {
                if (!sqlStatement.trim().isEmpty()) {
                    DBUtil.st.execute(sqlStatement);
                }
            }
    }

    public Main() {

        setTitle("学生选课管理系统");
        setBackground(Color.GRAY);
        setBounds(300, 100, 1000, 800);
        this.setResizable(false);
    //初始化SQL查询部分
        String sql = "select * from stu";
        userModel = new UserModel();
        userModel.init(sql);

        String sql1 = "select * from course";
        coursemodel = new CourseModel();
        coursemodel.init(sql1);

        String sql2 = "SELECT sc.sno,stu.name,sc.cno,course.cname,credit,score FROM sc,stu,course where sc.sno=stu.sno and course.cno=sc.cno";
        selectsmodel = new SelectsModel();
        selectsmodel.init(sql2);

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem itemDelete=new JMenuItem("删除");
        //加入到右击菜单
        //popupMenu.add(itemDelete);
    //初始化学生表
        // 学生表

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(jTable);

        add(jScrollPane, BorderLayout.CENTER);

        // add(dome, BorderLayout.SOUTH);
        // 菜单栏
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        // 学生信息菜单栏
        JMenu user = new JMenu("  学生管理  ");
            menuBar.add(user);
            JMenuItem addstu = new JMenuItem("增加学生");
            JMenuItem updatestu = new JMenuItem("修改学生");
            JMenuItem deletestu = new JMenuItem("删除学生");
            user.add(addstu);
            user.add(updatestu);
            user.add(deletestu);

        // 课程管理菜单栏
        JMenu course = new JMenu("  课程管理  ");
            menuBar.add(course);
            JMenuItem addcourse = new JMenuItem("增加课程");
            JMenuItem updatecourse = new JMenuItem("修改课程");
            JMenuItem deletecourse = new JMenuItem("删除课程");
            course.add(addcourse);
            course.add(updatecourse);
            course.add(deletecourse);

        // 选课管理菜单栏
        JMenu select = new JMenu("  选课管理  ");
            menuBar.add(select);
            JMenuItem addselect = new JMenuItem("添加选课");
            JMenuItem updateselect = new JMenuItem("修改选课");
            JMenuItem deleteselect = new JMenuItem("删除选课");
            select.add(addselect);
            select.add(updateselect);
            select.add(deleteselect);

        // 查询管理菜单栏
        JMenu query = new JMenu("  查询管理  ");
            menuBar.add(query);
            JMenuItem querystu = new JMenuItem("查询所有学生");
            JMenuItem querycourse = new JMenuItem("查询所有课程");
            JMenuItem queryselect = new JMenuItem("查询所有选课");
            query.add(querystu);
            query.add(querycourse);
            query.add(queryselect);

        // 系统退出
        JLabel exit = new JLabel("    系统退出");
            exit.setForeground(Color.red);
            exit.setFont(new java.awt.Font("宋体", 1, 15));
            menuBar.add(exit);
        // 查询区
        //panel 相当于QWidget
        JPanel jPaneln = new JPanel();
        JLabel nameJLabel = new JLabel("学号/课程号：");
        nameJLabel.setFont(new Font("Microsoft YaHei",1,15));
        JTextField nameField = new JTextField(20);
        JLabel selectbtn = new JLabel();
        ImageIcon icon5=new ImageIcon("D:\\Code\\Stu_3\\res\\Icon\\icon_ih4qrlvm887\\chaxun_1.png");
        icon5 = new ImageIcon(Login.getImage(icon5,30,30));
        selectbtn.setSize(30,30);
        selectbtn.setIcon(icon5);
        JLabel labWeather=new JLabel();

        ImageIcon icon7=new ImageIcon("D:\\Code\\Stu_3\\res\\Icon\\icon_ih4qrlvm887\\shijian_3.png");
        icon7 = new ImageIcon(Login.getImage(icon7,30,30));

        JLabel labDate=new JLabel();
        labDate.setIcon(icon7);
        labDate.setFont(new Font("Helvetica", Font.BOLD, 20));
        SpringLayout layout = new SpringLayout();
            jPaneln.add(nameJLabel);
            jPaneln.add(nameField);
            jPaneln.add(selectbtn);
            jPaneln.add(Box.createHorizontalStrut(20));
            jPaneln.add(labDate);
            jPaneln.add(Box.createHorizontalStrut(20));
            jPaneln.add(labWeather);
        //以北部布局加入主布局
        add(jPaneln, BorderLayout.NORTH);
        //天气时间信息区
        labDate.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        updateDateLabel(labDate);
        Timer timerDate = new Timer(1000, e -> updateDateLabel(labDate));
        timerDate.start();
        JPanel panelS=new JPanel();
        //JButton btn
        JButton btnFlush=new JButton("刷新");
        ImageIcon icon10=new ImageIcon("D:\\Code\\Stu_3\\res\\Icon\\icon_ih4qrlvm887\\shuaxin.png");
        icon10 = new ImageIcon(Login.getImage(icon10,30,30));
        btnFlush.setIcon(icon10);
        btnFlush.setBorder(BorderFactory.createEmptyBorder());

        JButton btnMultiwindow=new JButton("多开窗口");
        JButton btnSql=new JButton("导入脚本");
        panelS.add(btnMultiwindow);
        panelS.add(btnSql);
        panelS.add(btnFlush);
        add(panelS,BorderLayout.SOUTH);
        btnMultiwindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(){
                    @Override
                    public void run() {
                        mainWindow=new Main();
                        mainWindow.show();
                    }
                }.start();
            }
        });
        btnFlush.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userModel.reload(null);
                CourseModel.reload(null);
                SelectsModel.reload(null);
                TableModel SelectsModel;
                jTable.setModel(userModel);
            }
        });

        btnSql.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                // 设置文件过滤器，只允许选择 SQL 文件
                FileNameExtensionFilter filter = new FileNameExtensionFilter("SQL Files", "sql");
                fileChooser.setFileFilter(filter);

                // 显示文件选择对话框
                int result = fileChooser.showOpenDialog(Main.this);
                String selectedFilePath;
                // 处理用户选择的结果
                if (result == JFileChooser.APPROVE_OPTION) {
                    // 用户选择了一个文件
                    selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    System.out.println("Selected SQL File: " + selectedFilePath);
                    String sqlScript = null;
                    try {
                        sqlScript = readSqlScript(selectedFilePath);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    // 执行 SQL 语句
                    try {
                        executeSqlScript(sqlScript);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(Main.this, "导入成功！", "提示", JOptionPane.WARNING_MESSAGE);
                    userModel.reload(null);
                    jTable.setModel(userModel);
                } else {
                    JOptionPane.showMessageDialog(Main.this, "没有找到文件", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        //天气
        try{
            //湖南益阳的代码：101250700
            URL url = new URL("http://t.weather.itboy.net/api/weather/city/101250700");
            InputStreamReader isReader =  new InputStreamReader(url.openStream(),"UTF-8");//“UTF- 8”万国码，可以显示中文，这是为了防止乱码
            BufferedReader br = new BufferedReader(isReader);//采用缓冲式读入
            String str;
            StringBuilder strTpt=null;
            while((str = br.readLine()) != null){
                String regex="\\p{Punct}+";
                String digit[]=str.split(regex);
                System.out.println('\n'+"城市:"+digit[22]+digit[18]);
                System.out.println('\n'+"时间:"+digit[49]+"年"+digit[50]+"月"+digit[51]+"日"+digit[53]);
                int x=digit[47].lastIndexOf(' ');
                strTpt=new StringBuilder(digit[47].substring(x+1));
                x=digit[45].lastIndexOf(' ');
                strTpt.append('~');
                strTpt.append(digit[45].substring(x+1));
                System.out.println(strTpt);
                strTpt.append(' ');
                strTpt.append(digit[67]);
                String Weather=digit[67];
                ImageIcon icon6;
                if(Weather.equals("多云"))
                {
                    icon6=new ImageIcon("D:\\Code\\Stu_3\\res\\Icon\\icon_94g3nt82xlm\\qingduoyun.png");
                    icon6 = new ImageIcon(Login.getImage(icon6,30,30));
                }
                else if(Weather.equals("阴"))
                {
                    icon6=new ImageIcon("D:\\Code\\Stu_3\\res\\Icon\\icon_xk7bpnseu9c\\yintian_1.png");
                    icon6 = new ImageIcon(Login.getImage(icon6,30,30));
                }
                else if(Weather.equals("小雨"))
                {
                     icon6=new ImageIcon("D:\\Code\\Stu_3\\res\\Icon\\icon_xk7bpnseu9c\\xiaoyu.png");
                    icon6 = new ImageIcon(Login.getImage(icon6,30,30));
                }
                else if(Weather.equals("晴"))
                {
                    icon6=new ImageIcon("D:\\Code\\Stu_3\\res\\Icon\\icon_xk7bpnseu9c\\qingtian.png");
                    icon6 = new ImageIcon(Login.getImage(icon6,30,30));
                }
                else {
                    icon6=new ImageIcon("D:\\Code\\Stu_3\\res\\Icon\\icon_xk7bpnseu9c\\yintian.png");
                    icon6 = new ImageIcon(Login.getImage(icon6,30,30));
                }
                labWeather.setIcon(icon6);
                labWeather.setText(String.valueOf(strTpt));
                labWeather.setFont(new Font("Helvetica", Font.BOLD, 20));
                System.out.println('\n'+"温度:"+digit[47]+"~"+digit[45]);
                System.out.println('\n'+"天气:"+digit[67]+" "+digit[63]+digit[65]);
                System.out.println('\n'+digit[69]);
            }
            br.close();//网上资源使用结束后，数据流及时关闭
            isReader.close();
        }
        catch(Exception exp){
            System.out.println(exp);
        }

    //btnSelect 单击事件
        // 查询学号/课程号按钮,当按钮Clicled
        selectbtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                String stu;
                stu = nameField.getText();
                if(stu.equals("")) {
                    JOptionPane.showMessageDialog(Main.this, "字段不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                DBUtil.initst();;
                try {
                    ResultSet s=DBUtil.st.executeQuery("select sno from stu");
                    boolean f=false;
                    while (s.next())
                    {
                        String sno=s.getString("sno");
                        if(sno.equals(stu))
                        {
                            f=true;
                        }
                    }
                    if(!f)
                    {
                        JOptionPane.showMessageDialog(Main.this, "学生不存在！", "提示", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                //查询
                String sql = "select * from stu where sno='" + stu + "'";
                userModel.reload(sql);
                String sql1 = "select * from course where cno='" + stu + "'";
                coursemodel.reload(sql1);
                String sql2 = "SELECT sc.sno,stu.name,sc.cno,course.cname,credit,score FROM sc,stu,course where sc.sno=stu.sno and course.cno=sc.cno and sc.sno='"
                        + stu + "'";
                selectsmodel.reload(sql2);
            }
        });

        /**
         * 学生管理Mune
         */
        // 增加学生信息,menu项Clicled
           //重新按下、移动、退出、进入事件
            addstu.addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub
                    new AddStu().show();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub

                }
            });


        // 删除学生信息
            deletestu.addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub

                    //获取当前选的行号
                    int index = jTable.getSelectedRow();
                    if (index == -1) {
                        //弹出提示框
                        JOptionPane.showMessageDialog(null, "未选中");
                    } else {
                        //获取原始数据
                        Object user = userModel.rowData.get(index);

                        String sno = ((Vector) user).get(0).toString();
                        try {
                            //初始化数据库准备删除
                            DBUtil.initst();

                            int n = DBUtil.st.executeUpdate("delete from stu where sno='" + sno + "'");
                            if (n > 0) {
                                JOptionPane.showMessageDialog(null, "删除成功");
                            } else {
                                JOptionPane.showMessageDialog(null, "删除失败");
                            }
                            UserModel.reload(null);
                            DBUtil.closeDB();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub

                }
            });


        // 修改学生信息
            updatestu.addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub
                    int index = jTable.getSelectedRow();
                    if (index == -1) {
                        JOptionPane.showMessageDialog(null, "未选中");
                    } else {
                        Object user = userModel.rowData.get(index);
                        //重新Show一下界面
                        // 刷新jTable并把jTable显示出来
                        new UpdateStu(user).show();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub

                }
            });


        /**
         * 课程管理
         */
        // 增加课程信息
            addcourse.addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub
                    new AddCourse().show();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub

                }
            });


        // 删除课程信息
            deletecourse.addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub
                    int index = jTable.getSelectedRow();
                    if (index == -1) {
                        JOptionPane.showMessageDialog(null, "未选中");
                    } else {
                        Object course = coursemodel.rowData1.get(index);
                        String cno = ((Vector) course).get(0).toString();
                        try {
                            DBUtil.initst();
                            int n = DBUtil.st.executeUpdate("delete from course where cno='" + cno + "'");
                            if (n > 0) {
                                JOptionPane.showMessageDialog(null, "删除成功");
                            } else {
                                JOptionPane.showMessageDialog(null, "删除失败");
                            }
                            // 刷新jTable并把jTable显示出来
                            CourseModel.reload(null);
                            DBUtil.closeDB();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub

                }
            });


        // 修改课程信息
            updatecourse.addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub
                    int index = jTable.getSelectedRow();
                    if (index == -1) {
                        JOptionPane.showMessageDialog(null, "未选中");
                    } else {
                        Object course = coursemodel.rowData1.get(index);
                        new UpdateCourse(course).show();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub

                }
            });

        /**
         * 学生选课管理
         */
        // 增加学生选课信息
            addselect.addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub
                    new AddSelect().show();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub

                }
            });


        // 删除学生选课信息
            deleteselect.addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub
                    int index = jTable.getSelectedRow();
                    if (index == -1) {
                        JOptionPane.showMessageDialog(null, "未选中");
                    } else {
                        Object select = selectsmodel.rowData2.get(index);
                        String sno = ((Vector) select).get(0).toString();
                        try {
                            DBUtil.initst();
                            int n = DBUtil.st.executeUpdate("delete from sc where sno='" + sno + "'");
                            if (n > 0) {
                                JOptionPane.showMessageDialog(null, "删除成功");
                            } else {
                                JOptionPane.showMessageDialog(null, "删除失败");
                            }
                            SelectsModel.reload(null);
                            DBUtil.closeDB();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub

                }
            });


        // 修改学生选课信息
            updateselect.addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub
                    int index = jTable.getSelectedRow();
                    if (index == -1) {
                        JOptionPane.showMessageDialog(null, "未选中");
                    } else {
                        Object select = selectsmodel.rowData2.get(index);
                        new UpdateSelect(select).show();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub

                }
            });

        /**
         * 查询管理
         */
        // 查询所有学生信息
            querystu.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    // 刷新jTable并把jTable显示出来
                    userModel.reload(null);
                    jTable.setModel(userModel);
                }
            });
        // 查询所有课程信息
            querycourse.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    // 刷新jTable并把jTable显示出来
                    coursemodel.reload(null);
        //				jTable.show();

                    //设置模式为课程模式、即显示课程信息
                    jTable.setModel(coursemodel);
                }
            });
        // 查询所有学生选课信息
            queryselect.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    // 刷新jTable并把jTable显示出来
                    selectsmodel.reload(null);
    //				jTable.show();
                    //设置模式为选课模式、即显示选课信息
                    jTable.setModel(selectsmodel);
                }
            });
        /**
         * 系统退出
         */
        //监听Lab事件
            exit.addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    int choice = JOptionPane.showConfirmDialog(null, "确定要退出吗", "Exit",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (choice == JOptionPane.YES_OPTION) {
                        Main.this.dispose();
                    }
                }

                public void mouseEntered(MouseEvent e) {
                    // 处理鼠标移入
                }

                public void mouseExited(MouseEvent e) {
                    // 处理鼠标离开
                }

                public void mousePressed(MouseEvent e) {
                    // 处理鼠标按下
                }

                public void mouseReleased(MouseEvent e) {
                    // 处理鼠标释放
                }
            });
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //设置右击菜单
             jTable.setComponentPopupMenu(popupMenu);
            }

    public static void main(String[] args) {
        Main main = new Main();
    }
}

