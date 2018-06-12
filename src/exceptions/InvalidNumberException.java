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
public class InvalidNumberException extends Exception
{


    public InvalidNumberException()
    {
        super(Taal.vertaal("ongeldigGetal"));
    }
    
    public InvalidNumberException(String error)
    {
        super(error);
    }
    
    public InvalidNumberException(String error, Throwable t)
    {
        super(error,t);
    }
    
    public InvalidNumberException(Throwable t)
    {
        super(Taal.vertaal("ongeldigGetal"),t);
    }
}
