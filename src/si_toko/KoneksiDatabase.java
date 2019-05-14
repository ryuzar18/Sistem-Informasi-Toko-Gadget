package si_toko;
import java.sql.*;
public class KoneksiDatabase{
    public Connection koneksi;//method untuk menyingkat connection menjadi koneksi
    public Statement stat;//method untuk menyingkat statement menjadi stat
    public Connection getKoneksi(){
        try{
            Class.forName("com.mysql.jdbc.Driver");//alamat url driver mysql
        }
        catch(ClassNotFoundException ex){//untuk menangkap error ketika tidak bisa mengakses driver mysql
        }
        try{
            koneksi = DriverManager.getConnection("jdbc:mysql://localhost/to.com","root","");//memerintahkan driver untuk melakukan koneksi berdasarkan url database
        }
        catch(SQLException ex){
            System.out.println("Gagal Koneksi");//output ketika gagal koneksi
        }
        return koneksi;//return variabel koneksi
    }
}