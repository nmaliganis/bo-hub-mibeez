package com.eu.mibeez.comm.crc;

import com.eu.mibeez.comm.repository.HubPackageRepository;

public class HubCrcBuilder implements CrcBuilderOperations
{
    public HubCrcBuilder(){
    }

    @Override
    public byte build(byte[] buffer)
    {
        byte[] packageToBeCheckedForCrc = new byte[HubPackageRepository
                .getInstance().getPackageToBeCheckedForCrcLen()];
        System.arraycopy(
                buffer,
                HubPackageRepository.getInstance().getLanAddressHubOffset(),
                packageToBeCheckedForCrc,
                0,
                HubPackageRepository.getInstance().getPackageToBeCheckedForCrcLen()
        );

        return buildCrc(packageToBeCheckedForCrc);
    }

    private byte buildCrc(byte[] packageToBeCheckedForCrc)
    {
        byte theCrc = 0x00;

        for(int x = 0; x < HubPackageRepository.getInstance().getPackageToBeCheckedForCrcLen(); x++)
        {
            theCrc = checkCrc(theCrc, packageToBeCheckedForCrc[x]);
        }
        return theCrc;
    }

    private byte checkCrc(byte oldByte, byte newByte)
    {
        byte shiftReg = oldByte;

        for (byte j = 0; j < 8; j++)
        {
            byte dataBit = (byte)((newByte >> j) & 0x01);
            byte srLsb = (byte)(shiftReg & 0x01);
            byte fbBit = (byte)((dataBit ^ srLsb) & 0x01);
            shiftReg = (byte)(shiftReg >> 1);
            if (fbBit == 0x01)
                shiftReg = (byte)(shiftReg ^ 0x8c);
        }
        return (shiftReg);
    }
}
