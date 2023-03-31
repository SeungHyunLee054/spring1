package kr.mjc.lsh.spring.day1.class04;

public class TVUserWithoutSpring {
    public static void main(String[] args) {

        SonySpeaker sonySpeaker = new SonySpeaker();
        AppleSpeaker appleSpeaker = new AppleSpeaker();

        // 컨스트럭터를 사용한 의존성 주입
        SamsungTV samsungTV = new SamsungTV(sonySpeaker);

        // 세터를 사용한 의존성 주입
        LgTV lgTV = new LgTV();
        lgTV.setSpeaker(appleSpeaker);

        samsungTV.volumeUp();
        samsungTV.volumeDown();

        lgTV.volumeUp();
        lgTV.volumeDown();
    }
}