package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class SwingApp {

    private static final int DEF_WIDTH = 800;
    private static final int DEF_HEIGHT = 600;

    private static void initUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Easy Android");
        frame.setSize(DEF_WIDTH, DEF_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
        frame.setJMenuBar(getMenu());
//        frame.getContentPane().add(projectName, BorderLayout.PAGE_START);
//        frame.getContentPane().add(packageName, BorderLayout.PAGE_START);


        addComponentsToPane(frame.getContentPane());
//        frame.getContentPane().add(background, BorderLayout.CENTER);
//        frame.getContentPane().add(label, BorderLayout.CENTER);

        JSeparator jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        jSeparator.setForeground(new Color(0, 50, 255));
        jSeparator.setBackground(new Color(0, 50, 255));
        frame.getContentPane().add(jSeparator);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
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
        JLabel options = new JLabel("Options");
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 500;
        c.weightx = 1.0;   //request any extra vertical space
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridx = 0;       //aligned with button 2
        c.gridy = 2;       //third row
        pane.add(options, c);

        JLabel define = new JLabel("Define");
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 500;
        c.weightx = 1.0;   //request any extra vertical space
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.FIRST_LINE_START; //bottom of space
        c.gridx = 1;       //aligned with button 2
        c.gridy = 2;       //third row
        define.setVerticalTextPosition(SwingConstants.TOP);
        pane.add(define, c);

        JLabel hierarchy = new JLabel("Hierarchy");
        define.setVerticalTextPosition(SwingConstants.TOP);
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 500;
        c.weightx = 1.0;   //request any extra vertical space
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.gridx = 2;       //aligned with button 2
        c.gridy = 2;       //third row
        pane.add(hierarchy, c);

        JLabel preview = new JLabel("Preview");
        define.setVerticalTextPosition(SwingConstants.BOTTOM);
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 500;
        c.weightx = 1.0;   //request any extra vertical space
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.gridx = 3;       //aligned with button 2
        c.gridy = 2;       //third row
        pane.add(preview, c);
    }


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initUI();
            }
        });
    }
}
