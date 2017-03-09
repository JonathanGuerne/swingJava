package hearc.java.ch;/*
 * Project Name : swingJava
 * author : jonathan.guerne
 * create date : 09.03.2017
*/

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;

public class MySwingWorker extends SwingWorker<Integer, Integer>
{

    String pathToCheck;
    int numberOfFile;
    boolean searchInSubFolders;


    public MySwingWorker(String pathToCheck, boolean searchInSubFolders)
    {
        this.pathToCheck = pathToCheck;
        this.searchInSubFolders = searchInSubFolders;

    }

    @Override
    protected Integer doInBackground() throws Exception
    {
        int result = 0;
        numberOfFile = 0;
        firePropertyChange("numberOfFiles", 0, 0);
        if (pathToCheck != null)
        {
            result = countSubFolder(pathToCheck);
        }
        else
        {
            System.err.println("file path can not be empty");
        }
        numberOfFile = result;
        return result;
    }

    @Override
    protected void process(List<Integer> list)
    {
        super.process(list);
    }

    @Override
    protected void done()
    {
        super.done();
        setProgress(100);
    }

    public int countSubFolder(String path)
    {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        int number = 0;


        for (int i = 0; i < listOfFiles.length; i++)
        {
            if (listOfFiles[i].isFile())
            {
                number++;
                numberOfFile++;
                firePropertyChange("numberOfFiles", null, numberOfFile);
            }
            else if (listOfFiles[i].isDirectory())
            {
                if (searchInSubFolders)
                    number += countSubFolder(listOfFiles[i].getPath());
            }
        }
        return number;
    }
}
