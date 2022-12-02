import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

public class Rotation
{
    BufferedImage image;
    JLabel label;

    public Rotation(BufferedImage image)
    {
        this.image = image;
    }

    private BufferedImage getImage(double theta)
    {
        //  Determine the size of the rotated image

        double cos = Math.abs(Math.cos(theta));
        double sin = Math.abs(Math.sin(theta));
        double width  = image.getWidth();
        double height = image.getHeight();
        int w = (int)(width * cos + height * sin);
        int h = (int)(width * sin + height * cos);

        //  Rotate and paint the original image onto a BufferedImage

        BufferedImage out = new BufferedImage(w, h, image.getType());
        Graphics2D g2 = out.createGraphics();
        g2.setPaint(UIManager.getColor("Panel.background"));
        g2.fillRect(0,0,w,h);
        double x = w/2;
        double y = h/2;
        AffineTransform at = AffineTransform.getRotateInstance(theta, x, y);
        x = (w - width)/2;
        y = (h - height)/2;
        at.translate(x, y);
        g2.drawRenderedImage(image, at);
        g2.dispose();
        return out;
    }

    private JLabel getLabel()
    {
        ImageIcon icon = new ImageIcon(image);
        label = new JLabel(icon);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private JSlider getSlider()
    {
        final JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        slider.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                int value = slider.getValue();
                BufferedImage bi = getImage(Math.toRadians(value));
                label.setIcon(new ImageIcon(bi));
            }
        });
        return slider;
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    String path = "flags.jpg";
                    ClassLoader cl = Rotation.class.getClassLoader();
                    BufferedImage bi = ImageIO.read(cl.getResourceAsStream(path));
                    Rotation r = new Rotation(bi);
                    JFrame f = new JFrame();
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    f.getContentPane().add(new JScrollPane(r.getLabel()));
                    f.getContentPane().add(r.getSlider(), "South");
                    f.pack();
                    f.setLocation(200,200);
                    f.setVisible(true);
                }
                catch(IOException e)
                {
                    System.out.println(e);
                }
            }
        });
    }
}