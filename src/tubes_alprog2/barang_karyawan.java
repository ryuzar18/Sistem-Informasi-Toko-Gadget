package tubes_alprog2;//package dari class
import java.awt.*;//import library java awt
import java.sql.*;//import library java sql
import javax.swing.JOptionPane;//import library java option pane
import javax.swing.table.DefaultTableModel;//import library tabel
public class barang_karyawan extends javax.swing.JFrame implements crud{
    public Connection conn;//untuk menyingkat
    public Statement stat;//untuk menyingkat
    public ResultSet rs;//untuk menyingkat
    public DefaultTableModel model;//untuk menyingkat
    public barang_karyawan() {
        initComponents();//menginisiasi seluruh fungsi JFrame
        Dimension layar = Toolkit.getDefaultToolkit().getScreenSize();//mendapatkan dimensi layar
        int x = layar.width/2-this.getSize().width/2;//inisiasi koordinat x
        int y = layar.height/2-this.getSize().height/2;//inisiasi koordina y
        this.setLocation(x, y);//mengeset lokasi layar berdasarkan variabel x dan y
        String[] header ={"No","Kode Barang", "Kategori", "Nama Barang", "Jumlah Stok", "Harga"};//array untuk row header
        model = new DefaultTableModel(header,0);//mengeset tabel sesuai header dengan 0 baris
        barang.setModel(model);//membuat tabel
        combobox();
        tampil();//menjalankan method tampil
        
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

    public void tampil(){
        KoneksiDatabase classKoneksi = new KoneksiDatabase();//menjalankan menthod koneksi untuk koneksi ke database
        int jumlahrow = barang.getRowCount();//membuat variabel jumlah arrow, yang didapat dari tabel barang
        for (int n = 0; n < jumlahrow; n++) {
            model.removeRow(0);
        }// perulangan untuk mencegah duplikat data yang ada di tabel
        try{
            conn = classKoneksi.getKoneksi();//mengkoneksikan
            stat = conn.createStatement();//membuat statement database
            String pencarian = carix.getText();// mendapatkan data dari field input carix
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
        kodex.setText("");//mereset field kode
        namax.setText("");//mereset field nama
        stokx.setText("");//mereset field stok
        hargax.setText("");//mereset field harga
        kodex.setEditable(true);//membuat field kode bisa diedit
    }
    
    public void click(){
        int row = barang.getSelectedRow();//membuat variabel, untuk identifikasi row yang dipilih
        kodex.setEditable(false);//membuat field kode tidak bisa dipencet
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
        resetx = new javax.swing.JButton();
        carix = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        searchx = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        kategorix = new javax.swing.JComboBox<>();
        menux = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("APLIKASI TOKO ELEKTRONIK");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(103, 0, 809, 83));

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(412, 118, 568, 470));
        getContentPane().add(stokx, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 299, 233, 39));
        getContentPane().add(kodex, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 118, 233, 39));
        getContentPane().add(namax, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 234, 233, 39));
        getContentPane().add(hargax, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 375, 233, 39));

        jLabel2.setText("Harga");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 375, 96, 39));

        jLabel3.setText("Kode Barang");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 118, 96, 39));

        jLabel4.setText("Nama Barang");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 234, 96, 39));

        jLabel5.setText("Stok");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 299, 96, 39));

        resetx.setText("RESET");
        resetx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetxActionPerformed(evt);
            }
        });
        getContentPane().add(resetx, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 452, -1, -1));

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
        getContentPane().add(carix, new org.netbeans.lib.awtextra.AbsoluteConstraints(604, 90, 281, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("PENCARIAN NAMA BARANG");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(412, 90, 188, -1));

        searchx.setText("CARI");
        searchx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchxActionPerformed(evt);
            }
        });
        getContentPane().add(searchx, new org.netbeans.lib.awtextra.AbsoluteConstraints(891, 89, 89, -1));

        jLabel7.setText("Kategori");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 175, 96, 41));

        getContentPane().add(kategorix, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 175, 233, 41));

        menux.setText("MENU");
        menux.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuxActionPerformed(evt);
            }
        });
        getContentPane().add(menux, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 10, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void resetxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetxActionPerformed
        reset();//ketika tombol reset dipencet maka dijalankan method reset
    }//GEN-LAST:event_resetxActionPerformed

    private void barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barangMouseClicked
        click();
    }//GEN-LAST:event_barangMouseClicked

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
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(barang_karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(barang_karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(barang_karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(barang_karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new barang_karyawan().setVisible(true);//menjalankan class Administrator
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable barang;
    private javax.swing.JTextField carix;
    private javax.swing.JTextField hargax;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> kategorix;
    private javax.swing.JTextField kodex;
    private javax.swing.JButton menux;
    private javax.swing.JTextField namax;
    private javax.swing.JButton resetx;
    private javax.swing.JButton searchx;
    private javax.swing.JTextField stokx;
    // End of variables declaration//GEN-END:variables

    @Override
    public void delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
