package com.github.lamba92.kotlingram.parser

data class TelegramField(val name: String, val type: String, val description: String)
data class TelegramObject(val name: String, val fields: List<TelegramField>, val description: String) {
    init {
        val counting =
            fields.groupBy { it.name }
                .filter { (_, v) -> v.size > 1 }

        require(counting.isEmpty()) {
            buildString {
                appendLine("Following names are duplicated for object $name:")
                counting.forEach { (k, _) ->
                    appendLine(" - $k")
                }
            }

        }

    }
}

data class TelegramParameter(val name: String, val type: String, val required: String, val description: String)
data class TelegramMethod(val name: String, val parameters: List<TelegramParameter>, val description: String)

val sealedClassTypeRegex = Regex("must be ([\\w_]*)")

fun TelegramObject.generateSourceCode() = buildString {
    val isTypedSealedClass =
        "type" in fields.map { it.name } && name in inputMediaTypes
    val trueFields =
        when {
            isTypedSealedClass -> fields.filter { it.name != "type" }
            "type" in fields.map { it.name } ->
                fields.filter { it.name != "type" } + fields[fields.indexOfFirst { it.name == "type" }]

            else -> fields
        }
    appendLine("/**")
    description.split("\n").forEach {
        appendLine(" * $it")
    }
    trueFields.filter { it.description.isNotEmpty() }.forEach {
        appendLine(" * @param ${it.name.toCamelCase()} ${it.description}")
    }
    appendLine(" */")
    appendLine("@Serializable")
    if (isTypedSealedClass) {
        val sealedClassType = sealedClassTypeRegex
            .find(fields.first { it.name == "type" }.description)!!
            .groupValues[1]
        appendLine("@SerialName(\"$sealedClassType\")")
    }
    appendLine("data class $name(")
    trueFields.forEachIndexed { index, field ->
        append("    ")
        if ("_" in field.name)
            append("@SerialName(\"${field.name}\") ")

        append("val ${field.name.toCamelCase()}: ")
        val isOptional = field.description.startsWith("Optional")
        val arrayCount = field.type.split(" ").count { it == "Array" }
        val type = when {
            arrayCount > 0 -> ("List<" * arrayCount) + resolveType(field.type.removePrefixRecursive("Array of "),
                false) + (">" * arrayCount) + " = emptyList()"
            else -> resolveType(field.type, isOptional)
        }
        append(type)
//        val sealedClassType = fields.firstOrNull() { it.name == "type" }?.description
//            ?.let { sealedClassTypeRegex.find(it) }
//            ?.groupValues
//            ?.getOrNull(1)
//        if (sealedClassType != null && field.name == "type")
//            append(" = \"$sealedClassType\"")
        if (index != trueFields.lastIndex)
            append(",")
        appendLine()
    }
    append(")")
    if (name.startsWith("Input") && name.endsWith("Content"))
        append(" : InputMessageContent()")
    if (inlineQueryResultTypes.any { it in name })
        append(" : InlineQueryResult()")
    if (listOf("InlineKeyboardMarkup", "ReplyKeyboardMarkup", "ReplyKeyboardRemove", "ForceReply").any { it in name })
        append(" : ReplyMarkup()")
    if (listOf("InputMediaAudio", "InputMediaDocument", "InputMediaPhoto", "InputMediaVideo").any { it in name })
        append(" : InputMedia()")
    appendLine()
}

fun TelegramParameter.getKotlinType(): String {
    val isOptional = required == "Optional"
    val arrayCount = type.split(" ").count { it == "Array" || it == "array" }
    return when {
        arrayCount > 0 -> ("List<" * arrayCount) + resolveType(type.removePrefixRecursive("Array of ")
            .removePrefixRecursive("array of"),
            false) + (">" * arrayCount) + " = emptyList()"
        else -> resolveType(type, isOptional)
    }
}

fun TelegramMethod.generateSourceCode() = buildString {
    if (parameters.isNotEmpty()) {
        appendLine("/**")
        appendLine(" * Request body for [$name]")
        parameters.filter { it.description.isNotEmpty() }.forEach {
            appendLine(" * @param ${it.name.toCamelCase()} ${it.description}")
        }
        appendLine(" */")
        appendLine("@Serializable")
        appendLine("data class ${name.capitalize()}Request(")
        parameters.forEachIndexed { index, field ->
            append("\t")
            if ("_" in field.name)
                append("@SerialName(\"${field.name}\") ")
            append("val ${field.name.toCamelCase()}: ")
            append(field.getKotlinType())
            if (index != parameters.lastIndex)
                append(",")
            appendLine()
        }
        appendLine(")")
    }
    appendLine()
    val responseType = findResponseTypeFromDescription(description)
    val arrayCount = responseType.split(" ").count { it == "Array" || it == "array" }
    val type = when {
        arrayCount > 0 -> ("List<" * arrayCount) + resolveType(responseType.removePrefixRecursive("Array of ")
            .removePrefixRecursive("array of "),
            false).removeSuffix("s") + (">" * arrayCount)
        else -> resolveType(responseType, false)
    }
    val httpMethod = if (parameters.isEmpty()) "get" else "post"
    appendLine("/**")
    description.split("\n").forEach {
        appendLine(" * $it")
    }
    appendLine(" */")
    append("suspend fun TelegramBotApiClient.$name(")
    if (parameters.isNotEmpty())
        append("requestBody: ${name.capitalize()}Request")
    appendLine(") =")
    appendLine("    httpClient.$httpMethod<TelegramResponse<$type>> {")
    appendLine("        url {")
    appendLine("            protocol = apiProtocol")
    appendLine("            host = apiHost")
    appendLine("            port = apiPort")
    appendLine("            path(\"bot\$apiToken\", \"$name\")")
    appendLine("        }")
    if (parameters.isNotEmpty()) {
        appendLine("        header(HttpHeaders.ContentType, ContentType.Application.Json)")
        appendLine("        body = requestBody")
    }
    appendLine("    }")
    appendLine()
    if (parameters.isNotEmpty()) {
        appendLine("/**")
        description.split("\n").forEach {
            appendLine(" * $it")
        }
        parameters.filter { it.description.isNotEmpty() }.forEach {
            appendLine(" * @param ${it.name.toCamelCase()} ${it.description}")
        }
        appendLine(" */")
        appendLine("suspend fun TelegramBotApiClient.$name(")
        parameters.forEachIndexed { index, field ->
            append("    ${field.name.toCamelCase()}: ${field.getKotlinType()}")
            if (index != parameters.lastIndex)
                appendLine(",")
            else
                appendLine()
        }
        appendLine(") = ")
        appendLine("    $name(")
        appendLine("        ${name.capitalize()}Request(")
        parameters.forEachIndexed { index, field ->
            append("            ${field.name.toCamelCase()}")
            if (index != parameters.lastIndex)
                appendLine(",")
            else
                appendLine()
        }
        appendLine("        )")
        appendLine("    )")
    }

}
