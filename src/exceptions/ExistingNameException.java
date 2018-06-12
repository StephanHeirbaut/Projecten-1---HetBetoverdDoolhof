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
public class ExistingNameException extends Exception
{

    public ExistingNameException()
    {
        super(Taal.vertaal("bestaandeNaam"));
    }
    
    public ExistingNameException(String error)
    {
        super(error);
    }
    
    public ExistingNameException(String error, Throwable t)
    {
        super(error,t);
    }
    
    public ExistingNameException(Throwable t)
    {
        super(Taal.vertaal("bestaandeNaam"),t);
    }
}
