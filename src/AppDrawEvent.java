import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;

//TODO: in der Klasse kann eigentlich alles private gemacht werden
//TODO: @Override an die mouseClicked Methoden der Klassen, die von MouseAdapter erben, schreiben
// -> guter Stil und verringert Fehleranfälligkeit

class AppFrame extends JFrame {
    public AppFrame(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class ExitAction extends MouseAdapter {
    public void mouseClicked(MouseEvent e) {
        System.exit(0);
    }
}

class Original extends MouseAdapter {

    private BufferedImage img, pic;
    private JLabel picLabel;

    Original(BufferedImage img, BufferedImage pic, JLabel picLabel) {
        this.img = img;
        this.pic = pic;
        this.picLabel = picLabel;
    }

    public void mouseClicked(MouseEvent e) {
        int width = img.getWidth();
        int height = img.getHeight();

        //get pixel value
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int p = img.getRGB(j, i);

                pic.setRGB(2 * j, 2 * i, p);
                pic.setRGB(2 * j, 2 * i + 1, p);
                pic.setRGB(2 * j + 1, 2 * i, p);
                pic.setRGB(2 * j + 1, 2 * i + 1, p);
            }
        }
        picLabel.setIcon(new ImageIcon(pic));
    }
}

class GrayScale extends MouseAdapter {

    private BufferedImage img, pic;
    private JLabel picLabel;

    GrayScale(BufferedImage img, BufferedImage pic, JLabel picLabel) {
        this.img = img;
        this.pic = pic;
        this.picLabel = picLabel;
    }

    public void mouseClicked(MouseEvent e) {
        int width = img.getWidth();
        int height = img.getHeight();

        //get pixel value
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int p = img.getRGB(j, i);

                //get red
                int r = (p >> 16) & 0xff;

                //get green
                int g = (p >> 8) & 0xff;

                //get blue
                int b = p & 0xff;

                int mix = (r + g + b) / 3;

                p = (mix << 16) | (mix << 8) | mix;

                //set the pixel value
                pic.setRGB(2 * j, 2 * i, p);
                pic.setRGB(2 * j, 2 * i + 1, p);
                pic.setRGB(2 * j + 1, 2 * i, p);
                pic.setRGB(2 * j + 1, 2 * i + 1, p);
            }
        }
        picLabel.setIcon(new ImageIcon(pic));
    }

}

class Pattern extends MouseAdapter {

    private BufferedImage img, pic;
    private JLabel picLabel;

    Pattern(BufferedImage img, BufferedImage pic, JLabel picLabel) {
        this.img = img;
        this.pic = pic;
        this.picLabel = picLabel;
    }

    public void mouseClicked(MouseEvent e) {
        int width = img.getWidth();
        int height = img.getHeight();

        //highest rgb
        int v = 0;
        //get pixel value
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int p = img.getRGB(j, i);

                //get red
                int r = (p >> 16) & 0xff;
                if (r > v) v = r;
                //get green
                int g = (p >> 8) & 0xff;
                if (g > v) v = g;

                //get blue
                int b = p & 0xff;
                if (b > v) v = b;
            }
        }
        v/=5;
        int r = 255;
        int g = 255;
        int b = 255;
        int wh = (r << 16) | (g << 8) | b;
        r = 0;
        g = 0;
        b = 0;
        int bl = (r << 16) | (g << 8) | b;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int p = img.getRGB(j, i);

                //get red
                r = (p >> 16) & 0xff;
                //get green
                g = (p >> 8) & 0xff;

                //get blue
                b = p & 0xff;

                int mix = (r + g + b) / 3;

                //set the pixel value
                if (mix > (4 * v)) {
                    pic.setRGB(2 * j, 2 * i, wh);
                    pic.setRGB(2 * j, 2 * i + 1, wh);
                    pic.setRGB(2 * j + 1, 2 * i, wh);
                    pic.setRGB(2 * j + 1, 2 * i + 1, wh);
                } else if (mix > (3 * v)) {
                    pic.setRGB(2 * j, 2 * i, bl);
                    pic.setRGB(2 * j, 2 * i + 1, wh);
                    pic.setRGB(2 * j + 1, 2 * i, wh);
                    pic.setRGB(2 * j + 1, 2 * i + 1, wh);
                } else if (mix > (2 * v)) {
                    pic.setRGB(2 * j, 2 * i, bl);
                    pic.setRGB(2 * j, 2 * i + 1, wh);
                    pic.setRGB(2 * j + 1, 2 * i, wh);
                    pic.setRGB(2 * j + 1, 2 * i + 1, bl);
                } else if (mix > v) {
                    pic.setRGB(2 * j, 2 * i, wh);
                    pic.setRGB(2 * j, 2 * i + 1, bl);
                    pic.setRGB(2 * j + 1, 2 * i, bl);
                    pic.setRGB(2 * j + 1, 2 * i + 1, bl);
                } else {
                    pic.setRGB(2 * j, 2 * i, bl);
                    pic.setRGB(2 * j, 2 * i + 1, bl);
                    pic.setRGB(2 * j + 1, 2 * i, bl);
                    pic.setRGB(2 * j + 1, 2 * i + 1, bl);
                }
            }
        }
        picLabel.setIcon(new ImageIcon(pic));
    }

}


public class AppDrawEvent {

    public static void main(String[] args) {

        String path = args[0];
        BufferedImage img;
        //Der Try Block sollte wenn möglich nur den Code beinhalten, der den Fehler verursachen könnte
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            String workingDir = System.getProperty("user.dir");
            System.out.println(path + " Datei mit diesem Namen nicht gefunden,\n gesucht wird in diesem Verzeichnis : " + workingDir + "\n auch an die Dateiendung denken");
            return;
        }

        ImageIcon icon = new ImageIcon(img);
        icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth() * 2, icon.getIconHeight() * 2, Image.SCALE_DEFAULT));
        BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        // paint the Icon to the BufferedImage.
        icon.paintIcon(null, g, 0, 0);
        g.dispose();

        JLabel picLabel = new JLabel(new ImageIcon(bi));

        JFrame frame = new AppFrame("APP - Übung 5");
        JPanel butpanel = new JPanel(new BorderLayout());
        JPanel picpanel = new JPanel(new BorderLayout());
        JPanel exitpanel = new JPanel(new BorderLayout());
        frame.add(butpanel, BorderLayout.NORTH);
        frame.add(picpanel, BorderLayout.CENTER);
        frame.add(exitpanel, BorderLayout.SOUTH);

        JButton oButton = new JButton("Original");
        JButton gsButton = new JButton("Grayscale");
        JButton pButton = new JButton("Pattern");

        butpanel.add(oButton, BorderLayout.WEST);
        butpanel.add(gsButton, BorderLayout.CENTER);
        butpanel.add(pButton, BorderLayout.EAST);

        Label name = new Label(path);

        picpanel.add(name, BorderLayout.CENTER);
        picpanel.add(picLabel, BorderLayout.SOUTH);

        JButton exit = new JButton("Ende");
        exitpanel.add(exit, BorderLayout.EAST);


        ExitAction m = new ExitAction();
        exit.addMouseListener(m);

        Original n = new Original(img, bi, picLabel);
        oButton.addMouseListener(n);

        GrayScale l = new GrayScale(img, bi, picLabel);
        gsButton.addMouseListener(l);

        Pattern p = new Pattern(img, bi, picLabel);
        pButton.addMouseListener(p);

        frame.pack();
        frame.setVisible(true);
    }

}