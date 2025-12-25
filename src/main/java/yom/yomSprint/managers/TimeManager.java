package yom.yomSprint.managers;

public class TimeManager {

    public static String getTimeInSeconds(long time){

        long secTime = time/1000;
        long deTime = time/100;

        return String.valueOf(secTime+","+deTime);
    }

}
