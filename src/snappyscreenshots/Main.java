package snappyscreenshots;


import org.apache.commons.cli.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;

import static java.lang.Integer.parseInt;

// apache commons cli files for creating command line arguments

class Main {
    public static void main(String[] args) throws AWTException, IOException, InterruptedException {
        //creating a robot to take screenshots
        Robot robot = new Robot();
        Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()); // capture entire screen

        SnappyScreenshot ss = new SnappyScreenshot();

        // adding command line options for program

        Options options = new Options();

        // add option for "start time"
        Option s = Option.builder()
                .longOpt("start")
                .argName("start time")
                .hasArg()
                .desc("start capturing screenshot from the specified time, default value is your current system time")
                .build();

        options.addOption(s);

        // add option for "stop time"
        Option e = Option.builder()
                .longOpt("stop")
                .argName("stop time")
                .hasArg()
                .desc("stop capturing screenshot at the specified time")
                .build();

        options.addOption(e);

        // add option for "time interval"
        Option i = Option.builder()
                .longOpt("interval")
                .argName("time interval")
                .hasArg()
                .desc("time interval between successive captured screenshots, default value is 2 sec")
                .build();

        options.addOption(i);

        // add option for "number of screenshots"
        Option n = Option.builder()
                .longOpt("nos")
                .argName("number of screenshots")
                .hasArg()
                .desc("number of screenshots to take, default value is 5")
                .build();

        options.addOption(n);

        // add option for "output directory"
        Option d = Option.builder()
                .longOpt("dir")
                .argName("output directory")
                .hasArg()
                .desc("directory to save the captured screenshots, default value is snappy-screenshots folder in user's pictures folder")
                .build();

        options.addOption(d);

        //add option for help
        options.addOption("h", "help", false, "snappy-screenshot detailed help message.");

        HelpFormatter formatter = new HelpFormatter();

        //if no argument is supplied show error and usage guide
        if (args.length == 0) {
            final PrintWriter writer = new PrintWriter(System.out);
            System.out.println("error: You have not specified any argument, you must supply at least one argument.");
            formatter.printUsage(writer, 100, "snappy-screenshot", options);
            writer.flush();
            System.out.println("For more information use --help argument.");
            System.exit(0);
        }

        //apache command line parser to parse command line arguments
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (MissingArgumentException exp) {
            System.out.println(exp.getMessage());
        } catch (NullPointerException npe) {
            System.out.print("");
        } catch (ParseException ex) {
            ex.printStackTrace();
        }


        Time now = new Time(0, 0, 0);

        // check the presence of different command line arguments
        assert cmd != null;
        if (cmd.hasOption("start")) {
            now = new Time(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond());
            ss.startTime = Time.parse(cmd.getOptionValue("start"));
        }
        if (cmd.hasOption("interval")) {
            ss.interval = parseInt(cmd.getOptionValue("interval"));
        }
        if (cmd.hasOption("stop")) {
            ss.stopTime = Time.parse(cmd.getOptionValue("stop"));
            Time timeDif = Time.difference(ss.stopTime, ss.startTime);
            ss.numOfScreenshots = (int) (Time.getMilliSec(timeDif) / (ss.interval * 1000L)) + 1;
        }
        if (cmd.hasOption("dir")) {
            ss.outputDir = cmd.getOptionValue("dir");
        }
        if (cmd.hasOption("nos")) {
            ss.numOfScreenshots = parseInt(cmd.getOptionValue("nos"));
        }
        if (cmd.hasOption("help")) {
            formatter.printHelp("snappy-screenshot [arguments...]", options);
            System.exit(0);
        }

        if (Time.getMilliSec(Time.difference(ss.startTime, now)) > 0) {
            System.out.println("Starting Screenshot capture in ..." + Time.getMilliSec(Time.difference(ss.startTime, now)) / 1000 + " seconds");
            Thread.sleep(Time.getMilliSec(Time.difference(ss.startTime, now)));
        } else {
            System.out.println("Time Error : Enter a future value of start time ");
            System.exit(0);
        }

        createOutputDir(ss.outputDir); //create output directory if does not exists already

        //start capturing screenshots
        for (int k = 0; k < ss.numOfScreenshots; ++k) {
            BufferedImage image = robot.createScreenCapture(screen);
            ImageIO.write(image, "png", new File(ss.outputDir + "screenshot" + k + ".png")); //save screenshot image
            System.out.println("Captured image : screenshot" + k); //display name of screenshot file
            robot.delay((int) ss.interval * 1000); //delay the robot for "interval" amount of time before capturing next screenshot
        }
        System.out.println("\nAll Files saved to : " + ss.outputDir);
    }//end of main

    //method to create output directory
    private static void createOutputDir(String dir) {
        new File(dir).mkdir();
    }
}
