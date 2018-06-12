/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Nicolas
 */
public final class Schatten
{

    /*private final String[] schatten =
    {"doritos", "mountain", "aap", "q", "caraPils", "java", "zever", "hesp", "unsignedInteger", "everzwijn",
        "lepel", "salami", "pepsi", "facebook", "room17", "wijsheid", "vaas", "goedkopeVaas", "iets", "yoghurt",
        "kaart", "trol", "bicky", "nogIets"};*/
    private final List<String> schatten;

    public Schatten()
    {
        schatten = new ArrayList<>();
        schatten.addAll(Arrays.asList("doritos", "mountain", "aap", "q", "caraPils", "java", "zever", "hesp", "unsignedInteger", "everzwijn",
                "lepel", "salami", "pepsi", "facebook", "room17", "wijsheid", "vaas", "goedkopeVaas", "iets", "yoghurt",
                "kaart", "trol", "bicky", "nogIets"));
    }

    /**
     *
     * @return returns a list of the treasures
     */
    public List<String> getSchatten()
    {
        return schatten;
    }

    /**
     * shuffles the list of treasures
     */
    public void schudKaarten()
    {
        Collections.shuffle(schatten);
    }

}
