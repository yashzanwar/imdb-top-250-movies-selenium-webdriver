import java.sql.*;
import java.util.ArrayList;

public class DataBaseActivity {

    public void dataBase(ArrayList<panda1> list) {
        Connection c;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:imdb.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");


            String sql;
            stmt = c.createStatement();

            sql = "DROP TABLE IF EXISTS Top250Movies";
            stmt.executeUpdate(sql);


            sql = "CREATE TABLE Top250Movies (Name CHAR(100) NOT NULL, Year INT ,Rating REAL)";
            System.out.println("Table created successfully");
            stmt.executeUpdate(sql);

            for (int i=0;i<list.size();i++) {
                String MovieName = list.get(i).getMovieName();
                int Year = list.get(i).getYear();
                double Rating = list.get(i).getRating();
                if(list.get(i).getMovieName().contains("'")){
                    MovieName = MovieName.replace("'","''");
                }

               sql = "INSERT INTO Top250Movies "+
                       "VALUES (?,?,?)";
                PreparedStatement preparedStatement = c.prepareStatement(sql);
                preparedStatement.setString(1,MovieName);
                preparedStatement.setInt(2,Year);
                preparedStatement.setDouble(3,Rating);
                System.out.println(sql);
                preparedStatement.executeUpdate();
            }
            stmt.close();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM Top250Movies;" );
            while ( rs.next() ) {
                String name = rs.getString("Name");
                int year = rs.getInt("Year");
                double rating = rs.getDouble("Rating");
                System.out.println(name + " "+ year+ " "+ rating);
            }
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

    }

}
