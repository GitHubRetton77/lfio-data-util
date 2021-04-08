package io.lightfeather;


import io.lightfeather.models.KanbanTicket;
import io.lightfeather.repositories.KanbanTicketJPADatabase;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class LFIODataUtil implements CommandLineRunner {

    @Autowired
    private KanbanTicketJPADatabase kanbanTicketJPADatabase;

    public static void main(String[] args) {
        SpringApplication.run(LFIODataUtil.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        JSONObject configurationJSONObject = getConfigurationJSONObject();
        try {
            String dataBackupFilePath = (String) configurationJSONObject.get("Loads");
            FileInputStream dataInputStream = new FileInputStream(dataBackupFilePath);
            String dataString = convertInputStreamToString(dataInputStream);
            loadData(dataString, configurationJSONObject);
        } catch (FileNotFoundException e) {
            backupData(configurationJSONObject);
        }


    }

    private JSONObject getConfigurationJSONObject() {
        InputStream fileContentInputStream = this.getFileFromResourceAsStream("cipher-configuration.json");
        String fileContent = convertInputStreamToString(fileContentInputStream);
        JSONObject cipherConfigJSONObject = new JSONObject(fileContent);
        return cipherConfigJSONObject;
    }

    private void backupData(JSONObject configurationJSONObject) {
        String dataString = getStringDataFromDatabase();

        Integer cipherShiftConfig = (Integer) configurationJSONObject.get("Shift");
        String cipheredDataString = cipherStringWithShift(dataString, cipherShiftConfig);

        String backupDataFilePath = configurationJSONObject.getString("Loads");
        writeToFile(backupDataFilePath, cipheredDataString);
    }

    private String getStringDataFromDatabase() {
        String stringData = "";

        List<KanbanTicket> storedData = kanbanTicketJPADatabase.findAll();
        for(KanbanTicket kanbanTicket: storedData){
            stringData +=  kanbanTicket.toString() + "\n";
        }

        stringData.trim();
        return stringData;
    }

    private void loadData(String dataString, JSONObject configurationJSONObject) throws Exception {

        String decipheredDataString = decipherStringWithShift(dataString, configurationJSONObject.getInt("Shift"));

        List<KanbanTicket> listOfKanbanTicket = getListOfKanbanTicketDataObjectFromDataString(decipheredDataString);


        kanbanTicketJPADatabase.deleteAll();

        kanbanTicketJPADatabase.saveAll(listOfKanbanTicket);

    }

    private List<KanbanTicket> getListOfKanbanTicketDataObjectFromDataString(String dataString) throws Exception {
        List<KanbanTicket> listOfKanbanTicket = new ArrayList<>();

        String[] dataRows = dataString.split("\n");
        for(String dataRow : dataRows){
            listOfKanbanTicket.add(new KanbanTicket(dataRow));
        }
        
        return listOfKanbanTicket;
    }


    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    private void writeToFile(String filePath, String data) {
        try {
            File newFile = new File(filePath);
            if (newFile.createNewFile()) {
                FileWriter myWriter = new FileWriter(filePath);
                myWriter.write(data);
                myWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) {

        String newLine = System.getProperty("line.separator");
        String result = "";
        try {
            Stream<String> lines = new BufferedReader(new InputStreamReader(inputStream)).lines();
            result = lines.collect(Collectors.joining(newLine));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    private static String cipherStringWithShift(String readableString, int cipherShiftConfig) {

        String cipheredContent = "";
        for (int i = 0; i < readableString.length(); i++) {
            // Java auto casts char types to the ASCII number representation
            int charASCIINumber = readableString.charAt(i);
            int cipherASCIINumber = charASCIINumber + (int) cipherShiftConfig;
            char cipheredChar = (char) cipherASCIINumber;
            cipheredContent += cipheredChar;
        }

        System.out.println(cipheredContent);

        return cipheredContent;

    }

    private static String decipherStringWithShift(String cipheredString, int cipherShiftConfig) {

        String decipheredContent = "";
        for (int i = 0; i < cipheredString.length(); i++) {
            // Java auto casts char types to the ASCII number representation and vice versa
            int charASCIINumber = cipheredString.charAt(i);
            int decipherASCIINumber = charASCIINumber - (int) cipherShiftConfig;
            char decipheredChar = (char) decipherASCIINumber;
            decipheredContent += decipheredChar;
        }

        System.out.println(decipheredContent);

        return decipheredContent;
    }


}
