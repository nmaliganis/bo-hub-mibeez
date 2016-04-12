package com.eu.mibeez.comm.checker;

import com.eu.mibeez.comm.crc.CrcBuilderOperations;
import com.eu.mibeez.comm.crc.HubCrcBuilder;
import com.eu.mibeez.comm.exception.InvalidPackageCommandException;
import com.eu.mibeez.comm.exception.InvalidPackageCrcException;
import com.eu.mibeez.comm.exception.InvalidPackageEndOfMessageException;
import com.eu.mibeez.comm.exception.InvalidPackageStartOfMessageException;
import com.eu.mibeez.comm.repository.HubPackageRepository;
import org.slf4j.LoggerFactory;

public final class HubPackageChecker implements PackageCheckerOperations {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HubPackageChecker.class);

    private static HubPackageChecker uniqueInstance;
    private byte[] hubBuffer;

    private HubPackageChecker(){
    }

    public static synchronized HubPackageChecker getChecker() {
        if (uniqueInstance == null) {
            uniqueInstance = new HubPackageChecker();
            logger.debug("------------------------------------------------");
            logger.debug("------------{ Hub Package Checker Initialized }--------------");
            logger.debug("------------------------------------------------");
        }
        return uniqueInstance;
    }

    @Override
    public void check(byte[] buffer, byte command, boolean checkCrc) {
        hubBuffer = buffer;
        checkStartOfMessage();
        if (command != 0x00)
        {
            checkCommandCode(command);
        }
        if (checkCrc)
        {
            checkCrcOfMessage();
        }
        checkEndOfMessage();
    }

    @Override
    public boolean isEndOfReceivedPackage(byte[] buffer) {
        return (buffer.length > 2)
                && (buffer[buffer.length - 1] == HubPackageRepository.getInstance().getEndOfMessageCode())
                && (buffer[buffer.length - 2] == HubPackageRepository.getInstance().getEndOfMessageCode());
    }

    private void checkCommandCode(byte command)
    {
        if(command != 0x00)
        {
            if (hubBuffer[HubPackageRepository.getInstance().getCommandOffset()] != command)
                throw new InvalidPackageCommandException("Error on Checking Command of Message", hubBuffer[HubPackageRepository.getInstance().getCommandOffset()]);
        }
    }

    private void checkEndOfMessage()
    {
        if (hubBuffer[HubPackageRepository.
                getInstance().getEndOfMessageOffset()] !=
                HubPackageRepository.getInstance().getEndOfMessageCode() ||
                hubBuffer[HubPackageRepository.
                        getInstance().getEndOfMessageOffset() + 1] !=
                        HubPackageRepository.getInstance().getEndOfMessageCode())
        {
            throw new InvalidPackageEndOfMessageException("Error on Checking End of Message",
                    new byte[]
                            {
                                    hubBuffer[HubPackageRepository.
                                            getInstance().getEndOfMessageOffset()],
                                    hubBuffer[HubPackageRepository.
                                            getInstance().getEndOfMessageOffset() + 1]
                            });
        }
    }

    private void checkStartOfMessage()
    {
        if (hubBuffer[HubPackageRepository.
                getInstance().getStartOfMessageOffset()] !=
                HubPackageRepository.getInstance().getStartOfMessageCode() ||
                hubBuffer[HubPackageRepository.
                        getInstance().getStartOfMessageOffset() + 1] !=
                        HubPackageRepository.getInstance().getStartOfMessageCode())
        {
            throw new InvalidPackageStartOfMessageException("Error on Checking Start of Message",
                    new byte[]
                            {
                                    hubBuffer[HubPackageRepository.
                                            getInstance().getStartOfMessageOffset()],
                                    hubBuffer[HubPackageRepository.
                                            getInstance().getStartOfMessageOffset() + 1]
                            });
        }
    }

    private void checkCrcOfMessage()
    {
        CrcBuilderOperations crcBuilder = new HubCrcBuilder();

        byte theCrc = crcBuilder.build(hubBuffer);

        if (theCrc != hubBuffer[HubPackageRepository.getInstance().getCrcOffset()])
        {
            throw new InvalidPackageCrcException("Error on Checking Crc", hubBuffer[HubPackageRepository.getInstance().getCrcOffset()]);
        }
    }

}
