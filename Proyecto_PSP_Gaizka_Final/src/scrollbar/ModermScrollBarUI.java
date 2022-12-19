/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scrollbar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 *
 * @author omega
 */
public class ModermScrollBarUI extends BasicScrollBarUI{
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle rectangle) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int size = rectangle.width / 2;
        int x = rectangle.x + ((rectangle.width - size) / 2);
        int y = rectangle.y;
        int width = size;
        int height = rectangle.height;
        g2.setColor(new Color(240,240,240));
        g2.fillRect(x, y, width, height);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle rectangle) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = rectangle.x;
        int y = rectangle.y;
        int width = rectangle.width;
        int height = rectangle.height;
        y += 8;
        height -= 16;
        g2.setColor(scrollbar.getForeground());
        g2.fillRoundRect(x, y, width, height, 10, 10);
    }
}
