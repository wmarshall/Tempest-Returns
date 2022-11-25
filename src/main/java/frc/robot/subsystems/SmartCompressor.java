package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class SmartCompressor implements Subsystem {

    private final edu.wpi.first.wpilibj.Compressor comp;

    public SmartCompressor(Compressor comp) {
        this.comp = comp;
    }

    public void set(boolean enabled) {
        if (enabled) {
            comp.enableDigital();
        } else {
            comp.disable();
        }
    }
}
