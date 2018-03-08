/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hotelmanagement_beanswork;

/**
 *
 * @LamprosTzanetos LambrosTzanetos
 */
public class Seminar {
    
    private String name;
    private LocationCoordinates coordinates;

    public Seminar(String name, LocationCoordinates coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }
    
    public Seminar() {
        
    }

    public String getName() {
        return name;
    }

    public LocationCoordinates getCoordinates() {
        return coordinates;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(LocationCoordinates coordinates) {
        this.coordinates = coordinates;
    }

}

