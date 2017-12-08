package Peli;
 
import lejos.hardware.Button;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;
 
public class matka extends Thread {
   
    /**Luodaan tarvittavat muuttujat kaukosäätimelle, matkan mittaukselle ja tanssille*/
    static boolean tanssi;
    private EV3IRSensor infraredSensor;
    private EV3TouchSensor touchSensor;
    private static int remoteCommand0, remoteCommand1, remoteCommand2, remoteCommand3;
    private static int dist, mode;
   
    /**Metodi joka antaa sensoreille nimet*/
    public matka(final EV3IRSensor sensor, final EV3TouchSensor sensor2){
        this.infraredSensor = sensor;
        this.touchSensor = sensor2;
    }
   
    @Override
    public void run() {
        while (Button.ESCAPE.isUp()) {
           
            /**Tällä koodi pätkällä laskemme keskiarvon infrapunasensorin lukemista etäisyyksistä*/
            SampleProvider distance= infraredSensor.getMode("Distance"); // get an instance of this sensor in measurement mode
            SampleProvider average = new MeanFilter(distance, 3); // stack a filter on the sensor that gives the running average of the last 5 samples
            float[] sample = new float[average.sampleSize()]; // initialize an array of floats for fetching samples
            average.fetchSample(sample, 0); // fetch a sample
            dist = (int) sample[0];
            average = new MeanFilter(distance, 3);
            sample = new float[average.sampleSize()];
            average.fetchSample(sample, 0);                              
            dist = (int) sample[0];
            float[] sample2 = new float[touchSensor.sampleSize()];
            touchSensor.fetchSample(sample2, 0);
            mode = (int) sample2[0];
           
            /**Tämä if-lauseke aloittaa ja lopettaa tanssimisen*/
            if (mode != 0) {
                if (tanssi != true); {
                    tanssi = true;
                    Delay.msDelay(500);
                    while (tanssi == true) {
                        Delay.msDelay(50);
                        touchSensor.fetchSample(sample2, 0);
                        mode = (int) sample2[0];
                        if (mode != 0) {
                            tanssi = false;
                            Delay.msDelay(500);
                        }
                    }        
                }                
            }  
           
            /**Kaukosäätimen eri kanavat konfiguroituna*/
            remoteCommand0 = infraredSensor.getRemoteCommand(0);
            remoteCommand1 = infraredSensor.getRemoteCommand(1);
            remoteCommand2 = infraredSensor.getRemoteCommand(2);
            remoteCommand3 = infraredSensor.getRemoteCommand(3);
        }
    }
   
    /**Yhteensä neljä metodia kaukosäätimen neljälle eri kanavalle*/
    public static int getKauko0() {
            return remoteCommand0;      
    }
   
    public static int getKauko1() {
        return remoteCommand1;      
    }
   
    public static int getKauko2() {
        return remoteCommand2;      
    }
   
    public static int getKauko3() {
        return remoteCommand3;      
    }
   
    /**Metodi etäisyyden mittaamiseen*/
    public static int getEtaisyys() {
            return dist;
    }
   
    /**Metodi tanssimiseen*/
    public static boolean getTanssi() {
            return tanssi;
    }
}