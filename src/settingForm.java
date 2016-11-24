import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class settingForm {

	String ADR_BASE = startProgram.ADDRESS_DB_FILE;//"C://1-2012//to2reg//test2.db";
	private JFrame sForm;
	String id = "";
	private JTextField selFromMailAddress;
	private JTextField selMailPasswd;
	private JTextField selToMail;
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {
					
					settingForm window = new settingForm();
					window.sForm.setVisible(true);
					
				} catch (Exception e) {
					
					e.printStackTrace();
					
				}
				
			}
			
		});

	}//main
	
	public settingForm() throws Exception  {
		
		initialize();
		
	} //public mainForm() throws ClassNotFoundException, SQLException
	
	private void initialize() throws Exception {
		
		sForm = new JFrame();
		sForm.setTitle("setting");
		sForm.setBounds(0, 0, 367, 256);
		sForm.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		
		final JLabel labInfo = new JLabel("");
		labInfo.setBounds(0, 455, 658, 31);
		sForm.getContentPane().add(labInfo);
		//final String[] x1 = null;
		sForm.getContentPane().setLayout(null);
		
		selFromMailAddress = new JTextField();
		selFromMailAddress.setBounds(88, 28, 239, 20);
		sForm.getContentPane().add(selFromMailAddress);
		selFromMailAddress.setColumns(10);
		
		JLabel label = new JLabel("\u0441 \u043F\u043E\u0447\u0442\u044B");
		label.setBounds(10, 34, 68, 14);
		sForm.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\u043F\u0430\u0440\u043E\u043B\u044C");
		label_1.setBounds(10, 65, 68, 14);
		sForm.getContentPane().add(label_1);
		
		selMailPasswd = new JTextField();
		selMailPasswd.setBounds(88, 59, 239, 20);
		sForm.getContentPane().add(selMailPasswd);
		selMailPasswd.setColumns(10);
		
		JLabel label_2 = new JLabel("\u043D\u0430 \u043F\u043E\u0447\u0442\u0443");
		label_2.setBounds(10, 96, 68, 14);
		sForm.getContentPane().add(label_2);
		
		selToMail = new JTextField();
		selToMail.setBounds(88, 90, 239, 20);
		sForm.getContentPane().add(selToMail);
		selToMail.setColumns(10);
		
		JLabel label_3 = new JLabel("1. \u041D\u0430\u0441\u0442\u0440\u043E\u043A\u0430 \u043E\u0442\u043F\u0440\u0430\u0432\u043A\u0438 \u0443\u0432\u0435\u0434\u043E\u043C\u043B\u0435\u043D\u0438\u044F");
		label_3.setBounds(10, 9, 244, 14);
		sForm.getContentPane().add(label_3);
		
		JButton button = new JButton("ОК");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					updateSettingInBase();
					closeF();
					
				} catch (ClassNotFoundException | SQLException e1) {

					e1.printStackTrace();
					
				}
				
			}
		});
		button.setBounds(10, 183, 89, 23);
		sForm.getContentPane().add(button);
		
		JButton button_1 = new JButton("Отмена");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				closeF();
				
			}
		});
		button_1.setBounds(109, 183, 89, 23);
		sForm.getContentPane().add(button_1);
		JButton button_2 = new JButton("Закрыть");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				closeF();
				
			}
		});
		button_2.setBounds(208, 183, 89, 23);
		sForm.getContentPane().add(button_2);
		
		JLabel tablo = new JLabel("");
		tablo.setBounds(10, 132, 317, 14);
		sForm.getContentPane().add(tablo);
		sForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		loadFromBase();
		
	} //private void initialize() throws ClassNotFoundException, SQLException 
	
	public void loadFromBase() throws ClassNotFoundException, SQLException {
		
		ResultSet r = readAnotherDB.getResult("SELECT * FROM setprg", ADR_BASE);
		
		while(r.next()) {
			 
			selFromMailAddress.setText(r.getString("fromMail"));
			selMailPasswd.setText(r.getString("pass"));
			selToMail.setText(r.getString("toMail"));
 
		}
		
	}//loadFromBase
	
	public void updateSettingInBase() throws ClassNotFoundException, SQLException {
		
		Class.forName("org.sqlite.JDBC");
		Connection bd 	= DriverManager.getConnection("jdbc:sqlite:"+ADR_BASE);
		Statement st  	= bd.createStatement();
		st.setQueryTimeout(60);
		
		PreparedStatement q1;
		q1 = bd.prepareStatement("UPDATE setprg SET fromMail=?,pass=?,toMail=?;");
		
		q1.setString(1,selFromMailAddress.getText().toString());
		q1.setString(2,selMailPasswd.getText().toString());
		q1.setString(3,selToMail.getText().toString());
		
		q1.execute();
		st.close();
		
		loadFromBase();
		
	}//updateSettingInBase
	
	public void closeF() {
		
		sForm.dispatchEvent(new WindowEvent(sForm, WindowEvent.WINDOW_CLOSING));
		
	}//closeF
	
	
}//settingForm
