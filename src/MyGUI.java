/* Lab 7 Arrays and Data Files
   Create a new file/class for the data, like Pokemon.
   Use the following for the GUI.
*/

import javax.swing.*;     // JComponents
import java.awt.event.*;  // click events
import java.awt.*;        // colors
import java.util.*;       // for input
import java.io.*;   // File, IOException, FileNotFoundException
import javax.imageio.*;   // ImageIO
import java.net.*;        // URL

public class MyGUI extends JFrame
{
    private Pokemon[] pokerray = new Pokemon[200];
    private int currentIndex = 0;
    private Scanner scan = new Scanner(new File("pokemon.csv"));
	private JLabel imageLabel;
	private JLabel statsLabel;
   
        
    // Constructor sets up GUI
    public MyGUI() throws IOException
    {
    	readFile();
    	JPanel titlePanel = new JPanel();
    	getContentPane().add(titlePanel, BorderLayout.NORTH);
    	
    	JLabel lblNewLabel = new JLabel("Pokedex");
    	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
    	titlePanel.add(lblNewLabel);
    	
    	JPanel buttonPanel = new JPanel();
    	getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    	
    	JButton nextButton = new JButton("NEXT");
    	nextButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) 
    		{
    			currentIndex++;
    	        if (currentIndex >= 151) currentIndex = 0;
    	        setImage();
    		}
    	});
    	buttonPanel.add(nextButton);

    	JPanel imagePanel = new JPanel();
    	getContentPane().add(imagePanel, BorderLayout.CENTER);
    	GridBagLayout gbl_imagePanel = new GridBagLayout();
    	gbl_imagePanel.columnWidths = new int[]{168, 100, 0, 0};
    	gbl_imagePanel.rowHeights = new int[]{100, 0, 0};
    	gbl_imagePanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
    	gbl_imagePanel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
    	imagePanel.setLayout(gbl_imagePanel);
    	
    	imageLabel = new JLabel("");
    	imageLabel.setHorizontalAlignment(SwingConstants.LEFT);
    	imageLabel.setPreferredSize(new Dimension(175, 200));
    	imageLabel.setMaximumSize(new Dimension(300, 300));
    	imageLabel.setMinimumSize(new Dimension(50, 50));
    	GridBagConstraints gbc_imageLabel = new GridBagConstraints();
    	gbc_imageLabel.insets = new Insets(0, 0, 0, 5);
    	gbc_imageLabel.gridheight = 2;
    	gbc_imageLabel.fill = GridBagConstraints.VERTICAL;
    	gbc_imageLabel.gridwidth = 2;
    	gbc_imageLabel.anchor = GridBagConstraints.CENTER;
    	gbc_imageLabel.gridx = 0;
    	gbc_imageLabel.gridy = 0;
    	imagePanel.add(imageLabel, gbc_imageLabel);
    	
    	statsLabel = new JLabel();
    	statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	statsLabel.setVerticalAlignment(SwingConstants.CENTER);
    	GridBagConstraints gbc_statsLabel = new GridBagConstraints();
    	gbc_statsLabel.insets = new Insets(0, 0, 5, 0);
    	gbc_statsLabel.fill = GridBagConstraints.BOTH;
    	gbc_statsLabel.gridx = 2;
    	gbc_statsLabel.gridy = 0;
    	imagePanel.add(statsLabel, gbc_statsLabel);
    	
    	
    	setTitle("Pokemon Deck");
    	setSize(600, 400);
    	setLocationRelativeTo(null);  // centers it on screen
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
        
        // set up GUI
        
    }
    
    public void readFile() throws IOException
    {
        int i = 0;
        while (scan.hasNext() && i < pokerray.length)
        {
            String line = scan.nextLine();
            if (line.startsWith("Number"))
                continue;
            String[] data = line.split(",");
            String name = data[1];
            String type = data[2];
            String speed = data[7];
            String imagefile = data[8];
            
            Pokemon p = new Pokemon(name, type, speed, imagefile);
            pokerray[i] = p;
            
            i++;
        }

    }

    public void setImage() {
        Pokemon p = pokerray[currentIndex];
        
        try {
            URL url = new URL(p.getImagefile());
            Image image = ImageIO.read(url);
            Image scaled = image.getScaledInstance(175, 200, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } catch (IOException e) {
            System.out.println("Could not load image: " + p.getImagefile());
        }
        
        // Show stats
        statsLabel.setText("<html><center>" +
            "<h2>" + p.getName() + "</h2>" +
            "<p>Type: " + p.getType() + "</p>" +
            "<p>Speed: " + p.getSpeed() + "</p>" +
            "</center></html>");
    }
    
    public static void main(String[] args) throws IOException
    {
         // call constructor to set up GUI
         MyGUI g = new MyGUI();
         g.setVisible(true);
         g.setImage();
         
    }
}