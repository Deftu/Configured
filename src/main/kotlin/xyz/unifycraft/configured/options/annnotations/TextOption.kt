package xyz.unifycraft.configured.options.annnotations

import xyz.unifycraft.configured.options.Option

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class TextOption(
    /**
     * The serializable name of the option,
     * and the default if the localized
     * name is blank.
     */
    val name: String,
    /**
     * The localized name of the option.
     */
    val localizedName: String = "",
    /**
     * The description of the option.
     */
    val description: String = "",
    /**
     * Whether the option is hidden
     * from the user.
     */
    val hidden: Boolean = Option.DEFAULT_HIDDEN,
    /**
     * The tags that can be used to
     * search for the option in the
     * default UI.
     */
    val tags: Array<String> = [],
    /**
     * Whether this text is protected
     * in the way passwords are,
     * and cannot be seen.
     */
    val protectedText: Boolean = false,
    /**
     * The limit of characters which
     * can be entered into the text.
     */
    val limit: Int = -1
)
