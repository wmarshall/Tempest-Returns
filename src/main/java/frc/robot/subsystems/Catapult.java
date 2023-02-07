package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Catapult implements Subsystem {

    // TODO - should this be lifted into a command?
    public static enum Mode {
        AUTO,
        MANUAL,
        IDLE
    }

    private final Talon cam;
    private final AnalogPotentiometer camAngle;
    
    private Mode mode;

    public Catapult(Talon cam, AnalogPotentiometer camAngle, Mode mode) {
        
        this.cam = cam;
        this.camAngle = camAngle;
        this.setMode(mode);
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void set(double speed) {
        speed = MathUtil.clamp(speed, -1, 1);
        cam.set(speed);
    }

    public double getAngleDegrees() {
        return camAngle.get();
    }

}
