import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;

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
        g.drawLine(10, 10, 490, 190);
        g.drawString("Ein einfaches Panel", 50, 100);
    }
}

class ExitAction extends MouseAdapter {
    public void mouseClicked(MouseEvent e) {
        System.exit(0);
    }
}

class Original extends MouseAdapter {
    public void mouseClicked(MouseEvent e) {

    }
}

class grey extends MouseAdapter {
    public BufferedImage img;
    String path;
    JLabel pic;

    grey(BufferedImage img, String path, JLabel pic){
        this.img=img;
        this.path=path;
        this.pic=pic;
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
                img.setRGB(j, i, p);
                pic = new JLabel(new ImageIcon(img));
            }
        }
    }
}

public class AppDrawEvent {
    public static BufferedImage img;

    public static void main(String[] args) {

        String path = args[0];

        try {
            img = ImageIO.read(new File(path));
            JLabel picLabel = new JLabel(new ImageIcon(img));


            JFrame frame = new AppFrame("APP - Übung 5");
            JPanel butpanel = new JPanel(new BorderLayout());
            JPanel picpanel = new JPanel(new BorderLayout());
            JPanel exitpanel = new JPanel(new BorderLayout());
            frame.add(butpanel, BorderLayout.NORTH);
            frame.add(picpanel, BorderLayout.CENTER);
            frame.add(exitpanel, BorderLayout.SOUTH);

            JButton original = new JButton("Original");
            JButton gray = new JButton("Grayscale");
            JButton pattern = new JButton("Pattern");

            butpanel.add(original, BorderLayout.WEST);
            butpanel.add(gray, BorderLayout.CENTER);
            butpanel.add(pattern, BorderLayout.EAST);

            Label name = new Label(path);

            picpanel.add(name, BorderLayout.CENTER);
            picpanel.add(picLabel, BorderLayout.SOUTH);

            JButton exit = new JButton("Ende");
            exitpanel.add(exit, BorderLayout.EAST);


            ExitAction m = new ExitAction();
            exit.addMouseListener(m);

            Original n = new Original();
            original.addMouseListener(n);

            grey l = new grey(img, path, picLabel);
            gray.addMouseListener(l);
            picLabel=l.pic;


            frame.pack();
            frame.setVisible(true);

        } catch (IOException e) {
            String workingDir = System.getProperty("user.dir");
            System.out.println(path + " Datei mit diesem Namen nicht gefunden,\n gesucht wird in diesem Verzeichnis : " + workingDir + "\n auch an die Dateiendung denken");
        }


    }
}