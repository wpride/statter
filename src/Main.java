import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
	
	static String username;
	static String password;
	
	public static void main(String[] args) throws Exception {
		
		Properties properties = new Properties();
		try {
		  properties.load(new FileInputStream("C:\\Users\\wspride\\Desktop\\political\\baseball\\local.properties"));
		} catch (IOException e) {
		  System.out.println("Problem loading properties file.");
		}
		
		username = properties.getProperty("mysql.user");
		password = properties.getProperty("mysql.password");

		
		setupLahmanBatter();

	}

	public static void setupPectoraHitter() throws Exception{
		MySQLInserter hitterMSI = new MySQLInserter(username,password,"feedback.batter",
				"C:\\Users\\wspride\\Desktop\\political\\baseball\\assets\\pecota_2014_02_04_75106.xls", 1);
		//hitterMSI.setupTable();
		//hitterMSI.insertRows();

		hitterMSI.getTopTen("playerID","TAv");
		hitterMSI.getTopTen("playerID","SLG");
		hitterMSI.getCombinedStat();
	}

	public static void setupPectoraBatter() throws Exception{
		MySQLInserter pitcherMSI = new MySQLInserter(username,password,"feedback.pitcher",
				"C:\\Users\\wspride\\Desktop\\political\\baseball\\assets\\pecota_2014_02_04_75106.xls", 2);

		pitcherMSI.getTopTen("playerID","SO");
		pitcherMSI.getBottomTen("playerID","ERA");
	}
	
	public static void setupLahmanBatter() throws Exception{
		MySQLInserter batterMSI = new MySQLInserter(username,password,"feedback.lahman_batter",
				"C:\\Users\\wspride\\Desktop\\political\\baseball\\assets\\lahman-2013\\Batting2013.xls", 0);
		
		//batterMSI.setupTable();
		//batterMSI.insertRows();

		batterMSI.getTopX(15,"playerID","BB/SO","AB>100");
		
		batterMSI.getRowByStat("playerID","vottojo01");
		
		
	}

}