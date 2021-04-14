package de.rki.coronawarnapp.coronatest.type.pcr

import com.google.gson.annotations.SerializedName
import de.rki.coronawarnapp.coronatest.qrcode.CoronaTestGUID
import de.rki.coronawarnapp.coronatest.server.CoronaTestResult
import de.rki.coronawarnapp.coronatest.type.CoronaTest
import de.rki.coronawarnapp.coronatest.type.RegistrationToken
import org.joda.time.Instant

data class PCRCoronaTest(
    @SerializedName("testGUID") override val testGUID: CoronaTestGUID,
    @SerializedName("registeredAt") override val registeredAt: Instant,
    @SerializedName("registrationToken") override val registrationToken: RegistrationToken,
    @SerializedName("isSubmitted") override val isSubmitted: Boolean = false,
    @SerializedName("isViewed") override val isViewed: Boolean = false,
    @SerializedName("isAdvancedConsentGiven") override val isAdvancedConsentGiven: Boolean = false,
    @SerializedName("isJournalEntryCreated") override val isJournalEntryCreated: Boolean = false,
    @SerializedName("isNotificationSent") override val isNotificationSent: Boolean = false,
    @SerializedName("testResult") val testResult: CoronaTestResult,
    @Transient override val isProcessing: Boolean = false,
    @Transient override val lastError: Throwable? = null,
) : CoronaTest {

    override val type: CoronaTest.Type = CoronaTest.Type.PCR

    override val isSubmissionAllowed: Boolean = testResult == CoronaTestResult.PCR_POSITIVE

    val state: State = when (testResult) {
        CoronaTestResult.PCR_OR_RAT_PENDING -> State.PENDING
        CoronaTestResult.PCR_NEGATIVE -> State.NEGATIVE
        CoronaTestResult.PCR_POSITIVE -> State.POSITIVE
        CoronaTestResult.PCR_INVALID -> State.INVALID
        CoronaTestResult.PCR_REDEEMED -> State.REDEEMED
        else -> throw IllegalArgumentException("Invalid PCR test state $testResult")
    }

    enum class State {
        PENDING,
        INVALID,
        POSITIVE,
        NEGATIVE,
        REDEEMED,
    }
}
