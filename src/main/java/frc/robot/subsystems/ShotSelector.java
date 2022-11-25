package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class ShotSelector implements Subsystem {

    public static enum Shot {
        FULL(Value.kForward),
        LOB(Value.kReverse);

        // We don't need a getter since this value is only used by Shifter
        private Value solenoidValue;

        private Shot(Value v) {
            this.solenoidValue = v;
        }
    }

    private final DoubleSolenoid selector;

    private Shot currentShot;

    public ShotSelector(DoubleSolenoid selector) {
        this.selector = selector;
        this.set(Shot.FULL);
    }

    public void set(Shot shot) {
        this.currentShot = shot;
        this.selector.set(this.currentShot.solenoidValue);
    }

    public Shot getShot() {
        return currentShot;
    }

}
