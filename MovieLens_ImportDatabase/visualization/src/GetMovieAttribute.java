import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class GetMovieAttribute
{
	//����JDBC����(��Ӧ��mysql)
	static String driverName = "com.mysql.jdbc.Driver";
	//���ӷ����������ݿ�WeiboData
	static String dbURL = "jdbc:mysql://localhost/MovieLens";
	//SQL Sever�û���
	static String userName = "root";
	//SQL Sever����
	static String userPwd = "Sjw8893004";
	
	public static void init() throws SQLException
	{
		//Connection
		Connection conn = null;
		//Statement
		Statement stmt = null;
		//ResultSet
		ResultSet rs = null;
		try 
		{
			try
			{
				Class.forName(driverName);
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			conn = DriverManager.getConnection(dbURL, userName, userPwd);
			stmt = conn.createStatement();
			String tableName = "Movie";
			stmt.execute(
					"Create table if not exists " + tableName + "( MovieID int, Title varchar(100), Genres varchar(50) );"
					);
		}
		finally
		{
			if(rs != null)
			{
				rs.close();
			}
			if(stmt != null)
			{
				stmt.close();
			}
			if(conn != null)
			{
				conn.close();
			}
		}
	}
	public static boolean insertMovie(ArrayList <Movie> movie) throws SQLException
	{
		//Connection
		Connection conn = null;
		//Statement
		Statement stmt = null;
		//ResultSet
		ResultSet rs = null;
		try 
		{
			try
			{
				Class.forName(driverName);
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			conn = DriverManager.getConnection(dbURL, userName, userPwd);
			stmt = conn.createStatement();
			String SQL = "insert into Movie(MovieID, Title, Genres) values ";
			for(int i = 0; i < movie.size() - 1; i ++)
			{
				SQL += "('" + movie.get(i).MovieID + "', '" + movie.get(i).Title + "', '" + movie.get(i).Genres + "'), ";
			}
			SQL += "('" + movie.get(movie.size() - 1).MovieID + "', '" + movie.get(movie.size() - 1).Title + "', '" + movie.get(movie.size() - 1).Genres + "');";
			stmt.executeUpdate(SQL);
			return true;
		}
		finally
		{
			if(rs != null)
			{
				rs.close();
			}
			if(stmt != null)
			{
				stmt.close();
			}
			if(conn != null)
			{
				conn.close();
			}
		}
	}
	public static void readMovieFile() throws IOException, SQLException
	{
		File file = new File("ml-1m//movies.dat");
		BufferedReader br = new BufferedReader(new FileReader(file));
		ArrayList <Movie> m_tmp = new ArrayList <Movie> ();
        while(true)
        {
        	String temp = null;
        	temp = br.readLine();
        	if(temp == null)
        		break;
        	temp = temp.replace("'", "");
        	String[] arr = temp.split("::");
        	Movie movie = new Movie();
        	movie.MovieID = Integer.parseInt(arr[0]);
        	movie.Title = arr[1];
        	movie.Genres = arr[2];
        	m_tmp.add(movie);
        }
        br.close();
        insertMovie(m_tmp);
	}
	public static void main(String[] args) throws SQLException, IOException
	{
		init();
		readMovieFile();
		System.out.println("Database loading finish. ");
	}
}
