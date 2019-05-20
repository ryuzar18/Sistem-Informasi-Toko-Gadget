/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si_toko.FIX;

import si_toko.FIX.LOGIN;
import java.awt.*;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author REY
 */
public class Karyawan_Dashboard extends javax.swing.JFrame {
    public Connection conn;//untuk menyingkat
    public Statement stat;//untuk menyingkat
    public ResultSet rs;//untuk menyingkat
    public DefaultTableModel modelJadwal;//untuk menyingkat
    public DefaultTableModel modelBarang;
    
    public Karyawan_Dashboard() {
        initComponents();
        Dimension layar = Toolkit.getDefaultToolkit().getScreenSize();//mendapatkan dimensi layar
        int x = layar.width/2-this.getSize().width/2;//inisiasi koordinat x
        int y = layar.height/2-this.getSize().height/2;//inisiasi koordina y
        this.setLocation(x, y);
        mainJadwal();
        mainBarang();
    }
    
    public void setPanel(JPanel isi){
        PanelIsi.removeAll();
        PanelIsi.repaint();
        PanelIsi.revalidate();
        PanelIsi.add(isi);
        PanelIsi.repaint();
        PanelIsi.revalidate();
    }

    // JADWAL KODE---------------------------------------------------------------------------
    public void mainJadwal(){
        String[] header ={"No","Hari", "Sesi", "Nama Karyawan"};//array untuk row header
        modelJadwal = new DefaultTableModel(header,0);//mengeset tabel sesuai header dengan 0 baris
        jadwalx.setModel(modelJadwal);
        tampilJadwal();
    }
    
    public void tampilJadwal(){
        KoneksiDatabase classKoneksi = new KoneksiDatabase();//menjalankan menthod koneksi untuk koneksi ke database
        int jumlahrow = jadwalx.getRowCount();//membuat variabel jumlah arrow, yang didapat dari tabel barang
        for (int n = 0; n < jumlahrow; n++) {
            modelJadwal.removeRow(0);
        }// perulangan untuk mencegah duplikat data yang ada di tabel
        try{
            conn = classKoneksi.getKoneksi();//mengkoneksikan
            stat = conn.createStatement();//membuat statement database
            rs = stat.executeQuery("SELECT * FROM jadwal ORDER BY no_jadwal ASC");//membuat variabel resulset berisi eksekusi query yang menampilkan tabel berdasarkan kondisi nama barang yang sesuai
            int no = 1;//variabel penomoran
            while(rs.next()){
                String[] row = {Integer.toString(no),rs.getString("hari"),rs.getString("sesi"),rs.getString("nama_karyawan")};
                modelJadwal.addRow(row);//menambah row
                no++;//penomoran increment
            }//perulangan jika resultset masih ada maka menambahkan data di tabel barang ke java tabel
            jadwalx.setModel(modelJadwal);//membuat tabel
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "Tampil data tidak berhasil "+e.getMessage()); 
        }//ketika error terdapat maka muncul pop up
    }
    // END JADWAL KODE-----------------------------------------------------------------------
    
    // JADWAL KODE---------------------------------------------------------------------------
    public void mainBarang(){
        String[] header ={"No","Kode Barang", "Kategori", "Nama Barang", "Jumlah Stok", "Harga"};//array untuk row header
        modelBarang = new DefaultTableModel(header,0);//mengeset tabel sesuai header dengan 0 baris
        barang.setModel(modelBarang);//membuat tabel
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
            modelBarang.removeRow(0);
        }// perulangan untuk mencegah duplikat data yang ada di tabel
        try{
            conn = classKoneksi.getKoneksi();//mengkoneksikan
            stat = conn.createStatement();//membuat statement database
            String pencarian = carix.getText();// mendapatkan data dari field input carix
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
                modelBarang.addRow(coba);
                }
            }
        }
        
        for(int i =0 ; i<jumlahrow;i++){
            modelBarang.removeRow(0);
        }
        barang.setModel(modelBarang);
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
    
    // END BARANG KODE-----------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PanelMenu = new javax.swing.JPanel();
        BARANG = new javax.swing.JButton();
        JADWAL = new javax.swing.JButton();
        PanelIsi = new javax.swing.JPanel();
        PanelBarang = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        barang = new javax.swing.JTable();
        kodex = new javax.swing.JTextField();
        namax = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        resetx1 = new javax.swing.JButton();
        carix = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        kategorix = new javax.swing.JComboBox<>();
        hargax = new javax.swing.JFormattedTextField();
        stokx = new javax.swing.JFormattedTextField();
        searchx = new javax.swing.JButton();
        PanelJadwal = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jadwalx = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelMenu.setBackground(new java.awt.Color(153, 255, 153));
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

        javax.swing.GroupLayout PanelMenuLayout = new javax.swing.GroupLayout(PanelMenu);
        PanelMenu.setLayout(PanelMenuLayout);
        PanelMenuLayout.setHorizontalGroup(
            PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMenuLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JADWAL, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BARANG, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        PanelMenuLayout.setVerticalGroup(
            PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMenuLayout.createSequentialGroup()
                .addGap(182, 182, 182)
                .addComponent(BARANG)
                .addGap(68, 68, 68)
                .addComponent(JADWAL)
                .addContainerGap(220, Short.MAX_VALUE))
        );

        jPanel1.add(PanelMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 520));

        PanelIsi.setBackground(new java.awt.Color(153, 255, 153));
        PanelIsi.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
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
        jScrollPane3.setViewportView(barang);

        jLabel10.setText("Harga");

        jLabel11.setText("Kode Barang");

        jLabel12.setText("Nama Barang");

        jLabel13.setText("Stok");

        resetx1.setText("RESET");
        resetx1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetx1ActionPerformed(evt);
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

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("PENCARIAN NAMA BARANG");

        jLabel15.setText("Kategori");

        hargax.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        hargax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hargaxActionPerformed(evt);
            }
        });

        stokx.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        searchx.setText("CARI");
        searchx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelBarangLayout = new javax.swing.GroupLayout(PanelBarang);
        PanelBarang.setLayout(PanelBarangLayout);
        PanelBarangLayout.setHorizontalGroup(
            PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBarangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelBarangLayout.createSequentialGroup()
                        .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(stokx, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hargax, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(namax, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kategorix, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kodex, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelBarangLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(resetx1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118))
            .addGroup(PanelBarangLayout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(carix, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(searchx, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelBarangLayout.setVerticalGroup(
            PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBarangLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(carix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchx))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBarangLayout.createSequentialGroup()
                        .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kodex, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kategorix, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(namax, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(stokx, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(PanelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hargax, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addComponent(resetx1)
                        .addGap(114, 114, 114))))
        );

        PanelIsi.add(PanelBarang, "card3");

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
        jScrollPane1.setViewportView(jadwalx);

        jLabel1.setFont(new java.awt.Font("Perpetua Titling MT", 0, 24)); // NOI18N
        jLabel1.setText("JADWAL KARYAWAN");

        javax.swing.GroupLayout PanelJadwalLayout = new javax.swing.GroupLayout(PanelJadwal);
        PanelJadwal.setLayout(PanelJadwalLayout);
        PanelJadwalLayout.setHorizontalGroup(
            PanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelJadwalLayout.createSequentialGroup()
                .addGap(286, 286, 286)
                .addComponent(jLabel1)
                .addContainerGap(298, Short.MAX_VALUE))
            .addGroup(PanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelJadwalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1)
                    .addContainerGap()))
        );
        PanelJadwalLayout.setVerticalGroup(
            PanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelJadwalLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(427, Short.MAX_VALUE))
            .addGroup(PanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelJadwalLayout.createSequentialGroup()
                    .addContainerGap(104, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        PanelIsi.add(PanelJadwal, "card2");

        jPanel1.add(PanelIsi, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 840, 520));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barangMouseClicked
        click();
    }//GEN-LAST:event_barangMouseClicked

    private void resetx1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetx1ActionPerformed
        reset();//ketika tombol reset dipencet maka dijalankan method reset
    }//GEN-LAST:event_resetx1ActionPerformed

    private void carixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carixActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_carixActionPerformed

    private void carixKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_carixKeyReleased
        tampil();//menjalankan method tampil
    }//GEN-LAST:event_carixKeyReleased

    private void searchxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchxActionPerformed
        search();
    }//GEN-LAST:event_searchxActionPerformed

    private void BARANGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BARANGActionPerformed
        setPanel(PanelBarang);
    }//GEN-LAST:event_BARANGActionPerformed

    private void JADWALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JADWALActionPerformed
        setPanel(PanelJadwal);
    }//GEN-LAST:event_JADWALActionPerformed

    private void hargaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hargaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hargaxActionPerformed

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
            java.util.logging.Logger.getLogger(Karyawan_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Karyawan_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Karyawan_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Karyawan_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Karyawan_Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BARANG;
    private javax.swing.JButton JADWAL;
    private javax.swing.JPanel PanelBarang;
    private javax.swing.JPanel PanelIsi;
    private javax.swing.JPanel PanelJadwal;
    private javax.swing.JPanel PanelMenu;
    private javax.swing.JTable barang;
    private javax.swing.JTextField carix;
    private javax.swing.JFormattedTextField hargax;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jadwalx;
    private javax.swing.JComboBox<String> kategorix;
    private javax.swing.JTextField kodex;
    private javax.swing.JTextField namax;
    private javax.swing.JButton resetx1;
    private javax.swing.JButton searchx;
    private javax.swing.JFormattedTextField stokx;
    // End of variables declaration//GEN-END:variables
}
