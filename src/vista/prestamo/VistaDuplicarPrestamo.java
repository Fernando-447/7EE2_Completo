/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista.prestamo;

import conexion.ConexionDB;
import controlador.AgregarPrestamoControlador;
import controlador.AltaConsumibleControlador;
import controlador.DuplicarPrestamoControlador;
import dao.AlumnoDAO;
import dao.DocenteDAO;
import dao.DuplicarPrestamoDAO;
import dao.impl.AdministradorDAOImpl;
import dao.TipoHerramientaConsumibleDAO;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import modelo.TipoConsumibleModelo;
import vista.DashBoard;



/**
 *
 * @author otero
 */
public class VistaDuplicarPrestamo extends javax.swing.JFrame {

    /**
     * Creates new form VistaModificarPrestamo
     */
    ConexionDB conexionDB = new ConexionDB();
    java.sql.Connection conexion = conexionDB.getConexion();
    
    private AltaConsumibleControlador controlador;
    private List<String> numTraDoc;
    private Map<String, String> mapaDocentes = new HashMap<>();
    private Map<String, String> mapaAdmin = new HashMap<>();
    
    DocenteDAO daoD = new DocenteDAO();
    AlumnoDAO daoA = new AlumnoDAO();
    DuplicarPrestamoDAO daoDup = new DuplicarPrestamoDAO(conexion);
    
    DuplicarPrestamoControlador prestamo = new DuplicarPrestamoControlador(this);
    
    public VistaDuplicarPrestamo() throws SQLException  {
        initComponents();
        
        DefaultTableModel model = (DefaultTableModel) tblMatNoCon.getModel();
        model.setRowCount(0);
        
        tblMatNoCon.setModel(new DefaultTableModel(
                new Object[][]{},
                new String []{"ID UTNG", "NOMBRE"}
        ));
        
        DefaultTableModel model2 = (DefaultTableModel) tblMatCon.getModel();
        model2.setRowCount(0);
        
        tblMatCon.setModel(new DefaultTableModel(
                new Object[][]{},
                new String []{"CLAVE", "NOMBRE"}
        ));
        setLocationRelativeTo(null);
        
        DuplicarPrestamoControlador modiCont = new DuplicarPrestamoControlador(this);
        
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
        
        List<String> lista = daoDup.obtenerAdminNomCom();
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
    
    public String obtenerCveAdm(){
        String nombreSeleccionado = (String) cmbAdmin.getSelectedItem();
        
        if(nombreSeleccionado != null && !nombreSeleccionado.isEmpty()){
            return mapaAdmin.get(nombreSeleccionado);
        } 
        
        return null;
    }
    
    public List<String[]> getDatosNoConsumibles(){
        List<String[]> datos = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tblMatNoCon.getModel();
        for(int i = 0; i < model.getRowCount();i++){
            String idUTNG = model.getValueAt(i, 0).toString();
            
            datos.add(new String []{idUTNG});
        }
        
        return datos;
    }
    
    public List<String[]> getDatosConsumibles(){
        List<String[]> datos = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tblMatCon.getModel();
        for(int i = 0; i < model.getRowCount();i++){
            String cveMatCon = model.getValueAt(i, 0).toString();
            
            datos.add(new String []{cveMatCon});
        }
        
        return datos;
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
        jPanel3 = new javax.swing.JPanel();
        btnRegresar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNumConAlu = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMatCon = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMatNoCon = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtIdUTNGMod = new javax.swing.JTextField();
        txtCveMatCon = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnAddNoCon = new javax.swing.JButton();
        btnAddCon = new javax.swing.JButton();
        btnGuardarCambios = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtMateria = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtGrupo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCvePre = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnElimarPreMatNoCon = new javax.swing.JButton();
        btnEliminarPreMatCon = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtObs = new javax.swing.JTextField();
        cmbAdmin = new javax.swing.JComboBox<>();
        cmbDocente = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 102));

        jPanel3.setBackground(new java.awt.Color(0, 0, 51));

        btnRegresar.setBackground(new java.awt.Color(0, 0, 51));
        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zImagenes/regresar.png"))); // NOI18N
        btnRegresar.setBorder(null);
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto Condensed Black", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PRESTAMO");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnRegresar)
                .addGap(264, 264, 264)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRegresar)
                    .addComponent(jLabel1))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Docente:");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Alumno:");

        txtNumConAlu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumConAluActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Agregar material No consumible:");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Agregar material consumible:");

        tblMatCon.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblMatCon);

        tblMatNoCon.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblMatNoCon);

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("ID UTNG:");

        txtIdUTNGMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdUTNGModActionPerformed(evt);
            }
        });

        txtCveMatCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCveMatConActionPerformed(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("CLAVE:");

        btnAddNoCon.setText("AGREGAR");
        btnAddNoCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNoConActionPerformed(evt);
            }
        });

        btnAddCon.setText("AGREGAR");
        btnAddCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddConActionPerformed(evt);
            }
        });

        btnGuardarCambios.setText("Guardar");
        btnGuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCambiosActionPerformed(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Materia:");

        txtMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMateriaActionPerformed(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("GRUPO:");

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("No. VALE:");

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("ADMINISTRADOR");

        btnElimarPreMatNoCon.setText("ELIMINAR");
        btnElimarPreMatNoCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElimarPreMatNoConActionPerformed(evt);
            }
        });

        btnEliminarPreMatCon.setText("ELIMINAR");
        btnEliminarPreMatCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPreMatConActionPerformed(evt);
            }
        });

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("OBS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(btnAddNoCon)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnElimarPreMatNoCon))
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtIdUTNGMod, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel4))
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addGap(132, 132, 132))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtCveMatCon, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnAddCon)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnEliminarPreMatCon))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(371, 371, 371)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmbDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCvePre, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addGap(27, 27, 27)
                                .addComponent(txtMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtNumConAlu, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel10)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(334, 334, 334))
                                    .addComponent(txtObs))))))
                .addGap(56, 56, 56))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(344, 344, 344)
                .addComponent(btnGuardarCambios)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtCvePre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbDocente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtNumConAlu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtObs, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(55, 55, 55))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtIdUTNGMod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCveMatCon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddNoCon)
                    .addComponent(btnElimarPreMatNoCon)
                    .addComponent(btnAddCon)
                    .addComponent(btnEliminarPreMatCon))
                .addGap(20, 20, 20)
                .addComponent(btnGuardarCambios)
                .addContainerGap())
        );

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

    private void txtMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMateriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMateriaActionPerformed

    private void btnGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCambiosActionPerformed
        // TODO add your handling code here:
        int cvePre = Integer.parseInt(txtCvePre.getText().trim());
        String grupo = txtGrupo.getText().trim();
        String mat = txtMateria.getText().trim();
        String obs = txtObs.getText().trim();
        String numConAlu = txtNumConAlu.getText().trim();
        String numTraDoc = obtenerNumTraDoc();
        String cve = obtenerCveAdm();
        byte cveAdm = Byte.parseByte(cve);
        
        DuplicarPrestamoControlador dupliCont = new DuplicarPrestamoControlador(this);
        try {
            dupliCont.agregarPrestamo(cvePre, cveAdm, numConAlu, grupo, mat, numTraDoc, obs);
        } catch (SQLException ex) {
            Logger.getLogger(VistaDuplicarPrestamo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarCambiosActionPerformed

    private void txtCveMatConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCveMatConActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCveMatConActionPerformed

    private void txtIdUTNGModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdUTNGModActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdUTNGModActionPerformed

    private void txtNumConAluActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumConAluActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumConAluActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        VistaListaPrestamos vista = new VistaListaPrestamos();
        vista.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnAddNoConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNoConActionPerformed
        
        try {
            // TODO add your handling code here:
            DashBoard dash = new DashBoard();
            DuplicarPrestamoControlador pres = new DuplicarPrestamoControlador(this);
            String idUTNG = txtIdUTNGMod.getText().trim();
            if(idUTNG.isEmpty()){
                idUTNG = null;
            }
            //this.prestamo = new AgregarPrestamoControlador(this);
            pres.cargarListaNoConsumible(idUTNG);
            
        this.getIdUTNG().setText("");
        } catch (SQLException ex) {
            Logger.getLogger(VistaDuplicarPrestamo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_btnAddNoConActionPerformed

    private void btnAddConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddConActionPerformed
        // TODO add your handling code here:
        String cveMatCon = txtCveMatCon.getText().trim();
        if(cveMatCon.isEmpty()){
            cveMatCon = null;
        }
        //this.prestamo = new AgregarPrestamoControlador(this);
        prestamo.cargarListaConsumible(cveMatCon);
        this.getCveMatCon().setText("");
    }//GEN-LAST:event_btnAddConActionPerformed

    private void btnElimarPreMatNoConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElimarPreMatNoConActionPerformed
        // TODO add your handling code here:
        DuplicarPrestamoControlador modiCont = new DuplicarPrestamoControlador(this);
        modiCont.eliminarNoCon();
        
    }//GEN-LAST:event_btnElimarPreMatNoConActionPerformed

    private void btnEliminarPreMatConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPreMatConActionPerformed
        // TODO add your handling code here:
        DuplicarPrestamoControlador modiCont = new DuplicarPrestamoControlador(this);
        modiCont.eliminarCon();
    }//GEN-LAST:event_btnEliminarPreMatConActionPerformed

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
            java.util.logging.Logger.getLogger(VistaDuplicarPrestamo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaDuplicarPrestamo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaDuplicarPrestamo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaDuplicarPrestamo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new VistaDuplicarPrestamo().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(VistaDuplicarPrestamo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCon;
    private javax.swing.JButton btnAddNoCon;
    private javax.swing.JButton btnElimarPreMatNoCon;
    private javax.swing.JButton btnEliminarPreMatCon;
    private javax.swing.JButton btnGuardarCambios;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cmbAdmin;
    private javax.swing.JComboBox<String> cmbDocente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblMatCon;
    private javax.swing.JTable tblMatNoCon;
    private javax.swing.JTextField txtCveMatCon;
    private javax.swing.JTextField txtCvePre;
    private javax.swing.JTextField txtGrupo;
    private javax.swing.JTextField txtIdUTNGMod;
    private javax.swing.JTextField txtMateria;
    private javax.swing.JTextField txtNumConAlu;
    private javax.swing.JTextField txtObs;
    // End of variables declaration//GEN-END:variables
    public JTextField getIdUTNG(){
        return txtIdUTNGMod;
    }
    
    public JTextField getCveMatCon(){
        return txtCveMatCon;
    }
    
    public JTable getTblMatNoCon(){
        return tblMatNoCon;
    }
    public JTable getTblMatCon(){
        return tblMatCon;
    }
    
    public JTextField getCvePre(){
        return txtCvePre;
    }
    
    public JTextField getNumConAlu(){
        return txtNumConAlu;
    }
    
    public JTextField getGrupo(){
        return txtGrupo;
    }
    
    public JTextField getMateria(){
        return txtMateria;
    }
    
    public JTextField getObs() {
        return txtObs;
    }
    
    public JComboBox<String> getCmbDocente(){
        return cmbDocente;
    }
    
    public JComboBox<String> getCmbAdmin(){
        return cmbAdmin;
    }
}


