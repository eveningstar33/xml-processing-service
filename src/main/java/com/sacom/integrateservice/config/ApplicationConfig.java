package com.sacom.integrateservice.config;

import com.sacom.integrateservice.service.FileHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.RegexPatternFileListFilter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.io.File;

@Configuration
@EnableIntegration
public class ApplicationConfig {

    public static final String INPUT_LOCATION = "D:\\inputLocation";
    public static final String FILE_PATTERN = "\\borders\\d+\\.xml\\b";

    @Bean
    public MessageChannel fileChannel() {
        return new DirectChannel();
    }

    @Bean
    @InboundChannelAdapter(value = "fileChannel", poller = @Poller(fixedDelay = "1000"))
    public MessageSource<File> fileReadingMessageSource() {
        File directory = new File(INPUT_LOCATION);
        CompositeFileListFilter filter= new CompositeFileListFilter();
        filter.addFilter(new AcceptOnceFileListFilter());
        filter.addFilter(new RegexPatternFileListFilter(FILE_PATTERN));
        return new FilePoller(directory, filter);
    }

    @Bean
    @ServiceActivator(inputChannel = "fileChannel")
    public MessageHandler fileWritingMessageHandler() {
        return new FileHandler();
    }
}
