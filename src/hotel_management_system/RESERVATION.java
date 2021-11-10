
package hotel_management_system;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author afrid
 */
public class RESERVATION {
    
    //reservation table we need to add two forgein keys
//alter Table Reservations ADD constraint fk_client_id Foreign key(client id) references clients(id) on delete cascade    
//for the client and for the rooms
//alter Table Reservations ADD constraint fk_room_number Foreign key(room number) references rooms(r_number) on delete cascade    
//alter Table rooms ADD constraint fk_type_id Foreign key(type) references type(id) on delete cascade  
 
 //    
    My_Connection my_connection = new My_Connection();
    ROOMS room = new ROOMS();
    
    public boolean addReservation(int client_id,int room_number, String dateIn, String dateOut)
    {
        PreparedStatement st;
        String addQuery="INSERT INTO `reservations`( `client_id`, `room_number`, `date_in`, `date_out`) VALUES (?,?,?,?)";
        try {
            st = my_connection.createConnection().prepareStatement(addQuery);
            
            st.setInt(1, client_id);
            st.setInt(2, room_number);
            st.setString(3, dateIn);
            st.setString(4, dateOut);
            
            if(room.isRoomReserved(room_number).equals("No"))
            {
              if (st.executeUpdate()>0)
               {
                    room.setRoomToReserved(room_number, "Yes");
                    return true;
                }else{
                     return false;
                     }  
            }else {
                JOptionPane.showMessageDialog(null,"This Room is Already Reserved","Room Reserved",JOptionPane.WARNING_MESSAGE); 
                return false;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(CLIENT.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }  
    }

    public boolean editReservation(int reservation_id, int client_id,int room_number,String dateIn, String dateOut)
    {
        PreparedStatement st;
        String editQuery="UPDATE `reservations` SET `client_id`=?,`room_number`=?,`date_in`=?,`date_out`=? WHERE `id`=?";
        
        try {
            st = my_connection.createConnection().prepareStatement(editQuery);
            
            st.setInt(1, client_id);
            st.setInt(2, room_number);
            st.setString(3, dateIn);
            st.setString(4, dateOut);
            st.setInt(5, reservation_id);

            
            return(st.executeUpdate()>0);
           
            
            }
              catch (SQLException ex) {
              Logger.getLogger(CLIENT.class.getName()).log(Level.SEVERE, null, ex);
              return false;
        }  
    } 
   // create a function to remove the reservation 
    public boolean removeReservation(int reservation_id){
       PreparedStatement st;
        String deleteQuery="DELETE FROM `reservations` WHERE `id`=?";
        
        try {
            st = my_connection.createConnection().prepareStatement(deleteQuery);
            
            st.setInt(1, reservation_id);
            
            int room_number = getRoomNumberFromReservationID(reservation_id);
            
            if (st.executeUpdate()>0)
            {
                room.setRoomToReserved(room_number, "No");
                return true;
            }else{
                return false;
            }
            
            
            }
              catch (SQLException ex) {
              Logger.getLogger(CLIENT.class.getName()).log(Level.SEVERE, null, ex);
              return false;
        }  
    }
    
     public void fillReservationJTable(JTable table)
    {
        PreparedStatement ps;
        ResultSet rs;
        String selectQuery= "SELECT * FROM `reservations`";
        try {
            ps = my_connection.createConnection().prepareStatement(selectQuery);
            
            rs = ps.executeQuery();
            
            DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            Object[] row;
            
            while(rs.next())
            {
               row = new Object[5];
               row[0] = rs.getInt(1);
               row[1] = rs.getInt(2);
               row[2] = rs.getInt(3);
               row[3] = rs.getString(4);
               row[4] = rs.getString(5);
               
               tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CLIENT.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
     // a function to get the room from a reservation
    public int getRoomNumberFromReservationID(int reservationID)
    {
         PreparedStatement ps;
        ResultSet rs;
        String selectQuery= "SELECT  `room_number` FROM `reservations` WHERE `id`=?";
        try {
            ps = my_connection.createConnection().prepareStatement(selectQuery);
            ps.setInt(1, reservationID);
            rs = ps.executeQuery();
            
            if(rs.next())
            {
               return rs.getInt(1);
            }else{
                return 0;
            }
       } catch (SQLException ex) {
            Logger.getLogger(CLIENT.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    
    
    
    
}
