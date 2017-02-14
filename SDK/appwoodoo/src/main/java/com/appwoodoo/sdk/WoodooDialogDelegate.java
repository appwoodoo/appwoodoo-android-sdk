package com.appwoodoo.sdk;

import com.appwoodoo.sdk.Woodoo;

/**
 * Implement a WoodooDelegate to be called as soon as the settings arrive.  
 * See the README for details.
 *
 * @author wimagguc
 * @since 11.06.13
 */
public interface WoodooDialogDelegate {

    public void woodooDialogArrived(Woodoo.WoodooStatus status);

}
