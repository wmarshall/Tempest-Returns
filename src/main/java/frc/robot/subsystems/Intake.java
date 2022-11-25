package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Intake implements Subsystem {
    
    public static enum State {
        // Arm is out by default since catapult-on-intake conflicts are bad
        OUT(Value.kForward),
        IN(Value.kReverse);

        // We don't need a getter since this value is only used by Shifter
        private Value solenoidValue;

        private State(Value v) {
            this.solenoidValue = v;
        }
    }

    public static enum Direction {
        // Arm is out by default since catapult-on-intake conflicts are bad
        OUT(-1),
        IN(1);

        // We don't need a getter since this value is only used by Shifter
        private int coefficient;

        private Direction(int c) {
            this.coefficient = c;
        }
    }

    private final DoubleSolenoid arm;
    private final MotorController roller;
    private State currentState;

    public Intake(DoubleSolenoid arm, MotorController roller) {
        this.arm = arm;
        this.roller = roller;
        this.setState(State.OUT);
    }

    public void setState(State desired){
        this.currentState = desired;
        this.arm.set(this.currentState.solenoidValue);
    }

    public State getState() {
        return this.currentState;
    }

    public void roll(Direction direction, double speed) {
        speed = MathUtil.clamp(speed, -1, 1);
        this.roller.set(direction.coefficient * speed);
    }
}
