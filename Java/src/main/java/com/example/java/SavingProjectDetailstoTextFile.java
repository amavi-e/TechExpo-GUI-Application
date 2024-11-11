package com.example.java;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class SavingProjectDetailstoTextFile  extends BaseController{


    private HashMap<String, HashMap<String, Object>> projectDetails = getProjectDetails();

    public void createProjectDetailstoTextFile() throws IOException {
        FileWriter writer = new FileWriter("Project Details.txt");
        for (String id : projectDetails.keySet()) {
            writer.append(id + projectDetails.get(id) +"\n");
        }
        writer.close();
        showAlert("Text File", "Text File updated successfully");
    }
}


