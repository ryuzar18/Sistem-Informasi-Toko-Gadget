package si_toko.FIX;//package dari class
import java.awt.*;//import library java awt
import java.sql.*;//import library java sql
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;//import library java option pane
import javax.swing.table.DefaultTableModel;//import library tabel
import si_toko.FIX.CRUD;
public class Kasir extends javax.swing.JFrame{
    public Connection conn;//untuk menyingkat
    public Statement stat;//untuk menyingkat
    public ResultSet rs;//untuk menyingkat
    public DefaultTableModel model;//untuk menyingkat
    public DefaultTableModel modelKasir;
    public Kasir() {
        initComponents();//menginisiasi seluruh fungsi JFrame
        Dimension layar = Toolkit.getDefaultToolkit().getScreenSize();//mendapatkan dimensi layar
        int x = layar.width/2-this.getSize().width/2;//inisiasi koordinat x
        int y = layar.height/2-this.getSize().height/2;//inisiasi koordina y
        this.setLocation(x, y);//mengeset lokasi layar berdasarkan variabel x dan y
        setBarang();
        setSementara();
        kodeTransaksi();
        tampilKasir();
        tampil();//menjalankan method tampil
        
    }
    
    public void setBarang(){
        String[] header ={"No","Kode Barang", "Kategori", "Nama Barang", "Jumlah Stok", "Harga"};//array untuk row header
        model = new DefaultTableModel(header,0);//mengeset tabel sesuai header dengan 0 baris
        barang.setModel(model);//membuat tabel
    }
    
    public void setSementara(){
        String[] header ={"No","Nama Barang","Harga Satuan","Jumlah", "Harga Total"};//array untuk row header
        modelKasir = new DefaultTableModel(header,0);//mengeset tabel sesuai header dengan 0 baris
        kasir.setModel(modelKasir);//membuat tabel
    }
    
    public void kodeTransaksi(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
        String kode = dtf.format(now).toString();
        transaksix.setText(kode);
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
    
    public void tampilKasir(){
        KoneksiDatabase classKoneksi = new KoneksiDatabase();//menjalankan menthod koneksi untuk koneksi ke database
        int jumlahrow = kasir.getRowCount();//membuat variabel jumlah arrow, yang didapat dari tabel barang
        for (int n = 0; n < jumlahrow; n++) {
            modelKasir.removeRow(0);
        }// perulangan untuk mencegah duplikat data yang ada di tabel
        try{
            conn = classKoneksi.getKoneksi();//mengkoneksikan
            stat = conn.createStatement();//membuat statement database
            rs = stat.executeQuery("SELECT * FROM sementara");//membuat variabel resulset berisi eksekusi query yang menampilkan tabel berdasarkan kondisi nama barang yang sesuai
            int no = 1;
            while(rs.next()){
                String[] row = {Integer.toString(no),rs.getString("nama_barang"),rs.getString("harga"),rs.getString("jumlah"),rs.getString("total_harga")};
                modelKasir.addRow(row);//menambah row
                no++;
            }//perulangan jika resultset masih ada maka menambahkan data di tabel barang ke java tabel
            kasir.setModel(modelKasir);//membuat tabel
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "Tampil data tidak berhasil "+e.getMessage()); 
        }//ketika error terdapat maka muncul pop up
        total();
    }
    
    public void insert(){
        int row = barang.getSelectedRow();
        String kode = transaksix.getText();//variabel untuk mengambil text di field kode
        String nama = barang.getValueAt(row, 3).toString();//variabel untuk mengambil text di field nama
        String jumlah = jumlahx.getText();//variabel untuk mengambil text di field stok
        String harga = barang.getValueAt(row, 5).toString();//variabel untuk mengambil text di field harga
        String subtotal = subtotalx.getText();
        KoneksiDatabase classKoneksi = new KoneksiDatabase();//mengambil method KoneksiDatabase
        try{
            conn = classKoneksi.getKoneksi();//melakukan method koneksi
            stat = conn.createStatement();//membuat statement
            stat.execute("INSERT INTO sementara VALUES('"+nama+"','"+harga+"','"+jumlah+"','"+subtotal+"')");//query untuk insert data barang berdasarkan data yang diinput
            JOptionPane.showMessageDialog(null, "Tambah Data Berhasil");//pop up ketika tambah data berhasil
            tampilKasir();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Gagal Menambah Data"+e.getMessage());//pop up ketika gagal menambah data
        }
    }
    
    public void proses(){
        String kode = transaksix.getText();//variabel untuk mengambil text di field kode
        String total = totalx.getText();//variabel untuk mengambil text di field stok
        LocalDate currentDate = LocalDate.now();
        Month m = currentDate.getMonth();
        KoneksiDatabase classKoneksi = new KoneksiDatabase();//mengambil method KoneksiDatabase
        try{
            conn = classKoneksi.getKoneksi();//melakukan method koneksi
            stat = conn.createStatement();//membuat statement
            stat.execute("INSERT INTO transaksi (kode_transaksi, bulan, jumlah) VALUES('"+kode+"','"+m+"','"+total+"')");//query untuk insert data barang berdasarkan data yang diinput
            JOptionPane.showMessageDialog(null, "Proses Data Berhasil");//pop up ketika tambah data berhasil
            tampilKasir();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Gagal Proses Data"+e.getMessage());//pop up ketika gagal menambah data
        }
        delete();
    }
    
    public void delete(){
        KoneksiDatabase classKoneksi = new KoneksiDatabase();
        try{
            conn = classKoneksi.getKoneksi();//melakukan method koneksi
            stat = conn.createStatement();//membuat statement
            stat.execute("TRUNCATE TABLE sementara");//query untuk insert data barang berdasarkan data yang diinput
            JOptionPane.showMessageDialog(null, "Hapus Data Berhasil");//pop up ketika tambah data berhasil
            tampilKasir();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Gagal Menghapus Data"+e.getMessage());//pop up ketika gagal menambah data
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
       
    public void reset(){
        transaksix.setText("");//mereset field kode
        namax.setText("");//mereset field nama
        jumlahx.setText("");//mereset field stok
        hargax.setText("");//mereset field harga
    }
    
    public void click(){
        int row = barang.getSelectedRow();//membuat variabel, untuk identifikasi row yang dipilih
        transaksix.setEditable(false);//membuat field kode tidak bisa dipencet
        String nama = barang.getValueAt(row, 3).toString();//mendapatkan data nama pada tabel di row yang sudah dipilih
        DecimalFormat df = new DecimalFormat("#");
        int harga = Integer.parseInt(barang.getValueAt(row, 5).toString());
        String hargat = df.format(harga);
        namax.setText(nama);//mengisi field nama dengan data harga yang sudah didapatkan
        hargax.setText(hargat);
        
    }
    
    public void subtotal(){
        int row = barang.getSelectedRow();
        DecimalFormat df = new DecimalFormat("#");
        int harga = Integer.parseInt(barang.getValueAt(row, 5).toString());
        String hargat = df.format(harga);
        int total = harga*Integer.parseInt(jumlahx.getText());
        String totals = df.format(total);
        subtotalx.setText(totals);
    }
    
    public void total(){
        int total = 0;
        for (int i =0; i<kasir.getRowCount();i++){
            String amount = (kasir.getValueAt(i, 4)).toString();
            int amounts = Integer.parseInt(amount);
            total += amounts;
        }
        DecimalFormat df = new DecimalFormat("");
        String totals = df.format(total);
        totalx.setText(totals);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        barang = new javax.swing.JTable();
        jumlahx = new javax.swing.JTextField();
        namax = new javax.swing.JTextField();
        hargax = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        totalx = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        prosesx = new javax.swing.JButton();
        resetx = new javax.swing.JButton();
        carix = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        searchx = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        kasir = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tambahkanx = new javax.swing.JButton();
        transaksix = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        subtotalx = new javax.swing.JTextField();
        hapusx = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 700, 280));
        getContentPane().add(jumlahx, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 220, 233, 39));
        getContentPane().add(namax, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 100, 233, 39));
        getContentPane().add(hargax, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 160, 233, 39));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Harga");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 160, 96, 39));

        totalx.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 2, 48)); // NOI18N
        getContentPane().add(totalx, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 430, 520, 40));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Nama Barang");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 100, 96, 39));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Sub Total");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 280, 96, 39));

        prosesx.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        prosesx.setText("PROSES");
        prosesx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prosesxActionPerformed(evt);
            }
        });
        getContentPane().add(prosesx, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 580, -1, 30));

        resetx.setText("RESET");
        resetx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetxActionPerformed(evt);
            }
        });
        getContentPane().add(resetx, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 100, -1, -1));

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
        getContentPane().add(carix, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 281, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("PENCARIAN NAMA BARANG");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 188, -1));

        searchx.setText("CARI");
        searchx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchxActionPerformed(evt);
            }
        });
        getContentPane().add(searchx, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 89, -1));

        kasir.setModel(new javax.swing.table.DefaultTableModel(
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
        kasir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kasirMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(kasir);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 700, 300));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 1260, 10));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Kode Transaksi");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 50, 120, 39));

        jLabel9.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 2, 48)); // NOI18N
        jLabel9.setText("TOTAL BELI");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 370, 210, 40));

        tambahkanx.setText("Tambahkan");
        tambahkanx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahkanxActionPerformed(evt);
            }
        });
        getContentPane().add(tambahkanx, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 50, -1, -1));

        transaksix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transaksixActionPerformed(evt);
            }
        });
        getContentPane().add(transaksix, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 50, 230, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Jumlah");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 220, 96, 39));

        subtotalx.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                subtotalxMouseClicked(evt);
            }
        });
        subtotalx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subtotalxActionPerformed(evt);
            }
        });
        getContentPane().add(subtotalx, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 280, 230, 40));

        hapusx.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        hapusx.setText("HAPUS");
        hapusx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusxActionPerformed(evt);
            }
        });
        getContentPane().add(hapusx, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 580, -1, 30));

        jButton1.setText("LOGOUT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void prosesxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prosesxActionPerformed
        proses();
    }//GEN-LAST:event_prosesxActionPerformed

    private void resetxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetxActionPerformed
        reset();//ketika tombol reset dipencet maka dijalankan method reset
    }//GEN-LAST:event_resetxActionPerformed

    private void barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barangMouseClicked
        click();
    }//GEN-LAST:event_barangMouseClicked

    private void carixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carixActionPerformed
        search();
    }//GEN-LAST:event_carixActionPerformed

    private void carixKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_carixKeyReleased
        tampil();//menjalankan method tampil
    }//GEN-LAST:event_carixKeyReleased

    private void searchxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchxActionPerformed
        
    }//GEN-LAST:event_searchxActionPerformed

    private void kasirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kasirMouseClicked
        
    }//GEN-LAST:event_kasirMouseClicked

    private void tambahkanxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahkanxActionPerformed
        insert();//ketika tombol tambahkan dipencet maka dijalankan method insert
        tampil();//ketika tombol tambahkan dipencet maka dijalankan method tampil
    }//GEN-LAST:event_tambahkanxActionPerformed

    private void transaksixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transaksixActionPerformed

    }//GEN-LAST:event_transaksixActionPerformed

    private void hapusxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusxActionPerformed
        delete();
    }//GEN-LAST:event_hapusxActionPerformed

    private void subtotalxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subtotalxActionPerformed

    }//GEN-LAST:event_subtotalxActionPerformed

    private void subtotalxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subtotalxMouseClicked
        subtotal();
    }//GEN-LAST:event_subtotalxMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new LOGIN().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new Kasir().setVisible(true);//menjalankan class Administrator
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable barang;
    private javax.swing.JTextField carix;
    private javax.swing.JButton hapusx;
    private javax.swing.JTextField hargax;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jumlahx;
    private javax.swing.JTable kasir;
    private javax.swing.JTextField namax;
    private javax.swing.JButton prosesx;
    private javax.swing.JButton resetx;
    private javax.swing.JButton searchx;
    private javax.swing.JTextField subtotalx;
    private javax.swing.JButton tambahkanx;
    private javax.swing.JLabel totalx;
    private javax.swing.JTextField transaksix;
    // End of variables declaration//GEN-END:variables

}
