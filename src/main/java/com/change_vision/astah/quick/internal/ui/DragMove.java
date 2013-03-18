package com.change_vision.astah.quick.internal.ui;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DragMove implements MouseListener, MouseMotionListener {

    public static DragMove install(Component c) {
        DragMove x = new DragMove();
        c.addMouseListener(x);
        c.addMouseMotionListener(x);
        return x;
    }

    private Point start;

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        start = e.getPoint();
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        Point p = e.getLocationOnScreen();
        Component c = e.getComponent();
        c.setLocation((int) (p.getX() - start.getX()), (int) (p.getY() - start.getY()));
        c.repaint();
    }

    public void mouseMoved(MouseEvent e) {
    }
}