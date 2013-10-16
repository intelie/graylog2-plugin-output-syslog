package org.graylog2.syslogoutput.output;


import org.graylog2.plugin.GraylogServer;
import org.graylog2.plugin.logmessage.LogMessage;
import org.graylog2.plugin.outputs.MessageOutput;
import org.graylog2.plugin.outputs.MessageOutputConfigurationException;
import org.graylog2.plugin.outputs.OutputStreamConfiguration;
import org.graylog2.plugin.streams.Stream;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class SyslogOutput implements MessageOutput {

    private static final String NAME = "Syslog output";

    private Map<String, String> configuration;

    public static final Set<String> REQUIRED_FIELDS = new HashSet<String>() {{
        add("host");
        add("port");
    }};

    public void initialize(Map<String, String> configuration) throws MessageOutputConfigurationException {
        this.configuration = configuration;
        checkConfiguration();
    }

    public void write(List<LogMessage> messages, OutputStreamConfiguration streamConfiguration, GraylogServer server) throws Exception {
        for (LogMessage msg : messages) {
            for (Stream stream : msg.getStreams()) {
                Set<Map<String, String>> configuredOutputs = streamConfiguration.get(stream.getId());

                if (configuredOutputs != null) {
                    for (Map<String, String> config : configuredOutputs) {
                        sendSyslog(msg, configuration.get("host"), Integer.parseInt(configuration.get("port")));
                    }
                }
            }
        }
    }

    public Map<String, String> getRequestedConfiguration() {
        Map<String, String> config = new HashMap<String, String>();

        config.put("host", "Host to redirect syslog");
        config.put("port", "Port used at host");

        return config;
    }

    public Map<String, String> getRequestedStreamConfiguration() {
        Map<String, String> config = new HashMap<String, String>();

        return config;
    }

    public String getName() {
        return NAME;
    }

    private void checkConfiguration() throws MessageOutputConfigurationException {
        for (String field : REQUIRED_FIELDS) {
            if (!configSet(configuration, field)) {
                throw new MessageOutputConfigurationException("Missing configuration option: " + field);
            }
        }
    }

    private boolean configSet(Map<String, String> target, String key) {
        return target != null && target.containsKey(key)
                && target.get(key) != null && !target.get(key).isEmpty();
    }

    private void sendSyslog(LogMessage msg, String hostname, Integer port) {
        try {
            PrintWriter out = null;
            Socket socket = new Socket(hostname, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            out.write(msg.getFullMessage());
            out.flush();
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}