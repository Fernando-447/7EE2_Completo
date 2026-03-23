/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista.Herramientas;
// Paquete actual

// DAO, modelo y controlador
import conexion.ConexionDB;
import controlador.AltaNoConsumibleControlador;
import dao.AltaNoConsumibleDAO;
import dao.TipoHerramientaNoConsumibleDAO;
import modelo.TipoNoConsumibleModelo;
import modelo.HerramientaNoConsumibleModelo;


// Java estándar
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import controlador.AltaNoConsumibleControlador;
import java.awt.Component;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author carde
 */
public class vistaAltaHerramientaNoConsumible extends javax.swing.JFrame {

    //private JComboBox<TipoNoConsumibleModelo> cmbNoConsumibles = new JComboBox<>(); 
    
    public vistaAltaHerramientaNoConsumible() {
        setUndecorated(true);

        initComponents();
        
        setLocationRelativeTo(null);
        
        
        AltaNoConsumibleControlador alta = new AltaNoConsumibleControlador(this);
        alta.cargarTipos();
       
    }
    
    private void configurarComboBox(){
       cmbNoConsumibles.setEditable(true);
       
       cmbNoConsumibles.setRenderer(new DefaultListCellRenderer(){
           @Override 
           public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
               
               super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
               
               if(value instanceof TipoNoConsumibleModelo){
                   //value = ((TipoNoConsumibleModelo) value).getNomTip();
                   setText(((TipoNoConsumibleModelo)value).getNomTip());
               }
               return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
           }
       });
       
    }
    
    public void seleccionarTipoEnComboBox(TipoNoConsumibleModelo tipo) {
        DefaultComboBoxModel<TipoNoConsumibleModelo> model = 
                (DefaultComboBoxModel<TipoNoConsumibleModelo>) cmbNoConsumibles.getModel();
        for(int i = 0; i < model.getSize(); i++) {
            if(model.getElementAt(i).getIdTipNoCon() == tipo.getIdTipNoCon()) {
                cmbNoConsumibles.setSelectedIndex(i);
                break;
            }
        }
    }
    
    public JComboBox<TipoNoConsumibleModelo> getCmbNoConsumibles(){
        return cmbNoConsumibles;
    }
    
    public Object getTipoConsumibleSeleccionado(){
        
        if(cmbNoConsumibles.isEditable()){
            return cmbNoConsumibles.getEditor().getItem();
        }
        
        return cmbNoConsumibles.getSelectedItem();
       
    }
    
    public TipoNoConsumibleModelo getTipoNoConsumibleSeleccionado(){
        return(TipoNoConsumibleModelo) cmbNoConsumibles.getSelectedItem();
    }
    
    public String getIdUTNG(){
        return txtIdUTNG.getText().trim();
    }
    
    public String getIdEstatal(){
        return txtIdEstatal.getText().trim();
    }
    
    public String getDescripcion(){
        return txtDescripcion.getText().trim();
    }
    
    public String getNumActivo(){
        return txtNumActivo.getText().trim();
    }
    
    public String getNumSerie(){
        return txtNumSerie.getText().trim();
    }
    
    public String getMarca(){
        return txtMarca.getText().trim();
    }
    
    public String getModelo(){
        return txtModelo.getText().trim();
    }
    
    public double getCosto(){
        try{
            return Double.parseDouble(txtCosto.getText().trim());
        } catch (NumberFormatException e){
            return 0.0;
        }
    }
    
    public HerramientaNoConsumibleModelo getDatosIngresados(){
        HerramientaNoConsumibleModelo herramienta = new HerramientaNoConsumibleModelo();
        
        herramienta.setIdUTNG(getIdUTNG());
        herramienta.setIdEst(getIdEstatal());
        herramienta.setDesMatNoCon(getDescripcion());
        herramienta.setNumAct(getNumActivo());
        herramienta.setNumSer(getNumSerie());
        herramienta.setMarca(getMarca());
        herramienta.setModelo(getModelo());
        herramienta.setCosto(getCosto());
        
        return herramienta;
    }
    
    public String getTextoComboBoxTipo(){
        Object seleccion = cmbNoConsumibles.getSelectedItem();
        return seleccion != null ? seleccion.toString().trim() : "";
    }
   
    //Limpiar campos
    public void limpiarCampos(){
        cmbNoConsumibles.setSelectedIndex(0);
        //cmbEstado.setSelectedIndex(0);
        txtIdUTNG.setText("");
        txtIdEstatal.setText("");
        txtDescripcion.setText("");
        txtNumActivo.setText("");
        txtNumSerie.setText("");
        txtModelo.setText("");
        txtMarca.setText("");
        txtCosto.setText("");
    }
    
    public JButton getBtnAgregar(){ return btnAgregar;}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblNombreHerramienta = new javax.swing.JLabel();
        cmbNoConsumibles = new javax.swing.JComboBox<>();
        lblIdUTNG = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtIdUTNG = new javax.swing.JTextPane();
        lblIdEstatal = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtIdEstatal = new javax.swing.JTextPane();
        lblIdUTNG1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        lblNumActivo1 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtCosto = new javax.swing.JTextPane();
        lblMarca = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtNumSerie = new javax.swing.JTextPane();
        lblModelo = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtMarca = new javax.swing.JTextPane();
        lblModelo1 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtModelo = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtNumActivo = new javax.swing.JTextPane();
        lblNumActivo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAgregar.setBackground(new java.awt.Color(51, 153, 0));
        btnAgregar.setFont(new java.awt.Font("Roboto Condensed SemiBold", 1, 12)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("AGREGAR");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 400, 120, 30));

        jPanel3.setBackground(new java.awt.Color(0, 0, 51));

        jButton3.setBackground(new java.awt.Color(0, 0, 51));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zImagenes/regresar.png"))); // NOI18N
        jButton3.setBorder(null);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto Condensed Black", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("AGREGAR HERRAMIENTA NO CONSUMIBLE");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButton3)
                .addGap(134, 134, 134)
                .addComponent(jLabel1)
                .addContainerGap(160, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addComponent(jLabel1))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 80));

        lblNombreHerramienta.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblNombreHerramienta.setText("NOMBRE");

        cmbNoConsumibles.setEditable(true);
        cmbNoConsumibles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNoConsumiblesActionPerformed(evt);
            }
        });

        lblIdUTNG.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblIdUTNG.setText("ID UTNG");

        jScrollPane1.setViewportView(txtIdUTNG);

        lblIdEstatal.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblIdEstatal.setText("ID ESTATAL");

        jScrollPane2.setViewportView(txtIdEstatal);

        lblIdUTNG1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblIdUTNG1.setText("DESCRIPCIÓN");

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane3.setViewportView(txtDescripcion);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(lblIdUTNG))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIdEstatal, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblNombreHerramienta, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbNoConsumibles, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(382, 382, 382))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIdUTNG1)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbNoConsumibles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombreHerramienta))
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIdUTNG)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIdEstatal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIdUTNG1))
                .addGap(26, 26, 26))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 400, 280));

        lblNumActivo1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblNumActivo1.setText("NUM. SERIE");

        jScrollPane5.setViewportView(txtCosto);

        lblMarca.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblMarca.setText("MARCA");

        jScrollPane6.setViewportView(txtNumSerie);

        lblModelo.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblModelo.setText("MODELO");

        jScrollPane7.setViewportView(txtMarca);

        lblModelo1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblModelo1.setText("COSTO");

        jScrollPane8.setViewportView(txtModelo);

        jScrollPane4.setViewportView(txtNumActivo);

        lblNumActivo.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblNumActivo.setText("NUM. ACTIVO");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblModelo)
                            .addComponent(lblModelo1))
                        .addGap(54, 54, 54)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addComponent(jScrollPane5)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblNumActivo1)
                            .addComponent(lblNumActivo)
                            .addComponent(lblMarca))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane7))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumActivo))
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNumActivo1)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMarca))
                .addGap(33, 33, 33)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblModelo)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblModelo1)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 100, 430, 280));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 866, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed

        
        HerramientaNoConsumibleModelo h = new HerramientaNoConsumibleModelo();
        String idUTNG = this.getIdUTNG().trim();
        if (idUTNG.isEmpty()){
            JOptionPane.showMessageDialog(this, "El ID UTNG es obligatorio","Error", JOptionPane.ERROR_MESSAGE);
        }
        AltaNoConsumibleControlador alta = new AltaNoConsumibleControlador(this);
        try {
            alta.registrarHerramienta(h);
        } catch (SQLException ex) {
            Logger.getLogger(vistaAltaHerramientaNoConsumible.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        limpiarCampos();
        
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void cmbNoConsumiblesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNoConsumiblesActionPerformed
        // TODO add your handling code here:
        //cmbNoConsumibles.disable(true);
    }//GEN-LAST:event_cmbNoConsumiblesActionPerformed

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
            java.util.logging.Logger.getLogger(vistaAltaHerramientaNoConsumible.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vistaAltaHerramientaNoConsumible.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vistaAltaHerramientaNoConsumible.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vistaAltaHerramientaNoConsumible.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new vistaAltaHerramientaNoConsumible().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JComboBox<TipoNoConsumibleModelo> cmbNoConsumibles;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lblIdEstatal;
    private javax.swing.JLabel lblIdUTNG;
    private javax.swing.JLabel lblIdUTNG1;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblModelo1;
    private javax.swing.JLabel lblNombreHerramienta;
    private javax.swing.JLabel lblNumActivo;
    private javax.swing.JLabel lblNumActivo1;
    private javax.swing.JTextPane txtCosto;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextPane txtIdEstatal;
    private javax.swing.JTextPane txtIdUTNG;
    private javax.swing.JTextPane txtMarca;
    private javax.swing.JTextPane txtModelo;
    private javax.swing.JTextPane txtNumActivo;
    private javax.swing.JTextPane txtNumSerie;
    // End of variables declaration//GEN-END:variables


    public void mostrarError(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setCmbNoConsumibles(DefaultComboBoxModel<TipoNoConsumibleModelo> modelo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String txtIdUTNG() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String txtIdEstatal() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String txtCosto() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String cmbNoConsumibles() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
