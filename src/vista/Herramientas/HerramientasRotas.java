/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista.Herramientas;
// Paquete actual

// DAO y modelo
import dao.HerramientaNoConsumibleRotasDAO;
import dao.HerramientasConsumiblesRotasDAO;
import modelo.HerramientasRotasModelo;

// Java estándar
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

// Swing
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

// iText para PDF (v5.x)
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

// Apache POI para Excel
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author nataly
 */
public class HerramientasRotas extends javax.swing.JFrame {

    /**
     * Creates new form HerramientasRotas
     */
    public HerramientasRotas() {
        setUndecorated(true);

        initComponents();
        setLocationRelativeTo(null);

        // Agrupar radio buttons
        ButtonGroup grupoBotones = new ButtonGroup();
        grupoBotones.add(rbHerramientasConsumibles);
        grupoBotones.add(jRadioButton1);

        rbHerramientasConsumibles.setSelected(true);  // Seleccionado por defecto
        cargarHerramientasConsumiblesEnTabla(tbl_Datos);

    }
    // metodo para mostrar todas las herramientas  no consumibles rotas 

    public void cargarHerramientasNoConsumiblesEnTabla(JTable tbl_Datos) {
        HerramientaNoConsumibleRotasDAO dao = new HerramientaNoConsumibleRotasDAO();

        List<HerramientasRotasModelo> lista = dao.obtenerTodasHerramientasNoConsumiblesRotas();

        // Columnas que mostraremos
        String[] columnas = {"ID", "Nombre", "Descripción"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        // Rellenar filas
        for (HerramientasRotasModelo h : lista) {
            Object[] fila = {
                h.getId(),
                h.getNombre(),
                h.getDescripcion(),
                
            };
            modelo.addRow(fila);
        }

        // Asignar modelo a la tabla
        tbl_Datos.setModel(modelo);
        configurarFiltro(tbl_Datos, txtBuscador);

    }

    // metodo para mostrar todas las herramientas consumibles rotas 
    public void cargarHerramientasConsumiblesEnTabla(JTable tbl_Datos) {
        HerramientasConsumiblesRotasDAO dao = new HerramientasConsumiblesRotasDAO();
        List<HerramientasRotasModelo> lista = dao.obtenerTodasHerramientasConsumiblesRotas();
        if (lista == null) {
            System.out.print("no hay nada en la lista ");
        }
        // Columnas que mostraremos
        String[] columnas = {"ID", "Nombre", "Descripcion"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        // Rellenar filas
        for (HerramientasRotasModelo h : lista) {
            Object[] fila = {
                h.getId(),
                h.getNombre(),
                h.getDescripcion(),
               
            };
            modelo.addRow(fila);
        }

        // Asignar modelo a la tabla
        tbl_Datos.setModel(modelo);
         configurarFiltro(tbl_Datos, txtBuscador);

    }

    //PDF
    // metodo para exportar todo en el pdf
    public void exportarTablaComoPDF(JTable tabla, String rutaArchivo) {
        Document documento = new Document() {
        };

        try {
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            documento.open();

            // Título
            if (rbHerramientasConsumibles.isSelected()) {
                Paragraph titulo = new Paragraph("Listado de Herramientas Consumibles Rotas", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
                titulo.setAlignment(Element.ALIGN_CENTER);
                documento.add(titulo);
                documento.add(new Paragraph(" ")); // Espacio}
            } else {
                Paragraph titulo = new Paragraph("Listado de Herramientas No Consumibles Rotas", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
                titulo.setAlignment(Element.ALIGN_CENTER);
                documento.add(titulo);
                documento.add(new Paragraph(" ")); // Espacio}

            }

            // Crear tabla PDF
            TableModel modelo = tabla.getModel();
            PdfPTable tablaPDF = new PdfPTable(modelo.getColumnCount());
            tablaPDF.setWidthPercentage(100);

            // Agregar encabezados
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                tablaPDF.addCell(new PdfPCell(new Phrase(modelo.getColumnName(i))));
            }

            // Agregar filas
            for (int fila = 0; fila < modelo.getRowCount(); fila++) {
                for (int col = 0; col < modelo.getColumnCount(); col++) {
                    Object valor = modelo.getValueAt(fila, col);
                    tablaPDF.addCell(valor != null ? valor.toString() : "");
                }
            }

            documento.add(tablaPDF);
            documento.close();

            JOptionPane.showMessageDialog(null, "PDF generado correctamente en: " + rutaArchivo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // metodo para cambiar el nombre del documento 
    public void cambiarNombreDocumentoPDF() {
        if (rbHerramientasConsumibles.isSelected()) {
            String rutaDocumentos = System.getProperty("user.home") + "/Downloads/Herramientas_Consumibles_Rotas.pdf";

            exportarTablaComoPDF(tbl_Datos, rutaDocumentos);
        } else {
            String rutaDocumentos = System.getProperty("user.home") + "/Downloads/Herramientas_No_Consumibles_Rotas.pdf";

            exportarTablaComoPDF(tbl_Datos, rutaDocumentos);

        }
    }

    public void cambiarNombreDocumentoExcel() {
        String rutaDocumentos;
        if (rbHerramientasConsumibles.isSelected()) {
            rutaDocumentos = System.getProperty("user.home") + "/Downloads/Herramientas_Consumibles_Rotas.xlsx";
        } else {
            rutaDocumentos = System.getProperty("user.home") + "/Downloads/Herramientas_No_Consumibles_Rotas.xlsx";
        }
        exportarTablaComoExcel(tbl_Datos, rutaDocumentos);
    }

    //metodo para poder exportar en excel
    public void exportarTablaComoExcel(JTable tabla, String rutaArchivo) {
        try {
            // Crear libro y hoja
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet hoja = workbook.createSheet("Datos");

            // Obtener modelo de tabla
            TableModel modelo = tabla.getModel();
            int filaActual = 0;

            // Título
            Row filaTitulo = hoja.createRow(filaActual++);
            Cell celdaTitulo = filaTitulo.createCell(0);
            String tituloTexto = rbHerramientasConsumibles.isSelected()
                    ? "Listado de Herramientas Consumibles Rotas"
                    : "Listado de Herramientas No Consumibles Rotas";
            celdaTitulo.setCellValue(tituloTexto);

            // Fusionar celdas para el título
            hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, modelo.getColumnCount() - 1));

            // Encabezados
            Row filaEncabezados = hoja.createRow(filaActual++);
            for (int col = 0; col < modelo.getColumnCount(); col++) {
                Cell celda = filaEncabezados.createCell(col);
                celda.setCellValue(modelo.getColumnName(col));
            }

            // Datos
            for (int fila = 0; fila < modelo.getRowCount(); fila++) {
                Row filaExcel = hoja.createRow(fila + filaActual);
                for (int col = 0; col < modelo.getColumnCount(); col++) {
                    Object valor = modelo.getValueAt(fila, col);
                    Cell celda = filaExcel.createCell(col);
                    celda.setCellValue(valor != null ? valor.toString() : "");
                }
            }

            // Ajustar columnas
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                hoja.autoSizeColumn(i);
            }

            // Guardar archivo
            try (FileOutputStream archivoSalida = new FileOutputStream(rutaArchivo)) {
                workbook.write(archivoSalida);
            }

            workbook.close();
            JOptionPane.showMessageDialog(null, "Excel generado correctamente en: " + rutaArchivo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // metodo paea mostrar mensaje de desrcarga 
    private void mostrarDialogoDescarga() {
        Object[] opciones = {"PDF", "Excel"};

        int seleccion = JOptionPane.showOptionDialog(
                this,
                "¿En qué formato deseas descargar?",
                "Seleccionar formato",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion == 0) {
            System.out.println("El usuario eligió PDF.");
            cambiarNombreDocumentoPDF();
        } else if (seleccion == 1) {
            System.out.println("El usuario eligió Excel.");
            cambiarNombreDocumentoExcel();
        } else {
            System.out.println("El usuario canceló o cerró el diálogo.");
        }
    }

    // metodo buscador 
    public void configurarFiltro(JTable tabla, JTextField txtBuscar) {
        TableModel modelo = tabla.getModel();
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(modelo);
        tabla.setRowSorter(rowSorter);

        txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }

            private void filtrar() {
                String texto = txtBuscar.getText();
                if (texto.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    // (?i) hace la búsqueda case-insensitive
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
                }
            }
        });
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Datos = new javax.swing.JTable();
        txtBuscador = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        rbHerramientasConsumibles = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_Datos.setFont(new java.awt.Font("Roboto Condensed", 0, 12)); // NOI18N
        tbl_Datos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Imagen", "Nombre ", "Ubicacion", "Descripcion"
            }
        ));
        tbl_Datos.setFocusable(false);
        tbl_Datos.setSelectionBackground(new java.awt.Color(0, 51, 153));
        jScrollPane1.setViewportView(tbl_Datos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 800, 210));

        txtBuscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscadorActionPerformed(evt);
            }
        });
        jPanel1.add(txtBuscador, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 790, 30));

        jButton2.setBackground(new java.awt.Color(51, 153, 0));
        jButton2.setFont(new java.awt.Font("Roboto Condensed SemiBold", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("DESCARGAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 400, 120, 30));

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
        jLabel1.setText("HERRAMIENTAS ROTAS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButton3)
                .addGap(261, 261, 261)
                .addComponent(jLabel1)
                .addContainerGap(265, Short.MAX_VALUE))
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

        jRadioButton1.setBackground(new java.awt.Color(0, 51, 102));
        jRadioButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jRadioButton1.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton1.setText("HerramientasNoConsumibles");
        jRadioButton1.setBorder(null);
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 140, -1, -1));

        rbHerramientasConsumibles.setBackground(new java.awt.Color(0, 51, 102));
        rbHerramientasConsumibles.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbHerramientasConsumibles.setForeground(new java.awt.Color(255, 255, 255));
        rbHerramientasConsumibles.setText("HerramientasConsumibles");
        rbHerramientasConsumibles.setBorder(null);
        rbHerramientasConsumibles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbHerramientasConsumiblesActionPerformed(evt);
            }
        });
        jPanel1.add(rbHerramientasConsumibles, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        mostrarDialogoDescarga();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void rbHerramientasConsumiblesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbHerramientasConsumiblesActionPerformed
        cargarHerramientasConsumiblesEnTabla(tbl_Datos);
    }//GEN-LAST:event_rbHerramientasConsumiblesActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        cargarHerramientasNoConsumiblesEnTabla(tbl_Datos);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void txtBuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscadorActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(HerramientasRotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HerramientasRotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HerramientasRotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HerramientasRotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HerramientasRotas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbHerramientasConsumibles;
    private javax.swing.JTable tbl_Datos;
    private javax.swing.JTextField txtBuscador;
    // End of variables declaration//GEN-END:variables
}
