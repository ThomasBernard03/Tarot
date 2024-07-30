package fr.thomasbernard03.tarot.commons.helpers

import androidx.annotation.StringRes

interface ResourcesHelper {
    fun getString(@StringRes stringId: Int, vararg formatArgs: Any) : String
}