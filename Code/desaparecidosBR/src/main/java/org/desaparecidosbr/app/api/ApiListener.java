package org.desaparecidosbr.app.api;


import org.json.JSONArray;

import java.util.ArrayList;

/**
 * API Listener containing all call-backs to API. Every class that will have an
 * API connection needs to implement this interface.
 * 
 * @author Rafael Decker
 * 
 */
public interface ApiListener {

    /**
     * Call-back used to show some UI to the user while the server handling the
     * request
     */
    //public void onPreExecute(int operation);
    public void onPreExecute();

    /**
     * Call-back used when the request ends. It is called even if some error
     * happens.
     * 
     * @param booelan
     *            isSuccess
     * @param ArrayList
     *            <?> parsedData
     */
    public void onRequestEnds(int operation, boolean isSuccess, ArrayList<?> parsedData);
    public void onRequestEnds(boolean isSuccess, ArrayList<?> parsedData);
    
    
    /**
     * Call-back used when the request ends. It is called even if some error
     * happens.
     * 
     * @param booelan
     *            isSuccess
     * @param ArrayList
     *            <?> parsedData
     */
    public void onRequestEnds(boolean isSuccess, JSONArray parsedData);
}