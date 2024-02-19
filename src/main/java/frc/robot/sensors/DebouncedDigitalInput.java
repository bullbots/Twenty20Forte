
package frc.robot.sensors;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;


public class DebouncedDigitalInput extends DigitalInput {
    private static final float DEBOUNCE_TIME = 0.2f;

    private final Timer m_timer = new Timer();
    private boolean m_prevVal = false;
    private boolean m_lockVal = false;
    private boolean m_lockOut = false;
    

    public DebouncedDigitalInput(int channel) {
        super(channel);
    }

    @Override
    public boolean get() {
        var val = super.get();
        var debouncedResult = val;

        if (m_lockOut) {
            if (m_timer.hasElapsed(DEBOUNCE_TIME)) {
                m_lockOut = false;
            }
            debouncedResult = m_lockVal;

        } else {
            if (!m_prevVal && val) {  // Positive Edge
                System.out.println("INFO: Positive Edge Detected");
                m_lockOut = true;
                m_timer.restart();
                m_lockVal = val;
            } else if (m_prevVal && !val) {  // Negative Edge
                System.out.println("INFO: Negative Edge Detected");
                m_lockOut = true;
                m_timer.restart();
                m_lockVal = val;
            }
        }

        m_prevVal = val;
        return debouncedResult;
    }
}