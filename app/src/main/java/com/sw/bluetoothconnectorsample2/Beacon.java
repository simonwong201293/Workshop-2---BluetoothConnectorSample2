package com.sw.bluetoothconnectorsample2;

/**
 * Created by loretta on 11/23/16.
 */

public class Beacon {

    public Beacon(byte[] scanRecord, int rssi){
        this.rssi = rssi;
        this.id1 = BeaconParser.getUuidFromByteArray(scanRecord);
        this.id2 = String.valueOf(BeaconParser.getMajorFromByteArray(scanRecord));
        this.id3 = String.valueOf(BeaconParser.getMinorFromByteArray(scanRecord));
    }

    public String id1;
    public String id2;
    public String id3;
    public int rssi;
}
