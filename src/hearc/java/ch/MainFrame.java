package hearc.java.ch;/*
 * Project Name : swingJava
 * author : jonathan.guerne
 * create date : 09.03.2017
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainFrame extends JFrame
{

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu aboutMenu;
    private JMenuItem openMenuItem;
    private JMenuItem closeMenuItem;
    private JMenuItem infoMenuItem;

    String choosePath;

    JTextField txtFilePath;

    JLabel lblNumberOfFiles;

    JProgressBar progNumberFiles;

    JCheckBox checkSubFolders;


    public MainFrame() throws HeadlessException
    {
        this.setTitle("Number of files");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setSize(600, 250);

        choosePath = "";

        menuBar = new JMenuBar();

        // build the File menu
        fileMenu = new JMenu("File");
        openMenuItem = new JMenuItem("Open");
        closeMenuItem = new JMenuItem("Close");

        openMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                selectPath();
            }
        });
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));

        closeMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                System.exit(0);
            }
        });

        fileMenu.add(openMenuItem);
        fileMenu.add(closeMenuItem);

        // build the About menu
        aboutMenu = new JMenu("About");

        infoMenuItem = new JMenuItem("Info");

        infoMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                showAbout();
            }
        });
        aboutMenu.add(infoMenuItem);

        // add menus to menubar
        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);

        // put the menubar on the frame
        setJMenuBar(menuBar);

        GridLayout firstLayout = new GridLayout(5,0);
        this.setLayout(firstLayout);

        JPanel topLinePanel = new JPanel();
        topLinePanel.setLayout(new GridLayout(0,3));

        JLabel lblInfo = new JLabel("choose your path :");
        txtFilePath = new JTextField();
        JButton btnChoosePath = new JButton("Choose folder");

        btnChoosePath.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                selectPath();
            }
        });

        topLinePanel.add(lblInfo);
        topLinePanel.add(txtFilePath);
        topLinePanel.add(btnChoosePath);

        this.add(topLinePanel);

        checkSubFolders = new JCheckBox("Search in sub folders",true);

        lblNumberOfFiles = new JLabel("",SwingConstants.CENTER);

        JButton btnStart = new JButton("Start");

        btnStart.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                progNumberFiles.setIndeterminate(true);
                MySwingWorker mySwingWorker = new MySwingWorker(choosePath, checkSubFolders.isSelected());
                mySwingWorker.addPropertyChangeListener(new PropertyChangeListener()
                {
                    @Override
                    public void propertyChange(PropertyChangeEvent propertyChangeEvent)
                    {
                        if("numberOfFiles".equals(propertyChangeEvent.getPropertyName())){
                            //btn.setText((Integer)propertyChangeEvent.getNewValue()+"");
                            lblNumberOfFiles.setText((Integer)propertyChangeEvent.getNewValue()+"");
                            progNumberFiles.setValue(progNumberFiles.getValue()+1);
                        }
                        if("progress".equals(propertyChangeEvent.getPropertyName())){
                            //btn.setText((Integer)propertyChangeEvent.getNewValue()+"");
                            if((Integer)propertyChangeEvent.getNewValue()==100){
                                progNumberFiles.setIndeterminate(false);
                            }
                        }

                    }
                });
                mySwingWorker.execute();
            }
        });



        progNumberFiles = new JProgressBar();
        progNumberFiles.setValue(0);

        this.add(checkSubFolders);
        this.add(lblNumberOfFiles);
        this.add(btnStart);
        this.add(progNumberFiles);

        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        new MainFrame();
    }


    public void selectPath(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Choose your directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        chooser.showOpenDialog(this);

        if(chooser.getSelectedFile()!=null)
        {
            choosePath = chooser.getSelectedFile().toString();
            txtFilePath.setText(choosePath);
        }
    }

    public String getChoosePath()
    {
        return choosePath;
    }


    public void setChoosePath(String choosePath)
    {
        this.choosePath = choosePath;
    }

    public void showAbout(){
        JOptionPane.showMessageDialog(this,"Jonathan Guerne\nle 09/03/2017");

    }
}
