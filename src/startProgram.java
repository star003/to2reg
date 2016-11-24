import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.swing.JFileChooser;


public class startProgram {
	
	static String ADDRESS_LOG_FILE = "";
	static String ADDRESS_DB_FILE = "";
	static String LOG_VERSION = "0";
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		//**1. загрузим настройки и проверим , что все файлы на месте
		
		try {
			
			Properties loadProps = new Properties();
			loadProps.loadFromXML(new FileInputStream("settings.xml"));
			
			//**прочитали настройки , нужно проверить , все ли на месте. Если нет - то спросить где ?
			
			if (!fileExist(loadProps.getProperty("path_log"))) {
				
				ADDRESS_LOG_FILE =openFileDialog("Файл лога");
				loadProps.setProperty("path_log"	, ADDRESS_LOG_FILE);
				
			}
			else {
				
				ADDRESS_LOG_FILE = loadProps.getProperty("path_log");
				
			}
			
			if (!fileExist(loadProps.getProperty("path_db"))) {
				
				ADDRESS_DB_FILE=openFileDialog("Файл базы");
				loadProps.setProperty("path_db"		, ADDRESS_DB_FILE);
				
			}
			else {
				
				ADDRESS_DB_FILE = loadProps.getProperty("path_db");
				
			}
			
			try {
				
				LOG_VERSION =logVersion();
				System.out.println(LOG_VERSION);
				
			} catch (Exception e) {
				
				
			}
 
		}
		
		catch (FileNotFoundException e) {
			
			//**настроек не оказалось. Нужно запросить данные и создать новые настройки
			Properties saveProps = new Properties();
			ADDRESS_LOG_FILE =openFileDialog("Файл лога");
			ADDRESS_DB_FILE=openFileDialog("Файл базы");
			
		    saveProps.setProperty("path_log"	, ADDRESS_LOG_FILE);
		    saveProps.setProperty("path_db"		, ADDRESS_DB_FILE );
		    saveProps.storeToXML(new FileOutputStream("settings.xml"), "");
			
		}
		
		mainForm.main(null);
		
	}//main
	
	static boolean fileExist(String lnk) {
		//****
		//*проверим есть ли файл 
		//*есть - истина , иначе ложь
		//****
		File theDir = new File(lnk);
		
		  if (!theDir.exists()) {
			  
			  return false;
			  
		  }
		  else {
			  
			  return true;
			  
		  }
		  
	} //fileExist
	
	public static String openFileDialog(String mes){
		
		String r = "";
		 
		JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(mes);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
 
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
 
        	r = chooser.getSelectedFile().getAbsolutePath();
 
        } 
        
        return r;
        
	}//openFileDialog
	
	static String[] loadData(String lnk) throws Exception{
		//*******************************************************
		//*универсальная надстрока, вернет String[] ответ по lnk
		//*читаем тхт файл
		//*******************************************************
	
		String[] everything=null;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(lnk), Charset.forName("UTF-8")));
		//BufferedReader br = new BufferedReader(new FileReader(lnk));
			
		    try {
		    	
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();
	 
		        while (line != null) {
		        	
		            sb.append(line);
		            sb.append("\n");
		            line = br.readLine();
		            
		        }
		        
		        everything = sb.toString().split("\n");
		        
		    } finally {
		    	
		        br.close();
		        
		    }
		    
		    return everything;
	 
	} //static String[] loadData(String lnk) throws Exception
	
	static String logVersion() throws Exception{
				
		String[] st = loadData(ADDRESS_LOG_FILE);
		for(String g:st){
			
			if(g.indexOf(";")>0) {
				
				return "2.0";
				
			}
			
		}
		
		return "1.0";
		
	}//logVersion

	
}//startProgram
