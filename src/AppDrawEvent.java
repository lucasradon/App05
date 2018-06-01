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

class AppDrawPanel extends JPanel {
    public Dimension getPreferredSize() {
        return new Dimension(500, 200);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(0,0,0,0);
    }
}

class ExitAction extends MouseAdapter {
    public void mouseClicked(MouseEvent e) {
        System.exit(0);
    }
}

class Original extends MouseAdapter {

    public BufferedImage img, pic;

    Original(BufferedImage img, BufferedImage pic){
        this.img=img;
        this.pic=pic;
    }

    public void mouseClicked(MouseEvent e) {
        int width = img.getWidth();
        int height = img.getHeight();

        //get pixel value
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int p = img.getRGB(j, i);

                pic.setRGB(2*j,2*i,p);
                pic.setRGB(2*j,2*i+1,p);
                pic.setRGB(2*j+1, 2*i,p);
                pic.setRGB(2*j+1, 2*i+1, p);
            }
        }
        //TODO: variablen namen immer klein
        JFrame O = new JFrame("Original");
        JLabel test = new JLabel(new ImageIcon(pic));
        O.add(test);
        O.pack();
        O.setVisible(true);
    }
}

//TODO: Klassen immer groß schreiben
class grayScale extends MouseAdapter {

    BufferedImage img, pic;
    JLabel picLabel;

    grayScale(BufferedImage img, BufferedImage pic, JLabel picLabel){
        this.img=img;
        this.pic=pic;
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

                int mix =(r+g+b)/3;

                p =  (mix << 16) | (mix << 8) | mix;

                //set the pixel value
                pic.setRGB(2*j, 2*i, p);
                pic.setRGB(2*j,2*i+1,p);
                pic.setRGB(2*j+1, 2*i,p);
                pic.setRGB(2*j+1, 2*i+1, p);
            }
        }
        //NEU
        picLabel.setIcon(new ImageIcon(pic));
        //das war das was du wolltest oder? Die größe stimmt nicht

        //TODO: variablen namen immer klein
        JFrame O = new JFrame("Grayscale");
        JLabel test = new JLabel(new ImageIcon(pic));
        O.add(test);
        O.pack();
        O.setVisible(true);
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
        icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth()*2, icon.getIconHeight()*2, Image.SCALE_DEFAULT));
        BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        // paint the Icon to the BufferedImage.
        icon.paintIcon(null, g, 0,0);
        g.dispose();

        JLabel picLabel = new JLabel(new ImageIcon(img));

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

        Original n = new Original(img, bi);
        oButton.addMouseListener(n);

        grayScale l = new grayScale(img, bi, picLabel);
        gsButton.addMouseListener(l);

        frame.pack();
        frame.setVisible(true);
    }

}