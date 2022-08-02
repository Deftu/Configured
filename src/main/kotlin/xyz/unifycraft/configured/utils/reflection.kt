package xyz.unifycraft.configured.utils

import java.lang.reflect.AccessibleObject

/**
 * Internal utility for cross-version
 * acessibility with Reflection.
 */
fun AccessibleObject.setAccessibility(accessible: Boolean) {
    //#if MC<=11605
    isAccessible = accessible
    //#else
    //$$ if (accessible) trySetAccessible()
    //#endif
}
