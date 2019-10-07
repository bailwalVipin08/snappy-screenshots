package snappyscreenshots;

public class Time {
    int hours;
    int minutes;
    int seconds;

    public Time(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public static Time difference(Time start, Time stop){

        Time diff = new Time(0, 0, 0);
        if(stop.seconds > start.seconds){
            --start.minutes;
            start.seconds += 60;
        }
        diff.seconds = start.seconds - stop.seconds;
        if(stop.minutes > start.minutes){
            --start.hours;
            start.minutes += 60;
        }
        diff.minutes = start.minutes - stop.minutes;
        diff.hours = start.hours - stop.hours;

        return diff;
    }

    public static Time parse(String timeString){
        Time t;
        String[] userTime = timeString.split(":");
        int[] time = new int[3];
        for(int i = 0; i < userTime.length; i++){
            time[i] = Integer.parseInt(userTime[i]);
        }
        return new Time(time[0], time[1], time[2]);
    }

    public static long getMilliSec(Time t){
        return (t.hours*3600000 + t.minutes*60000 + t.seconds*1000);
    }

}
