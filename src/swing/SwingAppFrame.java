package swing;

import android.R;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SwingAppFrame extends JFrame {

    private static final int DEF_WIDTH = 1000;
    private static final int DEF_HEIGHT = 600;

    public SwingAppFrame() {
        //Create and set up the window.
        setTitle("Easy Android");
        setSize(DEF_WIDTH, DEF_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

//        JLabel header = new JLabel();
//        header.setPreferredSize(new Dimension(DEF_WIDTH, 100));
//        JLabel projectName = new JLabel("Project Name");
//        projectName.setPreferredSize(new Dimension(200, 40));
//        JLabel packageName = new JLabel("Package Name");
//        packageName.setPreferredSize(new Dimension(200, 40));
//        header.add(projectName);

        //Create a background to put in the content pane.
//        JLabel background = new JLabel();
//        background.setOpaque(false);
//        background.setBackground(new Color(130, 130, 130));
//        background.setPreferredSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));

        //Add the ubiquitous "Hello World" label.
//        JLabel label = new JLabel("Hello World");
//        label.setPreferredSize(new Dimension(200, 40));
//        background.add(label);

        //Set the menu bar and add the label to the content pane.
        setJMenuBar(getMenu());
//        frame.getContentPane().add(projectName, BorderLayout.PAGE_START);
//        frame.getContentPane().add(packageName, BorderLayout.PAGE_START);

        addComponentsToPane(getContentPane());
//        frame.getContentPane().add(background, BorderLayout.CENTER);
//        frame.getContentPane().add(label, BorderLayout.CENTER);

//        JSeparator jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
//        jSeparator.setForeground(new Color(0, 50, 255));
//        jSeparator.setBackground(new Color(0, 50, 255));
//        getContentPane().add(jSeparator);

        //Display the window.
        pack();
//        setVisible(true);
    }

    //Create the menu bar
    private static JMenuBar getMenu() {
        // gray
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);
        menuBar.setBackground(new Color(196, 196, 196));
        menuBar.setPreferredSize(new Dimension(DEF_WIDTH, 20));

        JMenu fileMenu = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open", KeyEvent.VK_0);
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Item clicked: " + e.getActionCommand());
            }
        });

        JMenuItem saveItem = new JMenuItem("Save", KeyEvent.VK_0);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_0, ActionEvent.ALT_MASK));
//        menuItem.getAccessibleContext().setAccessibleDescription(
//                "This doesn't really do anything");
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Item clicked: " + e.getActionCommand());
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_0);
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Item clicked: " + e.getActionCommand());
                System.exit(0);
            }
        });

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        JMenu buildMenu = new JMenu("Build");
        JMenuItem buildItem = new JMenuItem("Build Project", KeyEvent.VK_0);
        JMenuItem installItem = new JMenuItem("Install Project", KeyEvent.VK_0);

        buildMenu.add(buildItem);
        buildMenu.add(installItem);
        menuBar.add(buildMenu);

        return menuBar;
    }

    public static void addComponentsToPane(Container pane) {
//            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
//        if (shouldFill) {
//            //natural height, maximum width
//            c.fill = GridBagConstraints.HORIZONTAL;
//        }

        JLabel projectName = new JLabel("Project Name:  ");
        projectName.setToolTipText("Your project name here  ");
//        if (shouldWeightX) {
//            c.weightx = 0.5;
//        }
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(projectName, c);

        JTextField projectNameField = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(projectNameField, c);

        JLabel packageName = new JLabel("Package Name: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        pane.add(packageName, c);

        JTextField packageNameField = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 0;
        pane.add(packageNameField, c);

        JLabel path = new JLabel("Path: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(path, c);

        JTextField pathField = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        pane.add(pathField, c);

        JLabel mainClass = new JLabel("Main Class: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
//        c.gridwidth = 3;
        c.gridx = 2;
        c.gridy = 1;
        pane.add(mainClass, c);

        JTextField mainClassField = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 1;
        pane.add(mainClassField, c);

//        JSeparator jSeparator = new JSeparator();
//        jSeparator.setForeground(new Color(0, 50, 255));
//        jSeparator.setBackground(new Color(0, 50, 255));
//        pane.add(jSeparator, c);
//
        SpringLayout layout = new SpringLayout();
//        JPanel optionsPanel = new JPanel();
//        optionsPanel.setLayout(layout);
//        JLabel options1 = new JLabel("Options");
//        layout.putConstraint(SpringLayout.NORTH, options1, 5, SpringLayout.NORTH, pane);
////        options1.set
//        optionsPanel.add(options1);
//        JLabel options2 = new JLabel("Options2");
//        optionsPanel.add(options2);
//        layout.putConstraint(SpringLayout.NORTH, options2, 5, SpringLayout.SOUTH, options1);

//        JPanel optionsPanel = generatePanel(layout, getOptionsList());
        JPanel optionsPanel = getOptionsList3(layout);
        JPanel parametersPanel = generatePanel(layout, getParametersList());
        JPanel hierarchyPanel = generatePanel(layout, getHierarchyList("MyClass"));
        JPanel previewPanel = generatePanel(layout, getPreviewList("java asdfsadfasdfasdfadfasf\nasdfadsfafasfasf\nadsfadsfasfd"));

        JLabel options = new JLabel("Options");
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 500;
        c.weightx = 1.0;   //request any extra vertical space
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridx = 0;       //aligned with button 2
        c.gridy = 3;       //third row
//        c.gridheight = 6;
        pane.add(optionsPanel, c);

        JLabel define = new JLabel("Define");
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 500;
        c.weightx = 1.0;   //request any extra vertical space
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.gridx = 1;       //aligned with button 2
        c.gridy = 3;       //third row
        define.setVerticalTextPosition(SwingConstants.TOP);
//        pane.add(define, c);
        pane.add(parametersPanel, c);

        JLabel hierarchy = new JLabel("Hierarchy");
        hierarchy.setVerticalTextPosition(SwingConstants.TOP);
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 500;
        c.weightx = 1.0;   //request any extra vertical space
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.gridx = 2;       //aligned with button 2
        c.gridy = 3;       //third row
        pane.add(hierarchyPanel, c);

        JLabel preview = new JLabel("Preview");
        preview.setVerticalTextPosition(SwingConstants.BOTTOM);
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 500;
        c.weightx = 1.0;   //request any extra vertical space
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.gridx = 3;       //aligned with button 2
        c.gridy = 3;       //third row
        pane.add(previewPanel, c);

        c.fill = GridBagConstraints.BOTH;
        c.ipady = 0;
        c.gridwidth = 4;
        c.weightx = 1.0;   //request any extra vertical space
        c.weighty = 1.0;   //request any extra vertical space
        c.gridx = 0;       //aligned with button 2
        c.gridy = 2;       //third row

        JSeparator jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        jSeparator.setForeground(new Color(0, 50, 255));
        jSeparator.setBackground(new Color(0, 50, 255));
        pane.add(jSeparator, c);
    }


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SwingAppFrame frame = new SwingAppFrame();
                frame.setVisible(true);
            }
        });
    }

    public static ArrayList<JComponent> getOptionsList() {
        ArrayList<JComponent> componentList = new ArrayList<JComponent>();
        componentList.add(new JLabel("Options"));
        JButton button = new JButton("Add a Button");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        componentList.add(button);
        JButton textview = new JButton("Add a TextView");
        textview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        componentList.add(textview);
        JButton edittext = new JButton("Add an EditText");
        edittext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        componentList.add(edittext);

        return componentList;
    }


    // Initialization for JList
    public static JPanel getOptionsList3(SpringLayout layout) {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(layout);

        JLabel title = new JLabel("Options");

        layout.putConstraint(SpringLayout.NORTH, title, 5, SpringLayout.NORTH, optionsPanel);
        optionsPanel.add(title);

        JPanel listPanel = new JPanel();
        String[] labels = {"Add Button", "Add TextView", "Add EditText", "Add Contacts List"};
        JList<String> list = new JList<String>(labels); // Create the list
//        list.setSelectedIndex(0); // Set initial state

        // Handle state changes
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
//                ItemChooser.this.select(list.getSelectedIndex());
                System.out.println(e.toString());
                System.out.println(e.getSource());
            }
        });

        // Lay out list and name label vertically
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS)); // vertical
        listPanel.add(new JScrollPane(list)); // Add the JList
        listPanel.setPreferredSize(new Dimension(200, 400));
        optionsPanel.add(listPanel);

        layout.putConstraint(SpringLayout.NORTH, listPanel, 5, SpringLayout.SOUTH, title);
        return optionsPanel;
    }

    // Initialization for JComboBox
    public static JPanel getOptionsList2(SpringLayout layout) {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(layout);

        JLabel title = new JLabel("Options");

        layout.putConstraint(SpringLayout.NORTH, title, 5, SpringLayout.NORTH, optionsPanel);
        optionsPanel.add(title);

        JPanel comboPanel = new JPanel();

        String[] labels = {"Add Button", "Add TextView", "Add EditText"};
        JComboBox<String> combobox = new JComboBox<String>(labels); // Create the combo box
//    combobox.setSelectedIndex(selection); // Set initial state

        // Handle changes to the state
        combobox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
//                    this.select(combobox.getSelectedIndex());
                System.out.println(e.toString());
                System.out.println(e.getSource());
            }
        });

        // Lay out combo box and name label horizontally
        comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.X_AXIS));
        comboPanel.add(combobox);
        optionsPanel.add(comboPanel);

        layout.putConstraint(SpringLayout.NORTH, comboPanel, 5, SpringLayout.SOUTH, title);
        return optionsPanel;
    }

    public static ArrayList<JComponent> getParametersList() {
        ArrayList<JComponent> componentList = new ArrayList<JComponent>();
        JLabel title = new JLabel("Parameters");
        componentList.add(title);

        JPanel namePanel = new JPanel(new FlowLayout());
        JLabel name = new JLabel("name: ");
        namePanel.add(name);
        JTextField nameText = new JTextField();
        nameText.setPreferredSize(new Dimension(100, 20));
        namePanel.add(nameText);

//        componentList.add(generateParameterPanel("name"));
//        componentList.add(generateParameterPanel("width"));
//        componentList.add(generateParameterPanel("height"));
//        componentList.add(generateParameterPanel("text"));
        componentList.add(generateParameterPanel2());
//        componentList.add(new JLabel("Options3"));

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("sup");
            }
        });
        componentList.add(addButton);

        return componentList;
    }

    public static JPanel generatePanel(SpringLayout layout, ArrayList<JComponent> componentList) {
        JPanel panel = new JPanel();
        panel.setLayout(layout);

        layout.putConstraint(SpringLayout.NORTH, componentList.get(0), 5, SpringLayout.NORTH, panel);
        panel.add(componentList.get(0));
        for (int i = 1; i < componentList.size(); i++) {
            panel.add(componentList.get(i));
            layout.putConstraint(SpringLayout.NORTH, componentList.get(i), 5, SpringLayout.SOUTH, componentList.get(i - 1));
        }

        return panel;
    }

    public static JPanel generateParameterPanel(String s) {
        JPanel panel = new JPanel(new FlowLayout());
        JLabel field = new JLabel(s + ": ");
        panel.add(field);
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(100, 20));
        panel.add(textField);

        return panel;
    }

    public static JPanel generateParameterPanel2() {
        String[] labels = {"Name: ", "Text: ", "Width: ", "Height: "};

        //Create and populate the panel.
        JPanel p = new JPanel(new SpringLayout());
        for (String label : labels) {
            JLabel l = new JLabel(label, JLabel.TRAILING);
            p.add(l);
            JTextField textField = new JTextField(8);
            l.setLabelFor(textField);
            p.add(textField);
        }

////Lay out the panel.

        SpringUtilities.makeCompactGrid(p,
                labels.length, 2, //rows, cols
                1, 1,        //initX, initY
                6, 6);       //xPad, yPad

        return p;
    }

    public static ArrayList<JComponent> getHierarchyList(String className) {
        ArrayList<JComponent> componentList = new ArrayList<JComponent>();
        JLabel title = new JLabel("Hierarchy");
        componentList.add(title);

        String[] labels = {"button", "textview", "edditext", "1", "2", "3", "4", "5", "6", "7"};

        //Create and populate the panel.
        JPanel titlePanelParent = new JPanel(new SpringLayout());

        JLabel titlePanel = new JLabel("Class Name: ", JLabel.TRAILING);
        titlePanelParent.add(titlePanel);
        JTextField textField = new JTextField(8);
        titlePanel.setLabelFor(textField);
        textField.setText(className);
        titlePanelParent.add(textField);

        SpringUtilities.makeCompactGrid(titlePanelParent,
                1, 2, //rows, cols
                1, 1,        //initX, initY
                6, 6);       //xPad, yPad

//        SpringLayout layout = new SpringLayout();
//        JPanel itemsPanel = new JPanel(layout);

//        for (String label : labels) {
//            JButton l = new JButton(label);
//            itemsPanel.add(l);
//        }
//
//        SpringUtilities.makeCompactGrid(itemsPanel,
//                labels.length, 1, //rows, cols
//                1, 1,        //initX, initY
//                6, 6);       //xPad, yPad

        componentList.add(titlePanelParent);
//        componentList.add(itemsPanel);


        JPanel listPanel = new JPanel();
//        String[] labels = {"Add Button", "Add TextView", "Add EditText", "Add Contacts List"};
        JList<String> list = new JList<String>(labels); // Create the list
//        list.setSelectedIndex(0); // Set initial state

        // Handle state changes
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
//                ItemChooser.this.select(list.getSelectedIndex());
                System.out.println(e.toString());
                System.out.println(e.getSource());
            }
        });

        // Lay out list and name label vertically
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS)); // vertical
        JScrollPane listPane = new JScrollPane(list);
        listPane.setPreferredSize(new Dimension(200, 400));
        listPanel.add(listPane); // Add the JList
//        listPanel.setPreferredSize(new Dimension(200, 400));
        componentList.add(listPanel);

//        layout.putConstraint(SpringLayout.NORTH, listPanel, 5, SpringLayout.SOUTH, title);

        return componentList;
    }

    public static JPanel getHierarchyList1(SpringLayout layout) {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(layout);

        JLabel title = new JLabel("Options");

        layout.putConstraint(SpringLayout.NORTH, title, 5, SpringLayout.NORTH, optionsPanel);
        optionsPanel.add(title);

        JPanel listPanel = new JPanel();
        String[] labels = {"Add Button", "Add TextView", "Add EditText", "Add Contacts List"};
        JList<String> list = new JList<String>(labels); // Create the list
//        list.setSelectedIndex(0); // Set initial state

        // Handle state changes
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
//                ItemChooser.this.select(list.getSelectedIndex());
                System.out.println(e.toString());
                System.out.println(e.getSource());
            }
        });

        // Lay out list and name label vertically
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS)); // vertical
        listPanel.add(new JScrollPane(list)); // Add the JList
        listPanel.setPreferredSize(new Dimension(200, 400));
        optionsPanel.add(listPanel);

        layout.putConstraint(SpringLayout.NORTH, listPanel, 5, SpringLayout.SOUTH, title);
        return optionsPanel;
    }

    public static ArrayList<JComponent> getPreviewList(String code) {
        ArrayList<JComponent> componentList = new ArrayList<JComponent>();
        JLabel title = new JLabel("Preview");
        componentList.add(title);

        JScrollPane scrollPane = new JScrollPane(new JTextArea(code));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        scrollPane.setPreferredSize(new Dimension(200, 400));
        componentList.add(scrollPane);

        return componentList;
    }
}
