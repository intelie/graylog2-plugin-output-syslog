Graylog2 output plugin - Syslog redirect
=============

## How to use

Download org.graylog2.syslogoutput.output.SyslogOutput_gl2plugin.jar and put inside /graylog2/path/plugins/output/.

Restart Graylog2 and configure HOST and PORT to redirect syslog messages.

Associate the plugin to any stream and syslog messages will be redirected.

## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request

## About current package

* Apache Maven 3.1.1
* Oracle Java (build 1.7.0_45-b18)
