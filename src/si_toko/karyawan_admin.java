package si_toko;

import si_toko.FIX.KoneksiDatabase;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import si_toko.FIX.CRUD;

public class karyawan_admin extends javax.swing.JFrame implements CRUD {
    public Connection conn;//untuk menyingkat
    public Statement stat;//untuk menyingkat
    public ResultSet rs;//untuk menyingkat
    public DefaultTableModel model;//untuk menyingkat
    public karyawan_admin() {
        initComponents();//menginisiasi seluruh fungsi JFrame
        Dimension layar = Toolkit.getDefaultToolkit().getScreenSize();//mendapatkan dimensi layar
        int x = layar.width/2-this.getSize().width/2;//inisiasi koordinat x
        int y = layar.height/2-this.getSize().height/2;//inisiasi koordina y
        this.setLocation(x, y);//mengeset lokasi layar berdasarkan variabel x dan y
        String[] header ={"id_karyawan","nama", "tanggal_lahir", "alamat", "jabatan", "tanggal_masuk", "no_hp"};//array untuk row header
        model = new DefaultTableModel(header,0);//mengeset tabel sesuai header dengan 0 baris
        karyawanx.setModel(model);
        tampil();
    }
    
    public void tampil(){
        KoneksiDatabase classKoneksi = new KoneksiDatabase();//menjalankan menthod koneksi untuk koneksi ke database
        int jumlahrow = karyawanx.getRowCount();//membuat variabel jumlah arrow, yang didapat dari tabel barang
        for (int n = 0; n < jumlahrow; n++) {
            model.removeRow(0);
        }// perulangan untuk mencegah duplikat data yang ada di tabel
        try{
            conn = classKoneksi.getKoneksi();//mengkoneksikan
            stat = conn.createStatement();//membuat statement database
            rs = stat.executeQuery("SELECT * FROM karyawan ORDER BY id_karyawan ASC");//membuat variabel resulset berisi eksekusi query yang menampilkan tabel berdasarkan kondisi nama barang yang sesuai
            while(rs.next()){
                String[] row = {rs.getString("id_karyawan"),rs.getString("nama"),rs.getString("tanggal_lahir"),rs.getString("alamat"),rs.getString("jabatan"),rs.getString("tanggal_masuk"),rs.getString("no_hp")};
                model.addRow(row);//menambah row
            }//perulangan jika resultset masih ada maka menambahkan data di tabel barang ke java tabel
            karyawanx.setModel(model);//membuat tabel
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "Tampil data tidak berhasil "+e.getMessage()); 
        }//ketika error terdapat maka muncul pop up
    }
    
    public void insert(){
        String id = idx.getText();
        String alamat = alamatx.getText();
        String jabatan = jabatanx.getText();
        String lahir = lahirx.getText();
        String masuk = masukx.getText();
        String nama = namax.getText();
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
        reset();
        tampil();
    }
    
    public void search(){
        int jumlahrow = karyawanx.getRowCount();//membuat variabel jumlah arrow, yang didapat dari tabel barang
        String[] coba = new String[7];
        for (int n = 0; n < jumlahrow; n++) {
            for (int i = 1; i < 7; i++) {
                if (pencarianx.getText().equals(karyawanx.getValueAt(n,i))){
                coba[0]=karyawanx.getValueAt(n, 0).toString();
                coba[1]=karyawanx.getValueAt(n, 1).toString();
                coba[2]=karyawanx.getValueAt(n, 2).toString();
                coba[3]=karyawanx.getValueAt(n, 3).toString();
                coba[4]=karyawanx.getValueAt(n, 4).toString();
                coba[5]=karyawanx.getValueAt(n, 5).toString();
                coba[6]=karyawanx.getValueAt(n, 6).toString();
                model.addRow(coba);
                }
            }
        }
        
        for(int i =0 ; i<jumlahrow;i++){
            model.removeRow(0);
        }
        karyawanx.setModel(model);
    }
    
    public void delete(){
        String id = idx.getText();//variabel untuk mengambil text di field kode, hanya kode karena cukup menghapus sesuai primary key
        try{
            stat.executeUpdate("DELETE FROM karyawan WHERE id_karyawan='"+id+"'");//query untuk menghapus baris di database
            JOptionPane.showMessageDialog(null, "Menghapus Data Berhasil");//pop up ketika berhasil
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Gagal Menghapus Data"+e.getMessage());//pop up ketika gagal
        }
        reset();//menjalankan reset untuk membersihkan field input
        tampil();//menjalankan tampil untuk mengupdate data yang tertampil
    }
    
    public void update(){
        String id = idx.getText();
        String alamat = alamatx.getText();
        String jabatan = jabatanx.getText();
        String lahir = lahirx.getText();
        String masuk = masukx.getText();
        String nama = namax.getText();
        String nohp = nohpx.getText();
        try{
            stat.execute("UPDATE karyawan SET nama='"+nama+"', tanggal_lahir='"+lahir+"', alamat='"+alamat+"', jabatan='"+jabatan+"', tanggal_masuk='"+masuk+"', no_hp='"+nohp+"' WHERE id_karyawan='"+id+"'");
            JOptionPane.showMessageDialog(null, "Ubah Data Berhasil");//pop up ketika berhasil
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Gagal Ubah Data"+e.getMessage());//pop up ketika gagl
        }
        reset();//menjalankan reset untuk membersihkan field input
        tampil();//menjalankan tampil untuk mengupdate data yang tertampil
    }
    
    public void reset(){
        idx.setText("");
        alamatx.setText("");
        jabatanx.setText("");
        lahirx.setText("");
        masukx.setText("");
        namax.setText("");
        nohpx.setText("");
    }
    
    public void click(){
        int row = karyawanx.getSelectedRow();//membuat variabel, untuk identifikasi row yang dipilih
        String id = karyawanx.getValueAt(row, 0).toString();
        String nama = karyawanx.getValueAt(row, 1).toString();
        String lahir = karyawanx.getValueAt(row, 2).toString();
        String alamat = karyawanx.getValueAt(row, 3).toString();
        String jabatan = karyawanx.getValueAt(row, 4).toString();
        String masuk = karyawanx.getValueAt(row, 5).toString();
        String nohp = karyawanx.getValueAt(row, 6).toString();
        
        idx.setText(id);
        alamatx.setText(alamat);
        jabatanx.setText(jabatan);
        lahirx.setText(lahir);
        masukx.setText(masuk);
        namax.setText(nama);
        nohpx.setText(nohp);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        karyawanx = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        namax = new javax.swing.JTextField();
        lahirx = new javax.swing.JTextField();
        alamatx = new javax.swing.JTextField();
        resetx = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        idx = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jabatanx = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        masukx = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        nohpx = new javax.swing.JTextField();
        tambahx = new javax.swing.JButton();
        updatex = new javax.swing.JButton();
        deletex = new javax.swing.JButton();
        pencarianx = new javax.swing.JTextField();
        searchx = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        menux = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jScrollPane1.setViewportView(karyawanx);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 640, 260));

        jLabel1.setFont(new java.awt.Font("Perpetua Titling MT", 0, 24)); // NOI18N
        jLabel1.setText("DATA KARYAWAN");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, 48));

        jLabel2.setText("NAMA");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 97, -1));

        jLabel3.setText("TANGGAL LAHIR");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 97, -1));

        jLabel4.setText("ALAMAT");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 97, -1));
        getContentPane().add(namax, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 115, -1));
        getContentPane().add(lahirx, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 115, -1));
        getContentPane().add(alamatx, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 169, -1));

        resetx.setText("RESET");
        resetx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetxActionPerformed(evt);
            }
        });
        getContentPane().add(resetx, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 290, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("PENCARIAN");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 97, -1));
        getContentPane().add(idx, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 115, -1));

        jLabel6.setText("JABATAN");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 97, -1));
        getContentPane().add(jabatanx, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, 169, -1));

        jLabel7.setText("TANGGAL MASUK");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 97, -1));
        getContentPane().add(masukx, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, 169, -1));

        jLabel8.setText("NO HP");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 97, -1));
        getContentPane().add(nohpx, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, 169, -1));

        tambahx.setText("TAMBAH");
        tambahx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahxActionPerformed(evt);
            }
        });
        getContentPane().add(tambahx, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        updatex.setText("UPDATE");
        updatex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatexActionPerformed(evt);
            }
        });
        getContentPane().add(updatex, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 290, -1, -1));

        deletex.setText("DELETE");
        deletex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletexActionPerformed(evt);
            }
        });
        getContentPane().add(deletex, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 290, -1, -1));

        pencarianx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pencarianxKeyReleased(evt);
            }
        });
        getContentPane().add(pencarianx, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 320, -1));

        searchx.setText("SEARCH");
        searchx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchxActionPerformed(evt);
            }
        });
        getContentPane().add(searchx, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 30, -1, -1));

        jLabel9.setText("ID KARYAWAN");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 97, -1));

        menux.setText("MENU");
        menux.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuxActionPerformed(evt);
            }
        });
        getContentPane().add(menux, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 30, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void karyawanxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_karyawanxMouseClicked
        click();
    }//GEN-LAST:event_karyawanxMouseClicked

    private void resetxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetxActionPerformed
        reset();
    }//GEN-LAST:event_resetxActionPerformed

    private void tambahxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahxActionPerformed
        insert();
    }//GEN-LAST:event_tambahxActionPerformed

    private void updatexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatexActionPerformed
        update();
    }//GEN-LAST:event_updatexActionPerformed

    private void deletexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletexActionPerformed
        delete();
    }//GEN-LAST:event_deletexActionPerformed

    private void searchxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchxActionPerformed
        search();
    }//GEN-LAST:event_searchxActionPerformed

    private void pencarianxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pencarianxKeyReleased
        tampil();
    }//GEN-LAST:event_pencarianxKeyReleased

    private void menuxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuxActionPerformed
        adminMenu menu = new adminMenu();
        menu.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_menuxActionPerformed

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
            java.util.logging.Logger.getLogger(karyawan_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(karyawan_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(karyawan_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(karyawan_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new karyawan_admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField alamatx;
    private javax.swing.JButton deletex;
    private javax.swing.JTextField idx;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jabatanx;
    private javax.swing.JTable karyawanx;
    private javax.swing.JTextField lahirx;
    private javax.swing.JTextField masukx;
    private javax.swing.JButton menux;
    private javax.swing.JTextField namax;
    private javax.swing.JTextField nohpx;
    private javax.swing.JTextField pencarianx;
    private javax.swing.JButton resetx;
    private javax.swing.JButton searchx;
    private javax.swing.JButton tambahx;
    private javax.swing.JButton updatex;
    // End of variables declaration//GEN-END:variables
}
