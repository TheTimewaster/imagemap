package ui;


import generator.GeneratorElement;
import generator.HtmlGenerator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


public class MainFrame extends JFrame implements ActionListener {
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
	JMenuItem resetItem = new JMenuItem("Reset");
	JMenuItem endItem = new JMenuItem("Quit");
	// Menu Paint
	JMenu paintMenu = new JMenu("Paint");
	CheckboxMenuItem fillItem = new CheckboxMenuItem("Fill", false);
	String[] str = { "Rectangle", "Oval", "Polygon" };
	JRadioButtonMenuItem[] pItem = new JRadioButtonMenuItem[str.length];
	ButtonGroup bg = new ButtonGroup();
	// Menu Help
	JMenu helpMenu = new JMenu("Help");
	JMenuItem infoItem = new JMenuItem("About");
	// JFileChooser
	JFileChooser fChooser = new JFileChooser("../../resources");
	// JTextField
	JTextArea tArea = new JTextArea(5, 5);
	// Generate Button
	JButton generateButton = new JButton("Generate");
	// Delete Button
	JButton deleteButton = new JButton("Delete Entry");
	// Table
	String[] columns = { "SHAPE", "DIMENSIONS", "HREF", "ALT", "TITLE"};
	DefaultTableModel model = new DefaultTableModel(0, columns.length);
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
		resetItem.addActionListener(this);
		endItem.addActionListener(this);
		for (int i = 0; i < 3; i++) {
			pItem[i].addActionListener(this);
		}

		generateButton.addActionListener(this);
		
		deleteButton.addActionListener(this);
		
	}

	public void initMenu() {
		fileMenu.add(openImage);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(resetItem);
		fileMenu.addSeparator();
		fileMenu.add(endItem);
		bar.add(fileMenu);
		bar.add(paintMenu);

		for (int i = 0; i < str.length; i++) {
			pItem[i] = new JRadioButtonMenuItem(str[i]);
			paintMenu.add(pItem[i]);
			bg.add(pItem[i]);
		}

		pItem[1].setSelected(true);
		pPanel.setsItem(SelectedItem.oval);

		bar.add(helpMenu);

		bar.add(generateButton);
		
		bar.add(deleteButton);

		model.setColumnIdentifiers(columns);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		FileFilter filter = new FileNameExtensionFilter("Bilder", "gif", "png",
				"jpg");
		fChooser.addChoosableFileFilter(filter);

		pPanel.paintElement();
		
		this.markElementinTable();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (e.getActionCommand()) {
		case "Quit":
			if (JOptionPane.showConfirmDialog(this, "Are you sure?",
					"Sicherheitsabfrage", JOptionPane.YES_NO_OPTION)

			== JOptionPane.YES_OPTION)
				System.out.println("End");
			System.exit(0);
			break;

		case "Open Image":
			int state0 = fChooser.showOpenDialog(this);
			if (state0 == JFileChooser.APPROVE_OPTION) {
				img = fChooser.getSelectedFile();
				pPanel.setImage(img);
				pPanel.openImage();
			}
			break;

		case "Open":
			int state1 = fChooser.showOpenDialog(this);
			if (state1 == JFileChooser.APPROVE_OPTION) {
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
			if (state2 == JFileChooser.APPROVE_OPTION) {
				pPanel.setFile(fChooser.getSelectedFile());
				pPanel.saveFile();
			}
			break;

		case "Reset":
			pPanel.emptyList();
			model.setRowCount(0);
			tArea.setText("");
			break;

		case "Generate":
			HtmlGenerator generator = new HtmlGenerator();

			ArrayList<GeneratorElement> gElementList = new ArrayList<GeneratorElement>();

			for (int count = 0; count < model.getRowCount(); count++) {
				GeneratorElement gElement = new GeneratorElement(pPanel
						.getPaintList().get(count), pPanel.getPaintList()
						.get(count).getCoords(), model.getValueAt(count, 2)
						.toString(), model.getValueAt(count, 3).toString(),
						model.getValueAt(count, 4).toString());
				gElementList.add(gElement);
			}

			if (img == null) {
				JOptionPane.showMessageDialog(this,
						"No image loaded! Cannot generate imagemap!",
						"No Image", JOptionPane.ERROR_MESSAGE);
			} else {
				tArea.setText(generator.generateHTMLCode(img, gElementList));
			}
			break;
			
		case "Delete Entry":
			int rowIndex = table.getSelectedRow();
			if(rowIndex >= 0){
				model.removeRow(rowIndex);
				pPanel.removeElement(rowIndex);
			}else{
				JOptionPane.showMessageDialog(this,
						"No table row selected! Cannot remove!",
						"No table row selected", JOptionPane.ERROR_MESSAGE);
			}
		}

		if (pItem[0].isSelected()) {
			pPanel.setsItem(SelectedItem.rect);
		} else {
			if (pItem[1].isSelected()) {
				pPanel.setsItem(SelectedItem.oval);
			} else {
				if (pItem[2].isSelected()) {
					pPanel.setsItem(SelectedItem.polygon);
				}
			}
		}

	}
	
	public void markElementinTable(){
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if(table.getSelectedRow() >= 0){
					pPanel.markElementInPPanel(table.getSelectedRow());
				}						
			}
		});
	}

	public void itemDrawn(SelectedItem item) {
		selectedItem = item;
		String dimString = "";
		ArrayList<Integer> coords = pPanel.getPaintList()
				.get(pPanel.getPaintList().size() - 1).getCoords();
		for (int i = 0; i < coords.size(); i++) {
			dimString += coords.get(i);
			if (i < coords.size() - 1) {
				dimString += ",";
			}
		}

		switch (selectedItem) {
		case oval:
			model.addRow(new Object[] { "Oval", dimString, "", "", "", "Delete" });
			break;
		case rect:
			model.addRow(new Object[] { "Rectangle", dimString, "", "", "",
					"Delete" });
			break;
		case polygon:
			model.addRow(new Object[] { "Polygon", dimString, "", "", "",
					"Delete" });
		}
	}

}

