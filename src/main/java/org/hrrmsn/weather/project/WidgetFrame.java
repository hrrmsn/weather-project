package org.hrrmsn.weather.project;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author hrrmsn
 */
public class WidgetFrame extends JFrame {
    public WidgetFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                WidgetFrame.this.setVisible(false);
                WidgetFrame.this.dispose();
            }
        });
        
        //add slider to control frame opacity
        JSlider opacitySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
        add(opacitySlider);
        
        //set ticks
        opacitySlider.setMajorTickSpacing(10);
        opacitySlider.setMinorTickSpacing(1);
        opacitySlider.setPaintTicks(true);
        opacitySlider.setPaintLabels(true);
        
        opacitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSlider source = (JSlider)ce.getSource();
                int opacityValue = (int)source.getValue();
                WidgetFrame.this.setOpacity((float)opacityValue / 100);
            }
        });
    }
}
