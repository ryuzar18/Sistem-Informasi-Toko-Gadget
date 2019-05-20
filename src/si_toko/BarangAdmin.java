package si_toko;//package dari class
import si_toko.FIX.KoneksiDatabase;
import java.awt.*;//import library java awt
import java.sql.*;//import library java sql
import javax.swing.JOptionPane;//import library java option pane
import javax.swing.table.DefaultTableModel;//import library tabel
import si_toko.FIX.CRUD;
public class BarangAdmin extends javax.swing.JFrame{
    public Connection conn;//untuk menyingkat
    public Statement stat;//untuk menyingkat
    public ResultSet rs;//untuk menyingkat
    public DefaultTableModel model;//untuk menyingkat
    public BarangAdmin() {
        initComponents();//menginisiasi seluruh fungsi JFrame
        Dimension layar = Toolkit.getDefaultToolkit().getScreenSize();//mendapatkan dimensi layar
        int x = layar.width/2-this.getSize().width/2;//inisiasi koordinat x
        int y = layar.height/2-this.getSize().height/2;//inisiasi koordina y
        this.setLocation(x, y);//mengeset lokasi layar berdasarkan variabel x dan y
        String[] header ={"No","Kode Barang", "Kategori", "Nama Barang", "Jumlah Stok", "Harga"};//array untuk row header
        model = new DefaultTableModel(header,0);//mengeset tabel sesuai header dengan 0 baris
        barang.setModel(model);//membuat tabel
        tampil();//menjalankan method tampil
        
    }
    
    public void tampil(){
        KoneksiDatabase classKoneksi = new KoneksiDatabase();//menjalankan menthod koneksi untuk koneksi ke database
        int jumlahrow = barang.getRowCount();//membuat variabel jumlah arrow, yang didapat dari tabel barang
        for (int n = 0; n < jumlahrow; n++) {
            model.removeRow(0);
        }// perulangan untuk mencegah duplikat data yang ada di tabel
        try{
            conn = classKoneksi.getKoneksi();//mengkoneksikan
            stat = conn.createStatement();//membuat statement database
            rs = stat.executeQuery("SELECT * FROM barang ORDER BY nm_barang ASC");//membuat variabel resulset berisi eksekusi query yang menampilkan tabel berdasarkan kondisi nama barang yang sesuai
            int no = 1;//variabel penomoran
            while(rs.next()){
                String[] row = {Integer.toString(no),rs.getString("kd_barang"),rs.getString("kategori"),rs.getString("nm_barang"),Integer.toString(rs.getInt("stok")),rs.getString("harga")};
                model.addRow(row);//menambah row
                no++;//penomoran increment
            }//perulangan jika resultset masih ada maka menambahkan data di tabel barang ke java tabel
            barang.setModel(model);//membuat tabel
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "Tampil data tidak berhasil "+e.getMessage()); 
        }//ketika error terdapat maka muncul pop up
    }
    
    public void insert(){
        String kode = kodex.getText();//variabel untuk mengambil text di field kode
        String nama = namax.getText();//variabel untuk mengambil text di field nama
        String kategori = kategorix.getSelectedItem().toString();
        String stok = stokx.getText();//variabel untuk mengambil text di field stok
        String harga = hargax.getText();//variabel untuk mengambil text di field harga
        KoneksiDatabase classKoneksi = new KoneksiDatabase();//mengambil method KoneksiDatabase
        try{
            conn = classKoneksi.getKoneksi();//melakukan method koneksi
            stat = conn.createStatement();//membuat statement
            stat.execute("INSERT INTO barang VALUES('"+kode+"','"+kategori+"','"+nama+"','"+stok+"','"+harga+"')");//query untuk insert data barang berdasarkan data yang diinput
            JOptionPane.showMessageDialog(null, "Tambah Data Berhasil");//pop up ketika tambah data berhasil
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Gagal Menambah Data"+e.getMessage());//pop up ketika gagal menambah data
        }
    }
    
    public void search(){
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
                model.addRow(coba);
                }
            }
        }
        
        for(int i =0 ; i<jumlahrow;i++){
            model.removeRow(0);
        }
        barang.setModel(model);
    }
    
    public void delete(){
        String kode = kodex.getText();//variabel untuk mengambil text di field kode, hanya kode karena cukup menghapus sesuai primary key
        try{
            stat.executeUpdate("DELETE FROM barang WHERE kd_barang='"+kode+"'");//query untuk menghapus baris di database
            JOptionPane.showMessageDialog(null, "Menghapus Data Berhasil");//pop up ketika berhasil
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Gagal Menghapus Data"+e.getMessage());//pop up ketika gagal
        }
        reset();//menjalankan reset untuk membersihkan field input
        tampil();//menjalankan tampil untuk mengupdate data yang tertampil
    }
    
    public void update(){
        String kode = kodex.getText();//variabel untuk mengambil text di field kode
        String nama = namax.getText();//variabel untuk mengambil text di field nama
        String kategori = kategorix.getSelectedItem().toString();
        String stok = stokx.getText();//variabel untuk mengambil text di field stok
        String harga = hargax.getText();//variabel untuk mengambil text di field harga
        try{
            stat.execute("UPDATE barang SET nm_barang='"+nama+"',kategori='"+kategori+"',stok='"+stok+"',harga='"+harga+"' WHERE kd_barang='"+kode+"'");//query  untuk mengupdate data, kondisi yang diperlukan yaitu kecocokan primary key
            JOptionPane.showMessageDialog(null, "Ubah Data Berhasil");//pop up ketika berhasil
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Gagal Ubah Data"+e.getMessage());//pop up ketika gagl
        }
        tambahkanx.setEnabled(true);//membuat tombol tambahkan bisa dipencet
        reset();//menjalankan reset untuk membersihkan field input
        tampil();//menjalankan tampil untuk mengupdate data yang tertampil
    }
    
    public void reset(){
        kodex.setText("");//mereset field kode
        namax.setText("");//mereset field nama
        stokx.setText("");//mereset field stok
        hargax.setText("");//mereset field harga
        kodex.setEditable(true);//membuat field kode bisa diedit
        tambahkanx.setEnabled(true);//membuat tombol tambahkan menjadi bisa dipencet
    }
    
    public void click(){
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
        stokx.setText(stok);//mengisi field stok dengan data harga yang sudah didapatkan
        hargax.setText(harga);//mengisi field harga dengan data harga yang sudah didapatkan
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        barang = new javax.swing.JTable();
        stokx = new javax.swing.JTextField();
        kodex = new javax.swing.JTextField();
        namax = new javax.swing.JTextField();
        hargax = new javax.swing.JTextField();
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
        menux = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("APLIKASI TOKO ELEKTRONIK");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(103, 0, 844, 83));

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 118, 568, 470));
        getContentPane().add(stokx, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 240, 233, 39));
        getContentPane().add(kodex, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 118, 233, 39));
        getContentPane().add(namax, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 233, 39));
        getContentPane().add(hargax, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 320, 233, 39));

        jLabel2.setText("Harga");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 96, 39));

        jLabel3.setText("Kode Barang");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 118, 96, 39));

        jLabel4.setText("Nama Barang");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 96, 39));

        jLabel5.setText("Stok");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 96, 39));

        tambahkanx.setText("Tambahkan");
        tambahkanx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahkanxActionPerformed(evt);
            }
        });
        getContentPane().add(tambahkanx, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 390, -1, -1));

        resetx.setText("RESET");
        resetx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetxActionPerformed(evt);
            }
        });
        getContentPane().add(resetx, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, -1, -1));

        perbaruix.setText("Perbarui");
        perbaruix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                perbaruixActionPerformed(evt);
            }
        });
        getContentPane().add(perbaruix, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 390, -1, -1));

        hapusx.setText("Hapus");
        hapusx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusxActionPerformed(evt);
            }
        });
        getContentPane().add(hapusx, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 390, -1, -1));

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
        getContentPane().add(carix, new org.netbeans.lib.awtextra.AbsoluteConstraints(648, 90, 281, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("PENCARIAN NAMA BARANG");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 90, 188, -1));

        searchx.setText("CARI");
        searchx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchxActionPerformed(evt);
            }
        });
        getContentPane().add(searchx, new org.netbeans.lib.awtextra.AbsoluteConstraints(935, 89, 89, -1));

        menux.setText("MENU");
        menux.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuxActionPerformed(evt);
            }
        });
        getContentPane().add(menux, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 10, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tambahkanxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahkanxActionPerformed
        insert();//ketika tombol tambahkan dipencet maka dijalankan method insert
        tampil();//ketika tombol tambahkan dipencet maka dijalankan method tampil
    }//GEN-LAST:event_tambahkanxActionPerformed

    private void resetxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetxActionPerformed
        reset();//ketika tombol reset dipencet maka dijalankan method reset
    }//GEN-LAST:event_resetxActionPerformed

    private void barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barangMouseClicked
        click();
    }//GEN-LAST:event_barangMouseClicked

    private void hapusxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusxActionPerformed
        delete();//ketika tombol hapus dipencet maka menjalankan method delete
    }//GEN-LAST:event_hapusxActionPerformed

    private void perbaruixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_perbaruixActionPerformed
        update();//ketika tombol perbarui dipencet maka menjalankan method update
    }//GEN-LAST:event_perbaruixActionPerformed

    private void carixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carixActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_carixActionPerformed

    private void carixKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_carixKeyReleased
        tampil();//menjalankan method tampil
    }//GEN-LAST:event_carixKeyReleased

    private void searchxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchxActionPerformed
        search();
    }//GEN-LAST:event_searchxActionPerformed

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
            java.util.logging.Logger.getLogger(BarangAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BarangAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BarangAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BarangAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new BarangAdmin().setVisible(true);//menjalankan class Administrator
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable barang;
    private javax.swing.JTextField carix;
    private javax.swing.JButton hapusx;
    private javax.swing.JTextField hargax;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField kodex;
    private javax.swing.JButton menux;
    private javax.swing.JTextField namax;
    private javax.swing.JButton perbaruix;
    private javax.swing.JButton resetx;
    private javax.swing.JButton searchx;
    private javax.swing.JTextField stokx;
    private javax.swing.JButton tambahkanx;
    // End of variables declaration//GEN-END:variables
}
