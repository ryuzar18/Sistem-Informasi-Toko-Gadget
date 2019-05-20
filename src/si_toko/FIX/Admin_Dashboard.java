/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si_toko.FIX;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author REY
 */
public class Admin_Dashboard extends javax.swing.JFrame implements CRUD{

    public Connection conn;//untuk menyingkat
    public Statement stat;//untuk menyingkat
    public ResultSet rs;//untuk menyingkat
    public DefaultTableModel modelBarang;//untuk menyingkat
    public DefaultTableModel modelJadwal;
    public DefaultTableModel modelKaryawan;
    public DefaultTableModel modelReport;
    public Admin_Dashboard() {
        initComponents();
        Dimension layar = Toolkit.getDefaultToolkit().getScreenSize();//mendapatkan dimensi layar
        int x = layar.width/2-this.getSize().width/2;//inisiasi koordinat x
        int y = layar.height/2-this.getSize().height/2;//inisiasi koordina y
        this.setLocation(x, y);//mengeset lokasi layar berdasarkan variabel x dan y
        LOGIN login = new LOGIN();
        mainJadwal();
        mainBarang();
        mainKaryawan();
        mainReport();
        namaAdmin.setText(login.user);      
    }
    
    public void setPanel(JPanel isi){
        PanelIsi.removeAll();
        PanelIsi.repaint();
        PanelIsi.revalidate();
        PanelIsi.add(isi);
        PanelIsi.repaint();
        PanelIsi.revalidate();
    }
    // REPORT CODE -----------------------------------------------------------------------------------------------------------------------------
    public void mainReport(){
        String[] header ={"No Transaksi","Kode Transaksi", "Tanggal Transaksi", "Jumlah"};//array untuk row header
        modelReport = new DefaultTableModel(header,0);//mengeset tabel sesuai header dengan 0 baris
        report.setModel(modelReport);
        tampilReport();
    }
    
    public void tampilReport(){
        KoneksiDatabase classKoneksi = new KoneksiDatabase();//menjalankan menthod koneksi untuk koneksi ke database
        int jumlahrow = report.getRowCount();//membuat variabel jumlah arrow, yang didapat dari tabel barang
        for (int n = 0; n < jumlahrow; n++) {
            modelReport.removeRow(0);
        }// perulangan untuk mencegah duplikat data yang ada di tabel
        try{
            conn = classKoneksi.getKoneksi();//mengkoneksikan
            stat = conn.createStatement();//membuat statement database
            rs = stat.executeQuery("SELECT * FROM transaksi ORDER BY no_transaksi ASC");//membuat variabel resulset berisi eksekusi query yang menampilkan tabel berdasarkan kondisi nama barang yang sesuai
            while(rs.next()){
                String[] row = {rs.getString("no_transaksi"),rs.getString("kode_transaksi"),rs.getString("tanggal_transaksi"),rs.getString("jumlah")};
                modelReport.addRow(row);//menambah row
            }//perulangan jika resultset masih ada maka menambahkan data di tabel barang ke java tabel
            report.setModel(modelReport);//membuat tabel
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "Tampil data tidak berhasil "+e.getMessage()); 
        }//ketika error terdapat maka muncul pop up
    }
    // END REPORT CODE -----------------------------------------------------------------------------------------------------------------------------
    
    // KARYAWAN CODE -----------------------------------------------------------------------------------------------------------------------------
    public void mainKaryawan(){
        String[] header ={"id_karyawan","nama", "tanggal_lahir", "alamat", "jabatan", "tanggal_masuk", "no_hp"};//array untuk row header
        modelKaryawan = new DefaultTableModel(header,0);//mengeset tabel sesuai header dengan 0 baris
        karyawanx.setModel(modelKaryawan);
        tampilKaryawan();
    }
    
    public void tampilKaryawan(){
        KoneksiDatabase classKoneksi = new KoneksiDatabase();//menjalankan menthod koneksi untuk koneksi ke database
        int jumlahrow = karyawanx.getRowCount();//membuat variabel jumlah arrow, yang didapat dari tabel barang
        for (int n = 0; n < jumlahrow; n++) {
            modelKaryawan.removeRow(0);
        }// perulangan untuk mencegah duplikat data yang ada di tabel
        try{
            conn = classKoneksi.getKoneksi();//mengkoneksikan
            stat = conn.createStatement();//membuat statement database
            rs = stat.executeQuery("SELECT * FROM karyawan ORDER BY id_karyawan ASC");//membuat variabel resulset berisi eksekusi query yang menampilkan tabel berdasarkan kondisi nama barang yang sesuai
            while(rs.next()){
                String[] row = {rs.getString("id_karyawan"),rs.getString("nama"),rs.getString("tanggal_lahir"),rs.getString("alamat"),rs.getString("jabatan"),rs.getString("tanggal_masuk"),rs.getString("no_hp")};
                modelKaryawan.addRow(row);//menambah row
            }//perulangan jika resultset masih ada maka menambahkan data di tabel barang ke java tabel
            karyawanx.setModel(modelKaryawan);//membuat tabel
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "Tampil data tidak berhasil "+e.getMessage()); 
        }//ketika error terdapat maka muncul pop up
    }
    
    public void insertKaryawan(){
        String id = idkaryawanx.getText();
        String alamat = alamatkaryawanx.getText();
        String jabatan = jabatankaryawanx.getText();
        String lahir = lahirkaryawanx.getText();
        String masuk = tanggalkaryawanx.getText();
        String nama = namakaryawanx.getText();
        String nohp = nohpx.getText();
        KoneksiDatabase classKoneksi = new KoneksiDatabase();//mengambil method KoneksiDatabase
        try{
            conn = classKoneksi.getKoneksi();//melakukan method koneksi
            stat = conn.createStatement();//membuat statement
            stat.execute("INSERT INTO karyawan VALUES('"+id+"','"+nama+"','"+lahir+"','"+alamat+"','"+jabatan+"','"+masuk+"','"+nohp+"')");//query untuk insert data barang berdasarkan data yang diinput
            JOptionPane.showMessageDialog(null, "Tambah Data Berhasil");//pop up ketika tambah data berhasil
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Gagal Menambah Data"+e.getMessage());//pop up ketika gagal menambah data
        }
        resetKaryawan();
        tampilKaryawan();
    }
    
    public void searchKaryawan(){
        int jumlahrow = karyawanx.getRowCount();//membuat variabel jumlah arrow, yang didapat dari tabel barang
        String[] coba = new String[7];
        for (int n = 0; n < jumlahrow; n++) {
            for (int i = 1; i < 7; i++) {
                if (pencariankaryawanx.getText().equals(karyawanx.getValueAt(n,i))){
                coba[0]=karyawanx.getValueAt(n, 0).toString();
                coba[1]=karyawanx.getValueAt(n, 1).toString();
                coba[2]=karyawanx.getValueAt(n, 2).toString();
                coba[3]=karyawanx.getValueAt(n, 3).toString();
                coba[4]=karyawanx.getValueAt(n, 4).toString();
                coba[5]=karyawanx.getValueAt(n, 5).toString();
                coba[6]=karyawanx.getValueAt(n, 6).toString();
                modelKaryawan.addRow(coba);
                }
            }
        }
        
        for(int i =0 ; i<jumlahrow;i++){
            modelKaryawan.removeRow(0);
        }
        karyawanx.setModel(modelKaryawan);
    }
    
    public void deleteKaryawan(){
        String id = idkaryawanx.getText();//variabel untuk mengambil text di field kode, hanya kode karena cukup menghapus sesuai primary key
        try{
            stat.executeUpdate("DELETE FROM karyawan WHERE id_karyawan='"+id+"'");//query untuk menghapus baris di database
            JOptionPane.showMessageDialog(null, "Menghapus Data Berhasil");//pop up ketika berhasil
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Gagal Menghapus Data"+e.getMessage());//pop up ketika gagal
        }
        resetKaryawan();//menjalankan reset untuk membersihkan field input
        tampilKaryawan();//menjalankan tampil untuk mengupdate data yang tertampil
    }
    
    public void updateKaryawan(){
        String id = idkaryawanx.getText();
        String alamat = alamatkaryawanx.getText();
        String jabatan = jabatankaryawanx.getText();
        String lahir = lahirkaryawanx.getText();
        String masuk = tanggalkaryawanx.getText();
        String nama = namakaryawanx.getText();
        String nohp = nohpx.getText();
        try{
            stat.execute("UPDATE karyawan SET nama='"+nama+"', tanggal_lahir='"+lahir+"', alamat='"+alamat+"', jabatan='"+jabatan+"', tanggal_masuk='"+masuk+"', no_hp='"+nohp+"' WHERE id_karyawan='"+id+"'");
            JOptionPane.showMessageDialog(null, "Ubah Data Berhasil");//pop up ketika berhasil
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Gagal Ubah Data"+e.getMessage());//pop up ketika gagl
        }
        resetKaryawan();//menjalankan reset untuk membersihkan field input
        tampilKaryawan();//menjalankan tampil untuk mengupdate data yang tertampil
    }
    
    public void resetKaryawan(){
        idkaryawanx.setText("");
        alamatkaryawanx.setText("");
        jabatankaryawanx.setText("");
        lahirkaryawanx.setText("");
        tanggalkaryawanx.setText("");
        namakaryawanx.setText("");
        nohpx.setText("");
    }
    
    public void clickKaryawan(){
        int row = karyawanx.getSelectedRow();//membuat variabel, untuk identifikasi row yang dipilih
        String id = karyawanx.getValueAt(row, 0).toString();
        String nama = karyawanx.getValueAt(row, 1).toString();
        String lahir = karyawanx.getValueAt(row, 2).toString();
        String alamat = karyawanx.getValueAt(row, 3).toString();
        String jabatan = karyawanx.getValueAt(row, 4).toString();
        String masuk = karyawanx.getValueAt(row, 5).toString();
        String nohp = karyawanx.getValueAt(row, 6).toString();
        
        idkaryawanx.setText(id);
        alamatkaryawanx.setText(alamat);
        jabatankaryawanx.setText(jabatan);
        lahirkaryawanx.setText(lahir);
        tanggalkaryawanx.setText(masuk);
        namakaryawanx.setText(nama);
        nohpx.setText(nohp);
    }
    // END KARYAWAN CODE -------------------------------------------------------------------------------------------------------------------------
    
    // JADWAL CODE -----------------------------------------------------------------------------------------------------------------------------
    public void mainJadwal(){
        String[] header ={"No","Hari", "Sesi", "Nama Karyawan"};//array untuk row header
        modelJadwal = new DefaultTableModel(header,0);//mengeset tabel sesuai header dengan 0 baris
        tabelJadwal.setModel(modelJadwal);
        tampilJadwal();
    }
    
    public void insertJadwal(){
        String no = nojadwalx.getText();//variabel untuk mengambil text di field kode
        String nama = namajadwalx.getText();//variabel untuk mengambil text di field nama
        String sesi = sesijadwalx.getText();//variabel untuk mengambil text di field stok
        String hari = harijadwalx.getText();//variabel untuk mengambil text di field harga
        KoneksiDatabase classKoneksi = new KoneksiDatabase();//mengambil method KoneksiDatabase
        try{
            conn = classKoneksi.getKoneksi();//melakukan method koneksi
            stat = conn.createStatement();//membuat statement
            stat.execute("INSERT INTO jadwal VALUES('"+no+"','"+hari+"','"+sesi+"','"+nama+"')");//query untuk insert data barang berdasarkan data yang diinput
            JOptionPane.showMessageDialog(null, "Tambah Data Berhasil");//pop up ketika tambah data berhasil
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Gagal Menambah Data"+e.getMessage());//pop up ketika gagal menambah data
        }
        resetJadwal();//menjalankan reset untuk membersihkan field input
        tampilJadwal();//menjalankan tampil untuk mengupdate data yang tertampil
    }
    
    public void tampilJadwal(){
        KoneksiDatabase classKoneksi = new KoneksiDatabase();//menjalankan menthod koneksi untuk koneksi ke database
        int jumlahrow = tabelJadwal.getRowCount();//membuat variabel jumlah arrow, yang didapat dari tabel barang
        for (int n = 0; n < jumlahrow; n++) {
            modelJadwal.removeRow(0);
        }// perulangan untuk mencegah duplikat data yang ada di tabel
        try{
            conn = classKoneksi.getKoneksi();//mengkoneksikan
            stat = conn.createStatement();//membuat statement database
            rs = stat.executeQuery("SELECT * FROM jadwal ORDER BY no_jadwal ASC");//membuat variabel resulset berisi eksekusi query yang menampilkan tabel berdasarkan kondisi nama barang yang sesuai
            while(rs.next()){
                String[] row = {rs.getString("no_jadwal"),rs.getString("hari"),rs.getString("sesi"),rs.getString("nama_karyawan")};
                modelJadwal.addRow(row);//menambah row
            }//perulangan jika resultset masih ada maka menambahkan data di tabel barang ke java tabel
            tabelJadwal.setModel(modelJadwal);//membuat tabel
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "Tampil data tidak berhasil "+e.getMessage()); 
        }//ketika error terdapat maka muncul pop up
    }
    
    public void updateJadwal(){
        String no = nojadwalx.getText();
        String hari = harijadwalx.getText();
        String sesi = sesijadwalx.getText();
        String nama = namajadwalx.getText();
        try{
            stat.execute("UPDATE jadwal SET nama_karyawan='"+nama+"' WHERE no_jadwal='"+no+"'");//query  untuk mengupdate data, kondisi yang diperlukan yaitu kecocokan primary key
            JOptionPane.showMessageDialog(null, "Ubah Data Berhasil");//pop up ketika berhasil
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Gagal Ubah Data"+e.getMessage());//pop up ketika gagl
        }
        resetJadwal();//menjalankan reset untuk membersihkan field input
        tampilJadwal();//menjalankan tampil untuk mengupdate data yang tertampil
    }
    
    public void resetJadwal(){
        nojadwalx.setText("");
        harijadwalx.setText("");
        sesijadwalx.setText("");
        namajadwalx.setText("");
    }
    
    public void clickJadwal(){
        int row = tabelJadwal.getSelectedRow();//membuat variabel, untuk identifikasi row yang dipilih
        String no = tabelJadwal.getValueAt(row, 0).toString();//mendapatkan data kode pada tabel di row yang sudah dipilih
        String hari = tabelJadwal.getValueAt(row, 1).toString();
        String sesi = tabelJadwal.getValueAt(row, 2).toString();//mendapatkan data nama pada tabel di row yang sudah dipilih
        String nama = tabelJadwal.getValueAt(row, 3).toString();//mendapatkan data stok pada tabel di row yang sudah dipilih
        nojadwalx.setText(no);//mengisi field kode dengan data harga yang sudah didapatkan
        harijadwalx.setText(hari);//mengisi field nama dengan data harga yang sudah didapatkan
        sesijadwalx.setText(sesi);//mengisi field stok dengan data harga yang sudah didapatkan
        namajadwalx.setText(nama);//mengisi field harga dengan data harga yang sudah didapatkan
    }
    // END JADWAL CODE -------------------------------------------------------------------------------------------------------------------------

    // BARANG CODE -----------------------------------------------------------------------------------------------------------------------------
    public void mainBarang(){
        String[] header ={"No","Kode Barang", "Kategori", "Nama Barang", "Jumlah Stok", "Harga"};//array untuk row header
        modelBarang = new DefaultTableModel(header,0);//mengeset tabel sesuai header dengan 0 baris
        barang.setModel(modelBarang);//membuat tabel
        combobox();
        tampilBarang();//menjalankan method tampil
    }
    
    public void combobox(){
        KoneksiDatabase classKoneksi = new KoneksiDatabase();
        try{
            conn = classKoneksi.getKoneksi();//mengkoneksikan
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT DISTINCT kategori FROM barang");
            while(rs.next()){
                kategorix.addItem(rs.getString("kategori"));
            }
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "Tampil data tidak berhasil "+e.getMessage()); 
        }
    }

    public void tampilBarang(){
        KoneksiDatabase classKoneksi = new KoneksiDatabase();//menjalankan menthod koneksi untuk koneksi ke database
        int jumlahrow = barang.getRowCount();//membuat variabel jumlah arrow, yang didapat dari tabel barang
        for (int n = 0; n < jumlahrow; n++) {
            modelBarang.removeRow(0);
        }// perulangan untuk mencegah duplikat data yang ada di tabel
        try{
            conn = classKoneksi.getKoneksi();//mengkoneksikan
            stat = conn.createStatement();//membuat statement database
            rs = stat.executeQuery("SELECT * FROM barang ORDER BY nm_barang ASC");//membuat variabel resulset berisi eksekusi query yang menampilkan tabel berdasarkan kondisi nama barang yang sesuai
            int no = 1;//variabel penomoran
            while(rs.next()){
                String[] row = {Integer.toString(no),rs.getString("kd_barang"),rs.getString("kategori"),rs.getString("nm_barang"),Integer.toString(rs.getInt("stok")),rs.getString("harga")};
                modelBarang.addRow(row);//menambah row
                no++;//penomoran increment
            }//perulangan jika resultset masih ada maka menambahkan data di tabel barang ke java tabel
            barang.setModel(modelBarang);//membuat tabel
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "Tampil data tidak berhasil "+e.getMessage()); 
        }//ketika error terdapat maka muncul pop up
    }
    
    public void insertBarang(){
        String kode = kodex.getText();//variabel untuk mengambil text di field kode
        String nama = namax.getText();//variabel untuk mengambil text di field nama
        String kategori = kategorix.getSelectedItem().toString();
        String stok = hargax.getText();//variabel untuk mengambil text di field stok
        String harga = hargax.getText();//variabel untuk mengambil text di field harga
        KoneksiDatabase classKoneksi = new KoneksiDatabase();//mengambil method KoneksiDatabase
        try{
            conn = classKoneksi.getKoneksi();//melakukan method koneksi
            stat = conn.createStatement();//membuat statement
            stat.execute("INSERT INTO barang VALUES('"+kode+"','"+kategori+"','"+nama+"','"+stok+"','"+harga+"')");//query untuk insert data barang berdasarkan data yang diinput
            JOptionPane.showMessageDialog(null, "Tambah Data Berhasil");//pop up ketika tambah data berhasil
        }
        catch(Exception f){
            JOptionPane.showMessageDialog(null, "Gagal Menambah Data"+f.getMessage());//pop up ketika gagal menambah data
        }
    }
    
    public void searchBarang(){
        int jumlahrow = barang.getRowCount();//membuat variabel jumlah arrow, yang didapat dari tabel barang
        String[] coba = new String[6];
        for (int n = 0; n < jumlahrow; n++) {
            for (int i = 1; i < 6; i++) {
                if (carix.getText().equals(barang.getValueAt(n,i))){
                coba[0]=barang.getValueAt(n, 0).toString();
                coba[1]=barang.getValueAt(n, 1).toString();
                coba[2]=barang.getValueAt(n, 2).toString();
                coba[3]=barang.getValueAt(n, 3).toString();
                coba[4]=barang.getValueAt(n, 4).toString();
                coba[5]=barang.getValueAt(n, 5).toString();
                modelBarang.addRow(coba);
                }
            }
        }
        
        for(int i =0 ; i<jumlahrow;i++){
            modelBarang.removeRow(0);
        }
        barang.setModel(modelBarang);
    }
    
    public void deleteBarang(){
        String kode = kodex.getText();//variabel untuk mengambil text di field kode, hanya kode karena cukup menghapus sesuai primary key
        try{
            stat.executeUpdate("DELETE FROM barang WHERE kd_barang='"+kode+"'");//query untuk menghapus baris di database
            JOptionPane.showMessageDialog(null, "Menghapus Data Berhasil");//pop up ketika berhasil
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Gagal Menghapus Data"+e.getMessage());//pop up ketika gagal
        }
        resetBarang();//menjalankan reset untuk membersihkan field input
        tampilBarang();//menjalankan tampil untuk mengupdate data yang tertampil
    }
    
    public void updateBarang(){
        String kode = kodex.getText();//variabel untuk mengambil text di field kode
        String nama = namax.getText();//variabel untuk mengambil text di field nama
        String kategori = kategorix.getSelectedItem().toString();
        String stok = hargax.getText();//variabel untuk mengambil text di field stok
        String harga = hargax.getText();//variabel untuk mengambil text di field harga
        try{
            stat.execute("UPDATE barang SET nm_barang='"+nama+"',kategori='"+kategori+"',stok='"+stok+"',harga='"+harga+"' WHERE kd_barang='"+kode+"'");//query  untuk mengupdate data, kondisi yang diperlukan yaitu kecocokan primary key
            JOptionPane.showMessageDialog(null, "Ubah Data Berhasil");//pop up ketika berhasil
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Gagal Ubah Data"+e.getMessage());//pop up ketika gagl
        }
        tambahkanx.setEnabled(true);//membuat tombol tambahkan bisa dipencet
        resetBarang();//menjalankan reset untuk membersihkan field input
        tampilBarang();//menjalankan tampil untuk mengupdate data yang tertampil
    }
    
    public void resetBarang(){
        kodex.setText("");//mereset field kode
        namax.setText("");//mereset field nama
        hargax.setText("");//mereset field stok
        hargax.setText("");//mereset field harga
        kodex.setEditable(true);//membuat field kode bisa diedit
        tambahkanx.setEnabled(true);//membuat tombol tambahkan menjadi bisa dipencet
    }
    
    public void clickBarang(){
        int row = barang.getSelectedRow();//membuat variabel, untuk identifikasi row yang dipilih
        tambahkanx.setEnabled(false);//membuat tombol tambahkan tidak bisa dipencet
        perbaruix.setEnabled(true);//membuat tombol perbarui bisa dipencet
        kodex.setEditable(false);//membuat field kode tidak bisa dipencet
        hapusx.setEnabled(true);//membuat tombol hapus bisa dipencet
        String kode = barang.getValueAt(row, 1).toString();//mendapatkan data kode pada tabel di row yang sudah dipilih
        String kategori = barang.getValueAt(row, 2).toString();
        String nama = barang.getValueAt(row, 3).toString();//mendapatkan data nama pada tabel di row yang sudah dipilih
        String stok = barang.getValueAt(row, 4).toString();//mendapatkan data stok pada tabel di row yang sudah dipilih
        String harga = barang.getValueAt(row, 5).toString();//mendapatkan data harga pada tabel di row yang sudah dipilih
        kodex.setText(kode);//mengisi field kode dengan data harga yang sudah didapatkan
        kategorix.setSelectedItem(kategori);
        namax.setText(nama);//mengisi field nama dengan data harga yang sudah didapatkan
        hargax.setText(stok);//mengisi field stok dengan data harga yang sudah didapatkan
        hargax.setText(harga);//mengisi field harga dengan data harga yang sudah didapatkan
    }
    // END BARANGCODE -----------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane4 = new javax.swing.JScrollPane();
        jadwalx1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        PanelMenu = new javax.swing.JPanel();
        BARANG = new javax.swing.JButton();
        JADWAL = new javax.swing.JButton();
        KARYAWAN = new javax.swing.JButton();
        REPORT = new javax.swing.JButton();
        namaAdmin = new javax.swing.JLabel();
        namaAdmin1 = new javax.swing.JLabel();
        LOGOUT = new javax.swing.JButton();
        PanelIsi = new javax.swing.JPanel();
        PanelBarang = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        barang = new javax.swing.JTable();
        kodex = new javax.swing.JTextField();
        namax = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tambahkanx = new javax.swing.JButton();
        resetx = new javax.swing.JButton();
        perbaruix = new javax.swing.JButton();
        hapusx = new javax.swing.JButton();
        carix = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        searchx = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        kategorix = new javax.swing.JComboBox<>();
        hargax = new javax.swing.JFormattedTextField();
        stokx = new javax.swing.JFormattedTextField();
        PanelReport = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        report = new javax.swing.JTable();
        PanelKaryawan = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        karyawanx = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        namakaryawanx = new javax.swing.JTextField();
        lahirkaryawanx = new javax.swing.JTextField();
        alamatkaryawanx = new javax.swing.JTextField();
        resetkaryawanx = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        idkaryawanx = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jabatankaryawanx = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        tanggalkaryawanx = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        tambahkaryawanx = new javax.swing.JButton();
        updatekaryawanx = new javax.swing.JButton();
        deletekaryawanx = new javax.swing.JButton();
        pencariankaryawanx = new javax.swing.JTextField();
        searchkaryawanx = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        nohpx = new javax.swing.JFormattedTextField();
        PanelJadwal = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelJadwal = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        harijadwalx = new javax.swing.JTextField();
        namajadwalx = new javax.swing.JTextField();
        updatejadwalx = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        tambahjadwalx = new javax.swing.JButton();
        nojadwalx = new javax.swing.JFormattedTextField();
        sesijadwalx = new javax.swing.JFormattedTextField();

        jadwalx1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jadwalx1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 51, 51));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelMenu.setBackground(new java.awt.Color(204, 0, 0));
        PanelMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BARANG.setText("BARANG");
        BARANG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BARANGActionPerformed(evt);
            }
        });

        JADWAL.setText("JADWAL");
        JADWAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JADWALActionPerformed(evt);
            }
        });

        KARYAWAN.setText("KARYAWAN");
        KARYAWAN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KARYAWANActionPerformed(evt);
            }
        });

        REPORT.setText("REPORT");
        REPORT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                REPORTActionPerformed(evt);
            }
        });

        namaAdmin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        namaAdmin.setForeground(new java.awt.Color(255, 255, 255));
        namaAdmin.setText("jLabel20");

        namaAdmin1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        namaAdmin1.setForeground(new java.awt.Color(255, 255, 255));
        namaAdmin1.setText("HAI ADMIN");

        LOGOUT.setText("LOGOUT");
        LOGOUT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LOGOUTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelMenuLayout = new javax.swing.GroupLayout(PanelMenu);
        PanelMenu.setLayout(PanelMenuLayout);
        PanelMenuLayout.setHorizontalGroup(
            PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(namaAdmin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BARANG, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JADWAL, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(KARYAWAN, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(REPORT, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(namaAdmin1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMenuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(LOGOUT))
        );
        PanelMenuLayout.setVerticalGroup(
            PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMenuLayout.createSequentialGroup()
                .addComponent(LOGOUT)
                .addGap(17, 17, 17)
                .addComponent(namaAdmin1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(BARANG)
                .addGap(58, 58, 58)
                .addComponent(JADWAL)
                .addGap(52, 52, 52)
                .addComponent(KARYAWAN)
                .addGap(74, 74, 74)
                .addComponent(REPORT)
                .addGap(118, 118, 118))
        );

        jPanel1.add(PanelMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, 540));

        PanelIsi.setLayout(new java.awt.CardLayout());

        barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                barangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(barang);

        jLabel2.setText("Harga");

        jLabel3.setText("Kode Barang");

        jLabel4.setText("Nama Barang");

        jLabel5.setText("Stok");

        tambahkanx.setText("Tambahkan");
        tambahkanx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahkanxActionPerformed(evt);
            }
        });

        resetx.setText("RESET");
        resetx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetxActionPerformed(evt);
            }
        });

        perbaruix.setText("Perbarui");
        perbaruix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                perbaruixActionPerformed(evt);
            }
        });

        hapusx.setText("Hapus");
        hapusx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusxActionPerformed(evt);
            }
        });

        carix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carixActionPerformed(evt);
            }
        });
        carix.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                carixKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("PENCARIAN NAMA BARANG");

        searchx.setText("CARI");
        searchx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchxActionPerformed(evt);
            }
        });

        jLabel7.setText("Kategori");

        hargax.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        stokx.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        javax.swing.GroupLayout PanelBarangLayout = new javax.swing.GroupLayout(PanelBarang);
        PanelBarang.setLayout(PanelBarangLayout);
        PanelBarangLayout.setHorizontalGroup(
            PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBarangLayout.createSequentialGroup()
                .addGap(0, 25, Short.MAX_VALUE)
                .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelBarangLayout.createSequentialGroup()
                        .addGap(422, 422, 422)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(carix, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(searchx, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelBarangLayout.createSequentialGroup()
                        .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PanelBarangLayout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addComponent(resetx)))
                        .addGap(10, 10, 10)
                        .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelBarangLayout.createSequentialGroup()
                                .addComponent(hapusx)
                                .addGap(10, 10, 10)
                                .addComponent(perbaruix)
                                .addGap(10, 10, 10)
                                .addComponent(tambahkanx))
                            .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(kodex, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                                .addComponent(kategorix, 0, 243, Short.MAX_VALUE)
                                .addComponent(namax, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                                .addComponent(stokx, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(hargax, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 25, Short.MAX_VALUE))
        );
        PanelBarangLayout.setVerticalGroup(
            PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBarangLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchx)
                    .addGroup(PanelBarangLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(carix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6)
                .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelBarangLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(stokx, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hargax, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addComponent(resetx))
                    .addGroup(PanelBarangLayout.createSequentialGroup()
                        .addComponent(kodex, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kategorix, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(namax, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(174, 174, 174)
                        .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hapusx)
                            .addComponent(perbaruix)
                            .addComponent(tambahkanx)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 21, Short.MAX_VALUE))
        );

        PanelIsi.add(PanelBarang, "card2");

        jLabel1.setFont(new java.awt.Font("Perpetua Titling MT", 0, 24)); // NOI18N
        jLabel1.setText("LAPORAN TRANSAKSI");

        report.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(report);

        javax.swing.GroupLayout PanelReportLayout = new javax.swing.GroupLayout(PanelReport);
        PanelReport.setLayout(PanelReportLayout);
        PanelReportLayout.setHorizontalGroup(
            PanelReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelReportLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(PanelReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelReportLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(377, 377, 377))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelReportLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1008, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        PanelReportLayout.setVerticalGroup(
            PanelReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelReportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelIsi.add(PanelReport, "card5");

        karyawanx.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        karyawanx.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                karyawanxMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(karyawanx);

        jLabel12.setText("NAMA");

        jLabel13.setText("TANGGAL LAHIR");

        jLabel14.setText("ALAMAT");

        resetkaryawanx.setText("RESET");
        resetkaryawanx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetkaryawanxActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("PENCARIAN");

        jLabel16.setText("JABATAN");

        jLabel17.setText("TANGGAL MASUK");

        jLabel18.setText("NO HP");

        tambahkaryawanx.setText("TAMBAH");
        tambahkaryawanx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahkaryawanxActionPerformed(evt);
            }
        });

        updatekaryawanx.setText("UPDATE");
        updatekaryawanx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatekaryawanxActionPerformed(evt);
            }
        });

        deletekaryawanx.setText("DELETE");
        deletekaryawanx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletekaryawanxActionPerformed(evt);
            }
        });

        pencariankaryawanx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pencariankaryawanxKeyReleased(evt);
            }
        });

        searchkaryawanx.setText("SEARCH");
        searchkaryawanx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchkaryawanxActionPerformed(evt);
            }
        });

        jLabel19.setText("ID KARYAWAN");

        nohpx.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        javax.swing.GroupLayout PanelKaryawanLayout = new javax.swing.GroupLayout(PanelKaryawan);
        PanelKaryawan.setLayout(PanelKaryawanLayout);
        PanelKaryawanLayout.setHorizontalGroup(
            PanelKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelKaryawanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelKaryawanLayout.createSequentialGroup()
                        .addGroup(PanelKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelKaryawanLayout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(idkaryawanx, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelKaryawanLayout.createSequentialGroup()
                                .addComponent(tambahkaryawanx)
                                .addGap(7, 7, 7)
                                .addComponent(updatekaryawanx)
                                .addGap(9, 9, 9)
                                .addComponent(deletekaryawanx)
                                .addGap(11, 11, 11)
                                .addComponent(resetkaryawanx)
                                .addGap(69, 69, 69)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pencariankaryawanx, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(searchkaryawanx))
                            .addGroup(PanelKaryawanLayout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(namakaryawanx, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelKaryawanLayout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(lahirkaryawanx, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelKaryawanLayout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(alamatkaryawanx, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelKaryawanLayout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(jabatankaryawanx, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelKaryawanLayout.createSequentialGroup()
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(nohpx))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelKaryawanLayout.createSequentialGroup()
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(13, 13, 13)
                                    .addComponent(tanggalkaryawanx, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(140, Short.MAX_VALUE))
                    .addGroup(PanelKaryawanLayout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addGap(38, 38, 38))))
        );
        PanelKaryawanLayout.setVerticalGroup(
            PanelKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelKaryawanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(idkaryawanx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(PanelKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(namakaryawanx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(PanelKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(lahirkaryawanx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(PanelKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(alamatkaryawanx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(PanelKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jabatankaryawanx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(PanelKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(tanggalkaryawanx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(PanelKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(nohpx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(PanelKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tambahkaryawanx)
                    .addComponent(updatekaryawanx)
                    .addComponent(deletekaryawanx)
                    .addGroup(PanelKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(resetkaryawanx)
                        .addComponent(jLabel15)
                        .addComponent(pencariankaryawanx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchkaryawanx)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        PanelIsi.add(PanelKaryawan, "card4");

        tabelJadwal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelJadwal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelJadwalMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelJadwal);

        jLabel8.setText("HARI");

        jLabel9.setText("SESI");

        jLabel10.setText("NAMA KARYAWAN");

        updatejadwalx.setText("UPDATE");
        updatejadwalx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatejadwalxActionPerformed(evt);
            }
        });

        jLabel11.setText("NO");

        tambahjadwalx.setText("INSERT");
        tambahjadwalx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahjadwalxActionPerformed(evt);
            }
        });

        nojadwalx.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        sesijadwalx.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        javax.swing.GroupLayout PanelJadwalLayout = new javax.swing.GroupLayout(PanelJadwal);
        PanelJadwal.setLayout(PanelJadwalLayout);
        PanelJadwalLayout.setHorizontalGroup(
            PanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelJadwalLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(PanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelJadwalLayout.createSequentialGroup()
                        .addGroup(PanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelJadwalLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(namajadwalx, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(updatejadwalx))
                            .addGroup(PanelJadwalLayout.createSequentialGroup()
                                .addGroup(PanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelJadwalLayout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(sesijadwalx))
                                    .addGroup(PanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelJadwalLayout.createSequentialGroup()
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(nojadwalx, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelJadwalLayout.createSequentialGroup()
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(10, 10, 10)
                                            .addComponent(harijadwalx, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(98, 98, 98)
                                .addComponent(tambahjadwalx)))
                        .addGap(624, 624, 624))
                    .addGroup(PanelJadwalLayout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
        );
        PanelJadwalLayout.setVerticalGroup(
            PanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelJadwalLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(PanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(nojadwalx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(PanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelJadwalLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel8))
                    .addComponent(harijadwalx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(PanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelJadwalLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(PanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(sesijadwalx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(PanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelJadwalLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel10))
                            .addGroup(PanelJadwalLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(namajadwalx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(updatejadwalx))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(16, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelJadwalLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tambahjadwalx)
                        .addContainerGap())))
        );

        PanelIsi.add(PanelJadwal, "card3");

        jPanel1.add(PanelIsi, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 1040, 540));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1210, 590));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barangMouseClicked
        clickBarang();
    }//GEN-LAST:event_barangMouseClicked

    private void tambahkanxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahkanxActionPerformed
        insertBarang();//ketika tombol tambahkan dipencet maka dijalankan method insert
        tampilBarang();//ketika tombol tambahkan dipencet maka dijalankan method tampil
    }//GEN-LAST:event_tambahkanxActionPerformed

    private void resetxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetxActionPerformed
        resetBarang();//ketika tombol reset dipencet maka dijalankan method reset
    }//GEN-LAST:event_resetxActionPerformed

    private void perbaruixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_perbaruixActionPerformed
        updateBarang();//ketika tombol perbarui dipencet maka menjalankan method update
    }//GEN-LAST:event_perbaruixActionPerformed

    private void hapusxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusxActionPerformed
        deleteBarang();
    }//GEN-LAST:event_hapusxActionPerformed

    private void carixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carixActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_carixActionPerformed

    private void carixKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_carixKeyReleased
        tampilBarang();//menjalankan method tampil
    }//GEN-LAST:event_carixKeyReleased

    private void searchxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchxActionPerformed
        searchBarang();
    }//GEN-LAST:event_searchxActionPerformed

    private void tabelJadwalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelJadwalMouseClicked
        clickJadwal();
    }//GEN-LAST:event_tabelJadwalMouseClicked

    private void updatejadwalxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatejadwalxActionPerformed
        updateJadwal();
    }//GEN-LAST:event_updatejadwalxActionPerformed

    private void tambahjadwalxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahjadwalxActionPerformed
        insertJadwal();
    }//GEN-LAST:event_tambahjadwalxActionPerformed

    private void karyawanxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_karyawanxMouseClicked
        clickKaryawan();
    }//GEN-LAST:event_karyawanxMouseClicked

    private void resetkaryawanxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetkaryawanxActionPerformed
        resetKaryawan();
    }//GEN-LAST:event_resetkaryawanxActionPerformed

    private void tambahkaryawanxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahkaryawanxActionPerformed
        insertKaryawan();
    }//GEN-LAST:event_tambahkaryawanxActionPerformed

    private void updatekaryawanxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatekaryawanxActionPerformed
        updateKaryawan();
    }//GEN-LAST:event_updatekaryawanxActionPerformed

    private void deletekaryawanxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletekaryawanxActionPerformed
        deleteKaryawan();
    }//GEN-LAST:event_deletekaryawanxActionPerformed

    private void pencariankaryawanxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pencariankaryawanxKeyReleased
        tampilKaryawan();
    }//GEN-LAST:event_pencariankaryawanxKeyReleased

    private void searchkaryawanxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchkaryawanxActionPerformed
        searchKaryawan();
    }//GEN-LAST:event_searchkaryawanxActionPerformed

    private void BARANGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BARANGActionPerformed
        setPanel(PanelBarang);
    }//GEN-LAST:event_BARANGActionPerformed

    private void JADWALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JADWALActionPerformed
        setPanel(PanelJadwal);
    }//GEN-LAST:event_JADWALActionPerformed

    private void KARYAWANActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KARYAWANActionPerformed
        setPanel(PanelKaryawan);
    }//GEN-LAST:event_KARYAWANActionPerformed

    private void REPORTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_REPORTActionPerformed
        setPanel(PanelReport);
    }//GEN-LAST:event_REPORTActionPerformed

    private void LOGOUTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LOGOUTActionPerformed
        new LOGIN().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_LOGOUTActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Admin_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin_Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BARANG;
    private javax.swing.JButton JADWAL;
    private javax.swing.JButton KARYAWAN;
    private javax.swing.JButton LOGOUT;
    private javax.swing.JPanel PanelBarang;
    private javax.swing.JPanel PanelIsi;
    private javax.swing.JPanel PanelJadwal;
    private javax.swing.JPanel PanelKaryawan;
    private javax.swing.JPanel PanelMenu;
    private javax.swing.JPanel PanelReport;
    private javax.swing.JButton REPORT;
    private javax.swing.JTextField alamatkaryawanx;
    private javax.swing.JTable barang;
    private javax.swing.JTextField carix;
    private javax.swing.JButton deletekaryawanx;
    private javax.swing.JButton hapusx;
    private javax.swing.JFormattedTextField hargax;
    private javax.swing.JTextField harijadwalx;
    private javax.swing.JTextField idkaryawanx;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jabatankaryawanx;
    private javax.swing.JTable jadwalx1;
    private javax.swing.JTable karyawanx;
    private javax.swing.JComboBox<String> kategorix;
    private javax.swing.JTextField kodex;
    private javax.swing.JTextField lahirkaryawanx;
    private javax.swing.JLabel namaAdmin;
    private javax.swing.JLabel namaAdmin1;
    private javax.swing.JTextField namajadwalx;
    private javax.swing.JTextField namakaryawanx;
    private javax.swing.JTextField namax;
    private javax.swing.JFormattedTextField nohpx;
    private javax.swing.JFormattedTextField nojadwalx;
    private javax.swing.JTextField pencariankaryawanx;
    private javax.swing.JButton perbaruix;
    private javax.swing.JTable report;
    private javax.swing.JButton resetkaryawanx;
    private javax.swing.JButton resetx;
    private javax.swing.JButton searchkaryawanx;
    private javax.swing.JButton searchx;
    private javax.swing.JFormattedTextField sesijadwalx;
    private javax.swing.JFormattedTextField stokx;
    private javax.swing.JTable tabelJadwal;
    private javax.swing.JButton tambahjadwalx;
    private javax.swing.JButton tambahkanx;
    private javax.swing.JButton tambahkaryawanx;
    private javax.swing.JTextField tanggalkaryawanx;
    private javax.swing.JButton updatejadwalx;
    private javax.swing.JButton updatekaryawanx;
    // End of variables declaration//GEN-END:variables
}
