/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.lessvoid.nifty.controls;

import de.lessvoid.nifty.render.NiftyImage;

/**
 *
 * @author ractoc
 */
public interface Chat extends NiftyControl {
    
     /**
     * This method is called when a chat line is received which should be displayed in
     * the chat control.
     * 
     * @param text
     *            The text to display.
     * @param icon
     *            Optionally, an icon can be supplied which is then displayed at
     *            the start of the chat line.
     */
    void receivedChatLine(String text, NiftyImage icon);
    
    /**
     * This method is called when a new player enters the room. This adds that
     * player to the list of players already in the room. If more then one
     * player needs to be added, this method will have to be called multiple
     * times.
     * 
     * @param playerName
     *            The player to add.
     * @param playerIcon
     *            Optionally, an icon can be supplied which is then displayed in
     *            front of the player name.
     */
    void addPlayer(String playerName, NiftyImage playerIcon);
    
    /**
     * This method is called when a player leaves the rome and needs to be
     * removed from the list.
     * 
     * @param playerName
     *            The player name to remove.
     */
    void removePlayer(String playerName);
    
}
