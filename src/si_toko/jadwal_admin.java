package si_toko;

import si_toko.FIX.KoneksiDatabase;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import si_toko.FIX.CRUD;

public class jadwal_admin extends javax.swing.JFrame implements CRUD {
    public Connection conn;//untuk menyingkat
    public Statement stat;//untuk menyingkat
    public ResultSet rs;//untuk menyingkat
    public DefaultTableModel model;//untuk menyingkat
    
    public jadwal_admin() {
        initComponents();//menginisiasi seluruh fungsi JFrame
        Dimension layar = Toolkit.getDefaultToolkit().getScreenSize();//mendapatkan dimensi layar
        int x = layar.width/2-this.getSize().width/2;//inisiasi koordinat x
        int y = layar.height/2-this.getSize().height/2;//inisiasi koordina y
        this.setLocation(x, y);//mengeset lokasi layar berdasarkan variabel x dan y
        String[] header ={"No","Hari", "Sesi", "Nama Karyawan"};//array untuk row header
        model = new DefaultTableModel(header,0);//mengeset tabel sesuai header dengan 0 baris
        jadwalx.setModel(model);
        tampil();
    }
    
    public void insert(){
        String no = nox.getText();//variabel untuk mengambil text di field kode
        String nama = namax.getText();//variabel untuk mengambil text di field nama
        String sesi = sesix.getText();//variabel untuk mengambil text di field stok
        String hari = harix.getText();//variabel untuk mengambil text di field harga
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
        reset();//menjalankan reset untuk membersihkan field input
        tampil();//menjalankan tampil untuk mengupdate data yang tertampil
    }
    
    public void tampil(){
        KoneksiDatabase classKoneksi = new KoneksiDatabase();//menjalankan menthod koneksi untuk koneksi ke database
        int jumlahrow = jadwalx.getRowCount();//membuat variabel jumlah arrow, yang didapat dari tabel barang
        for (int n = 0; n < jumlahrow; n++) {
            model.removeRow(0);
        }// perulangan untuk mencegah duplikat data yang ada di tabel
        try{
            conn = classKoneksi.getKoneksi();//mengkoneksikan
            stat = conn.createStatement();//membuat statement database
            rs = stat.executeQuery("SELECT * FROM jadwal ORDER BY no_jadwal ASC");//membuat variabel resulset berisi eksekusi query yang menampilkan tabel berdasarkan kondisi nama barang yang sesuai
            while(rs.next()){
                String[] row = {rs.getString("no_jadwal"),rs.getString("hari"),rs.getString("sesi"),rs.getString("nama_karyawan")};
                model.addRow(row);//menambah row
            }//perulangan jika resultset masih ada maka menambahkan data di tabel barang ke java tabel
            jadwalx.setModel(model);//membuat tabel
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "Tampil data tidak berhasil "+e.getMessage()); 
        }//ketika error terdapat maka muncul pop up
    }
    
    public void update(){
        String no = nox.getText();
        String hari = harix.getText();
        String sesi = sesix.getText();
        String nama = namax.getText();
        try{
            stat.execute("UPDATE jadwal SET nama_karyawan='"+nama+"' WHERE no_jadwal='"+no+"'");//query  untuk mengupdate data, kondisi yang diperlukan yaitu kecocokan primary key
            JOptionPane.showMessageDialog(null, "Ubah Data Berhasil");//pop up ketika berhasil
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Gagal Ubah Data"+e.getMessage());//pop up ketika gagl
        }
        reset();//menjalankan reset untuk membersihkan field input
        tampil();//menjalankan tampil untuk mengupdate data yang tertampil
    }
    
    public void reset(){
        nox.setText("");
        harix.setText("");
        sesix.setText("");
        namax.setText("");
    }
    
    public void click(){
        int row = jadwalx.getSelectedRow();//membuat variabel, untuk identifikasi row yang dipilih
        String no = jadwalx.getValueAt(row, 0).toString();//mendapatkan data kode pada tabel di row yang sudah dipilih
        String hari = jadwalx.getValueAt(row, 1).toString();
        String sesi = jadwalx.getValueAt(row, 2).toString();//mendapatkan data nama pada tabel di row yang sudah dipilih
        String nama = jadwalx.getValueAt(row, 3).toString();//mendapatkan data stok pada tabel di row yang sudah dipilih
        nox.setText(no);//mengisi field kode dengan data harga yang sudah didapatkan
        harix.setText(hari);//mengisi field nama dengan data harga yang sudah didapatkan
        sesix.setText(sesi);//mengisi field stok dengan data harga yang sudah didapatkan
        namax.setText(nama);//mengisi field harga dengan data harga yang sudah didapatkan
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
        jadwalx = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        harix = new javax.swing.JTextField();
        sesix = new javax.swing.JTextField();
        namax = new javax.swing.JTextField();
        updatex = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        nox = new javax.swing.JTextField();
        menux = new javax.swing.JButton();
        tambahx = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jadwalx.setModel(new javax.swing.table.DefaultTableModel(
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
        jadwalx.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jadwalxMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jadwalx);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 228, 444, 277));

        jLabel1.setFont(new java.awt.Font("Perpetua Titling MT", 0, 24)); // NOI18N
        jLabel1.setText("JADWAL KARYAWAN");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 11, -1, 48));

        jLabel2.setText("HARI");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 113, 97, -1));

        jLabel3.setText("SESI");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 151, 97, -1));

        jLabel4.setText("NAMA KARYAWAN");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 191, 97, -1));
        getContentPane().add(harix, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 110, 115, -1));
        getContentPane().add(sesix, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 148, 115, -1));
        getContentPane().add(namax, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 188, 169, -1));

        updatex.setText("UPDATE");
        updatex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatexActionPerformed(evt);
            }
        });
        getContentPane().add(updatex, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 187, -1, -1));

        jLabel5.setText("NO");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 75, 97, -1));
        getContentPane().add(nox, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 72, 115, -1));

        menux.setText("MENU");
        menux.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuxActionPerformed(evt);
            }
        });
        getContentPane().add(menux, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, -1, -1));

        tambahx.setText("INSERT");
        tambahx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahxActionPerformed(evt);
            }
        });
        getContentPane().add(tambahx, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jadwalxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jadwalxMouseClicked
        click();
    }//GEN-LAST:event_jadwalxMouseClicked

    private void updatexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatexActionPerformed
        update();
    }//GEN-LAST:event_updatexActionPerformed

    private void menuxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuxActionPerformed
        adminMenu menu = new adminMenu();
        menu.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_menuxActionPerformed

    private void tambahxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahxActionPerformed
        insert();
    }//GEN-LAST:event_tambahxActionPerformed

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
            java.util.logging.Logger.getLogger(jadwal_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jadwal_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jadwal_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jadwal_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jadwal_admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField harix;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jadwalx;
    private javax.swing.JButton menux;
    private javax.swing.JTextField namax;
    private javax.swing.JTextField nox;
    private javax.swing.JTextField sesix;
    private javax.swing.JButton tambahx;
    private javax.swing.JButton updatex;
    // End of variables declaration//GEN-END:variables

    @Override
    public void delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void search() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
