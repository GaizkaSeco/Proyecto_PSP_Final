/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package swing;

import java.awt.Color;
import java.awt.Component;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.crypto.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import clases.Transferencia;
import scrollbar.ScrollBarCustom;
import table.TableHeader;

/**
 *
 * @author omega
 */
public class PanelMovimientos extends javax.swing.JPanel {
    String[] nombreColumnas = {"Origen", "Destino", "Descripcion", "Cantidad", "Fecha"};
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private SecretKey key;

    /**
     * Creates new form PanelMovimientos
     */
    public PanelMovimientos(ObjectInputStream ois, ObjectOutputStream oos, SecretKey key) {
        initComponents();
        this.ois = ois;
        this.oos = oos;
        this.key = key;
        //cargamos el modelo de la tabla
        table1.setShowHorizontalLines(true);
        table1.setGridColor(new Color(230, 230, 230));
        table1.setRowHeight(30);
        table1.getTableHeader().setReorderingAllowed(true);
        table1.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                TableHeader header = new TableHeader(value + "");
                if (column == nombreColumnas.length) {
                    header.setHorizontalAlignment(JLabel.CENTER);
                }
                return header;
            }
        });
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom());
        fixtable(jScrollPane1);
        //cargamos los datos de la tabla
        cargarDatos();
    }

    public void fixtable(JScrollPane scroll) {
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setVerticalScrollBar(new ScrollBarCustom());
        JPanel p = new JPanel();
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        scroll.setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    public void cargarDatos() {
        try {
            //mandamos la opcion que hemos escogido
            oos.writeObject(4);
            Cipher desCipher = Cipher.getInstance("DES");
            desCipher.init(Cipher.DECRYPT_MODE, key);
            //recuperamos la lista de movimientos
            byte[] movimientosCifradas = (byte[]) ois.readObject();
            byte[] movimientosBytes = desCipher.doFinal(movimientosCifradas);
            ByteArrayInputStream bis = new ByteArrayInputStream(movimientosBytes);
            ObjectInputStream oisbytes = new ObjectInputStream(bis);
            List<Transferencia> movimientos = (List<Transferencia>) oisbytes.readObject();
            bis.close();
            oisbytes.close();
            //cargamos los datos en la tabla
            int cantidad = movimientos.size();
            String[][] d = new String[cantidad][5];
            for (int i = 0; i < movimientos.size(); i++) {
                d[i][0] = String.valueOf(movimientos.get(i).getOrigen());
                d[i][1] = String.valueOf(movimientos.get(i).getDestino());
                d[i][2] = String.valueOf(movimientos.get(i).getDescripcion());
                d[i][3] = String.valueOf(movimientos.get(i).getCantidad());
                d[i][4] = String.valueOf(movimientos.get(i).getFecha());
            }
            table1.setModel(new DefaultTableModel(d, nombreColumnas));
        } catch (IOException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            JOptionPane.showMessageDialog(null, "ERROR inesperado.");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, "No se ha especficado el algoritmo.");
        } catch (InvalidKeyException e) {
            JOptionPane.showMessageDialog(null, "La llave que se esta usando no¡ es la correcta.");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No se han podido cargar los datos por que no se pueden castear.");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 204));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        table1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(table1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 690, 400));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MOVIMIENTOS");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 830, 30));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table1;
    // End of variables declaration//GEN-END:variables
}