package ru.carwash.utils.validation

import com.wajahatkarim3.easyvalidation.core.Validator
import com.wajahatkarim3.easyvalidation.core.rules.BaseRule

class ValidNumberRule(val msg: String) : BaseRule {
    override fun getErrorMessage(): String =  msg

    override fun validate(text: String): Boolean {
        var phone = text
        if (text.isEmpty())
            return false

        phone.replace("-","")
        phone.replace("(","")
        phone.replace(")","")
        return Validator(text).regex("^((\\+[1-999]|[1-999])+([0-9]){10,17})\$").check()
    }

}