import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 

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

public class AppDrawEvent
{ 
    public static void main( String[] args ) 
    { 
	JFrame frame = new AppFrame("Allgemeines Programmierpraktikum"); 
	JPanel panel = new JPanel();  
	frame.add(panel);
	
	JPanel draw  = new AppDrawPanel();
	JLabel label = new JLabel("Doppelklicken zum Beenden"); 
	panel.add(draw);
	panel.add(label);
	
	AppMouseAdapter m = new AppMouseAdapter();
	label.addMouseListener(m); 
	
	frame.pack();
	frame.setVisible(true); 
    } 
}