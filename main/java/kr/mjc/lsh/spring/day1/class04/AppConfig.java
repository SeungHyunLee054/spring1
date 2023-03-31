package kr.mjc.lsh.spring.day1.class04;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public SonySpeaker sonySpeaker() {
        return new SonySpeaker();
    }

    @Bean
    public AppleSpeaker appleSpeaker() {
        return new AppleSpeaker();
    }

    /**
     * Constructor-based dependency injection
     */
    @Bean
    public SamsungTV samsungTV() {
        return new SamsungTV(sonySpeaker());
    }

    /**
     * Setter-based dependency injection
     */
    @Bean
    public LgTV lgTV() {
        LgTV lgTV = new LgTV();
        lgTV.setSpeaker(appleSpeaker());
        return lgTV;
    }
}