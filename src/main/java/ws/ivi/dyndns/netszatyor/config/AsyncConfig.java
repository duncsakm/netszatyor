package ws.ivi.dyndns.netszatyor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig {
    // Ez engedélyezi az @Async annotáció használatát
}
