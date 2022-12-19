/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scrollbar;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;

/**
 *
 * @author omega
 */
public class ScrollBarCustom extends JScrollBar{
    public ScrollBarCustom() {
        setUI(new ModermScrollBarUI());
        setPreferredSize(new Dimension(8, 8));
        setForeground(new Color(198,177,201));
        setBackground(Color.WHITE);
    }
}
