package xyz.unifycraft.configured.utils

import java.lang.reflect.AccessibleObject

fun AccessibleObject.setAccessibility(accessible: Boolean) {
    //#if MC<=11502
    isAccessible = accessible
    //#else
    //$$ if (accessible) trySetAccessible()
    //#endif
}
