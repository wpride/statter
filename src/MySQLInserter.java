import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class MySQLInserter {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private PreparedStatement preparedStatement0 = null;

	String username;
	String password;
	String tablename;
	String filename;
	int sheetnumber;

	String[] headerStrings;
	String[] typeStrings;

	public MySQLInserter(String username, String password, String tablename, String filename, int sheetnumber) throws Exception{
		this.username = username;
		this.password = password;
		this.tablename = tablename;
		this.filename = filename;
		this.sheetnumber = sheetnumber;

		this.typeStrings = this.getTypes(filename, sheetnumber);
		this.headerStrings = this.getHeaders();
	}
	
	public String[] getRowByStat(String headerName, String value) throws Exception{
		ResultSet mResultSet = getResultSet("select * from " + tablename);
		
		String [] retStrings = new String[headerStrings.length];
		
	    while (mResultSet.next()) {
	        // It is possible to get the columns via name
	        // also possible to get the columns via the column number
	        // which starts at 1
	        // e.g. resultSet.getSTring(2);
	        String header = mResultSet.getString(headerName);
	        if(header.equals(value)){
	        	for(int i=1; i < headerStrings.length; i++){
	        		retStrings[i] = headerStrings[i-1] + ": " + mResultSet.getObject(i).toString();
	        		System.out.println(retStrings[i]);
	        	}
	        }
	      }
		
		return retStrings;
	}
	
	public String[] getHeaders() throws BiffException, IOException{
		Workbook workbook = Workbook.getWorkbook(new File(this.filename));

		Sheet sheet = workbook.getSheet(sheetnumber);

		Cell[] row1 = sheet.getRow(0);
		String[] returnString = new String[row1.length];

		for(int i=0; i< row1.length; i++){
			returnString[i] = row1[i].getContents();
		}	

		return returnString;
	}

	public String[] getTypes(String filename, int sheetnumber) throws Exception{
		Workbook workbook = Workbook.getWorkbook(new File(filename));

		Sheet sheet = workbook.getSheet(sheetnumber);

		Cell[] row1 = sheet.getRow(1);
		String[] returnTypes = new String[row1.length];

		for(int i=0; i< row1.length; i++){


			String contents = row1[i].getContents();

			CellType ct = row1[i].getType();

			if(ct == CellType.NUMBER_FORMULA || ct == CellType.NUMBER){
				returnTypes[i] = "FLOAT";
				continue;
			}
			if(ct == CellType.LABEL){
				returnTypes[i] = "VARCHAR(400)";
				continue;
			}
			if(ct == CellType.STRING_FORMULA){
				returnTypes[i] = "VARCHAR(400)";
				continue;
			}
			returnTypes[i] = "unknown";
		}	

		return returnTypes;
	}


	public void setupTable() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/feedback?"
							+ "user=" + this.username + "&password=" + this.password);	

			
			String preparedStatement = "CREATE TABLE " + tablename + " (";

			for(int i=0; i<headerStrings.length; i++){
				preparedStatement = preparedStatement + headerStrings[i] + " " + typeStrings[i] +" NOT NULL,";
			}

			// remove trailing ','
			preparedStatement = preparedStatement.substring(0, preparedStatement.length() - 1);

			preparedStatement = preparedStatement + ")";

			System.out.println(preparedStatement);

			preparedStatement0 = connect
					.prepareStatement(preparedStatement);

			preparedStatement0.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	public void insertRows() throws Exception{

		Workbook workbook = Workbook.getWorkbook(new File(filename));

		Sheet sheet = workbook.getSheet(sheetnumber);

		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost/feedback?"
						+ "user=" + this.username + "&password=" + this.password);	

		String preparedStatementString = "insert into " + this.tablename + " values (";

		for(int i =1; i<= headerStrings.length; i++){
			preparedStatementString += "?,";
		}

		// remove trailing ','
		preparedStatementString = preparedStatementString.substring(0, preparedStatementString.length() - 1);

		preparedStatementString += ")";

		int i=1;

		while(!sheet.getCell(0,i).getType().equals(CellType.EMPTY)){

			preparedStatement = connect
					.prepareStatement(preparedStatementString);

			for(int j =0; j < headerStrings.length; j++){
				Cell currentCell = sheet.getCell(j,i);
				String cTypeString = typeStrings[j];
				if(cTypeString.equals("FLOAT")){
					preparedStatement.setFloat(j+1, Float.valueOf(currentCell.getContents()));
				} else if(cTypeString.equals("VARCHAR(400)")){
					preparedStatement.setString(j+1,currentCell.getContents());
				}

			}

			preparedStatement.executeUpdate();
			i++;

		}
	}
	
	public void getCombinedStat() throws Exception{
		ResultSet mResultSet = getResultSet("SELECT LASTNAME,FIRSTNAME,1B,2B FROM "+  tablename +" ORDER BY HR/1B DESC LIMIT 10");
		
	    while (mResultSet.next()) {
	    	
	    	String singles = mResultSet.getString("1B");
	    	String doubles = mResultSet.getString("2B");
	    	
	    	float singlesf = Float.valueOf(singles);
	    	float doublesf = Float.valueOf(doubles);
	    	
	    	float result = singlesf + doublesf;
	    	
	    	System.out.println("Combined: " + mResultSet.getString("LASTNAME") + ", " + mResultSet.getString("FIRSTNAME") + ": " + result);
	    }
	}
	
	public void getTopTen(String print, String stat) throws Exception{
		getTopX(10, print, stat);
	}
	
	public void getBottomTen(String print, String stat) throws Exception{
		ResultSet mResultSet = getResultSet("SELECT " + print  + ", "  + stat + " FROM "+  tablename +" ORDER BY "+ stat +" ASC LIMIT 10");
		
	    while (mResultSet.next()) {
	    	System.out.println(mResultSet.getString(print) + ": " + mResultSet.getString(stat));
	    }
	}
	
	public void getTopX(int x, String print, String stat, String where) throws Exception{
		ResultSet mResultSet = getResultSet("SELECT " + print + ", " + stat + " FROM "+  tablename +" WHERE " + where + " ORDER BY "+ stat +" DESC LIMIT " + x);
		
	    while (mResultSet.next()) {
	    	System.out.println(mResultSet.getString(print) + ": " + mResultSet.getString(stat));
	    }
	}
	
	public void getTopX(int x, String print, String stat) throws Exception{
		ResultSet mResultSet = getResultSet("SELECT " + print + ", " + stat + " FROM "+  tablename + " ORDER BY "+ stat +" DESC LIMIT " + x);
		
	    while (mResultSet.next()) {
	    	System.out.println(mResultSet.getString(print) + ": " + mResultSet.getString(stat));
	    }
	}
	
	public void getBottomX(int x, String print, String stat, String where) throws Exception{
		ResultSet mResultSet = getResultSet("SELECT " + print + ", " + stat + " FROM "+  tablename +" WHERE " + where + " ORDER BY "+ stat +" ASC LIMIT " + x);
		
	    while (mResultSet.next()) {
	    	System.out.println(mResultSet.getString(print) + ": " + mResultSet.getString(stat));
	    }
	}
	
	
	public void getBottomX(int x, String print, String stat) throws Exception{
		ResultSet mResultSet = getResultSet("SELECT " + print + ", " + stat + " FROM "+  tablename +" ORDER BY "+ stat +" ASC LIMIT " + x);
		
	    while (mResultSet.next()) {
	    	System.out.println(mResultSet.getString(print) + ": " + mResultSet.getString(stat));
	    }
	}
	
	
	public float getStat(String playerLastName, String playerFirstName, String stat) throws Exception{
		ResultSet mResultSet = getResultSet("select * from " + tablename);
		
	    while (mResultSet.next()) {
	        // It is possible to get the columns via name
	        // also possible to get the columns via the column number
	        // which starts at 1
	        // e.g. resultSet.getSTring(2);
	        if(mResultSet.getString("LASTNAME").equals(playerLastName)){
	        	if(playerFirstName == null || playerFirstName.equals(mResultSet.getString("FIRSTNAME"))){
	        		return mResultSet.getFloat(stat);
	        	}
	        }
	      }
		
		return -100;
	}
	
	public float getWARP(String playerName) throws Exception{
		
		ResultSet mResultSet = getResultSet("select * from " + tablename);
		
	    while (mResultSet.next()) {
	        // It is possible to get the columns via name
	        // also possible to get the columns via the column number
	        // which starts at 1
	        // e.g. resultSet.getSTring(2);
	        String lastname = mResultSet.getString("LASTNAME");
	        if(lastname.equals(playerName)){
	        	return mResultSet.getFloat("WARP");
	        }
	      }
		
		return -100;
	}

	public ResultSet getResultSet(String query) throws Exception{
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost/feedback?"
						+ "user=" + this.username + "&password=" + this.password);	

		// Statements allow to issue SQL queries to the database
		statement = connect.createStatement();
		// Result set get the result of the SQL query
		ResultSet mResultSet = statement
				.executeQuery(query);
		
		return mResultSet;
	}

	// You need to close the resultSet
	private void close() {
		try {

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
