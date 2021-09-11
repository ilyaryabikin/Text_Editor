package org.editor;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.icons.FlatOptionPaneErrorIcon;
import com.formdev.flatlaf.icons.FlatOptionPaneInformationIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;
import org.editor.command.ExitCommand;
import org.editor.command.NextMatchCommand;
import org.editor.command.OpenCommand;
import org.editor.command.PreviousMatchCommand;
import org.editor.command.SaveAsCommand;
import org.editor.command.SaveCommand;
import org.editor.command.StartSearchCommand;
import org.editor.exception.NoSpecifiedQueryException;
import org.editor.searcher.Searcher;
import org.kordamp.ikonli.icomoon.Icomoon;
import org.kordamp.ikonli.swing.FontIcon;

public class TextEditor extends JFrame {

    private static final Dimension FRAME_MIN_SIZE = new Dimension(550, 500);
    private static final Border TEXT_PANEL_BORDER = BorderFactory.createEmptyBorder(0, 5, 10, 5);
    private static final Color ICONS_COLOR = new Color(110, 110, 110);

    private JTextArea textArea;
    private JTextField searchField;
    private JFileChooser fileChooser;
    private JCheckBox useRegexCheckbox;

    private final Searcher searcher = new Searcher();

    public TextEditor() {
        super("Text Editor");

        initDefaultFrameProperties();

        initMenu();
        initUtilPanel();
        initTextPanel();

        pack();

        setVisible(true);
    }

    private void initDefaultFrameProperties() {
        FlatLightLaf.install();

        UIManager.put("Component.arrowType", "triangle");
        UIManager.put("Component.hideMnemonics", false);
        UIManager.put("ScrollBar.showButtons", true);
        UIManager.put("ScrollBar.width", 12);
        UIManager.put("Button.arc", 0);
        UIManager.put("Component.arc", 0);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(FRAME_MIN_SIZE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
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
                new OpenCommand(fileChooser, textArea).execute();
            } catch (IOException ex) {
                showErrorPane(ex.getMessage());
            }
        });

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        saveMenuItem.addActionListener(e -> {
            try {
                new SaveCommand(fileChooser, textArea).execute();
            } catch (IOException ex) {
                showErrorPane(ex.getMessage());
            }
        });

        JMenuItem saveAsMenuItem = new JMenuItem("Save as...");
        saveAsMenuItem.setName("MenuSaveAs");
        saveAsMenuItem.addActionListener(e -> {
            try {
                new SaveAsCommand(fileChooser, textArea).execute();
            } catch (IOException ex) {
                showErrorPane(ex.getMessage());
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
        searchMenuItem.addActionListener(e -> {
            try {
                new StartSearchCommand(searcher, textArea, searchField).execute();
            } catch (NoSpecifiedQueryException ex) {
                showMessagePane(ex.getMessage());
            }
        });

        JMenuItem previousMatchMenuItem = new JMenuItem("Previous match");
        previousMatchMenuItem.setName("MenuPreviousMatch");
        previousMatchMenuItem.addActionListener(e -> {
            try {
                new PreviousMatchCommand(searcher, textArea).execute();
            } catch (Exception ex) {
                showMessagePane(ex.getMessage());
            }
        });

        JMenuItem nextMatchMenuItem = new JMenuItem("Next match");
        nextMatchMenuItem.setName("MenuNextMatch");
        nextMatchMenuItem.addActionListener(e -> {
            try {
                new NextMatchCommand(searcher, textArea).execute();
            } catch (Exception ex) {
                showMessagePane(ex.getMessage());
            }
        });

        JMenuItem useRegexMenuItem = new JMenuItem("Use regex");
        useRegexMenuItem.setName("MenuUseRegExp");
        useRegexMenuItem.addActionListener(e -> {
            if (useRegexCheckbox.isSelected()) {
                useRegexCheckbox.setSelected(false);
                searcher.setRegex(false);
            } else {
                useRegexCheckbox.setSelected(true);
                searcher.setRegex(true);
            }
        });

        menuSearch.add(searchMenuItem);
        menuSearch.add(previousMatchMenuItem);
        menuSearch.add(nextMatchMenuItem);
        menuSearch.add(useRegexMenuItem);
    }

    private void initUtilPanel() {
        JPanel utilPanel = new JPanel();
        utilPanel.setLayout(new MigLayout("", "[][][grow][][][][]"));

        fileChooser = new JFileChooser();
        fileChooser.setName("FileChooser");
        fileChooser.setDialogTitle("Select a File");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        JButton openButton = new JButton();
        openButton.setIcon(FontIcon.of(Icomoon.ICM_FOLDER_OPEN, ICONS_COLOR));
        openButton.setToolTipText("Open File");
        openButton.setName("OpenButton");
        openButton.addActionListener(e -> {
            try {
                new OpenCommand(fileChooser, textArea).execute();
            } catch (IOException ex) {
                showErrorPane(ex.getMessage());
            }
        });

        JButton saveButton = new JButton();
        saveButton.setIcon(FontIcon.of(Icomoon.ICM_FLOPPY_DISK, ICONS_COLOR));
        saveButton.setToolTipText("Save File");
        saveButton.setName("SaveButton");
        saveButton.addActionListener(e -> {
            try {
                new SaveCommand(fileChooser, textArea).execute();
            } catch (IOException ex) {
                showErrorPane(ex.getMessage());
            }
        });

        searchField = new JTextField();
        searchField.setName("SearchField");
        searchField.setToolTipText("Enter Search Query");

        JButton startSearchButton = new JButton();
        startSearchButton.setIcon(FontIcon.of(Icomoon.ICM_SEARCH, ICONS_COLOR));
        startSearchButton.setToolTipText("Start Searching");
        startSearchButton.setName("StartSearchButton");
        startSearchButton.addActionListener(e -> {
            try {
                new StartSearchCommand(searcher, textArea, searchField).execute();
            } catch (NoSpecifiedQueryException ex) {
                showMessagePane(ex.getMessage());
            }
        });

        JButton previousMatchButton = new JButton();
        previousMatchButton.setIcon(FontIcon.of(Icomoon.ICM_ARROW_LEFT2, ICONS_COLOR));
        previousMatchButton.setToolTipText("Previous Matched Group");
        previousMatchButton.setName("PreviousMatchButton");
        previousMatchButton.addActionListener(e -> {
            try {
                new PreviousMatchCommand(searcher, textArea).execute();
            } catch (Exception ex) {
                showMessagePane(ex.getMessage());
            }
        });

        JButton nextMatchButton = new JButton();
        nextMatchButton.setIcon(FontIcon.of(Icomoon.ICM_ARROW_RIGHT2, ICONS_COLOR));
        nextMatchButton.setToolTipText("Next Matched Group");
        nextMatchButton.setName("NextMatchButton");
        nextMatchButton.addActionListener(e -> {
            try {
                new NextMatchCommand(searcher, textArea).execute();
            } catch (Exception ex) {
                showMessagePane(ex.getMessage());
            }
        });

        useRegexCheckbox = new JCheckBox("Use regex");
        useRegexCheckbox.setName("UseRegExCheckbox");
        useRegexCheckbox.setToolTipText("Handle Search Query as Regular Expression");
        useRegexCheckbox.addActionListener(e -> searcher.setRegex(useRegexCheckbox.isSelected()));

        utilPanel.add(openButton, "gap");
        utilPanel.add(saveButton, "gap");
        utilPanel.add(searchField, "gap, growx");
        utilPanel.add(startSearchButton, "gap");
        utilPanel.add(previousMatchButton, "gap");
        utilPanel.add(nextMatchButton, "gap");
        utilPanel.add(useRegexCheckbox, "gap");

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

    private void showErrorPane(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE,
                new FlatOptionPaneErrorIcon()
        );
    }

    private void showMessagePane(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Info",
                JOptionPane.INFORMATION_MESSAGE,
                new FlatOptionPaneInformationIcon()
        );
    }
}
