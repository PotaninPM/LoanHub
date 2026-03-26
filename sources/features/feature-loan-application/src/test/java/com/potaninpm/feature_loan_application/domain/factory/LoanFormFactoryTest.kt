package com.potaninpm.feature_loan_application.domain.factory

import com.potaninpm.feature_loan_application.presentation.models.LoanField
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class LoanFormFactoryTest {

    private val factory = LoanFormFactory()

    @Test
    fun `createForm for consumer returns correct fields`() {
        // GIVEN
        val loanType = "consumer"
        val expectedKeys = listOf(
            LoanField.FIO.key, LoanField.PASSPORT.key, LoanField.PASSPORT_ISSUED.key,
            LoanField.PASSPORT_DATE.key, LoanField.PASSPORT_CODE.key, LoanField.INN.key,
            LoanField.ADDRESS.key, LoanField.SNILS.key, LoanField.EMAIL.key,
            LoanField.PHONE.key, LoanField.ORG_NAME.key, LoanField.ORG_INN.key,
            LoanField.INCOME.key, LoanField.AMOUNT.key, LoanField.TERM.key
        )

        // WHEN
        val fields = factory.createForm(loanType)
        val actualKeys = fields.map { it.id }

        // THEN
        assertEquals(expectedKeys.size, fields.size)
        expectedKeys.forEach { key ->
            assertTrue("Missing field: $key", actualKeys.contains(key))
        }
    }

    @Test
    fun `createForm for mortgage returns correct fields`() {
        // GIVEN
        val loanType = "mortgage"
        val expectedKeys = listOf(
            LoanField.FIO.key, LoanField.PASSPORT.key, LoanField.PASSPORT_ISSUED.key,
            LoanField.PASSPORT_DATE.key, LoanField.PASSPORT_CODE.key, LoanField.INN.key,
            LoanField.ADDRESS.key, LoanField.SNILS.key, LoanField.EMAIL.key,
            LoanField.PHONE.key, LoanField.ORG_NAME.key, LoanField.ORG_INN.key,
            LoanField.INCOME.key, LoanField.OBJ_ADDRESS.key, LoanField.AMOUNT.key,
            LoanField.TERM.key
        )

        // WHEN
        val fields = factory.createForm(loanType)
        val actualKeys = fields.map { it.id }

        // THEN
        assertEquals(expectedKeys.size, fields.size)
        expectedKeys.forEach { key ->
            assertTrue("Missing field: $key", actualKeys.contains(key))
        }
    }

    @Test
    fun `createForm for auto returns correct fields including separate names`() {
        // GIVEN
        val loanType = "auto"
        val expectedKeys = listOf(
            LoanField.SURNAME.key, LoanField.NAME.key, LoanField.PATRONYMIC.key,
            LoanField.PASSPORT.key, LoanField.PASSPORT_ISSUED.key, LoanField.PASSPORT_DATE.key,
            LoanField.PASSPORT_CODE.key, LoanField.INN.key, LoanField.ADDRESS.key,
            LoanField.SNILS.key, LoanField.EMAIL.key, LoanField.PHONE.key,
            LoanField.ORG_NAME.key, LoanField.ORG_INN.key, LoanField.INCOME.key,
            LoanField.CAR_BRAND.key, LoanField.AMOUNT.key, LoanField.TERM.key
        )

        // WHEN
        val fields = factory.createForm(loanType)
        val actualKeys = fields.map { it.id }

        // THEN
        assertEquals(expectedKeys.size, fields.size)
        expectedKeys.forEach { key ->
            assertTrue("Missing field: $key", actualKeys.contains(key))
        }
    }

    @Test
    fun `createForm for credit_card returns correct fields`() {
        // GIVEN
        val loanType = "credit_card"
        val expectedKeys = listOf(
            LoanField.FIO.key, LoanField.PASSPORT.key, LoanField.PASSPORT_ISSUED.key,
            LoanField.PASSPORT_DATE.key, LoanField.PASSPORT_CODE.key, LoanField.INN.key,
            LoanField.ADDRESS.key, LoanField.SNILS.key, LoanField.ZIP.key,
            LoanField.PHONE.key, LoanField.ORG_NAME.key, LoanField.ORG_INN.key,
            LoanField.INCOME.key
        )

        // WHEN
        val fields = factory.createForm(loanType)
        val actualKeys = fields.map { it.id }

        // THEN
        assertEquals(expectedKeys.size, fields.size)
        expectedKeys.forEach { key ->
            assertTrue("Missing field: $key", actualKeys.contains(key))
        }
    }

    @Test
    fun `createForm for leasing returns correct fields`() {
        // GIVEN
        val loanType = "leasing"
        val expectedKeys = listOf(
            LoanField.FIO.key, LoanField.PASSPORT.key, LoanField.PASSPORT_ISSUED.key,
            LoanField.PASSPORT_DATE.key, LoanField.PASSPORT_CODE.key, LoanField.INN.key,
            LoanField.ADDRESS.key, LoanField.SNILS.key, LoanField.EMAIL.key,
            LoanField.PHONE.key, LoanField.ORG_NAME.key, LoanField.ORG_INN.key,
            LoanField.INCOME.key, LoanField.PROPERTY_TYPE.key, LoanField.AMOUNT.key,
            LoanField.TERM.key, LoanField.ADVANCE.key
        )

        // WHEN
        val fields = factory.createForm(loanType)
        val actualKeys = fields.map { it.id }

        // THEN
        assertEquals(expectedKeys.size, fields.size)
        expectedKeys.forEach { key ->
            assertTrue("Missing field: $key", actualKeys.contains(key))
        }
    }

    @Test
    fun `createForm for unknown type returns empty list`() {
        // GIVEN
        val loanType = "unknown"

        // WHEN
        val fields = factory.createForm(loanType)

        // THEN
        assertTrue(fields.isEmpty())
    }
}
