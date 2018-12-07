package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import view.MainView;

public class SettingsController implements ActionListener {
	private MainView gameInterface;

	public SettingsController(MainView gameInterface) {
        this.gameInterface = gameInterface;
		gameInterface.addActionListenerSettingsController(this);
	}

    public void getConfig(File inputFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("board");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element: " + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    // TODO: Handle new config
                    System.out.println("Height: " + eElement.getElementsByTagName("height").item(0).getTextContent());
                    System.out.println("Width: " + eElement.getElementsByTagName("width").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveConfig(File outputFile, int configHeight, int configWidth) {
        try {
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.newDocument();

         // root element
         Element rootElement = doc.createElement("plants-vs-zombies");
         doc.appendChild(rootElement);

         // supercars element
         Element board = doc.createElement("board");
         rootElement.appendChild(board);

         // carname element
         Element height = doc.createElement("height");
         height.appendChild(doc.createTextNode(Integer.toString(configHeight)));
         board.appendChild(height);

         // carname element
         Element width = doc.createElement("width");
         width.appendChild(doc.createTextNode(Integer.toString(configWidth)));
         board.appendChild(width);

         // write the content into xml file
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();
         DOMSource source = new DOMSource(doc);
         StreamResult result = new StreamResult(outputFile);
         transformer.transform(source, result);

         // Output to console for testing
         StreamResult consoleResult = new StreamResult(System.out);
         transformer.transform(source, consoleResult);
      } catch (Exception e) {
         e.printStackTrace();
      }
    }

	public void actionPerformed(ActionEvent e) {
		/*
         * This action creates the game by making the game visible.
         * This also enables and disables some of the menu selections.
         */
		if(e.getSource() == gameInterface.getCreatePVZGame()) {
			gameInterface.getCreatePVZGame().setEnabled(false);
			gameInterface.getSavePVZGame().setEnabled(true);
			gameInterface.getCreateMap().setEnabled(true);
			gameInterface.getLoadMap().setEnabled(true);
			gameInterface.getContentPane().setVisible(true);
			System.out.println(e.getActionCommand());
		}

		if(e.getSource() == gameInterface.getQuitPVZGame()) {
			JOptionPane.showMessageDialog(gameInterface.getContentPane(),"Thanks for Playing! \nGoodBye!");
			System.exit(0);
		}

		if(e.getSource() == gameInterface.getCreateMap()) {

            String input_height = JOptionPane.showInputDialog("Please input custom map height: ");
            String input_width = JOptionPane.showInputDialog("Please input custom map width: ");
			int height, width;
			try {
				height = Integer.parseInt(input_height);
				width = Integer.parseInt(input_width);
			}
			catch(NumberFormatException err){
				System.out.println("Error parsing input");
				return;
			}

            JFileChooser fc = gameInterface.getFileChooser();
            int returnVal = fc.showSaveDialog(gameInterface);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                System.out.println("Saving file " + file.getName());
                // TODO: ask user for height and width
                saveConfig(file, height, width);
            } else {
                System.out.println("Open command cancelled by user.");
            }
		}

		if(e.getSource() == gameInterface.getLoadMap()) {
            JFileChooser fc = gameInterface.getFileChooser();
            int returnVal = fc.showOpenDialog(gameInterface);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                System.out.println("Opening: " + file.getName());
                getConfig(file);
            } else {
                System.out.println("Open command cancelled by user.");
            }
		}
	}
}
