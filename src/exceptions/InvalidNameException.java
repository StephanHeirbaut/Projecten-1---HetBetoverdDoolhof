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
public class InvalidNameException extends Exception
{

    public InvalidNameException()
    {
        super(Taal.vertaal("ongeldigeNaam"));
    }
    
    public InvalidNameException(String error)
    {
        super(error);
    }
    
    public InvalidNameException(String error, Throwable t)
    {
        super(error,t);
    }
    
    public InvalidNameException(Throwable t)
    {
        super(Taal.vertaal("ongeldigeNaam"),t);
    }
}
