package org.editor;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.icons.FlatFileViewComputerIcon;
import com.formdev.flatlaf.icons.FlatFileViewFloppyDriveIcon;
import org.editor.command.*;
import org.editor.searcher.Searcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class TextEditor extends JFrame {
    private static final int FRAME_MIN_WIDTH = 550;
    private static final int FRAME_MIN_HEIGHT = 500;
    private static final String RESOURCES_PATH = "src/main/resources/icons/";

    private JTextArea textArea;
    private JTextField searchField;
    private JFileChooser fileChooser;
    private JCheckBox useRegexCheckbox;
    private boolean isRegex = false;

    private Searcher searcher = new Searcher();

    public TextEditor() {
        super("Text Editor");
        FlatLightLaf.install();
        UIManager.put("Component.arrowType", "triangle");
        UIManager.put("Component.hideMnemonics", false);
        UIManager.put("ScrollBar.showButtons", true);
        UIManager.put("ScrollBar.width", 12);
        UIManager.put("Button.arc", 0);
        UIManager.put("Component.arc", 0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(FRAME_MIN_WIDTH, FRAME_MIN_HEIGHT));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        initMenu();
        initUtilPanel();
        initTextPanel();
        pack();
        setVisible(true);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuFile = new JMenu("File");
        menuFile.setName("MenuFile");
        menuFile.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menuFile);

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setName("MenuOpen");
        openMenuItem.addActionListener(e -> {
            try {
                new OpenCommand(this).execute();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        saveMenuItem.addActionListener(e -> {
            try {
                new SaveCommand(this).execute();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        JMenuItem saveAsMenuItem = new JMenuItem("Save as...");
        saveAsMenuItem.setName("MenuSaveAs");
        saveAsMenuItem.addActionListener(e -> {
            try {
                new SaveAsCommand(this).execute();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");
        exitMenuItem.addActionListener(e -> new ExitCommand(this).execute());

        menuFile.add(openMenuItem);
        menuFile.add(saveMenuItem);
        menuFile.add(saveAsMenuItem);
        menuFile.addSeparator();
        menuFile.add(exitMenuItem);

        JMenu menuSearch = new JMenu("Search");
        menuSearch.setName("MenuSearch");
        menuSearch.setMnemonic(KeyEvent.VK_S);
        menuBar.add(menuSearch);

        JMenuItem searchMenuItem = new JMenuItem("Start search");
        searchMenuItem.setName("MenuStartSearch");
        searchMenuItem.addActionListener(e -> new StartSearchCommand(this).execute());

        JMenuItem previousMatchMenuItem = new JMenuItem("Previous match");
        previousMatchMenuItem.setName("MenuPreviousMatch");
        previousMatchMenuItem.addActionListener(e -> new PreviousMatchCommand(this).execute());

        JMenuItem nextMatchMenuItem = new JMenuItem("Next match");
        nextMatchMenuItem.setName("MenuNextMatch");
        nextMatchMenuItem.addActionListener(e -> new NextMatchCommand(this).execute());

        JMenuItem useRegexMenuItem = new JMenuItem("Use regex");
        useRegexMenuItem.setName("MenuUseRegExp");
        useRegexMenuItem.addActionListener(e -> {
            if (useRegexCheckbox.isSelected()) {
                useRegexCheckbox.setSelected(false);
                isRegex = false;
            } else {
                useRegexCheckbox.setSelected(true);
                isRegex = true;
            }
        });

        menuSearch.add(searchMenuItem);
        menuSearch.add(previousMatchMenuItem);
        menuSearch.add(nextMatchMenuItem);
        menuSearch.add(useRegexMenuItem);
    }

    private void initUtilPanel() {
        JPanel utilPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        utilPanel.setMinimumSize(new Dimension(430, 25));
        utilPanel.setSize(new Dimension(430, 25));
        utilPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));

        fileChooser = new JFileChooser();
        fileChooser.setName("FileChooser");
        fileChooser.setDialogTitle("Select a file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        JButton openButton = new JButton();
        openButton.setIcon(new FlatFileViewComputerIcon());
        openButton.setToolTipText("Open File");
        openButton.setName("OpenButton");
        openButton.addActionListener(e -> {
            try {
                new OpenCommand(this).execute();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        JButton saveButton = new JButton();
        saveButton.setIcon(new FlatFileViewFloppyDriveIcon());
        saveButton.setToolTipText("Save File");
        saveButton.setName("SaveButton");
        saveButton.addActionListener(e -> {
            try {
                new SaveCommand(this).execute();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        searchField = new JTextField();
        searchField.setName("SearchField");
        searchField.setToolTipText("Enter Search Query");
        searchField.setMinimumSize(new Dimension(220, 22));
        searchField.setPreferredSize(new Dimension(220, 22));

        JButton startSearchButton = new JButton("Search");
        startSearchButton.setToolTipText("Start Searching");
        startSearchButton.setName("StartSearchButton");
        startSearchButton.addActionListener(e -> new StartSearchCommand(this).execute());

        JButton previousMatchButton = new JButton();
        previousMatchButton.setIcon(new ImageIcon(RESOURCES_PATH + "back.png"));
        previousMatchButton.setToolTipText("Previous Matched Group");
        previousMatchButton.setName("PreviousMatchButton");
        previousMatchButton.addActionListener(e -> new PreviousMatchCommand(this).execute());

        JButton nextMatchButton = new JButton();
        nextMatchButton.setIcon(new ImageIcon(RESOURCES_PATH + "forward.png"));
        nextMatchButton.setToolTipText("Next Matched Group");
        nextMatchButton.setName("NextMatchButton");
        nextMatchButton.addActionListener(e -> new NextMatchCommand(this).execute());

        useRegexCheckbox = new JCheckBox("Use regex");
        useRegexCheckbox.setName("UseRegExCheckbox");
        useRegexCheckbox.addActionListener(e -> isRegex = useRegexCheckbox.isSelected());

        utilPanel.add(openButton);
        utilPanel.add(saveButton);
        utilPanel.add(searchField);
        utilPanel.add(startSearchButton);
        utilPanel.add(previousMatchButton);
        utilPanel.add(nextMatchButton);
        utilPanel.add(useRegexCheckbox);

        add(utilPanel, BorderLayout.PAGE_START);
    }

    private void initTextPanel() {
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setAutoscrolls(true);

        JScrollPane scrollPane = new JScrollPane(textArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setName("ScrollPane");

        textPanel.add(scrollPane, BorderLayout.CENTER);

        add(textPanel, BorderLayout.CENTER);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    public boolean isRegex() {
        return isRegex;
    }

    public Searcher getSearcher() {
        return searcher;
    }
}
