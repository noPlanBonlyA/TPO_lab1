package lab1

data class Character(val name: String, val action: String)

data class Location(val name: String, val description: String)

data class Object(val name: String, val state: String)

data class Environment(val description: String)

fun createDomainModel() {
    val zafod = Character("Зафод", "расчищает проход")
    val marvin = Character("Марвин", "быстро расчищает проход")

    val cave = Location("Пещера", "Сеть галерей и переходов, заваленных обломками и потрохами")
    val depths = Environment("Сырой воздух из темных глубин, пыльный мрак")

    val whale = Object("Кит", "упал, образовав провал")
    val debris = Object("Обломки", "завалены")
    val flashlight = Object("Фонарь", "светит в темноту")

    println("Персонажи: $zafod, $marvin")
    println("Локация: $cave")
    println("Окружение: $depths")
    println("Объекты: $whale, $debris, $flashlight")
}

fun main() {
    createDomainModel()
}
