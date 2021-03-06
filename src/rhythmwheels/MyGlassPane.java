package rhythmwheels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.MouseInputAdapter;

/**
 * We have to provide our own glass pane so that it can paint.
 */
public class MyGlassPane extends JComponent
{
    private Point point;
    private ImagePanel selectedSound;
    private Sound sound;

    @Override
    public void paint(Graphics g)
    {
        if (point != null && sound != null)
        {
            Graphics2D g2 = (Graphics2D) g;
            g2.translate(-selectedSound.sound.getWidth() / 2,
                         -selectedSound.sound.getHeight() / 2);
            point.x /= Sound.scaleFactor;
            point.y /= Sound.scaleFactor;
            sound.setPoint(point);
            sound.paint(g2);
        }
    }

    public void setPoint(Point p)
    {
        point = p;
    }

    /**
     * Returns the Point associated with this GlassPane.
     * @return the Point associated with this GlassPane.
     */
    public Point getPoint()
    {
        return point;
    }

    /**
     * Sets the Sound associated with this GlassPane
     * @param sound the new Sound to be associated with this GlassPane
     */
    public void setSound(Sound sound)
    {
        this.sound = sound;
    }

    public Sound getSound()
    {
        return sound;
    }

    public void setSelectedSound(ImagePanel p)
    {
        if (selectedSound != null)
        {
            selectedSound.removehilite();
            sound = (Sound) selectedSound.sound.clone();
        }
        selectedSound = p;

        if (p != null)
        {
            sound = (Sound) p.sound.clone();
            selectedSound.hilite();
        }
        else
        {
            sound = null;
        }
    }

    public ImagePanel getSelectedSound()
    {
        return selectedSound;
    }

    public MyGlassPane(Container contentPane, SoundPanel soundPanel)
    {
        Listener listener = new Listener(this, contentPane, soundPanel);
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }
}

class Listener extends MouseInputAdapter
{

    Toolkit toolkit;
    MyGlassPane glassPane;
    SoundPanel soundPanel;
    Container contentPane;
    boolean inDrag = false;

    public Listener(MyGlassPane glassPane, Container contentPane, SoundPanel sp)
    {
        toolkit = Toolkit.getDefaultToolkit();
        this.glassPane = glassPane;
        this.contentPane = contentPane;
        this.soundPanel = sp;
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        // If we're not over the sound panel and we're not dragging, disable
        boolean overImagePanel = false;
        boolean overSoundPanel = false;
        Point glassPanePoint = e.getPoint();
        Container container = contentPane;
        Point containerPoint = SwingUtilities.convertPoint(glassPane,
                                                           glassPanePoint, contentPane);
        Component component = SwingUtilities.getDeepestComponentAt(container,
                                                                   containerPoint.x, containerPoint.y);

        if (component instanceof ImagePanel)
        {
            overImagePanel = true;
            ImagePanel ip = (ImagePanel) component;
            if (ip != glassPane.getSelectedSound())
            {
                glassPane.setSelectedSound(ip);
            }
        }
        else
        {
            glassPane.setSelectedSound(null);
        }
        if (component != null && component instanceof SoundPanel)
        {
            overSoundPanel = true;
        }

        if (!overImagePanel && !inDrag)
        {
            glassPane.setSelectedSound(null);
            if (!overSoundPanel)
            {
                glassPane.setPoint(null);
                glassPane.repaint();
                // Needed to prevent excessive clicks
                glassPane.setVisible(false);
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        inDrag = true;
        Component component = getDeepestComponent(e.getPoint());

        if (component != null && component instanceof ImagePanel)
        {
            ImagePanel ip = (ImagePanel) component;
            ip.sound.play();
            glassPane.setPoint(e.getPoint());
            glassPane.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        glassPane.setPoint(e.getPoint());
        glassPane.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        // if we're not over the sound panel, disable
        inDrag = false;
        boolean overImagePanel = false;
        Component component = getDeepestComponent(e.getPoint());
        if (component != null && component instanceof ImagePanel)
        {
            overImagePanel = true;
        }
        if (!overImagePanel)
        {
            glassPane.setVisible(false);
        }
        if (component != null && component instanceof Wheel)
        {
            Wheel w = (Wheel) component;
            Point containerPoint = SwingUtilities.convertPoint(glassPane,
                                                               e.getPoint(),
                                                               component);
            w.drop(glassPane.getSound(),
                   new Point(containerPoint.x, containerPoint.y));
        }
        glassPane.setPoint(null);
        glassPane.repaint();
        if (glassPane.getSelectedSound() != null && !overImagePanel)
        {
            glassPane.getSelectedSound().removehilite();
        }
    }

    private Component getDeepestComponent(Point mousePoint)
    {
        Container container = contentPane;
        Point containerPoint = SwingUtilities.convertPoint(glassPane,
                                                           mousePoint,
                                                           contentPane);
        Component comp = SwingUtilities.getDeepestComponentAt(container,
                                                              containerPoint.x, containerPoint.y);
        return comp;
    }
}
