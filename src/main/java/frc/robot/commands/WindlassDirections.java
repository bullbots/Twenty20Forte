package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Windlass;


public class WindlassDirections extends Command {

    private final Windlass m_Windlass;
    private final int m_direction;

    public WindlassDirections(Windlass subWindlass, int direction) {
        m_Windlass = subWindlass;
        m_direction = direction;
        addRequirements(m_Windlass);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_Windlass.setDirection(m_direction);
        m_Windlass.start();
        System.out.println("INFO: WindlassDirections initialize");
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_Windlass.stop();
        System.out.println("INFO: WindlassDirections end");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

}