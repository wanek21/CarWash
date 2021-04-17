package ru.carwash.utils.validation

import com.wajahatkarim3.easyvalidation.core.rules.BaseRule

class MinLengthRule(private val msg: String, private val minLength: Int) : BaseRule {
    override fun getErrorMessage(): String {
        return msg
    }

    override fun validate(text: String): Boolean {
        return text.length >= minLength
    }
}