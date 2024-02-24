
package frc.robot.motors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;

public class WPI_CANSparkMax extends CANSparkMax implements Sendable {
    /**
     * Create a new object to control a SPARK MAX motor Controller
     *
     * @param deviceId The device ID.
     * @param type     The motor type connected to the controller. Brushless motor
     *                 wires must be connected
     *                 to their matching colors and the hall sensor must be plugged
     *                 in. Brushed motors must be
     *                 connected to the Red and Black terminals only.
     */
    public SparkPIDController pidController;

    public WPI_CANSparkMax(int deviceId, MotorType type) {
        super(deviceId, type);
        configure();
        SendableRegistry.addLW(this, "SparkMax ", deviceId);
    }

    private void configure() {
        restoreFactoryDefaults();
        setSmartCurrentLimit(20);
        pidController = getPIDController();
        // PID coefficients
        var kP = 5e-5;
        var kI = 1e-6;
        var kD = 0;
        var kIz = 0;
        var kFF = 0.000156;
        var kMaxOutput = 1;
        var kMinOutput = -1;
        var maxRPM = 5700;

        // Smart Motion Coefficients
        var maxVel = 2000; // rpm
        var maxAcc = 1500;

        // set PID coefficients
        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
        pidController.setIZone(kIz);
        pidController.setFF(kFF);
        pidController.setOutputRange(kMinOutput, kMaxOutput);
        int smartMotionSlot = 0;
        pidController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
        pidController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        pidController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
        pidController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Motor Controller");
        builder.setActuator(true);
        builder.setSafeState(this::stopMotor);
        builder.addDoubleProperty("Value", this::get, this::set);
    }
}
