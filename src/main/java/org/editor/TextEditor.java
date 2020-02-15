package org.editor;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.icons.FlatFileViewComputerIcon;
import com.formdev.flatlaf.icons.FlatFileViewFloppyDriveIcon;
import org.editor.command.*;
import org.editor.searcher.Searcher;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;

public class TextEditor extends JFrame {
    private static final Dimension FRAME_MIN_SIZE = new Dimension(550, 500);
    private static final Dimension UTIL_PANEL_MIN_SIZE = new Dimension(430, 25);
    private static final Dimension SEARCH_FIELD_MIN_SIZE = new Dimension(220, 22);
    private static final Border TEXT_PANEL_BORDER = BorderFactory.createEmptyBorder(0, 10, 10, 10);
    private static final Border UTIL_PANEL_BORDER = BorderFactory.createEmptyBorder(0, 5, 0, 10);

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
        setMinimumSize(FRAME_MIN_SIZE);
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
        utilPanel.setMinimumSize(UTIL_PANEL_MIN_SIZE);
        utilPanel.setSize(UTIL_PANEL_MIN_SIZE);
        utilPanel.setBorder(UTIL_PANEL_BORDER);

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
        searchField.setMinimumSize(SEARCH_FIELD_MIN_SIZE);
        searchField.setPreferredSize(SEARCH_FIELD_MIN_SIZE);

        JButton startSearchButton = new JButton("Search");
        startSearchButton.setToolTipText("Start Searching");
        startSearchButton.setName("StartSearchButton");
        startSearchButton.addActionListener(e -> new StartSearchCommand(this).execute());

        JButton previousMatchButton = new JButton();
        previousMatchButton.setIcon(new ImageIcon(getIcon("back.png")));
        previousMatchButton.setToolTipText("Previous Matched Group");
        previousMatchButton.setName("PreviousMatchButton");
        previousMatchButton.addActionListener(e -> new PreviousMatchCommand(this).execute());

        JButton nextMatchButton = new JButton();
        nextMatchButton.setIcon(new ImageIcon(getIcon("forward.png")));
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
        textPanel.setBorder(TEXT_PANEL_BORDER);

        textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setTabSize(4);
        textArea.setAutoscrolls(true);

        JScrollPane scrollPane = new JScrollPane(textArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setName("ScrollPane");

        textPanel.add(scrollPane, BorderLayout.CENTER);

        add(textPanel, BorderLayout.CENTER);
    }

    private URL getIcon(String filename) {
        return TextEditor.class.getResource("/icons/" + filename);
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
