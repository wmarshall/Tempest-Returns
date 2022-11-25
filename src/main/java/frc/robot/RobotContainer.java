// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shifter;
import frc.robot.subsystems.ShotSelector;
import frc.robot.subsystems.SmartCompressor;
import frc.robot.subsystems.Intake.Direction;
import frc.robot.subsystems.Intake.State;
import frc.robot.subsystems.Shifter.Gear;
import frc.robot.subsystems.ShotSelector.Shot;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        var dtLeftJoy = new Joystick(Constants.OI.Ports.DT_LEFT_JOY);
        var dtRightJoy = new Joystick(Constants.OI.Ports.DT_RIGHT_JOY);
        var eeJoy = new Joystick(Constants.OI.Ports.EE_JOY);

        // TODO: Slew rate limiting
        DoubleSupplier dtLeftPercent = () -> invertIf(
                Constants.OI.Inverted.DT_LEFT_Y,
                smartDeadband(
                        Constants.OI.Deadbands.JOYSTICK_Y,
                        dtLeftJoy.getY()));
        DoubleSupplier dtRightPercent = () -> invertIf(
                Constants.OI.Inverted.DT_RIGHT_Y,
                smartDeadband(
                        Constants.OI.Deadbands.JOYSTICK_Y,
                        dtRightJoy.getY()));

        var shiftToggle = new JoystickButton(dtRightJoy, Constants.OI.Buttons.SHIFT);
        var selectFullShot = new JoystickButton(eeJoy, Constants.OI.Buttons.SELECT_FULL_SHOT);
        var selectLobShot = new JoystickButton(eeJoy, Constants.OI.Buttons.SELECT_LOB_SHOT);
        var load = new JoystickButton(eeJoy, Constants.OI.Buttons.LOAD);
        var unload = new JoystickButton(eeJoy, Constants.OI.Buttons.UNLOAD);
        var intakeExtendToggle = new JoystickButton(eeJoy, Constants.OI.Buttons.EXTEND_TOGGLE);

        var pdp = new PowerDistribution();

        var compressor = new SmartCompressor(
                new Compressor(PneumaticsModuleType.CTREPCM));

        var dt = new Drivetrain(
                newTalon(
                        Constants.Drivetrain.Ports.TALON_LEFT,
                        Constants.Drivetrain.Inverted.TALON_LEFT),
                newTalon(
                        Constants.Drivetrain.Ports.TALON_RIGHT,
                        Constants.Drivetrain.Inverted.TALON_RIGHT),
                newEncoder(
                        Constants.Drivetrain.Ports.ENCODER_LEFT_A,
                        Constants.Drivetrain.Ports.ENCODER_LEFT_B,
                        Constants.Drivetrain.Inverted.ENCODER_LEFT,
                        Constants.Drivetrain.Kinematics.DIST_PER_PULSE,
                        Constants.Drivetrain.Kinematics.ENCODER_SAMPLES),
                newEncoder(
                        Constants.Drivetrain.Ports.ENCODER_RIGHT_A,
                        Constants.Drivetrain.Ports.ENCODER_RIGHT_B,
                        Constants.Drivetrain.Inverted.ENCODER_RIGHT,
                        Constants.Drivetrain.Kinematics.DIST_PER_PULSE,
                        Constants.Drivetrain.Kinematics.ENCODER_SAMPLES));

        var shifter = new Shifter(
                new DoubleSolenoid(
                        PneumaticsModuleType.CTREPCM,
                        Constants.Shifter.Ports.HIGH,
                        Constants.Shifter.Ports.LOW));

        var shotSelector = new ShotSelector(
                new DoubleSolenoid(
                        PneumaticsModuleType.CTREPCM,
                        Constants.ShotSelector.Ports.FULL_SOLENOID,
                        Constants.ShotSelector.Ports.LOB_SOLENOID));

        var intake = new Intake(
                new DoubleSolenoid(
                        PneumaticsModuleType.CTREPCM,
                        Constants.Intake.Ports.OUT_SOLENOID,
                        Constants.Intake.Ports.IN_SOLENOID),
                new MotorControllerGroup(
                        newTalon(
                                Constants.Intake.Ports.TALON1,
                                Constants.Intake.Inverted.TALON1),
                        newTalon(
                                Constants.Intake.Ports.TALON2,
                                Constants.Intake.Inverted.TALON2)));

        // Configure default states
        compressor.setDefaultCommand(new RunCommand(() -> {
            compressor.set(
                    pdp.getVoltage() > Constants.Compressor.Control.MIN_RUN_VOLTAGE);
        }, compressor));

        dt.setDefaultCommand(new RunCommand(
                () -> dt.set(
                        dtLeftPercent.getAsDouble(),
                        dtRightPercent.getAsDouble()),
                dt));

        intake.setDefaultCommand(new StartEndCommand(
                () -> intake.roll(Direction.IN, 0),
                () -> {
                },
                intake));

        shiftToggle.whenPressed(new InstantCommand(() -> {
            if (shifter.getGear() == Gear.HIGH) {
                shifter.set(Gear.LOW);
            } else {
                shifter.set(Gear.HIGH);
            }
        }, shifter));

        selectFullShot.whenPressed(new InstantCommand(
                () -> shotSelector.set(Shot.FULL),
                shotSelector));

        selectLobShot.whenPressed(new InstantCommand(
                () -> shotSelector.set(Shot.LOB),
                shotSelector));

        load.whileHeld(new RunCommand(
                () -> intake.roll(Direction.IN, 1),
                intake));
        unload.whileHeld(new RunCommand(
                () -> intake.roll(Direction.OUT, 1),
                intake));
        intakeExtendToggle.whenPressed(new InstantCommand(
            () -> {
                if (intake.getState() == State.OUT) {
                    intake.setState(State.IN);
                } else {
                    intake.setState(State.OUT);
                }
            }, 
        intake));
    }

    public static Talon newTalon(int port, boolean invert) {
        Talon t = new Talon(port);
        t.setInverted(invert);
        t.enableDeadbandElimination(true);
        return t;
    }

    public static Encoder newEncoder(int aPort, int bPort, boolean invert, double distancePerPulse,
            int samplesToAverage) {
        Encoder e = new Encoder(aPort, bPort, invert);
        e.setDistancePerPulse(distancePerPulse);
        e.setSamplesToAverage(samplesToAverage);
        return e;
    }

    private static double invertIf(boolean invert, double value) {
        return (invert ? -1 : 1) * value;
    }

    private static double smartDeadband(double deadband, double value) {
        if (Math.abs(value) < deadband) {
            return 0;
        }
        return Math.copySign(
                (Math.abs(value) - deadband) / (1 - deadband),
                value);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return null;
    }
}
