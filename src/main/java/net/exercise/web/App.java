package net.exercise.web;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import fi.iki.elonen.NanoHTTPD;
import net.exercise.store.PersonStore;
import net.exercise.store.impl.SqlLitePersonStore;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "app", mixinStandardHelpOptions = true, version = "SNAPSHOT", description = "Person/Movie lookup web service.")
public class App implements Callable<Integer> {
    private static Logger LOGGER = Logger.getLogger(App.class.getName());

    @Option(names = { "-p", "--port" }, defaultValue = "8181", description = "Port to listen on (default ${DEFAULT-VALUE})")
    private int _httpPort;

    @Override
    public Integer call() throws Exception {
        // Setup dependency injection
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(PersonStore.class).to(SqlLitePersonStore.class);
            }
        });

        // Create our adapter
        final NanoHttpdAdapter nanoHttpdAdapter = injector.getInstance(NanoHttpdAdapter.class);
        NanoHTTPD httpd = new NanoHTTPD(this._httpPort) {

            // Handle requests
            @Override
            public Response serve(IHTTPSession session) {
                return nanoHttpdAdapter.Adapt(session);
            }
        };

        // Start the httpd server
        try {
            LOGGER.info(String.format("Listending on port %d", this._httpPort));
            httpd.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        } catch (IOException ex) {
            System.err.println((ex.getMessage()));
            System.exit(1);
        }

        return 0;
    }

    public static void main(String... args) {
        new CommandLine(new App()).execute(args);
    }
}
