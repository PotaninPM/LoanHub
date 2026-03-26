package com.potaninpm.feature_my_requests.utils

object MyRequestsScreenTags {
    const val SUBMITTED_LIST = "submitted_applications_list"
    const val DRAFTS_SECTION = "drafts_section"

    fun filterChip(label: String) = "filter_chip_$label"
    fun applicationCard(id: String) = "application_card_$id"
    fun draftCard(id: String) = "draft_card_$id"
}