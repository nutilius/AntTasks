/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.vctl.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.input.InputHandler;
import org.apache.tools.ant.input.InputRequest;

/**
 *
 * @author piotrk
 */
public class InputPasswordConsole implements InputHandler
{

    public void handleInput(InputRequest request)
    {
        java.io.Console console= System.console();
        if (console != null)
        {
            char passArray[] = null;
            passArray = console.readPassword(request.getPrompt() + " ");
            request.setInput(new String(passArray));
        }
        else
        {
            throw new BuildException("No valid password API");
        }

        return;
    }

}