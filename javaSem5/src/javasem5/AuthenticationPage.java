///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package javasem5;
//import java.sql.*;
///**
// *
// * @author Shardul
// */
//        
////this is the question given by sir, written in the notebook
//import javax.swing.JOptionPane;
//public class AuthenticationPage {
//        public void btnSubmitactionPerformed(){
//        DBConnect db = new DBConnect();
//        Connection con;
//        PreparedStatement ps;
//        String str = "select * from users where id=? and pswd=?";
//
//        try{
//            ps = con.prepareStatement(str);
//            ps.setString(1,txtUname.getText().trim());
//            ps.setString(2,txtPswd.getText().trim());
//            Result rs = ps.executeQuery();
//            if(rs.next()){
//                MainERP x = new MainERP;
//                x.setVisible(true);
//                x.setVisible();
//            }else{
//                JOptionPane.showMessageDialog(null, "Invalid User");
//            }
//        }catch(SQLException e){
//            
//        }
//            
//            
//        }
//                
//    }
//}
//}
