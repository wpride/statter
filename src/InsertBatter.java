import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class InsertBatter {
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private PreparedStatement preparedStatement0 = null;
  private ResultSet resultSet = null;

  public void setupTable() throws Exception {
    try {
      // This will load the MySQL driver, each DB has its own driver
      Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
      connect = DriverManager
          .getConnection("jdbc:mysql://localhost/feedback?"
              + "user=root&password=1234");
      
      preparedStatement0 = connect
    		  .prepareStatement("CREATE TABLE feedback.batters4 " +
    		  		"(bpid INT NOT NULL, " +
    		  		"lastname VARCHAR(45) NULL, " +
    		  		"firstname VARCHAR(45) NULL, " +
    		  		"pos VARCHAR(45) NULL, " +
    		  		"bats VARCHAR(45) NULL, " +
    		  		"throws VARCHAR(45) NULL, " +
    		  		"height INT NOT NULL, " +
    		  		"weight INT NOT NULL, " +
    		  		"year INT NOT NULL, " +
    		  		"team VARCHAR(45) NULL, " +
    		  		"league VARCHAR(45) NULL, " +
    		  		"lvl VARCHAR(45) NULL, " +
    		  		"age INT NOT NULL, " +
    		  		"pa INT NOT NULL, " +
    		  		"ab INT NOT NULL, " +
    		  		"r INT NOT NULL, " +
    		  		"1b INT NOT NULL, " +
    		  		"2b INT NOT NULL, " +
    		  		"3b INT NOT NULL, " +
    		  		"hr INT NOT NULL, " +
    		  		"hit INT NOT NULL, " +
    		  		"tb INT NOT NULL, " +
    		  		"rbi INT NOT NULL, " +
    		  		"bb INT NOT NULL, " +
    		  		"hbp INT NOT NULL, " +
    		  		"so INT NOT NULL, " +
    		  		"sh INT NOT NULL, " +
    		  		"sf INT NOT NULL, " +
    		  		"dp INT NOT NULL, " +
    		  		"sb INT NOT NULL, " +
    		  		"cs INT NOT NULL, " +
    		  		"avg FLOAT NOT NULL, " +
    		  		"obp FLOAT NOT NULL, " +
    		  		"slg FLOAT NOT NULL, " +
    		  		"tav FLOAT NOT NULL, " +
    		  		"babip FLOAT NOT NULL, " +
    		  		"brr FLOAT NOT NULL, " +
    		  		"pos_adj FLOAT NOT NULL, " +
    		  		"rep_adj FLOAT NOT NULL, " +
    		  		"raa FLOAT NOT NULL, " +
    		  		"fraa_val FLOAT NOT NULL, " +
    		  		"fraa VARCHAR(45) NOT NULL, " +
    		  		"warp FLOAT NOT NULL, " +
    		  		"vorp FLOAT NOT NULL, " +
    		  		"breakout INT NOT NULL, " +
    		  		"improve INT NOT NULL, " +
    		  		"collapse INT NOT NULL, " +
    		  		"attrition INT NOT NULL, " +
    		  		"PRIMARY KEY (bpid));");
      preparedStatement0.executeUpdate();

      
    } catch (Exception e) {
      throw e;
    } finally {
      close();
    }

  }
  
  public void writeData() throws Exception {
	  
	  Workbook workbook = Workbook.getWorkbook(new File("C:\\Users\\wspride\\Desktop\\political\\baseball\\assets\\pecota_2014_02_04_75106.xls"));
	  
	  Sheet sheet = workbook.getSheet(1);
	  
      Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
      connect = DriverManager
          .getConnection("jdbc:mysql://localhost/feedback?"
              + "user=root&password=1234");
      
      int i=1;
	  
	  while(sheet.getRow(i) != null){
		  
		  System.out.println("iteration: " + i);
		  
	      // PreparedStatements can use variables and are more efficient
	      preparedStatement = connect
	          .prepareStatement("insert into  FEEDBACK.BATTERS4 values (?,?,?,?,?,?," +
	        		  "?,?,?,?,?,?," +
	        		  "?,?,?,?,?,?," +
	        		  "?,?,?,?,?,?," +
	        		  "?,?,?,?,?,?," +
	        		  "?,?,?,?,?,?," +
	        		  "?,?,?,?,?,?," +
	        		  "?,?,?,?,?,?)");
		  
		  Cell bpid_cell = sheet.getCell(0,i);
		  preparedStatement.setInt(1, Integer.valueOf(bpid_cell.getContents()));
		  Cell lastname_cell = sheet.getCell(1,i);
		  preparedStatement.setString(2,(lastname_cell.getContents()));
		  Cell firstname_cell = sheet.getCell(2,i); 
		  preparedStatement.setString(3, (firstname_cell.getContents()));
		  Cell pos_cell = sheet.getCell(3, i);
		  preparedStatement.setString(4, (pos_cell.getContents()));
		  Cell bats_cell = sheet.getCell(4,i);
		  preparedStatement.setString(5, (bats_cell.getContents()));
		  Cell throws_cell = sheet.getCell(5,i);
		  preparedStatement.setString(6, (throws_cell.getContents()));
		  Cell height_cell = sheet.getCell(6,i);
		  preparedStatement.setInt(7, Integer.valueOf(height_cell.getContents()));
		  Cell weight_cell = sheet.getCell(7,i);
		  preparedStatement.setInt(8, Integer.valueOf(weight_cell.getContents()));
		  Cell year_cell = sheet.getCell(8,i);
		  preparedStatement.setInt(9, Integer.valueOf(year_cell.getContents()));
		  Cell team_cell = sheet.getCell(9,i); 
		  preparedStatement.setString(10, (team_cell.getContents()));
		  Cell league_cell = sheet.getCell(10,i);
		  preparedStatement.setString(11, (league_cell.getContents()));
		  Cell lvl_cell = sheet.getCell(11,i);
		  preparedStatement.setString(12, (lvl_cell.getContents()));
		  Cell age_cell = sheet.getCell(12,i);
		  preparedStatement.setInt(13, Integer.valueOf(age_cell.getContents()));
		  Cell pa_cell = sheet.getCell(13,i);
		  preparedStatement.setInt(14, Integer.valueOf(pa_cell.getContents()));
		  Cell ab_cell = sheet.getCell(14,i);
		  preparedStatement.setInt(15, Integer.valueOf(ab_cell.getContents()));
		  Cell r_cell = sheet.getCell(15,i); 
		  preparedStatement.setInt(16, Integer.valueOf(r_cell.getContents()));
		  Cell single_cell = sheet.getCell(16,i);
		  preparedStatement.setInt(17, Integer.valueOf(single_cell.getContents()));
		  Cell double_cell = sheet.getCell(17,i);
		  preparedStatement.setInt(18, Integer.valueOf(double_cell.getContents()));
		  Cell triple_cell = sheet.getCell(18,i);
		  preparedStatement.setInt(19, Integer.valueOf(triple_cell.getContents()));
		  Cell hr_cell = sheet.getCell(19,i);
		  preparedStatement.setInt(20, Integer.valueOf(hr_cell.getContents()));
		  Cell hit_cell = sheet.getCell(20,i);
		  preparedStatement.setInt(21, Integer.valueOf(hit_cell.getContents()));
		  Cell tb_cell = sheet.getCell(21,i); 
		  preparedStatement.setInt(22, Integer.valueOf(tb_cell.getContents()));
		  Cell rbi_cell = sheet.getCell(22,i);
		  preparedStatement.setInt(23, Integer.valueOf(rbi_cell.getContents()));
		  Cell bb_cell = sheet.getCell(23,i);
		  preparedStatement.setInt(24, Integer.valueOf(bb_cell.getContents()));
		  Cell hbp_cell = sheet.getCell(24,i);
		  preparedStatement.setInt(25, Integer.valueOf(hbp_cell.getContents()));
		  Cell so_cell = sheet.getCell(25,i);
		  preparedStatement.setInt(26, Integer.valueOf(so_cell.getContents()));
		  Cell sh_cell = sheet.getCell(26,i);
		  preparedStatement.setInt(27, Integer.valueOf(sh_cell.getContents()));
		  Cell sf_cell = sheet.getCell(27,i);
		  preparedStatement.setInt(28, Integer.valueOf(sf_cell.getContents()));
		  Cell dp_cell = sheet.getCell(28,i); 
		  preparedStatement.setInt(29, Integer.valueOf(dp_cell.getContents()));
		  Cell sb_cell = sheet.getCell(29,i);
		  preparedStatement.setInt(30, Integer.valueOf(sb_cell.getContents()));
		  Cell cs_cell = sheet.getCell(30,i);
		  preparedStatement.setInt(31, Integer.valueOf(cs_cell.getContents()));
		  Cell avg_cell = sheet.getCell(31,i);
		  preparedStatement.setFloat(32, Float.valueOf(avg_cell.getContents()));
		  Cell obp_cell = sheet.getCell(32,i);
		  preparedStatement.setFloat(33, Float.valueOf(obp_cell.getContents()));
		  Cell slg_cell = sheet.getCell(33,i);
		  preparedStatement.setFloat(34, Float.valueOf(slg_cell.getContents()));
		  Cell tav_cell = sheet.getCell(34,i); 
		  preparedStatement.setFloat(35, Float.valueOf(tav_cell.getContents()));
		  Cell babip_cell = sheet.getCell(35,i);
		  preparedStatement.setFloat(36, Float.valueOf(babip_cell.getContents()));
		  Cell brr_cell = sheet.getCell(36,i);
		  preparedStatement.setFloat(37, Float.valueOf(brr_cell.getContents()));
		  Cell pos_adj_cell = sheet.getCell(37,i);
		  preparedStatement.setFloat(38, Float.valueOf(pos_adj_cell.getContents()));
		  Cell rep_adj_cell = sheet.getCell(38,i);
		  preparedStatement.setFloat(39, Float.valueOf(rep_adj_cell.getContents()));
		  Cell raa_cell = sheet.getCell(39,i); 
		  preparedStatement.setFloat(40, Float.valueOf(raa_cell.getContents()));
		  Cell fraa_val_cell = sheet.getCell(40,i);
		  System.out.println(Float.valueOf(fraa_val_cell.getContents()));
		  preparedStatement.setDouble(41, Float.valueOf(fraa_val_cell.getContents()));
		  Cell fraa_cell = sheet.getCell(41,i);
		  preparedStatement.setString(42, (fraa_cell.getContents()));
		  Cell warp_cell = sheet.getCell(42,i);
		  preparedStatement.setFloat(43, Float.valueOf(warp_cell.getContents()));
		  Cell vorp_cell = sheet.getCell(43,i); 
		  preparedStatement.setFloat(44, Float.valueOf(vorp_cell.getContents()));
		  Cell breakout_cell = sheet.getCell(44,i);
		  preparedStatement.setInt(45, Integer.valueOf(breakout_cell.getContents()));
		  Cell improve_cell = sheet.getCell(45,i);
		  preparedStatement.setInt(46, Integer.valueOf(improve_cell.getContents()));
		  Cell collapse_cell = sheet.getCell(46,i);
		  preparedStatement.setInt(47, Integer.valueOf(collapse_cell.getContents()));
		  Cell attrition_cell = sheet.getCell(47,i);
		  preparedStatement.setInt(48, Integer.valueOf(attrition_cell.getContents()));
		  
		  System.out.println(preparedStatement);

	      preparedStatement.executeUpdate();
	      i++;
		  
	  }
  }

  private void writeMetaData(ResultSet resultSet) throws SQLException {
    //   Now get some metadata from the database
    // Result set get the result of the SQL query
    
    System.out.println("The columns in the table are: ");
    
    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
    }
  }

  private void writeResultSet(ResultSet resultSet) throws SQLException {
    // ResultSet is initially before the first data set
    while (resultSet.next()) {
      // It is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g. resultSet.getSTring(2);
      String user = resultSet.getString("myuser");
      String website = resultSet.getString("webpage");
      String summary = resultSet.getString("summary");
      Date date = resultSet.getDate("datum");
      String comment = resultSet.getString("comments");
      System.out.println("User: " + user);
      System.out.println("Website: " + website);
      System.out.println("Summary: " + summary);
      System.out.println("Date: " + date);
      System.out.println("Comment: " + comment);
    }
  }

  // You need to close the resultSet
  private void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }

} 