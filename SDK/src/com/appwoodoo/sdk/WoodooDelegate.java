package com.appwoodoo.sdk;

/**
 * Implement a WoodooDelegate to be called as soon as the settings arrive.  
 * See the README for details.
 *
 * @author wimagguc
 * @since 11.06.13
 */
public interface WoodooDelegate {

    public void woodooArrived(Woodoo.WoodooStatus status);

}
