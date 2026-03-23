/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.table.TableModel;
import dao.impl.MaterialConsumibleDAOImpl;
import dao.impl.MaterialNoConsumibleDAOImpl;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelo.MaterialConsumible;
import modelo.MaterialNoConsumible;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.fondo.PanelConFondo;
import vista.buscarMaterial.VistaListaMaterial;
import vista.login.VistaLogin;

/**
 *
 * @author Mr.Robot
 */
public class ControladorVistaListaMaterial {
    private VistaListaMaterial vista = null;
    private MaterialConsumibleDAOImpl materialConsumibleDAO = null;
    private MaterialNoConsumibleDAOImpl materialNoConsumibleDAO = null;
    private DefaultTableModel modeloTabla = null;

    public ControladorVistaListaMaterial(VistaListaMaterial vista) {
        this.vista = vista;
        materialConsumibleDAO = new MaterialConsumibleDAOImpl();
        materialNoConsumibleDAO = new MaterialNoConsumibleDAOImpl();
        cargarFondo();
        cargarMaterialNoCon();
        cargarEventos();
    }
    
    private void cargarEventos() {
        vista.getBtnBuscar().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buscarMaterial();
        }
    });
        
        vista.getBtnVolver().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            volver();
        }
    });
        
        vista.getjMICerrarSesion().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cerrarSesion();
        }
    });
        
        vista.getjRBConsumible().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            consumibleSeleccionado();
        }
    });
        
        vista.getjRBNoConsumible().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            noConsumibleSeleccionado();
        }
    });
    }
    
    private void cargarFondo() {
        vista.getjPFondo().setLayout(new BorderLayout());
        vista.getjPFondo().add(new PanelConFondo("/util/imagenes/fondoPrestamosYMaterial.jpg"), BorderLayout.CENTER);
        vista.getjPFondo().revalidate();
        vista.getjPFondo().repaint();
    }
    
    private void cargarMaterialNoCon() {
        DefaultTableModel modelo = crearModeloTablaNoConsumible();
        vista.getTblMaterial().setModel(modelo);
        for (MaterialNoConsumible m : materialNoConsumibleDAO.listar()) {
            modelo.addRow(filaNoConsumible(m));
        }
    }
    
    private void cargarMaterialCon() {
        DefaultTableModel modelo = crearModeloTablaConsumible();
        vista.getTblMaterial().setModel(modelo);
        for (MaterialConsumible m : materialConsumibleDAO.listar()) {
            modelo.addRow(filaConsumible(m));
        }
    }
    
    private void volver() {
        vista.dispose();
    }
    
    private void cerrarSesion() {
        vista.dispose();
        VistaLogin vista = new VistaLogin();
        new ControladorVistaLogin(vista);
        vista.setVisible(true);
    }
    
    private void consumibleSeleccionado() {
        vista.getjRBUTNG().setEnabled(false);
        vista.getjRBEstatal().setEnabled(false);
        vista.getjRBLocal().setEnabled(true);
        vista.getjRBCveMatCon().setEnabled(true);
    }
    
    private void noConsumibleSeleccionado() {
        vista.getjRBUTNG().setEnabled(true);
        vista.getjRBEstatal().setEnabled(true);
        vista.getjRBLocal().setEnabled(false);
        vista.getjRBCveMatCon().setEnabled(false);
    }
    
    private boolean campoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
    
    private void buscarMaterial() {
        String input = vista.getTxtNoControl().getText().trim();

        if (!vista.getjRBConsumible().isSelected() && !vista.getjRBNoConsumible().isSelected() && campoVacio(input)) {
            JOptionPane.showMessageDialog(vista, "Seleccione un tipo de material");
            return;
        }

        if (vista.getjRBNoConsumible().isSelected()) {
            buscarNoConsumible(input);
        } else if (vista.getjRBConsumible().isSelected()) {
            buscarConsumible(input);
        }
    }

    private void buscarConsumible(String input) {
        DefaultTableModel modelo = crearModeloTablaConsumible();
        vista.getTblMaterial().setModel(modelo);

        if (campoVacio(input)) {
            for (MaterialConsumible m : materialConsumibleDAO.listar()) {
                modelo.addRow(filaConsumible(m));
            }
            return;
        }

        if(!vista.getjRBCveMatCon().isSelected() && !vista.getjRBLocal().isSelected()) {
            JOptionPane.showMessageDialog(vista, "Seleccione un identificador");
            return;
        }
        
        MaterialConsumible m = null;
        if (vista.getjRBCveMatCon().isSelected()) {
            m = materialConsumibleDAO.buscarPorCveMatCon(input);
        } else if (vista.getjRBLocal().isSelected()) {
            m = materialConsumibleDAO.buscarPorIdLoc(input);
        }

        if (m != null) {
            modelo.addRow(filaConsumible(m));
        } else {
            JOptionPane.showMessageDialog(vista, "No se encontró el material");
            cargarMaterialCon();
        }
    }

    private void buscarNoConsumible(String input) {
        DefaultTableModel modelo = crearModeloTablaNoConsumible();
        vista.getTblMaterial().setModel(modelo);

        if (campoVacio(input)) {
            for (MaterialNoConsumible m : materialNoConsumibleDAO.listar()) {
                modelo.addRow(filaNoConsumible(m));
            }
            return;
        }
        
        if(!vista.getjRBUTNG().isSelected() && !vista.getjRBEstatal().isSelected()) {
            JOptionPane.showMessageDialog(vista, "Seleccione un identificador");
            return;
        }

        MaterialNoConsumible m = null;
        if (vista.getjRBUTNG().isSelected()) {
            m = materialNoConsumibleDAO.buscarPorIdUTNG(input);
        } else if (vista.getjRBEstatal().isSelected()) {
            m = materialNoConsumibleDAO.buscarPorIdEst(input);
        }

        if (m != null) {
            modelo.addRow(filaNoConsumible(m));
        } else {
            JOptionPane.showMessageDialog(vista, "No se encontró el material");
            cargarMaterialNoCon();
        }
    }
    
    private DefaultTableModel crearModeloTablaConsumible() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("ID Local");
        modelo.addColumn("Descripción");
        modelo.addColumn("Estado");
        modelo.addColumn("Disposición");
        modelo.addColumn("Tipo");
        return modelo;
    }

    private DefaultTableModel crearModeloTablaNoConsumible() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID UTNG");
        modelo.addColumn("ID Estatal");
        modelo.addColumn("Descripción");
        modelo.addColumn("No. activo");
        modelo.addColumn("No. serie");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Costo");
        modelo.addColumn("Estado");
        modelo.addColumn("Disposición");
        modelo.addColumn("Tipo");
        return modelo;
    }

    private Object[] filaConsumible(MaterialConsumible m) {
        return new Object[] {
            m.getCveMatCon(),
            m.getIdLoc(),
            m.getDesMatCon(),
            m.getEstMatCon().getValorBD(),
            m.getDisposicion().getValorBD(),
            m.getTipMat()
        };
    }

    private Object[] filaNoConsumible(MaterialNoConsumible m) {
        return new Object[] {
            m.getIdUTNG(),
            m.getIdEst(),
            m.getDesMatNoCon(),
            m.getNumAct(),
            m.getNumSer(),
            m.getMarca(),
            m.getModelo(),
            m.getCosto(),
            m.getEstMatNoCon().getValorBD(),
            m.getDisposicion().getValorBD(),
            m.getTipMat()
        };
    }
    
    public void exportarTablaAExcel(JTable tabla) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Guardar reporte Excel");
    fileChooser.setSelectedFile(new File("Inventario.xlsx")); // Nombre sugerido

    int userSelection = fileChooser.showSaveDialog(null);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();

        String rutaArchivo = fileToSave.getAbsolutePath();
        if (!rutaArchivo.toLowerCase().endsWith(".xlsx")) {
            rutaArchivo += ".xlsx";
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte");

            // Crear estilo de encabezado
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 11);
            headerStyle.setFont(headerFont);

            TableModel model = tabla.getModel();

            // Crear fila de encabezados
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < model.getColumnCount(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(model.getColumnName(col));
                cell.setCellStyle(headerStyle);
            }

            // Crear filas de datos
            for (int row = 0; row < model.getRowCount(); row++) {
                Row dataRow = sheet.createRow(row + 1);
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object value = model.getValueAt(row, col);
                    Cell cell = dataRow.createCell(col);

                    if (value instanceof Number) {
                        cell.setCellValue(((Number) value).doubleValue());
                    } else {
                        cell.setCellValue(value != null ? value.toString() : "");
                    }
                }
            }

            // Ajustar ancho de columnas automáticamente
            for (int col = 0; col < model.getColumnCount(); col++) {
                sheet.autoSizeColumn(col);
            }

            // Guardar el archivo
            try (FileOutputStream out = new FileOutputStream(rutaArchivo)) {
                workbook.write(out);
            }

            JOptionPane.showMessageDialog(null, "Reporte Excel generado correctamente:\n" + rutaArchivo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al exportar Excel:\n" + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
}