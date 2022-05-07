package cl.alphacode.reigntest.event

open class Event<out T> (private val content: T) {
    var hasBeenHandled = false
    private set // permite lecturas externas pero no escritura

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}