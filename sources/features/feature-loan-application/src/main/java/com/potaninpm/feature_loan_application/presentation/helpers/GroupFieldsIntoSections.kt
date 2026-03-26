package com.potaninpm.feature_loan_application.presentation.helpers

import com.potaninpm.database.models.FormField
import com.potaninpm.feature_loan_application.presentation.models.FormSection
import com.potaninpm.feature_loan_application.presentation.models.LoanSection

fun groupFieldsIntoSections(fields: List<FormField>): List<FormSection> {
    val sections = mutableListOf<FormSection>()

    LoanSection.entries.forEach { sectionEnum ->
        val sectionFields = fields.filter { field ->
            sectionEnum.fields.any { it.key == field.id }
        }

        if (sectionFields.isNotEmpty()) {
            sections.add(FormSection(sectionEnum.titleRes, sectionFields))
        }
    }

    return sections
}