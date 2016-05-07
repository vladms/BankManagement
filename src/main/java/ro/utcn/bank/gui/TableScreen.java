package ro.utcn.bank.gui;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import ro.utcn.bank.model.Account;



public class TableScreen {
	private boolean isOrderScreenParent;
	public JFrame mainFrame;
	private JPanel controlPanel;
	
	private JTable table;

	private JButton cancelButton;

	private AppInterfaceButtonEvents buttonEventHandler;

	private ArrayList<?> list;

	public TableScreen() {
		isOrderScreenParent = true;
	}

	public TableScreen(AppInterfaceButtonEvents buttonEventHandler) {
		this.buttonEventHandler = buttonEventHandler;
		isOrderScreenParent = false;
	}

	public void prepareComponents(ArrayList<?> list) {
		this.list = list;
		

		this.prepareGUI();
		this.prepareTable();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Edit product");
		mainFrame.setSize(1200, 1200);
		mainFrame.setLayout(new GridLayout(1, 1));
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(2, 1));

		mainFrame.add(controlPanel);
		mainFrame.setVisible(false);
	}

	private void prepareTable() {
		if (list.size() > 0) {
			ArrayList<Object> privateFields = new ArrayList<Object>();
			Field[] allFields = list.get(0).getClass().getDeclaredFields();

			if (list.get(0).getClass().getSuperclass() == Account.class){
				allFields = list.get(0).getClass().getSuperclass().getDeclaredFields();

			}
			Object columnNames[] = new Object[allFields.length];
			int index = 0;
			for (Field field : allFields) {
				if (Modifier.isPrivate(field.getModifiers()) || Modifier.isProtected(field.getModifiers())) {

					privateFields.add(field);

					columnNames[index] = field.getName();

					index++;
				}
			}
			int size = list.size();

			Object rowData[][] = new Object[size][256];
			for (index = 0; index < size; ++index) {
				int k = 0;
				for (Field field : allFields) {
					try {
						field.setAccessible(true);
						rowData[index][k] = field.get(list.get(index));
						k++;
					} catch (IllegalArgumentException e1) {
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}

				}

			}

			table = new JTable(rowData, columnNames);
		
			JScrollPane scrollPane = new JScrollPane();

			scrollPane.setViewportView(table);
			controlPanel.add(scrollPane);
			table.setEnabled(false);
		}
		cancelButton = new JButton("Cancel");

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.setVisible(false);
				mainFrame.remove(controlPanel);
				buttonEventHandler.canceled();
			}
		});
		controlPanel.add(cancelButton);
	}
}
