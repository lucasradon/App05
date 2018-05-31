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

class AppMouseAdapter extends MouseAdapter {
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() > 1)
            System.exit(0);
    }
}

public class AppDrawEvent {
    public static void main(String[] args) {

        String pic = args[0];

        try {
            BufferedImage myPicture = ImageIO.read(new File(pic));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));


            JFrame frame = new AppFrame("APP - Übung 5");
            JPanel butpanel = new JPanel();
            JPanel picpanel = new JPanel();
            JPanel exitpanel = new JPanel();
            frame.add(butpanel, BorderLayout.NORTH);
            frame.add(picpanel, BorderLayout.CENTER);
            frame.add(exitpanel, BorderLayout.SOUTH);

            JButton original = new JButton("Original");
            JButton gray = new JButton("Grayscale");
            JButton pattern = new JButton("Pattern");

            butpanel.add(original);
            butpanel.add(gray);
            butpanel.add(pattern);

            Label name = new Label(pic);

            picpanel.add(name);
            picpanel.add(picLabel, JLabel.CENTER);

            JButton exit = new JButton("Ende");
            exitpanel.add(exit,BorderLayout.EAST);

        /*
        JPanel draw = new AppDrawPanel();
        JLabel label = new JLabel("Doppelklicken zum Beenden");
        panel.add(draw);
        panel.add(label);

        AppMouseAdapter m = new AppMouseAdapter();
        label.addMouseListener(m);
        */

            frame.pack();
            frame.setVisible(true);
        } catch (IOException e) {
            String workingDir = System.getProperty("user.dir");
            System.out.println(pic + " Datei mit diesem Namen nicht gefunden,\n gesucht wird in diesem Verzeichnis : " + workingDir + "\n auch an die Dateiendung denken");
        }


    }
}