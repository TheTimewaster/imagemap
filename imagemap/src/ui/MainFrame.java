package ui;

import generator.HtmlGenerator;
import generator.HtmlGeneratorImpl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame implements ActionListener, ChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2574970292616605849L;
	// Menubar
	JMenuBar bar = new JMenuBar();
	// Menu File
	JMenu fileMenu = new JMenu("File");
	JMenuItem openImage = new JMenuItem("Open Image");
	JMenuItem openItem = new JMenuItem("Open");
	JMenuItem saveItem = new JMenuItem("Save");
	JMenuItem closeItem = new JMenuItem("Close");
	JMenuItem endItem = new JMenuItem("Quit");
	// Menu Settings
	JMenu settingsMenu = new JMenu("Settings");
	JMenuItem strokeColor =  new JMenuItem("Stroke Color");
	JMenuItem fillColor =  new JMenuItem("Fill Color");
	JCheckBox fillCBox = new JCheckBox("Fill");
	// Menu Paint
	JMenu paintMenu = new JMenu("Paint");
	CheckboxMenuItem fillItem = new CheckboxMenuItem("Fill", false);
	String[] str = { "Rectangle", "Oval", "Polygon" };
	JRadioButtonMenuItem[] pItem = new JRadioButtonMenuItem[str.length];
	ButtonGroup bg = new ButtonGroup();
	// Menu Help
	JMenu helpMenu = new JMenu("Help");
	JMenuItem infoItem = new JMenuItem("About");
	//strokeSlider
	JSlider strokeSlider = new JSlider(JSlider.HORIZONTAL, 1 , 10 , 5);
	//JFileChooser
	JFileChooser fChooser = new JFileChooser();
	//JTextField
	JTextArea tArea = new JTextArea(5,5);
	//Generate Button
	JButton generateButton = new JButton("Generate");
	//Table
	String[] columns = {"Shape", "Dimensions" , "href", "alt"};
	DefaultTableModel model = new DefaultTableModel(5, columns.length);
	public JTable table = new JTable();
	
	private SelectedItem selectedItem;
	private File img;

	JPanel cp;
	PaintPanel pPanel = new PaintPanel(this);
	JScrollPane tFScrollpane = new JScrollPane(tArea);
	JScrollPane tBScrollPane = new JScrollPane(table);
	
	public MainFrame() {
		initMenu();
		this.setJMenuBar(bar);
		registerListener();
		
		cp = (JPanel) this.getContentPane();
		cp.setLayout(new BorderLayout());
		pPanel.setBackground(Color.white);
		cp.add(pPanel, BorderLayout.CENTER);
		cp.add(tFScrollpane, BorderLayout.PAGE_END);
		cp.add(tBScrollPane, BorderLayout.EAST);
	}

	private void registerListener() {
		openImage.addActionListener(this);
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		endItem.addActionListener(this);
		fillColor.addActionListener(this);
		strokeColor.addActionListener(this);
		fillCBox.addActionListener(this);
		for (int i = 0; i < 3; i++ ){
			pItem[i].addActionListener(this);
		}
		
		strokeSlider.addChangeListener(this);
		generateButton.addActionListener(this);
	}

	public void initMenu() {
		fileMenu.add(openImage);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(closeItem);
		fileMenu.addSeparator();
		fileMenu.add(endItem);
		bar.add(fileMenu);
		
		settingsMenu.add(strokeColor);
		settingsMenu.add(fillColor);
		settingsMenu.addSeparator();
		settingsMenu.add(fillCBox);
		settingsMenu.addSeparator();
		settingsMenu.add(strokeSlider);
		
		strokeSlider.setMajorTickSpacing(5);
		strokeSlider.setMinorTickSpacing(1);
		strokeSlider.setPaintTicks(true);
		strokeSlider.setPaintLabels(true);
		
		bar.add(settingsMenu);
		
		bar.add(paintMenu);
		
		for (int i = 0; i < str.length; i++) {
			pItem[i] = new JRadioButtonMenuItem(str[i]);
			paintMenu.add(pItem[i]);
			bg.add(pItem[i]);
		}
		
		fillCBox.setSelected(false);
		pItem[2].setSelected(true);
		
		bar.add(helpMenu);
		
		bar.add(generateButton);
		
		pPanel.setColor1(new Color(0,0,0,0));
		pPanel.setColor2(Color.BLACK);
		pPanel.setStroke(5);
		//fChooser.setFileFilter(new FileNameExtensionFilter("*.shapes"));
		
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (e.getActionCommand()) {
		case "Quit":
			if(
			JOptionPane.showConfirmDialog(this, "Are you sure?",
					"Sicherheitsabfrage",JOptionPane.YES_NO_OPTION)
					
					== JOptionPane.YES_OPTION	
			)
			System.out.println("End");
			System.exit(0);
			break;
			
		case "Stroke Color":
			pPanel.setColor2(JColorChooser.showDialog(this, "Stroke Color", Color.black));
			break;
			
		case "Fill Color":
			if (fillCBox.isSelected() == true){
				pPanel.setColor1(JColorChooser.showDialog(settingsMenu, "Fill Color", Color.white));
			}
			break;
			
		case "Open Image":
			int state0 = fChooser.showOpenDialog(this);
			if(state0 == JFileChooser.APPROVE_OPTION){				
				img = fChooser.getSelectedFile();
				pPanel.setImage(img);
				pPanel.openImage();
			}
			break;
			
		case "Open":
			int state1 = fChooser.showOpenDialog(this);
			if (state1 == JFileChooser.APPROVE_OPTION){
				pPanel.setFile(fChooser.getSelectedFile());
				try {
					pPanel.openFile();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			break;
		
		case "Save":
			int state2 = fChooser.showSaveDialog(this);
			if (state2 == JFileChooser.APPROVE_OPTION){
				pPanel.setFile(fChooser.getSelectedFile());
				pPanel.saveFile();
			}
			break;
			
		case "Generate":
			HtmlGenerator generator = new HtmlGeneratorImpl();
			if(img == null){
				JOptionPane.showMessageDialog(this, "No image loaded! Cannot generate imagemap!", "No Image", JOptionPane.ERROR_MESSAGE);
			}else{
				tArea.setText(generator.generateHTMLCode(img, pPanel.paintList));
			}				
		}
		
		if(fillCBox.isSelected() == false){
			pPanel.setColor1(new Color(0,0,0,0));
			fillColor.setEnabled(false);
		}else{
			fillColor.setEnabled(true);
		}
		
		if (pItem[0].isSelected()){
			pPanel.setsItem(SelectedItem.rect);
			pPanel.setStroke(strokeSlider.getValue());
		}
		
		if (pItem[1].isSelected()){
			pPanel.setsItem(SelectedItem.oval);
			pPanel.setStroke(strokeSlider.getValue());
		}
		
		if (pItem[2].isSelected()){
			pPanel.setsItem(SelectedItem.polygon);
			pPanel.setStroke(strokeSlider.getValue());
		}
	
	}
	

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		int s = strokeSlider.getValue();
		pPanel.setStroke(s);
	}
	
	public void itemDrawn(SelectedItem item){
		selectedItem = item;
		if(selectedItem == SelectedItem.rect){
			model.addRow(new Object[]{"Rectangle"});
		}
	}

}
