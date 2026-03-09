package com.argio.enums;

/**
 * Represents the category of income.
 *
 * This enum defines a set of constants that represent different
 * types of income sources. Each constant corresponds to a specific
 * category of income that can be used for classification purposes.
 *
 * The available income categories are:
 * - SALARY: Regular income received as part of a salary or wage.
 * - BENEFIT: Income received as a benefit or allowance.
 * - REIMBURSEMENT: Income received as reimbursements for expenses incurred.
 * - OTHERS: Any other income not categorized above.
 *
 * This enum is typically used in conjunction with the Income class
 * to categorize and manage income data within the application.
 */
public enum IncomeCategory {
    SALARY,
    BENEFIT,
    REIMBURSEMENT,
    OTHERS
}