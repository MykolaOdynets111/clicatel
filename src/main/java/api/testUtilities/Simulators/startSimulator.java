package api.testUtilities.Simulators;

import com.google.common.io.CharStreams;
import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import static java.lang.Thread.sleep;

public class startSimulator {

    public void SimulatorScenario(String vendScenario) throws JSchException, InterruptedException, IOException {
        String serverUrl = ("10.13.52.22");
        String userName = ("root");
        String password = ("cl!ck@t3ll");

        JSch jsch = new JSch();


        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        config.put("compression.s2c", "zlib,none");
        config.put("compression.c2s", "zlib,none");

        Session session = jsch.getSession(userName, serverUrl);
        session.setConfig(config);
        session.setPort(22);
        session.setPassword(password);
        session.connect();

        //setup channel connection and shell execution to setup and start the simulator
        //String command = "./vendorSim.sh MTNNG SUCCESS";
        String command = "./vendorSim.sh " + vendScenario;
        //log.info(command);
        System.out.println(command);
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);

        channel.setInputStream(null);
        ((ChannelExec) channel).setErrStream(System.err);

        InputStream in = channel.getInputStream();
        channel.connect();

        //start reading the input from the executed commands on the shell
        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) {
                    break;
                }
                System.out.print(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                if (in.available() > 0) {
                    continue;
                }
                System.out
                        .println("exit-status: " + channel.getExitStatus());
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception ee) {
            }
        }
        channel.disconnect();
        session.disconnect();
    }

}
