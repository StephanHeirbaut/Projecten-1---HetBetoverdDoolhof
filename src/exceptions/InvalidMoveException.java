/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import locale.Taal;

/**
 *
 * @author Nicolas
 */
public class InvalidMoveException extends Exception
{
    public InvalidMoveException()
    {
        super(Taal.vertaal("ongeldigeZet"));
    }
    
    public InvalidMoveException(String s)
    {
        super(s);
    }
    
    public InvalidMoveException(String s, Throwable t)
    {
        super(s,t);
    }
    
    public InvalidMoveException(Throwable t)
    {
        super(Taal.vertaal("ongeldigeZet"),t);
    }
}
