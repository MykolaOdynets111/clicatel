package api.clients;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import static util.PropertiesReader.getProperty;

public class CTXSimulatorClient extends BasedAPIClient {

    public static String root;
    public static String host;
    public static String rootPassword;
    public static String vendorMTNNGException;
    public static String vendorMTNNGSuccess;
    public static String vendorMTNNGErrorNO_TRX;
    public static String vendorMTNNGErrorFORMAT;
    public static String vendorMTNNGErrorTempINVMSISDN;
    public static String vendorMTNNGErrorINVMSISDN;
    public static String vendorMTNNGErrorMsisdnBarred;
    public static String vendorMTNNGErrorPpasFail;
    public static String vendorMTNNGErrorDEFPPASMSG;
    public static String vendorMTNNGErrorPPASNoConnect;
    public static String vendorMTNNGErrorINVDestAcc;
    public static String vendorMTNNGErrorINVORIGACC;
    public static String vendorMTNNGErrorINSUFAirtime;
    public static String vendorMTNNGErrorServer;
    public static String vendorMTNNGErrorVendorSystem;
    public static String vendorMTNNGErrorINVTARCLS;
    public static String vendorMTNNGDelay5000;
    public static String vendorGLOException;
    public static String vendorGLOSuccess;
    public static String vendorGLODelay5000;
    public static String vendorGLOErrorProtocol;
    public static String vendorGLOErrorDUPTRANAPROV;
    public static String vendorGLOErrorINVAMT;
    public static String vendorGLOErrorVERFailed;
    public static String vendorGLOErrorSubLimits;
    public static String vendorGLOErrorDUPTranDECL;
    public static String vendorGLOErrorTranPROC;
    public static String vendorGLOErrorTRANLimit;
    public static String vendorGLOErrorINVMSISDN;
    public static String vendorGLOErrorINVStatus;
    public static String vendorETISALATErrorINVMSISDN;
    public static String vendorETISALATSuccess;
    public static String vendorETISALATErrorINVAMT;
    public static String vendorETISALATErrorSERVPROV;
    public static String vendorGLOErrorINVTRAN;
    public static String vendorETISALATErrorRechargeNotAllow;
    public static String vendorETISALATErrorAuthFailed;
    public static String vendorETISALATErrorNoRecharge;
    public static String vendorETISALATErrorNoFunds;
    public static String vendorETISALATErrorRechargeNotAuth;
    public static String vendorETISALATErrorTRANDUP;
    public static String vendorETISALATErrorSYSVOL;
    public static String vendorETISALATErrorTimeOUT;
    public static String vendorETISALATDelay5000;
    public static String vendorETISALATException;

    static {
        root = getProperty("root");
        host = getProperty("host");
        rootPassword = getProperty("rootPassword");
        vendorMTNNGException = getProperty("vendorMTNNGException");
        vendorMTNNGSuccess = getProperty("vendorMTNNGSuccess");
        vendorMTNNGErrorNO_TRX = getProperty("vendorMTNNGErrorNO_TRX");
        vendorMTNNGErrorFORMAT = getProperty("vendorMTNNGErrorFORMAT");
        vendorMTNNGErrorTempINVMSISDN = getProperty("vendorMTNNGErrorTempINVMSISDN");
        vendorMTNNGErrorINVMSISDN = getProperty("vendorMTNNGErrorINVMSISDN");
        vendorMTNNGErrorMsisdnBarred =getProperty("vendorMTNNGErrorMsisdnBarred");
        vendorMTNNGErrorPpasFail=getProperty("vendorMTNNGErrorPpasFail");
        vendorMTNNGErrorDEFPPASMSG=getProperty("vendorMTNNGErrorDEFPPASMSG");
        vendorMTNNGErrorPPASNoConnect=getProperty("vendorMTNNGErrorPPASNoConnect");
        vendorMTNNGErrorINVDestAcc=getProperty("vendorMTNNGErrorINVDestAcc");
        vendorMTNNGErrorINVORIGACC=getProperty("vendorMTNNGErrorINVORIGACC");
        vendorMTNNGErrorINSUFAirtime=getProperty("vendorMTNNGErrorINSUFAirtime");
        vendorMTNNGErrorServer=getProperty("vendorMTNNGErrorServer");
        vendorMTNNGErrorVendorSystem=getProperty("vendorMTNNGErrorVendorSystem");
        vendorMTNNGErrorINVTARCLS=getProperty("vendorMTNNGErrorINVTARCLS");
        vendorMTNNGDelay5000=getProperty("vendorMTNNGDelay5000");
        vendorGLOException=getProperty("vendorGLOException");
        vendorGLOSuccess=getProperty("vendorGLOSuccess");
        vendorGLODelay5000=getProperty("vendorGLODelay5000");
        vendorGLOErrorProtocol=getProperty("vendorGLOErrorProtocol");
        vendorGLOErrorDUPTRANAPROV=getProperty("vendorGLOErrorDUPTRANAPROV");
        vendorGLOErrorINVAMT=getProperty("vendorGLOErrorINVAMT");
        vendorGLOErrorVERFailed=getProperty("vendorGLOErrorVERFailed");
        vendorGLOErrorSubLimits=getProperty("vendorGLOErrorSubLimits");
        vendorGLOErrorDUPTranDECL=getProperty("vendorGLOErrorDUPTranDECL");
        vendorGLOErrorTranPROC=getProperty("vendorGLOErrorTranPROC");
        vendorGLOErrorTRANLimit=getProperty("vendorGLOErrorTRANLimit");
        vendorGLOErrorINVMSISDN=getProperty("vendorGLOErrorINVMSISDN");
        vendorGLOErrorINVStatus=getProperty("vendorGLOErrorINVStatus");
        vendorETISALATErrorINVMSISDN=getProperty("vendorETISALATErrorINVMSISDN");
        vendorETISALATSuccess=getProperty("vendorETISALATSuccess");
        vendorETISALATErrorINVAMT=getProperty("vendorETISALATErrorINVAMT");
        vendorETISALATErrorSERVPROV=getProperty("vendorETISALATErrorSERVPROV");
        vendorGLOErrorINVTRAN=getProperty("vendorGLOErrorINVTRAN");
        vendorETISALATErrorRechargeNotAllow=getProperty("vendorETISALATErrorRechargeNotAllow");
        vendorETISALATErrorAuthFailed=getProperty("vendorETISALATErrorAuthFailed");
        vendorETISALATErrorNoRecharge=getProperty("vendorETISALATErrorNoRecharge");
        vendorETISALATErrorNoFunds=getProperty("vendorETISALATErrorNoFunds");
        vendorETISALATErrorRechargeNotAuth=getProperty("vendorETISALATErrorRechargeNotAuth");
        vendorETISALATErrorTRANDUP=getProperty("vendorETISALATErrorTRANDUP");
        vendorETISALATErrorSYSVOL=getProperty("vendorETISALATErrorSYSVOL");
        vendorETISALATErrorTimeOUT=getProperty("vendorETISALATErrorTimeOUT");
        vendorETISALATDelay5000=getProperty("vendorETISALATDelay5000");
        vendorETISALATException=getProperty("vendorETISALATException");


    }


    public static void setSimulatorExpectedState(String vendorSimulatorState) throws Exception {

        JSch jsch = null;
        Properties config = null;
        Session session = null;
        ChannelExec channel = null;

        try {
            //Set StrictHostKeyChecking to no to avoid UnknownHostKey issue
            config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            jsch = new JSch();

             // A session represent connection to SSh server
            session = jsch.getSession(root, host, 22);
            session.setPassword(rootPassword);
            session.setConfig(config);
            session.connect();
            System.out.println("Connected to session");

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(vendorSimulatorState);
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

            String responseString = new String(responseStream.toByteArray());
            System.out.println(responseString);

        } catch (Exception e) {
            System.out.println("Problem with" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null && session.isConnected()) {
                System.out.println("Closing SSH session");
                session.disconnect();
            }
        }
    }
}



