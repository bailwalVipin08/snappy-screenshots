package snappyscreenshots;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws AWTException, IOException {
        Robot robot = new Robot(); // create a robot to take screenshot
        Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()); // define the area to capture (the current screen)

        // This will store screenshot on Specific location
        String outputDirectory = "/home/vb/Pictures/snappy-screenshots/";
        new File(outputDirectory).mkdir();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of images to capture : ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            BufferedImage image = robot.createScreenCapture(screen);
            // This will store screenshot on Specific location
            ImageIO.write(image, "png", new File(outputDirectory + "testImage" + i +".png"));
            System.out.println("Captured image : testImage"+i);
            robot.delay(2000); // capture screenshot after every 2 seconds from starting till 'n' images have been captured
        }
        System.out.println("Screen Captured Successfully. All files saved to " + outputDirectory);

    }
}
