package uk.co.hobnobian.chips.editor;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class EditorLayout implements LayoutManager {

    @Override
    public void addLayoutComponent(String name, Component comp) {
        System.out.println(comp);
        
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void layoutContainer(Container parent) {
        // TODO Auto-generated method stub
        
    }

}
