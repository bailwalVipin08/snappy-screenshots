package snappyscreenshots;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws AWTException, IOException {
        Robot robot = new Robot(); // create a robot to take screenshot
        Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()); // define the area to capture (the current screen)
        BufferedImage image = robot.createScreenCapture(screen);
        // This will store screenshot on Specific location
        String outputDirectory = "/home/vb/Pictures/snappy-screenshots/";
        new File(outputDirectory).mkdir(); // creating the output directory to save the captured screenshot
        ImageIO.write(image, "png", new File(outputDirectory + "testImage.png")); // write the file
        System.out.println("Screen Captured Successfully. File saved to " + outputDirectory);
    }
}
