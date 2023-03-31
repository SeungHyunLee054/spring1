package kr.mjc.lsh.spring.day1.class04;

import kr.mjc.lsh.spring.day1.class02.TV;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SamsungTV implements TV {

    private Speaker speaker;

    /**
     * speaker를 주입하는 constructor
     */
    public SamsungTV(Speaker speaker) {
        this.speaker = speaker;
        log.debug("samsungTV 인스턴스 생성됨. speaker 주입함.");
    }

    @Override
    public void powerOn() {
        log.debug("samsungTV power on.");
    }

    @Override
    public void powerOff() {
        log.debug("samsungTV power off.");
    }

    @Override
    public void volumeUp() {
        speaker.volumeUp();
    }

    @Override
    public void volumeDown() {
        speaker.volumeDown();
    }
}
