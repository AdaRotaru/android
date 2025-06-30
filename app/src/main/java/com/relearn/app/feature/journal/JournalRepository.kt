package com.relearn.app.feature.journal

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.*
import com.google.firebase.firestore.FirebaseFirestore
import com.relearn.app.feature.HOME.model.MoodType
import kotlinx.serialization.encodeToString
import javax.inject.Inject
import javax.inject.Named

class JournalRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    @Named("OpenAiApiKey") private val apiKey: String
) : JournalInterface {

    override suspend fun submitJournalEntry(content: String): JournalEntry {
        Log.d("JournalRepo", "submitJournalEntry called")
        val userId = auth.currentUser?.uid ?: throw IllegalStateException("User not authenticated")
        val mood = extractMoodFromText(content)
        val suggestions = generateSuggestionsFromText(content)

        val entry = JournalEntry(
            id = "",
            userId = userId,
            content = content,
            mood = mood,
            suggestions = suggestions
        )

        val docRef = firestore.collection("journalEntries").document()
        val savedEntry = entry.copy(id = docRef.id)
        docRef.set(savedEntry)
        return savedEntry
    }

    private suspend fun generateSuggestionsFromText(text: String): List<String> {
        val prompt = """
Text jurnal: "$text"

Răspunde strict în limba română. Nu folosi limba engleză deloc.

Generează exact 3 acțiuni scurte, clare și utile care pot ajuta utilizatorul. Scrie-le numerotate fix în acest format:
1. ...
2. ...
3. ...

Nu adăuga alte explicații, fraze introductive sau concluzii. Răspunsul trebuie să conțină doar lista numerotată.
""".trimIndent()

        Log.d("JournalRepo", "Prompt sugestii: $prompt")
        val response = openAiApiCall(prompt)
        Log.d("JournalRepo", "Răspuns AI sugestii: $response")

        return response
            .split("\n")
            .map { it.trim() }
            .filter { it.matches(Regex("^\\d+\\.\\s.+")) }
            .map { it.substringAfter(". ").trim() }
    }

    private suspend fun extractMoodFromText(text: String): MoodType? {
        val prompt = """
Text jurnal: "$text"

Clasifică starea emoțională într-unul dintre: happy, sad, anxious, calm, angry, stressed.
Răspunde exact cu un singur cuvânt. Nu adăuga alt text.
""".trimIndent()

        Log.d("JournalRepo", "Prompt mood: $prompt")
        val response = openAiApiCall(prompt)
        Log.d("JournalRepo", "Răspuns AI mood: $response")
        return MoodType.values().firstOrNull { it.name.equals(response.trim(), ignoreCase = true) }
    }

    private suspend fun openAiApiCall(prompt: String): String {
        val client = HttpClient(CIO)
        Log.d("JournalRepo", "Sending POST to OpenAI")

        val requestBody = buildJsonObject {
            put("model", "gpt-4o-mini")
            putJsonArray("messages") {
                addJsonObject {
                    put("role", "system")
                    put("content", "Ești un asistent care răspunde scurt și clar în română.")
                }
                addJsonObject {
                    put("role", "user")
                    put("content", prompt)
                }
            }
        }

        val response: String = client.post("https://api.openai.com/v1/chat/completions") {
            headers {
                append("Authorization", "Bearer $apiKey")
                append("Content-Type", "application/json")
            }
            setBody(Json.encodeToString(requestBody))
        }.bodyAsText()

        val json = Json.parseToJsonElement(response).jsonObject
        return json["choices"]?.jsonArray?.firstOrNull()
            ?.jsonObject?.get("message")?.jsonObject?.get("content")?.jsonPrimitive?.content ?: ""
    }

}
