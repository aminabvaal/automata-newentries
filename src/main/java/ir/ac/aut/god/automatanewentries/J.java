package ir.ac.aut.god.automatanewentries;

import javax.sound.sampled.*;

/**
 * created By aMIN on 5/12/2019 2:45 PM
 */

public class J {

    public static void main(String[] args) throws LineUnavailableException {
        AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
        TargetDataLine microphone = AudioSystem.getTargetDataLine(format);

        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
        for (Mixer.Info info : mixerInfos) {
            Mixer m = AudioSystem.getMixer(info);
            Line.Info[] lineInfos = m.getSourceLineInfo();
            for (Line.Info lineInfo : lineInfos) {
                System.out.println(info.getName() + "---" + lineInfo);
                Line line = m.getLine(lineInfo);
                System.out.println("\t-----" + line);
            }
            lineInfos = m.getTargetLineInfo();
            for (Line.Info lineInfo : lineInfos) {
                System.out.println(m + "---" + lineInfo);
                Line line = m.getLine(lineInfo);
                System.out.println("\t-----" + line);
            }
        }
    }


}
