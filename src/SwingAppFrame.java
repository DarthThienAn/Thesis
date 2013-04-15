import activityobject.*;
import swing.SpringUtilities;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class SwingAppFrame extends JFrame {

    private static final int DEF_WIDTH = 1300;
    private static final int DEF_HEIGHT = 700;

    private static final int MENU_HEIGHT = 20;

    private static final int OPTIONS_WIDTH = 200;
    private static final int OPTIONS_HEIGHT = 600;
    private static final int FUNCTIONS_WIDTH = 200;
    private static final int FUNCTIONS_HEIGHT = 600;
    private static final int PARAMETERS_WIDTH = 220;
    private static final int PARAMETERS_HEIGHT = 600;
    private static final int HIERARCHY_WIDTH = 220;
    private static final int HIERARCHY_HEIGHT = 600;
    private static final int PREVIEW_WIDTH = 350;
    private static final int PREVIEW_HEIGHT = 400;
    private static final int WINDOW_WIDTH = 200;
    private static final int WINDOW_HEIGHT = 400;

    private static final int BORDER_COLOR = 150;
    private static final int MENUBAR_COLOR = 196;

    private static final String[] FUNCTION_HELP = { "addition(100, 20)", "subtraction(100, 20)", "multiplication(2.5, 1.0)", "division(10.0, 4.0)" };

    private static final String[] BUTTON_PARAMETERS = {"Name: ", "Text: ", "Height: ", "Width: ", "Action: "};
    private static final String[] TEXTVIEW_PARAMETERS = {"Name: ", "Text: ", "Height: ", "Width: "};
    private static final String[] EDITTEXT_PARAMETERS = {"Name: ", "Text: ", "Height: ", "Width: ", "Hint: "};
    private static final String[] CONTACTS_PARAMETERS = {"Name: ", "Height: ", "Width: ", "HasName: ", "HasNumber: ", "Divider: ", "Action: "};
    private static final String[] CUSTOMFUNCTION_PARAMETERS = {"Name: ", "ReturnType: ", "Parameters: ", "Body: "};

    private static final int BUTTON_TYPE = 0;
    private static final int TEXTVIEW_TYPE = 1;
    private static final int EDITTEXT_TYPE = 2;
    private static final int CONTACTS_TYPE = 3;
    private static final int CUSTOMFUNCTION_TYPE = 4;
    private static int currentType = 0;

    private SwingAppFrame frame;
    private JPanel optionsPane, functionPane, parameterPane, hierarchyPane, previewPane, componentsPane;
    private JTextField projectNameField, pathField, packageNameField, mainClassField;
    private JTextField classNameField;
    private ArrayList<JTextField> headerFields;
    private JSeparator jSeparator;
    private Container mainContentPane;
    private ArrayList<ActivityObject> hierarchyList = new ArrayList<ActivityObject>();
    private SpringLayout mainSpringLayout;

    private CommandLineObject cmd;

    /* Constructor */
    public SwingAppFrame() {
        //Create and set up the window.
        setTitle("EasyAndroid");
        mainSpringLayout = new SpringLayout();
        setLayout(mainSpringLayout);
        setPreferredSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));
        setSize(DEF_WIDTH, DEF_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainContentPane = getContentPane();

        cmd = new CommandLineObject();

        //Create a background to put in the content pane.
//        JLabel background = new JLabel();
//        background.setOpaque(false);
//        background.setBackground(new Color(130, 130, 130));
//        background.setPreferredSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));

        //Set the menu bar and add the label to the content pane.
        setJMenuBar(getMenu());

        //add the header project properties
        addHeader();

        //add the main content panes (components)
        addComponents();

//        frame.getContentPane().add(projectName, BorderLayout.PAGE_START);
//        frame.getContentPane().add(packageName, BorderLayout.PAGE_START);
//        frame.getContentPane().add(background, BorderLayout.CENTER);
//        frame.getContentPane().add(label, BorderLayout.CENTER);

        //Display the window.
        pack();
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

    /* Create the menu bar */
    private JMenuBar getMenu() {
        /* define the general properties of the menu bar */
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);
        menuBar.setBackground(new Color(MENUBAR_COLOR, MENUBAR_COLOR, MENUBAR_COLOR));
        menuBar.setPreferredSize(new Dimension(DEF_WIDTH, MENU_HEIGHT));

        /* create the file submenu */
        JMenu fileMenu = new JMenu("File");

        // open menu item
        JMenuItem openItem = new JMenuItem("Open", KeyEvent.VK_0);
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Item clicked: " + e.getActionCommand());
            }
        });

        // save menu item
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

        // reset menu item
        JMenuItem resetItem = new JMenuItem("Reset", KeyEvent.VK_0);
        resetItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmd.parseCmd("reset");
            }
        });

        // exit menu item
        JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_0);
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Item clicked: " + e.getActionCommand());
                System.exit(0);
            }
        });


//        fileMenu.add(openItem);
//        fileMenu.add(saveItem);
        fileMenu.add(resetItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        /* create the build submenu */
        JMenu buildMenu = new JMenu("Build");
        // build menu item
        JMenuItem buildItem = new JMenuItem("Build Project", KeyEvent.VK_0);
        buildItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGlobalProjectProperties();
                cmd.parseCmd("create");
                cmd.parseCmd("build");
            }
        });

        // install menu item
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

    /* Add global property items */
    public void addHeader() {
        SpringLayout mainLayout = new SpringLayout();
        JPanel headerPane = new JPanel(mainLayout);
        mainContentPane.add(headerPane);

        String[] labels = { "Project Name: ", "Package Name: ", "Path: ", "Main Class: "};
        String[] texts = { cmd.getProjectName(), cmd.getPackageName(), cmd.getPath(), cmd.getMainActivity()};
        ArrayList<JLabel> labelsList = new ArrayList<JLabel>(4);
        headerFields = new ArrayList<JTextField>(4);

        for (int i = 0; i < labels.length; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            headerPane.add(l);
//        projectName.setToolTipText("Your project name here  ");
            labelsList.add(l);
            JTextField textField = new JTextField(20);
            textField.setText(texts[i]);
            l.setLabelFor(textField);
            headerPane.add(textField);
            headerFields.add(textField);
        }


        // Global Headers in a block
        SpringUtilities.makeCompactGrid(headerPane,
                2, labels.length, //rows, cols
                1, 1,        //initX, initY
                10, 10);       //xPad, yPad

//        JPanel classPicker = getClassPicker();
//        parent.add(classPicker);

        jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        jSeparator.setForeground(new Color(150, 150, 255));
        jSeparator.setBackground(new Color(150, 150, 255));
        jSeparator.setPreferredSize(new Dimension(2000, 3));
        mainContentPane.add(jSeparator);

        mainSpringLayout.putConstraint(SpringLayout.NORTH, headerPane, 5, SpringLayout.NORTH, mainContentPane);
        mainSpringLayout.putConstraint(SpringLayout.NORTH, jSeparator, 5, SpringLayout.SOUTH, headerPane);
//        parentLayout.putConstraint(SpringLayout.NORTH, classPicker, 5, SpringLayout.SOUTH, headerPane);
//        parentLayout.putConstraint(SpringLayout.NORTH, jSeparator, 5, SpringLayout.SOUTH, classPicker);
    }

    /* Add the content panes */
    public void addComponents() {
        componentsPane = new JPanel();
        BoxLayout componentsLayout = new BoxLayout(componentsPane, BoxLayout.X_AXIS);
        componentsPane.setLayout(componentsLayout);
        mainContentPane.add(componentsPane);

        optionsPane = getOptionsList();
        functionPane = getFunctionsList();
        parameterPane = generatePanel(getParametersList());
        hierarchyPane = generatePanel(getHierarchyList());
        previewPane = generatePanel(getPreviewList());

//        optionsPane.setAlignmentY(Component.TOP_ALIGNMENT);
        optionsPane.setPreferredSize(new Dimension(OPTIONS_WIDTH, OPTIONS_HEIGHT));
        functionPane.setPreferredSize(new Dimension(FUNCTIONS_WIDTH, FUNCTIONS_HEIGHT));
        parameterPane.setPreferredSize(new Dimension(PARAMETERS_WIDTH, PARAMETERS_HEIGHT));
        hierarchyPane.setPreferredSize(new Dimension(HIERARCHY_WIDTH, HIERARCHY_HEIGHT));
        previewPane.setPreferredSize(new Dimension(PREVIEW_WIDTH, PREVIEW_HEIGHT));

        componentsPane.add(optionsPane);
        componentsPane.add(Box.createRigidArea(new Dimension(10, 0)));
        componentsPane.add(functionPane);
        componentsPane.add(Box.createRigidArea(new Dimension(10, 0)));
        componentsPane.add(parameterPane);
        componentsPane.add(Box.createRigidArea(new Dimension(10, 0)));
        componentsPane.add(hierarchyPane);
        componentsPane.add(Box.createRigidArea(new Dimension(10, 0)));
        componentsPane.add(previewPane);
        componentsPane.add(Box.createRigidArea(new Dimension(10, 0)));

        mainSpringLayout.putConstraint(SpringLayout.NORTH, componentsPane, 5, SpringLayout.SOUTH, jSeparator);
        mainSpringLayout.putConstraint(SpringLayout.WEST, componentsPane, 5, SpringLayout.WEST, mainContentPane);
    }

    /* ClassPicker as a ComboBox */
    public JPanel getClassPicker() {
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
    public JPanel getOptionsList() {
        SpringLayout layout = new SpringLayout();
        JPanel optionsPanel = new JPanel(layout);

        JLabel title = new JLabel("Options");

        layout.putConstraint(SpringLayout.NORTH, title, 5, SpringLayout.NORTH, optionsPanel);
        optionsPanel.add(title);

        JPanel listPanel = new JPanel();
        String[] labels = {"Add Button", "Add TextView", "Add EditText", "Add Contacts List", "Create a Custom Function"};
        final JList<String> list = new JList<String>(labels); // Create the list
//        list.setSelectedIndex(0); // Set initial state

        // Handle state changes
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
//                ItemChooser.this.select(list.getSelectedIndex());
                    System.out.println("?" + list.getSelectedIndex());
                    currentType = list.getSelectedIndex();
                    refreshParameterPane();
                }
            }
        });
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Lay out list and name label vertically
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS)); // vertical
        listPanel.add(new JScrollPane(list)); // Add the JList
        listPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
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

    public ArrayList<JComponent> getParametersList() {
        ArrayList<JComponent> componentList = new ArrayList<JComponent>();
        JLabel title = new JLabel("Parameters");
        final JPanel parameters = generateParameterPanel(currentType);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActivityObject o = getActivityObject(currentType, parameters);
                if (o != null) {
                    hierarchyList.add(o);
                    refreshHierarchyPane();
                    refreshPreviewPane();
                    refreshParameterPane();
                }
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
//        JPanel panel = new JPanel();
//        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
//        panel.setLayout(layout);
//
//        for (JComponent component : componentList)
//            panel.add(component);
//
//        return panel;
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
            case CUSTOMFUNCTION_TYPE:
                labels = CUSTOMFUNCTION_PARAMETERS;
                break;
            default:
                return null;
        }

        //Create and populate the panel.
        JPanel panel = new JPanel(new SpringLayout());
        for (String label : labels) {
            JLabel l = new JLabel(label, JLabel.TRAILING);
            panel.add(l);
            JComponent textField = new JTextField(8);
            if (label.equals("Action: ") || label.equals("Body: ") || label.equals("Parameters: ")) {
                textField = new JScrollPane(new JTextArea(10, 10));
                textField.setBorder(BorderFactory.createLineBorder(new Color(BORDER_COLOR, BORDER_COLOR, BORDER_COLOR)));
//                textField.setPreferredSize(new Dimension(400, 400));

            }
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

    public ActivityObject getActivityObject(int type, JPanel parameters) {
//        for (int i = 1; i < parameters.getComponentCount(); i += 2) {
//            Component component = parameters.getComponent(i);
//            System.out.println("Text: " + i + " \'" + ((JTextField) component).getText() + "\'");
//            if (i == 1)
//                ((JTextField) component).getText();
//        }

        ActivityObject object;
        String name = null;
        String text = null;
        String height = null;
        String width = null;
        String hint = null;
        String action = null;
        StringBuilder sb;

        try {
        name = ((JTextField) parameters.getComponent(1)).getText();
        text = ((JTextField) parameters.getComponent(3)).getText();
        height = ((JTextField) parameters.getComponent(5)).getText().trim();
        width = ((JTextField) parameters.getComponent(7)).getText().trim();
        } catch (ClassCastException ignored) {}

        System.out.println(height + ".");
        System.out.println(width);

        // auto-generate name if not specified
        if (StringHelper.isNullorEmpty(name))
            name = cmd.getNextDefaultObjectName();

        switch (type) {
            case BUTTON_TYPE:
                action = ((JTextArea) ((JViewport) ((JScrollPane) parameters.getComponent(9)).getComponent(0)).getComponent(0)).getText();
                object = new ButtonActivityObject(name, text, height, width, action);
                sb = new StringBuilder("button ");
                if (!StringHelper.isNullorEmpty(name))
                    sb.append("-name ").append(name).append(" ");
                if (!StringHelper.isNullorEmpty(text))
                    sb.append("-text ").append(text).append(" ");
                if (!StringHelper.isNullorEmpty(height))
                    sb.append("-height ").append(height).append(" ");
                if (!StringHelper.isNullorEmpty(width))
                    sb.append("-width ").append(width).append(" ");
                if (!StringHelper.isNullorEmpty(action))
                    sb.append("-action ").append(action);
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
                action = ((JTextArea) ((JViewport) ((JScrollPane) parameters.getComponent(13)).getComponent(0)).getComponent(0)).getText();
                object = new ContactsListActivityObject(name, height, width, action, !StringHelper.isNullorEmpty(hasName), !StringHelper.isNullorEmpty(hasNumber), divider);
                sb = new StringBuilder("contactslist ");
                if (!StringHelper.isNullorEmpty(name))
                    sb.append("-name ").append(name).append(" ");
                if (!StringHelper.isNullorEmpty(height))
                    sb.append("-height ").append(height).append(" ");
                if (!StringHelper.isNullorEmpty(width))
                    sb.append("-width ").append(width).append(" ");
                if (!StringHelper.isNullorEmpty(hasName))
                    sb.append("-hasName ").append("1 ");
                if (!StringHelper.isNullorEmpty(hasNumber))
                    sb.append("-hasNumber ").append("1 ");
                if (!StringHelper.isNullorEmpty(divider))
                    sb.append("-divider ").append(divider);
                cmd.parseCmd(sb.toString());
                break;
            case CUSTOMFUNCTION_TYPE:
                String returnType = ((JTextField) parameters.getComponent(3)).getText();
                String params = ((JTextArea) ((JViewport) ((JScrollPane) parameters.getComponent(5)).getComponent(0)).getComponent(0)).getText();
                String body = ((JTextArea) ((JViewport) ((JScrollPane) parameters.getComponent(7)).getComponent(0)).getComponent(0)).getText();
//                CustomFunction customFunction = new CustomFunction(name, body, returnType, StringHelper.stringToParams(params));
                object = null;
                sb = new StringBuilder("customfunction ");
                if (!StringHelper.isNullorEmpty(name))
                    sb.append("-name ").append(name).append(" ");
                else return null;
                if (!StringHelper.isNullorEmpty(returnType))
                    sb.append("-returnType ").append(returnType).append(" ");
                else return null;
                if (!StringHelper.isNullorEmpty(body))
                    sb.append("-body ").append(body).append(" ");
                else return null;
                if (!StringHelper.isNullorEmpty(params))
                    sb.append("-params ").append(params).append(" ");
                cmd.parseCmd(sb.toString());
                refreshFunctionPane();
                refreshPreviewPane();
                refreshParameterPane();
                break;
            default:
                return null;
        }
        return object;
    }

    public ArrayList<JComponent> getHierarchyList() {
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
        listPane.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
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
        listPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        optionsPanel.add(listPanel);

        layout.putConstraint(SpringLayout.NORTH, listPanel, 5, SpringLayout.SOUTH, title);
        return optionsPanel;
    }

    public ArrayList<JComponent> getPreviewList() {
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
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(BORDER_COLOR, BORDER_COLOR, BORDER_COLOR)));
        scrollPane.setPreferredSize(new Dimension(PREVIEW_WIDTH, PREVIEW_HEIGHT));
        componentList.add(scrollPane);

        return componentList;
    }

    public JPanel getFunctionsList() {
        SpringLayout layout = new SpringLayout();
        JPanel functionsPanel = new JPanel(layout);

        JLabel title = new JLabel("Functions");

        layout.putConstraint(SpringLayout.NORTH, title, 5, SpringLayout.NORTH, functionsPanel);
        functionsPanel.add(title);

        List<CustomFunction> functions = cmd.getFunctions();
        String[] items = new String [functions.size()];
        for (int i = 0; i < functions.size(); i++)
            items[i] = functions.get(i).getName();

        JPanel listPanel = new JPanel();
        final JList<String> list = new JList<String>(items); // Create the list
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
        listPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT - 100));
        functionsPanel.add(listPanel);

        layout.putConstraint(SpringLayout.NORTH, listPanel, 5, SpringLayout.SOUTH, title);

        JLabel label = new JLabel("");
        label.setPreferredSize(new Dimension(WINDOW_WIDTH, 100));

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmd.parseCmd("removefunction " + list.getSelectedIndex());

                refreshFunctionPane();
                refreshPreviewPane();
            }
        });
        functionsPanel.add(removeButton);

        layout.putConstraint(SpringLayout.NORTH, removeButton, 5, SpringLayout.SOUTH, listPanel);

        return functionsPanel;
    }

    private void refreshFunctionPane() {
        componentsPane.remove(functionPane);
        functionPane = getFunctionsList();
        functionPane.setPreferredSize(new Dimension(FUNCTIONS_WIDTH, FUNCTIONS_HEIGHT));
        componentsPane.add(functionPane, 2);
        componentsPane.revalidate();
    }

    private void refreshParameterPane() {
        componentsPane.remove(parameterPane);
        parameterPane = generatePanel(getParametersList());
        parameterPane.setPreferredSize(new Dimension(PARAMETERS_WIDTH, PARAMETERS_HEIGHT));
        componentsPane.add(parameterPane, 4);
        componentsPane.revalidate();
    }

    private void refreshHierarchyPane() {
        componentsPane.remove(hierarchyPane);
        hierarchyPane = generatePanel(getHierarchyList());
        hierarchyPane.setPreferredSize(new Dimension(HIERARCHY_WIDTH, HIERARCHY_HEIGHT));
        componentsPane.add(hierarchyPane, 6);
        componentsPane.revalidate();
    }

    private void refreshPreviewPane() {
        componentsPane.remove(previewPane);
        previewPane = generatePanel(getPreviewList());
        previewPane.setPreferredSize(new Dimension(PREVIEW_WIDTH, PREVIEW_HEIGHT));
        componentsPane.add(previewPane, 8);
        componentsPane.revalidate();
    }

    private void refreshAll() {
        refreshFunctionPane();
        refreshParameterPane();
        refreshHierarchyPane();
        refreshPreviewPane();
    }

    private void updateGlobalProjectProperties() {
        cmd.parseCmd("name " + headerFields.get(0).getText());
        cmd.parseCmd("package " + headerFields.get(1).getText());
        cmd.parseCmd("path " + headerFields.get(2).getText());
        cmd.parseCmd("main " + headerFields.get(3).getText());
        cmd.parseCmd("classname " + classNameField.getText());
    }
}
