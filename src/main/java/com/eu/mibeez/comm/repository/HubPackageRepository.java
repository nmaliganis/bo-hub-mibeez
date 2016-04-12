package com.eu.mibeez.comm.repository;

public final class HubPackageRepository {
    private static HubPackageRepository uniqueInstance;

    private HubPackageRepository() {
        this.startOfMessageCode = '{';
        this.endOfMessageCode = '}';
        this.ackCode = '@';
        this.nackCode = '!';
        this.asyncTamperIdentifiedCode = '#';
        this.asyncTiltEventCode = '$';
        this.asyncBatteryLevelEventCode = '%';
        this.asyncInternalTempEventCode = '^';
        this.asyncExternalTempEventCode = '&';
        this.asyncGpsEventCode = '*';
        this.asyncConnectionRequestedCode = '-';

        this.startOfMessageLen = 2;
        this.lanAddressShellLen = 4;
        this.lanAddressHubLen = 4;
        this.commandLen = 1;
        this.messageLen = 40;
        this.crcLen = 1;
        this.endOfMessageLen = 2;


        this.packageLen = (byte)(startOfMessageLen
                + lanAddressShellLen
                + lanAddressHubLen
                + commandLen
                + messageLen
                + crcLen
                + endOfMessageLen);

        this.startOfMessageOffset = 0;
        this.lanAddressHubOffset = (byte) (startOfMessageLen + startOfMessageOffset);
        this.lanAddressShellOffset = (byte) (lanAddressHubLen + lanAddressHubOffset);
        this.commandOffset = (byte) (lanAddressShellLen + lanAddressShellOffset);
        this.messageOffset = (byte) (commandLen + commandOffset);
        this.crcOffset = (byte) (messageLen + messageOffset);
        this.endOfMessageOffset = (byte) (crcLen + crcOffset);
        this.currentPackageNumOffset = (byte)(commandOffset + 1);
        this.totalPackageLenOffset = (byte)(commandOffset + 2);

        this.packageToBeCheckedForCrcLen = (byte) (lanAddressHubLen + lanAddressShellLen + commandLen + messageLen);

        this.batteryValueOffset = messageOffset;
        this.tamperActionOffset = (byte)(batteryValueOffset + batteryValueLen);
        this.humidityValueOffset = (byte)(tamperActionOffset + tamperActionLen);
        this.externalTempValueOffset = (byte)(humidityValueOffset + humidityValueLen);
        this.internalTempValueOffset = (byte)(externalTempValueOffset  + externalTempValueLen);
        this.broodTempValueOffset = (byte)(internalTempValueOffset  + internalTempValueLen);
        this.tiltValueOffset = (byte)(broodTempValueOffset  + broodTempValueLen);
        this.latitudeValueOffset = (byte)(tiltValueOffset  + tiltValueLen);
        this.longitudeValueOffset = (byte)(latitudeValueOffset  + latitudeValueLen);
    }

    public static synchronized HubPackageRepository getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new HubPackageRepository();
        }
        return uniqueInstance;
    }

    private int batteryValueOffset;
    public int getBatteryValueOffset() {
        return batteryValueOffset;
    }

    private int tamperActionOffset;
    public int getTamperActionOffset() {
        return tamperActionOffset;
    }

    private int humidityValueOffset;
    public int getHumidityValueOffset() {
        return humidityValueOffset;
    }

    private int externalTempValueOffset;
    public int getExternalTempValueOffset() {
        return externalTempValueOffset;
    }

    private int internalTempValueOffset;
    public int getInternalTempValueOffset() {
        return internalTempValueOffset;
    }

    private int broodTempValueOffset;
    public int getBroodTempValueOffset() {
        return broodTempValueOffset;
    }

    private int tiltValueOffset;
    public int getTiltValueOffset() {
        return tiltValueOffset;
    }

    private int latitudeValueOffset;
    public int getLatitudeValueOffset() {
        return latitudeValueOffset;
    }

    private int longitudeValueOffset;
    public int getLongitudeValueOffset() {
        return longitudeValueOffset;
    }

    private final char startOfMessageCode;
    public char getStartOfMessageCode()
    {
        return startOfMessageCode;
    }
    private final char endOfMessageCode;
    public char getEndOfMessageCode()
    {
        return endOfMessageCode;
    }
    private final char ackCode;
    public char getAckCode()
    {
        return ackCode;
    }
    private final char nackCode;
    public char getNackCode()
    {
        return nackCode;
    }

    private final char asyncTamperIdentifiedCode;
    public char getAsyncTamperIdentifiedCode()
    {
        return asyncTamperIdentifiedCode;
    }

    private final char asyncTiltEventCode;
    public char getAsyncTiltEventCode()
    {
        return asyncTiltEventCode;
    }

    private final char asyncBatteryLevelEventCode;
    public char getAsyncBatteryLevelEventCode()
    {
        return asyncBatteryLevelEventCode;
    }

    private final char asyncInternalTempEventCode;
    public char getAsyncInternalTempEventCode()
    {
        return asyncInternalTempEventCode;
    }

    private final char asyncExternalTempEventCode;
    public char getAsyncExternalTempEventCode()
    {
        return asyncExternalTempEventCode;
    }

    private final char asyncGpsEventCode;
    public char getAsyncGpsEventCode()
    {
        return asyncGpsEventCode;
    }

    private final char asyncConnectionRequestedCode;
    public char getAsyncConnectionRequestedCode()
    {
        return asyncConnectionRequestedCode;
    }

    private final byte startOfMessageOffset;
    public byte getStartOfMessageOffset()
    {
        return startOfMessageOffset;
    }

    private final byte lanAddressShellOffset;
    public byte getLanAddressShellOffset()
    {
        return lanAddressShellOffset;
    }

    private final byte lanAddressHubOffset;
    public byte getLanAddressHubOffset()
    {
        return lanAddressHubOffset;
    }

    private final byte commandOffset;
    public byte getCommandOffset()
    {
        return commandOffset;
    }

    private final byte messageOffset;
    public byte getMessageOffset()
    {
        return messageOffset;
    }

    private final byte crcOffset;
    public byte getCrcOffset()
    {
        return crcOffset;
    }

    private final byte endOfMessageOffset;
    public byte getEndOfMessageOffset()
    {
        return endOfMessageOffset;
    }

    private final byte startOfMessageLen;
    public byte getStartOfMessageLen()
    {
        return startOfMessageLen;
    }

    private final byte lanAddressShellLen;
    public byte getLanAddressShellLen()
    {
        return lanAddressShellLen;
    }

    private final byte lanAddressHubLen;
    public byte getLanAddressHubLen()
    {
        return lanAddressHubLen;
    }

    private final byte commandLen;
    public byte getCommandLen()
    {
        return commandLen;
    }

    private final byte messageLen;
    public byte getMessageLen()
    {
        return messageLen;
    }

    private final byte crcLen;
    public byte getCrcLen()
    {
        return crcLen;
    }

    private final byte endOfMessageLen;
    public byte getEndOfMessageLen()
    {
        return endOfMessageLen;
    }

    private final byte currentPackageNumOffset;
    public byte getCurrentPackageNumOffset()
    {
        return currentPackageNumOffset;
    }

    private final byte totalPackageLenOffset;
    public byte getTotalPackageLenOffset() {
        return totalPackageLenOffset;
    }

    private final byte packageLen;
    public byte getPackageLen()
    {
        return packageLen;
    }

    public final byte packageToBeCheckedForCrcLen;
    public byte getPackageToBeCheckedForCrcLen()
    {
        return packageToBeCheckedForCrcLen;
    }


    public byte getBatteryValueLen() {
        return batteryValueLen;
    }

    private final byte batteryValueLen = 1;

    private final byte humidityValueLen = 1;
    public byte getHumidityValueLen() {
        return humidityValueLen;
    }

    private final byte longitudeValueLen = 4;
    public byte getLongitudeValueLen() {
        return longitudeValueLen;
    }

    private final byte latitudeValueLen = 4;
    public byte getLatitudeValueLen() {

        return latitudeValueLen;
    }

    private final byte internalTempValueLen = 2;
    public byte getInternalTempValueLen() {

        return internalTempValueLen;
    }

    private final byte broodTempValueLen = 2;
    public byte getBroodTempValueLen() {

        return broodTempValueLen;
    }

    private final byte externalTempValueLen = 2;
    public byte getExternalTempValueLen() {

        return externalTempValueLen;
    }

    private final byte tiltValueLen = 3;
    public byte getTiltValueLen() {

        return tiltValueLen;
    }

    private final byte tamperActionLen = 1;
    public byte getTamperActionLen() {

        return tamperActionLen;
    }
}
