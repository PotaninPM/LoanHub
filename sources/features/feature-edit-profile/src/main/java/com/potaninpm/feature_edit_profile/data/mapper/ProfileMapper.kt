import com.potaninpm.feature_edit_profile.utils.UserProfile
import com.potaninpm.network.supabase.profile.models.UserProfileDto
import java.text.SimpleDateFormat
import java.util.Locale

private val uiDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
private val apiDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

private fun String?.toUiDate(): String {
    if (this.isNullOrBlank()) return ""
    return try {
        val date = apiDateFormat.parse(this)
        uiDateFormat.format(date!!)
    } catch (e: Exception) {
        this
    }
}

private fun String.toApiDate(): String? {
    if (this.isBlank()) return null
    return try {
        val date = uiDateFormat.parse(this)
        apiDateFormat.format(date!!)
    } catch (e: Exception) {
        null
    }
}

fun UserProfileDto.toDomain(): UserProfile = UserProfile(
    id = id.orEmpty(),
    firstName = firstName.orEmpty(),
    lastName = lastName.orEmpty(),
    patronymic = patronymic.orEmpty(),
    birthDate = birthDate.toUiDate(),
    phone = phone.orEmpty(),
    email = email.orEmpty(),
    
    passportSeriesNumber = passportSeriesNumber.orEmpty(),
    passportIssuedBy = passportIssuedBy.orEmpty(),
    passportIssueDate = passportIssueDate.toUiDate(),
    passportDivisionCode = passportDivisionCode.orEmpty(),
    
    addressRegistration = addressRegistration.orEmpty(),
    postalCode = postalCode.orEmpty(),
    inn = inn.orEmpty(),
    snils = snils.orEmpty(),
    
    employerName = employerName.orEmpty(),
    employerInn = employerInn.orEmpty(),
    jobTitle = jobTitle.orEmpty(),
    workExperience = workExperience.orEmpty(),
    monthlyIncome = monthlyIncome,
    
    role = role.orEmpty()
)

fun UserProfile.toDto(): UserProfileDto = UserProfileDto(
    firstName = firstName,
    lastName = lastName,
    patronymic = patronymic,
    birthDate = birthDate.toApiDate(),
    phone = phone,
    email = email,
    
    passportSeriesNumber = passportSeriesNumber,
    passportIssuedBy = passportIssuedBy,
    passportIssueDate = passportIssueDate.toApiDate(),
    passportDivisionCode = passportDivisionCode,
    
    addressRegistration = addressRegistration,
    postalCode = postalCode,
    inn = inn,
    snils = snils,
    
    employerName = employerName,
    employerInn = employerInn,
    jobTitle = jobTitle,
    workExperience = workExperience,
    monthlyIncome = monthlyIncome
)
