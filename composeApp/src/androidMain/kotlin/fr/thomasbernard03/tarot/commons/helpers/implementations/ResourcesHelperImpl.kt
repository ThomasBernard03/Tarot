package fr.thomasbernard03.tarot.commons.helpers.implementations

import android.content.Context
import androidx.annotation.StringRes
import fr.thomasbernard03.tarot.commons.helpers.ResourcesHelper

class ResourcesHelperImpl(private val context: Context) : ResourcesHelper {
    override fun getString(@StringRes stringId: Int, vararg formatArgs: Any): String
            = context.getString(stringId, *formatArgs)
}