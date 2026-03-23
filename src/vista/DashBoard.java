/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author nataly
 */
import controlador.AltaConsumibleControlador;
import dao.AlumnoDAO;
import dao.DocenteDAO;
import dao.TipoHerramientaConsumibleDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import modelo.TipoConsumibleModelo;
import vista.Docente.Docente;
import vista.Alumnos.Alumno;
import vista.Herramientas.HerramientasRotas;
import vista.prestamo.VistaListaPrestamos;
import vista.buscarMaterial.VistaListaMaterial;
import conexion.ConexionDB;
import controlador.AgregarPrestamoControlador;
import controlador.ControladorVistaListaAdministradores;
import controlador.ControladorVistaListaMaterial;
import controlador.ControladorVistaListaPrestamos;
import controlador.DuplicarPrestamoControlador;
import dao.DuplicarPrestamoDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import vista.administrador.VistaListaAdministradores;
import vista.prestamo.VistaDuplicarPrestamo;

public class DashBoard extends javax.swing.JFrame {

    ConexionDB conexionDB = new ConexionDB();
    java.sql.Connection conexion = conexionDB.getConexion();
    
    /**
     * Creates new form DashBoard
     */
    
    private AltaConsumibleControlador controlador;
    private List<String> numTraDoc;
    private Map<String, String> mapaDocentes = new HashMap<>();
    private Map<String, String> mapaAdmin = new HashMap<>();
    DocenteDAO daoD = new DocenteDAO();
    AlumnoDAO daoA = new AlumnoDAO();
    AgregarPrestamoControlador prestamo = new AgregarPrestamoControlador(this);
    
    DuplicarPrestamoDAO admin = new DuplicarPrestamoDAO(conexion);
    
    public DashBoard() throws SQLException {
        initComponents();
        
        DefaultTableModel model = (DefaultTableModel) tblNoConsumible.getModel();
        model.setRowCount(0);
        
        tblNoConsumible.setModel(new DefaultTableModel(
                new Object[][]{},
                new String []{"ID UTNG", "NOMBRE"}
        ));
        
        //DefaultTableModel model = (DefaultTableModel) tblConsumible.getModel();
        model.setRowCount(0);
        
        tblConsumible.setModel(new DefaultTableModel(
                new Object[][]{},
                new String []{"CLAVE", "NOMBRE"}
        ));
        setLocationRelativeTo(null);
        
        //this.setExtendedState(this.MAXIMIZED_BOTH);

        cargarDatosConsumibles();
        cargarDocentes();
        cargarAdmin();
        
        
    }

    
    private void cargarDatosConsumibles() throws SQLException{
        TipoHerramientaConsumibleDAO daoT = new TipoHerramientaConsumibleDAO();
        try {
            List<TipoConsumibleModelo> listaTipos = daoT.listar();
        
        DefaultComboBoxModel<TipoConsumibleModelo> modelo = new DefaultComboBoxModel<>();
        
        for(TipoConsumibleModelo tipo : listaTipos){
            modelo.addElement(tipo);
        }
        
        cmbConsumible.setModel(modelo);
        
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void cargarDocentes() throws SQLException{
        //DocenteDAO daoD = new DocenteDAO();
        mapaDocentes.clear();
        cmbDocente.removeAllItems();
        
        List<String> lista = daoD.obtenerDocenteNomCom();
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        for(String d : lista){
                String [] partes = d.split("-");
      
                if(partes.length >= 2){
                    String numTraDoc = partes[0];
                    String nomCom = partes[1];
                    
                    mapaDocentes.put(nomCom, numTraDoc);
                    
                    modelo.addElement(nomCom);
                }
                
        }
        
        cmbDocente.setModel(modelo);
        
    }
    
    public void cargarAdmin() throws SQLException{
        //DocenteDAO daoD = new DocenteDAO();
        mapaAdmin.clear();
        cmbAdmin.removeAllItems();
        
        List<String> lista = admin.obtenerAdminNomCom();
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        for(String d : lista){
                String [] partes = d.split("-");
      
                if(partes.length >= 2){
                    String cveAdm = partes[0];
                    String nomCom = partes[1];
                    
                    mapaAdmin.put(nomCom, cveAdm);
                    
                    modelo.addElement(nomCom);
                }
                
        }
        
        cmbAdmin.setModel(modelo);
        
    }
    
    public List<String[]> getDatosNoConsumibles(){
        List<String[]> datos = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tblNoConsumible.getModel();
        for(int i = 0; i < model.getRowCount();i++){
            String idUTNG = model.getValueAt(i, 0).toString();
            
            datos.add(new String []{idUTNG});
        }
        
        return datos;
    }
    
    public List<String[]> getDatosConsumibles(){
        List<String[]> datos = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tblConsumible.getModel();
        for(int i = 0; i < model.getRowCount();i++){
            String cveMatCon = model.getValueAt(i, 0).toString();
            
            datos.add(new String []{cveMatCon});
        }
        
        return datos;
    }
    
    public String obtenerDocenteSeleccionado(){
        String nombreSeleccionado = (String) cmbDocente.getSelectedItem();
        
        
        if(nombreSeleccionado != null && !nombreSeleccionado.isEmpty()){
                //String docenteCompleto = d.get(selectedIndex);
                return mapaDocentes.get(nombreSeleccionado);
            } else {
            
            return null;
        }
        
    }
    
    public String obtenerNumTraDoc(){
        String nombreSeleccionado = (String) cmbDocente.getSelectedItem();
        
        if(nombreSeleccionado != null && !nombreSeleccionado.isEmpty()){
            return mapaDocentes.get(nombreSeleccionado);
        } 
        
        return null;
    }
    
    public String obtenerAdminSeleccionado(){
        String nombreSeleccionado = (String) cmbAdmin.getSelectedItem();
        
        
        if(nombreSeleccionado != null && !nombreSeleccionado.isEmpty()){
                //String docenteCompleto = d.get(selectedIndex);
                return mapaAdmin.get(nombreSeleccionado);
            } else {
            
            return null;
        }
        
    }
    
    public String obtenerNumTraAdmin(){
        String nombreSeleccionado = (String) cmbAdmin.getSelectedItem();
        
        if(nombreSeleccionado != null && !nombreSeleccionado.isEmpty()){
            return mapaAdmin.get(nombreSeleccionado);
        } 
        
        return null;
    }
    public void limpiarCampos(){
        txtCvePre.setText("");
        cmbAdmin.setSelectedItem(0);
        cmbDocente.setSelectedItem(0);
        txtNumConAlu.setText("");
        txtGrupo.setText("");
        txtMat.setText("");
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnInventario = new javax.swing.JButton();
        btnPrestamos = new javax.swing.JButton();
        btnAlumnos1 = new javax.swing.JButton();
        btnDocentes = new javax.swing.JButton();
        btnImpriRotas1 = new javax.swing.JButton();
        btnAdmin = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cmbDocente = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtIdUTNG = new javax.swing.JTextPane();
        jLabel8 = new javax.swing.JLabel();
        cmbConsumible = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtCveMatCon = new javax.swing.JTextPane();
        btnAgregarConsumible = new javax.swing.JButton();
        btnAgregarPrestamo = new javax.swing.JButton();
        lblMateria = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblConsumible = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblNoConsumible = new javax.swing.JTable();
        btnAgregarNoConsumible = new javax.swing.JButton();
        jScrollBar1 = new javax.swing.JScrollBar();
        jScrollBar2 = new javax.swing.JScrollBar();
        lblDocente1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtGrupo = new javax.swing.JTextPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtMat = new javax.swing.JTextPane();
        lblGrupo2 = new javax.swing.JLabel();
        lblDocente2 = new javax.swing.JLabel();
        txtCveAlu = new javax.swing.JScrollPane();
        txtNumConAlu = new javax.swing.JTextPane();
        lblDocente3 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtCvePre = new javax.swing.JTextPane();
        cmbAdmin = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnInventario.setBackground(new java.awt.Color(0, 0, 102));
        btnInventario.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnInventario.setForeground(new java.awt.Color(255, 255, 255));
        btnInventario.setText("INVENTARIO");
        btnInventario.setBorder(null);
        btnInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioActionPerformed(evt);
            }
        });
        jPanel2.add(btnInventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 60));

        btnPrestamos.setBackground(new java.awt.Color(0, 0, 102));
        btnPrestamos.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnPrestamos.setForeground(new java.awt.Color(255, 255, 255));
        btnPrestamos.setText("PRESTAMOS");
        btnPrestamos.setBorder(null);
        btnPrestamos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrestamosActionPerformed(evt);
            }
        });
        jPanel2.add(btnPrestamos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 220, 60));

        btnAlumnos1.setBackground(new java.awt.Color(0, 0, 102));
        btnAlumnos1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnAlumnos1.setForeground(new java.awt.Color(255, 255, 255));
        btnAlumnos1.setText("ALUMNOS");
        btnAlumnos1.setBorder(null);
        btnAlumnos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlumnos1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnAlumnos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 220, 60));

        btnDocentes.setBackground(new java.awt.Color(0, 0, 102));
        btnDocentes.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnDocentes.setForeground(new java.awt.Color(255, 255, 255));
        btnDocentes.setText("DOCENTES");
        btnDocentes.setBorder(null);
        btnDocentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDocentesActionPerformed(evt);
            }
        });
        jPanel2.add(btnDocentes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 220, 60));

        btnImpriRotas1.setBackground(new java.awt.Color(0, 0, 102));
        btnImpriRotas1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnImpriRotas1.setForeground(new java.awt.Color(255, 255, 255));
        btnImpriRotas1.setText("IMPRIMIR HERRAMIENTAS ROTAS");
        btnImpriRotas1.setBorder(null);
        btnImpriRotas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImpriRotas1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnImpriRotas1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 220, 30));

        btnAdmin.setBackground(new java.awt.Color(0, 0, 102));
        btnAdmin.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnAdmin.setForeground(new java.awt.Color(255, 255, 255));
        btnAdmin.setText("ADMINISTRADORES");
        btnAdmin.setBorder(null);
        btnAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminActionPerformed(evt);
            }
        });
        jPanel2.add(btnAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 220, 60));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 220, 510));

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Roboto Black", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PRESTAMOS");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 237, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 820, 70));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zImagenes/LOGO.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 260, 50));

        jPanel3.setLayout(null);

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setText("AGREGAR CONSUMIBLE");
        jPanel3.add(jLabel4);
        jLabel4.setBounds(410, 130, 180, 19);

        cmbDocente.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jPanel3.add(cmbDocente);
        cmbDocente.setBounds(90, 50, 290, 25);

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel5.setText("ID UTNG");
        jPanel3.add(jLabel5);
        jLabel5.setBounds(10, 160, 60, 19);

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel6.setText("AGREGAR NO CONSUMIBLE");
        jPanel3.add(jLabel6);
        jLabel6.setBounds(10, 130, 200, 19);

        jScrollPane1.setViewportView(txtIdUTNG);

        jPanel3.add(jScrollPane1);
        jScrollPane1.setBounds(100, 160, 190, 22);

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel8.setText("ALUMNO");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(420, 50, 70, 19);

        jPanel3.add(cmbConsumible);
        cmbConsumible.setBounds(500, 160, 190, 22);

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel9.setText("NOMBRE");
        jPanel3.add(jLabel9);
        jLabel9.setBounds(410, 160, 70, 19);

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel10.setText("CLAVE");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(420, 190, 50, 19);

        jScrollPane3.setViewportView(txtCveMatCon);

        jPanel3.add(jScrollPane3);
        jScrollPane3.setBounds(500, 190, 190, 22);

        btnAgregarConsumible.setBackground(new java.awt.Color(51, 153, 0));
        btnAgregarConsumible.setFont(new java.awt.Font("Roboto Condensed SemiBold", 1, 12)); // NOI18N
        btnAgregarConsumible.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarConsumible.setText("AGREGAR");
        btnAgregarConsumible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarConsumibleActionPerformed(evt);
            }
        });
        jPanel3.add(btnAgregarConsumible);
        btnAgregarConsumible.setBounds(500, 220, 110, 23);

        btnAgregarPrestamo.setBackground(new java.awt.Color(51, 153, 0));
        btnAgregarPrestamo.setFont(new java.awt.Font("Roboto Condensed SemiBold", 1, 12)); // NOI18N
        btnAgregarPrestamo.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarPrestamo.setText("AGREGAR");
        btnAgregarPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPrestamoActionPerformed(evt);
            }
        });
        jPanel3.add(btnAgregarPrestamo);
        btnAgregarPrestamo.setBounds(330, 400, 110, 23);

        lblMateria.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblMateria.setText("MATERIA");
        jPanel3.add(lblMateria);
        lblMateria.setBounds(240, 90, 70, 19);

        tblConsumible.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblConsumible);

        jPanel3.add(jScrollPane5);
        jScrollPane5.setBounds(420, 250, 280, 130);

        tblNoConsumible.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(tblNoConsumible);

        jPanel3.add(jScrollPane6);
        jScrollPane6.setBounds(10, 230, 280, 150);

        btnAgregarNoConsumible.setBackground(new java.awt.Color(51, 153, 0));
        btnAgregarNoConsumible.setFont(new java.awt.Font("Roboto Condensed SemiBold", 1, 12)); // NOI18N
        btnAgregarNoConsumible.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarNoConsumible.setText("AGREGAR");
        btnAgregarNoConsumible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarNoConsumibleActionPerformed(evt);
            }
        });
        jPanel3.add(btnAgregarNoConsumible);
        btnAgregarNoConsumible.setBounds(90, 190, 110, 23);
        jPanel3.add(jScrollBar1);
        jScrollBar1.setBounds(280, 230, 10, 140);
        jPanel3.add(jScrollBar2);
        jScrollBar2.setBounds(690, 270, 10, 100);

        lblDocente1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblDocente1.setText("ADMINISTRADOR");
        jPanel3.add(lblDocente1);
        lblDocente1.setBounds(350, 10, 130, 19);

        jScrollPane2.setViewportView(txtGrupo);

        jPanel3.add(jScrollPane2);
        jScrollPane2.setBounds(90, 90, 120, 22);

        jScrollPane7.setViewportView(txtMat);

        jPanel3.add(jScrollPane7);
        jScrollPane7.setBounds(320, 90, 380, 22);

        lblGrupo2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblGrupo2.setText("GRUPO");
        jPanel3.add(lblGrupo2);
        lblGrupo2.setBounds(30, 90, 60, 19);

        lblDocente2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblDocente2.setText("DOCENTE");
        jPanel3.add(lblDocente2);
        lblDocente2.setBounds(10, 50, 70, 19);

        txtCveAlu.setViewportView(txtNumConAlu);

        jPanel3.add(txtCveAlu);
        txtCveAlu.setBounds(500, 50, 200, 22);

        lblDocente3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblDocente3.setText("NO. VALE");
        jPanel3.add(lblDocente3);
        lblDocente3.setBounds(10, 10, 70, 19);

        jScrollPane9.setViewportView(txtCvePre);

        jPanel3.add(jScrollPane9);
        jScrollPane9.setBounds(90, 10, 200, 22);

        jPanel3.add(cmbAdmin);
        cmbAdmin.setBounds(500, 10, 200, 22);

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 170, 770, 430));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
        // TODO add your handling code here:
        VistaListaMaterial inv = new VistaListaMaterial();
        new ControladorVistaListaMaterial(inv);
        inv.setVisible(true);
        
        //Extra
        try {
            cargarDatosConsumibles();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInventarioActionPerformed

    private void btnPrestamosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrestamosActionPerformed
        // TODO add your handling code here:
        VistaListaPrestamos prestamos = new VistaListaPrestamos();
        new ControladorVistaListaPrestamos(prestamos);
        prestamos.setVisible(true);
    }//GEN-LAST:event_btnPrestamosActionPerformed

    private void btnAlumnos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlumnos1ActionPerformed
        // TODO add your handling code here:
        Alumno registroAlumnos = new Alumno();
        registroAlumnos.setLocationRelativeTo(null); // Mismo lugar
        registroAlumnos.setVisible(true);
        JOptionPane.showMessageDialog(null,"Despues de un movimiento recuerda actializar la tabla para reflejar los cambios");
    }//GEN-LAST:event_btnAlumnos1ActionPerformed

    private void btnDocentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDocentesActionPerformed
        // TODO add your handling code here:
        Docente registroDocentes = new Docente();
        registroDocentes.setLocationRelativeTo(null); // Mismo lugar
        registroDocentes.setVisible(true);
        JOptionPane.showMessageDialog(null,"Despues de un movimiento recuerda actualizar la tabla para reflejar los cambios");
        
        //Extra
        try {
            cargarDocentes();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDocentesActionPerformed

    private void btnImpriRotas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImpriRotas1ActionPerformed
        // TODO add your handling code here:
        HerramientasRotas imprimirRotas = new HerramientasRotas();
        imprimirRotas.setLocationRelativeTo(null); // Mismo lugar
        imprimirRotas.setVisible(true);
    }//GEN-LAST:event_btnImpriRotas1ActionPerformed

    private void btnAgregarConsumibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarConsumibleActionPerformed
        //AgregarPrestamoControlador prestamo = new AgregarPrestamoControlador(this);
        String cveMatCon = txtCveMatCon.getText().trim();
        if(cveMatCon.isEmpty()){
            cveMatCon = null;
        }
        //this.prestamo = new AgregarPrestamoControlador(this);
        prestamo.cargarListaConsumible(cveMatCon);
        this.getCveMatCon().setText("");
     
    }//GEN-LAST:event_btnAgregarConsumibleActionPerformed

    private void btnAgregarPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPrestamoActionPerformed
        // TODO add your handling code here:
        //AgregarPrestamoControlador prestamo = new AgregarPrestamoControlador(this);
        //List<String> d = daoD.obtenerDocenteNomCom();
        int cvePre = Integer.parseInt(txtCvePre.getText().trim());
        String numConAlu = txtNumConAlu.getText().trim();
        String gruAlu = txtGrupo.getText().trim();
        String mat = txtMat.getText().trim();
        String numTraDoc = obtenerNumTraDoc();
        String admin = obtenerNumTraAdmin();
        byte cveAdm = (byte) admin.charAt(0);
        
        System.out.println("Clave Administrador: "+cveAdm);
        try {
            prestamo.agregarPrestamo(cvePre,cveAdm,numConAlu, gruAlu, mat, numTraDoc);
            
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        limpiarCampos();
       
    }//GEN-LAST:event_btnAgregarPrestamoActionPerformed

    private void btnAgregarNoConsumibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarNoConsumibleActionPerformed
        // TODO add your handling code here:
        //AgregarPrestamoControlador prestamo = new AgregarPrestamoControlador(this);
        String idUTNG = txtIdUTNG.getText().trim();
        if(idUTNG.isEmpty()){
            idUTNG = null;
        }
        //this.prestamo = new AgregarPrestamoControlador(this);
        prestamo.cargarListaNoConsumible(idUTNG);
        this.getIdUTNG().setText("");
    }//GEN-LAST:event_btnAgregarNoConsumibleActionPerformed

    private void btnAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminActionPerformed
        // TODO add your handling code here:
        VistaListaAdministradores admin = new VistaListaAdministradores();
        
        new ControladorVistaListaAdministradores(admin);
        admin.setVisible(true);
    }//GEN-LAST:event_btnAdminActionPerformed

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
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new DashBoard().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdmin;
    private javax.swing.JButton btnAgregarConsumible;
    private javax.swing.JButton btnAgregarNoConsumible;
    private javax.swing.JButton btnAgregarPrestamo;
    private javax.swing.JButton btnAlumnos1;
    private javax.swing.JButton btnDocentes;
    private javax.swing.JButton btnImpriRotas1;
    private javax.swing.JButton btnInventario;
    private javax.swing.JButton btnPrestamos;
    private javax.swing.JComboBox<String> cmbAdmin;
    private javax.swing.JComboBox<TipoConsumibleModelo> cmbConsumible;
    private javax.swing.JComboBox<String> cmbDocente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollBar jScrollBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lblDocente1;
    private javax.swing.JLabel lblDocente2;
    private javax.swing.JLabel lblDocente3;
    private javax.swing.JLabel lblGrupo2;
    private javax.swing.JLabel lblMateria;
    private javax.swing.JTable tblConsumible;
    private javax.swing.JTable tblNoConsumible;
    private javax.swing.JScrollPane txtCveAlu;
    private javax.swing.JTextPane txtCveMatCon;
    private javax.swing.JTextPane txtCvePre;
    private javax.swing.JTextPane txtGrupo;
    private javax.swing.JTextPane txtIdUTNG;
    private javax.swing.JTextPane txtMat;
    private javax.swing.JTextPane txtNumConAlu;
    // End of variables declaration//GEN-END:variables

    public JComboBox<TipoConsumibleModelo> getCmbConsumible(){
        return cmbConsumible;
    }
    
    public JComboBox<String> getCmbDocente(){
        return cmbDocente;
    }
    
    public JComboBox<String> getCmbAdmin(){
        return cmbAdmin;
    }

    public JTextPane getCvePre(){
        return txtCvePre;
    }
    
    public JTextPane getCveAdm (){
        return txtNumConAlu;
    }
    
    
    public JTextPane getIdUTNG(){
        return txtIdUTNG;
    }
    
    public JTextPane getGrupo(){
        return txtGrupo;
    }
    
    public JTextPane getMat(){
        return txtMat;
    }
    //public String getIdUTNG(){
      //  return txtIdUTNG.getText().trim();
    //}
    
    public JTextPane getCveMatCon(){
        return txtCveMatCon;
    }
    
    public JTable getTblNoConsumible(){
        return tblNoConsumible;
    }
    
    public JTable getTblConsumible(){
        return tblConsumible;
    }
    //public String getIdLoc(){
      //  return txtIdLoc.getText().trim();
    //}
    
}
