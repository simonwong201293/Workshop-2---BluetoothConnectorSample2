package com.sw.bluetoothconnectorsample2;

/**
 * Created by loretta on 11/23/16.
 */

public class BeaconParser {

    public static int getMajorFromByteArray(byte[] advertisement) {
        return ((advertisement[25] & 0xFF) << 8)
                + (advertisement[26] & 0xFF);
    }


    public static int getMinorFromByteArray(byte[] advertisement) {
        return ((advertisement[27] & 0xFF) << 8)
                + (advertisement[28] & 0xFF);
    }

    private static String To2HexStr(byte paramByte) {
        String str2 = Integer.toHexString(paramByte & 0xFF);
        String str1 = str2;
        if ((paramByte & 0xFF) <= 15) {
            str1 = "0" + str2;
        }
        return str1;
    }

    public static String getUuidFromByteArray(byte[] advertisement) {
        byte[] uuid = new byte[16];
        for (int i = 0; i < (byte) 16; i = (byte) (i + 1)) {
            uuid[i] = (advertisement[(byte) 9 + i]);
        }
        return new StringBuilder(
                String.valueOf(new StringBuilder(String
                        .valueOf(new StringBuilder(String
                                .valueOf(new StringBuilder(String
                                        .valueOf(To2HexStr(uuid[0])))
                                        .append(To2HexStr(uuid[1]))
                                        .append(To2HexStr(uuid[2]))
                                        .append(To2HexStr(uuid[3]))
                                        .append("-").toString()))
                                .append(To2HexStr(uuid[4]))
                                .append(To2HexStr(uuid[5])).append("-")
                                .toString())).append(To2HexStr(uuid[6]))
                        .append(To2HexStr(uuid[7])).append("-").toString()))
                .append(To2HexStr(uuid[8])).append(To2HexStr(uuid[9]))
                .append("-").toString()
                + To2HexStr(uuid[10])
                + To2HexStr(uuid[11])
                + To2HexStr(uuid[12])
                + To2HexStr(uuid[13])
                + To2HexStr(uuid[14]) + To2HexStr(uuid[15]);
    }
}
