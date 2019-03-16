package frc.team6027.robot.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RobotStatusServer {

    // As far as I know, available "team use" ports are numbered 5800-5810
    public static final int DEFAULT_ROBOT_SERVER_PORT = 5801;
    private final Logger logger = LogManager.getLogger(getClass());

    protected boolean stopped = false;
    protected int port = DEFAULT_ROBOT_SERVER_PORT;

    public RobotStatusServer() {
    }

    public RobotStatusServer(int port) {
        this.port = port;
    }

    public void stop() {
        this.stopped = true;
    }

    public void start() {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(5);

        Runnable serverTask = () -> {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(RobotStatusServer.this.port);
                logger.info("RobotServer is up and waiting...");
            } catch (Exception ex) {
                RobotStatusServer.this.stopped = true;
                logger.error("Failed to start server on port " + RobotStatusServer.this.port, ex);
            }

            while(! RobotStatusServer.this.stopped) {
                Socket clientSocket;
                try {
                    clientSocket = serverSocket.accept();
                } catch (Exception ex) {
                    logger.error("Failure on serverSocket accept!", ex);
                    continue;
                }
                clientProcessingPool.submit(new ProcessClientRequestTask(this, clientSocket));
                logger.debug("RobotServer waiting...");
            }
            logger.info("RobotServer stopped");
        };

        Thread serverThread = new Thread(serverTask);
        serverThread.start();

    }


    public static void main(String[] args) {
        RobotStatusServer server = new RobotStatusServer(DEFAULT_ROBOT_SERVER_PORT);
        server.start();
    }
}