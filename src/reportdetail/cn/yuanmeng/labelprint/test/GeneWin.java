package cn.yuanmeng.labelprint.test;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GeneWin extends JFrame {

	public static void main(String[] args) {
		JFrame frame = new GeneWin();

		frame.setTitle("基因数据处理窗口");// 名字可以随便改
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		Container c = frame.getContentPane();
		c.setLayout(new GridLayout(3, 1, 10, 10));

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panel3 = new JPanel(new FlowLayout());

		JLabel label1 = new JLabel("请输入：");
		final JTextField jt = new JTextField(30);
		panel1.add(label1);
		panel1.add(jt);

		JButton jb1 = new JButton("导入数据");
		JButton jb2 = new JButton("重置");
		panel2.add(jb1);
		panel2.add(jb2);

		c.add(panel1);
		c.add(panel2);

		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputStr=jt.getText();
				System.out.println(inputStr);
//				TestExcelProcess.start(inputStr);
			}
		});
		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jt.setText("");
			}
		});
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
