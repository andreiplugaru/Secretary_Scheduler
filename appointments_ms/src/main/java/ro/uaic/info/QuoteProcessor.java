package ro.uaic.info;

import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class QuoteProcessor {

    @Incoming("requests")
    @Blocking
    public void process(String quoteRequest) throws InterruptedException {
        // simulate some hard-working task
        Thread.sleep(1000);
        System.out.println("Processing quote request: " + quoteRequest);
    }
}
