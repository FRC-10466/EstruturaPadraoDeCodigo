package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.DriveCommands;
import frc.robot.subsystems.drive.DriveIO;
import frc.robot.subsystems.drive.DriveIOSim;
import frc.robot.subsystems.drive.DriveIOSparkMax;

/**
 * Monta o robo: escolhe as implementacoes de hardware e liga os controles.
 *
 * Este e o unico lugar do projeto que decide se a tracao vai falar com SPARK
 * MAX ou com a simulacao. Nenhum subsistema participa dessa escolha.
 */
public class RobotContainer {

  private final Drive drive;

  private final CommandXboxController driverController = new CommandXboxController(0);

  public RobotContainer() {
    DriveIO driveIO =
        switch (Constants.getMode()) {
          case REAL -> new DriveIOSparkMax();
          case SIM -> new DriveIOSim();
          // Em REPLAY ninguem conversa com hardware: as entradas vem do log.
          case REPLAY -> new DriveIO() {};
        };

    drive = new Drive(driveIO);

    configureBindings();
  }

  private void configureBindings() {
    // Comando padrao: enquanto nada mais pedir a tracao, ela obedece ao joystick.
    drive.setDefaultCommand(
        drive.arcadeDriveCommand(
            () -> -driverController.getLeftY(), () -> -driverController.getRightX()));

    driverController.a().onTrue(DriveCommands.nudgeForward(drive));
    driverController.b().onTrue(drive.stopCommand());
  }

  public Command getAutonomousCommand() {
    // Rotina minima de exemplo: anda para a frente e para.
    return Commands.sequence(DriveCommands.driveDistance(drive, 1.5)).withName("AutoSimples");
  }
}
