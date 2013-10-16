Graylog2 output plugin - Syslog redirect
=============

Install steps:

    git clone https://github.com/jvrmaia/graylog2-plugin-output-syslog.git
    cd graylog2-plugin-output-syslog
    mvn package
    Ensure *.jar created filename is org.graylog2.syslogoutput.output.SyslogOutput_gl2plugin.jar
    Copy *.jar to your plugin path of Graylog2
    In Graylog2-web-interface: Settings > System > Syslog configuration and edit host and port to redirect syslog
    Configure your stream to use this plugin



