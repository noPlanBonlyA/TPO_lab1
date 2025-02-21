package lab1

interface Entity {
    val name: String
    var state: String
}

data class Person(override val name: String, override var state: String = "") : Entity
data class Place(
    override val name: String,
    var description: String,
    override var state: String = ""
) : Entity
data class Thing(override val name: String, override var state: String) : Entity

sealed class Action {
    abstract val actor: Entity
    abstract val verb: String
    abstract fun execute(): String
}

data class MoveAction(
    override val actor: Entity,
    val destination: Place
) : Action() {
    override val verb: String = "перемещается"
    override fun execute(): String {
        actor.state = "$verb в ${destination.name}"
        return "${actor.name} $verb в ${destination.name}"
    }
}

data class InteractAction(
    override val actor: Entity,
    val target: Entity
) : Action() {
    override val verb: String = "взаимодействует"
    override fun execute(): String {
        actor.state = "$verb с ${target.name}"
        target.state = "использован персонажем ${actor.name}"
        return "${actor.name} $verb с ${target.name}"
    }
}

data class IlluminateAction(
    override val actor: Entity,
    val target: Thing,
    val environment: Place
) : Action() {
    override val verb: String = "освещает"
    override fun execute(): String {
        actor.state = "$verb ${target.name}"
        target.state = "включен"
        environment.state = "Освещенная ${environment.name}, мрак рассеян"
        return "${actor.name} $verb ${target.name} и изменяет обстановку в ${environment.name}"
    }
}

class Story {
    private val actions = mutableListOf<Action>()

    fun addAction(action: Action) {
        actions.add(action)
        println(action.execute())
    }

    fun narrate() {
        println("\nНарратив истории:")
        actions.forEach { println("- ${it.execute()}") }
    }
}

fun story(block: Story.() -> Unit) {
    val story = Story()
    story.block()

     story.narrate()
}

fun main() {

    val zafod = Person("Зафод", "осматривается")
    val marvin = Person("Марвин", "ждёт команды")


    val cave = Place("Пещера", "Сеть галерей и переходов, заваленных обломками и потрохами")


    val whale = Thing("Кит", "упал, образовав провал")
    val debris = Thing("Обломки", "завалены")
    val flashlight = Thing("Фонарь", "выключен")


    story {
        println(
            "Земля провалилась там, где упал ${whale.name}, " +
                    "обнаружив целую сеть галерей и переходов, " +
                    "которые теперь были сильно завалены ${debris.name} и потрохами."
        )
        println(
            "${zafod.name} начал расчищать один из проходов, но у ${marvin.name} это вышло гораздо быстрее."
        )
        println(
            "Сырой воздух поднимался из темных глубин, и когда ${zafod.name} посветил ${flashlight.name} внутрь, " +
                    "мало что было видно в пыльном мраке.\n"
        )


        addAction(MoveAction(zafod, cave))
        addAction(InteractAction(marvin, debris))
        addAction(InteractAction(zafod, flashlight))
        addAction(IlluminateAction(zafod, flashlight, cave))
    }
}
