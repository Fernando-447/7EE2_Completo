/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.fondo;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author Mr.Robot
 */
public class PanelTransparente extends JPanel {

    private float alpha = 0.6f; // 0.0 = completamente transparente, 1.0 = opaco

    public PanelTransparente() {
        setOpaque(false); // Importante para permitir transparencia real
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        // Aplica el nivel de transparencia
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        // Pinta el fondo (puedes cambiar el color)
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();

        // Llama al super después para que los hijos (JRadioButton, etc.) se pinten correctamente
        super.paintComponent(g);
    }
}