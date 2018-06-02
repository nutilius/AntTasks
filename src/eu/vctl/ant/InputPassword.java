/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.vctl.ant;

import java.awt.GraphicsEnvironment;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.input.InputHandler;
import org.apache.tools.ant.input.InputRequest;
/**
 *
 * @author piotrk
 */
public class InputPassword implements InputHandler
{

    public void handleInput(InputRequest request)
    {
        // If graphic environment is active - go to GUI mode
        
        if (!GraphicsEnvironment.isHeadless())
        {
            InputHandler handler = new InputPasswordGUI();
            handler.handleInput(request);
            return;
        }
        else // Go to console mode
        {
            InputHandler handler = new InputPasswordConsole();
            handler.handleInput(request);
            return;
        }
    }

}
