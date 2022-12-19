/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.crypto.SecretKey;
import javax.swing.*;

/**
 * @author 9FDAM09
 */
public class VentanaPrincipal extends javax.swing.JFrame {
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private SecretKey key;
    public VentanaPrincipal(ObjectInputStream ois, ObjectOutputStream oos, SecretKey key) {
        initComponents();
        this.ois = ois;
        this.oos = oos;
        this.key = key;

        PanelCuentas frame = new PanelCuentas(ois, oos, key);
        frame.setSize(830, 550);
        frame.setLocation(0, 0);
        content.removeAll();
        content.add(frame, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMenu = new javax.swing.JPanel();
        botonSalir = new javax.swing.JPanel();
        bordeSalir = new javax.swing.JPanel();
        labelSalir = new javax.swing.JLabel();
        botonNuevaCuenta = new javax.swing.JPanel();
        bordePiezas = new javax.swing.JPanel();
        labelPiezas = new javax.swing.JLabel();
        botonMovimientos = new javax.swing.JPanel();
        bordeGestion = new javax.swing.JPanel();
        labelGestion = new javax.swing.JLabel();
        botonTransferencia = new javax.swing.JPanel();
        bordeProyectos = new javax.swing.JPanel();
        labelProyectos = new javax.swing.JLabel();
        botonCuentas = new javax.swing.JPanel();
        labelPlatos = new javax.swing.JLabel();
        bordePlatos1 = new javax.swing.JPanel();
        content = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelMenu.setBackground(new java.awt.Color(57, 57, 58));
        panelMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botonSalir.setBackground(new java.awt.Color(57, 57, 58));
        botonSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonSalirMousePressed(evt);
            }
        });

        bordeSalir.setBackground(new java.awt.Color(57, 57, 58));
        bordeSalir.setPreferredSize(new java.awt.Dimension(5, 0));

        javax.swing.GroupLayout bordeSalirLayout = new javax.swing.GroupLayout(bordeSalir);
        bordeSalir.setLayout(bordeSalirLayout);
        bordeSalirLayout.setHorizontalGroup(
            bordeSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        bordeSalirLayout.setVerticalGroup(
            bordeSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 53, Short.MAX_VALUE)
        );

        labelSalir.setBackground(new java.awt.Color(219, 219, 219));
        labelSalir.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelSalir.setForeground(new java.awt.Color(219, 219, 219));
        labelSalir.setText("Cerrar Sesion");

        javax.swing.GroupLayout botonSalirLayout = new javax.swing.GroupLayout(botonSalir);
        botonSalir.setLayout(botonSalirLayout);
        botonSalirLayout.setHorizontalGroup(
            botonSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botonSalirLayout.createSequentialGroup()
                .addComponent(bordeSalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelSalir)
                .addContainerGap(59, Short.MAX_VALUE))
        );
        botonSalirLayout.setVerticalGroup(
            botonSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bordeSalir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
            .addGroup(botonSalirLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(labelSalir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelMenu.add(botonSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 170, -1));

        botonNuevaCuenta.setBackground(new java.awt.Color(57, 57, 58));
        botonNuevaCuenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonNuevaCuentaMousePressed(evt);
            }
        });

        bordePiezas.setBackground(new java.awt.Color(57, 57, 58));
        bordePiezas.setPreferredSize(new java.awt.Dimension(5, 0));

        javax.swing.GroupLayout bordePiezasLayout = new javax.swing.GroupLayout(bordePiezas);
        bordePiezas.setLayout(bordePiezasLayout);
        bordePiezasLayout.setHorizontalGroup(
            bordePiezasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        bordePiezasLayout.setVerticalGroup(
            bordePiezasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        labelPiezas.setBackground(new java.awt.Color(219, 219, 219));
        labelPiezas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelPiezas.setForeground(new java.awt.Color(219, 219, 219));
        labelPiezas.setText("Nueva Cuenta");

        javax.swing.GroupLayout botonNuevaCuentaLayout = new javax.swing.GroupLayout(botonNuevaCuenta);
        botonNuevaCuenta.setLayout(botonNuevaCuentaLayout);
        botonNuevaCuentaLayout.setHorizontalGroup(
            botonNuevaCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botonNuevaCuentaLayout.createSequentialGroup()
                .addComponent(bordePiezas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelPiezas)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        botonNuevaCuentaLayout.setVerticalGroup(
            botonNuevaCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bordePiezas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addGroup(botonNuevaCuentaLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(labelPiezas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelMenu.add(botonNuevaCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 170, -1));

        botonMovimientos.setBackground(new java.awt.Color(57, 57, 58));
        botonMovimientos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonMovimientosMousePressed(evt);
            }
        });

        bordeGestion.setBackground(new java.awt.Color(57, 57, 58));
        bordeGestion.setPreferredSize(new java.awt.Dimension(5, 0));

        javax.swing.GroupLayout bordeGestionLayout = new javax.swing.GroupLayout(bordeGestion);
        bordeGestion.setLayout(bordeGestionLayout);
        bordeGestionLayout.setHorizontalGroup(
            bordeGestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        bordeGestionLayout.setVerticalGroup(
            bordeGestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        labelGestion.setBackground(new java.awt.Color(219, 219, 219));
        labelGestion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelGestion.setForeground(new java.awt.Color(219, 219, 219));
        labelGestion.setText("Movimientos");

        javax.swing.GroupLayout botonMovimientosLayout = new javax.swing.GroupLayout(botonMovimientos);
        botonMovimientos.setLayout(botonMovimientosLayout);
        botonMovimientosLayout.setHorizontalGroup(
            botonMovimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botonMovimientosLayout.createSequentialGroup()
                .addComponent(bordeGestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelGestion)
                .addContainerGap(69, Short.MAX_VALUE))
        );
        botonMovimientosLayout.setVerticalGroup(
            botonMovimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bordeGestion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addGroup(botonMovimientosLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(labelGestion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelMenu.add(botonMovimientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 170, -1));

        botonTransferencia.setBackground(new java.awt.Color(57, 57, 58));
        botonTransferencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonTransferenciaMousePressed(evt);
            }
        });

        bordeProyectos.setBackground(new java.awt.Color(57, 57, 58));
        bordeProyectos.setPreferredSize(new java.awt.Dimension(5, 0));

        javax.swing.GroupLayout bordeProyectosLayout = new javax.swing.GroupLayout(bordeProyectos);
        bordeProyectos.setLayout(bordeProyectosLayout);
        bordeProyectosLayout.setHorizontalGroup(
            bordeProyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        bordeProyectosLayout.setVerticalGroup(
            bordeProyectosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        labelProyectos.setBackground(new java.awt.Color(219, 219, 219));
        labelProyectos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelProyectos.setForeground(new java.awt.Color(219, 219, 219));
        labelProyectos.setText("Nueva Transferencia");

        javax.swing.GroupLayout botonTransferenciaLayout = new javax.swing.GroupLayout(botonTransferencia);
        botonTransferencia.setLayout(botonTransferenciaLayout);
        botonTransferenciaLayout.setHorizontalGroup(
            botonTransferenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botonTransferenciaLayout.createSequentialGroup()
                .addComponent(bordeProyectos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelProyectos)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        botonTransferenciaLayout.setVerticalGroup(
            botonTransferenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bordeProyectos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addGroup(botonTransferenciaLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(labelProyectos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelMenu.add(botonTransferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 170, 50));

        botonCuentas.setBackground(new java.awt.Color(184, 184, 184));
        botonCuentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonCuentasMousePressed(evt);
            }
        });

        labelPlatos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelPlatos.setForeground(new java.awt.Color(0, 0, 0));
        labelPlatos.setText("Consultar Cuentas");

        bordePlatos1.setBackground(new java.awt.Color(255, 255, 255));
        bordePlatos1.setPreferredSize(new java.awt.Dimension(5, 0));

        javax.swing.GroupLayout bordePlatos1Layout = new javax.swing.GroupLayout(bordePlatos1);
        bordePlatos1.setLayout(bordePlatos1Layout);
        bordePlatos1Layout.setHorizontalGroup(
            bordePlatos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        bordePlatos1Layout.setVerticalGroup(
            bordePlatos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout botonCuentasLayout = new javax.swing.GroupLayout(botonCuentas);
        botonCuentas.setLayout(botonCuentasLayout);
        botonCuentasLayout.setHorizontalGroup(
            botonCuentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botonCuentasLayout.createSequentialGroup()
                .addComponent(bordePlatos1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelPlatos)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        botonCuentasLayout.setVerticalGroup(
            botonCuentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botonCuentasLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(labelPlatos)
                .addContainerGap(18, Short.MAX_VALUE))
            .addComponent(bordePlatos1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
        );

        panelMenu.add(botonCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 170, -1));

        getContentPane().add(panelMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 550));

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 830, Short.MAX_VALUE)
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );

        getContentPane().add(content, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 830, 550));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botonNuevaCuentaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonNuevaCuentaMousePressed
        setColor(botonNuevaCuenta, bordePiezas, labelPiezas);
        resetColor(botonSalir, bordeSalir, labelSalir);
        resetColor(botonMovimientos, bordeGestion, labelGestion);
        resetColor(botonTransferencia, bordeProyectos, labelProyectos);
        resetColor(botonCuentas, bordePlatos1, labelPlatos);

        PanelNuevaCuenta frame = new PanelNuevaCuenta(ois, oos, key);
        frame.setSize(830, 550);
        frame.setLocation(0, 0);
        content.removeAll();
        content.add(frame, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }//GEN-LAST:event_botonNuevaCuentaMousePressed

    private void botonSalirMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonSalirMousePressed
        setColor(botonSalir, bordeSalir, labelSalir);
        resetColor(botonNuevaCuenta, bordePiezas, labelPiezas);
        resetColor(botonMovimientos, bordeGestion, labelGestion);
        resetColor(botonTransferencia, bordeProyectos, labelProyectos);
        resetColor(botonCuentas, bordePlatos1, labelPlatos);

        try {
            oos.writeObject(6);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "ERROR inesperado.");
        }
        VentanaLogin frame = new VentanaLogin(ois, oos, key);
        frame.setVisible(true);
        dispose();
    }//GEN-LAST:event_botonSalirMousePressed

    private void botonMovimientosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonMovimientosMousePressed
        setColor(botonMovimientos, bordeGestion, labelGestion);
        resetColor(botonNuevaCuenta, bordePiezas, labelPiezas);
        resetColor(botonSalir, bordeSalir, labelSalir);
        resetColor(botonTransferencia, bordeProyectos, labelProyectos);
        resetColor(botonCuentas, bordePlatos1, labelPlatos);

        PanelMovimientos frame = new PanelMovimientos(ois, oos, key);
        frame.setSize(830, 550);
        frame.setLocation(0, 0);
        content.removeAll();
        content.add(frame, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }//GEN-LAST:event_botonMovimientosMousePressed

    private void botonTransferenciaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonTransferenciaMousePressed
        setColor(botonTransferencia, bordeProyectos, labelProyectos);
        resetColor(botonMovimientos, bordeGestion, labelGestion);
        resetColor(botonNuevaCuenta, bordePiezas, labelPiezas);
        resetColor(botonSalir, bordeSalir, labelSalir);
        resetColor(botonCuentas, bordePlatos1, labelPlatos);

        PanelNuevaTranferencia frame = new PanelNuevaTranferencia(ois, oos, key, content);
        frame.setSize(830, 550);
        frame.setLocation(0, 0);
        content.removeAll();
        content.add(frame, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }//GEN-LAST:event_botonTransferenciaMousePressed

    private void botonCuentasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonCuentasMousePressed
        setColor(botonCuentas, bordePlatos1, labelPlatos);
        resetColor(botonMovimientos, bordeGestion, labelGestion);
        resetColor(botonNuevaCuenta, bordePiezas, labelPiezas);
        resetColor(botonSalir, bordeSalir, labelSalir);
        resetColor(botonTransferencia, bordeProyectos, labelProyectos);

        PanelCuentas frame = new PanelCuentas(ois, oos, key);
        frame.setSize(830, 550);
        frame.setLocation(0, 0);
        content.removeAll();
        content.add(frame, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }//GEN-LAST:event_botonCuentasMousePressed

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
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                //new VentanaPrincipal(ois, oos, key).setVisible(true);
            }
        });
    }

    public VentanaPrincipal(JPanel boton1, JPanel boton2, JPanel boton3, JPanel boton4, JPanel boton5, JLabel labelBoton1, JLabel labelBoton2, JLabel labelBoton3, JLabel labelBoton4, JLabel labelBoton5, JPanel panelMenu) throws HeadlessException {
        this.botonSalir = boton1;
        this.botonNuevaCuenta = boton3;
        this.labelSalir = labelBoton1;
        this.labelPiezas = labelBoton2;
        this.panelMenu = panelMenu;
    }

    private void setColor(JPanel pane, JPanel borde, JLabel texto) {
        pane.setBackground(new Color(184, 184, 184));
        borde.setBackground(new Color(255, 255, 255));
        texto.setForeground(new Color(0, 0, 0));
    }

    private void resetColor(JPanel pane, JPanel borde, JLabel texto) {
        pane.setBackground(new Color(57, 57, 58));
        borde.setBackground(new Color(57, 57, 58));
        texto.setForeground(new Color(219, 219, 219));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bordeGestion;
    private javax.swing.JPanel bordePiezas;
    private javax.swing.JPanel bordePlatos1;
    private javax.swing.JPanel bordeProyectos;
    private javax.swing.JPanel bordeSalir;
    private javax.swing.JPanel botonCuentas;
    private javax.swing.JPanel botonMovimientos;
    private javax.swing.JPanel botonNuevaCuenta;
    private javax.swing.JPanel botonSalir;
    private javax.swing.JPanel botonTransferencia;
    private javax.swing.JPanel content;
    private javax.swing.JLabel labelGestion;
    private javax.swing.JLabel labelPiezas;
    private javax.swing.JLabel labelPlatos;
    private javax.swing.JLabel labelProyectos;
    private javax.swing.JLabel labelSalir;
    private javax.swing.JPanel panelMenu;
    // End of variables declaration//GEN-END:variables
}
