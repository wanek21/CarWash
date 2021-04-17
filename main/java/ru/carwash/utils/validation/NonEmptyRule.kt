package ru.carwash.utils.validation

import com.wajahatkarim3.easyvalidation.core.rules.BaseRule

class NonEmptyRule(val msg: String) : BaseRule {

    override fun getErrorMessage(): String {
        return msg
    }

    override fun validate(text: String): Boolean = text.isNotEmpty()
}