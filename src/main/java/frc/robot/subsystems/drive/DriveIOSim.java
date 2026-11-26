package frc.robot.subsystems.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import frc.robot.Constants;

/**
 * Implementacao simulada da tracao.
 *
 * Cumpre o mesmo contrato do SPARK MAX, so que alimentando um modelo fisico em
 * vez de motores reais. Para a classe Drive as duas sao indistinguiveis, e e
 * por isso que da para programar e testar sem o robo montado.
 */
public class DriveIOSim implements DriveIO {

  private final DifferentialDrivetrainSim sim =
      new DifferentialDrivetrainSim(
          DCMotor.getCIM(2),
          DriveConstants.GEAR_RATIO,
          DriveConstants.MOMENT_OF_INERTIA_KILOGRAM_SQUARE_METERS,
          DriveConstants.ROBOT_MASS_KILOGRAMS,
          DriveConstants.WHEEL_RADIUS_METERS,
          DriveConstants.TRACK_WIDTH_METERS,
          null);

  private double leftAppliedVolts = 0.0;
  private double rightAppliedVolts = 0.0;

  @Override
  public void updateInputs(DriveIOInputs inputs) {
    sim.update(Constants.LOOP_PERIOD_SECONDS);

    inputs.leftConnected = true;
    inputs.rightConnected = true;

    inputs.leftPositionMeters = sim.getLeftPositionMeters();
    inputs.rightPositionMeters = sim.getRightPositionMeters();

    inputs.leftVelocityMetersPerSecond = sim.getLeftVelocityMetersPerSecond();
    inputs.rightVelocityMetersPerSecond = sim.getRightVelocityMetersPerSecond();

    inputs.leftAppliedVolts = leftAppliedVolts;
    inputs.rightAppliedVolts = rightAppliedVolts;

    inputs.leftCurrentAmps = sim.getLeftCurrentDrawAmps();
    inputs.rightCurrentAmps = sim.getRightCurrentDrawAmps();

    inputs.gyroYawRadians = sim.getHeading().getRadians();
  }

  @Override
  public void setVoltage(double leftVolts, double rightVolts) {
    leftAppliedVolts = MathUtil.clamp(leftVolts, -DriveConstants.MAX_VOLTS, DriveConstants.MAX_VOLTS);
    rightAppliedVolts =
        MathUtil.clamp(rightVolts, -DriveConstants.MAX_VOLTS, DriveConstants.MAX_VOLTS);
    sim.setInputs(leftAppliedVolts, rightAppliedVolts);
  }
}
