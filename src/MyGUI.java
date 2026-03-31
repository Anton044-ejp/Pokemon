/* Lab 8 ArrayLists and HashMaps
   Create a new file/class for the data, like Pokemon.
*/

import javax.swing.*;     // JComponents
import java.awt.event.*;  // click events
import java.awt.*;        // colors
import java.util.*;       // for HashMap and ArrayList
import java.io.*;   // File, IOException, FileNotFoundException
import javax.imageio.*;   // ImageIO
import java.net.*;        // URL
import java.nio.file.*;
import java.net.HttpURLConnection;

public class MyGUI extends JFrame
{
    private ArrayList<Pokemon> pokeList = new ArrayList<>();
    private LinkedHashMap<String, Pokemon> pokemap = new LinkedHashMap<>();
	private JLabel imageLabel;
	private JLabel statsLabel;
	private JLabel resultLabel;
	private JLabel scoreLabel;
	private JButton resetButton;
	private JComboBox<String> nameComboBox;
	private int currentIndex = 0;
	private int rightAnswers = 0;
	private int totalAsked = 0;
	private boolean answered = false;

   
        
    // Constructor sets up GUI
    public MyGUI() throws IOException
    {
    	readFile();
    	
    	//=========title panel=========
    	
    	JPanel titlePanel = new JPanel();
    	getContentPane().add(titlePanel, BorderLayout.NORTH);
    	titlePanel.setLayout(new GridLayout(2, 2, 0, 0));
    	
    	resultLabel = new JLabel("Who is this Pokemon?");
    	resultLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
    	titlePanel.add(resultLabel);
    	
    	scoreLabel = new JLabel("# Correct / Total      :     0/0");
    	scoreLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
    	scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	titlePanel.add(scoreLabel);
    	
    	JPanel resetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	titlePanel.add(resetPanel);
    	resetButton = new JButton("Reset");
    	resetButton.setFocusPainted(false);
    	resetPanel.add(resetButton);
    	resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				currentIndex = 0;
				rightAnswers = 0;
				totalAsked = 0;
				answered = false;
				scoreLabel.setText("# Correct / Total      :     0/0");
				resultLabel.setText("Who is this Pokemon?");
				statsLabel.setVisible(false);
				setImage(pokeList.get(currentIndex));
				nameComboBox.setSelectedIndex(0);
			}
		});
    	resetButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
    	resetButton.setForeground(Color.RED);
    	resetButton.setPreferredSize(new Dimension(80, 25));
    	
    	JLabel lblNewLabel_1 = new JLabel(""); // spacer label
    	titlePanel.add(lblNewLabel_1);
    	
    	
    	
    	//=========button panel=========
    	
    	JPanel buttonPanel = new JPanel();
    	getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    	buttonPanel.setPreferredSize(new Dimension(600, 50));
    	buttonPanel.setLayout(new GridLayout(0, 3, 0, 0));
    	
    	// -->NEXT button
    	JButton nextButton = new JButton("NEXT IMAGE");
    	nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				statsLabel.setVisible(false);
				answered = false;
				resultLabel.setText("Who is this Pokemon?");
				currentIndex++;
		        if (currentIndex >= pokeList.size()) currentIndex = 0;
		        setImage(pokeList.get(currentIndex));
			}
		});
    	nextButton.setFont(new Font("Arial", Font.BOLD, 16));
    	nextButton.setPreferredSize(new Dimension(100, 200));
    	buttonPanel.add(nextButton);
    	
    	// -->names display
    	nameComboBox = new JComboBox<>(new Vector<>(pokemap.keySet()));
    	nameComboBox.setFont(new Font("Tahoma", Font.BOLD, 22));
    	buttonPanel.add(nameComboBox);
    	
    	
    	// -->ENTER button
    	JButton enterButton = new JButton("ENTER");
    	enterButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) 
    		{
    			if (answered) return; // prevent multiple answers for the same image
    			
    			answered = true;
    			totalAsked++;
    			String selectedName = (String) nameComboBox.getSelectedItem();
				String correct = pokeList.get(currentIndex).getName();
				if (selectedName.equals(correct))
					{
						resultLabel.setText("Correct!");
						statsLabel.setVisible(true);
						rightAnswers++;
					}
				else
					{
						resultLabel.setText("Incorrect! It was: ");
						statsLabel.setVisible(true);
					}
				scoreLabel.setText("# Correct / Total  : " + rightAnswers + "/" + totalAsked);
    		}
    	});
    	enterButton.setPreferredSize(new Dimension(100, 200));
    	enterButton.setFont(new Font("Arial", Font.BOLD, 16));
    	buttonPanel.add(enterButton);
    	
    	//=========end button panel=========

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
    	statsLabel.setVisible(false);
    	
    	
    	setTitle("Pokedex Quiz");
    	setSize(600, 400);
    	setLocationRelativeTo(null);  // centers it on screen
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    } // end constructor
    
    public void readFile() throws IOException
    {
        ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("pokemon.csv")));
        lines.remove(0); // remove header line
 
        for (String line : lines) 
        {
            String[] data = line.split(",");
            Pokemon p  = new Pokemon(data[1], data[2], data[7], data[8]);
            pokeList.add(p);
        }
        
        for (Pokemon p : pokeList) 
		{
			pokemap.put(p.getName(), p);
		}
        Collections.shuffle(pokeList); // randomize order of Pokemon
		
    } // end readFile

    public void setImage(Pokemon p) 
    {
        
        try 
        {
        	URI uri = URI.create(p.getImagefile());  
            URL url = uri.toURL(); 
            
            // open a connection and pretend to be a web browser (some sites block non-browser requests)
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            // read the image from that connection
            Image image = ImageIO.read(connection.getInputStream());
            if (image != null)
            {
            Image scaled = image.getScaledInstance(175, 200, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
            }
            else
            {
            	System.out.println("Image was null for: " + p.getImagefile());
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Could not load image: " + p.getImagefile());
        }

        statsLabel.setText("<html><center>" +
            "<h2>" + p.getName() + "</h2>" +
            "<p>Type: " + p.getType() + "</p>" +
            "<p>Speed: " + p.getSpeed() + "</p>" +
            "</center></html>");
    } // end setImage
    
    public static void main(String[] args) throws IOException
    {
         // call constructor to set up GUI
         MyGUI g = new MyGUI();
         g.setVisible(true);
         g.setImage(g.pokeList.get(0));
         
    }
}