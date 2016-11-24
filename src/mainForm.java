import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;

import javax.swing.JLabel;

public class mainForm {
	
	private JFrame mForm;
	private JTable table;
	String id = "";
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {
					
					mainForm window = new mainForm();
					window.mForm.setVisible(true);
					
				} catch (Exception e) {
					
					e.printStackTrace();
					
				}
				
			}
			
		});
		
	} //main 
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public mainForm() throws Exception  {
		
		initialize();
		
	} //mainForm
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void initialize() throws Exception {
		
		mForm = new JFrame();
		mForm.setBounds(0, 0, 702, 656);
		mForm.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		final JLabel labInfo = new JLabel("");
		labInfo.setBounds(0, 586, 658, 31);
		mForm.getContentPane().add(labInfo);
		mForm.getContentPane().setLayout(null);
		table = new JTable(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"date", "type"
			}
		));
		
		table.setModel(buildTableModel(getLog(),false,false));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		final TableColumnModel columnModel = table.getColumnModel();
		
		setColWidth(columnModel);

		table.setBounds(10, 10, 337, 208);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 45, 648, 536);
		
		mForm.getContentPane().add(scrollPane);
		
		JButton btnGetData = new JButton("Получить данные");
		btnGetData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					table.setModel(buildTableModel(getLog(),false,false));
					setColWidth(columnModel);
					
				} catch (Exception e1) {

					e1.printStackTrace();
					
				}
				
				labInfo.setText("   Данные обновлены...");
				try {
					
					getLog();
					
				} catch (Exception e) {

					e.printStackTrace();
					
				}
				
			} //actionPerformed
			
		});//btnGetData.addActionListener
		
		btnGetData.setBounds(10, 11, 169, 23);
		mForm.getContentPane().add(btnGetData);
		mForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		labInfo.setText("   Жду...");
		
		JButton button = new JButton("Настройки...");
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				settingForm.main(null);
				
			}//actionPerformed
			
		});//button.addActionListener
		
		button.setBounds(346, 11, 151, 23);
		mForm.getContentPane().add(button);
		
		JButton btnNewButton = new JButton("Выход");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
				
			}
		});
		btnNewButton.setBounds(507, 11, 151, 23);
		mForm.getContentPane().add(btnNewButton);
		
		JButton button_1 = new JButton("\u043F\u043E\u043A\u0430\u0437\u0430\u0442\u044C \u0432\u0441\u0435");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					table.setModel(buildTableModel(getLog(),false,true));
					setColWidth(columnModel);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		button_1.setBounds(192, 11, 144, 23);
		mForm.getContentPane().add(button_1);
		
		System.out.println(columnModel.getColumn(0).getPreferredWidth());
		
	} //private void initialize() throws ClassNotFoundException, SQLException 
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static DefaultTableModel buildTableModel(String[] log,boolean emtyModel,boolean viewAll) throws SQLException {
		
		Vector<String> columnNames = new Vector<String>();
		
		if (startProgram.LOG_VERSION.equals("1.0")) {
			
			columnNames.add("date");
			columnNames.add("type");
			
		}
		else if(startProgram.LOG_VERSION.equals("2.0")){
        	
			columnNames.add("date");
			columnNames.add("id");
			columnNames.add("type");
			columnNames.add("descr");
        	
        }
		
		
		if (emtyModel == true) {
			
			//**случай , когда нужно получить просто пустую таблицу
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			return new DefaultTableModel(data, columnNames);
			
		}
		
		 Vector<Vector<String>> dataVector = new Vector<Vector<String>>();
	        for (String row : log) {
	        	
	            row = row.trim();  //UPDATE
	            Vector<String> data = new Vector<String>();
	            
	            if (startProgram.LOG_VERSION.equals("1.0")) {
	            	
	            	data.addAll(Arrays.asList(row.split("_")));
	            	
	            }
	            else if(startProgram.LOG_VERSION.equals("2.0")){
	            	
	            	if (viewAll) {
	            		
	            		data.addAll(Arrays.asList(row.split(";")));
            			dataVector.add(data);
	            		
	            	}
	            	else {
	            		
	            		if (!row.split(";")[1].equals("t")) {
	            		
	            			data.addAll(Arrays.asList(row.split(";")));
	            			dataVector.add(data);
	            		
	            		}
	            	}
	            }
	            
	        }
		
	    return new DefaultTableModel(dataVector, columnNames);
	    
	} //DefaultTableModel
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String[] getLog() throws Exception {
		
		String adr = startProgram.ADDRESS_LOG_FILE;
		
		try {
			
			String[] p = startProgram.loadData(adr);
			return p;
			
		}
		catch (FileNotFoundException e){ //**тот случай когда запуск с этого класса , а не startProgram
			
			startProgram.main(null);
			String[] p = startProgram.loadData(adr);
			return p;
			
		}
		
	}//getLog
	
	static void setColWidth( TableColumnModel columnModel){
		
		if (startProgram.LOG_VERSION.equals("1.0")) {
			
			columnModel.getColumn(0).setPreferredWidth(150);
			columnModel.getColumn(1).setPreferredWidth(470);
			
		}
		else if(startProgram.LOG_VERSION.equals("2.0")){
        	
			columnModel.getColumn(0).setPreferredWidth(140);
			columnModel.getColumn(1).setPreferredWidth(20);
			columnModel.getColumn(2).setPreferredWidth(20);
			columnModel.getColumn(3).setPreferredWidth(470);
        	
        }
	}
} //mainForm
