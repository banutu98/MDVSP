package data.dao.jpa;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Singleton
public class TimerBean {

    private static final String DIR_PATH = "C:\\Users\\tatu georgian\\Desktop\\MDVSP\\src\\main\\resources\\pages";

    @Schedule(hour = "*", minute = "*", second = "*/10")
    public void createPage(){
        Date now = new Date();
        String currentTime = now.toString().replace(":", "-");
        String fileName = currentTime + ".html";
        Path path = Paths.get(DIR_PATH, fileName);
        try {
            Files.createFile(path);
            String content = "Created at " + now.toString();
            Files.write(path, content.getBytes());
        }catch (IOException e){
            System.out.println("Could not create file");
        }
    }
}
