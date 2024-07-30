package fr.thomasbernard03.tarot.commons.helpers.implementations

import android.content.Context
import androidx.annotation.StringRes
import fr.thomasbernard03.tarot.commons.helpers.ResourcesHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ResourcesHelperImpl : ResourcesHelper, KoinComponent {

    private val context : Context by inject()

    override fun getString(@StringRes stringId: Int, vararg formatArgs: Any): String
            = context.getString(stringId, *formatArgs)
}