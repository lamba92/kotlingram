@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package com.github.lamba92.kotlingram.builder

class MarkdownBuilder : Appendable, CharSequence {

    private fun CharSequence.wrapWith(style: Style) =
        "${style.character}$this${style.character}"

    enum class Style(val character: Char) {
        BOLD('*'), ITALIC('_'), INLINE_CODE('`')
    }

    private val lines = mutableListOf<CharSequence?>()

    fun appendImage(url: String, alternativeText: String) =
        lines.add("\n[$alternativeText]($url)").let { this }

    fun append(p0: Any?) =
        append(p0.toString()).let { this }

    fun append(p0: Any?, style: Style) =
        append(p0.toString().wrapWith(style)).let { this }

    fun append(p0: CharSequence, style: Style) =
        append(p0.wrapWith(style)).let { this }

    fun appendLine(p0: CharSequence, style: Style) =
        appendLine(p0.wrapWith(style)).let { this }

    override fun append(p0: CharSequence?) =
        lines.add(p0).let { this }

    override fun append(p0: CharSequence?, p1: Int, p2: Int) =
        lines.add(p0?.substring(p1, p2)).let { this }

    override fun append(p0: Char) =
        lines.add(p0.toString()).let { this }

    override val length
        get() = toString().length

    override fun get(index: Int) =
        toString()[index]

    override fun subSequence(startIndex: Int, endIndex: Int) =
        toString().subSequence(startIndex, endIndex)

    override fun toString() =
        lines.joinToString("")

}
