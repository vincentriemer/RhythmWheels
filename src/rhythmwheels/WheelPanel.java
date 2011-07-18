package RhythmWheels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

// Class WheelPanel handles the spinning of the wheel.  Contains a NumberPanel
public class WheelPanel extends JPanel
{

    public Wheel wheel = new Wheel();
    public Color BACKGROUND_COLOR = RhythmWheel.BACKGROUND_COLOR;
    public Color FOREGROUND_COLOR = RhythmWheel.FOREGROUND_COLOR;
    public NumberPanel numPanel = new NumberPanel(wheel);
    public JTextField loopField = new JTextField("1", 3);
    public JLabel loopLabel = new JLabel("Loop:");
    public JPanel inputPanel = new JPanel();
    private JPanel top = new JPanel(new FlowLayout());

    public WheelPanel()
    {
        setLayout(new BorderLayout());
        top.add(numPanel);
        
        add(top, BorderLayout.NORTH);
        add(wheel, BorderLayout.CENTER);
        loopLabel.setForeground(Color.white);

        if (RhythmWheel.isLowRes())
        {
            Font current = loopLabel.getFont();
            loopLabel.setFont(new Font(current.getName(), Font.PLAIN, current.getSize() - 2));
            loopField.setFont(new Font(current.getName(), Font.PLAIN, current.getSize() - 2));
        }

        inputPanel.add(loopLabel);
        inputPanel.add(loopField);
        add(inputPanel, BorderLayout.SOUTH);
        changeColors();
    }

    /**
     * Sets the background and foreground color of all components in this panel, as well as the 
     * panel itself to BACKGROUND_COLOR and FOREGROUND_COLOR respectively.
     */
    private void changeColors()
    {
        RhythmWheel.changeComponent(this, BACKGROUND_COLOR, FOREGROUND_COLOR);
        RhythmWheel.changeComponent(top, BACKGROUND_COLOR, FOREGROUND_COLOR);
        RhythmWheel.changeComponent(numPanel, BACKGROUND_COLOR, FOREGROUND_COLOR);
        RhythmWheel.changeComponent(wheel, BACKGROUND_COLOR, FOREGROUND_COLOR);
        RhythmWheel.changeComponent(loopLabel, BACKGROUND_COLOR, FOREGROUND_COLOR);
        RhythmWheel.changeComponent(inputPanel, BACKGROUND_COLOR, FOREGROUND_COLOR);
        numPanel.changeColor(BACKGROUND_COLOR, FOREGROUND_COLOR);
    }

    /**
     * Gets the number of iterations of this wheel.
     * @return The number of iterations for this wheel, as shown in the Loop Field.
     */
    public int getIterations()
    {
        int wheelIterations = 0;
        try
        {
            wheelIterations = Integer.parseInt(loopField.getText());
        }
        catch (NumberFormatException e)
        {
            setIterations(0);
        }
        return wheelIterations;
    }
    
    /**
     * Sets the number of iterations for this wheel
     * @param numIterations The new number of iterations for this wheel, as is to be shown in the
     *                      Loop Field.
     */
    public void setIterations(int numIterations)
    {
        loopField.setText(Integer.toString(numIterations));
    }
}