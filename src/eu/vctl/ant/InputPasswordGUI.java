package eu.vctl.ant;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.Console.*;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.input.InputHandler;
import org.apache.tools.ant.input.InputRequest;
/**
 * Klasa umożliwiajaca pobranie użykownika i hasła w trybie graficznym
 * @author piotrk
 *
 */
public class InputPasswordGUI extends JFrame implements ActionListener, InputHandler
{
    private final Object  monitor = new Object();
    private String  pass = null;
    private String  msge = "Enter password:";

    JPanel panel = null;

    // Panel opisujący nazwę użytkownika
    JLabel      lblMessage = new JLabel(msge);
    JPanel      pnlMessage = new JPanel(new FlowLayout(FlowLayout.LEFT));

    // Panel opisujący hasło użytkownika
    JPasswordField txtPassword = new JPasswordField(8);
    JPanel         pnlPassword = new JPanel(new FlowLayout(FlowLayout.LEFT));

    // Panel opisujący przyciski
    JButton     btnOk     = new JButton("OK");
    JButton     btnCancel = new JButton("Cancel");
    JPanel      pnlAction = new JPanel(new FlowLayout(FlowLayout.LEFT));

    public void handleInput(InputRequest request)
    {
        try
        {
            // Ustawienie zdania pytajacego podanego przez użytkownika
            lblMessage.setText(request.getPrompt());
            pnlMessage.add(lblMessage);

            // Dodanie pola hasła wyrównanego do lewej strony
            pnlPassword.add(txtPassword);

            // Dodanie panelu przycisków
            btnOk.addActionListener(this);
            btnCancel.addActionListener(this);
            pnlAction.add(btnOk);
            pnlAction.add(btnCancel);

            // Operacje na panelu
            panel = (JPanel) getContentPane();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            //setContentPane(pnlMain);
            panel.add(pnlMessage);
            panel.add(Box.createRigidArea(new Dimension(5, 0)));
            panel.add(pnlPassword);
            panel.add(Box.createRigidArea(new Dimension(5, 0)));
            panel.add(pnlAction);

            
            // Operacje na głównej ramce
            try
            {
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
            catch (SecurityException ex)
            {
                
            }
            getRootPane().setDefaultButton(btnOk);
            setLocation(100, 100);
            setSize(350, 150);
            setVisible(true);
            try
            {
                synchronized (monitor)
                {
                    monitor.wait();
                }
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
            if (pass != null)
            {
                request.setInput(pass);
            }
        }
        catch (java.awt.HeadlessException ex)
        {
            java.io.Console console= System.console();
            if (console != null)
            {
                char passArray[] = null;
                passArray = console.readPassword(request.getPrompt());
                request.setInput(passArray.toString());
            }
            else
            {
                throw new BuildException("No valid password API");
            }
            
        }
        //return !cancel;
        return;
    }

    
    public void actionPerformed(ActionEvent ev)
    {
        if ("OK".equals(ev.getActionCommand()))
        {
            pass = txtPassword.getText();
        }
        else
        {
            pass = null;
        }
        synchronized (monitor)
        {
            monitor.notify();
        }
        dispose();

    }

    public static void main(String[] argv)
    {

        InputPasswordGUI frame = new InputPasswordGUI();
        InputRequest request = new InputRequest("Enter password:");
        frame.handleInput(request);
        
        System.out.println("Pass: " + request.getInput());
        System.out.println("Koniec");
    }
}
