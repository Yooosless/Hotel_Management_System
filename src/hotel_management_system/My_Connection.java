
package hotel_management_system;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author afrid
 */
public class My_Connection {
    
    public Connection createConnection()
    {
        Connection connection = null;
        
        MysqlDataSource mds = new MysqlDataSource();
        
        mds.setServerName("localhost");
        mds.setPortNumber(3306);
        mds.setUser("root");
        mds.setPassword("");
        mds.setDatabaseName("hotelmanagement_db");
        
        try {
            connection = mds.getConnection();
                    } catch (SQLException ex) {
            Logger.getLogger(My_Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
    
    
    
}
