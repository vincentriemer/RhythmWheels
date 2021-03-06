package rhythmwheels;

import java.applet.AudioClip;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.net.URL;
import java.io.IOException;

/**
 * 
 * @author Varun Madiath (vamega@gmail.com)
 */
public abstract class Sound implements Cloneable, Serializable
{

    protected final static BasicStroke mediumStroke = new BasicStroke(2.0f);
    protected static Color SOUND_COLOR = Color.cyan;
    protected Color backgroundColor = Color.black;
    protected static int SOUND_LENGTH = 250; // milliseconds
    public String strCurrentFileName;       // such as clap1
    protected AudioClip audioClip;          // such as clap
    public String strFileBaseName;
    
    protected static final int WIDTH = 50;
    protected static final int HEIGHT = 50;
    
    // Max number of volumes for each sound
    public static final int MAX_VOLUME = 3;
    // 1 is default - softest
    protected int volumeLevel = 1;
    
    protected Point p = new Point(0, 0);  // the top left point before rotation
    protected Point cp = new Point(0, 0); // Center point after rotation
    public int MAX_SOUND_SIZE = 100000;
    public static String SOUND_DIR = "sounds/";
    public static String EXTENSION = ".au";
    public static double scaleFactor = 1.0; // For low resolution screens

    public Sound(String fileName)
    {
        strFileBaseName = fileName;
        strCurrentFileName = SOUND_DIR + strFileBaseName + volumeLevel + EXTENSION;
        audioClip = getAudioClip();

    }

    /**
     * Returns the AudioClip associated with this Sound object.
     * @return The AudioClip associated with this Sound object.
     */
    public AudioClip getAudioClip()
    {
        AudioClip ac = null;
        URL u;
        ac = java.applet.Applet.newAudioClip(RhythmWheel.class.getResource(strCurrentFileName));
        return ac;
    }

    /**
     * Increments the volume level. If the volume is currently at the highest supported level, then
     * the volume is reset to 1.
     */
    public void cycleVolume()
    {
        if (!setVolume(volumeLevel + 1))
        {
            setVolume(1);
        }
        play();
    }

    /**
     * Sets the volume of this Sound. If the supplied volume is not in the range of [1, MAX_VOLUME]
     * then nothing is changed.
     * @param volume The new volume for the sound
     * @return true if the volume changed as a result of this call, false otherwise.
     */
    public boolean setVolume(int volume)
    {
        if (1 <= volume && volume <= MAX_VOLUME)
        {
            volumeLevel = volume;
            strCurrentFileName = SOUND_DIR + strFileBaseName + volumeLevel + EXTENSION;
            audioClip = getAudioClip();

            // Change the background color
            switch (volumeLevel)
            {
                case 2:
                    backgroundColor = Color.darkGray.darker().darker().darker();
                    break;
                case 3:
                    backgroundColor = Color.darkGray.darker();
                    break;
                default:
                    backgroundColor = Color.black;
            }
            return true;
        }
        else
        {
            return false;
        }

    }

    /**
     * Returns the volume of this Sound.
     * @return the volume of this Sound.
     */
    public int getVolume()
    {
        return volumeLevel;
    }
    
    /*
     * This method need to be implemented, otherwise it doesn't show up when dragging it off the 
     * sound panel onto the wheel.
     */
    public Object clone()
    {
        try
        {
            return super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Plays the AudioClip associated with this Sound.
     */
    public void play()
    {
        audioClip.play();
    }

    public void setPoint(Point point)
    {
        p = point;
    }

    public void setCenter(Point point)
    {
        cp = point;
    }

    /**
     * The current center of this sound.
     * @return The current center of this sound.
     */
    public Point getCenter()
    {
        return cp;
    }

    /**
     * Returns the width of Sound graphic.
     * @return the width of Sound graphic.
     */
    public static final int getWidth()
    {
        return WIDTH;
    }

    /**
     * Returns the width of Sound graphic.
     * @return the width of Sound graphic.
     */
    public static final int getHeight()
    {
        return HEIGHT;
    }

    /**
     * The filename of the sound associated with this Sound Object.
     * @return 
     */
    public String getStrFileBaseName()
    {
        return strFileBaseName;
    }

    /**
     * Draws the Triangle outline for the Sound object, and then calls the paintMe method
     * to paint the Sound specific graphic.
     * @param g The graphics object to draw the triangle onto.
     */
    public final void paint(Graphics g)
    {
        // Draws the black triangle
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        g2.scale(scaleFactor, scaleFactor);
        Point p1 = new Point(4 + p.x, 4 + p.y);
        Point p2 = new Point(WIDTH - 4 + p.x, 4 + p.y);
        Point p3 = new Point(WIDTH / 2 + p.x, HEIGHT - 4 + p.y);
        int xpts[] = new int[3];
        int ypts[] = new int[3];
        xpts[0] = p1.x;
        xpts[1] = p2.x;
        xpts[2] = p3.x;
        ypts[0] = p1.y;
        ypts[1] = p2.y;
        ypts[2] = p3.y;
        g2.setColor(backgroundColor);
        g2.fillPolygon(xpts, ypts, 3);
        g2.setColor(Color.blue);
        g2.setStroke(mediumStroke);
        Line2D top = new Line2D.Double(p1, p2);
        Line2D left = new Line2D.Double(p1, p3);
        Line2D right = new Line2D.Double(p2, p3);
        g2.draw(top);
        g2.draw(left);
        g2.draw(right);
        paintMe(g2);
        g2.scale(1.0 / scaleFactor, 1.0 / scaleFactor);

    }

    /**
     * This method should be overridden by Individual sound subclasses to draw themselves to the 
     * screen.
     * @param g The graphics object to draw the representation to.
     */
    public void paintMe(Graphics g)
    {
    }
}
