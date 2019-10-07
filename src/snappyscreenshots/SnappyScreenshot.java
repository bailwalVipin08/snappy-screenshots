package snappyscreenshots;

import java.time.LocalTime;

public class SnappyScreenshot {
    Time startTime;
    Time stopTime;
    int numOfScreenshots;
    long interval;
    String outputDir;

    SnappyScreenshot() {
        this.startTime = new Time(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond());
        this.interval = 2; // 2 seconds
        this.numOfScreenshots = 5;
        String username = System.getProperty("user.name");
        this.stopTime = null;
        this.outputDir = "/home/" + username + "/Pictures/snappy-screenshots";
    }
}
