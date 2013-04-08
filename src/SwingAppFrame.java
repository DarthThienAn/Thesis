import activityobject.*;
import swing.SpringUtilities;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SwingAppFrame extends JFrame {

    private static final int DEF_WIDTH = 1200;
    private static final int DEF_HEIGHT = 800;

    static final String[] BUTTON_PARAMETERS = {"Name: ", "Text: ", "Height: ", "Width: "};
    static final String[] TEXTVIEW_PARAMETERS = {"Name: ", "Text: ", "Height: ", "Width: "};
    static final String[] EDITTEXT_PARAMETERS = {"Name: ", "Text: ", "Height: ", "Width: ", "Hint: "};
    static final String[] CONTACTS_PARAMETERS = {"Name: ", "Height: ", "Width: ", "HasName: ", "HasNumber: ", "Divider: "};

    private static final int BUTTON_TYPE = 0;
    private static final int TEXTVIEW_TYPE = 1;
    private static final int EDITTEXT_TYPE = 2;
    private static final int CONTACTS_TYPE = 3;
    private static int currentType = 0;

    private static SwingAppFrame frame;
    private static JPanel parameterPane, hierarchyPane, previewPanel;
    private static JTextField projectNameField, pathField, packageNameField, mainClassField;
    private static JTextField classNameField;
    private static Container contentPane;
    private static ArrayList<ActivityObject> hierarchyList = new ArrayList<ActivityObject>();

    private static CommandLineObject cmd;

    public SwingAppFrame() {
        //Create and set up the window.
        setTitle("Easy Android");
        setSize(DEF_WIDTH, DEF_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentPane = getContentPane();



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

        addHeader(getContentPane());
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

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame = new SwingAppFrame();
                frame.setVisible(true);
            }
        });

        cmd = new CommandLineObject();

    }

    /* Create the menu bar */
    private static JMenuBar getMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);
        // gray
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
        buildItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGlobalProjectProperties();
                cmd.parseCmd("create");
                cmd.parseCmd("build");
            }
        });
        JMenuItem installItem = new JMenuItem("Install Project", KeyEvent.VK_0);
        installItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmd.parseCmd("install");
            }
        });

        buildMenu.add(buildItem);
        buildMenu.add(installItem);
        menuBar.add(buildMenu);

        return menuBar;
    }

    /* Add Global property items */
    public static void addHeader(Container pane) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel projectName = new JLabel("Project Name:  ");
        projectName.setToolTipText("Your project name here  ");

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 0, 0);
        c.weightx = 0.5;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(projectName, c);

        projectNameField = new JTextField();
        projectNameField.setText(cmd.getProjectName());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
//        projectName.setLabelFor(projectNameField);
        pane.add(projectNameField, c);

        JLabel packageName = new JLabel("Package Name: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        pane.add(packageName, c);

        packageNameField = new JTextField();
        packageNameField.setText(cmd.getPackageName());
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

        pathField = new JTextField();
        pathField.setText(cmd.getPath());
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

        mainClassField = new JTextField();
        mainClassField.setText(cmd.getMainActivity());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridx = 3;
        c.gridy = 1;
        pane.add(mainClassField, c);

        JPanel classPicker = getClassPicker();
        c = new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
//        c.ipady = 5;
//        c.ipadx = 0;
//        c.gridwidth = 1;
//        c.weightx = 1.0;   //request any extra vertical space
//        c.weighty = 1.0;   //request any extra vertical space
//        c.gridx = 0;
//        c.gridy = 2;
        pane.add(classPicker, c);

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.PAGE_START;
        c.ipady = 0;
        c.gridwidth = 4;
        c.weightx = 1.0;   //request any extra vertical space
        c.weighty = 1.0;   //request any extra vertical space
        c.gridx = 0;
        c.gridy = 3;

        JSeparator jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        jSeparator.setForeground(new Color(150, 150, 255));
        jSeparator.setBackground(new Color(150, 150, 255));
        pane.add(jSeparator, c);
    }

    public static void addComponentsToPane(Container pane) {
        JPanel optionsPanel = getOptionsList();
        parameterPane = generatePanel(getParametersList());
        hierarchyPane = generatePanel(getHierarchyList());
        previewPanel = generatePanel(getPreviewList());

        pane.add(optionsPanel, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0, GridBagConstraints.PAGE_END, GridBagConstraints.BOTH, new Insets(10, 10, 0, 0), 0, 500));
        pane.add(parameterPane, new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0, GridBagConstraints.PAGE_END, GridBagConstraints.BOTH, new Insets(10, 10, 0, 0), 0, 500));
        pane.add(hierarchyPane, new GridBagConstraints(2, 3, 1, 1, 1.0, 1.0, GridBagConstraints.PAGE_END, GridBagConstraints.BOTH, new Insets(10, 10, 0, 0), 0, 500));
        pane.add(previewPanel, new GridBagConstraints(3, 3, 1, 1, 1.0, 1.0, GridBagConstraints.PAGE_END, GridBagConstraints.BOTH, new Insets(10, 10, 0, 0), 0, 500));
    }

    /* ClassPicker as a ComboBox */
    public static JPanel getClassPicker() {
        JPanel classPicker = new JPanel();
        ((FlowLayout)classPicker.getLayout()).setAlignment(FlowLayout.LEFT);

        JLabel title = new JLabel("Class: ");
        classPicker.add(title);

        JPanel comboPanel = new JPanel();

        String[] labels = {"MyClass1", "MyClass2", "MyClass3"};
        JComboBox<String> combobox = new JComboBox<String>(labels);
        // Create the combo box
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
        classPicker.add(comboPanel);

        return classPicker;
    }

    public static ArrayList<JComponent> getOptionsList1() {
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


    /* Options as a JList */
    public static JPanel getOptionsList() {
        SpringLayout layout = new SpringLayout();
        JPanel optionsPanel = new JPanel(layout);

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
                currentType = e.getLastIndex();
//                frame.setVisible(false);
//                frame = new SwingAppFrame();
//                frame.setVisible(true);
                contentPane.remove(parameterPane);
                parameterPane = generatePanel(getParametersList());
                contentPane.add(parameterPane, new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0, GridBagConstraints.PAGE_END, GridBagConstraints.BOTH, new Insets(10, 10, 0, 0), 0, 500));
                parameterPane.revalidate();
//                parameters = generateParameterPanel(currentType);
//                contentPane.add(parameters);
//                parameters.revalidate();
//                contentPane.revalidate();
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

    /* Options as a ComboBox */
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
        final JPanel parameters = generateParameterPanel(currentType);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the text from all of the text fields
                ActivityObject object;
                String name = null;
                for (int i = 1; i < parameters.getComponentCount(); i += 2) {
                    Component component = parameters.getComponent(i);
                    System.out.println("Text: " + i + " \'" + ((JTextField) component).getText() + "\'");
                    if (i == 1)
                        name = ((JTextField) component).getText();
                }
                hierarchyList.add(getActivityObject(currentType, parameters));
                refreshHierarchyPane();
                refreshPreviewPane();
            }
        });

        componentList.add(title);
        componentList.add(addButton);
        componentList.add(parameters);

        return componentList;
    }

    public static JPanel generatePanel(ArrayList<JComponent> componentList) {
        SpringLayout layout = new SpringLayout();
        JPanel panel = new JPanel(layout);

        layout.putConstraint(SpringLayout.NORTH, componentList.get(0), 5, SpringLayout.NORTH, panel);
        panel.add(componentList.get(0));
        for (int i = 1; i < componentList.size(); i++) {
            panel.add(componentList.get(i));
            layout.putConstraint(SpringLayout.NORTH, componentList.get(i), 5, SpringLayout.SOUTH, componentList.get(i - 1));
        }

        return panel;
    }

    public static JPanel generateParameterPanel(int type) {
        String[] labels;
        switch (type) {
            case BUTTON_TYPE:
                labels = BUTTON_PARAMETERS;
                break;
            case TEXTVIEW_TYPE:
                labels = TEXTVIEW_PARAMETERS;
                break;
            case EDITTEXT_TYPE:
                labels = EDITTEXT_PARAMETERS;
                break;
            case CONTACTS_TYPE:
                labels = CONTACTS_PARAMETERS;
                break;
            default:
                return null;
        }

        //Create and populate the panel.
        JPanel panel = new JPanel(new SpringLayout());
        for (String label : labels) {
            JLabel l = new JLabel(label, JLabel.TRAILING);
            panel.add(l);
            JTextField textField = new JTextField(8);
            l.setLabelFor(textField);
            panel.add(textField);
        }

        // Panel layout
        SpringUtilities.makeCompactGrid(panel,
                labels.length, 2, //rows, cols
                1, 1,        //initX, initY
                6, 6);       //xPad, yPad

        return panel;
    }

    public static ActivityObject getActivityObject(int type, JPanel parameters) {
//        for (int i = 1; i < parameters.getComponentCount(); i += 2) {
//            Component component = parameters.getComponent(i);
//            System.out.println("Text: " + i + " \'" + ((JTextField) component).getText() + "\'");
//            if (i == 1)
//                ((JTextField) component).getText();
//        }

        ActivityObject object;
        String name, text, height, width, hint;
        StringBuilder sb;
        name = ((JTextField) parameters.getComponent(1)).getText();
        text = ((JTextField) parameters.getComponent(3)).getText();
        height = ((JTextField) parameters.getComponent(5)).getText();
        width = ((JTextField) parameters.getComponent(7)).getText();
        if (StringHelper.isNullorEmpty(name))
            name = cmd.getNextDefaultObjectName();
        switch (type) {
            case BUTTON_TYPE:
                object = new ButtonActivityObject(name, text, height, width, null);
                sb = new StringBuilder("button ");
                if (!StringHelper.isNullorEmpty(name))
                    sb.append("-name ").append(name).append(" ");
                if (!StringHelper.isNullorEmpty(text))
                    sb.append("-text ").append(text).append(" ");
                if (!StringHelper.isNullorEmpty(height))
                    sb.append("-height ").append(height).append(" ");
                if (!StringHelper.isNullorEmpty(width))
                    sb.append("-width ").append(width).append(" ");
                cmd.parseCmd(sb.toString());
                break;
            case TEXTVIEW_TYPE:
                object = new TextViewActivityObject(name, text, height, width, null);
                sb = new StringBuilder("textview ");
                if (!StringHelper.isNullorEmpty(name))
                    sb.append("-name ").append(name).append(" ");
                if (!StringHelper.isNullorEmpty(text))
                    sb.append("-text ").append(text).append(" ");
                if (!StringHelper.isNullorEmpty(height))
                    sb.append("-height ").append(height).append(" ");
                if (!StringHelper.isNullorEmpty(width))
                    sb.append("-width ").append(width).append(" ");
                cmd.parseCmd(sb.toString());
                break;
            case EDITTEXT_TYPE:
                hint = ((JTextField) parameters.getComponent(9)).getText();
                object = new EditTextActivityObject(name, text, hint, height, width, null);
                sb = new StringBuilder("edittext ");
                if (!StringHelper.isNullorEmpty(name))
                    sb.append("-name ").append(name).append(" ");
                if (!StringHelper.isNullorEmpty(text))
                    sb.append("-text ").append(text).append(" ");
                if (!StringHelper.isNullorEmpty(height))
                    sb.append("-height ").append(height).append(" ");
                if (!StringHelper.isNullorEmpty(width))
                    sb.append("-width ").append(width).append(" ");
                if (!StringHelper.isNullorEmpty(hint))
                    sb.append("-hint ").append(hint).append(" ");
                cmd.parseCmd(sb.toString());
                break;
            case CONTACTS_TYPE:
                height = ((JTextField) parameters.getComponent(3)).getText();
                width = ((JTextField) parameters.getComponent(5)).getText();
                String hasName = ((JTextField) parameters.getComponent(7)).getText();
                String hasNumber = ((JTextField) parameters.getComponent(9)).getText();
                String divider = ((JTextField) parameters.getComponent(11)).getText();
                object = new ContactsListActivityObject(name, height, width, null, !StringHelper.isNullorEmpty(hasName), !StringHelper.isNullorEmpty(hasNumber), divider);
                sb = new StringBuilder("contactslist ");
                if (!StringHelper.isNullorEmpty(name))
                    sb.append("-name ").append(name).append(" ");
                if (!StringHelper.isNullorEmpty(height))
                    sb.append("-height ").append(height).append(" ");
                if (!StringHelper.isNullorEmpty(width))
                    sb.append("-width ").append(width).append(" ");
                if (!StringHelper.isNullorEmpty(hasName))
                    sb.append("-hasName ").append("1").append(" ");
                if (!StringHelper.isNullorEmpty(hasNumber))
                    sb.append("-hasNumber ").append("1").append(" ");
                if (!StringHelper.isNullorEmpty(divider))
                    sb.append("-divider ").append("1").append(" ");
                cmd.parseCmd(sb.toString());
                break;
            default:
                return null;
        }
        return object;
    }

    public static ArrayList<JComponent> getHierarchyList() {
        ArrayList<JComponent> componentList = new ArrayList<JComponent>();
        JLabel title = new JLabel("Hierarchy");
        componentList.add(title);

        //Create and populate the panel.
        JPanel titlePanelParent = new JPanel(new SpringLayout());

        JLabel titlePanel = new JLabel("Class Name: ", JLabel.TRAILING);
        titlePanelParent.add(titlePanel);
        classNameField  = new JTextField(8);
        titlePanel.setLabelFor(classNameField);
        classNameField.setText(cmd.getClassName());
        titlePanelParent.add(classNameField);

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
        String[] items = new String[hierarchyList.size()];
        for (int i = 0; i < hierarchyList.size(); i++) {
            items[i] = hierarchyList.get(i).getObjectName();
        }
        final JList<String> list = new JList<String>(items);
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

        /* button pane */

        JButton upButton = new JButton("Up");
        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repositionInArrayList(hierarchyList, list.getSelectedIndex(), list.getSelectedIndex() - 1);
                cmd.parseCmd("up " + list.getSelectedIndex());

                refreshHierarchyPane();
                refreshPreviewPane();
            }
        });
        JButton downButton = new JButton("Down");
        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = list.getSelectedIndex();
                repositionInArrayList(hierarchyList, list.getSelectedIndex(), list.getSelectedIndex() + 1);
                cmd.parseCmd("down " + list.getSelectedIndex());

                refreshHierarchyPane();
                refreshPreviewPane();
            }
        });
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hierarchyList.remove(list.getSelectedIndex());
                cmd.parseCmd("remove " + list.getSelectedIndex());

                refreshHierarchyPane();
                refreshPreviewPane();
            }
        });

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
//        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
//        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(upButton);
        buttonPane.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonPane.add(downButton);
        buttonPane.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonPane.add(removeButton);

//        layout.putConstraint(SpringLayout.NORTH, listPanel, 5, SpringLayout.SOUTH, title);
        componentList.add(buttonPane);

        return componentList;
    }

    /* Put item at index a at index b instead */
    private static void repositionInArrayList(ArrayList list, int a, int b) {
        if ((a < 0) || (b < 0)) return;
        if ((a > (list.size() - 1)) || (b > (list.size() - 1))) return;
        Object item = list.remove(a);
        list.add(b, item);
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

    public static ArrayList<JComponent> getPreviewList() {
        ArrayList<JComponent> componentList = new ArrayList<JComponent>();
        JLabel title = new JLabel("Java Code");
        componentList.add(title);

        JButton addButton = new JButton("Refresh");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                cmd.parseCmd("");
//                cmd.setPath(pathField.getText());
//                cmd.setPackageName(packageNameField.getText());
//                cmd.setMainActivity(mainClassField.getText());

                refreshPreviewPane();
            }
        });
        componentList.add(addButton);

        JScrollPane scrollPane = new JScrollPane(new JTextArea(cmd.getString()));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        scrollPane.setPreferredSize(new Dimension(200, 400));
        componentList.add(scrollPane);

        return componentList;
    }

    private static void refreshHierarchyPane() {
        contentPane.remove(hierarchyPane);
        hierarchyPane = generatePanel(getHierarchyList());
        contentPane.add(hierarchyPane, new GridBagConstraints(2, 3, 1, 1, 1.0, 1.0, GridBagConstraints.PAGE_END, GridBagConstraints.BOTH, new Insets(10, 10, 0, 0), 0, 500));
        hierarchyPane.revalidate();
    }

    private static void refreshPreviewPane() {
        contentPane.remove(previewPanel);
        previewPanel = generatePanel(getPreviewList());
        contentPane.add(previewPanel, new GridBagConstraints(3, 3, 1, 1, 1.0, 1.0, GridBagConstraints.PAGE_END, GridBagConstraints.BOTH, new Insets(10, 10, 0, 0), 0, 500));
        hierarchyPane.revalidate();
    }


    private static void updateGlobalProjectProperties() {
        cmd.parseCmd("name " + projectNameField.getText());
        cmd.parseCmd("package " + packageNameField.getText());
        cmd.parseCmd("path " + pathField.getText());
        cmd.parseCmd("main " + mainClassField.getText());
        cmd.parseCmd("classname " + classNameField.getText());
    }
}
