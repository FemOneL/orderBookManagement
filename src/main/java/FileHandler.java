import items.Const;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    private static final String fileName = Const.FILE_NAME;
    private static final Application app = new Application();

    public void run() {
        getFromFile();
        writeInFile(app.getOutput());
    }


    public void getFromFile() {
        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while (bufferedReader.ready()) {
                app.execute(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeInFile(String output) {
        try (FileWriter fileWriter = new FileWriter("output.txt")) {
            fileWriter.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
